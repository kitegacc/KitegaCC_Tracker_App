package org.kitegacc.information_tracker;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CreateFormActivity extends AppCompatActivity {

    private String COMMUNITY_ID;
    private String FORM_TYPE = "";
    private int NUM_FIELDS = 0;
    private HashMap<String, String> QUERY_ARGS;
    private ProgressDialog pDialog;
    JSONParserHTTPRequest jphtr = new JSONParserHTTPRequest();
    private static String url_create_element = "http://androidapp.davidclemy.org/create_element.php";

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
                break;
            case "loan":
                break;
            case "payment":
                break;
            case "business":
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

        INPUT_LAYOUT_3.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_3.setHint("Vicoba Balance");
        FORM_FIELD_3.setVisibility(View.VISIBLE);
        FORM_FIELD_3.setHint("Vicoba Balance");

        INPUT_LAYOUT_4.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_4.setHint("Username");
        FORM_FIELD_4.setVisibility(View.VISIBLE);
        FORM_FIELD_4.setHint("Username");

        INPUT_LAYOUT_5.setVisibility(View.VISIBLE);
        INPUT_LAYOUT_5.setHint("Password");
        FORM_FIELD_5.setVisibility(View.VISIBLE);
        FORM_FIELD_5.setHint("Password");
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
                    break;
                case "loan":
                    break;
                case "payment":
                    break;
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
//                            CommunityMembersList.this, membersList,
//                            R.layout.member_list_item, new String[] { TAG_MID,
//                            TAG_NAME},
//                            new int[] { R.id.member_id_list_item, R.id.member_name_list_item });
//                    // updating listview
//                    setListAdapter(adapter);
//                }
//            });
            if(success) {
                Toast.makeText(CreateFormActivity.this, "Successfully created " + FORM_TYPE + "!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(CreateFormActivity.this, "Error creating " + FORM_TYPE + ".", Toast.LENGTH_LONG).show();
            }

        }

    }
}