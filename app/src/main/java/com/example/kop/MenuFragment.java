package com.example.kop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kop.databinding.FragmentMenuBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MenuFragment extends Fragment {
    private FragmentMenuBinding fragmentMenuBinding;
    private Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChipNavigationBar chipNavigationBar = view.findViewById(R.id.chipNavigationBar);

        chipNavigationBar.setItemSelected(R.id.home, true);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.hot:
                        fragment = new HotFragment();
                        Log.i("TAG", "hot");
                        break;

                    case R.id.notification:
                        fragment = new NotificationFragment();
                        Log.i("TAG", "notification");
                        break;

                    case R.id.bookmark:
                        fragment = new FavoriteFragment();
                        Log.i("TAG", "profile");
                        break;

                    case R.id.alien:
                        fragment = new HomeFragment();
                        break;


                }
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });


    }
}