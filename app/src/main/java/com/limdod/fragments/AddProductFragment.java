package com.limdod.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.limdod.MainActivity;
import com.limdod.R;
import com.limdod.databinding.FragmentAddProductBinding;
import com.limdod.models.Product;
import com.limdod.models.User;
import com.limdod.utils.SharedPreferenceManager;

import java.io.File;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class AddProductFragment extends Fragment
{
    Uri image;
    FragmentAddProductBinding binding;
    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        dialog = new ProgressDialog(requireContext());

        binding.btnAddPictures.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ImagePicker.with(requireActivity())
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        binding.btnAddProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(image == null)
                {
                    Toast.makeText(requireContext(), "Please add at least 1 image", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(binding.etTitle.getText().toString().isEmpty())
                {
                    binding.etTitle.setError("Field is required");
                    binding.etTitle.requestFocus();
                    return;
                }

                if(binding.etPrice.getText().toString().isEmpty())
                {
                    binding.etPrice.setError("Field is required");
                    binding.etPrice.requestFocus();
                    return;
                }

                if(binding.etState.getText().toString().isEmpty())
                {
                    binding.etState.setError("Field is required");
                    binding.etState.requestFocus();
                    return;
                }

                if(binding.etDescription.getText().toString().isEmpty())
                {
                    binding.etDescription.setError("Field is required");
                    binding.etDescription.requestFocus();
                    return;
                }

                dialog.setMessage("Posting Ad");
                dialog.show();

                // Defining the child of storageReference
                StorageReference ref
                        = FirebaseStorage.getInstance().getReference()
                        .child(
                                "images/"
                                        + UUID.randomUUID().toString());

                ref.putFile(image)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                        {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                {
                                    @Override
                                    public void onSuccess(Uri uri)
                                    {

                                        String name = SharedPreferenceManager
                                                .getDataFromSharedPreferences(requireContext(), "name");

                                        String email = SharedPreferenceManager
                                                .getDataFromSharedPreferences(requireContext(), "email");

                                        Product product = new Product(
                                                "",
                                                binding.etTitle.getText().toString(),
                                                binding.etDescription.getText().toString(),
                                                uri.toString(),
                                                binding.etPrice.getText().toString(),
                                                binding.etState.getText().toString(),
                                                "available"
                                        );

                                        User user = new User(name,email);
                                        user.setId(FirebaseAuth.getInstance().getUid());
                                        product.setPostedBy(user);

                                        String key = FirebaseDatabase.getInstance().getReference()
                                                .child("products")
                                                .push().getKey();

                                        product.setId(key);

                                        FirebaseDatabase.getInstance().getReference()
                                                .child("products")
                                                .child(key)
                                                .setValue(product);

                                        FirebaseDatabase.getInstance().getReference()
                                                .child("users")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .child("my_products")
                                                .child(key)
                                                .setValue(product);

                                        dialog.dismiss();

                                        Toast.makeText(requireContext(), "Post Added!", Toast.LENGTH_SHORT).show();

                                        ((MainActivity) requireActivity()).moveToHome();

                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(requireContext(), "An unexpected error occurred, Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    public void setImage(Bitmap bitmap, Uri image)
    {
        this.image = image;
        binding.ivProduct.setImageBitmap(bitmap);
        binding.btnAddPictures.setVisibility( View.GONE);
        binding.ivProduct.setVisibility( View.VISIBLE);
    }
}
