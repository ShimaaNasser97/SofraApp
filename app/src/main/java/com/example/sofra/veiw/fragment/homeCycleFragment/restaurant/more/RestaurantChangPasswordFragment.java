package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.client_change_password.ClientChangePassword;
import com.example.sofra.data.model.restaurant_change_password.RestaurantChangePassword;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class RestaurantChangPasswordFragment extends BaseFragment {
    @BindView(R.id.restaurant_chang_password_fragment_et_old_pqss)
    EditText restaurantChangPasswordFragmentEtOldPqss;
    @BindView(R.id.restaurant_chang_password_fragment_et_new_pqss)
    EditText restaurantChangPasswordFragmentEtNewPqss;
    @BindView(R.id.restaurant_chang_password_fragment_et_confirm_pqss)
    EditText restaurantChangPasswordFragmentEtConfirmPqss;
    Unbinder unbinder;
    private String oldPass, newPass, confirrmPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_chang_password, container, false);
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

    @OnClick(R.id.restaurant_chang_password_fragment_btn_chang)
    public void onViewClicked() {
        oldPass=restaurantChangPasswordFragmentEtOldPqss.getText().toString();
        newPass=restaurantChangPasswordFragmentEtNewPqss.getText().toString();
        confirrmPass=restaurantChangPasswordFragmentEtConfirmPqss.getText().toString();
        validation(oldPass,newPass,confirrmPass);
        getChangPassword();
    }

    private void getChangPassword() {
        getClient().getRestaurantChangePassword(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken(),oldPass,newPass,confirrmPass).enqueue(new Callback<RestaurantChangePassword>() {
            @Override
            public void onResponse(Call<RestaurantChangePassword> call, Response<RestaurantChangePassword> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantChangePassword> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validation(String oldPass, String newPass, String confirrmPass) {
        if (oldPass.isEmpty()){
            restaurantChangPasswordFragmentEtOldPqss.setError("old Password is requierd");
            restaurantChangPasswordFragmentEtOldPqss.requestFocus();
        }
        if (newPass.isEmpty()){
            restaurantChangPasswordFragmentEtNewPqss.setError("new Password is requierd");
            restaurantChangPasswordFragmentEtNewPqss.requestFocus();
        }
        if (confirrmPass.isEmpty()){
            restaurantChangPasswordFragmentEtConfirmPqss.setError("confirrm Password is requierd");
            restaurantChangPasswordFragmentEtConfirmPqss.requestFocus();
        }
    }
}
