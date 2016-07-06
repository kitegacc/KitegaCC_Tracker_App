package org.kitegacc.information_tracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import java.util.List;

public class ElementDetailActivity extends AppCompatActivity {

    private String VIEW_TYPE = "";
    private Bundle bundle;
    private String ELEMENT_ID = "";
    private String VIEW_ELEMENT_URL = "http://androidapp.kitegacc.org/view_element.php";
    private static String url_create_element = "http://androidapp.kitegacc.org/create_element.php";
    private static String url_delete_element = "http://androidapp.kitegacc.org/delete_element.php";
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
    private ElementPickerDialog elementPicker;
    JSONParserHTTPRequest jphtr = new JSONParserHTTPRequest();
    public HashMap<String, String> QUERY_ARGS;
    public String elementType = "";

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 9876) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        if (resultCode == RESULT_OK) {
            elementPicker.setID(data.getExtras().getString("element_id"));
            elementPicker.setDisplay(data.getExtras().getString("element_display"));
        }
        if(VIEW_TYPE.equals("meeting")) {
            addToMeeting();
        }
        if(VIEW_TYPE.equals("business")) {
            addToBusiness();
        }
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

        view_button4 = (Button) findViewById(R.id.detail_page_button4);
        view_button4.setVisibility(View.VISIBLE);
        view_button4.setText("View Businesses");
        view_button4.setOnClickListener(new ListElementsButtonListener("member_businesses"));

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Member");
        view_button5.setOnClickListener(new EditElementButtonListener("member"));

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Member");
        view_button6.setOnClickListener(new DeleteElementButtonListener("member", member_id));
    }

    public String meeting_id = "";
    public String date_time = "";
    public String meeting_time = "";
    public String business_summary = "";
    public String meeting_summary = "";

    public void viewMeeting(JSONObject json) {
        setTitle("Meeting Details");
        try {
            meeting_id = json.getString("meeting_id");
            community_id = json.getString("community_id");
            date_time = json.getString("date_time");
            meeting_time = json.getString("meeting_time");
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
        String mid_display_text2 = "Time: " + meeting_time;
        view_display2.setText(mid_display_text2);

        view_display3 = (TextView) findViewById(R.id.detail_page_field3);
        view_display3.setVisibility(View.VISIBLE);
        String mid_display_text3 = "Meeting Summary: " + meeting_summary;
        view_display3.setText(mid_display_text3);

        view_display4 = (TextView) findViewById(R.id.detail_page_field4);
        view_display4.setVisibility(View.VISIBLE);
        String mid_display_text4 = "Business Summary: " + business_summary;
        view_display4.setText(mid_display_text4);

        view_button2 = (Button) findViewById(R.id.detail_page_button2);
        view_button2.setVisibility(View.VISIBLE);
        view_button2.setText("Remove Meeting Items");
        view_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeMeetingItems();
            }
        });

        view_button3 = (Button) findViewById(R.id.detail_page_button3);
        view_button3.setVisibility(View.VISIBLE);
        view_button3.setText("Add Meeting Items");
        view_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMeetingItems();
            }
        });

        view_button4 = (Button) findViewById(R.id.detail_page_button4);
        view_button4.setVisibility(View.VISIBLE);
        view_button4.setText("View Meeting Items");
        view_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMeetingItems();
            }
        });

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Meeting");
        view_button5.setOnClickListener(new EditElementButtonListener("meeting"));

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Meeting");
        view_button6.setOnClickListener(new DeleteElementButtonListener("meeting", meeting_id));
    }

    public void removeMeetingItems() {
        final AlertDialog levelDialog;
        final CharSequence[] items = {" Remove Attending Member "," Remove Awarded Loan "," Remove Created Business "};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Meeting Items ...");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        new ListElementsButtonListener("meeting_members");
                        break;
                    case 1:
                        new ListElementsButtonListener("meeting_loans");
                        break;
                    case 2:
                        new ListElementsButtonListener("meeting_businesses");
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

    public void addMeetingItems() {
        final AlertDialog levelDialog;
        final CharSequence[] items = {" Add Attending Member "," Add Loans Awarded "," Add Businesses Created "};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Meeting Items ...");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                elementType = "";
                switch(item) {
                    case 0:
                        elementType = "member";
                        break;
                    case 1:
                        elementType = "loan";
                        break;
                    case 2:
                        elementType = "business";
                        break;
                    default:
                        break;
                }
                elementPicker = new ElementPickerDialog(elementType, ElementDetailActivity.this);
                elementPicker.pickElement();
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    public void addToMeeting() {
        String element_id = elementPicker.getID();
        // String element_display = elementPicker.getDisplay();
        QUERY_ARGS = new HashMap<>();
        QUERY_ARGS.put("meeting_id", meeting_id);
        switch (elementType) {
            case "member":
                QUERY_ARGS.put("form_type", "MemberHasMeeting");
                QUERY_ARGS.put("member_id", element_id);
                break;
            case "loan":
                QUERY_ARGS.put("form_type", "LoanAwardedMeeting");
                QUERY_ARGS.put("loan_id", element_id);
                break;
            case "business":
                QUERY_ARGS.put("form_type", "MeetingHasBusiness");
                QUERY_ARGS.put("business_id", element_id);
                break;
            default:
                break;
        }
        Log.d("QUERY_ARGS: ", QUERY_ARGS.toString());
        new FormPoster().execute();
    }

    public void viewMeetingItems() {
        final AlertDialog levelDialog;
        final CharSequence[] items = {" View Attending Members "," View Loans Awarded "," View Businesses Created "};

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("View Meeting Items ...");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        new ListElementsButtonListener("meeting_members").doClick();
                        break;
                    case 1:
                        new ListElementsButtonListener("meeting_loans").doClick();
                        break;
                    case 2:
                        new ListElementsButtonListener("meeting_businesses").doClick();
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

        view_button3 = (Button) findViewById(R.id.detail_page_button3);
        view_button3.setVisibility(View.VISIBLE);
        view_button3.setText("Create Loan Payment");
        view_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLoanPayment();
            }
        });

        view_button4 = (Button) findViewById(R.id.detail_page_button4);
        view_button4.setVisibility(View.VISIBLE);
        view_button4.setText("View Loan Payments");
        view_button4.setOnClickListener(new ListElementsButtonListener("loan_payments"));

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Loan");
        view_button5.setOnClickListener(new EditElementButtonListener("loan"));

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Loan");
        view_button6.setOnClickListener(new DeleteElementButtonListener("loan", loan_id));
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

        // view_display1 = (TextView) findViewById(R.id.detail_page_field1);
        // view_display1.setVisibility(View.VISIBLE);
        // String mid_display_text1 = "member_id: " + "member name!!!!!!!!";
        // view_display1.setText(mid_display_text1);

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

        view_button3 = (Button) findViewById(R.id.detail_page_button3);
        view_button3.setVisibility(View.VISIBLE);
        view_button3.setText("View Loan");
        view_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ElementDetailActivity.class);
                intent.putExtra("loan_id", loan_id);
                intent.putExtra("view_type", "loan");
                startActivity(intent);
            }
        });

        view_button4 = (Button) findViewById(R.id.detail_page_button4);
        view_button4.setVisibility(View.VISIBLE);
        view_button4.setText("View Paying Member");
        view_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ElementDetailActivity.class);
                intent.putExtra("member_id", member_id);
                intent.putExtra("view_type", "member");
                startActivity(intent);
            }
        });

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Payment");
        view_button5.setOnClickListener(new EditElementButtonListener("payment"));

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Payment");
        view_button6.setOnClickListener(new DeleteElementButtonListener("payment", payment_id));
    }

    public String business_id = "";
    public String business_status = "";

    public void viewBusiness(JSONObject json) {
        setTitle("Business Details");
        try {
            business_id = json.getString("business_id");
            community_id = json.getString("community_id");
            meeting_id = json.getString("meeting_id");
            name = json.getString("name");
            business_summary = json.getString("business_summary");
            business_status = json.getString("business_status");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        view_display1 = (TextView) findViewById(R.id.detail_page_field1);
        view_display1.setVisibility(View.VISIBLE);
        String mid_display_text1 = "Business Name: " + name;
        view_display1.setText(mid_display_text1);

        view_display2 = (TextView) findViewById(R.id.detail_page_field2);
        view_display2.setVisibility(View.VISIBLE);
        String mid_display_text2 = "Business Summary: " + business_summary;
        view_display2.setText(mid_display_text2);

        view_display3 = (TextView) findViewById(R.id.detail_page_field3);
        view_display3.setVisibility(View.VISIBLE);
        String mid_display_text3 = "Business Status: " + business_status;
        view_display3.setText(mid_display_text3);

        view_button3 = (Button) findViewById(R.id.detail_page_button3);
        view_button3.setVisibility(View.VISIBLE);
        view_button3.setText("Add Owning Member");
        view_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBusinessMember();
            }
        });

        view_button4 = (Button) findViewById(R.id.detail_page_button4);
        view_button4.setVisibility(View.VISIBLE);
        view_button4.setText("View Owning Members");
        view_button4.setOnClickListener(new ListElementsButtonListener("business_members"));

        view_button5 = (Button) findViewById(R.id.detail_page_button5);
        view_button5.setVisibility(View.VISIBLE);
        view_button5.setText("Edit Business");
        view_button5.setOnClickListener(new EditElementButtonListener("business"));

        view_button6 = (Button) findViewById(R.id.detail_page_button6);
        view_button6.setVisibility(View.VISIBLE);
        view_button6.setText("Delete Business");
        view_button6.setOnClickListener(new DeleteElementButtonListener("business", business_id));
    }

    public void addBusinessMember() {
        elementPicker = new ElementPickerDialog("member", ElementDetailActivity.this);
        elementPicker.pickElement();
    }

    public void addToBusiness() {
        String element_id = elementPicker.getID();
        // String element_display = elementPicker.getDisplay();
        QUERY_ARGS = new HashMap<>();
        QUERY_ARGS.put("business_id", business_id);
        QUERY_ARGS.put("form_type", "MemberHasBusiness");
        QUERY_ARGS.put("member_id", element_id);
        Log.d("QUERY_ARGS: ", QUERY_ARGS.toString());
        new FormPoster().execute();
    }

    class ListElementsButtonListener implements View.OnClickListener {
        private String function;
        public ListElementsButtonListener(String function) {
            this.function = function;
        }

        public void doClick() {
            onClick(new View(getApplicationContext()));
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
                    intent.putExtra("base_id", meeting_id);
                    intent.putExtra("CLICK_ACTION", "MEMBER_PAGE");
                    break;
                case "meeting_loans":
                    intent.putExtra("base_id", meeting_id);
                    intent.putExtra("CLICK_ACTION", "LOAN_PAGE");
                    break;
                case "meeting_businesses":
                    intent.putExtra("base_id", meeting_id);
                    intent.putExtra("CLICK_ACTION", "BUSINESS_PAGE");
                    break;
                case "loan_payments":
                    intent.putExtra("base_id", loan_id);
                    intent.putExtra("CLICK_ACTION", "PAYMENT_PAGE");
                    break;
                case "business_members":
                    intent.putExtra("base_id", business_id);
                    intent.putExtra("CLICK_ACTION", "MEMBER_PAGE");
                    break;
                default:
                    Toast.makeText(ElementDetailActivity.this, "Error Viewing Elements: " + function, Toast.LENGTH_LONG).show();
                    break;
            }
            startActivity(intent);
        }
    }

    class EditElementButtonListener implements View.OnClickListener {

        private String element_type;

        public EditElementButtonListener(String element_type) {
            this.element_type = element_type;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ElementDetailActivity.this, UpdateFormActivity.class);
            intent.putExtra("form", element_type);
            switch(element_type) {
                case "community":
                    break;
                case "member":
                    intent.putExtra("member_id", member_id);
                    intent.putExtra("name", name);
                    intent.putExtra("age", age);
                    intent.putExtra("emergency_contact", emergency_contact);
                    intent.putExtra("residence", residence);
                    intent.putExtra("kin_or_spouse", kin_or_spouse);
                    intent.putExtra("community_id", community_id);
                    break;
                case "meeting":
                    intent.putExtra("meeting_id", meeting_id);
                    intent.putExtra("community_id", community_id);
                    intent.putExtra("date_time", date_time);
                    intent.putExtra("meeting_time", meeting_time);
                    intent.putExtra("business_summary", business_summary);
                    intent.putExtra("meeting_summary", meeting_summary);
                    break;
                case "loan":
                    intent.putExtra("loan_id", loan_id);
                    intent.putExtra("community_id", community_id);
                    intent.putExtra("award_date", award_date);
                    intent.putExtra("amount", amount);
                    intent.putExtra("balance", balance);
                    break;
                case "payment":
                    intent.putExtra("payment_id", payment_id);
                    intent.putExtra("loan_id", loan_id);
                    intent.putExtra("member_id", member_id);
                    intent.putExtra("payment_date", payment_date);
                    intent.putExtra("expected_amount", expected_amount);
                    intent.putExtra("actual_amount", actual_amount);
                    break;
                case "business":
                    intent.putExtra("business_id", business_id);
                    intent.putExtra("community_id", community_id);
                    intent.putExtra("meeting_id", meeting_id);
                    intent.putExtra("name", name);
                    intent.putExtra("business_summary", business_summary);
                    intent.putExtra("business_status", business_status);
                    break;
                default:
                    break;
            }
            Log.d("Intent Extras: ", intent.getExtras().toString());
            startActivityForResult(intent, 9876);
            // Toast.makeText(ElementDetailActivity.this, "Edit Element", Toast.LENGTH_LONG).show();
        }
    }

    class DeleteElementButtonListener implements View.OnClickListener {

        private String elementType = "";
        private String elementID = "";

        public DeleteElementButtonListener(String elementType, String elementID) {
            this.elementType = elementType;
            this.elementID = elementID;
        }

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(ElementDetailActivity.this)
                    .setTitle("Confirm Delete")
                    .setMessage("Do you really want to delete this " + elementType + "?")
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            doDelete();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }

        public void doDelete() {
            // Toast.makeText(ElementDetailActivity.this, "Delete" + elementType, Toast.LENGTH_LONG).show();
            QUERY_ARGS = new HashMap<>();
            QUERY_ARGS.put("form_type", elementType);
            QUERY_ARGS.put("security_key", "E92FC684-612B-45A9-B55F-F79E75BAF60B");
            QUERY_ARGS.put("elementID", elementID);
            FormPoster deleteElement = new FormPoster();
            deleteElement.request_url = url_delete_element;
            deleteElement.action = "Deleting";
            deleteElement.execute();
            finish();
        }
    }

    public void createLoanPayment() {
        Intent intent = new Intent(ElementDetailActivity.this, CreateFormActivity.class);
        intent.putExtra("community_id", CommunityHomePage.GLOBAL_COMMUNITY_ID);
        intent.putExtra("loan_id", loan_id);
        intent.putExtra("form", "payment");
        startActivity(intent);
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
                        // Toast.makeText(ElementDetailActivity.this, "Successfully loaded " + elementType + "!", Toast.LENGTH_LONG).show();
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

    class FormPoster extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog pDialog;
        public String request_url = url_create_element;
        public String action = "Adding";

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ElementDetailActivity.this);
            pDialog.setMessage(action + " Item. Please wait...");
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
            JSONObject json = jphtr.makeHttpRequest(request_url, "GET", QUERY_ARGS);

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
                // Toast.makeText(ElementDetailActivity.this, "Success!", Toast.LENGTH_LONG).show();
                // finish();
            } else {
                // Toast.makeText(ElementDetailActivity.this, "Error", Toast.LENGTH_LONG).show();
            }

        }

    }

}
