package com.example.tugasbesar2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentMainMenu extends Fragment implements View.OnClickListener{

    protected Button play;
    protected Button setting;
    protected Button exit;
    protected FragmentCommunication fragmentCommunication;

    public FragmentMainMenu() {
        // Required empty public constructor
    }


    public static FragmentMainMenu newInstance(String param1) {
        FragmentMainMenu fragment = new FragmentMainMenu();
        Bundle args = new Bundle();
        args.putString("title", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_main_menu, container, false);

        // atribut initialitation
         this.play = view.findViewById(R.id.btnplay);
         this.setting = view.findViewById(R.id.btnset);
         this.exit = view.findViewById(R.id.btnext);
         this.fragmentCommunication = (MainActivity)getActivity();

         // set on click
         this.play.setOnClickListener(this);
         this.setting.setOnClickListener(this);
         this.exit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        if(view.getId() == this.play.getId()){
            Log.d("tes","jalan");
            fragmentCommunication.changePage("playGame");
        }
    }

}
