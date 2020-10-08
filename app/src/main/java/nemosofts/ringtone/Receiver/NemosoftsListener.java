package nemosofts.ringtone.Receiver;

/**
 * Created by thivakaran
 */

public interface NemosoftsListener {
    void onStart();
    void onEnd(String success, String verifyStatus, String message);
}