package com.example.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurants.RestauranrsData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.MinuFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.RestaurantDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    private Context context;
    private BaseActivity baseActivity;
    private List<RestauranrsData> restaurantDataList = new ArrayList<>();

    public RestaurantsAdapter(Context context, BaseActivity baseActivity, List<RestauranrsData> restaurantDataList) {
        this.context = context;
        this.baseActivity = baseActivity;
        this.restaurantDataList = restaurantDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rastaurant,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        onLoadImageFromUrl(holder.restaurantsFragmentCivPhoto, restaurantDataList.get(position).getPhotoUrl(), context);
        holder.restaurantsFragmentTvName.setText(restaurantDataList.get(position).getName());
        holder.restaurantsFragmentTvMinimumCharger.setText(restaurantDataList.get(position).getMinimumCharger());
        holder.restaurantsFragmentTvDeliveryCost.setText(restaurantDataList.get(position).getDeliveryCost());
        holder.restaurantsFragmentTvAvailability.setText(restaurantDataList.get(position).getAvailability());
        holder.restaurantsFragmentRbRatingbar.setRating(restaurantDataList.get(position).getRate());
    }

    private void setAction(ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MinuFragment minuFragment = new MinuFragment();
                RestaurantDetailsFragment restaurantDetailsFragment = new RestaurantDetailsFragment();
                RestaurantDetailsFragment.restaurant = restaurantDataList.get(position);

                HelperMethod.replace(restaurantDetailsFragment, baseActivity.getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurants_fragment_tv_name)
        TextView restaurantsFragmentTvName;
        @BindView(R.id.restaurants_fragment_rb_ratingbar)
        RatingBar restaurantsFragmentRbRatingbar;
        @BindView(R.id.restaurants_fragment_tv_minimum_charger)
        TextView restaurantsFragmentTvMinimumCharger;
        @BindView(R.id.restaurants_fragment_tv_delivery_cost)
        TextView restaurantsFragmentTvDeliveryCost;
        @BindView(R.id.restaurants_fragment_tv_circil)
        TextView restaurantsFragmentTvCircil;
        @BindView(R.id.restaurants_fragment_tv_availability)
        TextView restaurantsFragmentTvAvailability;
        @BindView(R.id.restaurants_fragment_civ_photo)
        CircleImageView restaurantsFragmentCivPhoto;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
