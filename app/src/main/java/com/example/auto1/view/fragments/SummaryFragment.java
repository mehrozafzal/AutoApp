package com.example.auto1.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.auto1.R;
import com.example.auto1.constants.ActivityVariables;
import com.example.auto1.constants.FragmentVariables;
import com.example.auto1.model.Summary;
import com.example.auto1.view.activities.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SummaryFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.fragmentSummary_manufactureTextView)
    TextView fragmentSummaryManufactureTextView;
    @BindView(R.id.fragmentSummary_modelText)
    TextView fragmentSummaryModelText;
    @BindView(R.id.fragmentSummary_yearText)
    TextView fragmentSummaryYearText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        setRetainInstance(true);
        unbinder = ButterKnife.bind(this, view);
        bindViews();
        return view;
    }

    private void bindViews() {
        setToolBarTitle();
        Bundle bundle = getArguments();
        if (bundle != null) {
            Summary summary = bundle.getParcelable(ActivityVariables.Keys.SUMMARY_OBJ_KEY);
            assert summary != null;
            fragmentSummaryManufactureTextView.setText(summary.getManufacture());
            fragmentSummaryModelText.setText(summary.getModel());
            fragmentSummaryYearText.setText(summary.getYear());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setToolBarTitle() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setToolBarTitle(FragmentVariables.SUMMARY_FRAGMENT);
    }

}
