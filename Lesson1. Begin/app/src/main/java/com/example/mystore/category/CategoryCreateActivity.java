package com.example.mystore.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mystore.BaseActivity;
import com.example.mystore.MainActivity;
import com.example.mystore.R;
import com.example.mystore.application.HomeApplication;
import com.example.mystore.dto.category.CategoryCreateDTO;
import com.example.mystore.dto.category.CategoryItemDTO;
import com.example.mystore.service.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    String filePath;
    private TextInputLayout tfCategoryName;
    private ImageView ivSelectImage;
    private TextInputLayout tfCategoryDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);
        tfCategoryName=findViewById(R.id.tfCategoryName);
        ivSelectImage=findViewById(R.id.ivSelectImage);
        tfCategoryDescription=findViewById(R.id.tfCategoryDescription);

        String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQuLnfPLVTh4KeLNvuyj15nRqSQ71A5yccavrGpwlTX2RJlM-_BD3_yCFALnCyOLHEoz1w&usqp=CAU";
        Glide
                .with(HomeApplication.getAppContext())
                .load(url)
                .apply(new RequestOptions().override(300))
                .into(ivSelectImage);
    }

    public void onClickAddCategory(View view) {
        File file = new File(filePath);
        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
        RequestBody name = RequestBody.create(MultipartBody.FORM, tfCategoryName.getEditText().getText().toString());
        RequestBody description = RequestBody.create(MultipartBody.FORM, tfCategoryDescription.getEditText().getText().toString());

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

    public void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            Uri uri = data.getData();

            Glide
                    .with(HomeApplication.getAppContext())
                    .load(uri)
                    .apply(new RequestOptions().override(300))
                    .into(ivSelectImage);

            // If you want to get the file path from the URI, you can use the following code:
            filePath = getPathFromURI(uri);
        }
    }
    // This method converts the image URI to the direct file system path of the image file
    private String getPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }
}