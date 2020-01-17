package com.example.sofra.veiw.fragment.homeCycleFragment.clint.more;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.client_change_password.ClientChangePassword;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class ClientChangPasswordFragment extends BaseFragment {

    @BindView(R.id.clint_chang_password_fragment_et_old_pqss)
    EditText changPasswordFragmentEtOldPqss;
    @BindView(R.id.clint_chang_password_fragment_et_new_pqss)
    EditText changPasswordFragmentEtNewPqss;
    @BindView(R.id.clint_chang_password_fragment_et_confirm_pqss)
    EditText changPasswordFragmentEtConfirmPqss;
    Unbinder unbinder;
    private String oldPass ,newPass,confirrmPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_cange_password, container, false);
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

    @OnClick(R.id.chang_password_fragment_btn_chang)
    public void onViewClicked() {
        oldPass=changPasswordFragmentEtOldPqss.getText().toString();
        newPass=changPasswordFragmentEtNewPqss.getText().toString();
        confirrmPass=changPasswordFragmentEtConfirmPqss.getText().toString();
        validation(oldPass,newPass,confirrmPass);
        getChangPassword();
    }
    private void validation(String oldPass, String newPass, String confiirmPass) {
        if (oldPass.isEmpty()){
            changPasswordFragmentEtOldPqss.setError("old Password is requierd");
            changPasswordFragmentEtOldPqss.requestFocus();
        }
        if (newPass.isEmpty()){
            changPasswordFragmentEtNewPqss.setError("new Password is requierd");
            changPasswordFragmentEtNewPqss.requestFocus();
        }
        if (confiirmPass.isEmpty()){
            changPasswordFragmentEtConfirmPqss.setError("confirrm Password is requierd");
            changPasswordFragmentEtConfirmPqss.requestFocus();
        }
    }
    private void getChangPassword() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getClient().getClientChangePassword(SharedPreferencesManger.loadClientData(getActivity()).getApiToken(),oldPass,newPass,confirrmPass).enqueue(new Callback<ClientChangePassword>() {
                @Override
                public void onResponse(Call<ClientChangePassword> call, Response<ClientChangePassword> response) {
                    try {
                        if (response.body().getStatus()==1) {
                            Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ClientChangePassword> call, Throwable t) {
                    Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
