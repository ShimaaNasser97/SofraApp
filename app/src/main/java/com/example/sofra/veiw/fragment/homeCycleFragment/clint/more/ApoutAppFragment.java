package com.example.sofra.veiw.fragment.homeCycleFragment.clint.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.settings.Settings;
import com.example.sofra.veiw.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class ApoutAppFragment extends BaseFragment {

    @BindView(R.id.apoute_app_fragment_tv_apout)
    TextView apouteAppFragmentTvApout;
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
        View view = inflater.inflate(R.layout.fragment_apout_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        getApout();
        return view;
    }

    private void getApout() {

        getClient().getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                try {
                    if (response.body().getStatus()==1) {
                        apouteAppFragmentTvApout.setText(response.body().getData().getAboutApp());
                         }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
}
