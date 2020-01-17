package com.example.sofra.adapter;

import android.app.Dialog;
import android.content.Context;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_delete_category.RestaurantDeleteCategory;
import com.example.sofra.data.model.restaurant_login.RestaurantLogin;
import com.example.sofra.data.model.restaurant_login.RestaurantLoginData;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategories;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategoriesData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.MyItemFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<RestaurantMyCategoriesData> restaurantMyCategoriesDataList = new ArrayList<>();
    private ArrayList<AlbumFile> imagesList=new ArrayList<>();
    private String path;
    private Dialog dialog;


    public CategoryAdapter(Context context, BaseActivity activity, List<RestaurantMyCategoriesData> restaurantMyCategoriesDataList) {
        this.context = context;
        this.activity = activity;
        this.restaurantMyCategoriesDataList = restaurantMyCategoriesDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home1,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.itemHome1TvText.setText(restaurantMyCategoriesDataList.get(position).getName());
        onLoadImageFromUrl(holder.itemHome1IvImage, restaurantMyCategoriesDataList.get(position).getPhotoUrl(), context);
    }

    private void setAction(final ViewHolder holder, final int position) {
        holder.itemHome1RvRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyItemFragment myItemFragment = new MyItemFragment();
                myItemFragment.myCategory = restaurantMyCategoriesDataList.get(position);
                HelperMethod.replace(myItemFragment, activity.getSupportFragmentManager(), R.id.home_cycle_activity_fl_contaner, null, null);
            }
        });
        holder.itemHome1BtnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog=new Dialog(activity);
                dialog.setContentView(R.layout.dialog);
                final CircleImageView photo = dialog.findViewById(R.id.my_category_fragment_iv_image);
                EditText name = dialog.findViewById(R.id.my_category_fragment_et_name);
                Button add = dialog.findViewById(R.id.my_category_fragment_btn_added);
                photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HelperMethod.openGallery(context, 1, imagesList, new Action<ArrayList<AlbumFile>>() {
                            @Override
                            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                                imagesList.clear();
                                imagesList = result;
                                path = imagesList.get(0).getPath();
                                HelperMethod.onLoadImageFromUrl(photo, path, context);
                            }
                        });
                        dialog.show();
                    }
                });
                add.setText(context.getString(R.string.update));
                name.setText(holder.itemHome1TvText.getText());
                onLoadImageFromUrl(photo,restaurantMyCategoriesDataList.get(position).getPhotoUrl(),context);
                dialog.show();
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getClient().getRestaurantUpdateCategory(convertToRequestBody(restaurantMyCategoriesDataList.get(position).getName()),convertFileToMultipart(path,"photo"),convertToRequestBody(SharedPreferencesManger.loadRestaurantData(activity).getApiToken()),convertToRequestBody( String.valueOf(restaurantMyCategoriesDataList.get(position).getId()))).enqueue(new Callback<RestaurantMyCategories>() {
                            @Override
                            public void onResponse(Call<RestaurantMyCategories> call, Response<RestaurantMyCategories> response) {
                                try {
                                    if (response.body().getStatus()==1) {
                                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){

                                }
                            }
                            @Override
                            public void onFailure(Call<RestaurantMyCategories> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        });
        holder.itemHome1BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClient().getRestaurantDeleteCategory(SharedPreferencesManger.loadRestaurantData(activity).getApiToken(),restaurantMyCategoriesDataList.get(position).getId().toString()).enqueue(new Callback<RestaurantDeleteCategory>() {
                    @Override
                    public void onResponse(Call<RestaurantDeleteCategory> call, Response<RestaurantDeleteCategory> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                restaurantMyCategoriesDataList.remove(position);
                            }
                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantDeleteCategory> call, Throwable t) {

                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return restaurantMyCategoriesDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home1_iv_image)
        ImageView itemHome1IvImage;
        @BindView(R.id.item_home1_tv_text)
        TextView itemHome1TvText;
        @BindView(R.id.item_home1_btn_edit)
        Button itemHome1BtnEdit;
        @BindView(R.id.item_home1_btn_delete)
        Button itemHome1BtnDelete;
        @BindView(R.id.item_home1_rv_relative)
        RelativeLayout itemHome1RvRelative;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
