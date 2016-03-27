package id.semmi.alumnispacemessaging.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.semmi.alumnispacemessaging.Api.ApiConstant;
import id.semmi.alumnispacemessaging.Api.RetrofitApiInterface;
import id.semmi.alumnispacemessaging.R;
import id.semmi.alumnispacemessaging.mainChatting.AllUserActivity;
import id.semmi.alumnispacemessaging.models.User;
import id.semmi.alumnispacemessaging.utils.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.username) EditText mUsernameText;
    @Bind(R.id.password) EditText mPassword;
    @Bind(R.id.loginGo) Button mButtonLogin;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences("Session Control", Context.MODE_PRIVATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkExistingSession();
    }

    private void checkExistingSession() {
        String existingSession = sharedPreferences.getString("myName","none");
        if(!existingSession.equals("none")){
            Intent chattingIntent = new Intent(this,AllUserActivity.class);
            startActivity(chattingIntent);
        }
    }

    /*
        Function to respond of the click for the login button
     */
    @OnClick(R.id.loginGo)
    void loggedInUser(Button button){
        // get the user input
        String username = mUsernameText.getText().toString();
        String password = mPassword.getText().toString();
        // check null value
        if(username.equals("") || password.equals("")){
            Toast.makeText(MainActivity.this, "Please fill all the form", Toast.LENGTH_SHORT).show();
        }
        proceedLogin(username, password);
    }


    /*
        Process to connect to the server to check the credentials that user input
     */
    private void proceedLogin(String username, String password) {
        // Initiate Retrofit
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(ApiConstant.myApiEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface retrofitApiInterface = retrofit2.create(RetrofitApiInterface.class);

        User user = new User(username,password);
        Call<User> call = retrofitApiInterface.loggedInUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccess()) {
                    User user = response.body();
                    Log.d(Constant.TAG, "onResponse: " + user.getKode());
                    if(user.getKode().equals("4")){
                        Toast.makeText(MainActivity.this, "Success Gan", Toast.LENGTH_SHORT).show();

                        // Intent to the main messaging activity
                        Intent allUserIntent = new Intent(MainActivity.this, AllUserActivity.class);
                        startActivity(allUserIntent);
                        // Set the user session
                        setUserSession(user);
                        return;
                    }
                    // Error feedback
                    Toast.makeText(MainActivity.this, user.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(Constant.TAG, "onFailure: " + t.getMessage());
            }

        });
    }

    /*
        Function to save the current logged in user to the shared preferences
     */
    private void setUserSession(User user) {
        editor = sharedPreferences.edit();
        editor.putString("myName",user.getNama());
        editor.putString("myUsername",user.getUsername());
        editor.putInt("myId", user.getId());
        editor.apply();
    }


}
