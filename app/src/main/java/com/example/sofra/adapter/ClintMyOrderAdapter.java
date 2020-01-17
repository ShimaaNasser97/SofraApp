package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.client_confirm_order.ClientConfirmOrder;
import com.example.sofra.data.model.client_my_orders.ClientMyOrdersData;

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

public class ClintMyOrderAdapter extends RecyclerView.Adapter<ClintMyOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<ClientMyOrdersData> clientMyOrdersDataList = new ArrayList<>();

    public ClintMyOrderAdapter(Context context, Activity activity, List<ClientMyOrdersData> clientMyOrdersDataList) {
        this.context = context;
        this.activity = activity;
        this.clientMyOrdersDataList = clientMyOrdersDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clint_my_order,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.ClintMyOrderTvName.setText(clientMyOrdersDataList.get(position).getRestaurant().getName());
        holder.ClintMyOrderTvAdress.setText(clientMyOrdersDataList.get(position).getAddress());
        holder.clintMyOrderFragmentTvTotal.setText(clientMyOrdersDataList.get(position).getTotal());
        //holder.ClintMyOrderTvOrderNum.setText(clientMyOrdersDataList.get(position).);
        onLoadImageFromUrl(holder.ClintMyOrderCivPhoto, clientMyOrdersDataList.get(position).getRestaurant().getPhotoUrl(), context);
        if (clientMyOrdersDataList.get(position).getState().equals("pending")){
            holder.ClintMyOrderBtnButten.setBackgroundResource(R.drawable.btn2_shape);
            //holder.ClintMyOrderBtnButten.setText(clientMyOrdersDataList.get(position).getItems().get(position).getPivot().);
        }else if(clientMyOrdersDataList.get(position).getState().equals("current")){
            holder.ClintMyOrderBtnButten.setBackgroundResource(R.drawable.btn3_shape);
        }
        else
            holder.ClintMyOrderBtnButten.setBackgroundResource(R.drawable.btn3_shape);
    }

    private void setAction(ViewHolder holder, final int position) {
    if(clientMyOrdersDataList.get(position).getState().equals("current")){
        holder.ClintMyOrderBtnButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClient().getClientConfirmOrder(String.valueOf(clientMyOrdersDataList.get(position).getId()), SharedPreferencesManger.loadClientData(activity).getApiToken()).enqueue(new Callback<ClientConfirmOrder>() {
                    @Override
                    public void onResponse(Call<ClientConfirmOrder> call, Response<ClientConfirmOrder> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(context,response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }}

                    @Override
                    public void onFailure(Call<ClientConfirmOrder> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    }
    @Override
    public int getItemCount() {
        return clientMyOrdersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.clint_my_order_fragment_civ_photo)
        CircleImageView ClintMyOrderCivPhoto;
        @BindView(R.id.clint_my_order_fragment_tv_name)
        TextView ClintMyOrderTvName;
        @BindView(R.id.clint_my_order_fragment_tv_order_num)
        TextView ClintMyOrderTvOrderNum;
        @BindView(R.id.clint_my_order_fragment_tv_adress)
        TextView ClintMyOrderTvAdress;
        @BindView(R.id.clint_my_order_fragment_tv_total)
        TextView clintMyOrderFragmentTvTotal;
        @BindView(R.id.clint_my_order_fragment_btn_butten)
        Button ClintMyOrderBtnButten;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
