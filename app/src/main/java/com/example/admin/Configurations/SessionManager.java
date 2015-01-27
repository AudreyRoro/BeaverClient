package com.example.admin.Configurations;

/**
 * source : http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences/
 *
 * Created by ACER on 25/01/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.admin.beaver.PageConnexion;
import com.example.admin.model.User;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "BeaverPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_ID = "id";

    // Email address (make variable public to access from outside)
    public static final String KEY_PSEUDO = "pseudo";

    private static final String KEY_EMAIL = "email";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(JSONObject jsonObject) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        User user = new User();
        ObjectMapper mapper = new ObjectMapper();

        try {
            user = mapper.readValue(jsonObject.toString(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        editor.putInt(KEY_ID, user.getuId());
        editor.putString(KEY_PSEUDO, user.getuPseudo());
        editor.putString(KEY_EMAIL, user.getuMail());

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
                /*// user is not logged in redirect him to Login Activity
                Intent i = new Intent(_context, PageConnexion.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                _context.startActivity(i);*/
        }

    }


    /**
     * Get stored session data
     */
    public int getSessionID() {
        return pref.getInt(KEY_ID, 0);
    }

    public String getSessionPseudo() {
        return pref.getString(KEY_PSEUDO, "---");
    }

    public String getSessionMail() { return pref.getString(KEY_EMAIL, "---");}

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, PageConnexion.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * *
     */
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}

