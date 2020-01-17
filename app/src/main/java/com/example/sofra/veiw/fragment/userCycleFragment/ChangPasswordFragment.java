package com.example.sofra.veiw.fragment.userCycleFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.new_password.NewPassword;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class ChangPasswordFragment extends BaseFragment {

    @BindView(R.id.chang_password_et_pin_code)
    EditText changPasswordEtPinCode;
    @BindView(R.id.chang_password_et_password)
    EditText changPasswordEtPassword;
    @BindView(R.id.chang_password_et_verify_password)
    EditText changPasswordEtVerifyPassword;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chang_password, container, false);
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

    @OnClick(R.id.chang_password_btn_send)
    public void onViewClicked() {
        String code=changPasswordEtPinCode.getText().toString();
        String pass=changPasswordEtPassword.getText().toString();
        String verifyPass=changPasswordEtVerifyPassword.getText().toString();

        validation(code,pass,verifyPass);

        getNewPassword(code,pass,verifyPass);

    }

    private void getNewPassword(String code, String pass, String verifyPass) {
        getClient().getNewPassword(code,pass,verifyPass).enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {
                try {
                    Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT);
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void validation(String code, String pass, String verifyPass) {
        if (code.isEmpty()){
            changPasswordEtPinCode.setError("pin code is requerd");
            changPasswordEtPinCode.requestFocus();
        }
        if (pass.isEmpty()){
            changPasswordEtPassword.setError("pin code is requerd");
            changPasswordEtPassword.requestFocus();
        }
        if (verifyPass.isEmpty()){
            changPasswordEtVerifyPassword.setError("pin code is requerd");
            changPasswordEtVerifyPassword.requestFocus();
        }

    }
}
