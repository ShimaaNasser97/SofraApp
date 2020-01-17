package com.example.sofra.veiw.fragment.homeCycleFragment.clint.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.contact.Contact;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class ConnectUsFragment extends BaseFragment {

    @BindView(R.id.connect_us_fragment_et_name)
    EditText connectUsFragmentEtName;
    @BindView(R.id.connect_us_fragment_et_email)
    EditText connectUsFragmentEtEmail;
    @BindView(R.id.connect_us_fragment_et_phone)
    EditText connectUsFragmentEtPhone;
    @BindView(R.id.connect_us_fragment_et_massege)
    EditText connectUsFragmentEtMassege;
    Unbinder unbinder;
    @BindView(R.id.connect_us_fragment_rb_complaint)
    RadioButton connectUsFragmentRbComplaint;
    @BindView(R.id.connect_us_fragment_rb_suggestion)
    RadioButton connectUsFragmentRbSuggestion;
    @BindView(R.id.connect_us_fragment_rb_inquiry)
    RadioButton connectUsFragmentRbInquiry;
    @BindView(R.id.connect_us_fragment_rg_group)
    RadioGroup connectUsFragmentRgGroup;
    private String name, phone, email, massege;
    private String type;
    private int sellectedId;
   // private int CheckedRadioButtonId=connectUsFragmentRgGroup.getCheckedRadioButtonId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (connectUsFragmentRbComplaint.isChecked()) {
            type = "complaint";
        } else if (connectUsFragmentRbSuggestion.isChecked()) {
            type = "suggestion ";
        } else {
            type = "inquiry";
        }
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

    @OnClick(R.id.connect_us_fragment_btn_send)
    public void onViewClicked() {
        name = connectUsFragmentEtName.getText().toString();
        email = connectUsFragmentEtEmail.getText().toString();
        phone = connectUsFragmentEtPhone.getText().toString();
        massege = connectUsFragmentEtMassege.getText().toString();
        validation(name, email,phone,massege);
        getContact(name, email,phone,massege);
    }
    private void validation(String name, String email, String phone, String massege) {
        if (name.isEmpty()){
            connectUsFragmentEtName.setError("name is requaired");
            connectUsFragmentEtName.requestFocus();
        }
        if (email.isEmpty()){
            connectUsFragmentEtEmail.setError("email is requaired");
            connectUsFragmentEtEmail.requestFocus();
        }
        if (phone.isEmpty()){
            connectUsFragmentEtPhone.setError("phone is requaired");
            connectUsFragmentEtPhone.requestFocus();
        }
        if (massege.isEmpty()){
            connectUsFragmentEtMassege.setError("massege is requaired");
            connectUsFragmentEtMassege.requestFocus();
        }
       /* if (checkedRadioButtonId!=(R.id.connect_us_fragment_rb_inquiry)){
            Toast.makeText(baseActivity, "chose type of masseg", Toast.LENGTH_SHORT).show();
        }else if(checkedRadioButtonId!=(R.id.connect_us_fragment_rb_complaint)){
            Toast.makeText(baseActivity, "chose type of masseg", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(baseActivity, "chose type of masseg", Toast.LENGTH_SHORT).show();*/
    }
    private void getContact(String name, String email, String phone, String massege) {
        getClient().getContact(name, email, phone,type,massege).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
            }catch (Exception e){
                    Toast.makeText(baseActivity, "exeption"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Toast.makeText(baseActivity, "failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
