package com.piteravto.rockabilla.checkingvehicles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.piteravto.rockabilla.checkingvehicles.api.ServerApi;
import com.piteravto.rockabilla.checkingvehicles.api.ServerApiInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by MishustinAI on 19.12.2016.
 */

public class ItemListActivity extends Activity {
    ListView choiceList;
    TextView selection;

    String path;
    long size;

    private static int TAKE_PICTURE = 1;
    private Uri mOutputFileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_list);

        try {
            String[] menuItemsName = getIntent().getExtras().getStringArray("menuItemNameList");
            int[] menuItemsValues =  getIntent().getExtras().getIntArray("menuItemValueList");
            if (menuItemsName!=null && menuItemsValues!=null) {

                choiceList = (ListView) findViewById(R.id.listView1);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        R.layout.list_item, menuItemsName);

                choiceList.setAdapter(adapter);

                for (int i = 0; i<menuItemsValues.length; i++)
                {
                    choiceList.setItemChecked(i, menuItemsValues[i] == 1);
                }
            }
            else
            {
                Toast.makeText(ItemListActivity.this, "menuItemsName is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(ItemListActivity.this, "ItemListActivity onCreate error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                if (data.hasExtra("data")) {
                    // TODO Какие-то действия с миниатюрой
                    //mImageView.setImageBitmap(thumbnailBitmap);
                    Toast.makeText(ItemListActivity.this, "onActivityResult bad", Toast.LENGTH_LONG).show();
                }
            } else {
                // TODO Какие-то действия с полноценным изображением,
                // сохраненным по адресу mOutputFileUri
                //mImageView.setImageURI(mOutputFileUri);
                Toast.makeText(ItemListActivity.this, "onActivityResult good", Toast.LENGTH_LONG).show();

                File file = new File(mOutputFileUri.getPath());
                path = file.getPath();
                size = file.length();
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

                String descriptionString = "todo vehicleid";

                RequestBody description =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), descriptionString);
                ServerApi.getApi().uploadImage(getString(R.string.stk), getString(R.string.upload_image), description, body).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(ItemListActivity.this, "upload success " + mOutputFileUri.getPath(), Toast.LENGTH_LONG).show();
                        Toast.makeText(ItemListActivity.this, path + " " + size, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ItemListActivity.this, "upload failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public void onClickPhotoButton(View view) {
        saveFullImage();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String imageFileName = "STK_" + timeStamp + ".jpg";

        File image = new File (storageDir, imageFileName);

        return image;
    }

    private void saveFullImage()  {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = null;
        try {
            file = createImageFile();
        } catch (IOException e) {
            Toast.makeText(ItemListActivity.this, "createImageFile error", Toast.LENGTH_LONG).show();
        }
        mOutputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }
}
