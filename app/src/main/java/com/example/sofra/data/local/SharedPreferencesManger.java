package com.example.sofra.data.local;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.service.autofill.UserData;
import android.support.annotation.RequiresApi;

import com.example.sofra.data.model.client_login.ClientLoginData;
import com.example.sofra.data.model.restaurant_login.RestaurantLoginData;
import com.google.gson.Gson;


public class SharedPreferencesManger {

    private static SharedPreferences sharedPreferences = null;
    public static String USER_DATA = "USER_DATA";
    public static String CLINT_PASSWORD = "CLINT_PASSWORD";
    public static String CLINT_EMAIL = "CLINT_EMAIL";

    public static String RESTAURANT_PASSWORD = "RESTAURANT_PASSWORD";
    public static String RESTAURANT_EMAIL = "RESTAURANT_EMAIL";

    public static String RESTAURANT_ITEM = "RESTAURANT_ITEM";
    public static String ADD = "ADD";
    public static String UPDATE = "UPDATE";

    public static String USER_TYPE = "USER_TYPE";
    public static String CLINT = "CLINT";
    public static String RESTAURANT = "RESTAURANT";

    public static String CLIENT_DATA = "CLIENT_DATA";
    public static String RESTAURANT_DATA = "RESTAURANT_DATA";

    public static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                    "Blood", activity.MODE_PRIVATE);
        }
    }

    public static void SaveData(Activity activity, String data_Key, String data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void SaveData(Activity activity, String data_Key, boolean data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static String LoadData(Activity activity, String data_Key) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getString(data_Key, null);
    }

   /* public static boolean LoadBoolean(Activity activity, String data_Key) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getBoolean(data_Key, false);
    }*/

    public static void SaveData(Activity activity, String data_Key, Object data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String StringData = gson.toJson(data_Value);
            editor.putString(data_Key, StringData);
            editor.commit();
        }
    }

    public static void saveUserData(Activity activity, UserData userData) {
        SaveData(activity, USER_DATA, userData);
    }

    public static ClientLoginData loadClientData(Activity activity) {
        ClientLoginData userData = null;

        Gson gson = new Gson();
        userData = gson.fromJson(LoadData(activity, CLIENT_DATA), ClientLoginData.class);

        return userData;
    }


    public static RestaurantLoginData loadRestaurantData(Activity activity) {
        RestaurantLoginData userData = null;

        Gson gson = new Gson();
        userData = gson.fromJson(LoadData(activity, RESTAURANT_DATA), RestaurantLoginData.class);

        return userData;
    }

    public static void clean(Activity activity) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

}
