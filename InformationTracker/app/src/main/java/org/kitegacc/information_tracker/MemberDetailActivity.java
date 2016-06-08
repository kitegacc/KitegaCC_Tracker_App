package org.kitegacc.information_tracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MemberDetailActivity extends AppCompatActivity {

    private String MEMBER_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        MEMBER_ID = extras.getString("member_id");

        TextView mid_display = (TextView) findViewById(R.id.member_detail_page_mid_display);
        String mid_display_text = "member_id: " + MEMBER_ID;
        mid_display.setText(mid_display_text);
    }

}
