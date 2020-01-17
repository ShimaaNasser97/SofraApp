package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.HalfFloat;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.roomDatabase.ShopingDao;
import com.example.sofra.roomDatabase.ShopingDataBase;
import com.example.sofra.roomDatabase.model.Shoping;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class ShopingAdapter extends RecyclerView.Adapter<ShopingAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<Shoping> shopingList = new ArrayList<>();
    private int q;

    public ShopingAdapter(Context context, Activity activity, List<Shoping> shopingList) {
        this.context = context;
        this.activity = activity;
        this.shopingList = shopingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_order_details,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        onLoadImageFromUrl(holder.myOrderDetailsFragmentIvPhoto,shopingList.get(position).getPath(),context);
        holder.myOrderDetailsFragmentTvQuantity.setText(shopingList.get(position).getQuantity());
        holder.myOrderDetailsFragmentTvName.setText(shopingList.get(position).getName());
    }

    private void setAction(final ViewHolder holder, final int position) {

        holder.myOrderDetailsFragmentBtnPluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuantity('+', holder, position);
            }
        });

        holder.myOrderDetailsFragmentBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuantity('-', holder, position);
            }
        });

        holder.myOrderDetailsFragmentBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shoping shoping = shopingList.get(position);
                ShopingDataBase.getInstance(context)
                        .shopingDao()
                        .removeShoping(shoping);
                shopingList.remove(position);
                notifyDataSetChanged();

            }
        });

    }

    private void getQuantity(char c, final ViewHolder holder, int position) {
        q = Integer.parseInt(holder.myOrderDetailsFragmentTvQuantity.getText().toString());
        if (c == '+') q++;
        else q--;

        if (q < 0) q = 0;
        holder.myOrderDetailsFragmentTvQuantity.setText(q + "");
        Shoping shoping1 = shopingList.get(position);
        shoping1.setQuantity(String.valueOf(q));
        ShopingDataBase.getInstance(context)
                .shopingDao().updateShoping(shoping1);
    }


    @Override
    public int getItemCount() {
        return shopingList.size();
    }

    public void changData(List<Shoping> shopingList) {
        this.shopingList = shopingList;
        notifyDataSetChanged();
    }
    public void clearData(List<Shoping> shopingList) {
        this.shopingList = shopingList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.my_order_details_fragment_iv_photo)
        ImageView myOrderDetailsFragmentIvPhoto;
        @BindView(R.id.my_order_details_fragment_tv_quantity)
        TextView myOrderDetailsFragmentTvQuantity;
        @BindView(R.id.my_order_details_fragment_tv_name)
        TextView myOrderDetailsFragmentTvName;
        @BindView(R.id.my_order_details_fragment_btn_minus)
        Button myOrderDetailsFragmentBtnMinus;
        @BindView(R.id.my_order_details_fragment_btn_pluse)
        Button myOrderDetailsFragmentBtnPluse;
        @BindView(R.id.my_order_details_fragment_btn_cancel)
        Button myOrderDetailsFragmentBtnCancel;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
