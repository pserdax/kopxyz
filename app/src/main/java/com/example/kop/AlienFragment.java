package com.example.tiderdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kop.R;
import com.example.kop.databinding.FragmentAlienBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Queue;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlienFragment extends Fragment {

    private FragmentAlienBinding fragmentAlienBinding;
    NavController navController;

    public AlienFragment() {
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentAlienBinding = FragmentAlienBinding.inflate(inflater, container, false);

        View view =  fragmentAlienBinding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAlienBinding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        fragmentAlienBinding.logoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                navController.navigate(R.id.action_menuFragment_to_loginFragment);


            }
        });


    }
}
