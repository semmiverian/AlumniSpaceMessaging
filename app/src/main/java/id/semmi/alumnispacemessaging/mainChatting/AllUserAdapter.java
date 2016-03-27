package id.semmi.alumnispacemessaging.mainChatting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.semmi.alumnispacemessaging.R;
import id.semmi.alumnispacemessaging.models.User;

/**
 * Created by semmi on 15/03/2016.
 */
public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private OnItemClickListener onItemClickListener;

    public AllUserAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View usersView = inflater.inflate(R.layout.adapter_all_user,parent,false);
        return new ViewHolder(usersView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.userName.setText(user.getNama());
        // Using glide to cache the image from the internet
        Glide.with(mContext)
             .load(user.getImage())
             .fitCenter()
             .centerCrop()
             .into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       @Bind(R.id.userImage) ImageView userImage;
       @Bind(R.id.userName) TextView userName;
        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if(v.getId() == itemView.getId()){
                        if(onItemClickListener !=null){
                            onItemClickListener.onItemClickListener(itemView,position);
                        }
                    }
                }
            });
        }
    }

    /*
        Creating Interface for item listener
     */
    public interface OnItemClickListener{
        void onItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
