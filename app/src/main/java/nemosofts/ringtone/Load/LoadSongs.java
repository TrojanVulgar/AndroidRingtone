package nemosofts.ringtone.Load;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nemosofts.ringtone.JsonUtils.JsonUtils;
import nemosofts.ringtone.Method.Methods;
import nemosofts.ringtone.Listener.RingtoneListener;
import nemosofts.ringtone.SharedPref.Setting;
import nemosofts.ringtone.item.ItemRingtone;
import okhttp3.RequestBody;

/**
 * Created by thivakaran
 */

public class LoadSongs extends AsyncTask<String, String, String> {

    private RingtoneListener ringtoneListener;
    private ArrayList<ItemRingtone> arrayList;
    private RequestBody requestBody;

    public LoadSongs(RingtoneListener ringtoneListener, RequestBody requestBody) {
        this.ringtoneListener = ringtoneListener;
        arrayList = new ArrayList<>();
        this.requestBody = requestBody;
    }

    @Override
    protected void onPreExecute() {
        ringtoneListener.onStart();
        super.onPreExecute();
    }

    @Override
    protected  String doInBackground(String... strings)  {
        String json = JsonUtils.okhttpPost(Setting.SERVER_URL, requestBody);

        try {
            JSONObject jOb = new JSONObject(json);
            JSONArray jsonArray = jOb.getJSONArray(Setting.TAG_ROOT);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject thiva = jsonArray.getJSONObject(i);

                String id = thiva.getString("id");
                String cat_id = thiva.getString("cat_id");
                String title = thiva.getString("title");
                String radio_id = thiva.getString("ringtone_id");
                String url_fm = thiva.getString("url");
                String total_views = thiva.getString("total_views");
                String total_download = thiva.getString("total_download");
                String cid = thiva.getString("cid");
                String category_name = thiva.getString("category_name");
                String category_image = thiva.getString("category_image");
                String category_image_thumb = thiva.getString("category_image_thumb");

                ItemRingtone objItem = new ItemRingtone(id, cat_id, title, radio_id, url_fm, total_views, total_download, cid, category_name, category_image, category_image_thumb);
                arrayList.add(objItem);

            }
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        ringtoneListener.onEnd(String.valueOf(s),arrayList);
        super.onPostExecute(s);
    }

}

