package com.piteravto.rockabilla.checkingvehicles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.piteravto.rockabilla.checkingvehicles.api.ServerApi;
import com.piteravto.rockabilla.checkingvehicles.structure.MenuItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * Получаем менюшку, можем фоткать и сразу отправлять и при нажатии на кнопку готово, остылаем список
 */

public class ItemListActivity extends Activity {
    ListView choiceList;

    private String vehicleId;
    private String[] menuItemsName;

    private static int TAKE_PICTURE = 1;
    private Uri mOutputFileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_list);

        try {
            menuItemsName = getIntent().getExtras().getStringArray("menuItemNameList");
            int[] menuItemsValues =  getIntent().getExtras().getIntArray("menuItemValueList");
            vehicleId = getIntent().getExtras().getString("vehicleId");

            if (menuItemsName!=null && menuItemsValues!=null) {

                choiceList = (ListView) findViewById(R.id.listView1);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        R.layout.list_item, menuItemsName);

                choiceList.setAdapter(adapter);

                for (int i = 0; i<menuItemsValues.length; i++)
                {
                    choiceList.setItemChecked(i, menuItemsValues[i] == 1);
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                if (data.hasExtra("data")) {
                    Toast.makeText(ItemListActivity.this, "onActivityResult bad", Toast.LENGTH_LONG).show();
                }
            } else {

                File file = new File(mOutputFileUri.getPath());

                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), decodeFile(file));

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

                String descriptionString = vehicleId;

                RequestBody description =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), descriptionString);
                ServerApi.getApi().uploadImage(getString(R.string.stk), getString(R.string.upload_image), description, body).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(ItemListActivity.this, "send", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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


        return new File (storageDir, imageFileName);
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

    public void onClickDoneButton(View view) {
        SparseBooleanArray chosen = choiceList.getCheckedItemPositions();
        ArrayList<MenuItem> menuItems= new ArrayList<>();
        for (int i = 0; i < chosen.size(); i++) {
            if (chosen.valueAt(i)) {
                //selection+=chosen.valueAt(i) + ", ";
                menuItems.add(new MenuItem(menuItemsName[chosen.keyAt(i)], 1));
            }
        }


        ServerApi.getApi().uploadChosen(getString(R.string.stk), getString(R.string.upload_chosen), menuItems).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    // Decodes image and scales it to reduce memory consumption
    private File decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=1200;

            // Find the correct scale value. It should be the power of 2.
            // rockabilla сделали константой, пока так устраивает
            int scale = 2;
            //while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
            //        o.outHeight / scale / 2 >= REQUIRED_SIZE) {
            //    scale *= 2;
            //}

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 65 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f;
        } catch (FileNotFoundException e) {

        }
        catch (IOException e)
        {

        }
        return null;
    }
}
