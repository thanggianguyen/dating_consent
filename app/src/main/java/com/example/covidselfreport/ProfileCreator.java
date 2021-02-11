package com.example.covidselfreport;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.profileresources.ProfileCreatorFragment;


/**
 * The activity used to create a user profile.
 * Prompts the user to enter their first and last names, and phone number.
 * Uses the ProfileCreatorFragment for the UI and user input.
 */
public class ProfileCreator extends AppCompatActivity {

    /** The fragment manager for this activity */
    private FragmentManager fm;
    /** The only fragment that will be displayed by this activity */
    private Fragment mainFragment;


    /**
     * Called upon Activity creation.
     * Displays the ProfileCreatorFragment (where the user is prompted to enter their info)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creator);

        fm = getSupportFragmentManager();
        mainFragment = new ProfileCreatorFragment();
        fm.beginTransaction().replace(R.id.profilecreator_content_container, mainFragment).commit();
    }

}