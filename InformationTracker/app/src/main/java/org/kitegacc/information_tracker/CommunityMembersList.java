package org.kitegacc.information_tracker;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommunityMembersList extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    JSONParserHTTPRequest jphtr = new JSONParserHTTPRequest();

    ArrayList<HashMap<String, String>> membersList;

    public static int COMMUNITY_ID = 0;
    // url to get all members list
    private static String url_all_members = "http://androidapp.davidclemy.org/get_all_community_members.php"; //?community_id=0"; // + COMMUNITY_ID;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MEMBERS = "members";
    private static final String TAG_MID = "member_id";
    private static final String TAG_NAME = "name";

    // products JSONArray
    JSONArray members = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_community_members_list);

        COMMUNITY_ID = Integer.parseInt(getIntent().getExtras().getString("community_id"));

        // Hashmap for ListView
        membersList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllProducts().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String mid = ((TextView) view.findViewById(R.id.member_id_list_item)).getText()
                        .toString();

                Bundle extras = getIntent().getExtras();
                String click_action = "";
                if (extras != null) {
                    click_action = extras.getString("CLICK_ACTION");
                }
                if(click_action.equals("MEMBER_PAGE")) {
                    Intent intent = new Intent(getApplicationContext(), MemberDetailActivity.class);
                    // send member_id to the member detail page
                    intent.putExtra(TAG_MID, mid);
                    // starting new activity and expecting some response back
                    startActivityForResult(intent, 100);
                }
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
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommunityMembersList.this);
            pDialog.setMessage("Loading members. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("community_id", Integer.toString(COMMUNITY_ID));
            // getting JSON string from URL
            JSONObject json = jphtr.makeHttpRequest(url_all_members, "GET", params);

            // Check your log cat for JSON response
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    members = json.getJSONArray(TAG_MEMBERS);

                    // looping through All Products
                    for (int i = 0; i < members.length(); i++) {
                        JSONObject c = members.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_MID);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_MID, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        membersList.add(map);
                    }
                } /*else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            NewProductActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }*/
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
                            CommunityMembersList.this, membersList,
                            R.layout.member_list_item, new String[] { TAG_MID,
                            TAG_NAME},
                            new int[] { R.id.member_id_list_item, R.id.member_name_list_item });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}



























