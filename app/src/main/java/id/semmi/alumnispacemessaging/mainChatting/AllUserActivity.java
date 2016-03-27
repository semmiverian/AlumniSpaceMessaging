package id.semmi.alumnispacemessaging.mainChatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.semmi.alumnispacemessaging.Api.ApiConstant;
import id.semmi.alumnispacemessaging.Api.RetrofitApiInterface;
import id.semmi.alumnispacemessaging.R;
import id.semmi.alumnispacemessaging.models.User;
import id.semmi.alumnispacemessaging.utils.Constant;
import id.semmi.alumnispacemessaging.utils.SharedPreferencesHelper;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by semmi on 15/03/2016.
 */
public class AllUserActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView) RecyclerView rv;

    private AllUserAdapter mAdapter;
    // first initialize empty data to the adapter
    private List<User> users = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        ButterKnife.bind(this);
        displayAllUser();

        SharedPreferencesHelper helper = new SharedPreferencesHelper(this);
        String myName = helper.myName();
        Log.d(Constant.TAG, "onCreate: "+myName);
    }

    /*
        Function to display all the user data from the server to the activity
     */
    private void displayAllUser() {
        // Initiate Retrofit
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(ApiConstant.myApiEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiInterface retrofitApiInterface = retrofit2.create(RetrofitApiInterface.class);

        Call<List<User>> call = retrofitApiInterface.fetchAllUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                // Fetch All User data from the server
                if(response.isSuccess()){
//                    mAdapter = new AllUserAdapter(AllUserActivity.this,response.body());
                    // Add the data to the Recyclerview adapter
                    users=response.body();
                    mAdapter = new AllUserAdapter(AllUserActivity.this,users);
                    rv.setAdapter(mAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(AllUserActivity.this));
                    rv.addItemDecoration(new VerticalDividerItemDecoration.Builder(AllUserActivity.this)
                            .build());
                    rv.setItemAnimator(new FadeInUpAnimator());
                    mAdapter.setOnItemClickListener(new AllUserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClickListener(View view, int position) {
                            // Put extra for the receiver name
                            // Implement click listener
                            // Intent to the messaging
                            Intent goChatting = new Intent(AllUserActivity.this,ChattingActivity.class);
                            goChatting.putExtra(ChattingActivity.extraNama, users.get(position).getUsername());
                            startActivity(goChatting);
                            Toast.makeText(AllUserActivity.this, ""+users.get(position).getUsername(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    mAdapter.notifyDataSetChanged();
//                    rv.setAdapter(mAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(AllUserActivity.this));
                    for(User user : response.body()){
                        Log.d(Constant.TAG, "onResponse: "+ user.getUsername());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(Constant.TAG, "onFailure: " + t.getMessage());

            }
        });

    }
}
