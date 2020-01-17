package com.example.sofra.veiw.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//import com.example.sofra.R;
import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.NewOrderFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.NotificationFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.RestaurantsFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.more.ClintMoreFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.orders.ClintOrderFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.MyCategoryFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.ProfileFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.more.RestaurantMoreFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.orders.RestaurantOrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.CLINT;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;

public class HomeCycleActivity extends BaseActivity {

    @BindView(R.id.home_cycle_activity_iv_notification)
    ImageView homeCycleActivityIvNotification;
    @BindView(R.id.home_cycle_activity_iv_market)
    ImageView homeCycleActivityIvMarket;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_cycle_activity_fl_contaner)
    FrameLayout homeCycleActivityFlContaner;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);
        if (SharedPreferencesManger.LoadData(this, USER_TYPE).equals(CLINT)) {
            RestaurantsFragment restaurantsFragment = new RestaurantsFragment();
            HelperMethod.replace(restaurantsFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
        }/*else{
            NewOrderFragment newOrderFragment=new NewOrderFragment();
            HelperMethod.replace(newOrderFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);*/
     //   }
        if (SharedPreferencesManger.LoadData(this, USER_TYPE).equals(RESTAURANT)) {
            homeCycleActivityIvMarket.setBackgroundResource(R.drawable.ic_commetion);
            MyCategoryFragment myCategoryFragment = new MyCategoryFragment();
            HelperMethod.replace(myCategoryFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (SharedPreferencesManger.LoadData(HomeCycleActivity.this, USER_TYPE).equals(CLINT)) {
                        RestaurantsFragment restaurantsFragment = new RestaurantsFragment();
                        HelperMethod.replace(restaurantsFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                    }
                    if (SharedPreferencesManger.LoadData(HomeCycleActivity.this, USER_TYPE).equals(RESTAURANT)) {
                        MyCategoryFragment myCategoryFragment = new MyCategoryFragment();
                        HelperMethod.replace(myCategoryFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (SharedPreferencesManger.LoadData(HomeCycleActivity.this, USER_TYPE).equals(CLINT)) {
                        ClintOrderFragment clintOrderFragment = new ClintOrderFragment();
                        HelperMethod.replace(clintOrderFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                    }
                    if (SharedPreferencesManger.LoadData(HomeCycleActivity.this, USER_TYPE).equals(RESTAURANT)) {

                        RestaurantOrderFragment restaurantOrderFragment = new RestaurantOrderFragment();
                        HelperMethod.replace(restaurantOrderFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                    }
                    return true;
                case R.id.navigation_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    HelperMethod.replace(profileFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);

                    return true;
                case R.id.navigation_more:
                    if (SharedPreferencesManger.LoadData(HomeCycleActivity.this, USER_TYPE).equals(CLINT)) {
                        ClintMoreFragment clintMoreFragment = new ClintMoreFragment();
                        HelperMethod.replace(clintMoreFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                    }
                    if (SharedPreferencesManger.LoadData(HomeCycleActivity.this, USER_TYPE).equals(RESTAURANT)) {
                        RestaurantMoreFragment restaurantMoreFragment = new RestaurantMoreFragment();
                        HelperMethod.replace(restaurantMoreFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                    }
                    return true;

            }
            return false;
        }
    };

    @OnClick({R.id.home_cycle_activity_iv_notification, R.id.home_cycle_activity_iv_market})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_cycle_activity_iv_notification:
                NotificationFragment notificationFragment=new NotificationFragment();
                HelperMethod.replace(notificationFragment, getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                break;
            case R.id.home_cycle_activity_iv_market:
                break;
        }
    }
}
