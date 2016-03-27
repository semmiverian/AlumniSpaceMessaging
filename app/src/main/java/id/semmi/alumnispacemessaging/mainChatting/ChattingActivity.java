package id.semmi.alumnispacemessaging.mainChatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.semmi.alumnispacemessaging.Api.ApiConstant;
import id.semmi.alumnispacemessaging.Api.RetrofitApiInterface;
import id.semmi.alumnispacemessaging.R;
import id.semmi.alumnispacemessaging.models.Chatting;
import id.semmi.alumnispacemessaging.models.FirebaseKey;
import id.semmi.alumnispacemessaging.utils.Constant;
import id.semmi.alumnispacemessaging.utils.SharedPreferencesHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChattingActivity extends AppCompatActivity {
    @Bind(R.id.messageInput) EditText messageText;
    @Bind(R.id.sendMessage) Button submit;
    @Bind(R.id.recyclerView) RecyclerView rv;

    public final static String extraNama = "extra nama";
    private String receiverName = "none";
    private String myName;
    private String childKey ="";
    private List<Chatting> mChatting;
    private ChattingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ButterKnife.bind(this);
//        getSupportActionBar().set
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Get the session name
        SharedPreferencesHelper helper = new SharedPreferencesHelper(this);
        myName = helper.myName();

        // RecylerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        mChatting = new ArrayList<>();
        mAdapter = new ChattingAdapter(this,mChatting,myName);
        rv.setAdapter(mAdapter);





        Log.d(Constant.TAG, "onCreate: "+myName);
        // Get the receiver name
        if(getIntent().getExtras()!=null){
             receiverName = getIntent().getExtras().getString(extraNama);
        }
        // Check whether there is a key for this combo ( from and to )
        final Firebase ref = new Firebase(Constant.FIREBASE_URL);
        FirebaseKey check = new FirebaseKey(myName,receiverName);
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(ApiConstant.myApiEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface retrofitApiInterface = retrofit2.create(RetrofitApiInterface.class);
        Call<FirebaseKey> call = retrofitApiInterface.checkFirebaseKey(check);
        call.enqueue(new Callback<FirebaseKey>() {
            @Override
            public void onResponse(Call<FirebaseKey> call, Response<FirebaseKey> response) {
                FirebaseKey key = response.body();
                // if yes set the firebase child to that key
                if(key.getKode().equals("21")){
                    startFirebaseChat(key.getKey());
                    childKey = key.getKey();
                    return;
                }
                // if no set a new key for the firebase
                // persist the new key for that combo to the server
                if(key.getKode().equals("22")){
//                    Chatting content = new Chatting(myName,receiverName,"Starter Message");
                    Firebase push = ref.push();
//                    push.setValue(content);
                    Log.d(Constant.TAG, "onResponse: " + push.getKey());
                    addKeyToServer(myName, receiverName, push.getKey());
                    childKey = push.getKey();

                }
            }

            @Override
            public void onFailure(Call<FirebaseKey> call, Throwable t) {

            }
        });


    }

    private void addKeyToServer(String myName, String receiverName, final String key) {
        FirebaseKey addFirebaseKey = new FirebaseKey(key,myName,receiverName);
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(ApiConstant.myApiEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface retrofitApiInterface = retrofit2.create(RetrofitApiInterface.class);
        Call<FirebaseKey> call = retrofitApiInterface.addFirebaseKey(addFirebaseKey);
        call.enqueue(new Callback<FirebaseKey>() {
            @Override
            public void onResponse(Call<FirebaseKey> call, Response<FirebaseKey> response) {
                FirebaseKey keyServer = response.body();
                if(keyServer.getKode().equals("31")){
                    startFirebaseChat(key);
                    return;
                }
                Log.d(Constant.TAG, "onResponse: "+keyServer.getKode());
            }

            @Override
            public void onFailure(Call<FirebaseKey> call, Throwable t) {
                Log.e(Constant.TAG, "onFailure: "+t.getMessage() );
            }
        });

    }


    private void startFirebaseChat(String key) {
        Firebase ref = new Firebase(Constant.FIREBASE_URL);
        Firebase chattingFirebase = ref.child(key);
        chattingFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mChatting.clear();
                for (DataSnapshot chattingSnapshot : dataSnapshot.getChildren()) {
                    Chatting chattingRetrieve = chattingSnapshot.getValue(Chatting.class);
                    Log.d(Constant.TAG, "onDataChange: " + chattingRetrieve.getMessage());
                    mChatting.add(chattingRetrieve);
                    mAdapter.notifyDataSetChanged();
                    rv.smoothScrollToPosition(mChatting.size() - 1);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(Constant.TAG, "onCancelled: " + firebaseError.getMessage());
            }
        });

    }

    @OnClick(R.id.sendMessage)
    void sendMessageClick (Button button){

        Log.d(Constant.TAG, "sendMessageClick: ");
        String message  = messageText.getText().toString();
        Chatting postMessage = new Chatting(myName,receiverName,message);

        Firebase ref = new Firebase(Constant.FIREBASE_URL);
        Firebase sendMessageFirebase = ref.child(childKey);
        Firebase push = sendMessageFirebase.push();
        push.setValue(postMessage);

    }


}
