package com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.CategoryClientAdapter;
import com.example.sofra.adapter.ItemAdapter;
import com.example.sofra.data.model.categories.Categories;
import com.example.sofra.data.model.items.Items;
import com.example.sofra.data.model.items.ItemsData;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategories;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategoriesData;
import com.example.sofra.data.model.restaurant_my_items2.RestaurantMyItemsPagenation;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.veiw.activity.BaseActivity;
import com.example.sofra.veiw.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;


public class MinuFragment extends BaseFragment {
    @BindView(R.id.minu_fragment_rv_items)
    RecyclerView minuFragmentRvItems;
    Unbinder unbinder;
    @BindView(R.id.minu_fragment_rv_category)
    RecyclerView minuFragmentRvCategory;
    private ItemAdapter itemAdapter;
    private List<ItemsData> itemList = new ArrayList<>();
    private List<RestaurantMyCategoriesData> categoryList=new ArrayList<>();
    private CategoryClientAdapter categoryAapter;
    private LinearLayoutManager linearLayoutManager;

    public int category = -1;
    private OnEndLess onEndless;
    private int maxPage = 1;

    public MinuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_minu, container, false);
        unbinder = ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        minuFragmentRvItems.setLayoutManager(linearLayoutManager);
        onEndless = new OnEndLess(linearLayoutManager, 1) {

            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndless.previous_page = current_page;
                         getItems(current_page);

                    } else {
                        onEndless.current_page = onEndless.previous_page;
                    }
                } else {
                    onEndless.current_page = onEndless.previous_page;
                }
            }
        };
        itemAdapter = new ItemAdapter(getActivity(), baseActivity, itemList);
        minuFragmentRvItems.setAdapter(itemAdapter);

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
        minuFragmentRvCategory.setLayoutManager(linearLayoutManager);

        categoryAapter=new CategoryClientAdapter(getActivity(),baseActivity,categoryList,this);
        minuFragmentRvCategory.setAdapter(categoryAapter);
        getCategory();
        getItems(1);

        return view;
    }

    private void getCategory() {
        getClient().getClientCategories(String.valueOf(RestaurantDetailsFragment.restaurant.getId())).enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        categoryList.addAll(response.body().getData());
                        categoryAapter.notifyDataSetChanged();
                    }
                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(TAG, "onResponse:");
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    public void getItems(final int page) {

        getClient().getItems(String.valueOf(RestaurantDetailsFragment.restaurant.getId()), category ,page).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        if (page == 1) {
                            onEndless.current_page = 1;
                            onEndless.firstVisibleItem= 0;
                            onEndless.previousTotal = 0;
                            onEndless.visibleItemCount = 0;
                            onEndless.previous_page = 1;
                            itemList = new ArrayList<>();
                            itemAdapter = new ItemAdapter(getContext(),baseActivity,itemList);
                            minuFragmentRvItems.setAdapter(itemAdapter);
                        }
                        maxPage = response.body().getData().getLastPage();
                        itemList.addAll(response.body().getData().getData());
                        itemAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }
            @Override
            public void onFailure(Call<Items> call, Throwable t) {

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
}
