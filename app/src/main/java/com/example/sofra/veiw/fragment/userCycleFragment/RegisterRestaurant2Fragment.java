package com.example.sofra.veiw.fragment.userCycleFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_login.RestaurantLogin;
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
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;


public class RegisterRestaurant2Fragment extends BaseFragment {

    @BindView(R.id.register_restaurant2_fragment_et_phone)
    EditText registerRestaurant2FragmentEtPhone;
    @BindView(R.id.register_restaurant2_fragment_et_whates)
    EditText registerRestaurant2FragmentEtWhates;
    @BindView(R.id.register_restaurant2_fragment_iv_image)
    ImageView registerRestaurant2FragmentIvImage;
    Unbinder unbinder;
    private ArrayList<AlbumFile> imagesList=new ArrayList<>() ;
    private String path;
    private RegisterRestaurant1Fragment registerRestaurant1Fragment;
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
        View view = inflater.inflate(R.layout.fragment_register_restaurent2, container, false);
        unbinder = ButterKnife.bind(this, view);

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

    @OnClick({R.id.register_restaurant2_fragment_iv_image, R.id.register_restaurant2_fragment_btn_sinup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_restaurant2_fragment_iv_image:
                HelperMethod.openGallery(getActivity(), 1, imagesList, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        imagesList.clear();
                        imagesList = result;
                        path = imagesList.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(registerRestaurant2FragmentIvImage, path, getActivity());
                    }
                });
                break;
            case R.id.register_restaurant2_fragment_btn_sinup:
                 registerRestaurant1Fragment=new RegisterRestaurant1Fragment();
                 String phone=registerRestaurant2FragmentEtPhone.getText().toString();
                 String whatsup=registerRestaurant2FragmentEtWhates.getText().toString();
                 regionAdapter=new SpinnerAdapter(getActivity());
                 int regionId=regionAdapter.selectedId;
                 validation(phone,whatsup);

                 getRestaurentRegister(registerRestaurant1Fragment.name,registerRestaurant1Fragment.email, registerRestaurant1Fragment.password,registerRestaurant1Fragment.verifypass,
                        phone,whatsup,regionId,registerRestaurant1Fragment.deliveryCost,registerRestaurant1Fragment.minimumCharger,registerRestaurant1Fragment.time);

                break;
        }
    }

    private void getRestaurentRegister(String name, String email, String password, String verifypass, String phone, String whatsup, int regionId, String deliveryCost, String minimumCharger, String time) {
        getClient().getRestaurantSinup(convertToRequestBody(name),convertToRequestBody(email),convertToRequestBody(password),convertToRequestBody(verifypass),
                convertToRequestBody(phone),convertToRequestBody(whatsup),convertToRequestBody(String.valueOf(regionId)),convertToRequestBody(String.valueOf(deliveryCost)),
                convertToRequestBody(String.valueOf(minimumCharger)),convertFileToMultipart(path,"photo"),convertToRequestBody(time)).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                try {
                    if (response.body().getStatus()==1) {
                        //SharedPreferencesManger.SaveData(getActivity(),"USER_DATA",response.body().getData());
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(baseActivity,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {
               try {
                   Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
               }catch (Exception e){
                   Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void validation(String phone, String whatsup) {
        if (phone.isEmpty()) {
            registerRestaurant2FragmentEtPhone.setError("password is required");
            registerRestaurant2FragmentEtPhone.requestFocus();
            return;
        }
        if (whatsup.isEmpty()) {
            registerRestaurant2FragmentEtWhates.setError("password is required");
            registerRestaurant2FragmentEtWhates.requestFocus();
            return;
    }
}
}
