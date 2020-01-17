package com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.ViewPagerWithFragmentAdapter;
import com.example.sofra.data.model.restaurants.RestauranrsData;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RestaurantDetailsFragment extends BaseFragment {
    public static RestauranrsData restaurant;
    @BindView(R.id.restaurant_details_fragment_tl_tabs)
    TabLayout restaurantDetailsFragmentTlTabs;
    @BindView(R.id.restaurant_details_fragment_vp_viewpager)
    ViewPager restaurantDetailsFragmentVpViewpager;
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
        View view = inflater.inflate(R.layout.fragment_rstaurant_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewPagerWithFragmentAdapter = new ViewPagerWithFragmentAdapter(getActivity().getSupportFragmentManager());
        MinuFragment minuFragment = new MinuFragment();

        viewPagerWithFragmentAdapter.addPager(minuFragment, "قائمة الطعام");
        viewPagerWithFragmentAdapter.addPager(new CommentsFragment(), "التعليقات والتقييم");
        viewPagerWithFragmentAdapter.addPager(new InformationFragment(), "معلومات المتجر");
        restaurantDetailsFragmentVpViewpager.setAdapter(viewPagerWithFragmentAdapter);
        restaurantDetailsFragmentTlTabs.setupWithViewPager(restaurantDetailsFragmentVpViewpager);
        //homeFragmentTlTabs.setSelectedTabIndicatorColor(R.color.colorPrimaryDark);

        return view;
    }

    @Override
    public void onBack() {
        baseActivity.finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
