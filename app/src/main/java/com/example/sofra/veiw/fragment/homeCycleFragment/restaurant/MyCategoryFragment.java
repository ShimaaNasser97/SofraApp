package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.CategoryAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategories;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategoriesData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;

public class MyCategoryFragment extends BaseFragment {
    @BindView(R.id.my_category_fragment_rv_categorys)
    RecyclerView myCategoryFragmentRvCategorys;
    Unbinder unbinder;
    private CategoryAdapter categoryAdapter;
    private List<RestaurantMyCategoriesData> categoryList = new ArrayList<>();
    private EditText name;
    private String path;
    private ArrayList<AlbumFile> imagesList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    public static Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        myCategoryFragmentRvCategorys.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(getActivity(), (BaseActivity) getActivity(), categoryList);
        myCategoryFragmentRvCategorys.setAdapter(categoryAdapter);

        getCategory();
    }

    private void getCategory() {
        getClient().getRestaurantMyCategories(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken()).enqueue(new Callback<RestaurantMyCategories>() {
            @Override
            public void onResponse(Call<RestaurantMyCategories> call, Response<RestaurantMyCategories> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        categoryList.addAll(response.body().getData().getData());
                        categoryAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyCategories> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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


    @OnClick(R.id.my_category_fragment_btn_add)
    public void onViewClicked() {

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog);
        final CircleImageView photo = dialog.findViewById(R.id.my_category_fragment_iv_image);
        name = dialog.findViewById(R.id.my_category_fragment_et_name);
        Button add = dialog.findViewById(R.id.my_category_fragment_btn_added);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.openGallery(getActivity(), 1, imagesList, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        imagesList.clear();
                        imagesList = result;
                        path = imagesList.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(photo, path, getActivity());
                    }
                });
                dialog.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getNewCategory();
            }
        });
        dialog.show();
    }

    private void getNewCategory() {
        getClient().getRestaurantNewCategory(convertToRequestBody(name.getText().toString()), convertFileToMultipart(path, "photo"), convertToRequestBody(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken())).enqueue(new Callback<RestaurantMyCategories>() {
            @Override
            public void onResponse(Call<RestaurantMyCategories> call, Response<RestaurantMyCategories> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getCategory();
                    }

                } catch (Exception e) {

                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantMyCategories> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
