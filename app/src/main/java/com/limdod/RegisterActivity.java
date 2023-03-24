package com.limdod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.limdod.databinding.ActivityRegisterBinding;
import com.limdod.utils.SharedPreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
{

    ActivityRegisterBinding binding;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        dialog = new ProgressDialog(this);

        binding.btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(validateFields())
                {
                    dialog.setMessage("Signing up");
                    dialog.show();

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                            binding.etEmail.getText().toString(),
                            binding.etPassword.getText().toString()
                            )
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>()
                            {
                                @Override
                                public void onSuccess(AuthResult authResult)
                                {
                                    Map<String,Object> userMap = new HashMap<>();

                                    userMap.put("name", binding.etUsername.getText().toString());
                                    userMap.put("email", binding.etEmail.getText().toString());

                                    Log.d("MLK", "Auth: " + authResult.getUser().getUid());

                                    FirebaseDatabase.getInstance().getReference()
                                            .child("users")
                                            .child(authResult.getUser().getUid())
                                            .setValue(userMap);

                                    SharedPreferenceManager.addDataToSharedPreferences(
                                            RegisterActivity.this,
                                            "email",
                                            binding.etEmail.getText().toString()
                                    );

                                    SharedPreferenceManager.addDataToSharedPreferences(
                                            RegisterActivity.this,
                                            "name",
                                            binding.etUsername.getText().toString()
                                    );


                                    Intent intent = new Intent(RegisterActivity.this, SplashActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    dialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, e.getMessage()
                                            , Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private boolean validateFields()
    {
        if(binding.etUsername.getText().toString().isEmpty())
        {
            binding.etUsername.setError("Username is required");
            binding.etUsername.requestFocus();
            return false;
        }

        if(binding.etEmail.getText().toString().isEmpty())
        {
            binding.etEmail.setError("Email is required");
            binding.etEmail.requestFocus();
            return false;
        }

        if(binding.etPassword.getText().toString().isEmpty())
        {
            binding.etPassword.setError("Please enter password");
            binding.etPassword.requestFocus();
            return false;
        }

        if(binding.etConfirmPassword.getText().toString().isEmpty())
        {
            binding.etConfirmPassword.setError("Please enter password");
            binding.etConfirmPassword.requestFocus();
            return false;
        }


        if(!binding.etPassword.getText().toString().equals(binding.etConfirmPassword.getText().toString()))
        {
            binding.etConfirmPassword.setError("Passwords do not match");
            binding.etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}