package com.example.wallpaperapp.Listeners;

import com.example.wallpaperapp.Models.CuratedApiResponse;

public interface CuratedResponseListener {
    void onFetch(CuratedApiResponse response, String message);
    void onError(String message);

}
