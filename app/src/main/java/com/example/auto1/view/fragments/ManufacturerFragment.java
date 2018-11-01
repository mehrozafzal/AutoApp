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
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.example.auto1.MyApplication;
import com.example.auto1.R;
import com.example.auto1.constants.ActivityVariables;
import com.example.auto1.constants.FragmentVariables;
import com.example.auto1.constants.ResponseVariables;
import com.example.auto1.model.Summary;
import com.example.auto1.retrofit.ApiResponse;
import com.example.auto1.retrofit.response.ManufacturerResponse;
import com.example.auto1.utils.PreferenceUtils;
import com.example.auto1.utils.ProgressDialogUtils;
import com.example.auto1.utils.toast.ToastDuration;
import com.example.auto1.utils.toast.ToastUtils;
import com.example.auto1.view.activities.MainActivity;
import com.example.auto1.view.adapters.ManufacturerAdapter;
import com.example.auto1.viewModel.MainActivityViewModel;
import com.example.auto1.viewModel.ViewModelFactory;
import com.google.gson.JsonElement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.auto1.constants.ActivityVariables.Keys.SUMMARY_OBJ_KEY;

public class ManufacturerFragment extends Fragment {

    Unbinder unbinder;
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    PreferenceUtils preferenceUtils;
    MainActivityViewModel mainActivityViewModel;
    @BindView(R.id.fragmentManufacturers_list)
    RecyclerView fragmentManufacturersList;
    @BindView(R.id.fragmentManufacturers_progressBar)
    ProgressBar fragmentManufacturersProgressBar;

    private ManufacturerAdapter manufacturerAdapter;
    private boolean isScrolling;
    private int currentItems;
    private int totalItems;
    private int scrolledOutItems;
    private LinearLayoutManager layoutManager;
    private int PAGE = 1;
    private int TOTAL_PAGE = 0;
    private int PAGE_SIZE = 15;
    private LinkedHashMap<String, String> manufacturersSet;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manufacturers, container, false);
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
        layoutManager = new LinearLayoutManager(getContext());
        manufacturersSet = new LinkedHashMap<>();
        recyclerScrollListener();

        ((MainActivity) Objects.requireNonNull(getActivity())).setToolBarTitle(FragmentVariables.MANUFACTURER_FRAGMENT);
        if (mainActivityViewModel == null) {
            ProgressDialogUtils.initProgressDialog(getContext(), "Fetching Manufacturers...");
            mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
            mainActivityViewModel.manufacturerResponse().observe(this, this::consumeResponse);
            requestManufacturers(String.valueOf(PAGE), String.valueOf(PAGE_SIZE));
        } else {
            PAGE = 1;
            requestManufacturers(String.valueOf(1), String.valueOf(PAGE_SIZE));
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                if (PAGE == 1)
                    ProgressDialogUtils.showProgressDialog();
                break;

            case SUCCESS:
                ProgressDialogUtils.dismissProgressDialog();
                if (apiResponse.data != null) {
                    renderManufacturersResponse(apiResponse.data);
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

    private void renderManufacturersResponse(JsonElement response) {
        if (!response.isJsonNull()) {
            fragmentManufacturersProgressBar.setVisibility(View.GONE);
            ManufacturerResponse manufacturerResponse = (ManufacturerResponse) preferenceUtils.convertStringToObject(response.toString(), ManufacturerResponse.class);
            if (PAGE == 1) {
                final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
                fragmentManufacturersList.setLayoutAnimation(controller);
                manufacturerAdapter = new ManufacturerAdapter(manufacturerResponse.getWkdaMap(), getActivity(), (item, key) -> {
                    Bundle bundle = new Bundle();
                    Summary summary = new Summary();
                    summary.setManufacture(item);
                    summary.setManufactureKey(key);
                    bundle.putParcelable(SUMMARY_OBJ_KEY, summary);
                    ((MainActivity) Objects.requireNonNull(getActivity())).selectFragment(FragmentVariables.MAIN_TYPES_FRAGMENT, bundle);
                });
                fragmentManufacturersList.setAdapter(manufacturerAdapter);
                fragmentManufacturersList.setLayoutManager(layoutManager);
                updateDataSet(manufacturerResponse.getWkdaMap());
                TOTAL_PAGE = manufacturerResponse.getTotalPageCount();
            } else {
                if (manufacturerAdapter != null) {
                    updateDataSet(manufacturerResponse.getWkdaMap());
                    manufacturerAdapter.notifyManufacturerAdapter(manufacturersSet);
                }
            }
        }
    }

    private void updateDataSet(LinkedHashMap<String, String> manufacturers) {
        for (Map.Entry e : manufacturers.entrySet()) {
            if (!manufacturersSet.containsKey(e.getKey()))
                manufacturersSet.put(e.getKey().toString(), e.getValue().toString());
        }
    }

    private void requestManufacturers(String page, String pageSize) {
        if (PAGE != 1)
            fragmentManufacturersProgressBar.setVisibility(View.VISIBLE);
        mainActivityViewModel.hitManufacturerApi(page, pageSize, ResponseVariables.wa_key);
    }

    private void recyclerScrollListener() {
        fragmentManufacturersList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (PAGE <= TOTAL_PAGE) {
                    currentItems = layoutManager.getChildCount();
                    totalItems = layoutManager.getItemCount();
                    scrolledOutItems = layoutManager.findFirstVisibleItemPosition();
                    if (isScrolling && (currentItems + scrolledOutItems == totalItems)) {
                        isScrolling = false;
                        PAGE++;
                        if (PAGE <= TOTAL_PAGE)
                            requestManufacturers(String.valueOf(PAGE), String.valueOf(PAGE_SIZE));
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
