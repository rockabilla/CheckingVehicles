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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by MishustinAI on 19.12.2016.
 */

public class ItemListActivity extends Activity {
    ListView choiceList;
    TextView selection;

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
