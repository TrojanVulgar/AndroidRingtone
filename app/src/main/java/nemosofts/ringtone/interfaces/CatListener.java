package nemosofts.ringtone.interfaces;

import java.util.ArrayList;


import nemosofts.ringtone.item.ListltemCategory;

public interface CatListener {
    void onStart();
    void onEnd(String success, String verifyStatus, String message, ArrayList<ListltemCategory> arrayList);
}
