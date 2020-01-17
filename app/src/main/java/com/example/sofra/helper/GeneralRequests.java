package com.example.sofra.helper;


import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.generalResponce.GeneralResponce;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyOrders;
import com.example.sofra.data.model.restaurant_reject_order.RestaurantRejectOrder;
import com.example.sofra.veiw.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;

public class GeneralRequests {
    public BaseActivity activity;
    public static List<String> names = new ArrayList<>();
    List<Integer> ids = new ArrayList<>();

    public static void getSpinnerData(Call<GeneralResponce> method, final Spinner spinner
            , final SpinnerAdapter spinnerAdapter, final String hint) {

        method.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {
                try {
                    // spinner.setVisibility(View.VISIBLE);
                    if (response.body().getStatus() == 1) {
                        spinnerAdapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(spinnerAdapter);
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });
    }


    public static void getSpinnerData(final Call<GeneralResponce> method, final Spinner spinner, final SpinnerAdapter spinnerAdapter, final String hint, final AdapterView.OnItemSelectedListener listener) {
        method.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {
                try {
                    if (response.body().getStatus()==1) {
                        spinnerAdapter.setData(response.body().getData(),hint);
                        spinner.setAdapter(spinnerAdapter);
                        spinner.setOnItemSelectedListener(listener);
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });
    }

    public static void getSpinnerData(final Call<GeneralResponce> method, final Spinner spinner
            , final SpinnerAdapter adapter, final String hint,  final AdapterView.OnItemSelectedListener listener,final int selectedId) {

        method.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {
                try {

                    if (response.body().getStatus() == 1) {

                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);

                        int pos = 0;
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getId() == selectedId) {
                                pos = i +1;
                                break;
                            }
                        }
                        spinner.setSelection(pos);
                        spinner.setOnItemSelectedListener(listener);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });
    }


    public static void getSpinnerData(final Call<GeneralResponce> method, final Spinner spinner
            , final SpinnerAdapter adapter, final String hint,final int selectedId) {

        method.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {
                try {

                    if (response.body().getStatus() == 1) {

                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);

                        int pos = 0;
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getId() == selectedId) {
                                pos = i +1;
                                break;
                            }
                        }
                        spinner.setSelection(pos);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });
    }



}
