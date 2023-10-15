package com.example.mystore.network;

import com.example.mystore.dto.category.CategoryCreateDTO;
import com.example.mystore.dto.category.CategoryItemDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CategorriesApi {
    @POST("/api/categories/create")
    public Call<CategoryItemDTO> create(@Body CategoryCreateDTO model);
}
