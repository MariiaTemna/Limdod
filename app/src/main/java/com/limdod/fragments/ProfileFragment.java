package com.limdod.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.limdod.MainActivity;
import com.limdod.R;
import com.limdod.SplashActivity;
import com.limdod.databinding.FragmentAddProductBinding;
import com.limdod.databinding.FragmentProfileBinding;
import com.limdod.utils.SharedPreferenceManager;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment
{

    FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        String name = SharedPreferenceManager
                .getDataFromSharedPreferences(requireContext(), "name");

        String email = SharedPreferenceManager
                .getDataFromSharedPreferences(requireContext(), "email");


        binding.tvUsername.setText(name);
        binding.tvEmail.setText(email);

        String[] initials = name.split(" ");
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

        binding.btnMyProducts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ((MainActivity) requireActivity()).displayFragment(
                        new MyProductsFragment()
                );
            }
        });

        binding.btnOrders.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ((MainActivity) requireActivity()).displayFragment(
                        new MyOrdersFragment()
                );
            }
        });


        binding.btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent =  new Intent(requireContext(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
