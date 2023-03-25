package com.limdod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.limdod.databinding.ActivityBuyBinding;
import com.limdod.databinding.BottomSheetCheckoutBinding;
import com.limdod.models.Product;

import java.util.Locale;

public class BuyActivity extends AppCompatActivity
{
    ActivityBuyBinding binding;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buy);
        product = (Product) getIntent().getSerializableExtra("product");

        Glide.with(this)
                .load(product.getImageUrl())
                .into(binding.ivProduct);



        binding.tvUsername.setText(product.getPostedBy().getName());

        String[] initials = product.getPostedBy().getName().split(" ");
        String initialToDisplay = "";

        if(initials.length > 1)
        {
            initialToDisplay = initials[0].charAt(0) + "" + initials[1].charAt(0);
        }
        else
        {
            initialToDisplay = String.valueOf(initials[0].charAt(0));
        }

        binding.tvInitials.setText(initialToDisplay.toUpperCase(Locale.ROOT));

        binding.tvEmail.setText(product.getPostedBy().getEmail());

        binding.tvName.setText(product.getName());
        binding.tvDescription.setText(product.getDescription());
        binding.tvPrice.setText("$" + product.getPrice());


        binding.btnBuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                loadBottomSheet();
            }
        });

    }

    private void loadBottomSheet()
    {
        BottomSheetDialog dialog = new BottomSheetDialog(this);

        BottomSheetCheckoutBinding sheetBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.bottom_sheet_checkout,
                null,
                false);

        dialog.setContentView(sheetBinding.getRoot());

        sheetBinding.tvTotalPrice.setText("$" + product.getPrice());


        sheetBinding.btnBuy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ProgressDialog progressDialog = new ProgressDialog(BuyActivity.this);
                progressDialog.setMessage("Processing");
                progressDialog.show();


                FirebaseDatabase.getInstance().getReference()
                        .child("products")
                        .child(product.getId())
                        .child("status")
                        .setValue("sold")
                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void unused)
                            {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .child(product.getPostedBy().getId())
                                        .child("my_products")
                                        .child(product.getId())
                                        .child("status")
                                        .setValue("sold");


                                FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .child("my_orders")
                                        .push()
                                        .setValue(product)
                                        .addOnSuccessListener(new OnSuccessListener<Void>()
                                        {
                                            @Override
                                            public void onSuccess(Void unused)
                                            {
                                                dialog.dismiss();

                                                Intent intent = new Intent(BuyActivity.this, OrderSuccessScreen.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }
                                        });
                            }
                        });


            }
        });

        dialog.show();
    }
}