package com.example.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurant_notifications.RestaurantNotificationsData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.orders.ResraurantPendingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantNotificationAdapter extends RecyclerView.Adapter<RestaurantNotificationAdapter.ViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<RestaurantNotificationsData> restaurantNotificationsDataList = new ArrayList<>();

    public RestaurantNotificationAdapter(Context context, BaseActivity activity, List<RestaurantNotificationsData> restaurantNotificationsDataList) {
        this.context = context;
        this.activity = activity;
        this.restaurantNotificationsDataList = restaurantNotificationsDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.notificationFragmentTvText.setText(restaurantNotificationsDataList.get(position).getTitle());
    }

    private void setAction(ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResraurantPendingFragment resraurantMyOrderFragment=new ResraurantPendingFragment();
                HelperMethod.replace(resraurantMyOrderFragment,activity.getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
            }
        });
        }

    @Override
    public int getItemCount() {
        return restaurantNotificationsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_fragment_iv_image)
        ImageView notificationFragmentIvImage;
        @BindView(R.id.notification_fragment_tv_text)
        TextView notificationFragmentTvText;
        @BindView(R.id.notification_fragment_tv_time)
        TextView notificationFragmentTvTime;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
