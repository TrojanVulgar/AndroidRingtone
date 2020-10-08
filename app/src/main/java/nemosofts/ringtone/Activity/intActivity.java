package nemosofts.ringtone.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import nemosofts.ringtone.Method.Methods;
import nemosofts.ringtone.SharedPref.Setting;
import nemosofts.ringtone.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by thivakaran
 */
public class intActivity extends AppCompatActivity {

    Button btn;
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
        setContentView(R.layout.activity_int);

        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(intActivity.this, SplashActivity.class);
                startActivity(main);
                finish();
            }
        });
    }


}
