package com.example.sofra.veiw.fragment.homeCycleFragment.clint.restaurantDetails;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.CommentsAdapter;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.client_restaurant_review.ClientRestaurantReview;
import com.example.sofra.data.model.restaurant_reviews.Restaurant;
import com.example.sofra.data.model.restaurant_reviews.RestaurantReviews;
import com.example.sofra.data.model.restaurant_reviews.RestaurantReviewsData;
import com.example.sofra.data.model.restaurants.RestauranrsData;
import com.example.sofra.data.model.restaurants.Restaurants;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.veiw.fragment.BaseFragment;
import com.example.sofra.veiw.fragment.homeCycleFragment.clint.RestaurantsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class CommentsFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.comment_fragment_rv_comments)
    RecyclerView commentFragmentRvComments;

    private List<RestaurantReviewsData> commentList = new ArrayList<>();
    private CommentsAdapter commentsAdapter;
    private OnEndLess onEndLess;
    private int maxPage = 0;
    private int previous_page;
    private String rate="1";
    private LinearLayoutManager linearLayoutManager;
    private ImageView like,veryHappy,huppy,sad,angry;
    private EditText comment;
    private Button add;
    private ImageView v;
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
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        unbinder = ButterKnife.bind(this, view);


        init(1);
        return view;
    }

    private void init(int page) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        commentFragmentRvComments.setLayoutManager(linearLayoutManager);
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
        commentFragmentRvComments.addOnScrollListener(onEndLess);
        commentsAdapter = new CommentsAdapter(getActivity(), getActivity(), commentList);
        commentFragmentRvComments.setAdapter(commentsAdapter);
        getComment(1);
    }

    private void getComment(int page) {
        getClient().getRestaurantReviews(RestaurantDetailsFragment.restaurant.getId(), page).enqueue(new Callback<RestaurantReviews>() {
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

    @OnClick({R.id.comment_fragment_btn_add})
    public void onViewClicked(View view) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.comment_daialog);
        ImageView like=dialog.findViewById(R.id.comment_fragment_iv_like);
        ImageView veryHappy=dialog.findViewById(R.id.comment_fragment_iv_very_happy);
        ImageView happy=dialog.findViewById(R.id.comment_fragment_iv_happy);
        ImageView sad=dialog.findViewById(R.id.comment_fragment_iv_sad);
        ImageView angry=dialog.findViewById(R.id.comment_fragment_iv_angry);
         comment=dialog.findViewById(R.id.comment_fragment_et_comment);

         rate="3";
         add=dialog.findViewById(R.id.comment_fragment_btn_add_comment);
         add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getClient().getAddReviews(rate, comment.getText().toString(), String.valueOf(RestaurantDetailsFragment.restaurant.getId()), SharedPreferencesManger.loadClientData(getActivity()).getApiToken()).enqueue(new Callback<ClientRestaurantReview>() {
                     @Override
                     public void onResponse(Call<ClientRestaurantReview> call, Response<ClientRestaurantReview> response) {
                         try {
                             if (response.body().getStatus() == 1) {
                                 Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                             }else
                                 Toast.makeText(baseActivity, "لايمكنك التعليق", Toast.LENGTH_SHORT).show();

                             dialog.cancel();
                         } catch (Exception e) {
                             Toast.makeText(baseActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }

                     @Override
                     public void onFailure(Call<ClientRestaurantReview> call, Throwable t) {
                         Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
             }
         });
         dialog.show();
    }

}

