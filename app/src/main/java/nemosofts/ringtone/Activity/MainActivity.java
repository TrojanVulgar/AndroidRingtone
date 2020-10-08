package nemosofts.ringtone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import nemosofts.ringtone.Constant.Constant;
import nemosofts.ringtone.DBHelper.DBHelper;
import nemosofts.ringtone.Login.ItemUser;
import nemosofts.ringtone.Login.LoginActivity;
import nemosofts.ringtone.Method.Methods;
import nemosofts.ringtone.SharedPref.Setting;
import nemosofts.ringtone.R;
import nemosofts.ringtone.Adapter.ViewPagerAdapter;
import nemosofts.ringtone.SharedPref.SharedPref;

/**
 * Created by thivakaran
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager viewPager;
    private TabLayout tabLayout;

    DrawerLayout drawer;
    Toolbar toolbar;

    NavigationView navigationView;

    MenuItem menu_login, menu_upload,menu_user_by;

    private static final int AUDIO_PERMISSION_REQUEST_CODE = 102;

    public static final String[] WRITE_EXTERNAL_STORAGE_PERMS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    Methods methods;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Setting.Dark_Mode) {
            setTheme(R.style.AppTheme2);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());
        dbHelper = new DBHelper(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        if (Setting.Dark_Mode) {
            toggle.setHomeAsUpIndicator(R.drawable.ic_menu2);
        } else {
            toggle.setHomeAsUpIndicator(R.drawable.ic_menu1);
        }

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setDrawerIndicatorEnabled(false);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        menu_login = menu.findItem(R.id.nav_login);
        menu_upload = menu.findItem(R.id.nav_upload);
        menu_user_by = menu.findItem(R.id.nav_user_by);

        changeLoginName();


        viewPager = (ViewPager) findViewById(R.id.view_pager);

        String[] pageTitle = {"Home", "Top 10 Views", "Categories"};
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }

        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        LinearLayout adView = findViewById(R.id.adView);
        methods.showBannerAd(adView);
    }

    private void changeLoginName() {
        if (menu_login != null) {
            if (Setting.isLoginOn) {
                if (Setting.isLogged) {
                    menu_login.setTitle(getResources().getString(R.string.logout));
                    menu_login.setIcon(getResources().getDrawable(R.drawable.ic_logout));
                    menu_upload.setVisible(true);
                    menu_user_by.setVisible(true);
                } else {
                    menu_login.setTitle(getResources().getString(R.string.login));
                    menu_login.setIcon(getResources().getDrawable(R.drawable.ic_login));
                    menu_upload.setVisible(false);
                    menu_user_by.setVisible(false);
                }
            } else {
                menu_login.setVisible(false);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                break;
            case R.id.nav_user_by:
                Stop();
                Intent intent_user = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent_user);
                break;
            case R.id.nav_fav:
                Stop();
                Intent intent_fav = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent_fav);
                break;
            case R.id.nav_download:
                Stop();
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_upload:
                Intent update = new Intent(MainActivity.this,UploadRingtoneActivity.class);
                startActivity(update);
                break;
            case R.id.nav_set:
                overridePendingTransition(0, 0);
                overridePendingTransition(0, 0);
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                finish();
                break;
            case R.id.nav_login:
                methods.clickLogin();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_search) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            Stop();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void Stop() {
        try {
            Setting.exoPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        try {
            Setting.exoPlayer.stop();
            Setting.exoPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        try {
            Setting.exoPlayer.stop();
            Setting.exoPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

}
