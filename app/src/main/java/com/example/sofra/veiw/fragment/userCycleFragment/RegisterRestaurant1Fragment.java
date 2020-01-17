package com.example.sofra.veiw.fragment.userCycleFragment;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;


import com.example.sofra.R;
import com.example.sofra.adapter.SpinnerAdapter;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.GeneralRequests.getSpinnerData;


public class RegisterRestaurant1Fragment extends BaseFragment {


    @BindView(R.id.register_restaurant1_fragment_et_restaurent_name)
    EditText registerRestaurant1FragmentEtRestaurentName;
    @BindView(R.id.register_restaurant1_fragment_email)
    EditText registerRestaurant1FragmentEmail;
    @BindView(R.id.register_restaurant1_fragment_et_time)
    EditText registerRestaurant1FragmentEtTime;
    @BindView(R.id.register_restaurant1_fragment_sp_city)
    Spinner registerRestaurant1FragmentSpCity;
    @BindView(R.id.register_restaurant1_fragment_sp_gavernment)
    Spinner registerRestaurant1FragmentSpGavernment;
    @BindView(R.id.register_restaurant1_fragment_et_password)
    EditText registerRestaurant1FragmentEtPassword;
    @BindView(R.id.register_restaurant1_fragment_et_verify_pass)
    EditText registerRestaurant1FragmentEtVerifyPass;
    @BindView(R.id.register_restaurant1_fragment_et_minimum_order)
    EditText registerRestaurant1FragmentEtMinimumOrder;
    @BindView(R.id.register_restaurant1_fragment_et_tips)
    EditText registerRestaurant1FragmentEtTips;
    Unbinder unbinder;
    public String name,email,time,password,verifypass;
    public String minimumCharger,deliveryCost;
    private SpinnerAdapter cityAdapter,regionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_restaurent1, container, false);
        unbinder = ButterKnife.bind(this, view);

        cityAdapter=new SpinnerAdapter(getActivity());
        regionAdapter=new SpinnerAdapter(getActivity());

        AdapterView.OnItemSelectedListener listener=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    getSpinnerData(getClient().getRegionsNoPaginated(cityAdapter.selectedId),registerRestaurant1FragmentSpGavernment,regionAdapter,getString(R.string.region));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getSpinnerData(getClient().getCitesNoPaginated(), registerRestaurant1FragmentSpCity, cityAdapter, getString(R.string.city),listener);

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


    @OnClick(R.id.register_restaurant1_fragment_btn_butten)
    public void onViewClicked() {
         name=registerRestaurant1FragmentEtRestaurentName.getText().toString();
         email=registerRestaurant1FragmentEmail.getText().toString();
       time=registerRestaurant1FragmentEtTime.getText().toString();
        password=registerRestaurant1FragmentEtPassword.getText().toString();
        verifypass=registerRestaurant1FragmentEtVerifyPass.getText().toString();
        minimumCharger= registerRestaurant1FragmentEtMinimumOrder.getText().toString();
         deliveryCost= registerRestaurant1FragmentEtTips.getText().toString();

        validation(name,email,time,password,verifypass,minimumCharger,deliveryCost);
        RegisterRestaurant2Fragment registerRestaurant2Fragment = new RegisterRestaurant2Fragment();
        HelperMethod.replace(registerRestaurant2Fragment, getActivity().getSupportFragmentManager(), R.id.auth_cycle_activity_fl_contaner, null, null);

    }

    private void validation(String name, String email,String time, String password, String verifypass, String minimumCharger, String deliveryCost) {

        if (name.isEmpty()) {
            registerRestaurant1FragmentEtRestaurentName.setError("restauorent name is required");
            registerRestaurant1FragmentEtRestaurentName.requestFocus();

            return;
        }

        if (email.isEmpty()) {
            registerRestaurant1FragmentEmail.setError("email is required");
            registerRestaurant1FragmentEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerRestaurant1FragmentEmail.setError("email is required");
            registerRestaurant1FragmentEmail.requestFocus();
            return;
        }
        if (time.isEmpty()) {
            registerRestaurant1FragmentEtTime.setError("phone is required");
            registerRestaurant1FragmentEtTime.requestFocus();
            return;
        }

       /* if () {
            registerClintFragmentEtPhone.setError("phone is required");
            registerClintFragmentEtPhone.requestFocus();
            return;
        }*/

        if (password.isEmpty()) {
            registerRestaurant1FragmentEtPassword.setError("password is required");
            registerRestaurant1FragmentEtPassword.requestFocus();
            return;
        }
        if (verifypass.isEmpty()) {
            registerRestaurant1FragmentEtVerifyPass.setError("password is required");
            registerRestaurant1FragmentEtVerifyPass.requestFocus();
            return;
        }
        if (minimumCharger.toString().isEmpty()) {
            registerRestaurant1FragmentEtMinimumOrder.setError("password is required");
            registerRestaurant1FragmentEtMinimumOrder.requestFocus();
            return;
        }
        if (deliveryCost.toString().isEmpty()) {
            registerRestaurant1FragmentEtTips.setError("password is required");
            registerRestaurant1FragmentEtTips.requestFocus();
            return;
        }
      /*  if (verifyPassword!=password) {
            registerClintFragmentEtVerifyPassword.setError("bbbbbb");
            registerClintFragmentEtVerifyPassword.requestFocus();
            return;
        }*/


    }


}
