package nemosofts.ringtone.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nemosofts.ringtone.Adapter.RingtoneAdapter;
import nemosofts.ringtone.BuildConfig;
import nemosofts.ringtone.Constant.Constant;
import nemosofts.ringtone.EndlessRecyclerViewScroll.EndlessRecyclerViewScrollListener;
import nemosofts.ringtone.Listener.ClickListenerRecorder;
import nemosofts.ringtone.Listener.InterAdListener;
import nemosofts.ringtone.Listener.RingtoneListener;
import nemosofts.ringtone.Load.LoadSongs;
import nemosofts.ringtone.Method.Methods;
import nemosofts.ringtone.R;
import nemosofts.ringtone.SharedPref.Setting;
import nemosofts.ringtone.item.ItemRingtone;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by thivakaran
 */

public class UserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static RingtoneAdapter adapter;
    ArrayList<ItemRingtone> arrayList;
    ProgressBar progressBar;
    Boolean isOver = false, isScroll = false;
    int page = 1;
    GridLayoutManager grid;
    LoadSongs loadWallpaper;
    Toolbar toolbar2;
    Methods methods;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Setting.Dark_Mode) {
            setTheme(R.style.AppTheme2);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());

        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        setTitle(Setting.itemUser.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        arrayList = new ArrayList<>();
        progressBar = findViewById(R.id.load_video);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        grid = new GridLayoutManager(UserActivity.this, 1);
        grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? grid.getSpanCount() : 1;
            }
        });
        recyclerView.setLayoutManager(grid);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(grid) {
            @Override
            public void onLoadMore(int p, int totalItemsCount) {
                if(!isOver) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isScroll = true;
                            getData();
                        }
                    }, 0);
                } else {
                    adapter.hideHeader();
                }
            }
        });

        if (BuildConfig.APPLICATION_ID.equals(Setting.itemAbout.getPackage_name())){
            getData();
        }

        LinearLayout adView = findViewById(R.id.adView);
        methods.showBannerAd(adView);

        methods = new Methods(UserActivity.this, new InterAdListener() {
            @Override
            public void onClick(int position, String type) {
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void getData() {
        loadWallpaper = new LoadSongs(new RingtoneListener() {
            @Override
            public void onStart() {
                if(arrayList.size() == 0) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onEnd(String success, ArrayList<ItemRingtone> arrayListWall) {
                if(arrayListWall.size() == 0) {
                    isOver = true;
                    try {
                        adapter.hideHeader();
                    }catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                } else {
                    page = page + 1;
                    arrayList.addAll(arrayListWall);
                    progressBar.setVisibility(View.INVISIBLE);

                    Setad();
                }
            }
        },methods.getAPIRequest(Setting.METHOD_USER_BY_SONGS, page, "", "", "", "", "", "", "", "","","","","","",Setting.itemUser.getId(),"", null));
        loadWallpaper.execute();
    }

    private void Setad() {
        if(!isScroll) {
            adapter = new RingtoneAdapter(UserActivity.this, arrayList , new ClickListenerRecorder(){
                @Override
                public void onClick(int position) {
                    methods.showInter(position, "");
                    Setting.arrayList_play_rc.clear();
                    Setting.arrayList_play_rc.addAll(arrayList);
                    Setting.playPos_rc = position;

                    adapter.notifyDataSetChanged();
                }
            });

            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
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