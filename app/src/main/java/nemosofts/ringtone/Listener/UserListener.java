package nemosofts.ringtone.Listener;

import java.util.ArrayList;

import nemosofts.ringtone.item.ItemRingtone;
import nemosofts.ringtone.item.ListltemUser;

/**
 * Created by thivakaran
 */

public interface UserListener {
    void onStart();
    void onEnd(String success, ArrayList<ListltemUser> arrayListCat);
}
