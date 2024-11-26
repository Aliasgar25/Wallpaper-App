package com.example.wallpaperapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wallpaperapp.Listeners.CuratedResponseListener;
import com.example.wallpaperapp.Listeners.SearchResponseListener;
import com.example.wallpaperapp.Models.CuratedApiResponse;
import com.example.wallpaperapp.Models.CuratedApiResponse;
import com.example.wallpaperapp.Models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.pexels.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getCuratedWallpapers(CuratedResponseListener listener, String page){
        CallWallpaperList callWallpaperlist = retrofit.create(CallWallpaperList.class);
        Call<CuratedApiResponse> call = callWallpaperlist.getWallpapers(page, "20");

        call.enqueue(new Callback<CuratedApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<CuratedApiResponse> call, @NonNull Response<CuratedApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "An Error occurred!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(), response.message());

            }

            @Override
            public void onFailure(@NonNull Call<CuratedApiResponse> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchCuratedWallpapers(SearchResponseListener listener, String page, String query){
        CallWallpaperListSearch callWallpaperListSearch = retrofit.create(CallWallpaperListSearch.class);
        Call<SearchApiResponse> call = callWallpaperListSearch.SearchWallpapers(query, page, "20");

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchApiResponse> call, @NonNull Response<SearchApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "An Error occurred!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(), response.message());

            }

            @Override
            public void onFailure(@NonNull Call<SearchApiResponse> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }



    private interface CallWallpaperList {
        @Headers({
                "Accept: application/json",
                "Authorization: PerLbPWSH9IDZbu3xflpKRo3as0npAdsGsYseKMeGpOp71FWzyxtWrKk"
        })
        @GET("curated/")
        Call<CuratedApiResponse> getWallpapers(
                @Query("page")String page,
                @Query("per_page")String per_page
        );
    }

    private interface CallWallpaperListSearch {
        @Headers({
                "Accept: application/json",
                "Authorization: PerLbPWSH9IDZbu3xflpKRo3as0npAdsGsYseKMeGpOp71FWzyxtWrKk"
        })
        @GET("search")
        Call<SearchApiResponse> SearchWallpapers(
                @Query("query")String query,
                @Query("page")String page,
                @Query("per_page")String per_page
        );
    }


}
