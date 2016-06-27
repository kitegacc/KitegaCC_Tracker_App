package org.kitegacc.information_tracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ElementDetailActivity extends AppCompatActivity {

    private String VIEW_TYPE = "";
    private Bundle bundle;
    private String ELEMENT_ID = "";
    private String VIEW_ELEMENT_URL = "http://androidapp.kitegacc.org/view_element.php";

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

    public void viewMember(JSONObject json) {
        setTitle("Member Details");
        String member_id = "";
        try {
            member_id = json.getString("member_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView mid_display = (TextView) findViewById(R.id.detail_page_field1);
        String mid_display_text = "member_id: " + member_id;
        mid_display.setText(mid_display_text);
    }

    public void viewMeeting(JSONObject json) {

    }

    public void viewLoan(JSONObject json) {

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
            pDialog = new ProgressDialog(this.context);    // CommunityMembersList.this
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

            // getting JSON string from URL
            json = jphtr.makeHttpRequest(REQUEST_URL, "GET", params);

            // Check your log cat for JSON response
            Log.d("View Element Response: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    // products found

                    // Getting Array of Products
//                members = json.getJSONArray(TAG_MEMBERS);
//
//                // looping through All Products
//                for (int i = 0; i < members.length(); i++) {
//                    JSONObject c = members.getJSONObject(i);
//
//                    // Storing each json item in variable
//                    String id = c.getString(TAG_MID);
//                    String name = c.getString(TAG_NAME);
//
//                    // creating new HashMap
//                    HashMap<String, String> map = new HashMap<String, String>();
//
//                    // adding each child node to HashMap key => value
//                    map.put(TAG_MID, id);
//                    map.put(TAG_NAME, name);
//
//                    // adding HashList to ArrayList
//                    membersList.add(map);
//                }
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
                                viewCommunityAccount(json);
                                break;
                            case "member":
                                viewMember(json);
                                break;
                            case "meeting":
                                viewMeeting(json);
                                break;
                            case "loan":
                                viewLoan(json);
                                break;
                            case "payment":
                                break;
                            case "business":
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
//                ListAdapter adapter = new SimpleAdapter(
//                        CommunityMembersList.this, membersList,
//                        R.layout.member_list_item, new String[] { TAG_MID,
//                        TAG_NAME},
//                        new int[] { R.id.member_id_list_item, R.id.member_name_list_item });
//                // updating listview
//                setListAdapter(adapter);
                }
            });

        }

    }

}
