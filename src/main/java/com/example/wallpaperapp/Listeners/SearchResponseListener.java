package com.example.wallpaperapp.Listeners;

import com.example.wallpaperapp.Models.SearchApiResponse;

public interface SearchResponseListener {
    void onFetch(SearchApiResponse response, String message);
    void onError(String message);
}
