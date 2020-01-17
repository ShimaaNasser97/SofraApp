package com.example.sofra.veiw.fragment.homeCycleFragment.clint;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.client_new_order.ClientNewOrder;
import com.example.sofra.roomDatabase.ShopingDataBase;
import com.example.sofra.roomDatabase.model.Shoping;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.RestaurantDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class NewOrderFragment extends BaseFragment {

    @BindView(R.id.new_order_fragment_tv_text)
    TextView newOrderFragmentTvText;
    @BindView(R.id.new_order_fragment_et_details)
    EditText newOrderFragmentEtDetails;
    @BindView(R.id.new_order_fragment_tv_adress)
    EditText newOrderFragmentTvAdress;
    @BindView(R.id.new_order_fragment_iv_cash)
    RadioButton newOrderFragmentIvCash;
    @BindView(R.id.new_order_fragment_iv_online)
    RadioButton newOrderFragmentIvOnline;
    @BindView(R.id.new_order_fragment_tv_total)
    TextView newOrderFragmentTvTotal;
    @BindView(R.id.new_order_fragment_tv_tips)
    TextView newOrderFragmentTvTips;
    @BindView(R.id.new_order_fragment_tv_all_total)
    TextView newOrderFragmentTvAllTotal;
    Unbinder unbinder;
    private String note, adress, payment, total, cost, allTotal;
    private double t;
    private List<String> items, quantities, notes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        t = MyOrderDetailsFragment.total;
        newOrderFragmentTvTotal.setText(String.valueOf(t));
        newOrderFragmentTvTips.setText(RestaurantDetailsFragment.restaurant.getDeliveryCost());
        newOrderFragmentTvAllTotal.setText("100");

        items = new ArrayList<>();
        quantities = new ArrayList<>();
        notes = new ArrayList<>();

        List<Shoping> list = ShopingDataBase.getInstance(getActivity()).shopingDao().getAllShoping();
        for (int i = 0; i < list.size(); i++) {
            items.add(list.get(i).getItemId());
            quantities.add(list.get(i).getQuantity());
            notes.add(list.get(i).getContent());
        }
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

    @OnClick({R.id.new_order_fragment_iv_cash, R.id.new_order_fragment_iv_online})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.new_order_fragment_iv_cash:
                payment = "1";
                break;
            case R.id.new_order_fragment_iv_online:
                payment = "2";
                break;
        }
    }

    @OnClick(R.id.new_order_fragment_btn_accept)
    public void onViewClicked() {
        note = newOrderFragmentEtDetails.getText().toString();
        adress = newOrderFragmentTvAdress.getText().toString();
        total = newOrderFragmentTvTotal.getText().toString();
        cost = newOrderFragmentTvTips.getText().toString();
        allTotal = newOrderFragmentTvAllTotal.getText().toString();

        getNewOrder(note, adress, total, cost, allTotal);
    }

    private void getNewOrder(String note, String adress, String total, String cost, String allTotal) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getClient().getNewOrder(RestaurantDetailsFragment.restaurant.getId().toString(), note, adress, payment,
                    RestaurantDetailsFragment.restaurant.getPhone(), RestaurantDetailsFragment.restaurant.getName(),
                    SharedPreferencesManger.loadClientData(getActivity()).getApiToken(), items, quantities, notes).enqueue(new Callback<ClientNewOrder>() {
                @Override
                public void onResponse(Call<ClientNewOrder> call, Response<ClientNewOrder> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            ShopingDataBase.getInstance(getActivity()).shopingDao().deleteAllShoping();
                            response.body().getData();
                        }
                    } catch (Exception e) {
                        Toast.makeText(baseActivity, "catch " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClientNewOrder> call, Throwable t) {
                    try {
                        Toast.makeText(baseActivity, "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
