package com.limdod.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limdod.R;
import com.limdod.adapters.ProductAdapter;
import com.limdod.databinding.FragmentHomeBinding;
import com.limdod.databinding.FragmentMyProductsBinding;
import com.limdod.models.Product;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

public class MyProductsFragment extends Fragment
{
    FragmentMyProductsBinding binding;
    ArrayList<Product> products;
    ProgressDialog dialog;


    @Override
    public void onResume()
    {
        super.onResume();
        loadProducts();
    }

    private void loadProducts()
    {
        dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Loading products");
        dialog.show();

        products = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference()
                .child("products")
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        dialog.dismiss();

                        if(snapshot.exists())
                        {
                            for(DataSnapshot productJson: snapshot.getChildren())
                            {
                                Product product = productJson.getValue(Product.class);
                                if((product.getPostedBy().getId().equals(FirebaseAuth.getInstance().getUid())))
                                {
                                    products.add(product);
                                }
                            }


                            binding.productList.setAdapter(new ProductAdapter(requireContext(), products,"product"));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_products, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.productList.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.productList.setHasFixedSize(true);


    }

}
