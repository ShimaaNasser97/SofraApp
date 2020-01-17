package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.orders;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.ViewPagerWithFragmentAdapter;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RestaurantOrderFragment extends BaseFragment {

    @BindView(R.id.restaurant_order_fragment_tl_tabs)
    TabLayout restaurantOrderFragmentTlTabs;
    @BindView(R.id.restaurant_order_fragment_vp_viewpager)
    ViewPager restaurantOrderFragmentVpViewpager;
    Unbinder unbinder;
    private ViewPagerWithFragmentAdapter viewPagerWithFragmentAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewPagerWithFragmentAdapter=new ViewPagerWithFragmentAdapter(getActivity().getSupportFragmentManager());
        ResraurantPendingFragment resraurantPendingOrderFragment= new ResraurantPendingFragment();
        RestaurantCurrentFragment resraurantCurrentFragment= new RestaurantCurrentFragment();
        RestaurantComplietFragment resraurantCompletedFragment= new RestaurantComplietFragment();

        resraurantPendingOrderFragment.type = "pending";
        resraurantCurrentFragment.type = "current";
        resraurantCompletedFragment.type = "completed";

        viewPagerWithFragmentAdapter.addPager(resraurantPendingOrderFragment, "طلبات جديدة");
        viewPagerWithFragmentAdapter.addPager(resraurantCurrentFragment, "طلبات حالية");
        viewPagerWithFragmentAdapter.addPager(resraurantCompletedFragment, "طلبات سابقة");
        restaurantOrderFragmentVpViewpager.setAdapter(viewPagerWithFragmentAdapter);
        restaurantOrderFragmentTlTabs.setupWithViewPager(restaurantOrderFragmentVpViewpager);
        //homeFragmentTlTabs.setSelectedTabIndicatorColor(R.color.colorPrimaryDark);

        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
