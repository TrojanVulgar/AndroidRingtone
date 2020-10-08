package nemosofts.ringtone.SharedPref;

import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.Serializable;
import java.util.ArrayList;

import nemosofts.ringtone.Constant.Constant;
import nemosofts.ringtone.Login.ItemUser;
import nemosofts.ringtone.Receiver.ItemNemosofts;
import nemosofts.ringtone.item.ItemRingtone;
import nemosofts.ringtone.item.Itemdownload;
/**
 * Created by thivakaran
 */

public class Setting implements Serializable {


    public static String SERVER_URL = Constant.Saver_Url + "speed_api.php";

    public static String purchase_code = "";
    public static String nemosofts_key = "";
    public static ItemNemosofts itemAbout;

    public static boolean Dark_Mode = false;

    public static SimpleExoPlayer exoPlayer;
    public static ArrayList<ItemRingtone> arrayList_play_rc = new ArrayList<>();
    public static int playPos_rc = 0;

    public static SimpleExoPlayer exoPlayer_do;
    public static ArrayList<Itemdownload> arrayList_play_do = new ArrayList<>();
    public static int playPos_do = 0;

    public static String company = "";
    public static String email = "";
    public static String website = "";
    public static String contact = "";

    public static Boolean   isAdmobBannerAd = true, isAdmobInterAd = true, isFBBannerAd = true, isFBInterAd = true;
    public static String ad_publisher_id = "";
    public static String ad_banner_id = "", ad_inter_id = "", fb_ad_banner_id = "", fb_ad_inter_id = "";

    public static int adShow = 10;
    public static int adShowFB = 10;
    public static int adCount = 0;

    public static ItemUser itemUser;
    public static Boolean isLogged = false;
    public static Boolean isLoginOn = true;

    public static final String METHOD_APP_DETAILS = "app_details";
    public static final String METHOD_CAT = "cat_list";
    public static final String TAG_ID = "id";
    public static final String TAG_CAT_ID = "cat_id";
    public static final String TAG_CAT_NAME = "category_name";
    public static final String TAG_CAT_IMAGE = "category_image";
    public static final String TAG_CID = "cid";
    public static final String METHOD_ALL_SONGS = "all_songs";
    public static final String METHOD_MOST_VIEWED = "most_viewed";
    public static final String METHOD_SEARCH = "song_search";
    public static final String METHOD_SONGS = "songs";
    public static final String METHOD_SONG_BY_CAT = "cat_songs";
    public static final String METHOD_DOWNLOAD_COUNT = "download";
    public static final String METHOD_USER_BY_SONGS = "user_id_by_songs";
    public static final String METHOD_LOGIN = "user_login";
    public static final String METHOD_REGISTER = "user_register";
    public static final String TAG_ROOT = "nemosofts";

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MSG = "msg";
}