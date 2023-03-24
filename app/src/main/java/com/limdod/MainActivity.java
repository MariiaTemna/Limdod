package com.limdod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.navigation.NavigationBarView;
import com.limdod.databinding.ActivityMainBinding;
import com.limdod.fragments.AddProductFragment;
import com.limdod.fragments.HomeFragment;
import com.limdod.fragments.ProfileFragment;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding binding;
    AddProductFragment addProductFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        displayFragment(new HomeFragment());

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                int id = item.getItemId();

                if(id == R.id.navHome)
                {
                    displayFragment(new HomeFragment());
                }
                else if(id == R.id.navAddProduct)
                {
                    addProductFragment = new AddProductFragment();
                    displayFragment(addProductFragment);
                }
                else if(id == R.id.navProfile)
                {
                    displayFragment(new ProfileFragment());
                }

                return true;
            }
        });

    }

    public void displayFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootLayout, fragment)
                .addToBackStack("")
                .commit();
    }

    public void moveToHome()
    {
        binding.bottomNav.setSelectedItemId(R.id.navHome);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {


            if(data != null)
            {
                Uri uri = data.getData();
                try
                {
                    Bitmap bitmap = MediaStore
                            .Images
                            .Media
                            .getBitmap(
                                    getContentResolver(),
                                    uri);

                    addProductFragment.setImage(bitmap, uri);

                } catch (IOException e)
                {
                    Log.d("MLK", e.getMessage());
                    Toast.makeText(this, "Couldn't send image, Please try again", Toast.LENGTH_SHORT).show();
                }

            }


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}