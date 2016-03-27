package id.semmi.alumnispacemessaging;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by semmi on 16/03/2016.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
