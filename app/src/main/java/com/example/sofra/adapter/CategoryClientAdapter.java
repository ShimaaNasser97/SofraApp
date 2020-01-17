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
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategoriesData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.MinuFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.MyItemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class

CategoryClientAdapter extends RecyclerView.Adapter<CategoryClientAdapter.ViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<RestaurantMyCategoriesData> restaurantMyCategoriesDataList = new ArrayList<>();
    private MinuFragment minuFragment;
    public CategoryClientAdapter(Context context, BaseActivity activity, List<RestaurantMyCategoriesData> restaurantMyCategoriesDataList , MinuFragment minuFragment) {
        this.context = context;
        this.activity = activity;
        this.restaurantMyCategoriesDataList = restaurantMyCategoriesDataList;
        this.minuFragment = minuFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.minuFragmentTvText.setText(restaurantMyCategoriesDataList.get(position).getName());
        onLoadImageFromUrl(holder.minuFragmentIvPhoto, restaurantMyCategoriesDataList.get(position).getPhotoUrl(), context);
    }

    private void setAction(ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minuFragment.category=restaurantMyCategoriesDataList.get(position).getId();
                minuFragment.getItems(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantMyCategoriesDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.minu_fragment_iv_photo)
        ImageView minuFragmentIvPhoto;
        @BindView(R.id.minu_fragment_tv_text)
        TextView minuFragmentTvText;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
