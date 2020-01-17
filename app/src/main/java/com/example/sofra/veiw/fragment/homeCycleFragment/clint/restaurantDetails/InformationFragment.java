package com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class InformationFragment extends BaseFragment {

    @BindView(R.id.information_fragment_tv_state)
    TextView informationFragmentTvState;
    @BindView(R.id.information_fragment_tv_city)
    TextView informationFragmentTvCity;
    @BindView(R.id.information_fragment_tv_region)
    TextView informationFragmentTvRegion;
    @BindView(R.id.information_fragment_tv_min)
    TextView informationFragmentTvMin;
    @BindView(R.id.information_fragment_tv_price)
    TextView informationFragmentTvPrice;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        unbinder = ButterKnife.bind(this, view);
       informationFragmentTvState.setText(RestaurantDetailsFragment.restaurant.getAvailability());
       informationFragmentTvCity.setText(RestaurantDetailsFragment.restaurant.getRegion().getCity().getName());
       informationFragmentTvRegion.setText(RestaurantDetailsFragment.restaurant.getRegion().getName());
       informationFragmentTvMin.setText(RestaurantDetailsFragment.restaurant.getMinimumCharger());
       informationFragmentTvPrice.setText(RestaurantDetailsFragment.restaurant.getDeliveryCost());
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
