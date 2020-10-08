package nemosofts.ringtone.Activity;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import nemosofts.ringtone.BuildConfig;
import nemosofts.ringtone.Login.ItemUser;
import nemosofts.ringtone.Login.LoadLogin;
import nemosofts.ringtone.Login.LoginActivity;
import nemosofts.ringtone.Login.LoginListener;
import nemosofts.ringtone.Method.Methods;

import nemosofts.ringtone.SharedPref.Setting;
import nemosofts.ringtone.R;
import nemosofts.ringtone.Receiver.NemosoftsListener;
import nemosofts.ringtone.Receiver.LoadNemosofts;
import nemosofts.ringtone.SharedPref.SharedPref;
import nemosofts.ringtone.asyncTask.LoadAbout;
import nemosofts.ringtone.interfaces.AboutListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by thivakaran
 */
public class SplashActivity extends AppCompatActivity {


    Methods methods;
    private static int SPLASH_TIME_OUT = 2000;
    private static final int AUDIO_PERMISSION_REQUEST_CODE = 102;

    public static final String[] WRITE_EXTERNAL_STORAGE_PERMS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    SharedPref sharedPref;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.getNightMode()) {
            Setting.Dark_Mode = true;
            setTheme(R.style.AppTheme2);
        } else {
            Setting.Dark_Mode = false;
            setTheme(R.style.AppTheme);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        methods = new Methods(this);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        changeStatusBarColor();

        if (methods.isNetworkAvailable()) {
            initialize();
        } else {
            IntActivity();
        }

    }
    public void initialize() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(WRITE_EXTERNAL_STORAGE_PERMS, AUDIO_PERMISSION_REQUEST_CODE);
        } else {
            loadAboutData();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case AUDIO_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadAboutData();
                } else {
                    this.finish();
                }
        }
    }


    public void loadAboutData() {
        Toast.makeText(SplashActivity.this, "load About Data", Toast.LENGTH_SHORT).show();
        if (methods.isNetworkAvailable()) {
            LoadAbout loadAbout = new LoadAbout(SplashActivity.this, new AboutListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onEnd(String success, String verifyStatus, String message) {
                    if (success.equals("1")) {
                            Loadnemosofts();

                    } else {
                        errorDialog(getString(R.string.server_error), getString(R.string.err_server));
                    }
                }
            });
            loadAbout.execute();
        } else {
            errorDialog(getString(R.string.err_internet_not_conn), getString(R.string.error_connect_net_tryagain));
        }
    }


    public void Loadnemosofts() {
        if (sharedPref.getIsFirstPurchaseCode()) {
            Toast.makeText(SplashActivity.this, "load Settings", Toast.LENGTH_SHORT).show();
            LoadNemosofts loadAbout = new LoadNemosofts(SplashActivity.this, new NemosoftsListener() {
                @Override
                public void onStart() {
                }
                @Override
                public void onEnd(String success, String verifyStatus, String message) {
                            if (BuildConfig.APPLICATION_ID.equals(Setting.itemAbout.getPackage_name())) {
                                sharedPref.setIsFirstPurchaseCode(false);
                                sharedPref.setPurchaseCode(Setting.itemAbout);
                                loadSettings();

                    } else {
                        errorDialog(getString(R.string.error_unauth_access), "err server");
                    }
                }
            });
            loadAbout.execute();
        } else {
            sharedPref.getPurchaseCode();
            loadSettings();
        }
    }

    public void loadSettings() {
        if (sharedPref.getIsFirst()) {
            openLoginActivity();
        } else {
            if (!sharedPref.getIsAutoLogin()) {
                thiva();
            } else {
                if (methods.isNetworkAvailable()) {
                    loadLogin();
                } else {
                    thiva();
                }
            }
        }
    }

    private void loadLogin() {
        if (methods.isNetworkAvailable()) {
            LoadLogin loadLogin = new LoadLogin(new LoginListener() {
                @Override
                public void onStart() {
                }
                @Override
                public void onEnd(String success, String loginSuccess, String message, String user_id, String user_name) {
                    if (success.equals("1")) {
                        if (loginSuccess.equals("1")) {
                            Setting.itemUser = new ItemUser(user_id, user_name, sharedPref.getEmail(), "");
                            Setting.isLogged = true;
                            thiva();
                        } else {
                            thiva();
                        }
                    } else {
                        thiva();
                    }
                }
            }, methods.getAPIRequest(Setting.METHOD_LOGIN, 0, "", "", "", "", "", "", "", "", "", sharedPref.getEmail(), sharedPref.getPassword(), "", "", "", "", null));
            loadLogin.execute();
        } else {
            Toast.makeText(SplashActivity.this, getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
        }
    }

    private void errorDialog(String title, String message) {
        final AlertDialog.Builder  alertDialog ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Setting.Dark_Mode){
                alertDialog = new AlertDialog.Builder(SplashActivity.this, R.style.ThemeDialog2);
            }else {
                alertDialog = new AlertDialog.Builder(SplashActivity.this, R.style.ThemeDialog);
            }
        } else {
            alertDialog = new AlertDialog.Builder(SplashActivity.this, R.style.ThemeDialog);
        }

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        if (title.equals(getString(R.string.err_internet_not_conn)) || title.equals(getString(R.string.server_error))) {
            alertDialog.setNegativeButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loadAboutData();
                }
            });
        }

        alertDialog.setPositiveButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }

    private void openLoginActivity() {
        Intent intent;
        if (Setting.isLoginOn && sharedPref.getIsFirst()) {
            sharedPref.setIsFirst(false);
            intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("from", "");
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }


    private void thiva() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        },SPLASH_TIME_OUT);
    }


    private void IntActivity() {
        Intent mainb = new Intent(SplashActivity.this, intActivity.class);
        startActivity(mainb);
        finish();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


}