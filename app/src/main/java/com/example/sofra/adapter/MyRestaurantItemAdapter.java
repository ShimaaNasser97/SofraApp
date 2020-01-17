package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_delete_item.RestaurantDeleteItem;
import com.example.sofra.data.model.restaurant_my_items2.RestaurantMyItemsData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.NewItemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.ADD;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_ITEM;
import static com.example.sofra.data.local.SharedPreferencesManger.UPDATE;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class

MyRestaurantItemAdapter extends RecyclerView.Adapter<MyRestaurantItemAdapter.ViewHolder> {
    private Context context;
    private BaseActivity activity;
    private List<RestaurantMyItemsData> restaurantMyItemDataList = new ArrayList<>();

    public MyRestaurantItemAdapter(Context context, BaseActivity activity, List<RestaurantMyItemsData> restaurantMyItemDataList) {
        this.context = context;
        this.activity = activity;
        this.restaurantMyItemDataList = restaurantMyItemDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home2,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        onLoadImageFromUrl(holder.itemHome2IvImage, restaurantMyItemDataList.get(position).getPhotoUrl(), context);
        holder.itemHome2TvName.setText(restaurantMyItemDataList.get(position).getName());
        holder.itemHome2TvDescription.setText(restaurantMyItemDataList.get(position).getDescription());
        holder.itemHome2TvPrice.setText(restaurantMyItemDataList.get(position).getPrice());


    }

    private void setAction(ViewHolder holder, final int position) {
        holder.itemHome1BtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManger.SaveData(activity,RESTAURANT_ITEM,UPDATE);
                NewItemFragment newItemFragment=new NewItemFragment();
                NewItemFragment.myItem=restaurantMyItemDataList.get(position);
                HelperMethod.replace(newItemFragment,activity.getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
            }
        });
        holder.itemHome1BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClient().getRestaurantDeleteItem(String.valueOf(restaurantMyItemDataList.get(position).getId()),SharedPreferencesManger.loadRestaurantData(activity).getApiToken()).enqueue(new Callback<RestaurantDeleteItem>() {
                    @Override
                    public void onResponse(Call<RestaurantDeleteItem> call, Response<RestaurantDeleteItem> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                restaurantMyItemDataList.remove(position);
                            }
                        }catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantDeleteItem> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantMyItemDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home2_iv_image)
        ImageView itemHome2IvImage;
        @BindView(R.id.item_home2_tv_name)
        TextView itemHome2TvName;
        @BindView(R.id.item_home2_tv_description)
        TextView itemHome2TvDescription;
        @BindView(R.id.item_home2_tv_text)
        TextView itemHome2TvText;
        @BindView(R.id.item_home2_tv_price)
        TextView itemHome2TvPrice;
        @BindView(R.id.item_home1_btn_edit)
        Button itemHome1BtnEdit;
        @BindView(R.id.item_home1_btn_delete)
        Button itemHome1BtnDelete;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
