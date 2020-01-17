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
import com.example.sofra.data.model.restaurant_reviews.RestaurantReviews;
import com.example.sofra.data.model.restaurant_reviews.RestaurantReviewsData;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    @BindView(R.id.comment_fragmet_tx_name)
    TextView commentFragmetTxName;
    @BindView(R.id.comment_fragment_tv_comment)
    TextView commentFragmentTvComment;
    @BindView(R.id.comment_fragment_iv_photo)
    ImageView commentFragmentIvPhoto;
    private Context context;
    private Activity activity;
    private List<RestaurantReviewsData> restaurantReviewsDataList = new ArrayList<>();

    public CommentsAdapter(Context context, Activity activity, List<RestaurantReviewsData> restaurantReviewsDataList) {
        this.context = context;
        this.activity = activity;
        this.restaurantReviewsDataList = restaurantReviewsDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reviews,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.commentFragmetTxName.setText(restaurantReviewsDataList.get(position).getClient().getName());
        holder.commentFragmentTvComment.setText(restaurantReviewsDataList.get(position).getComment());
        switch (restaurantReviewsDataList.get(position).getRate()){
            case "1": holder.commentFragmentIvPhoto.setImageResource(R.drawable.ic_angry); break;
            case "2": holder.commentFragmentIvPhoto.setImageResource(R.drawable.ic_sad);break;
            case "3": holder.commentFragmentIvPhoto.setImageResource(R.drawable.ic_happy);break;
            case "4": holder.commentFragmentIvPhoto.setImageResource(R.drawable.ic_very_happy);break;
            case "5": holder.commentFragmentIvPhoto.setImageResource(R.drawable.ic_love);break;
        }
    }
    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return restaurantReviewsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_fragmet_tx_name)
        TextView commentFragmetTxName;
        @BindView(R.id.comment_fragment_tv_comment)
        TextView commentFragmentTvComment;
        @BindView(R.id.comment_fragment_iv_photo)
        ImageView commentFragmentIvPhoto;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
