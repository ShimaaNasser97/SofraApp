package com.example.sofra.veiw.activity;

import android.support.v7.app.AppCompatActivity;

import com.example.sofra.veiw.fragment.BaseFragment;

public class BaseActivity extends AppCompatActivity {

     public BaseFragment baseFragment;

    public void superBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
}
