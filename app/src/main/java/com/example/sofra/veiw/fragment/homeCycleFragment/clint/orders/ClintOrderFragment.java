package com.example.sofra.veiw.fragment.homeCycleFragment.clint.orders;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.ViewPagerWithFragmentAdapter;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.CommentsFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.InformationFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.MinuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ClintOrderFragment extends BaseFragment {

    @BindView(R.id.clint_order_fragment_tl_tabs)
    TabLayout clintOrderFragmentTlTabs;
    @BindView(R.id.clint_order_fragment_vp_viewpager)
    ViewPager clintOrderFragmentVpViewpager;
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
        View view = inflater.inflate(R.layout.fragment_clint_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewPagerWithFragmentAdapter = new ViewPagerWithFragmentAdapter(getActivity().getSupportFragmentManager());
        ClintMyOrderFragment clintPendingOrderFragment= new ClintMyOrderFragment();
        ClintMyOrderFragment clintCurrentOrderFragment= new ClintMyOrderFragment();
        ClintMyOrderFragment clintCompletedOrderFragment= new ClintMyOrderFragment();

        clintPendingOrderFragment.type = "pending";
        clintCurrentOrderFragment.type = "current";
        clintCompletedOrderFragment.type = "completed";

        viewPagerWithFragmentAdapter.addPager(clintPendingOrderFragment, "طلبات جديدة");
        viewPagerWithFragmentAdapter.addPager(clintCurrentOrderFragment, "طلبات حالية");
        viewPagerWithFragmentAdapter.addPager(clintCompletedOrderFragment, "طلبات سابقة");
        clintOrderFragmentVpViewpager.setAdapter(viewPagerWithFragmentAdapter);
        clintOrderFragmentTlTabs.setupWithViewPager(clintOrderFragmentVpViewpager);
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
