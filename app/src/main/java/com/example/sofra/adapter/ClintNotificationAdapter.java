package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.client_notifications.ClientNotificationsData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.MyOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClintNotificationAdapter extends RecyclerView.Adapter<ClintNotificationAdapter.ViewHolder> {
    private Context context;
    private BaseActivity activity;
    private List<ClientNotificationsData> clientNotificationsDataList = new ArrayList<>();

    public ClintNotificationAdapter(Context context, BaseActivity activity, List<ClientNotificationsData> clientNotificationsDataList) {
        this.context = context;
        this.activity = activity;
        this.clientNotificationsDataList = clientNotificationsDataList;
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
        holder.notificationFragmentTvText.setText(clientNotificationsDataList.get(position).getTitle());
    }

    private void setAction(ViewHolder holder, int position) {
       holder.view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MyOrderFragment myOrderFragment=new MyOrderFragment();
               HelperMethod.replace(myOrderFragment,activity.getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);

           }
       });
    }

    @Override
    public int getItemCount() {
        return clientNotificationsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_fragment_iv_image)
        ImageView notificationFragmentIvImage;
        @BindView(R.id.notification_fragment_tv_text)
        TextView notificationFragmentTvText;
        @BindView(R.id.notification_fragment_iv_time)
        ImageView notificationFragmentIvTime;
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
