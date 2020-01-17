package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.MyRestaurantItemAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategoriesData;
import com.example.sofra.data.model.restaurant_my_items2.RestaurantMyItems;
import com.example.sofra.data.model.restaurant_my_items2.RestaurantMyItemsData;
import com.example.sofra.helper.HelperMethod;
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

import static android.support.constraint.Constraints.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.ADD;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_ITEM;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_DATA;


public class MyItemFragment extends BaseFragment {

    @BindView(R.id.home2_fragment_rv_items)
    RecyclerView home2FragmentRvItems;
    Unbinder unbinder;
    public RestaurantMyCategoriesData myCategory;
    private MyRestaurantItemAdapter restaurantItemAdapter;
    private OnEndLess onEndLess;
    private Integer maxPage = 0;
    private List<RestaurantMyItemsData> restaurantItemList = new ArrayList<>();
    private int previous_page;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        home2FragmentRvItems.setLayoutManager(linearLayoutManager);
       /* onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        previous_page = current_page;
                        getRestaurantMyItems(current_page);
                    } else {
                        onEndLess.current_page = previous_page;
                    }
                } else {
                    onEndLess.current_page = previous_page;
                }

            }
        };*/
        restaurantItemAdapter = new MyRestaurantItemAdapter(getActivity(), baseActivity, restaurantItemList);
        home2FragmentRvItems.setAdapter(restaurantItemAdapter);
        getRestaurantMyItems(1);
//        restaurantItemList = new ArrayList<>();
//        restaurantItemAdapter = new MyRestaurantItemAdapter(getActivity(), baseActivity, restaurantItemList);
//        previous_page = 1;
//        onEndLess.current_page = 1;
//        onEndLess.previousTotal = 0;
    }

    private void getRestaurantMyItems(int page) {
        getClient().getRestaurantMyItems(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken(), myCategory.getId(), page).enqueue(new Callback<RestaurantMyItems>() {
            @Override
            public void onResponse(Call<RestaurantMyItems> call, Response<RestaurantMyItems> response) {
                try {
                    if (response.body().getStatus() == 1) {
                       // maxPage = response.body().getData().getLastPage();
                        restaurantItemList.addAll(response.body().getData().getData());
                        restaurantItemAdapter.notifyDataSetChanged();
                    }
                    Log.d(TAG, "onResponse:");
                } catch (Exception e) {
                    Log.d(TAG, "onResponse:");
                    Toast.makeText(baseActivity, "exeption" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyItems> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                Toast.makeText(baseActivity, "onFailure" + t.getMessage(), Toast.LENGTH_LONG).show();
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

    @OnClick(R.id.my_item_fragment_fbtn_add)
    public void onViewClicked() {
        SharedPreferencesManger.SaveData(getActivity(), RESTAURANT_ITEM, ADD);
        NewItemFragment newItemFragment = new NewItemFragment();
        HelperMethod.replace(newItemFragment, getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
    }
}
