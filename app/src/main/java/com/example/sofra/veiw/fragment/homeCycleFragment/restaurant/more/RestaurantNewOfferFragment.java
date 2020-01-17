package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.more;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.DateModel;
import com.example.sofra.data.model.restaurant_my_offers.RestaurantMyOffers;
import com.example.sofra.data.model.restaurant_my_offers.RestaurantMyOffersData;
import com.example.sofra.data.model.restaurant_update_offer.RestaurantUpdateOffer;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.ADD;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_ITEM;
import static com.example.sofra.data.local.SharedPreferencesManger.UPDATE;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.showCalender;


public class RestaurantNewOfferFragment extends BaseFragment {

    public RestaurantMyOffersData myOfferDataList;
    @BindView(R.id.restaurant_new_offer_fragment_text)
    TextView restaurantNewOfferFragmentText;
    @BindView(R.id.restaurant_new_offer_fragment_iv_photo)
    ImageView restaurantNewOfferFragmentIvPhoto;
    @BindView(R.id.restaurant_new_offer_fragment_name)
    EditText restaurantNewOfferFragmentName;
    @BindView(R.id.restaurant_new_offer_fragment_et_description)
    EditText restaurantNewOfferFragmentEtDescription;
    @BindView(R.id.restaurant_new_offer_fragment_et_from)
    EditText restaurantNewOfferFragmentEtFrom;
    @BindView(R.id.restaurant_new_offer_fragment_et_to)
    EditText restaurantNewOfferFragmentEtTo;
    Unbinder unbinder;
    @BindView(R.id.restaurant_new_offer_fragment_btn_add)
    Button restaurantNewOfferFragmentBtnAdd;
    private ArrayList<AlbumFile> imagesList = new ArrayList<>();
    private String path;
    private DateModel from, to;
    private String name, discription, fromDate, toDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_new_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (SharedPreferencesManger.LoadData(getActivity(), RESTAURANT_ITEM).equals(UPDATE)) {
            restaurantNewOfferFragmentName.setText(myOfferDataList.getName());
            restaurantNewOfferFragmentEtDescription.setText(myOfferDataList.getDescription());
            restaurantNewOfferFragmentEtFrom.setText(myOfferDataList.getStartingAt());
            restaurantNewOfferFragmentEtTo.setText(myOfferDataList.getEndingAt());
            restaurantNewOfferFragmentBtnAdd.setText(getString(R.string.update));
            HelperMethod.onLoadImageFromUrl(restaurantNewOfferFragmentIvPhoto, myOfferDataList.getPhotoUrl(), getActivity());
            restaurantNewOfferFragmentText.setText(getString(R.string.offerImage));


        }
        from = new DateModel("01", "01", "1970", "1970-01-01");
        to = new DateModel("01", "01", "1970", "1970-01-01");

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


    @OnClick({R.id.restaurant_new_offer_fragment_et_from, R.id.restaurant_new_offer_fragment_et_to, R.id.restaurant_new_offer_fragment_iv_photo, R.id.restaurant_new_offer_fragment_btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_new_offer_fragment_et_from:
                showCalender(getActivity(), getString(R.string.from), restaurantNewOfferFragmentEtFrom, from);
                break;
            case R.id.restaurant_new_offer_fragment_et_to:
                showCalender(getActivity(), getString(R.string.to), restaurantNewOfferFragmentEtTo, to);
                break;
            case R.id.restaurant_new_offer_fragment_iv_photo:
                HelperMethod.openGallery(getActivity(), 1, imagesList, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        imagesList.clear();
                        imagesList = result;
                        path = imagesList.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(restaurantNewOfferFragmentIvPhoto, path, getActivity());
                    }
                });
                break;
            case R.id.restaurant_new_offer_fragment_btn_add:
                name = restaurantNewOfferFragmentName.getText().toString();
                discription = restaurantNewOfferFragmentEtDescription.getText().toString();
                fromDate = restaurantNewOfferFragmentEtFrom.getText().toString();
                toDate = restaurantNewOfferFragmentEtTo.getText().toString();
                valedation(name, discription, fromDate, toDate);
                if (SharedPreferencesManger.LoadData(getActivity(),RESTAURANT_ITEM).equals(ADD)){
                    getNewOffer(name, discription, fromDate, toDate);
                }
                if (SharedPreferencesManger.LoadData(getActivity(),RESTAURANT_ITEM).equals(UPDATE)){
                    getUpdateOffer(name, discription, fromDate, toDate);
                }

                break;
        }
    }

    private void getNewOffer(String name, String discription, String fromDate, String toDate) {
        getClient().getRestaurantNewOffer(convertToRequestBody(discription), convertToRequestBody("100"),
                convertToRequestBody(fromDate), convertToRequestBody(name), convertFileToMultipart(path, "photo"), convertToRequestBody(toDate),
                convertToRequestBody(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken())).enqueue(new Callback<RestaurantMyOffers>() {
            @Override
            public void onResponse(Call<RestaurantMyOffers> call, Response<RestaurantMyOffers> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyOffers> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUpdateOffer(String name, String discription, String fromDate, String toDate) {
        getClient().getRestaurantUpdateOffer(convertToRequestBody(discription),convertToRequestBody(myOfferDataList.getPrice()),
                convertToRequestBody(fromDate),convertToRequestBody(name),convertFileToMultipart(path,"photo"),
                convertToRequestBody(toDate),convertToRequestBody(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken())).enqueue(new Callback<RestaurantMyOffers>() {
            @Override
            public void onResponse(Call<RestaurantMyOffers> call, Response<RestaurantMyOffers> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyOffers> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void valedation(String name, String discription, String fromDate, String toDate) {

    }
}
