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
import com.example.sofra.data.model.restaurant_delete_offer.RestaurantDeleteOffer;
import com.example.sofra.data.model.restaurant_my_offers.RestaurantMyOffersData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.more.RestaurantNewOfferFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.ADD;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_ITEM;
import static com.example.sofra.data.local.SharedPreferencesManger.UPDATE;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class RestaurantMyaofferAdapter extends RecyclerView.Adapter<RestaurantMyaofferAdapter.ViewHolder> {
    private Context context;
    private BaseActivity activity;
    private List<RestaurantMyOffersData> restaurantMyOffersDataList = new ArrayList<>();

    public RestaurantMyaofferAdapter(Context context, BaseActivity activity, List<RestaurantMyOffersData> restaurantMyOffersDataList) {
        this.context = context;
        this.activity = activity;
        this.restaurantMyOffersDataList = restaurantMyOffersDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_offer,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        onLoadImageFromUrl(holder.restaurantOffersFragmentCivPhoto,restaurantMyOffersDataList.get(position).getPhotoUrl(),context);
        holder.restaurantOffersFragmentTvOffer.setText(restaurantMyOffersDataList.get(position).getName());
    }

    private void setAction(ViewHolder holder, final int position) {
        holder.restaurantOffersFragmentBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManger.SaveData(activity,RESTAURANT_ITEM ,UPDATE);
                RestaurantNewOfferFragment restaurantNewOfferFragment=new RestaurantNewOfferFragment();
                restaurantNewOfferFragment.myOfferDataList=restaurantMyOffersDataList.get(position);
                HelperMethod.replace(restaurantNewOfferFragment,activity.getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
            }
        });

        holder.restaurantOffersFragmentBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClient().getRestaurantDeleteOffer(restaurantMyOffersDataList.get(position).getId().toString(),SharedPreferencesManger.loadRestaurantData(activity).getApiToken()).enqueue(new Callback<RestaurantDeleteOffer>() {
                    @Override
                    public void onResponse(Call<RestaurantDeleteOffer> call, Response<RestaurantDeleteOffer> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantDeleteOffer> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return restaurantMyOffersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_offers_fragment_btn_edit)
        Button restaurantOffersFragmentBtnEdit;
        @BindView(R.id.restaurant_offers_fragment_btn_delete)
        Button restaurantOffersFragmentBtnDelete;
        @BindView(R.id.restaurant_offers_fragment_tv_offer)
        TextView restaurantOffersFragmentTvOffer;
        @BindView(R.id.restaurant_offers_fragment_civ_photo)
        CircleImageView restaurantOffersFragmentCivPhoto;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
