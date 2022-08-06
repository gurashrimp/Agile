package com.example.herogame.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.herogame.Login;
import com.example.herogame.MainActivity;
import com.example.herogame.R;
import com.example.herogame.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private Button signOut;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_profile,container,false);
        auth = FirebaseAuth.getInstance();
//get current user
        final FirebaseUser user =
              auth.getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth
                                                   firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
// user auth state is changed - user is null
// launch login activity
                    startActivity(new Intent(getActivity(),
                            Login.class));

                }
            }
        };
       signOut=view.findViewById(R.id.sign_out);
       signOut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               signout();

           }
       });



        return view;
    }

    private void signout() {
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(getContext(),Login.class);
        startActivity(i);
    }
}