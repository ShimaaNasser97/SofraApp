package com.example.sofra.veiw.activity;

import android.os.Bundle;

import com.example.sofra.R;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.userCycleFragment.LoginFragment;

public class UseaCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        LoginFragment loginFragment=new LoginFragment();
        HelperMethod.replace(loginFragment,getSupportFragmentManager(),R.id.auth_cycle_activity_fl_contaner,null,null);
    }
}
