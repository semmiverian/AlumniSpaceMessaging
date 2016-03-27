package id.semmi.alumnispacemessaging.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by semmi on 17/03/2016.
 */
public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences("Session Control", Context.MODE_PRIVATE);
    }

    public int myId(){
        return this.sharedPreferences.getInt("myId",0);
    }

    public String myName(){
        return this.sharedPreferences.getString("myName", "myName");
    }

    public String myUsername(){
        return this.sharedPreferences.getString("myUsername","myUsername");
    }
}
