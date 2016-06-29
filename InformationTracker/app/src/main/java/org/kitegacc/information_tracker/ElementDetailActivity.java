package org.kitegacc.information_tracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;

public class ElementDetailActivity extends AppCompatActivity {

    private String VIEW_TYPE = "";
    private Bundle bundle;
    private String ELEMENT_ID = "";
    private String VIEW_ELEMENT_URL = "http://androidapp.kitegacc.org/view_element.php";
    private TextView view_display1;
    private TextView view_display2;
    private TextView view_display3;
    private TextView view_display4;
    private TextView view_display5;
    private Button view_button1;
    private Button view_button2;
    private Button view_button3;
    private Button view_button4;
    private Button view_button5;
    private Button view_button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();
        VIEW_TYPE = bundle.getString("view_type");

        switch (VIEW_TYPE) {
            case "community":
                ELEMENT_ID = bundle.getString("community_id");
                break;
            case "member":
                ELEMENT_ID = bundle.getString("member_id");
                break;
            case "meeting":
                ELEMENT_ID = bundle.getString("meeting_id");
                break;
            case "loan":
                ELEMENT_ID = bundle.getString("loan_id");
                break;
            case "payment":
                ELEMENT_ID = bundle.getString("payment_id");
                break;
            case "business":
                ELEMENT_ID = bundle.getString("business_id");
                break;
            default:
                break;
        }

