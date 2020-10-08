package nemosofts.ringtone.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nemosofts.ringtone.Adapter.SongAdapter;
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

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public static SongAdapter adapter;
    ArrayList<ItemRingtone> arrayList;
    ProgressBar progressBar;
    Boolean isOver = false, isScroll = false;
    int page = 1;
    GridLayoutManager grid;
    LoadSongs load;

    EditText searchView;
    ImageView search;
    Methods methods;
    Toolbar toolbar2;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Setting.Dark_Mode ) {
            setTheme(R.style.AppTheme2);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());

        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        setTitle( getResources().getString(R.string.search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        arrayList = new ArrayList<>();
        progressBar = findViewById(R.id.load_video);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        grid = new GridLayoutManager(SearchActivity.this, 1);
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


        searchView = findViewById(R.id.search_view);
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

            }
        });

        methods = new Methods(SearchActivity.this, new InterAdListener() {
            @Override
            public void onClick(int position, String type) {
                methods.showInter(position, "");
                Setting.arrayList_play_rc.clear();
                Setting.arrayList_play_rc.addAll(arrayList);
                Setting.playPos_rc = position;

                adapter.notifyDataSetChanged();
            }
        });

    }


    private void getData() {
        load = new LoadSongs(new RingtoneListener() {
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
        },methods.getAPIRequest(Setting.METHOD_SEARCH, page, "", "", searchView.getText().toString(), "", "", "", "", "","","","","","","","", null));
        load.execute();
    }

    private void Setad() {

        if(!isScroll) {
            adapter = new SongAdapter(SearchActivity.this, arrayList, new ClickListenerRecorder() {
                @Override
                public void onClick(int position) {
                    methods.showInter(position, "");
                    Setting.arrayList_play_rc.clear();
                    Setting.arrayList_play_rc.addAll(arrayList);
                    Setting.playPos_rc = position;

                    adapter.notifyDataSetChanged();
                }

            }, "");
            recyclerView.setAdapter(adapter);
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

