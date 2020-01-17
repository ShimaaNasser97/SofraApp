package com.example.sofra.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_confirm_order.RestaurantConfirmOrder;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyOrders;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyordersData;
import com.example.sofra.data.model.restaurant_reject_order.RestaurantRejectOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class RestaurantCurrentAdapter extends RecyclerView.Adapter<RestaurantCurrentAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<RestaurantMyordersData> restaurantMyordersDataList = new ArrayList<>();

    public RestaurantCurrentAdapter(Context context, Activity activity, List<RestaurantMyordersData> restaurantMyordersDataList) {
        this.context = context;
        this.activity = activity;
        this.restaurantMyordersDataList = restaurantMyordersDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_current,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.RestaurantCurrentTvName.setText(restaurantMyordersDataList.get(position).getClient().getName());
        holder.RestaurantCurrentTvAdress.setText(restaurantMyordersDataList.get(position).getAddress());
        // holder.ClintMyOrderTvOrderNum.setText(clientMyOrdersDataList.g);
        onLoadImageFromUrl(holder.RestaurantCurrentCivPhoto,restaurantMyordersDataList.get(position).getRestaurant().getPhotoUrl(),context);

    }

    private void setAction(ViewHolder holder, final int position) {
        holder.RestaurantCurrentBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClient().getRestaurantConfirmOrder(SharedPreferencesManger.loadRestaurantData(activity).getApiToken(),
                        String.valueOf(restaurantMyordersDataList.get(position).getId())).enqueue(new Callback<RestaurantConfirmOrder>() {
                    @Override
                    public void onResponse(Call<RestaurantConfirmOrder> call, Response<RestaurantConfirmOrder> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<RestaurantConfirmOrder> call, Throwable t) {
                        try {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        holder.RestaurantCurrentBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(activity);
                dialog.setContentView(R.layout.reject_dialoge);

                EditText editText = dialog.findViewById(R.id.reject_dialige_et_content);
                Button cancel=dialog.findViewById(R.id.reject_dialige_btn_cancel);
                final String content=editText.getText().toString();
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getClient().getRestaurantRejectOrder(SharedPreferencesManger.loadRestaurantData(activity).getApiToken(),
                                String.valueOf(restaurantMyordersDataList.get(position).getId()),content).enqueue(new Callback<RestaurantRejectOrder>() {
                            @Override
                            public void onResponse(Call<RestaurantRejectOrder> call, Response<RestaurantRejectOrder> response) {
                                try {
                                    if (response.body().getStatus()==1) {
                                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }catch (Exception e){
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RestaurantRejectOrder> call, Throwable t) {
                                try {
                                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantMyordersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_current_fragment_civ__photo)
        CircleImageView RestaurantCurrentCivPhoto;
        @BindView(R.id.restaurant_current_fragment_tv_name)
        TextView RestaurantCurrentTvName;
        @BindView(R.id.restaurant_current_fragment_tv_order_num)
        TextView RestaurantCurrentTvOrderNum;
        @BindView(R.id.restaurant_current_fragment_tv_adress)
        TextView RestaurantCurrentTvAdress;
        @BindView(R.id.restaurant_current_fragment_btn_cancel)
        Button RestaurantCurrentBtnCancel;
        @BindView(R.id.restaurant_current_fragment_btn_confirm)
        Button RestaurantCurrentBtnConfirm;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
