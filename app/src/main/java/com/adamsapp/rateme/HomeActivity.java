package com.adamsapp.rateme;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by user on 09-Jul-17.
 */

public class HomeActivity extends AppCompatActivity {

    NavigationTabBar navigationTabBar;
    ArrayList<NavigationTabBar.Model> models;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationTabBar = (NavigationTabBar) findViewById(R.id.nav_bar);
        models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_globe), Color.parseColor("#632de9"))
                .title("Globe").badgeTitle("5").build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_whatshot), Color.parseColor("#632de9"))
                .title("Hot").badgeTitle("15").build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_chat), Color.parseColor("#632de9"))
                .title("Chat").badgeTitle("25").build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_person), Color.parseColor("#632de9"))
                .title("Account").badgeTitle("5").build());

        navigationTabBar.setModels(models);
        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.TOP);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.RIGHT);
        navigationTabBar.setIsSwiped(false);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(false);
        navigationTabBar.setBadgeSize(10);
        navigationTabBar.setTitleSize(15);
        //navigationTabBar.setIconSizeFraction(0.5);
        setFragment(new FragHot());
        navigationTabBar.setModelIndex(1);
        navigationTabBar.animate();

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int i) {
                String tit = model.getTitle();
                navigationTabBar.setAnimationDuration(600);
                if (tit.equalsIgnoreCase("Globe"))
                {
                    setFragment(new FragGlobe());
                    navigationTabBar.setAnimationDuration(600);
                    navigationTabBar.setBehaviorEnabled(true);
                }
                else if (tit.equalsIgnoreCase("Hot"))
                {
                    setFragment(new FragHot());
                    navigationTabBar.setAnimationDuration(600);
                }
                else if (tit.equalsIgnoreCase("Chat"))
                {
                    setFragment(new FragChat());
                    navigationTabBar.setAnimationDuration(600);
                }
                else if (tit.equalsIgnoreCase("Account")) {
                    setFragment(new FragAccount());
                    navigationTabBar.setAnimationDuration(600);
                } else {
                    setFragment(new FragHot());
                    navigationTabBar.setAnimationDuration(600);
                }
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int i) {

            }
        });

    }

    protected void setFragment(Fragment fragment){
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragment_frame,fragment);
        t.commit();
        navigationTabBar.setBehaviorEnabled(true);
    }
}
