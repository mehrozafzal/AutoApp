package com.example.auto1.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.auto1.view.adapters.ModelAdapter;
import com.example.auto1.viewModel.MainActivityViewModel;
import com.example.auto1.viewModel.ViewModelFactory;
import com.google.gson.JsonElement;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.auto1.constants.ActivityVariables.Keys.SUMMARY_OBJ_KEY;

public class ModelFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    Unbinder unbinder;
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    PreferenceUtils preferenceUtils;
    MainActivityViewModel mainActivityViewModel;
    @BindView(R.id.fragmentModels_list)
    RecyclerView fragmentModelsList;


    private Summary summary = null;
    private ModelAdapter modelAdapter;
    private LinkedHashMap<String, String> models;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model, container, false);
        setHasOptionsMenu(true);
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
                ProgressDialogUtils.initProgressDialog(getContext(), "Fetching Models...");
                mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
                mainActivityViewModel.modelResponse().observe(this, this::consumeResponse);
                requestModels(summary.getManufactureKey(), "1", "10");
                setToolBarTitle(summary.getManufacture());
            } else {
                mainActivityViewModel.modelResponse().observe(this, this::consumeResponse);
                setToolBarTitle((summary.getManufacture()));
            }
        } else
            setToolBarTitle(FragmentVariables.MAIN_TYPES_FRAGMENT);
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
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
            TypesResponse typesResponse = (TypesResponse) preferenceUtils.convertStringToObject(response.toString(), TypesResponse.class);
            final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
            models = typesResponse.getWkdaTypeMap();
            modelAdapter = new ModelAdapter(typesResponse.getWkdaTypeMap(), getActivity(), (item, key) -> {
                Bundle bundle = new Bundle();
                summary.setModel(item);
                bundle.putParcelable(SUMMARY_OBJ_KEY, summary);
                ((MainActivity) Objects.requireNonNull(getActivity())).selectFragment(FragmentVariables.BUILT_DATE_FRAGMENT, bundle);
            });
            fragmentModelsList.setLayoutAnimation(controller);
            fragmentModelsList.setAdapter(modelAdapter);
            fragmentModelsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void requestModels(String manufacture, String page, String pageSize) {
        mainActivityViewModel.hitModelApi(manufacture, page, pageSize, ResponseVariables.wa_key);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setToolBarTitle(String title) {
        ((MainActivity) Objects.requireNonNull(getActivity())).setToolBarTitle(title);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_model, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchItem.setOnActionExpandListener(this);
        searchView.setQueryHint("Search Model");

        super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetAdapter();
            return false;
        }

        LinkedHashMap<String, String> filteredValues = new LinkedHashMap<>(models);
        Iterator it = models.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (!pair.getValue().toString().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(pair.getKey().toString());
            }
        }
        modelAdapter.updateDataSet(filteredValues);
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        ((MainActivity) Objects.requireNonNull(getActivity())).toggleToolbarItemVisibility(View.GONE);
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        ((MainActivity) Objects.requireNonNull(getActivity())).toggleToolbarItemVisibility(View.VISIBLE);
        return true;
    }

    public void resetAdapter() {
        if (modelAdapter != null)
            modelAdapter.updateDataSet(models);
    }
}

