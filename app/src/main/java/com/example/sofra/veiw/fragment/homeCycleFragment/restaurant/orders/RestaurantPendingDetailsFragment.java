package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyordersData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class RestaurantPendingDetailsFragment extends BaseFragment {

    public RestaurantMyordersData pendingData;
    @BindView(R.id.restaurant_pending_details_fragment_civ_photo)
    CircleImageView restaurantPendingDetailsFragmentCivPhoto;
    @BindView(R.id.restaurant_pending_details_fragment_tv_name)
    TextView restaurantPendingDetailsFragmentTvName;
    @BindView(R.id.restaurant_pending_details_fragment_tv_date)
    TextView restaurantPendingDetailsFragmentTvDate;
    @BindView(R.id.restaurant_pending_details_fragment_tv_adress)
    TextView restaurantPendingDetailsFragmentTvAdress;
    @BindView(R.id.restaurant_pending_details_fragment_tv_price)
    TextView restaurantPendingDetailsFragmentTvPrice;
    @BindView(R.id.restaurant_pending_details_fragment_tv_cost)
    TextView restaurantPendingDetailsFragmentTvCost;
    @BindView(R.id.restaurant_pending_details_fragment_tv_total)
    TextView restaurantPendingDetailsFragmentTvTotal;
    @BindView(R.id.restaurant_pending_details_fragment_tv_payment)
    TextView restaurantPendingDetailsFragmentTvPayment;

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
        View view = inflater.inflate(R.layout.fragment_restaurant_pending_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        HelperMethod.onLoadImageFromUrl(restaurantPendingDetailsFragmentCivPhoto,pendingData.getRestaurant().getPhotoUrl(),getContext());
        restaurantPendingDetailsFragmentTvAdress.setText(pendingData.getAddress());
        restaurantPendingDetailsFragmentTvPrice.setText(pendingData.getTotal());
        restaurantPendingDetailsFragmentTvCost.setText(pendingData.getDeliveryCost());
        if (pendingData.getPaymentMethodId()=="1") {
            restaurantPendingDetailsFragmentTvPayment.setText("كاش");
        }else  restaurantPendingDetailsFragmentTvPayment.setText("اونلاين");

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
