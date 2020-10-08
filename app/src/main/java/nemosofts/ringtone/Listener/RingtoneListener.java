package nemosofts.ringtone.Listener;

import java.util.ArrayList;

import nemosofts.ringtone.item.ItemRingtone;
/**
 * Created by thivakaran
 */

public interface RingtoneListener {
    void onStart();
    void onEnd(String success, ArrayList<ItemRingtone> arrayListCat);
}
