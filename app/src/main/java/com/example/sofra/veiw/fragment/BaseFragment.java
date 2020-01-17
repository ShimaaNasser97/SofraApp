package com.example.sofra.veiw.fragment;

import android.support.v4.app.Fragment;

import com.example.sofra.veiw.activity.BaseActivity;


public class BaseFragment extends Fragment {

    public BaseActivity baseActivity;

    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onBack() {
        baseActivity.superBackPressed();
    }

}
