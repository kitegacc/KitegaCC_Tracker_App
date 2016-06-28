package org.kitegacc.information_tracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CommunityHomePage extends AppCompatActivity {

    public static int COMMUNITY_ID = -1;
    public String USERNAME = "";
    public String LOCATION = "";
    public String COM_BALANCE = "";
    public String VICOBA_BALANCE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communityhomepage);

        try {
            Bundle extras = getIntent().getExtras();
            COMMUNITY_ID = Integer.parseInt(extras.getString("community_id"));
            USERNAME = extras.getString("username");
            LOCATION = extras.getString("location");
            COM_BALANCE = extras.getString("com_balance");
            VICOBA_BALANCE = extras.getString("vicoba_balance");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewMembers(View view) {
        Intent intent = new Intent(CommunityHomePage.this, CommunityMembersList.class);
        intent.putExtra("CLICK_ACTION", "MEMBER_PAGE"); // other will be "SELECT_MEMBER"
        intent.putExtra("community_id", Integer.toString(COMMUNITY_ID));
        startActivity(intent);
    }

    public void viewMeetings(View view) {

    }

    public void viewLoans(View view) {

    }

    public void viewBusinesses(View view) {

    }

    // meeting, member, loan, payment, business
    public void createNewElement(View view) {
        final AlertDialog levelDialog;
        final CharSequence[] items = {" Member "," Meeting "," Loan "," Payment ", " Business "};

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
                        createPayment();
                        break;
                    case 4:
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

    public void createPayment() {

    }

    public void createBusiness() {

    }
}
