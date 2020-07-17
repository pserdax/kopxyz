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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FragmentLoginBinding fragmentLoginBinding;

    @Override
    public void onStart() {
        super.onStart();

        //Check if user is signed in (not-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();
        EditText email = view.findViewById(R.id.login_email);
        EditText password = view.findViewById(R.id.login_password);
        TextView txtRegister = view.findViewById(R.id.tv_register);
        Button btnLogin = view.findViewById(R.id.btn_login);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);

        txtRegister.setOnClickListener( v -> {
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });

        btnLogin.setOnClickListener( v ->{

            String mEmail = email.getText().toString().trim();
            String mPassword = password.getText().toString().trim();

            if(TextUtils.isEmpty(mEmail)){
                email.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(mPassword)){
                password.setError("Password is required");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_loginFragment_to_menuFragment);
                    } else {
                        Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                    });




        });

    }
}