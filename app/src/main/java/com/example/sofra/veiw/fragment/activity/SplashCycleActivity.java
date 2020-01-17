package com.example.sofra.veiw.fragment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.CLINT;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT;
import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;

public class SplashCycleActivity extends AppCompatActivity {

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.splash_activity_btn_order, R.id.splash_activity_btn_bay})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.splash_activity_btn_order:
                SharedPreferencesManger.SaveData(this, USER_TYPE, CLINT);
                //intent = new Intent(SplashCycleActivity.this, HomeCycleActivity.class);
                intent = new Intent(SplashCycleActivity.this, UseaCycleActivity.class);
                startActivity(intent);
                break;
            case R.id.splash_activity_btn_bay:
                SharedPreferencesManger.SaveData(this, USER_TYPE, RESTAURANT);
              //  intent = new Intent(SplashCycleActivity.this, HomeCycleActivity.class);
                intent = new Intent(SplashCycleActivity.this, UseaCycleActivity.class);
                startActivity(intent);
                break;

        }
    }
}
