package com.example.kop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kop.databinding.FragmentLoginBinding;
import com.example.kop.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FragmentRegisterBinding fragmentRegisterBinding;
    NavController navController;


    @Override
    public void onStart() {
        super.onStart();

        //Check if user is signed in (not-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_loginFragment_to_menuFragment);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        return fragmentRegisterBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentRegisterBinding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController  = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();
        EditText email = fragmentRegisterBinding.registerEmail;
        EditText fullName = fragmentRegisterBinding.registerFullname;
        EditText password = fragmentRegisterBinding.registerPassword;
        EditText passwordConfirm = fragmentRegisterBinding.registerPasswordConfirm;
        TextView txtLogin = fragmentRegisterBinding.tvSign;
        Button btnRegister = fragmentRegisterBinding.btnRegister;
        ProgressBar progressBar = fragmentRegisterBinding.progressBar;

        txtLogin.setOnClickListener(v -> {
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        });

        btnRegister.setOnClickListener(v->{

            final String mEmail, mPassword, mPasswordConfirmation, mFullName;
            mEmail =
            mPassword = password.getText().toString().trim();
            mPasswordConfirmation = passwordConfirm.getText().toString().trim();
            mFullName = fullName.getText().toString().trim();

            if(TextUtils.isEmpty(mEmail)){
                email.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(mFullName)){
                fullName.setError("Full name is required");
                return;
            }
            if(TextUtils.isEmpty(mPassword)){
                password.setError("Please set a password");
                return;
            }
            if(TextUtils.isEmpty(mPasswordConfirmation)){
                passwordConfirm.setError("Please confirm your password");
                return;
            }
            if(!mPasswordConfirmation.equals(mPassword)){
                Toast.makeText(getActivity(), "Oops, password doesn't match", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(getActivity(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.action_registerFragment_to_menuFragment);
                            }
                            else {
                                Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        });

    }
}