package org.kitegacc.information_tracker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CreateFormActivity extends AppCompatActivity {

    private String COMMUNITY_ID = "";
    private String LOAN_ID = "";
    private String MEMBER_ID = "";
    private String FORM_TYPE = "";
    private int NUM_FIELDS = 0;
    private HashMap<String, String> QUERY_ARGS;
    private ProgressDialog pDialog;
    JSONParserHTTPRequest jphtr = new JSONParserHTTPRequest();
    private static String url_create_element = "http://androidapp.kitegacc.org/create_element.php";
    public DatePickerDialog datePickerDialog;
    public SimpleDateFormat dateFormatter;
    public ElementPickerDialog elementPicker;

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
            case "community":
                createCommunityAccountForm();
                break;
            case "member":
                createMemberForm(bundle);
                break;
            case "meeting":
                createMeetingForm(bundle);
                break;
            case "loan":
                createLoanForm(bundle);
                break;
            case "payment":
                createPaymentForm(bundle);
                break;
            case "business":
                createBusinessForm(bundle);
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

    public void createCommunityAccountForm() {
        NUM_FIELDS = 5;
        QUERY_ARGS.put("form_type", "community");
        setTitle("Create Community Account");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Location");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Location");

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Community Cash Balance");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Community Cash Balance");
        FORM_FIELD_2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Vicoba Balance");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Vicoba Balance");
        FORM_FIELD_3.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        INPUT_LAYOUT_4.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_4.setHint("Username");
        FORM_FIELD_4.setVisibility(View.VISIBLE);
        FORM_FIELD_4.setHint("Username");

        INPUT_LAYOUT_5.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_5.setHint("Password");
        FORM_FIELD_5.setVisibility(View.VISIBLE);
        FORM_FIELD_5.setHint("Password");
    }

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
        FORM_FIELD_2.setInputType(InputType.TYPE_CLASS_NUMBER);

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

    public void createMeetingForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        NUM_FIELDS = 3;
        QUERY_ARGS.put("form_type", "meeting");
        QUERY_ARGS.put("community_id", COMMUNITY_ID);
        setTitle("Create Meeting");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Date");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Date");
        FORM_FIELD_1.setInputType(InputType.TYPE_NULL);
        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                FORM_FIELD_1.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        FORM_FIELD_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Business Summary");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Business Summary");
        FORM_FIELD_2.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Meeting Summary");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Meeting Summary");
        FORM_FIELD_3.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
    }

    public void createLoanForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        NUM_FIELDS = 3;
        QUERY_ARGS.put("form_type", "loan");
        QUERY_ARGS.put("community_id", COMMUNITY_ID);
        setTitle("Create Loan");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Award Date");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Award Date");
        FORM_FIELD_1.setInputType(InputType.TYPE_NULL);
        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                FORM_FIELD_1.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        FORM_FIELD_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Loan Amount");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Loan Amount");
        FORM_FIELD_2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Remaining Balance");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Remaining Balance");
        FORM_FIELD_3.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void createPaymentForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        LOAN_ID = bundle.getString("loan_id");
        NUM_FIELDS = 4;
        QUERY_ARGS.put("form_type", "payment");
        QUERY_ARGS.put("loan_id", LOAN_ID);
        setTitle("Create Payment");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Member Making Payment");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Member Making Payment");
        FORM_FIELD_2.setInputType(InputType.TYPE_NULL);
        elementPicker = new ElementPickerDialog("member", this);
        FORM_FIELD_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elementPicker.pickElement();
                MEMBER_ID = elementPicker.getID();
                FORM_FIELD_1.setText(elementPicker.getDisplay());
            }
        });

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Payment Date");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Payment Date");
        FORM_FIELD_2.setInputType(InputType.TYPE_NULL);
        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                FORM_FIELD_2.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        FORM_FIELD_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Expected Amount");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Expected Amount");
        FORM_FIELD_3.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        INPUT_LAYOUT_4.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_4.setHint("Actual Amount");
        FORM_FIELD_4.setVisibility(View.VISIBLE);
        FORM_FIELD_4.setHint("Actual Amount");
        FORM_FIELD_4.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void createBusinessForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        NUM_FIELDS = 2;
        QUERY_ARGS.put("form_type", "business");
        QUERY_ARGS.put("community_id", COMMUNITY_ID);
        QUERY_ARGS.put("meeting_id", "-1");
        QUERY_ARGS.put("business_status", "active");
        setTitle("Create Business");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Business Name");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Business Name");

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Business Summary");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Business Summary");
    }

    public void submitCreateForm(View view) {
        if(isFormComplete()) {
            switch (FORM_TYPE) {
                case "member":
                    submitCreateMemberForm();
                    break;
                case "community":
                    submitCreateCommunityAccountForm();
                    break;
                case "meeting":
                    submitCreateMeetingForm();
                    break;
                case "loan":
                    submitCreateLoanForm();
                    break;
                case "payment":
                    submitCreatePaymentForm();
                    break;
                case "business":
                    submitCreateBusinessForm();
                default:
                    break;
            }
        }
    }

    public void submitCreateMemberForm() {
        QUERY_ARGS.put("name", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("age", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("emergency_contact", FORM_FIELD_3.getText().toString());
        QUERY_ARGS.put("residence", FORM_FIELD_4.getText().toString());
        QUERY_ARGS.put("kin_or_spouse", FORM_FIELD_5.getText().toString());
        new FormPoster().execute();
    }

    public void submitCreateCommunityAccountForm() {
        QUERY_ARGS.put("location", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("com_balance", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("vicoba_balance", FORM_FIELD_3.getText().toString());
        QUERY_ARGS.put("username", FORM_FIELD_4.getText().toString());
        QUERY_ARGS.put("password", FORM_FIELD_5.getText().toString());
        new FormPoster().execute();
    }

    public void submitCreateMeetingForm() {
        QUERY_ARGS.put("date_time", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("business_summary", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("meeting_summary", FORM_FIELD_3.getText().toString());
        new FormPoster().execute();
    }

    public void submitCreateLoanForm() {
        QUERY_ARGS.put("award_date", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("amount", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("balance", FORM_FIELD_3.getText().toString());
        new FormPoster().execute();
    }

    public void submitCreatePaymentForm() {
        QUERY_ARGS.put("member_id", MEMBER_ID);
        QUERY_ARGS.put("payment_date", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("expected_amount", FORM_FIELD_3.getText().toString());
        QUERY_ARGS.put("actual_amount", FORM_FIELD_3.getText().toString());
        new FormPoster().execute();
    }

    public void submitCreateBusinessForm() {
        QUERY_ARGS.put("name", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("business_summary", FORM_FIELD_2.getText().toString());
        new FormPoster().execute();
    }

    public boolean isFormComplete() {
        if(NUM_FIELDS >= 1) {
            if(FORM_FIELD_1.getText().toString().trim().equals("")) return false;
        }
        if(NUM_FIELDS >= 2) {
            if(FORM_FIELD_2.getText().toString().trim().equals("")) return false;
        }
        if(NUM_FIELDS >= 3) {
            if(FORM_FIELD_3.getText().toString().trim().equals("")) return false;
        }
        if(NUM_FIELDS >= 4) {
            if(FORM_FIELD_4.getText().toString().trim().equals("")) return false;
        }
        if(NUM_FIELDS >= 5) {
            if(FORM_FIELD_5.getText().toString().trim().equals("")) return false;
        }
        if(NUM_FIELDS >= 6) {
            if(FORM_FIELD_6.getText().toString().trim().equals("")) return false;
        }
        if(NUM_FIELDS >= 7) {
            if(FORM_FIELD_7.getText().toString().trim().equals("")) return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            elementPicker.setID(data.getExtras().getString("element_id"));
            elementPicker.setDisplay(data.getExtras().getString("element_display"));
        }
    }

    /**
     * Background Async Task to Load all members by making HTTP Request
     * */
    class FormPoster extends AsyncTask<Void, Void, Boolean> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateFormActivity.this);
            pDialog.setMessage("Submitting form. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        @Override
        protected Boolean doInBackground(Void... params) {
            // Building Parameters
//            HashMap<String, String> params = new HashMap<>();
//            params.put("community_id", Integer.toString(COMMUNITY_ID));
//            // getting JSON string from URL
            JSONObject json = jphtr.makeHttpRequest(url_create_element, "GET", QUERY_ARGS);

            // Check your log cat for JSON response
            Log.d("All Products: ", json.toString());

            try {
                int success = json.getInt("success");
                if(success == 1) {
                    return true;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(final Boolean success) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if(success) {
                Toast.makeText(CreateFormActivity.this, "Successfully created " + FORM_TYPE + "!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(CreateFormActivity.this, "Error creating " + FORM_TYPE + ".", Toast.LENGTH_LONG).show();
            }

        }

    }
}
