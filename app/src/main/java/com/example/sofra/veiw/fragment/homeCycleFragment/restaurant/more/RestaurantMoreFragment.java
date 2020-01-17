package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.more;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.UseaCycleActivity;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.more.ApoutAppFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.more.ConnectUsFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.CLINT;
import static com.example.sofra.data.local.SharedPreferencesManger.CLINT_EMAIL;
import static com.example.sofra.data.local.SharedPreferencesManger.CLINT_PASSWORD;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_EMAIL;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_PASSWORD;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;

public class RestaurantMoreFragment extends BaseFragment {

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
        View view = inflater.inflate(R.layout.fragment_restaurant_more, container, false);
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

    @OnClick({R.id.restaurant_more_fragment_tv_offer, R.id.restaurant_more_fragment_tv_connect, R.id.restaurant_more_fragment_tv_about, R.id.restaurant_more_fragment_tv_comments, R.id.restaurant_more_fragment_tv_chang_pass, R.id.restaurant_more_fragment_tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_more_fragment_tv_offer:
                RestaurantOffersFragment restaurantOffersFragment=new RestaurantOffersFragment();
                HelperMethod.replace(restaurantOffersFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.restaurant_more_fragment_tv_connect:
                ConnectUsFragment connectUsFragment=new ConnectUsFragment();
                HelperMethod.replace(connectUsFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.restaurant_more_fragment_tv_about:
                ApoutAppFragment apoutAppFragment=new ApoutAppFragment();
                HelperMethod.replace(apoutAppFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.restaurant_more_fragment_tv_comments:
                CommentsMoreFragment commentsMoreFragment=new CommentsMoreFragment();
                HelperMethod.replace(commentsMoreFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.restaurant_more_fragment_tv_chang_pass:
                RestaurantChangPasswordFragment restaurantChangPasswordFragment=new RestaurantChangPasswordFragment();
                HelperMethod.replace(restaurantChangPasswordFragment,getActivity().getSupportFragmentManager(),R.id.home_cycle_activity_fl_contaner,null,null);
                break;
            case R.id.restaurant_more_fragment_tv_exit:
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.exite);
                Button yes=dialog.findViewById(R.id.apoute_app_fragment_btn_yes);
                Button no=dialog.findViewById(R.id.apoute_app_fragment_btn_no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SharedPreferencesManger.LoadData(getActivity(), USER_TYPE).equals(CLINT)){
                            SharedPreferencesManger.SaveData(getActivity(),CLIENT_DATA,null);
                            SharedPreferencesManger.SaveData(getActivity(),CLINT_PASSWORD,null);
                            SharedPreferencesManger.SaveData(getActivity(),CLINT_EMAIL,null);

                        }else{
                            SharedPreferencesManger.SaveData(getActivity(),RESTAURANT_DATA,null);
                            SharedPreferencesManger.SaveData(getActivity(),RESTAURANT_PASSWORD,null);
                            SharedPreferencesManger.SaveData(getActivity(),RESTAURANT_EMAIL,null);
                        }
                        baseActivity.startActivity(new Intent(baseActivity, UseaCycleActivity.class));
                        baseActivity.finish();
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
