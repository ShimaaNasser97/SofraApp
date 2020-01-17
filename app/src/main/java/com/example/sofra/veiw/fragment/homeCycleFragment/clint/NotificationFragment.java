package com.example.sofra.veiw.fragment.homeCycleFragment.clint;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.ClintNotificationAdapter;
import com.example.sofra.adapter.RestaurantNotificationAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.client_notifications.ClientNotifications;
import com.example.sofra.data.model.client_notifications.ClientNotificationsData;
import com.example.sofra.data.model.restaurant_notifications.RestaurantNotifications;
import com.example.sofra.data.model.restaurant_notifications.RestaurantNotificationsData;
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
import static com.example.sofra.data.local.SharedPreferencesManger.CLINT;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;


public class NotificationFragment extends BaseFragment {

    @BindView(R.id.notification_fragment_rv_notification)
    RecyclerView notificationFragmentRvNotification;
    Unbinder unbinder;
    private List<ClientNotificationsData> clientList=new ArrayList<>();
    private List<RestaurantNotificationsData> restaurantList=new ArrayList<>();
    private ClintNotificationAdapter clintNotificationAdapter;
    private RestaurantNotificationAdapter restaurantNotificationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Integer max=1;
    private OnEndLess onEndLess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        init(1);
        return view;
    }

    private void init(int page) {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        notificationFragmentRvNotification.setLayoutManager(linearLayoutManager);
        if (SharedPreferencesManger.LoadData(getActivity(),USER_TYPE).equals(CLINT)) {
            linearLayoutManager=new LinearLayoutManager(getActivity());
            notificationFragmentRvNotification.setLayoutManager(linearLayoutManager);
            onEndLess=new OnEndLess(linearLayoutManager,1) {
                 @Override
                 public void onLoadMore(int current_page) {
                     if (current_page <= max) {
                         if (max != 0 && current_page != 1) {
                             onEndLess.previous_page = current_page;
                             getClientNotifications(current_page);
                         } else {
                             onEndLess.current_page=onEndLess.previous_page;
                         }
                     }
                 }
             };
            clintNotificationAdapter=new ClintNotificationAdapter(getActivity(),baseActivity,clientList);
            notificationFragmentRvNotification.setAdapter(clintNotificationAdapter);
            getClientNotifications(page);
        }

        if (SharedPreferencesManger.LoadData(getActivity(),USER_TYPE).equals(RESTAURANT)) {
            linearLayoutManager=new LinearLayoutManager(getActivity());
            notificationFragmentRvNotification.setLayoutManager(linearLayoutManager);
            onEndLess=new OnEndLess(linearLayoutManager,1) {
                @Override
                public void onLoadMore(int current_page) {
                    if (current_page <= max) {
                        if (max != 0 && current_page != 1) {
                            onEndLess.previous_page = current_page;
                            getClientNotifications(current_page);
                        } else {
                            onEndLess.current_page=onEndLess.previous_page;
                        }
                    }
                }
            };
            restaurantNotificationAdapter=new RestaurantNotificationAdapter(getActivity(),baseActivity,restaurantList);
            notificationFragmentRvNotification.setAdapter(restaurantNotificationAdapter);
            getRestaurantNotifications(page);
        }
    }
    private void getClientNotifications(int page) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getClient().getClientNotifications(SharedPreferencesManger.loadClientData(getActivity()).getApiToken()).enqueue(new Callback<ClientNotifications>() {
                @Override
                public void onResponse(Call<ClientNotifications> call, Response<ClientNotifications> response) {
                    try {
                        if (response.body().getStatus()==1) {
                            max=response.body().getData().getLastPage();
                            clientList.addAll(response.body().getData().getData());
                            clintNotificationAdapter.notifyDataSetChanged();
                        }
                    }catch (Exception e){
                        Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClientNotifications> call, Throwable t) {
                    Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void getRestaurantNotifications(int page) {
        getClient().getRestauranNotifications(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken()).enqueue(new Callback<RestaurantNotifications>() {
            @Override
            public void onResponse(Call<RestaurantNotifications> call, Response<RestaurantNotifications> response) {
                try {
                    if (response.body().getStatus()==1) {
                        max=response.body().getData().getLastPage();
                        restaurantList.addAll(response.body().getData().getData());
                        restaurantNotificationAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantNotifications> call, Throwable t) {
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
