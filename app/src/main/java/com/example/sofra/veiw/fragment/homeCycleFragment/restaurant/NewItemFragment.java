package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategoriesData;
import com.example.sofra.data.model.restaurant_my_items2.RestaurantMyItems;
import com.example.sofra.data.model.restaurant_my_items2.RestaurantMyItemsData;
import com.example.sofra.data.model.restaurant_update_item.RestaurantUpdateItem;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.ADD;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_ITEM;
import static com.example.sofra.data.local.SharedPreferencesManger.UPDATE;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;


public class NewItemFragment extends BaseFragment {

    public static RestaurantMyItemsData myItem;

    @BindView(R.id.new_item_fragment_et_name)
    EditText newItemFragmentEtName;
    @BindView(R.id.new_item_fragment_et_description)
    EditText newItemFragmentEtDescription;
    @BindView(R.id.new_item_fragment_et_price)
    EditText newItemFragmentEtPrice;
    @BindView(R.id.new_item_fragment_et_offer)
    EditText newItemFragmentEtOffer;
    Unbinder unbinder;
    @BindView(R.id.new_item_fragment_civ_photo)
    CircleImageView newItemFragmentCivPhoto;
    @BindView(R.id.login_fragment_enter)
    Button loginFragmentEnter;
    private ArrayList<AlbumFile> imagesList = new ArrayList<>();
    private String path;
    private String name,price,offer,description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (SharedPreferencesManger.LoadData(getActivity(),RESTAURANT_ITEM).equals(UPDATE)){
            newItemFragmentEtName.setText(myItem.getName());
            newItemFragmentEtPrice.setText(myItem.getPrice());
            newItemFragmentEtOffer.setText(myItem.getOfferPrice());
            newItemFragmentEtDescription.setText(myItem.getDescription());
            onLoadImageFromUrl(newItemFragmentCivPhoto,myItem.getPhotoUrl(),getActivity());
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

    @OnClick({R.id.new_item_fragment_civ_photo, R.id.login_fragment_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.new_item_fragment_civ_photo:
                HelperMethod.openGallery(getActivity(), 1, imagesList, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        imagesList.clear();
                        imagesList = result;
                        path = imagesList.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(newItemFragmentCivPhoto, path, getActivity());
                    }
                });
                break;
            case R.id.login_fragment_enter:
                  name=newItemFragmentEtName.getText().toString();
                  description=newItemFragmentEtDescription.getText().toString();
                  price=newItemFragmentEtPrice.getText().toString();
                  offer=newItemFragmentEtOffer.getText().toString();
                if (SharedPreferencesManger.LoadData(getActivity(),RESTAURANT_ITEM).equals(ADD)){
                    getNewItem(name,description,price,offer);
                }
                if (SharedPreferencesManger.LoadData(getActivity(),RESTAURANT_ITEM).equals(UPDATE)){
                    getUpdateItem(name,description,price,offer);
                }
                break;
        }
    }
    private void getNewItem(final String name, String description, String price, String offer) {
        getClient().getRestaurantNewItems(convertToRequestBody(description),convertToRequestBody(price),convertToRequestBody(name),convertFileToMultipart(path,"photo"),
                convertToRequestBody(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken()),convertToRequestBody(offer),convertToRequestBody(myItem.getCategoryId())).enqueue(new Callback<RestaurantMyItems>() {
            @Override
            public void onResponse(Call<RestaurantMyItems> call, Response<RestaurantMyItems> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                         }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestaurantMyItems> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUpdateItem(String name, String description, String price, String offer) {
        getClient().getRestaurantUpdateItem(convertToRequestBody(description),convertToRequestBody(price),convertToRequestBody(name),convertFileToMultipart(path,"photo"),
                convertToRequestBody(String.valueOf(myItem.getId())),convertToRequestBody(SharedPreferencesManger.loadRestaurantData(getActivity()).getApiToken()),convertToRequestBody(offer),
                convertToRequestBody(myItem.getCategoryId())).enqueue(new Callback<RestaurantMyItems>() {
            @Override
            public void onResponse(Call<RestaurantMyItems> call, Response<RestaurantMyItems> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestaurantMyItems> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
