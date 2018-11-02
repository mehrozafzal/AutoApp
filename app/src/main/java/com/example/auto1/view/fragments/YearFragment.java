package com.example.auto1.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.auto1.MyApplication;
import com.example.auto1.R;
import com.example.auto1.constants.ActivityVariables;
import com.example.auto1.constants.FragmentVariables;
import com.example.auto1.constants.ResponseVariables;
import com.example.auto1.model.Summary;
import com.example.auto1.retrofit.ApiResponse;
import com.example.auto1.retrofit.response.TypesResponse;
import com.example.auto1.utils.PreferenceUtils;
import com.example.auto1.utils.ProgressDialogUtils;
import com.example.auto1.utils.toast.ToastDuration;
import com.example.auto1.utils.toast.ToastUtils;
import com.example.auto1.view.activities.MainActivity;
import com.example.auto1.view.adapters.YearAdapter;
import com.example.auto1.viewModel.MainActivityViewModel;
import com.example.auto1.viewModel.ViewModelFactory;
import com.google.gson.JsonElement;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.auto1.constants.ActivityVariables.Keys.SUMMARY_OBJ_KEY;

public class YearFragment extends Fragment {

    Unbinder unbinder;
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    PreferenceUtils preferenceUtils;
    MainActivityViewModel mainActivityViewModel;
    @BindView(R.id.fragmentYears_list)
    RecyclerView fragmentYearsList;
    private Summary summary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_year, container, false);
        setRetainInstance(true);
        unbinder = ButterKnife.bind(this, view);
        bindViews();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        ((MyApplication) context.getApplicationContext()).getAppActivityComponent().doInjection(this);
        super.onAttach(context);
    }

    private void bindViews() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (mainActivityViewModel == null) {
                summary = bundle.getParcelable(ActivityVariables.Keys.SUMMARY_OBJ_KEY);
                ProgressDialogUtils.initProgressDialog(getContext(), "Fetching Years...");
                mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
                mainActivityViewModel.yearResponse().observe(this, this::consumeResponse);
                requestYears(summary.getManufactureKey(), summary.getModel());
                setToolBarTitle(summary.getManufacture() + ": " + summary.getModel());
            } else {
                mainActivityViewModel.yearResponse().observe(this, this::consumeResponse);
                setToolBarTitle(FragmentVariables.BUILT_DATE_FRAGMENT);
            }
        } else
            setToolBarTitle(FragmentVariables.BUILT_DATE_FRAGMENT);
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                ProgressDialogUtils.showProgressDialog();
                break;

            case SUCCESS:
                ProgressDialogUtils.dismissProgressDialog();
                if (apiResponse.data != null) {
                    renderYearsResponse(apiResponse.data);
                }
                break;

            case ERROR:
                ProgressDialogUtils.dismissProgressDialog();
                if (apiResponse.error != null) {
                    Log.d(ActivityVariables.Tags.MAIN_ACTIVITY_TAG, apiResponse.error.getMessage());
                    ToastUtils.getInstance().showToast(getContext(), apiResponse.error.toString(), ToastDuration.LONG);
                }
                break;
            default:
                break;
        }
    }

    private void renderYearsResponse(JsonElement response) {
        if (!response.isJsonNull()) {
            TypesResponse typesResponse = (TypesResponse) preferenceUtils.convertStringToObject(response.toString(), TypesResponse.class);
            final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
            fragmentYearsList.setLayoutAnimation(controller);
            fragmentYearsList.setAdapter(new YearAdapter(typesResponse.getWkdaTypeMap(), getActivity(), (item, key) -> {
                Bundle bundle = new Bundle();
                summary.setYear(item);
                bundle.putParcelable(SUMMARY_OBJ_KEY, summary);
                ((MainActivity) Objects.requireNonNull(getActivity())).selectFragment(FragmentVariables.SUMMARY_FRAGMENT, bundle);
            }));
            fragmentYearsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void requestYears(String manufacture, String model) {
        mainActivityViewModel.hitYearApi(manufacture, model, ResponseVariables.wa_key);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setToolBarTitle(String title) {
        ((MainActivity) Objects.requireNonNull(getActivity())).setToolBarTitle(title);
    }
}