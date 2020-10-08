package nemosofts.ringtone.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import nemosofts.ringtone.Adapter.SongAdapter;
import nemosofts.ringtone.Constant.Constant;
import nemosofts.ringtone.SharedPref.Setting;
import nemosofts.ringtone.DBHelper.DBHelper;
import nemosofts.ringtone.Listener.InterAdListener;
import nemosofts.ringtone.Listener.ClickListenerRecorder;
import nemosofts.ringtone.Method.Methods;
import nemosofts.ringtone.R;
import nemosofts.ringtone.item.ItemRingtone;

/**
 * Created by thivakaran
 */

public class FavouriteActivity extends AppCompatActivity {
    Methods methods;
    private RecyclerView rv;
    private SongAdapter adapter;
    private ArrayList<ItemRingtone> arrayList;
    private ProgressBar progressBar;

    Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Setting.Dark_Mode ) {
            setTheme(R.style.AppTheme2);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());

        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        setTitle(getResources().getString(R.string.favourite));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        DBHelper dbHelper = new DBHelper(this);
        arrayList = new ArrayList<>();
        arrayList.addAll(dbHelper.loadFavData());

        progressBar = findViewById(R.id.load_video);
        progressBar.setVisibility(View.GONE);

        rv = findViewById(R.id.recycler);
        LinearLayoutManager llm_banner = new LinearLayoutManager(this);
        rv.setLayoutManager(llm_banner);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);

        methods = new Methods(this, new InterAdListener() {
            @Override
            public void onClick(int position, String type) {
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new SongAdapter(FavouriteActivity.this, arrayList, new ClickListenerRecorder() {
            @Override
            public void onClick(int position) {
                methods.showInter(position, "");
                Setting.arrayList_play_rc.clear();
                Setting.arrayList_play_rc.addAll(arrayList);
                Setting.playPos_rc = position;
            }

        }, "");
        rv.setAdapter(adapter);

        LinearLayout adView = findViewById(R.id.adView);
        methods.showBannerAd(adView);
    }

    @Override
    protected void onStart() {
        try {
            Setting.exoPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }


    @Override
    public void onDestroy() {
        try {
            Setting.exoPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        try {
            Setting.exoPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }






}
