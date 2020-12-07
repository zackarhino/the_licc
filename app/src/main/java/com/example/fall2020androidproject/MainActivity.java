package com.example.fall2020androidproject;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    NavController navController;

    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = (TextView) toolbar.getChildAt(0); // Get the toolbar title
        Typeface toolbarTypeface = ResourcesCompat.getFont(this, R.font.gingerly);
        toolbarTitle.setTypeface(toolbarTypeface);
        toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.toolbarTitleFontSize));

        fab = findViewById(R.id.fab);

        // Default preferences
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.key_show_button), true);
        editor.putInt(getString(R.string.key_play_mode), 0);
        editor.putBoolean(getString(R.string.key_party_mode), false);
        editor.apply();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main, R.id.nav_tone, R.id.nav_comment, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (item.getItemId()){
            case R.id.action_show_button:
                editor.putBoolean(getString(R.string.key_show_button), !sharedPreferences.getBoolean(getString(R.string.key_show_button), true));
                editor.apply();
                Toast.makeText(this, "Show button: " + sharedPreferences.getBoolean(getString(R.string.key_show_button), false), Toast.LENGTH_SHORT).show();
                break;
            case  R.id.action_play_mode_1:
                editor.putInt(getString(R.string.key_play_mode), 1);
                editor.apply();
                Toast.makeText(this, "Play mode: " + sharedPreferences.getInt(getString(R.string.key_play_mode), -1), Toast.LENGTH_SHORT).show();
                break;
            case  R.id.action_play_mode_2:
                editor.putInt(getString(R.string.key_play_mode), 2);
                editor.apply();
                Toast.makeText(this, "Play mode: " + sharedPreferences.getInt(getString(R.string.key_play_mode), -1), Toast.LENGTH_SHORT).show();
                break;
            case  R.id.action_play_mode_3:
                editor.putInt(getString(R.string.key_play_mode), 3);
                editor.apply();
                Toast.makeText(this, "Play mode: " + sharedPreferences.getInt(getString(R.string.key_play_mode), -1), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_party_mode:
                // Flip the boolean
                editor.putBoolean(getString(R.string.key_party_mode), !sharedPreferences.getBoolean(getString(R.string.key_party_mode), false));
                editor.apply();
                Toast.makeText(this, "Party mode: " + sharedPreferences.getBoolean(getString(R.string.key_party_mode), false), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}