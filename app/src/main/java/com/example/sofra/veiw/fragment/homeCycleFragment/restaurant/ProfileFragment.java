package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_login.RestaurantLogin;
import com.example.sofra.data.model.restaurant_login.RestaurantLoginData;
import com.example.sofra.data.model.restaurant_profile.RestaurantProfile;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.HomeCycleActivity;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_PASSWORD;
import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.example.sofra.helper.GeneralRequests.getSpinnerData;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;


public class ProfileFragment extends BaseFragment {

    @BindView(R.id.profile_fragment_iv_imege)
    CircleImageView profileFragmentIvImege;
    @BindView(R.id.profile_fragment_et_name)
    EditText profileFragmentEtName;
    @BindView(R.id.profile_fragment_et_email)
    EditText profileFragmentEtEmail;
    @BindView(R.id.profile_fragment_et_phone)
    EditText profileFragmentEtPhone;
    @BindView(R.id.profile_fragment_sp_city)
    Spinner profileFragmentSpCity;
    @BindView(R.id.profile_fragment_ll_contaer)
    LinearLayout profileFragmentLlContaer;
    @BindView(R.id.profile_fragment_sp_regione)
    Spinner profileFragmentSpRegione;
    @BindView(R.id.profile_fragment_ll_contaer2)
    LinearLayout profileFragmentLlContaer2;
    @BindView(R.id.profile_fragment_et_password)
    EditText profileFragmentEtPassword;
    @BindView(R.id.profile_fragment_et_mincharge)
    EditText profileFragmentEtMincharge;
    @BindView(R.id.profile_fragment_et_cost)
    EditText profileFragmentEtCost;
    @BindView(R.id.profile_fragment_sw_state)
    Switch profileFragmentSwState;
    @BindView(R.id.profile_fragment_ll_contaner3)
    LinearLayout profileFragmentLlContaner3;
    @BindView(R.id.profile_fragment_tx_connection)
    TextView profileFragmentTxConnection;
    @BindView(R.id.profile_fragment_et_whats)
    EditText profileFragmentEtWhats;
    Unbinder unbinder;
    private ArrayList<AlbumFile> imagesList;
    private String path , name , email , phone , pass , cost, min ,  avelable , time , watsApp;
    private RestaurantLoginData restaurantLoginData;
    private SpinnerAdapter cityAdapter;
    private SpinnerAdapter regionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        cityAdapter = new SpinnerAdapter(getActivity());
        regionAdapter = new SpinnerAdapter(getActivity());

        restaurantLoginData = SharedPreferencesManger.loadRestaurantData(getActivity());
        HelperMethod.onLoadImageFromUrl(profileFragmentIvImege, restaurantLoginData.getUser().getPhotoUrl(), getContext());
        profileFragmentEtName.setText(restaurantLoginData.getUser().getName());
        profileFragmentEtEmail.setText(restaurantLoginData.getUser().getEmail());
        profileFragmentEtPhone.setText(restaurantLoginData.getUser().getPhone());
        profileFragmentEtCost.setText(restaurantLoginData.getUser().getDeliveryCost());
        profileFragmentEtMincharge.setText(restaurantLoginData.getUser().getMinimumCharger());
        profileFragmentEtPassword.setText(restaurantLoginData.getUser().getDeliveryTime());
        profileFragmentEtWhats.setText(restaurantLoginData.getUser().getWhatsapp());

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getSpinnerData(getClient().getRegionsNoPaginated(cityAdapter.selectedId), profileFragmentSpRegione, regionAdapter, getString(R.string.region), SharedPreferencesManger.loadRestaurantData(getActivity()).getUser().getRegion().getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getSpinnerData(getClient().getCitesNoPaginated(), profileFragmentSpCity, cityAdapter, getString(R.string.city), listener, SharedPreferencesManger.loadRestaurantData(getActivity()).getUser().getRegion().getCity().getId());

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

    @OnClick({R.id.profile_fragment_iv_imege, R.id.profile_fragment_btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_fragment_iv_imege:
                HelperMethod.openGallery(getActivity(), 1, imagesList, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        imagesList.clear();
                        imagesList = result;
                        path = imagesList.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(profileFragmentIvImege, path, getActivity());
                    }
                });
                        break;
            case R.id.profile_fragment_btn_update:

                editProfile();
                break;
        }
    }
    private void editProfile() {
        name = profileFragmentEtName.getText().toString();
        email = profileFragmentEtEmail.getText().toString();
        phone = profileFragmentEtPhone.getText().toString();
        pass = profileFragmentEtPassword.getText().toString();
        cost = profileFragmentEtCost.getText().toString();
        min = profileFragmentEtMincharge.getText().toString();
        time = profileFragmentEtPassword.getText().toString();
        watsApp=profileFragmentEtWhats.getText().toString();
        if (profileFragmentSwState.isChecked()) {
            avelable = "open";
        }else avelable = "close";

        int region_Id = regionAdapter.selectedId;
        getClient().getRestaurantProfile(convertToRequestBody(email), convertToRequestBody(name), convertToRequestBody(phone),
                convertToRequestBody(String.valueOf(regionAdapter.selectedId)), convertToRequestBody(cost), convertToRequestBody(min), convertToRequestBody(avelable),
                convertFileToMultipart(path, "photo"), convertToRequestBody(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken()),convertToRequestBody(time),convertToRequestBody(watsApp)).enqueue(new Callback<RestaurantProfile>() {
            @Override
            public void onResponse(Call<RestaurantProfile> call, Response<RestaurantProfile> response) {
                if (response.body().getStatus() == 1) {
                    response.body().getData().getUser().setApiToken(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken());
                    SaveData(getActivity(), RESTAURANT_DATA, response.body().getData());
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<RestaurantProfile> call, Throwable t) {

            }
        });
    }
}
