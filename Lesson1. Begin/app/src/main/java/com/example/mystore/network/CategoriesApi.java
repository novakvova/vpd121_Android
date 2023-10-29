package com.example.mystore.network;

import com.example.mystore.dto.category.CategoryCreateDTO;
import com.example.mystore.dto.category.CategoryItemDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CategoriesApi {
    @Multipart
    @POST("/api/categories/create")
    public Call<CategoryItemDTO> create(@Part CategoryCreateDTO model);

    @GET("/api/categories/list")
    public Call<List<CategoryItemDTO>> list();
}
