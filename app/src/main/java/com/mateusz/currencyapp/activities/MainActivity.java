package com.mateusz.currencyapp.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mateusz.currencyapp.R;
import com.mateusz.currencyapp.fragments.CurrencyConverterFragment;
import com.mateusz.currencyapp.fragments.CurrencyListFragment;
import com.mateusz.currencyapp.services.GetDataService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final String TAG=MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private BroadcastReceiver receiverSnackBar=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Snackbar snackbar=Snackbar.make(drawerLayout,"Connection Error",Snackbar.LENGTH_LONG);
            View snacckbarView=snackbar.getView();
            snacckbarView.setBackgroundColor(Color.RED);
            snackbar.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Method onCreate");
        setContentView(R.layout.navigation_drawer);

        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigationView= (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new CurrencyListFragment());

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiverSnackBar, new IntentFilter(GetDataService.SHOW_SNACKBAR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiverSnackBar);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG,"Method onOptionItemSelected");
        if(item.getItemId()==R.id.action_refresh){
            Log.d(TAG,"Method onOptionItemSelected: action refresh");
            Intent intent=new Intent(this,GetDataService.class);
            startService(intent);
        }
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.nav_list){
            replaceFragment(new CurrencyListFragment());
        }else if(id==R.id.nav_converter){
            replaceFragment(new CurrencyConverterFragment());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        transaction.commit();
    }
}
