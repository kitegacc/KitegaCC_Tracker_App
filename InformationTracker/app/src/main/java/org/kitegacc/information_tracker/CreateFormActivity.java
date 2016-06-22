package org.kitegacc.information_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateFormActivity extends AppCompatActivity {

    private String COMMUNITY_ID;
    private String FORM_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        Bundle bundle = getIntent().getExtras();
        COMMUNITY_ID = bundle.getString("community_id");
        FORM_TYPE = bundle.getString("form");

        switch (FORM_TYPE) {
            case "member":
                setTitle("Create Member");
                break;
            default:
                break;
        }

    }
}
