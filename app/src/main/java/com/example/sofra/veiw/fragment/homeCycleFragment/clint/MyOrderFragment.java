package com.example.sofra.veiw.fragment.homeCycleFragment.clint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.items.ItemsData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.roomDatabase.ShopingDao;
import com.example.sofra.roomDatabase.ShopingDataBase;
import com.example.sofra.roomDatabase.model.Shoping;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.RestaurantDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.roomDatabase.ShopingDataBase.getInstance;


public class MyOrderFragment extends BaseFragment {

    public ItemsData item;
    public static String path;
    @BindView(R.id.my_order_fragment_iv_photo)
    ImageView myOrderFragmentIvPhoto;
    @BindView(R.id.my_order_fragment_tv_name)
    TextView myOrderFragmentTvName;
    @BindView(R.id.my_order_fragment_tv_discreption)
    TextView myOrderFragmentTvDiscreption;
    @BindView(R.id.my_order_fragment_tv_price)
    TextView myOrderFragmentTvPrice;
    @BindView(R.id.my_order_fragment_tv_offer_price)
    TextView myOrderFragmentTvOfferPrice;
    Unbinder unbinder;
    @BindView(R.id.my_category_fragment_et_spicial_order)
    EditText myCategoryFragmentEtSpicialOrder;
    @BindView(R.id.my_order_fragment_tv_quantity)
    TextView myOrderFragmentTvQuantity;
    private int q;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        onLoadImageFromUrl(myOrderFragmentIvPhoto, item.getPhotoUrl(), getActivity());
        myOrderFragmentTvName.setText(item.getName());
        myOrderFragmentTvDiscreption.setText(item.getDescription());
        myOrderFragmentTvOfferPrice.setText(item.getOfferPrice());
        myOrderFragmentTvPrice.setText(item.getPrice());

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

    @OnClick({R.id.my_order_fragment_btn_pluse, R.id.my_order_fragment_btn_minus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_order_fragment_btn_pluse:
                quantity('+');
                break;
            case R.id.my_order_fragment_btn_minus:
                    quantity('-');
                break;
        }
    }

    private void quantity(char c) {
        q= Integer.parseInt(myOrderFragmentTvQuantity.getText().toString());
        if (c=='+') q++;
        else  q--;
        if (q<0) q=0;
        myOrderFragmentTvQuantity.setText(q+"");
    }

    @OnClick(R.id.my_order_fragment_btn_enter)
    public void onViewClicked() {

      path =item.getPhotoUrl();
      String name=myOrderFragmentTvName.getText().toString();
      String discription=myOrderFragmentTvDiscreption.getText().toString();
      String price=myOrderFragmentTvPrice.getText().toString();
      String contect=myCategoryFragmentEtSpicialOrder.getText().toString();
      String quantity=myOrderFragmentTvQuantity.getText().toString();

      if (quantity.equals("0")){
          Toast.makeText(baseActivity, "Enter The Quantity", Toast.LENGTH_SHORT).show();
      }else {

          Shoping shoping = new Shoping(item.getId().toString(),path, name, price, discription, contect, quantity);
          ShopingDataBase.getInstance(getActivity())
                  .shopingDao()
                  .addShoping(shoping);

          Toast.makeText(baseActivity, "added", Toast.LENGTH_SHORT).show();
          MyOrderDetailsFragment myOrderDetailsFragment = new MyOrderDetailsFragment();
          HelperMethod.replace(myOrderDetailsFragment, getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
      }
    }
}
