package com.example.mystore.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mystore.BaseActivity;
import com.example.mystore.MainActivity;
import com.example.mystore.R;
import com.example.mystore.dto.category.CategoryCreateDTO;
import com.example.mystore.dto.category.CategoryItemDTO;
import com.example.mystore.service.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    private TextInputLayout tfCategoryName;
    private TextInputLayout tfCategoryImage;
    private TextInputLayout tfCategoryDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);
        tfCategoryName=findViewById(R.id.tfCategoryName);
        tfCategoryImage=findViewById(R.id.tfCategoryImage);
        tfCategoryDescription=findViewById(R.id.tfCategoryDescription);
    }

    public void onClickAddCategory(View view) {
        String name = tfCategoryName.getEditText().getText().toString();
        String image = tfCategoryImage.getEditText().getText().toString();
        String description = tfCategoryDescription.getEditText().getText().toString();
//        Log.d("myapp", "------Нажали кнопку---------");
//        Log.d("myapp", "------Name---------"+ name);
//        Log.d("myapp", "------Image--------"+image);
//        Log.d("myapp", "------Description--------"+description);

        CategoryCreateDTO dto = new CategoryCreateDTO();
        dto.setName(name);
        dto.setImage(image);
        dto.setDescription(description);
        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                .create(dto)
                .enqueue(new Callback<CategoryItemDTO>() {
                    @Override
                    public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                        if(response.isSuccessful()) {
                            CategoryItemDTO result = response.body();
                            //Log.i("myapp", "----Crate category id ="+result.getId());
                            Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryItemDTO> call, Throwable t) {

                    }
                });

    }
}