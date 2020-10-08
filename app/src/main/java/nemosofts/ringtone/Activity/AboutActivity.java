package nemosofts.ringtone.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import nemosofts.ringtone.Constant.Constant;
import nemosofts.ringtone.Method.Methods;
import nemosofts.ringtone.SharedPref.Setting;
import nemosofts.ringtone.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by thivakaran
 */

public class AboutActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView  company, email, website, contact;
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
        setContentView(R.layout.activity_about);

        methods = new Methods(this);
        methods.forceRTLIfSupported(getWindow());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        company = (TextView) findViewById(R.id.company);
        email = (TextView) findViewById(R.id.email);
        website = (TextView) findViewById(R.id.website);
        contact = (TextView) findViewById(R.id.contact);


        company.setText(Setting.company);
        email.setText(Setting.email);
        website.setText(Setting.website);
        contact.setText(Setting.contact);

    }


}