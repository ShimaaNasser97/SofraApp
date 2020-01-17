package com.example.sofra.veiw.fragment.userCycleFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.client_login.ClientLogin;
import com.example.sofra.data.model.restaurant_login.RestaurantLogin;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.HomeCycleActivity;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.CLINT;
import static com.example.sofra.data.local.SharedPreferencesManger.CLINT_EMAIL;
import static com.example.sofra.data.local.SharedPreferencesManger.CLINT_PASSWORD;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_EMAIL;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_PASSWORD;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;


public class LoginFragment extends BaseFragment {

    @BindView(R.id.login_fragment_et_email)
    EditText loginFragmentEtEmail;
    @BindView(R.id.login_fragment_et_password)
    EditText loginFragmentEtPassword;
    Unbinder unbinder;
    private Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBack() {
        baseActivity.finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.login_fragment_tv_forget_password, R.id.login_fragment_enter, R.id.login_fragment_register_clint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_fragment_tv_forget_password:
                ForgetPasswordFragment forgetPasswordFragment = new ForgetPasswordFragment();
                HelperMethod.replace(forgetPasswordFragment, getActivity().getSupportFragmentManager(), R.id.auth_cycle_activity_fl_contaner, null, null);
                break;
            case R.id.login_fragment_enter:
                String email=loginFragmentEtEmail.getText().toString();
                String password=loginFragmentEtPassword.getText().toString();

                if (SharedPreferencesManger.LoadData(getActivity(), USER_TYPE).equals(CLINT)) {
                    validation(email,password);
                    getClientLogin(email,password);
                }else if(SharedPreferencesManger.LoadData(getActivity(),USER_TYPE).equals(RESTAURANT)) {
                    validation(email,password);
                    getRestaurantLogin(email,password);
                }


                 break;
            case R.id.login_fragment_register_clint:


                if (SharedPreferencesManger.LoadData(getActivity(), USER_TYPE).equals(CLINT)) {
                    RegisterClintFragment registerClintFragment = new RegisterClintFragment();
                    HelperMethod.replace(registerClintFragment, getActivity().getSupportFragmentManager(), R.id.auth_cycle_activity_fl_contaner, null, null);

                   //Intent intent=new Intent(getActivity(),HomeCycleActivity.class);
                  // startActivity(intent);
                }else if(SharedPreferencesManger.LoadData(getActivity(),USER_TYPE).equals(RESTAURANT)) {
                    RegisterRestaurant1Fragment registerRestaurant1Fragment=new RegisterRestaurant1Fragment();
                    HelperMethod.replace(registerRestaurant1Fragment,getActivity().getSupportFragmentManager(),R.id.auth_cycle_activity_fl_contaner,null,null);
                }

                break;
        }
    }

    private void getClientLogin(String email, final String password) {
        getClient().getClientLogin(email,password).enqueue(new Callback<ClientLogin>() {
            @Override
            public void onResponse(Call<ClientLogin> call, Response<ClientLogin> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        SharedPreferencesManger.SaveData(getActivity(),CLIENT_DATA,response.body().getData());
                        SharedPreferencesManger.SaveData(getActivity(),CLINT_PASSWORD,password);
                        SharedPreferencesManger.SaveData(getActivity(),CLINT_EMAIL,response.body().getData().getUser().getEmail());
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        Intent intent=new Intent(getActivity(),HomeCycleActivity.class);
                        startActivity(intent);

                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClientLogin> call, Throwable t) {
                try {
                    Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void validation(String email, String password) {
        if (email.isEmpty()) {
            loginFragmentEtEmail.setError("phone is required");
            loginFragmentEtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            loginFragmentEtPassword.setError("password is required");
            loginFragmentEtPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            loginFragmentEtPassword.setError("password should be atleast 6 character long");
            loginFragmentEtPassword.requestFocus();

            return;
        }
    }

    private void getRestaurantLogin(String email, final String password) {
        getClient().getRestaurantLogin(email,password).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        SharedPreferencesManger.SaveData(getActivity(),RESTAURANT_DATA,response.body().getData());
                        SharedPreferencesManger.SaveData(getActivity(),RESTAURANT_PASSWORD,password);
                        SharedPreferencesManger.SaveData(getActivity(),RESTAURANT_EMAIL,response.body().getData().getUser().getEmail());
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), HomeCycleActivity.class);
                        startActivity(intent);
                       }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
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


}






