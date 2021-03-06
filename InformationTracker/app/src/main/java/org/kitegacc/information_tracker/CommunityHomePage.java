package org.kitegacc.information_tracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CommunityHomePage extends AppCompatActivity {

    public static int COMMUNITY_ID = -1;
    public String USERNAME = "";
    public String PASSWORD = "";
    public String LOCATION = "";
    public String COM_BALANCE = "";
    public String VICOBA_BALANCE = "";
    public static String GLOBAL_COMMUNITY_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communityhomepage);

        try {
            Bundle extras = getIntent().getExtras();
            COMMUNITY_ID = Integer.parseInt(extras.getString("community_id"));
            USERNAME = extras.getString("username");
            PASSWORD = extras.getString("password");
            LOCATION = extras.getString("location");
            COM_BALANCE = extras.getString("com_balance");
            VICOBA_BALANCE = extras.getString("vicoba_balance");
            GLOBAL_COMMUNITY_ID = Integer.toString(COMMUNITY_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView com_username_text = (TextView) findViewById(R.id.community_username_text);
        String com_display_text1 = "Username: " + USERNAME;
        com_username_text.setText(com_display_text1);

        TextView com_location_text = (TextView) findViewById(R.id.community_location_text);
        String com_display_text2 = "Location: " + LOCATION;
        com_location_text.setText(com_display_text2);

        TextView com_com_balance_text = (TextView) findViewById(R.id.community_com_balance_text);
        String com_display_text3 = "Community Balance: " + COM_BALANCE;
        com_com_balance_text.setText(com_display_text3);

        TextView com_vicoba_balance_text = (TextView) findViewById(R.id.community_vicoba_balance_text);
        String com_display_text4 = "Vicoba Balance: " + VICOBA_BALANCE;
        com_vicoba_balance_text.setText(com_display_text4);

        // Button businessButton = (Button) findViewById(R.id.btnViewBusinesses);
        // businessButton.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if(id == R.id.action_edit_community) {
            Intent intent = new Intent(CommunityHomePage.this, UpdateFormActivity.class);
            intent.putExtra("form", "community");
            intent.putExtra("community_id", Integer.toString(COMMUNITY_ID));
            intent.putExtra("username", USERNAME);
            intent.putExtra("password", PASSWORD);
            intent.putExtra("location", LOCATION);
            intent.putExtra("com_balance", COM_BALANCE);
            intent.putExtra("vicoba_balance", VICOBA_BALANCE);
            startActivityForResult(intent, 555);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 555) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CommunityHomePage.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void viewMembers(View view) {
        Intent intent = new Intent(CommunityHomePage.this, ListElementsActivity.class);
        intent.putExtra("LIST_TYPE", "community_members"); // other will be "SELECT_MEMBER"
        intent.putExtra("CLICK_ACTION", "MEMBER_PAGE");
        intent.putExtra("base_id", Integer.toString(COMMUNITY_ID));
        // intent.putExtra("community_id", Integer.toString(COMMUNITY_ID));
        startActivity(intent);
    }

    public void viewMeetings(View view) {
        Intent intent = new Intent(CommunityHomePage.this, ListElementsActivity.class);
        intent.putExtra("LIST_TYPE", "community_meetings"); // other will be "SELECT_MEMBER"
        intent.putExtra("CLICK_ACTION", "MEETING_PAGE");
        intent.putExtra("base_id", Integer.toString(COMMUNITY_ID));
        // intent.putExtra("community_id", Integer.toString(COMMUNITY_ID));
        startActivity(intent);
    }

    public void viewLoans(View view) {
        Intent intent = new Intent(CommunityHomePage.this, ListElementsActivity.class);
        intent.putExtra("LIST_TYPE", "community_loans"); // other will be "SELECT_MEMBER"
        intent.putExtra("CLICK_ACTION", "LOAN_PAGE");
        intent.putExtra("base_id", Integer.toString(COMMUNITY_ID));
        // intent.putExtra("community_id", Integer.toString(COMMUNITY_ID));
        startActivity(intent);
    }

    public void viewBusinesses(View view) {
        Intent intent = new Intent(CommunityHomePage.this, ListElementsActivity.class);
        intent.putExtra("LIST_TYPE", "community_businesses"); // other will be "SELECT_MEMBER"
        intent.putExtra("CLICK_ACTION", "BUSINESS_PAGE");
        intent.putExtra("base_id", Integer.toString(COMMUNITY_ID));
        // intent.putExtra("community_id", Integer.toString(COMMUNITY_ID));
        startActivity(intent);
    }

    // meeting, member, loan, payment, business
    public void createNewElement(View view) {
        final AlertDialog levelDialog;
        final CharSequence[] items = {" Member "," Meeting "," Loan "," Business "};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create a new ...");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch(item)
                {
                    case 0:
                        createMember();
                        break;
                    case 1:
                        createMeeting();
                        break;
                    case 2:
                        createLoan();
                        break;
                    case 3:
                        createBusiness();
                        break;
                    default:
                        break;

                }
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public void createMember() {
        Intent intent = new Intent(CommunityHomePage.this, CreateFormActivity.class);
        intent.putExtra("community_id",Integer.toString(COMMUNITY_ID));
        intent.putExtra("form", "member");
        startActivity(intent);
    }

    public void createMeeting() {
        Intent intent = new Intent(CommunityHomePage.this, CreateFormActivity.class);
        intent.putExtra("community_id",Integer.toString(COMMUNITY_ID));
        intent.putExtra("form", "meeting");
        startActivity(intent);
    }

    public void createLoan() {
        Intent intent = new Intent(CommunityHomePage.this, CreateFormActivity.class);
        intent.putExtra("community_id",Integer.toString(COMMUNITY_ID));
        intent.putExtra("form", "loan");
        startActivity(intent);
    }

    public void createBusiness() {
        Intent intent = new Intent(CommunityHomePage.this, CreateFormActivity.class);
        intent.putExtra("community_id",Integer.toString(COMMUNITY_ID));
        intent.putExtra("form", "business");
        startActivity(intent);
    }
}
