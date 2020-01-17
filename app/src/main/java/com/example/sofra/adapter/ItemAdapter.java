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
import com.example.sofra.data.model.items.ItemsData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.roomDatabase.model.Shoping;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.MyOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class

ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<ItemsData> itemsDataList ;

    public ItemAdapter(Context context, BaseActivity activity, List<ItemsData> itemsDataList) {
        this.context = context;
        this.activity = activity;
        this.itemsDataList = itemsDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        onLoadImageFromUrl(holder.itemFragmentIvPhoto,itemsDataList.get(position).getPhotoUrl(),context);

        holder.itemFragmentTvName.setText(itemsDataList.get(position).getName());
        holder.itemFragmentTvDiscreption.setText(itemsDataList.get(position).getDescription());
        holder.itemFragmentTvPrice.setText(itemsDataList.get(position).getPrice());
        if (itemsDataList.get(position).getHasOffer()) {
            holder.itemFragmentTvOfferPrice.setText(itemsDataList.get(position).getOfferPrice());
        }
    }

    private void setAction(ViewHolder holder, final int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrderFragment myOrderFragment=new MyOrderFragment();
                myOrderFragment.item=itemsDataList.get(position);
                HelperMethod.replace(myOrderFragment,activity.getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
            }
        });

    }

    @Override
    public int getItemCount() {
       // if (itemsDataList==null) return 0;
        return itemsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_fragment_iv_photo)
        ImageView itemFragmentIvPhoto;
        @BindView(R.id.item_fragment_tv_name)
        TextView itemFragmentTvName;
        @BindView(R.id.item_fragment_tv_discreption)
        TextView itemFragmentTvDiscreption;
        @BindView(R.id.item_fragment_tv_price)
        TextView itemFragmentTvPrice;
        @BindView(R.id.item_fragment_tv_offer_price)
        TextView itemFragmentTvOfferPrice;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
