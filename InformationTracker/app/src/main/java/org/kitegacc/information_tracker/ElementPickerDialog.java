package org.kitegacc.information_tracker;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Philip Liberato on 6/30/16.
 */
public class ElementPickerDialog {

    private String elementType = "";
    private String element_ID = "";
    private String elementDisplay = "";
    private Activity activity;

    public ElementPickerDialog(String elementType, Activity activity) {
        this.elementType = elementType;
        this.activity = activity;
    }

    public void setID(String s) {
        this.element_ID = s;
    }

    public void setDisplay(String s) {
        this.elementDisplay = s;
    }

    public String getID() {
        return this.element_ID;
    }

    public String getDisplay() {
        return this.elementDisplay;
    }

    public void pickElement() {
        Intent intent = new Intent(activity, ListElementsActivity.class);
        if(elementType.equals("member")) {
            intent.putExtra("LIST_TYPE", "community_members"); // other will be "SELECT_MEMBER"
            intent.putExtra("CLICK_ACTION", "RETURN_ELEMENT");
            intent.putExtra("base_id", CommunityHomePage.GLOBAL_COMMUNITY_ID);
        }
        activity.startActivityForResult(intent, 100);
    }
}
