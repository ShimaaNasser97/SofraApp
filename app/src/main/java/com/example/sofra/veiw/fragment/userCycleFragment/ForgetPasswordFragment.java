package com.example.sofra.veiw.fragment.userCycleFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.reset_password.ResetPassword;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class ForgetPasswordFragment extends BaseFragment {

    @BindView(R.id.forget_password_fragment_tv_email)
    EditText forgetPasswordFragmentTvEmail;
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
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
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

    @OnClick(R.id.forget_password_fragment_btn_send)
    public void onViewClicked() {
        String email=forgetPasswordFragmentTvEmail.getText().toString();
        if (email.isEmpty()){
            forgetPasswordFragmentTvEmail.setError("email is requard");
            forgetPasswordFragmentTvEmail.requestFocus();
        }
       getResetPassword(email);
        }

    private void getResetPassword(String email) {
        getClient().getResetPassword(email).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        ChangPasswordFragment changPasswordFragment = new ChangPasswordFragment();
                        HelperMethod.replace(changPasswordFragment, getActivity().getSupportFragmentManager(), R.id.auth_cycle_activity_fl_contaner, null, null);

                    }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                try {
                    Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
