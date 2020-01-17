package com.example.sofra.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyordersData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class RestaurantCompleteAdapter extends RecyclerView.Adapter<RestaurantCompleteAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<RestaurantMyordersData> restaurantMyordersData = new ArrayList<>();

    public RestaurantCompleteAdapter(Context context, Activity activity, List<RestaurantMyordersData> restaurantMyordersData) {
        this.context = context;
        this.activity = activity;
        this.restaurantMyordersData = restaurantMyordersData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_complete,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        if (restaurantMyordersData.get(position).getState().equals("rejected")) {
            //  holder.RestaurantComplietBtnConfirm.setVisibility(View.GONE);
            holder.RestaurantComplietBtnConfirm.setBackgroundResource(R.drawable.btn_daialog_shape);
            holder.RestaurantComplietBtnConfirm.setText(R.string.orderIsReject);
        } else {
            holder.RestaurantComplietBtnConfirm.setBackgroundResource(R.drawable.btn3_shape);
            holder.RestaurantComplietBtnConfirm.setText(R.string.deleverd);
        }

        holder.RestaurantComplietTvName.setText(restaurantMyordersData.get(position).getClient().getName());
        holder.RestaurantComplietTvAdress.setText(restaurantMyordersData.get(position).getAddress());

        // holder.ClintMyOrderTvOrderNum.setText(clientMyOrdersDataList.g);
        onLoadImageFromUrl(holder.RestaurantComplietCivPhoto, restaurantMyordersData.get(position).getRestaurant().getPhotoUrl(), context);

    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return restaurantMyordersData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_compliet_fragment_civ__photo)
        CircleImageView RestaurantComplietCivPhoto;
        @BindView(R.id.restaurant_compliet_fragment_tv_name)
        TextView RestaurantComplietTvName;
        @BindView(R.id.restaurant_compliet_fragment_tv_order_num)
        TextView RestaurantComplietTvOrderNum;
        @BindView(R.id.restaurant_compliet_fragment_tv_adress)
        TextView RestaurantComplietTvAdress;
        @BindView(R.id.restaurant_compliet_fragment_btn_confirm)
        Button RestaurantComplietBtnConfirm;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
