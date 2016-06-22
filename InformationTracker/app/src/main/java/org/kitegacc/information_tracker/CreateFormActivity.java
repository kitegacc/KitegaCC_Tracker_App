package org.kitegacc.information_tracker;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class CreateFormActivity extends AppCompatActivity {

    private String COMMUNITY_ID;
    private String FORM_TYPE = "";
    private int NUM_FIELDS = 0;
    private HashMap<String, String> QUERY_ARGS;

    private EditText FORM_FIELD_1;
    private EditText FORM_FIELD_2;
    private EditText FORM_FIELD_3;
    private EditText FORM_FIELD_4;
    private EditText FORM_FIELD_5;
    private EditText FORM_FIELD_6;
    private EditText FORM_FIELD_7;
    private TextInputLayout INPUT_LAYOUT_1;
    private TextInputLayout INPUT_LAYOUT_2;
    private TextInputLayout INPUT_LAYOUT_3;
    private TextInputLayout INPUT_LAYOUT_4;
    private TextInputLayout INPUT_LAYOUT_5;
    private TextInputLayout INPUT_LAYOUT_6;
    private TextInputLayout INPUT_LAYOUT_7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        attachFormFields();
        QUERY_ARGS = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        FORM_TYPE = bundle.getString("form");

        switch (FORM_TYPE) {
            case "member":
                createMemberForm(bundle);
                break;
            case "community":
                createCommunityAccountForm();
                break;
            default:
                break;
        }

    }

    public void attachFormFields() {
        FORM_FIELD_1 = (EditText) findViewById(R.id.form_field_1);
        FORM_FIELD_2 = (EditText) findViewById(R.id.form_field_2);
        FORM_FIELD_3 = (EditText) findViewById(R.id.form_field_3);
        FORM_FIELD_4 = (EditText) findViewById(R.id.form_field_4);
        FORM_FIELD_5 = (EditText) findViewById(R.id.form_field_5);
        FORM_FIELD_6 = (EditText) findViewById(R.id.form_field_6);
        FORM_FIELD_7 = (EditText) findViewById(R.id.form_field_7);

        INPUT_LAYOUT_1 = (TextInputLayout) findViewById(R.id.create_input_layout_1);
        INPUT_LAYOUT_2 = (TextInputLayout) findViewById(R.id.create_input_layout_2);
        INPUT_LAYOUT_3 = (TextInputLayout) findViewById(R.id.create_input_layout_3);
        INPUT_LAYOUT_4 = (TextInputLayout) findViewById(R.id.create_input_layout_4);
        INPUT_LAYOUT_5 = (TextInputLayout) findViewById(R.id.create_input_layout_5);
        INPUT_LAYOUT_6 = (TextInputLayout) findViewById(R.id.create_input_layout_6);
        INPUT_LAYOUT_7 = (TextInputLayout) findViewById(R.id.create_input_layout_7);
    }

    /* Member form needs fields:
        1) name
        2) age
        3) emergency_contact
        4) residence
        5) kin_or_spouse */
    public void createMemberForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        NUM_FIELDS = 5;
        QUERY_ARGS.put("form_type", "member");
        QUERY_ARGS.put("community_id", COMMUNITY_ID);
        setTitle("Create Member");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Name");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Name");

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Age");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Age");

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Emergency Contact");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Emergency Contact");

        INPUT_LAYOUT_4.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_4.setHint("Residence");
        FORM_FIELD_4.setVisibility(View.VISIBLE);
        FORM_FIELD_4.setHint("Residence");

        INPUT_LAYOUT_5.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_5.setHint("Kin / Spouse");
        FORM_FIELD_5.setVisibility(View.VISIBLE);
        FORM_FIELD_5.setHint("Kin / Spouse");
    }

    public void createCommunityAccountForm() {
        setTitle("Create Community Account");
    }

    public void submitCreateForm() {
        switch (FORM_TYPE) {
            case "member":
                submitCreateMemberForm();
                break;
            case "community":
                submitCreateCommunityAccountForm();
                break;
            default:
                break;
        }
    }

    public void submitCreateMemberForm() {
        QUERY_ARGS.put("name", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("age", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("emergency_contact", FORM_FIELD_3.getText().toString());
        QUERY_ARGS.put("residence", FORM_FIELD_4.getText().toString());
        QUERY_ARGS.put("kin_or_spouse", FORM_FIELD_5.getText().toString());

    }

    public void submitCreateCommunityAccountForm() {

    }
}
