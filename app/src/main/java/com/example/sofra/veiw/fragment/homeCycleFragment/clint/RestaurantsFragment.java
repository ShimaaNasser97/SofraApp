package com.example.sofra.veiw.fragment.homeCycleFragment.clint;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantsAdapter;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.model.restaurants.RestauranrsData;
import com.example.sofra.data.model.restaurants.Restaurants;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.veiw.fragment.BaseFragment;

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
import static com.example.sofra.helper.GeneralRequests.getSpinnerData;


public class RestaurantsFragment extends BaseFragment {

    @BindView(R.id.restaurants_fragment_et_filter)
    EditText restaurantsFragmentEtFilter;
    @BindView(R.id.restaurants_fragment_sp_city)
    Spinner restaurantsFragmentSpCity;
    @BindView(R.id.restaurants_fragment_rv_restaurants)
    RecyclerView restaurantsFragmentRvRestaurants;
    Unbinder unbinder;
    private List<RestauranrsData> rastaurantsList = new ArrayList<>();
    private RestaurantsAdapter restaurantsAdapter;
    private OnEndLess onEndLess;
    private Integer maxPage=1;
    private boolean filter = false;
    private SpinnerAdapter cityAdapter;
    private String etFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);
        unbinder = ButterKnife.bind(this, view);
        cityAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getClient().getCitesNoPaginated(), restaurantsFragmentSpCity, cityAdapter, getString(R.string.city));
        init();
        return view;
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantsFragmentRvRestaurants.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (filter) {
                            onFilter(current_page);
                        } else {
                            getRestaurants(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }else {
                    onEndLess.current_page = previous_page;
                }
            }
        };
        restaurantsFragmentRvRestaurants.addOnScrollListener(onEndLess);
        restaurantsAdapter = new RestaurantsAdapter(getActivity(), baseActivity, rastaurantsList);
        restaurantsFragmentRvRestaurants.setAdapter(restaurantsAdapter);
        getRestaurants(1);
    }

    private void getRestaurants(int page) {
        Call<Restaurants> call = getClient().getRestaurants(page);
        startCall(call, page);

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

    @OnClick(R.id.restaurants_fragment_iv_filter)
    public void onViewClicked() {
          etFilter=restaurantsFragmentEtFilter.getText().toString();
        filter=true;
        onFilter(1);
    }
    private void onFilter(int current_page) {
        filter = true;

        onEndLess.current_page = 1;
        onEndLess.previousTotal = 0;
        onEndLess.previous_page = 1;
        rastaurantsList = new ArrayList<>();
        restaurantsAdapter = new RestaurantsAdapter(getActivity(), baseActivity, rastaurantsList);
        restaurantsFragmentRvRestaurants.setAdapter(restaurantsAdapter);
        Call<Restaurants> call = getClient().getRestaurants(etFilter, cityAdapter.selectedId);
        startCall(call, 1);

    }
    private void startCall(Call<Restaurants> call, int page) {
        call.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        rastaurantsList.addAll(response.body().getData().getData());
                        restaurantsAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
