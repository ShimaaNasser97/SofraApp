package com.example.sofra.veiw.fragment.userCycleFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.client_login.ClientLogin;
import com.example.sofra.data.model.client_sign_up.ClientSignUp;
import com.example.sofra.data.model.generalResponce.GeneralResponceData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.NewOrderFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT_DATA;
import static com.example.sofra.helper.GeneralRequests.getSpinnerData;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;


public class RegisterClintFragment extends BaseFragment {


    @BindView(R.id.register_clint_fragment_iv_imege)
    ImageView registerClintFragmentIvImege;
    Unbinder unbinder;
    @BindView(R.id.register_clint_fragment_et_name)
    EditText registerClintFragmentEtName;
    @BindView(R.id.register_clint_fragment_et_email)
    EditText registerClintFragmentEtEmail;
    @BindView(R.id.register_clint_fragment_et_phone)
    EditText registerClintFragmentEtPhone;
    @BindView(R.id.register_clint_fragment_sp_city)
    Spinner registerClintFragmentSpCity;
    @BindView(R.id.register_clint_fragment_sp_region)
    Spinner registerClintFragmentSpRegion;
    @BindView(R.id.register_clint_fragment_et_password)
    EditText registerClintFragmentEtPassword;
    @BindView(R.id.register_clint_fragment_et_verify_password)
    EditText registerClintFragmentEtVerifyPassword;

    private ArrayList<AlbumFile> imagesList = new ArrayList<>();
    private String path;

    private List<String> cityList;

    private List<GeneralResponceData> regionList;
    private SpinnerAdapter cityAdapter, regionAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_client, container, false);
        unbinder = ButterKnife.bind(this, view);
        cityAdapter = new SpinnerAdapter(getActivity());
        regionAdapter = new SpinnerAdapter(getActivity());
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getSpinnerData(getClient().getRegionsNoPaginated(cityAdapter.selectedId), registerClintFragmentSpRegion, regionAdapter, getString(R.string.region));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getSpinnerData(getClient().getCitesNoPaginated(), registerClintFragmentSpCity, cityAdapter, getString(R.string.city), listener);

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

    @OnClick({R.id.register_clint_fragment_iv_imege, R.id.register_clint_fragment_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_clint_fragment_iv_imege:
                HelperMethod.openGallery(getActivity(), 1, imagesList, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        imagesList.clear();
                        imagesList = result;
                        path = imagesList.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(registerClintFragmentIvImege, path, getActivity());
                    }
                });
                break;

            case R.id.register_clint_fragment_btn_register:

                String name = registerClintFragmentEtName.getText().toString();
                String email = registerClintFragmentEtEmail.getText().toString();
                String phone = registerClintFragmentEtPhone.getText().toString();
                int regionId = regionAdapter.selectedId;
                String password = registerClintFragmentEtPassword.getText().toString();
                String verifyPassword = registerClintFragmentEtVerifyPassword.getText().toString();
                validation(name, email, phone, password, verifyPassword);
                getClintRegister(name, email, phone, password, verifyPassword, regionId);

                break;
        }
    }

    private void getClintRegister(String name, String email, String password, String verifyPassword, String phone, int regionId) {

        getClient().getClientResister(convertToRequestBody(name), convertToRequestBody(email)
                , convertToRequestBody(password), convertToRequestBody(verifyPassword)
                , convertToRequestBody(phone), convertToRequestBody(String.valueOf(regionId))
                , convertFileToMultipart(path, "profile_image")).enqueue(new Callback<ClientLogin>() {
            @Override
            public void onResponse(Call<ClientLogin> call, Response<ClientLogin> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        SharedPreferencesManger.SaveData(getActivity(), CLIENT_DATA, response.body().getData());
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        LoginFragment loginFragment = new LoginFragment();
                        HelperMethod.replace(loginFragment, getActivity().getSupportFragmentManager(), R.id.auth_cycle_activity_fl_contaner, null, null);
                    }

                } catch (Exception e) {
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClientLogin> call, Throwable t) {
                try {
                    Log.d(TAG, "onFailure: ");
                    Toast.makeText(baseActivity, "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }
        });


    }

    private void validation(String name, String email, String phone, String password, String verifyPassword) {
        if (name.isEmpty()) {
            registerClintFragmentEtName.setError("name eis required");
            registerClintFragmentEtName.requestFocus();

            return;
        }

        if (email.isEmpty()) {
            registerClintFragmentEtEmail.setError("email is required");
            registerClintFragmentEtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerClintFragmentEtEmail.setError("email is required");
            registerClintFragmentEtEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            registerClintFragmentEtPhone.setError("phone is required");
            registerClintFragmentEtPhone.requestFocus();
            return;
        }
       /* if () {
            registerClintFragmentEtPhone.setError("phone is required");
            registerClintFragmentEtPhone.requestFocus();
            return;
        }*/

        if (password.isEmpty()) {
            registerClintFragmentEtPassword.setError("password is required");
            registerClintFragmentEtPassword.requestFocus();
            return;
        }
       /* if (verifyPassword.isEmpty()) {
            registerClintFragmentEtVerifyPassword.setError("verify password is required");
            registerClintFragmentEtVerifyPassword.requestFocus();
            return;
        }*/
    }

}

