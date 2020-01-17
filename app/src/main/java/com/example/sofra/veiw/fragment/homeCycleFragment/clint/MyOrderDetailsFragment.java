package com.example.sofra.veiw.fragment.homeCycleFragment.clint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.adapter.ShopingAdapter;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.roomDatabase.ShopingDataBase;
import com.example.sofra.roomDatabase.model.Shoping;
import com.example.sofra.veiw.activity.UseaCycleActivity;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.RestaurantDetailsFragment;
import com.example.sofra.veiw.fragment.userCycleFragment.LoginFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.lang.Integer.parseInt;


public class MyOrderDetailsFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.my_order_details_fragment_rv_details)
    RecyclerView myOrderDetailsFragmentRvDetails;
    @BindView(R.id.my_order_details_fragment_tv_total)
    TextView myOrderDetailsFragmentTvTotal;
    public static List<Shoping> list;
    private LinearLayoutManager linearLayoutManager;
    private ShopingAdapter shopingAdapter;
    public static double total;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        myOrderDetailsFragmentRvDetails.setLayoutManager(linearLayoutManager);
        shopingAdapter = new ShopingAdapter(getActivity(), getActivity(), null);
        myOrderDetailsFragmentRvDetails.setAdapter(shopingAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        list = ShopingDataBase.getInstance(getActivity()).shopingDao().getAllShoping();
        shopingAdapter.changData(list);

        total = 0.0;
        for (int i = 0; i < list.size(); i++) {







            total = total + Double.parseDouble(list.get(i).getQuantity()) * Double.parseDouble(list.get(i).getPrice());
        }
        myOrderDetailsFragmentTvTotal.setText(String.valueOf(total));
    }

    @Override
    public void onBack() {
        super.onBack();
        shopingAdapter.clearData(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_order_details_fragment_btn_confirm, R.id.my_order_details_fragment_btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_order_details_fragment_btn_confirm:
                /*Intent intent=new Intent(getActivity(), UseaCycleActivity.class);
                startActivity(intent);*/
                shopingAdapter.clearData(list);
                NewOrderFragment newOrderFragment = new NewOrderFragment();
                HelperMethod.replace(newOrderFragment, getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                break;
            case R.id.my_order_details_fragment_btn_add:
                RestaurantDetailsFragment restaurantDetailsFragment = new RestaurantDetailsFragment();
                HelperMethod.replace(restaurantDetailsFragment, getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
                break;
        }
    }
}
