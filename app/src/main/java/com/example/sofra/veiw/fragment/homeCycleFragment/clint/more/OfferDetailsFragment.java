package com.example.sofra.veiw.fragment.homeCycleFragment.clint.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class OfferDetailsFragment extends BaseFragment {

    public OffersData offerdata;
    @BindView(R.id.offer_details_fragment_tv_name)
    TextView offerDetailsFragmentTvName;
    @BindView(R.id.offer_details_fragment_tv_discription)
    TextView offerDetailsFragmentTvDiscription;
    @BindView(R.id.offer_details_fragment_tv_from)
    TextView offerDetailsFragmentTvFrom;
    @BindView(R.id.offer_details_fragment_tv_to)
    TextView offerDetailsFragmentTvTo;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        offerDetailsFragmentTvName.setText(offerdata.getName());
        offerDetailsFragmentTvDiscription.setText(offerdata.getDescription());
        offerDetailsFragmentTvFrom.setText(offerdata.getStartingAt());
        offerDetailsFragmentTvTo.setText(offerdata.getEndingAt());
        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.offer_details_fragment_btn_order)
    public void onViewClicked() {
    }
}
