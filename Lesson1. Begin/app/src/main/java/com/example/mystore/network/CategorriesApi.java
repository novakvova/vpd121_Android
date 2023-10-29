package com.example.mystore.network;

import com.example.mystore.dto.category.CategoryCreateDTO;
import com.example.mystore.dto.category.CategoryItemDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategorriesApi {
    @POST("/api/categories/create")
    public Call<CategoryItemDTO> create(@Body CategoryCreateDTO model);

    @GET("/api/categories/list")
    public Call<List<CategoryItemDTO>> list();
}
