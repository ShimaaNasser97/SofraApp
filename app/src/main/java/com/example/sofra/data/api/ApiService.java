package com.example.sofra.data.api;

import com.example.sofra.data.model.categories.Categories;
import com.example.sofra.data.model.client_change_password.ClientChangePassword;
import com.example.sofra.data.model.client_confirm_order.ClientConfirmOrder;
import com.example.sofra.data.model.client_login.ClientLogin;
import com.example.sofra.data.model.client_my_orders.ClientMyOrders;
import com.example.sofra.data.model.client_new_order.Client;
import com.example.sofra.data.model.client_new_order.ClientNewOrder;
import com.example.sofra.data.model.client_notifications.ClientNotifications;
import com.example.sofra.data.model.client_restaurant_review.ClientRestaurantReview;
import com.example.sofra.data.model.client_show_order.ClientShowOrder;
import com.example.sofra.data.model.client_sign_up.ClientSignUp;
import com.example.sofra.data.model.contact.Contact;
import com.example.sofra.data.model.generalResponce.GeneralResponce;
import com.example.sofra.data.model.items.Items;
import com.example.sofra.data.model.new_password.NewPassword;
import com.example.sofra.data.model.offers.Offers;
import com.example.sofra.data.model.reset_password.ResetPassword;
import com.example.sofra.data.model.restaurant_accept_order.RestaurantAcceptOrder;
import com.example.sofra.data.model.restaurant_change_password.RestaurantChangePassword;
import com.example.sofra.data.model.restaurant_confirm_order.RestaurantConfirmOrder;
import com.example.sofra.data.model.restaurant_delete_category.RestaurantDeleteCategory;
import com.example.sofra.data.model.restaurant_delete_item.RestaurantDeleteItem;
import com.example.sofra.data.model.restaurant_delete_offer.RestaurantDeleteOffer;
import com.example.sofra.data.model.restaurant_login.RestaurantLogin;
import com.example.sofra.data.model.restaurant_my_categories.RestaurantMyCategories;
import com.example.sofra.data.model.restaurant_my_items2.RestaurantMyItems;
import com.example.sofra.data.model.restaurant_my_offers.RestaurantMyOffers;
import com.example.sofra.data.model.restaurant_my_orders.RestaurantMyOrders;
import com.example.sofra.data.model.restaurant_new_category.RestaurantNewCategory;
import com.example.sofra.data.model.restaurant_notifications.RestaurantNotifications;
import com.example.sofra.data.model.restaurant_profile.RestaurantProfile;
import com.example.sofra.data.model.restaurant_reject_order.RestaurantRejectOrder;
import com.example.sofra.data.model.restaurant_reviews.RestaurantReviews;
import com.example.sofra.data.model.restaurants.Restaurants;
import com.example.sofra.data.model.settings.Settings;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @GET("cities-not-paginated")
    Call<GeneralResponce> getCitesNoPaginated();

    @GET("regions-not-paginated")
    Call<GeneralResponce> getRegionsNoPaginated(@Query("city_id") int cityId);


    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestaurantLogin> getRestaurantLogin(@Field("email") String email,
                                             @Field("password") String password);

    @POST("client/login")
    @FormUrlEncoded
    Call<ClientLogin> getClientLogin(@Field("email") String email,
                                     @Field("password") String password);

    @POST("client/sign-up")
    @Multipart
    Call<ClientSignUp> getClientResister(@Part("name") RequestBody name,
                                         @Part("email") RequestBody email,
                                         @Part("password") RequestBody password,
                                         @Part("password_confirmation") RequestBody passwordConfirmation,
                                         @Part("phone") RequestBody phone,
                                         @Part("region_id") RequestBody regionId,
                                         @Part MultipartBody.Part profile_image);

    @POST("restaurant/sign-up")
    @Multipart
    Call<RestaurantLogin> getRestaurantSinup(@Part("name") RequestBody name,
                                             @Part("email") RequestBody email,
                                             @Part("password") RequestBody password,
                                             @Part("password_confirmation") RequestBody passwordConfirmation,
                                             @Part("phone") RequestBody phone,
                                             @Part("whatsapp") RequestBody whatsapp,
                                             @Part("region_id") RequestBody regionId,
                                             @Part("delivery_cost") RequestBody deliveryCost,
                                             @Part("minimum_charger") RequestBody minimumCharger,
                                             @Part MultipartBody.Part photo,
                                             @Part("delivery_time") RequestBody delivery_time);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> getResetPassword(@Field("email") String email);

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<NewPassword> getNewPassword(@Field("code") String code,
                                     @Field("password") String password,
                                     @Field("password_confirmation") String passwordConfirmation);

    @GET("categories")
    Call<Categories> getClientCategories(@Query("restaurant_id") String restaurant_id);


    @POST("restaurant/new-category")
    @Multipart
    Call<RestaurantMyCategories> getRestaurantNewCategory(@Part("name") RequestBody name,
                                                          @Part MultipartBody.Part photo,
                                                          @Part("api_token") RequestBody apiToken);


    @GET("restaurant/my-items")
    Call<RestaurantMyItems> getRestaurantMyItems(@Query("api_token") String apiToken,
                                                 @Query("category_id") int categoryId,
                                                 @Query("page") int page);

    @POST("restaurant/new-item")
    @Multipart
    Call<RestaurantMyItems> getRestaurantNewItems(@Part("description") RequestBody description,
                                                  @Part("price") RequestBody price,
                                                  @Part("name") RequestBody name,
                                                  @Part MultipartBody.Part photo,
                                                  @Part("api_token") RequestBody apiToken,
                                                  @Part("offer_price") RequestBody offerPrice,
                                                  @Part("category_id") RequestBody categoryId);

    @POST("restaurant/update-item")
    @Multipart
    Call<RestaurantMyItems> getRestaurantUpdateItem(@Part("description") RequestBody description,
                                                    @Part("price") RequestBody price,
                                                    @Part("name") RequestBody name,
                                                    @Part MultipartBody.Part photo,
                                                    @Part("item_id") RequestBody itemId,
                                                    @Part("api_token") RequestBody apiToken,
                                                    @Part("offer_price") RequestBody offerPrice,
                                                    @Part("category_id") RequestBody categoryId);


    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<RestaurantDeleteItem> getRestaurantDeleteItem(@Field("item_id") String item_id,
                                                       @Field("api_token") String apiToken);

    @GET("client/show-order")
    Call<ClientMyOrders> getClientMyOrders(@Query("api_token") String apiToken,
                                           @Query("order_id") int orderId);

    @GET("restaurants")
    Call<Restaurants> getRestaurants(@Query("page") int page);


    @GET("restaurants")
    Call<Restaurants> getRestaurants(@Query("keyword") String keyword,
                                     @Query("region_id") int regionId);

    @GET("restaurant/my-categories")
    Call<RestaurantMyCategories> getRestaurantMyCategories(@Query("api_token") String apiToken);

    @GET("items")
    Call<Items> getItems(@Query("restaurant_id") String restaurantId,
                         @Query("category_id") int categoryId,
                         @Query("page") int page);

    @POST("client/new-order")
    @FormUrlEncoded
    Call<ClientNewOrder> getNewOrder(@Field("restaurant_id") String restaurantId,
                                     @Field("note") String note,
                                     @Field("address") String address,
                                     @Field("payment_method_id") String paymentMethodId,
                                     @Field("phone") String phone,
                                     @Field("name") String name,
                                     @Field("api_token") String apiToken,
                                     @Field("items[]") List<String> items,
                                     @Field("quantities[]") List<String> quantities,
                                     @Field("notes[]") List<String> notes);

    @GET("client/my-orders")
    Call<ClientMyOrders> getClientMyOrders(@Query("api_token") String apiToken,
                                           @Query("state") String state,
                                           @Query("page") int page);

    @GET("restaurant/my-orders")
    Call<RestaurantMyOrders> getRestaurantMyOrders(@Query("api_token") String apiToken,
                                                   @Query("state") String state,
                                                   @Query("page") int page);

    @GET("client/show-order")
    Call<ClientShowOrder> getClintShowOrder(@Query("api_token") String apiToken,
                                            @Query("order_id") int orderId);

    @GET("restaurant/reviews")
    Call<RestaurantReviews> getRestaurantReviews(
            @Query("restaurant_id") int restaurantId,
            @Query("page") int page);

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<ClientRestaurantReview> getAddReviews(@Field("rate") String rate,
                                               @Field("comment") String comment,
                                               @Field("restaurant_id") String restaurantId,
                                               @Field("api_token") String apiToken);

    @POST("restaurant/update-category")
    @Multipart
    Call<RestaurantMyCategories> getRestaurantUpdateCategory(@Part("name") RequestBody name,
                                                             @Part MultipartBody.Part photo,
                                                             @Part("api_token") RequestBody apiToken,
                                                             @Part("category_id") RequestBody categoryId);

    @POST("restaurant/delete-category")
    @FormUrlEncoded
    Call<RestaurantDeleteCategory> getRestaurantDeleteCategory(@Field("api_token") String apiToken,
                                                               @Field("category_id") String categoryId);

    @POST("restaurant/delete-offer")
    @FormUrlEncoded
    Call<RestaurantDeleteOffer> getRestaurantDeleteOffer(@Field("offer_id") String offerId,
                                                         @Field("api_token") String apiToken);

    @GET("settings")
    Call<Settings> getSettings();

    @POST("client/change-password")
    @FormUrlEncoded
    Call<ClientChangePassword> getClientChangePassword(@Field("api_token") String apiToken,
                                                       @Field("old_password") String oldPassword,
                                                       @Field("password") String password,
                                                       @Field("password_confirmation") String passwordConfirmation);

    @POST("restaurant/change-password")
    @FormUrlEncoded
    Call<RestaurantChangePassword> getRestaurantChangePassword(@Field("api_token") String apiToken,
                                                               @Field("old_password") String oldPassword,
                                                               @Field("password") String password,
                                                               @Field("password_confirmation") String passwordConfirmation);


    @GET("offers")
    Call<Offers> getClientOffers(@Query("page") int page);

    @POST("contact")
    @FormUrlEncoded
    Call<Contact> getContact(@Field("name") String name,
                             @Field("email") String email,
                             @Field("phone") String phone,
                             @Field("type") String type,
                             @Field("content") String content);

    @GET("client/notifications")
    Call<ClientNotifications> getClientNotifications(@Query("api_token") String apiToken);

    @GET("restaurant/notifications")
    Call<RestaurantNotifications> getRestauranNotifications(@Query("api_token") String apiToken);

    @POST("restaurant/new-offer")
    @Multipart
    Call<RestaurantMyOffers> getRestaurantNewOffer(@Part("description") RequestBody description,
                                                   @Part("price") RequestBody price,
                                                   @Part("starting_at") RequestBody startingAt,
                                                   @Part("name") RequestBody name,
                                                   @Part MultipartBody.Part photo,
                                                   @Part("ending_at") RequestBody endingAt,
                                                   @Part("api_token") RequestBody apiToken);

    @POST("restaurant/update-offer")
    @Multipart
    Call<RestaurantMyOffers> getRestaurantUpdateOffer(@Part("description") RequestBody description,
                                                      @Part("price") RequestBody price,
                                                      @Part("starting_at") RequestBody startingAt,
                                                      @Part("name") RequestBody name,
                                                      @Part MultipartBody.Part photo,
                                                      @Part("ending_at") RequestBody endingAt,
                                                      @Part("api_token") RequestBody apiToken);


    @GET("restaurant/my-offers")
    Call<RestaurantMyOffers> getRestaurantMyOffers(@Query("api_token") String apiToken,
                                                   @Query("page") int page);

    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<RestaurantAcceptOrder> getRestaurantAcceptOrder(@Field("api_token") String apiToken,
                                                         @Field("order_id") String orderId);

    @POST("restaurant/reject-order")
    @FormUrlEncoded
    Call<RestaurantRejectOrder> getRestaurantRejectOrder(@Field("api_token") String apiToken,
                                                         @Field("order_id") String orderId,
                                                         @Field("refuse_reason") String refuseReason);

    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<RestaurantConfirmOrder> getRestaurantConfirmOrder(@Field("api_token") String apiToken,
                                                           @Field("order_id") String orderId);

    @POST("restaurant/profile")
    @Multipart
    Call<RestaurantProfile> getRestaurantProfile(@Part("email") RequestBody email,
                                                 @Part("name") RequestBody name,
                                                 @Part("phone") RequestBody phone,
                                                 @Part("region_id") RequestBody regionId,
                                                 @Part("delivery_cost") RequestBody deliveryCost,
                                                 @Part("minimum_charger") RequestBody minimumCharger,
                                                 @Part("availability") RequestBody availability,
                                                 @Part MultipartBody.Part photo,
                                                 @Part("api_token") RequestBody apiToken,
                                                 @Part("delivery_time") RequestBody deliveryTime,
                                                 @Part("whatapp") RequestBody whatapp);

    @POST("client/confirm-order")
    @FormUrlEncoded
    Call<ClientConfirmOrder> getClientConfirmOrder(@Field("order_id") String orderId,
                                                   @Field("api_token") String apiToken);
}