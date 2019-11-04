package com.example.tugasbesar2;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class GameOverFragment extends DialogFragment{

    protected Button play;
    protected Button exit;
    protected MainActivity mainActivity;

    public GameOverFragment() {
        // Required empty public constructor
    }


    public static GameOverFragment newInstance(String param1) {
        GameOverFragment fragment = new GameOverFragment();
        Bundle args = new Bundle();
        args.putString("title", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_over, container, false);
        this.exit = view.findViewById(R.id.btn_extAftGO);
        mainActivity = (MainActivity)getActivity();

        this.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.closeApp();
            }
        });
        return view;

    }


}
