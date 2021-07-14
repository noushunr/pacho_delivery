package com.palana.paachoodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import static com.palana.paachoodelivery.SharedPrefs.Keys.IS_LOGIN;
import static com.palana.paachoodelivery.SharedPrefs.Keys.TOKEN;
import static com.palana.paachoodelivery.SharedPrefs.Keys.TYPE;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private Toolbar mToolabr;
    StateDialog stateDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mToolabr = findViewById(R.id.toolbar);
        setSupportActionBar(mToolabr);
        String type = SharedPrefs.getString(TYPE,"");
        Log.d("Type",type);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (type!=null && !type.equalsIgnoreCase("delivery_boy")){
            adapter.addFragment(OrdersListFragment.newInstance("1",""), "Placed");
            adapter.addFragment(OrdersListFragment.newInstance("2",""), "Item prepared");
            adapter.addFragment(OrdersListFragment.newInstance("3",""), "Packed");
        }
        adapter.addFragment(OrdersListFragment.newInstance("4",""), "Picked");
        adapter.addFragment(OrdersListFragment.newInstance("5",""), "Delivered");
        adapter.addFragment(OrdersListFragment.newInstance("0",""), "Cancelled");
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
//        stateDialog = new StateDialog();
//        stateDialog.show(getSupportFragmentManager(), "State");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout){

                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("Are you sure to logout?")
                                .setCancelable(false)
                                .setNegativeButton(R.string.no, null) // dismisses by default
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPrefs.remove(TOKEN);
                                        SharedPrefs.remove(IS_LOGIN);
                                        SharedPrefs.remove(TYPE);
                                        SharedPrefs.clear();
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                })
                                .create()
                                .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
