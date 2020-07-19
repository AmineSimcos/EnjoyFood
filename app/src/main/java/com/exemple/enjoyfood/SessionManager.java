package com.exemple.enjoyfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSessionManager;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "app_prefs";
    private final int PRIVATE_MODE = 0;
    private final String IS_LOGGED = "isLogged";
    private final String PSEUDO = "pseudo";
    private final String ID = "id";
    private final String EMAIL = "email";
    private Context context;

    public SessionManager(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public boolean isLogged(){
        return prefs.getBoolean(IS_LOGGED, false);
    }

    public String getPseudo(){
        return prefs.getString(PSEUDO, null);
    }

    public String getID(){
        return prefs.getString(ID, null);
    }

    public String getEmail(){
        return prefs.getString(EMAIL, null);
    }

    public void insertUser(String id, String pseudo, String email){
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(ID, id);
        editor.putString(EMAIL, email);
        editor.putString(PSEUDO, pseudo);
        editor.commit();
    }

    public void logout(){
        editor.clear().commit();
    }

}
