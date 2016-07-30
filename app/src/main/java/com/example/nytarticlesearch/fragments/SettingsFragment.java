package com.example.nytarticlesearch.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nytarticlesearch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends DialogFragment {
    Button btnSave;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String Title){
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString("title", "Settings");
        settingsFragment.setArguments(args);
        return settingsFragment;

    }

    public interface EditSettingsDialogListener{

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //btnSave.
        return inflater.inflate(R.layout.fragment_settings, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSave = (Button) view.findViewById(R.id.btnSave);

    }
}