        LoadElementForView loadElementView = new LoadElementForView(ElementDetailActivity.this, VIEW_ELEMENT_URL, VIEW_TYPE, ELEMENT_ID);
        loadElementView.execute();
    }

    public void viewCommunityAccount(JSONObject json) {

    }

    public String member_id = "";
    public String name = "";
    public String age = "";
    public String emergency_contact = "";
    public String residence = "";
    public String kin_or_spouse = "";
    public String community_id = "";

    public void viewMember(JSONObject json) {
        setTitle("Member Details");
        try {
            member_id = json.getString("member_id");
            name = json.getString("name");
            age = json.getString("age");
            emergency_contact = json.getString("emergency_contact");
            residence = json.getString("residence");
            kin_or_spouse = json.getString("kin_or_spouse");
            community_id = json.getString("community_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        view_display1 = (TextView) findViewById(R.id.detail_page_field1);
        view_display1.setVisibility(View.VISIBLE);
        String mid_display_text1 = "Name: " + name;
        view_display1.setText(mid_display_text1);

        view_display2 = (TextView) findViewById(R.id.detail_page_field2);
        view_display2.setVisibility(View.VISIBLE);
        String mid_display_text2 = "Age: " + age;
        view_display2.setText(mid_display_text2);

        view_display3 = (TextView) findViewById(R.id.detail_page_field3);
        view_display3.setVisibility(View.VISIBLE);
        String mid_display_text3 = "Emergency Contact: " + emergency_contact;
        view_display3.setText(mid_display_text3);

        view_display4 = (TextView) findViewById(R.id.detail_page_field4);
        view_display4.setVisibility(View.VISIBLE);
        String mid_display_text4 = "Residence: " + residence;
        view_display4.setText(mid_display_text4);

        view_display5 = (TextView) findViewById(R.id.detail_page_field5);
        view_display5.setVisibility(View.VISIBLE);
        String mid_display_text5 = "Kin/Spouse: " + kin_or_spouse;
        view_display5.setText(mid_display_text5);

        view_button1 = (Button) findViewById(R.id.detail_page_button1);
        view_button1.setVisibility(View.VISIBLE);
        view_button1.setText("View Meetings");
        view_button1.setOnClickListener(new ListElementsButtonListener("member_meetings"));

        view_button2 = (Button) findViewById(R.id.detail_page_button2);
        view_button2.setVisibility(View.VISIBLE);
        view_button2.setText("View Loans");
        view_button2.setOnClickListener(new ListElementsButtonListener("member_loans"));

        view_button3 = (Button) findViewById(R.id.detail_page_button3);
        view_button3.setVisibility(View.VISIBLE);
        view_button3.setText("View Payments");
        view_button3.setOnClickListener(new ListElementsButtonListener("member_payments"));
        view_button3.setEnabled(false);

        view_button4 = (Button) findViewById(R.id.detail_page_button4);
        view_button4.setVisibility(View.VISIBLE);
        view_button4.setText("View Businesses");
        view_button4.setOnClickListener(new ListElementsButtonListener("member_businesses"));
        view_button4.setEnabled(false);

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Member");
        view_button5.setOnClickListener(new EditElementButtonListener());

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Member");
        view_button6.setOnClickListener(new DeleteElementButtonListener());
    }

    public String meeting_id = "";
    public String date_time = "";
    public String business_summary = "";
    public String meeting_summary = "";

    public void viewMeeting(JSONObject json) {
        setTitle("Meeting Details");
        try {
            meeting_id = json.getString("meeting_id");
            community_id = json.getString("community_id");
            date_time = json.getString("date_time");
            business_summary = json.getString("business_summary");
            meeting_summary = json.getString("meeting_summary");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        view_display1 = (TextView) findViewById(R.id.detail_page_field1);
        view_display1.setVisibility(View.VISIBLE);
        String mid_display_text1 = "Date: " + date_time;
        view_display1.setText(mid_display_text1);

        view_display2 = (TextView) findViewById(R.id.detail_page_field2);
        view_display2.setVisibility(View.VISIBLE);
        String mid_display_text2 = "Meeting Summary: " + meeting_summary;
        view_display2.setText(mid_display_text2);

        view_display3 = (TextView) findViewById(R.id.detail_page_field3);
        view_display3.setVisibility(View.VISIBLE);
        String mid_display_text3 = "Business Summary: " + business_summary;
        view_display3.setText(mid_display_text3);

        view_button2 = (Button) findViewById(R.id.detail_page_button2);
        view_button2.setVisibility(View.VISIBLE);
        view_button2.setText("View Attending Members");
        view_button2.setOnClickListener(new ListElementsButtonListener("meeting_members"));
        view_button2.setEnabled(false);

        view_button3 = (Button) findViewById(R.id.detail_page_button3);
        view_button3.setVisibility(View.VISIBLE);
        view_button3.setText("View Loans Awarded");
        view_button3.setOnClickListener(new ListElementsButtonListener("meeting_loans"));
        view_button3.setEnabled(false);

        view_button4 = (Button) findViewById(R.id.detail_page_button4);
        view_button4.setVisibility(View.VISIBLE);
        view_button4.setText("View Businesses Created");
        view_button4.setOnClickListener(new ListElementsButtonListener("meeting_businesses"));
        view_button4.setEnabled(false);

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Meeting");
        view_button5.setOnClickListener(new EditElementButtonListener());

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Meeting");
        view_button6.setOnClickListener(new DeleteElementButtonListener());
    }

    public String loan_id = "";
    public String award_date = "";
    public String amount = "";
    public String balance = "";

    public void viewLoan(JSONObject json) {
        setTitle("Loan Details");
        try {
            loan_id = json.getString("loan_id");
            community_id = json.getString("community_id");
            award_date = json.getString("award_date");
            amount = json.getString("amount");
            balance = json.getString("balance");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        view_display1 = (TextView) findViewById(R.id.detail_page_field1);
        view_display1.setVisibility(View.VISIBLE);
        String mid_display_text1 = "Award Date: " + award_date;
        view_display1.setText(mid_display_text1);

        view_display2 = (TextView) findViewById(R.id.detail_page_field2);
        view_display2.setVisibility(View.VISIBLE);
        String mid_display_text2 = "Amount: " + amount;
        view_display2.setText(mid_display_text2);

        view_display3 = (TextView) findViewById(R.id.detail_page_field3);
        view_display3.setVisibility(View.VISIBLE);
        String mid_display_text3 = "Balance: " + balance;
        view_display3.setText(mid_display_text3);

        view_button4 = (Button) findViewById(R.id.detail_page_button4);
        view_button4.setVisibility(View.VISIBLE);
        view_button4.setText("View Loan Payments");
        view_button4.setOnClickListener(new ListElementsButtonListener("loan_payments"));
        view_button4.setEnabled(false);

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Loan");
        view_button5.setOnClickListener(new EditElementButtonListener());

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Loan");
        view_button6.setOnClickListener(new DeleteElementButtonListener());
    }

    public String payment_id = "";
    public String payment_date = "";
    public String expected_amount = "";
    public String actual_amount = "";

    public void viewPayment(JSONObject json) {
        setTitle("Payment Details");
        try {
            payment_id = json.getString("payment_id");
            loan_id = json.getString("loan_id");
            member_id = json.getString("member_id");
            payment_date = json.getString("payment_date");
            expected_amount = json.getString("expected_amount");
            actual_amount = json.getString("actual_amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        view_display1 = (TextView) findViewById(R.id.detail_page_field1);
        view_display1.setVisibility(View.VISIBLE);
        String mid_display_text1 = "member_id: " + "member name!!!!!!!!";
        view_display1.setText(mid_display_text1);

        view_display2 = (TextView) findViewById(R.id.detail_page_field2);
        view_display2.setVisibility(View.VISIBLE);
        String mid_display_text2 = "Payment Date: " + payment_date;
        view_display2.setText(mid_display_text2);

        view_display3 = (TextView) findViewById(R.id.detail_page_field3);
        view_display3.setVisibility(View.VISIBLE);
        String mid_display_text3 = "Amount Due: " + expected_amount;
        view_display3.setText(mid_display_text3);

        view_display4 = (TextView) findViewById(R.id.detail_page_field4);
        view_display4.setVisibility(View.VISIBLE);
        String mid_display_text4 = "Amount Paid: " + actual_amount;
        view_display4.setText(mid_display_text4);

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Payment");
        view_button5.setOnClickListener(new EditElementButtonListener());

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Payment");
        view_button6.setOnClickListener(new DeleteElementButtonListener());
    }

    public void viewBusiness(JSONObject json) {
        setTitle("Business Details");

    }

    class ListElementsButtonListener implements View.OnClickListener {
        private String function;
        public ListElementsButtonListener(String function) {
            this.function = function;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ElementDetailActivity.this, ListElementsActivity.class);
            intent.putExtra("LIST_TYPE", function);
            switch (function) {
                case "member_meetings":
                    intent.putExtra("base_id", member_id);
                    intent.putExtra("CLICK_ACTION", "MEETING_PAGE");
                    break;
                case "member_loans":
                    intent.putExtra("base_id", member_id);
                    intent.putExtra("CLICK_ACTION", "LOAN_PAGE");
                    break;
                case "member_payments":
                    intent.putExtra("base_id", member_id);
                    intent.putExtra("CLICK_ACTION", "PAYMENT_PAGE");
                    break;
                case "member_businesses":
                    intent.putExtra("base_id", member_id);
                    intent.putExtra("CLICK_ACTION", "BUSINESS_PAGE");
                    break;
                case "meeting_members":
                    break;
                case "meeting_loans":
                    break;
                default:
                    Toast.makeText(ElementDetailActivity.this, "Error Viewing Elements: " + function, Toast.LENGTH_LONG).show();
                    break;
            }
            startActivity(intent);
        }
    }

    class EditElementButtonListener implements View.OnClickListener {
        public EditElementButtonListener() {

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(ElementDetailActivity.this, "Edit Element", Toast.LENGTH_LONG).show();
        }
    }

    class DeleteElementButtonListener implements View.OnClickListener {
        public DeleteElementButtonListener() {

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(ElementDetailActivity.this, "Delete Element", Toast.LENGTH_LONG).show();
        }
    }

    class LoadElementForView extends AsyncTask<String, String, Boolean> {

        private ProgressDialog pDialog;
        private Context context;
        private String elementType;
        private String elementID;
        private String REQUEST_URL;
        private JSONParserHTTPRequest jphtr;
        private String SECURITY_KEY = "E92FC684-612B-45A9-B55F-F79E75BAF60B";
        private JSONObject json;
        JSONObject jObject;

        public LoadElementForView(Context context, String requestUrl, String elementType, String elementID) {
            jphtr = new JSONParserHTTPRequest();
            this.context = context;
            this.REQUEST_URL = requestUrl;
            this.elementType = elementType;
            this.elementID = elementID;
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(this.context);    // ListElementsActivity.this
            pDialog.setMessage("Loading " + elementType + ". Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting element from url
         * */
        protected Boolean doInBackground(String... args) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("view_type", elementType);
            params.put("security_key", SECURITY_KEY);
            String elementIDLabel = elementType + "_id";
            params.put(elementIDLabel, elementID);

            System.out.println(params.toString());

            // getting JSON string from URL
            json = jphtr.makeHttpRequest(REQUEST_URL, "GET", params);

            // Check your log cat for JSON response
            Log.d("View Element Response: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    JSONArray jArray = json.getJSONArray(elementType);
                    jObject = jArray.getJSONObject(0);
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(final Boolean success) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    if(success) {
                        switch (elementType) {
                            case "community":
                                viewCommunityAccount(jObject);
                                break;
                            case "member":
                                viewMember(jObject);
                                break;
                            case "meeting":
                                viewMeeting(jObject);
                                break;
                            case "loan":
                                viewLoan(jObject);
                                break;
                            case "payment":
                                viewPayment(jObject);
                                break;
                            case "business":
                                viewBusiness(jObject);
                                break;
                            default:
                                break;
                        }
                        Toast.makeText(ElementDetailActivity.this, "Successfully loaded " + elementType + "!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ElementDetailActivity.this, "Error loading " + elementType + "!", Toast.LENGTH_LONG).show();
                    }
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                }
            });

        }

    }

}
