package com.example.sofra.veiw.fragment.homeCycleFragment.clint.more;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.ClientOffersAdapter;
import com.example.sofra.data.model.offers.Offers;
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.veiw.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class ClientOffersFragment extends BaseFragment {

    @BindView(R.id.client_offers_fragment_rv_offers)
    RecyclerView clientOffersFragmentRvOffers;
    Unbinder unbinder;
    private ClientOffersAdapter clientofferAdapter;
    private List<OffersData> clientOffersList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private int maxPage=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_offers, container, false);
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        clientOffersFragmentRvOffers.setLayoutManager(linearLayoutManager);
       /* onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        previous_page = current_page;
                        getClientOffers(current_page);
                    } else {
                        onEndLess.current_page = previous_page;
                    }
                } else {
                    onEndLess.current_page = previous_page;
                }
            }
        };
        clientOffersFragmentRvOffers.addOnScrollListener(onEndLess);*/
        clientofferAdapter=new ClientOffersAdapter(getActivity(),baseActivity,clientOffersList);
        clientOffersFragmentRvOffers.setAdapter(clientofferAdapter);
        getClientOffers(1);
        return view;
    }
    private void getClientOffers(int page) {
        getClient().getClientOffers(page).enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                try {
                    if (response.body().getStatus()==1) {
                        maxPage=response.body().getData().getLastPage();
                        clientOffersList.addAll(response.body().getData().getData());
                        clientofferAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){

                }
            }
            @Override
            public void onFailure(Call<Offers> call, Throwable t) {

            }
        });
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
}
