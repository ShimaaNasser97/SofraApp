package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.more;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantMyaofferAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_my_offers.RestaurantMyOffers;
import com.example.sofra.data.model.restaurant_my_offers.RestaurantMyOffersData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.RestaurantsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.ADD;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_ITEM;
import static com.example.sofra.data.local.SharedPreferencesManger.saveUserData;


public class RestaurantOffersFragment extends BaseFragment {

    @BindView(R.id.restaurant_offers_fragment_rv_offers)
    RecyclerView restaurantOffersFragmentRvOffers;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private RestaurantMyaofferAdapter restaurantMyaofferAdapter;
    private List<RestaurantMyOffersData> offresDataList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_offers, container, false);
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        restaurantOffersFragmentRvOffers.setLayoutManager(linearLayoutManager);

        restaurantMyaofferAdapter=new RestaurantMyaofferAdapter(getActivity(), baseActivity,offresDataList);
        restaurantOffersFragmentRvOffers.setAdapter(restaurantMyaofferAdapter);
        getRestaurantMyaoffer(1);
        return view;
    }

    private void getRestaurantMyaoffer(int page) {
        getClient().getRestaurantMyOffers(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken(),page).enqueue(new Callback<RestaurantMyOffers>() {
            @Override
            public void onResponse(Call<RestaurantMyOffers> call, Response<RestaurantMyOffers> response) {
                try {
                    if (response.body().getStatus()==1) {
                        offresDataList.addAll(response.body().getData().getData());
                        restaurantMyaofferAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onFailure(Call<RestaurantMyOffers> call, Throwable t) {

            }
        });
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

    @OnClick(R.id.restaurant_offers_fragment_btn_add)
    public void onViewClicked() {
        SharedPreferencesManger.SaveData(getActivity(),RESTAURANT_ITEM ,ADD);
        RestaurantNewOfferFragment restaurantNewOfferFragment=new RestaurantNewOfferFragment();
        HelperMethod.replace(restaurantNewOfferFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
    }
}
