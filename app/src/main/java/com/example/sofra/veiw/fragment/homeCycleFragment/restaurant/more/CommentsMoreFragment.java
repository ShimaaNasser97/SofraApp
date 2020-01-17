package com.example.sofra.veiw.fragment.homeCycleFragment.restaurant.more;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.CommentsAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.restaurant_reviews.RestaurantReviews;
import com.example.sofra.data.model.restaurant_reviews.RestaurantReviewsData;
import com.example.sofra.data.model.restaurants.RestauranrsData;
import com.example.sofra.data.model.restaurants.Restaurants;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails.RestaurantDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class CommentsMoreFragment extends BaseFragment {

    @BindView(R.id.comments_more_fragment_rv_recycle)
    RecyclerView commentsMoreFragmentRvRecycle;
    Unbinder unbinder;
    private CommentsAdapter commentsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<RestaurantReviewsData> commentList=new ArrayList<>();
    private Integer maxPage=1;
    private OnEndLess onEndLess;
   // private RestauranrsData restaurants;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments_more, container, false);
        unbinder = ButterKnife.bind(this, view);
        init(1);
        return view;
    }

    private void init(int page) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        commentsMoreFragmentRvRecycle.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        previous_page = current_page;
                        getComment(current_page);
                    } else {
                        onEndLess.current_page = previous_page;
                    }
                } else {
                    onEndLess.current_page = previous_page;
                }

            }
        };
        commentsMoreFragmentRvRecycle.addOnScrollListener(onEndLess);
        commentsAdapter = new CommentsAdapter(getActivity(), getActivity(), commentList);
        commentsMoreFragmentRvRecycle.setAdapter(commentsAdapter);
        getComment(1);
       /* commentList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(getActivity(), getActivity(), commentList);
        previous_page = 1;
        onEndLess.current_page = 1;
        onEndLess.previousTotal = 0;*/
    }

    private void getComment(int page) {
        getClient().getRestaurantReviews(SharedPreferencesManger.loadRestaurantData(getActivity()).getUser().getId(), page).enqueue(new Callback<RestaurantReviews>() {
            @Override
            public void onResponse(Call<RestaurantReviews> call, Response<RestaurantReviews> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage=response.body().getData().getLastPage();
                        commentList.addAll(response.body().getData().getData());
                        commentsAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<RestaurantReviews> call, Throwable t) {

            }
        });    }

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
