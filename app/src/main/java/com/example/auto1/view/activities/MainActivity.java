package com.example.auto1.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auto1.R;
import com.example.auto1.constants.FragmentVariables;
import com.example.auto1.view.fragments.ManufacturerFragment;
import com.example.auto1.view.fragments.ModelFragment;
import com.example.auto1.view.fragments.SummaryFragment;
import com.example.auto1.view.fragments.YearFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.activityMain_container)
    FrameLayout activityMainContainer;
    @BindView(R.id.activityMain_toolbar)
    View content;
    ImageView backImg;
    TextView title;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        bindViews();
    }

    private void bindViews() {
        backImg = content.findViewById(R.id.toolbar_backBtn);
        title = content.findViewById(R.id.toolbar_title);
        toolbar = content.findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        backImg.setOnClickListener(this);
        selectFragment(FragmentVariables.MANUFACTURER_FRAGMENT, null);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void selectFragment(String type, Bundle bundle) {
        Fragment fragment = null;
        String fragmentTag = "";
        switch (type) {
            case FragmentVariables.MANUFACTURER_FRAGMENT:
                fragment = new ManufacturerFragment();
                fragmentTag = FragmentVariables.MANUFACTURER_FRAGMENT;
                break;
            case FragmentVariables.MAIN_TYPES_FRAGMENT:
                fragment = new ModelFragment();
                fragmentTag = FragmentVariables.MAIN_TYPES_FRAGMENT;
                break;
            case FragmentVariables.BUILT_DATE_FRAGMENT:
                fragment = new YearFragment();
                fragmentTag = FragmentVariables.BUILT_DATE_FRAGMENT;
                break;
            case FragmentVariables.SUMMARY_FRAGMENT:
                fragment = new SummaryFragment();
                fragmentTag = FragmentVariables.SUMMARY_FRAGMENT;
                break;
            default:
                return;
        }
        if (bundle != null)
            fragment.setArguments(bundle);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentStack = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (fragmentStack == null) {
            fragmentTransaction.replace(R.id.activityMain_container, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void setToolBarTitle(String titleTxt) {
        backImg.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        if (!titleTxt.equals(""))
            title.setText(titleTxt);
    }

    public void toggleToolbarItemVisibility(int visibility) {
        if (visibility == View.GONE) {
            backImg.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
        } else {
            backImg.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_backBtn:
                onBackPressed();
                break;
        }
    }


}
