package com.example.fetchrewardtask;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
        @GET("hiring.json")
        Call<List<Item>> getItemList();
}
