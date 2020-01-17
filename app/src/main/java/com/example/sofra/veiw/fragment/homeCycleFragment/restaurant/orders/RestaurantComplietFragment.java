package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.orders;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantCompleteAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyOrders;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyordersData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.veiw.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class RestaurantComplietFragment extends BaseFragment {

    public String type;
    @BindView(R.id.restaurant_compliet_fragment_rv_my_order)
    RecyclerView restaurantComplietFragmentRvMyOrder;
    Unbinder unbinder;
    private OnEndLess onEndLess;
    private int maxPage=1;
    private RestaurantCompleteAdapter reataurantCompleteAdapter;
    private List<RestaurantMyordersData> restaurantCompleteList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_compliet, container, false);
        unbinder = ButterKnife.bind(this, view);
        init(1);
        return view;
    }
    private void init(int page) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        restaurantComplietFragmentRvMyOrder.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if(maxPage != 0){
                    getRestaurantMyOrder(current_page);
                }
                if (current_page <= maxPage){
                    if (maxPage != 0 && current_page != 1){
                        previous_page=current_page;
                        getRestaurantMyOrder(current_page);
                    }else {
                        onEndLess.current_page=previous_page;
                    }
                }else {
                    onEndLess.current_page=previous_page;
                }

            } };
        restaurantComplietFragmentRvMyOrder.addOnScrollListener(onEndLess);
        reataurantCompleteAdapter =new RestaurantCompleteAdapter(getActivity(),getActivity(),restaurantCompleteList);
        restaurantComplietFragmentRvMyOrder.setAdapter(reataurantCompleteAdapter);
        getRestaurantMyOrder(page);
    }

    private void getRestaurantMyOrder(int page) {
        getClient().getRestaurantMyOrders(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken(),type,page).enqueue(new Callback<RestaurantMyOrders>() {
            @Override
            public void onResponse(Call<RestaurantMyOrders> call, Response<RestaurantMyOrders> response) {
                try {
                    if (response.body().getStatus()==1) {
                        maxPage=response.body().getData().getLastPage();
                        restaurantCompleteList.addAll(response.body().getData().getData());
                        reataurantCompleteAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyOrders> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
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
}
