package com.example.sofra.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.sofra.data.model.restaurant_accept_order.RestaurantAcceptOrder;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyordersData;
import com.example.sofra.data.model.restaurant_reject_order.RestaurantRejectOrder;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.orders.RestaurantPendingDetailsFragment;

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

public class RestaurantPendingAdapter extends RecyclerView.Adapter<RestaurantPendingAdapter.ViewHolder> {
    private Context context;
    private BaseActivity activity;
   private List<RestaurantMyordersData> restaurantMyordersDataList = new ArrayList<>();

    public RestaurantPendingAdapter(Context context, BaseActivity activity, List<RestaurantMyordersData> restaurantMyordersDataList) {
        this.context = context;
        this.activity = activity;
        this.restaurantMyordersDataList = restaurantMyordersDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_my_order,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.RestaurantMyOrderTvName.setText(restaurantMyordersDataList.get(position).getClient().getName());
        holder.RestaurantMyOrderTvAdress.setText(restaurantMyordersDataList.get(position).getAddress());
        // holder.ClintMyOrderTvOrderNum.setText(clientMyOrdersDataList.g);
        onLoadImageFromUrl(holder.RestaurantMyOrderCivPhoto,restaurantMyordersDataList.get(position).getRestaurant().getPhotoUrl(),context);

    }
    private void setAction(ViewHolder holder, final int position) {

        holder.RestaurantMyOrderBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+restaurantMyordersDataList.get(position).getClient().getPhone()));
                Intent i=Intent.createChooser(intent,"make call with");
                activity.startActivity(i);
            }
        });

        holder.RestaurantMyOrderBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClient().getRestaurantAcceptOrder(SharedPreferencesManger.loadRestaurantData(activity).getApiToken(),
                        String.valueOf(restaurantMyordersDataList.get(position).getId())).enqueue(new Callback<RestaurantAcceptOrder>() {
                    @Override
                    public void onResponse(Call<RestaurantAcceptOrder> call, Response<RestaurantAcceptOrder> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantAcceptOrder> call, Throwable t) {
                        try {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        holder.RestaurantMyOrderBtnCancel.setOnClickListener(new View.OnClickListener() {
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

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantPendingDetailsFragment restaurantPendingDetailsFragment = new RestaurantPendingDetailsFragment();
                restaurantPendingDetailsFragment.pendingData=restaurantMyordersDataList.get(position);
                HelperMethod.replace(restaurantPendingDetailsFragment,activity.getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
            }
        });
    }
    @Override
    public int getItemCount() {
        return restaurantMyordersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_my_order_fragment_civ__photo)
        CircleImageView RestaurantMyOrderCivPhoto;
        @BindView(R.id.restaurant_my_order_fragment_tv_name)
        TextView RestaurantMyOrderTvName;
        @BindView(R.id.restaurant_my_order_fragment_tv_order_num)
        TextView RestaurantMyOrderTvOrderNum;
        @BindView(R.id.restaurant_my_order_fragment_tv_adress)
        TextView RestaurantMyOrderTvAdress;
        @BindView(R.id.restaurant_my_order_fragment_btn_cancel)
        Button RestaurantMyOrderBtnCancel;
        @BindView(R.id.restaurant_my_order_fragment_btn_confirm)
        Button RestaurantMyOrderBtnConfirm;
        @BindView(R.id.restaurant_my_order_fragment_btn_call)
        Button RestaurantMyOrderBtnCall;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
