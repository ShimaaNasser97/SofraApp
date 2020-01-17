package com.example.sofra.veiw.fragment.homeCycleFragment.clint.more;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sofra.R;
import com.example.sofra.data.model.client_change_password.ClientChangePassword;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ClintMoreFragment extends BaseFragment {

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
        View view = inflater.inflate(R.layout.fragment_clint_more, container, false);
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


    @OnClick({R.id.clint_more_fragment_tv_offer, R.id.clint_more_fragment_tv_connect, R.id.clint_more_fragment_tv_about, R.id.clint_more_fragment_tv_chang_pass, R.id.clint_more_fragment_tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clint_more_fragment_tv_offer:
                ClientOffersFragment clientOffersFragment=new ClientOffersFragment();
                HelperMethod.replace(clientOffersFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.clint_more_fragment_tv_connect:
                ConnectUsFragment connectUsFragment=new ConnectUsFragment();
                HelperMethod.replace(connectUsFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.clint_more_fragment_tv_about:
                ApoutAppFragment apoutAppFragment=new ApoutAppFragment();
                HelperMethod.replace(apoutAppFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.clint_more_fragment_tv_chang_pass:
                ClientChangPasswordFragment clientChangPasswordFragment=new ClientChangPasswordFragment();
                HelperMethod.replace(clientChangPasswordFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.clint_more_fragment_tv_exit:
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.exite);
                Button yes=dialog.findViewById(R.id.apoute_app_fragment_btn_yes);
                Button no=dialog.findViewById(R.id.apoute_app_fragment_btn_no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;

        }
    }
}
