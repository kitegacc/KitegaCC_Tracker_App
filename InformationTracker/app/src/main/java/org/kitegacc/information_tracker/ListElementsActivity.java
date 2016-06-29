package org.kitegacc.information_tracker;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;

public class ListElementsActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private JSONParserHTTPRequest jphtr = new JSONParserHTTPRequest();

    private ArrayList<HashMap<String, String>> elementsList;
    private HashMap<String, String> params;

    public static int COMMUNITY_ID = 0;
    // url to get all members list
    private static String URL_LIST_ELEMENTS = "http://androidapp.kitegacc.org/get_list_elements.php"; // get_all_community_members.php"; //?community_id=0"; // + COMMUNITY_ID;

    // JSON Node names
    private String TAG_SUCCESS = "success";
    // private String TAG_MEMBERS = "members";
    private String ELEMENT_ID = "";
    private String ELEMENT_DISPLAY = "element_display";
    private String VIEW_TYPE = "view_type";
    private String LIST_TYPE = "";
    private String JSON_TAG = "";
    private String BASE_ID = "";
    private String SECURITY_KEY = "E92FC684-612B-45A9-B55F-F79E75BAF60B";

    // products JSONArray
    private JSONArray elements = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elements_list);

        // COMMUNITY_ID = Integer.parseInt(getIntent().getExtras().getString("community_id"));
        BASE_ID = getIntent().getExtras().getString("base_id");
        LIST_TYPE = getIntent().getExtras().getString("LIST_TYPE");
        params = new HashMap<>();
        params.put("function", LIST_TYPE);
        params.put("security_key", SECURITY_KEY);

        switch (LIST_TYPE) {
            case "community_members":
                ELEMENT_ID = "member_id";
                JSON_TAG = "members";
                params.put("community_id", BASE_ID);
                break;
            case "community_meetings":
                ELEMENT_ID = "meeting_id";
                JSON_TAG = "meetings";
                params.put("community_id", BASE_ID);
                break;
            case "community_loans":
                ELEMENT_ID = "loan_id";
                JSON_TAG = "loans";
                params.put("community_id", BASE_ID);
                break;
            default:
                break;
        }

        // Hashmap for ListView
        elementsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadElements().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ElementDetailActivity.class);

                // getting values from selected ListItem
                String element_id = ((TextView) view.findViewById(R.id.element_id_list_item)).getText()
                        .toString();

                // send element_id to the element detail page
                intent.putExtra(ELEMENT_ID, element_id);

                Bundle extras = getIntent().getExtras();
                String click_action = extras.getString("CLICK_ACTION");

                // specify what type of element to retrieve
                switch (click_action) {
                    case "MEMBER_PAGE":
                        intent.putExtra(VIEW_TYPE, "member");
                        break;
                    case "MEETING_PAGE":
                        intent.putExtra(VIEW_TYPE, "meeting");
                        break;
                    case "LOAN_PAGE":
                        intent.putExtra(VIEW_TYPE, "loan");
                        break;
                    default:
                        break;
                }

                // starting new activity and expecting some response back
                startActivityForResult(intent, 100);
            }
        });

    }

    // Response from Edit Member Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all members by making HTTP Request
     * */
    class LoadElements extends AsyncTask<String, String, String> {

        private String getElementDisplay(JSONObject jObj) throws JSONException {
            switch(JSON_TAG) {
                case "members":
                    return jObj.getString("name");
                case "meetings":
                    return jObj.getString("date_time");
                case "loans":
                    String award_date = jObj.getString("award_date");
                    String amount = jObj.getString("amount");
                    return award_date + " amount: " + amount;
                default:
                    return "ERROR POPULATING DATA";
            }
        }

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListElementsActivity.this);
            pDialog.setMessage("Loading elements. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All elements from url
         * */
        protected String doInBackground(String... args) {
            // Finish Building Parameters
            // params.put("community_id", Integer.toString(COMMUNITY_ID));
            // getting JSON string from URL
            JSONObject json = jphtr.makeHttpRequest(URL_LIST_ELEMENTS, "GET", params);

            // Check your log cat for JSON response
            Log.d("All Elements: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // elements found
                    // Getting Array of Elements
                    elements = json.getJSONArray(JSON_TAG);

                    // looping through All Elements
                    for (int i = 0; i < elements.length(); i++) {
                        JSONObject c = elements.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(ELEMENT_ID);
                        String display = getElementDisplay(c);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(ELEMENT_ID, id);
                        map.put(ELEMENT_DISPLAY, display);

                        // adding HashList to ArrayList
                        elementsList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            ListElementsActivity.this, elementsList,
                            R.layout.element_list_item, new String[] { ELEMENT_ID,
                            ELEMENT_DISPLAY},
                            new int[] { R.id.element_id_list_item, R.id.element_detail_list_item });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}



























