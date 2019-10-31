package com.example.tugasbesar2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FragmentCommunication {
    protected FragmentGameplay fragmentGameplay;
    protected FragmentMainMenu fragmentMainMenu;
    protected FragmentManager fragmentManager;
    protected ArrayList<Fragment> halamanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //atribut initialitation
        this.halamanFragment = new ArrayList<>();
        this.fragmentGameplay = new FragmentGameplay();
        this.fragmentMainMenu = new FragmentMainMenu();
        this.fragmentManager = this.getSupportFragmentManager();

        //put fragment in array
        halamanFragment.add(fragmentGameplay);
        halamanFragment.add(fragmentMainMenu);

        //begin transaction
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container,this.fragmentMainMenu)
                .addToBackStack(null)
                .commit();

    }

    public void changePage(String halaman){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(halaman.equalsIgnoreCase("playGame")){
            if(this.fragmentGameplay.isAdded()){
                ft.show(this.fragmentGameplay);
            }
            else {
                ft.add(R.id.fragment_container,this.fragmentGameplay)
                        .addToBackStack(null);
            }
            for(int i = 0 ; i < halamanFragment.size();i++){
                if(halamanFragment.get(i) != this.fragmentGameplay){
                    ft.hide(halamanFragment.get(i));
                }
            }
        }
        else if(halaman.equalsIgnoreCase("mainMenu")){
            if(this.fragmentMainMenu.isAdded()){
                ft.show(this.fragmentMainMenu);
            }
            else {
                ft.add(R.id.fragment_container,this.fragmentMainMenu);
            }
            for(int i = 0 ; i < halamanFragment.size();i++){
                if(halamanFragment.get(i) != this.fragmentMainMenu){
                    ft.hide(halamanFragment.get(i));
                }
            }
        }
        ft.commit();
    }
}
