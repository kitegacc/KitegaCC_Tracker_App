package org.kitegacc.information_tracker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by philip on 6/24/16.
 */

public class UpdateFormActivity extends AppCompatActivity {

    private String COMMUNITY_ID;
    private String FORM_TYPE = "";
    private String FORM_ID = "";
    private String FORM_VALUE = "";
    private int NUM_FIELDS = 0;
    private HashMap<String, String> QUERY_ARGS;
    private ProgressDialog pDialog;
    JSONParserHTTPRequest jphtr = new JSONParserHTTPRequest();
    private static String url_update_element = "http://androidapp.kitegacc.org/update_element.php";
    public DatePickerDialog datePickerDialog;
    public SimpleDateFormat dateFormatter;

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
        setContentView(R.layout.activity_update_form);

        attachFormFields();
        QUERY_ARGS = new HashMap<>();

        Bundle bundle = getIntent().getExtras();
        FORM_TYPE = bundle.getString("form");
        // FORM_ID = bundle.getString("form_id");
        // FORM_VALUE = bundle.getString("form_value");

        switch (FORM_TYPE) {
            case "community":
                updateCommunityAccountForm();
                break;
            case "member":
                updateMemberForm(bundle);
                break;
            case "meeting":
                updateMeetingForm(bundle);
                break;
            case "loan":
                updateLoanForm(bundle);
                break;
            case "payment":
                updatePaymentForm(bundle);
                break;
            case "business":
                updateBusinessForm(bundle);
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

    public void updateCommunityAccountForm() {
        NUM_FIELDS = 5;
        QUERY_ARGS.put("form_type", "community");
        setTitle("Update Community Account");

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

    public void updateMemberForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        NUM_FIELDS = 5;
        QUERY_ARGS.put("form_type", "member");
        // QUERY_ARGS.put("community_id", COMMUNITY_ID);
        QUERY_ARGS.put("member_id", bundle.getString("member_id"));
        setTitle("Update Member");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Name");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Name");
        FORM_FIELD_1.setText(bundle.getString("name"));

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Age");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Age");
        FORM_FIELD_2.setInputType(InputType.TYPE_CLASS_NUMBER);
        FORM_FIELD_2.setText(bundle.getString("age"));

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Emergency Contact");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Emergency Contact");
        FORM_FIELD_3.setText(bundle.getString("emergency_contact"));

        INPUT_LAYOUT_4.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_4.setHint("Residence");
        FORM_FIELD_4.setVisibility(View.VISIBLE);
        FORM_FIELD_4.setHint("Residence");
        FORM_FIELD_4.setText(bundle.getString("residence"));

        INPUT_LAYOUT_5.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_5.setHint("Kin / Spouse");
        FORM_FIELD_5.setVisibility(View.VISIBLE);
        FORM_FIELD_5.setHint("Kin / Spouse");
        FORM_FIELD_5.setText(bundle.getString("kin_or_spouse"));
    }

    public void updateMeetingForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        NUM_FIELDS = 4;
        QUERY_ARGS.put("form_type", "meeting");
        // QUERY_ARGS.put("community_id", COMMUNITY_ID);
        QUERY_ARGS.put("meeting_id", bundle.getString("meeting_id"));
        setTitle("Update Meeting");

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
        FORM_FIELD_1.setText(bundle.getString("date_time"));

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Time");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Time");
        FORM_FIELD_2.setText(bundle.getString("meeting_time"));

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Business Summary");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Business Summary");
        FORM_FIELD_3.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        FORM_FIELD_3.setText(bundle.getString("business_summary"));

        INPUT_LAYOUT_4.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_4.setHint("Meeting Summary");
        FORM_FIELD_4.setVisibility(View.VISIBLE);
        FORM_FIELD_4.setHint("Meeting Summary");
        FORM_FIELD_4.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        FORM_FIELD_4.setText(bundle.getString("meeting_summary"));
    }

    public void updateLoanForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        NUM_FIELDS = 3;
        QUERY_ARGS.put("form_type", "loan");
        // QUERY_ARGS.put("community_id", COMMUNITY_ID);
        QUERY_ARGS.put("loan_id", bundle.getString("loan_id"));
        setTitle("Update Loan");

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
        FORM_FIELD_1.setText(bundle.getString("award_date"));

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Loan Amount");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Loan Amount");
        FORM_FIELD_2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        FORM_FIELD_2.setText(bundle.getString("amount"));

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Remaining Balance");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Remaining Balance");
        FORM_FIELD_3.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        FORM_FIELD_3.setText(bundle.getString("balance"));
    }

    public void updatePaymentForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        String payment_id = bundle.getString("payment_id");
        String loan_id = bundle.getString("loan_id");
        String member_id = bundle.getString("member_id");
        NUM_FIELDS = 3;
        QUERY_ARGS.put("form_type", "payment");
        QUERY_ARGS.put("payment_id", payment_id);
        QUERY_ARGS.put("loan_id", loan_id);
        QUERY_ARGS.put("member_id", member_id);
        setTitle("Update Payment");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Payment Date");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Payment Date");
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
        FORM_FIELD_1.setText(bundle.getString("payment_date"));

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Expected Amount");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Expected Amount");
        FORM_FIELD_2.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        FORM_FIELD_2.setText(bundle.getString("expected_amount"));

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Actual Amount");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Actual Amount");
        FORM_FIELD_3.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        FORM_FIELD_3.setText(bundle.getString("actual_amount"));
    }

    public void updateBusinessForm(Bundle bundle) {
        COMMUNITY_ID = bundle.getString("community_id");
        NUM_FIELDS = 3;
        QUERY_ARGS.put("form_type", "business");

        QUERY_ARGS.put("business_id", bundle.getString("business_id"));
        QUERY_ARGS.put("community_id", COMMUNITY_ID);
        QUERY_ARGS.put("meeting_id", bundle.getString("meeting_id"));
        QUERY_ARGS.put("business_status", "active");
        setTitle("Update Business");

        INPUT_LAYOUT_1.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_1.setHint("Business Name");
        FORM_FIELD_1.setVisibility(View.VISIBLE);
        FORM_FIELD_1.setHint("Business Name");
        FORM_FIELD_1.setText(bundle.getString("name"));

        INPUT_LAYOUT_2.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_2.setHint("Business Summary");
        FORM_FIELD_2.setVisibility(View.VISIBLE);
        FORM_FIELD_2.setHint("Business Summary");
        FORM_FIELD_2.setText(bundle.getString("business_summary"));

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Business Status");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Business Status");
        FORM_FIELD_3.setText(bundle.getString("business_status"));
    }

    public void submitUpdateForm(View view) {
        if(isFormComplete()) {
            switch (FORM_TYPE) {
                case "member":
                    updateCreateMemberForm();
                    break;
                case "community":
                    updateCreateCommunityAccountForm();
                    break;
                case "meeting":
                    updateCreateMeetingForm();
                    break;
                case "loan":
                    updateCreateLoanForm();
                    break;
                case "payment":
                    updateCreatePaymentForm();
                    break;
                case "business":
                    updateCreateBusinessForm();
                default:
                    break;
            }
        }
    }

    public void updateCreateMemberForm() {
        QUERY_ARGS.put("name", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("age", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("emergency_contact", FORM_FIELD_3.getText().toString());
        QUERY_ARGS.put("residence", FORM_FIELD_4.getText().toString());
        QUERY_ARGS.put("kin_or_spouse", FORM_FIELD_5.getText().toString());
        Log.d("Update Member Args: ", QUERY_ARGS.toString());
        new FormPoster().execute();
    }

    public void updateCreateCommunityAccountForm() {
        QUERY_ARGS.put("location", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("com_balance", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("vicoba_balance", FORM_FIELD_3.getText().toString());
        QUERY_ARGS.put("username", FORM_FIELD_4.getText().toString());
        QUERY_ARGS.put("password", FORM_FIELD_5.getText().toString());
        Log.d("Update Community Args: ", QUERY_ARGS.toString());
        new FormPoster().execute();
    }

    public void updateCreateMeetingForm() {
        QUERY_ARGS.put("date_time", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("meeting_time", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("business_summary", FORM_FIELD_3.getText().toString());
        QUERY_ARGS.put("meeting_summary", FORM_FIELD_4.getText().toString());
        Log.d("Update Meeting Args: ", QUERY_ARGS.toString());
        new FormPoster().execute();
    }

    public void updateCreateLoanForm() {
        QUERY_ARGS.put("award_date", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("amount", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("balance", FORM_FIELD_3.getText().toString());
        Log.d("Update Loan Args: ", QUERY_ARGS.toString());
        new FormPoster().execute();
    }

    public void updateCreatePaymentForm() {
        QUERY_ARGS.put("payment_date", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("expected_amount", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("actual_amount", FORM_FIELD_3.getText().toString());
        Log.d("Update Payment Args: ", QUERY_ARGS.toString());
        new FormPoster().execute();
    }

    public void updateCreateBusinessForm() {
        QUERY_ARGS.put("name", FORM_FIELD_1.getText().toString());
        QUERY_ARGS.put("business_summary", FORM_FIELD_2.getText().toString());
        QUERY_ARGS.put("business_status", FORM_FIELD_3.getText().toString());
        Log.d("Update Business Args: ", QUERY_ARGS.toString());
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
            pDialog = new ProgressDialog(UpdateFormActivity.this);
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
            JSONObject json = jphtr.makeHttpRequest(url_update_element, "GET", QUERY_ARGS);

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
//
//            try {
//                // Checking for SUCCESS TAG
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//                    // products found
//                    // Getting Array of Products
//                    members = json.getJSONArray(TAG_MEMBERS);
//
//                    // looping through All Products
//                    for (int i = 0; i < members.length(); i++) {
//                        JSONObject c = members.getJSONObject(i);
//
//                        // Storing each json item in variable
//                        String id = c.getString(TAG_MID);
//                        String name = c.getString(TAG_NAME);
//
//                        // creating new HashMap
//                        HashMap<String, String> map = new HashMap<String, String>();
//
//                        // adding each child node to HashMap key => value
//                        map.put(TAG_MID, id);
//                        map.put(TAG_NAME, name);
//
//                        // adding HashList to ArrayList
//                        membersList.add(map);
//                    }
//                } /*else {
//                    // no products found
//                    // Launch Add New product Activity
//                    Intent i = new Intent(getApplicationContext(),
//                            NewProductActivity.class);
//                    // Closing all previous activities
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);
//                }*/
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            return false;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(final Boolean success) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    /**
//                     * Updating parsed JSON data into ListView
//                     * */
//                    ListAdapter adapter = new SimpleAdapter(
//                            ListElementsActivity.this, membersList,
//                            R.layout.element_list_item, new String[] { TAG_MID,
//                            TAG_NAME},
//                            new int[] { R.id.member_id_list_item, R.id.member_name_list_item });
//                    // updating listview
//                    setListAdapter(adapter);
//                }
//            });
            if(success) {
                Toast.makeText(UpdateFormActivity.this, "Successfully updated " + FORM_TYPE + "!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(UpdateFormActivity.this, "Error updating " + FORM_TYPE + ".", Toast.LENGTH_LONG).show();
            }

        }

    }
}
