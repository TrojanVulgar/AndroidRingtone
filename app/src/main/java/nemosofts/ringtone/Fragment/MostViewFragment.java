package nemosofts.ringtone.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import nemosofts.ringtone.Adapter.RingtoneAdapter;
import nemosofts.ringtone.BuildConfig;
import nemosofts.ringtone.EndlessRecyclerViewScroll.EndlessRecyclerViewScrollListener;
import nemosofts.ringtone.SharedPref.Setting;
import nemosofts.ringtone.Listener.InterAdListener;
import nemosofts.ringtone.Listener.ClickListenerRecorder;
import nemosofts.ringtone.Method.Methods;
import nemosofts.ringtone.item.ItemRingtone;
import nemosofts.ringtone.Load.LoadSongs;
import nemosofts.ringtone.R;
import nemosofts.ringtone.Listener.RingtoneListener;
/**
 * Created by thivakaran
 */
public class MostViewFragment extends Fragment {
    Methods methods;
    RecyclerView recyclerView;
    public static RingtoneAdapter adapter;
    ArrayList<ItemRingtone> arrayList;
    ProgressBar progressBar;
    Boolean isOver = false, isScroll = false;
    int page = 1;
    GridLayoutManager grid;
    LoadSongs load;

    public View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        arrayList = new ArrayList<>();
        progressBar = view.findViewById(R.id.load_video);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        grid = new GridLayoutManager(getActivity(), 1);
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
                    try {
                        adapter.hideHeader();
                    }catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                } else {
                    adapter.hideHeader();
                }
            }
        });

        methods = new Methods(getActivity(), new InterAdListener() {
            @Override
            public void onClick(int position, String type) {
                adapter.notifyDataSetChanged();
            }
        });

        if (BuildConfig.APPLICATION_ID.equals(Setting.itemAbout.getPackage_name())){
            getData();
        }



        return view;
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

                    Setting.arrayList_play_rc.clear();
                    Setting.arrayList_play_rc.addAll(arrayList);
                    Setting.playPos_rc= 0;

                    Setad();
                }
            }
        },methods.getAPIRequest(Setting.METHOD_MOST_VIEWED, page, "", "", "", "", "", "", "", "","","","","","","","", null));
        load.execute();
    }

    private void Setad() {
        if(!isScroll) {
            adapter = new RingtoneAdapter(getActivity(), arrayList , new ClickListenerRecorder(){
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


}