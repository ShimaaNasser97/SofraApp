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

import com.example.sofra.R;
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.more.OfferDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class ClientOffersAdapter extends RecyclerView.Adapter<ClientOffersAdapter.ViewHolder> {
    private Context context;
    private BaseActivity activity;
    private List<OffersData> offersDataList = new ArrayList<>();

    public ClientOffersAdapter(Context context, BaseActivity activity, List<OffersData> offersDataList) {
        this.context = context;
        this.activity = activity;
        this.offersDataList = offersDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_client_offers,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        onLoadImageFromUrl(holder.clientOffersFragmentCivPhoto,offersDataList.get(position).getPhotoUrl(),context);
        holder.clientOffersFragmentTvOffer.setText(offersDataList.get(position).getName());
    }

    private void setAction(ViewHolder holder, final int position) {
        holder.clientOffersFragmentBtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OfferDetailsFragment offerDetailsFragment=new OfferDetailsFragment();
                offerDetailsFragment.offerdata=offersDataList.get(position);
                HelperMethod.replace(offerDetailsFragment,activity.getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.client_offers_fragment_tv_offer)
        TextView clientOffersFragmentTvOffer;
        @BindView(R.id.client_offers_fragment_btn_details)
        Button clientOffersFragmentBtnDetails;
        @BindView(R.id.client_offers_fragment_civ_photo)
        CircleImageView clientOffersFragmentCivPhoto;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
