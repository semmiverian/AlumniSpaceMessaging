package id.semmi.alumnispacemessaging.mainChatting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.semmi.alumnispacemessaging.R;
import id.semmi.alumnispacemessaging.models.Chatting;

/**
 * Created by semmi on 27/03/2016.
 */
public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ViewHolder>  {

    private Context mContext;
    private List<Chatting> mChattings;
    private String myName;

    public ChattingAdapter(Context mContext, List<Chatting> mChattings, String myName) {
        this.mContext = mContext;
        this.mChattings = mChattings;
        this.myName = myName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View chattingView = inflater.inflate(R.layout.adapter_chatting_list,parent,false);
        return new ViewHolder(chattingView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chatting chat = mChattings.get(position);
        if(chat.getFromName().equals(myName)){
            holder.messageContent.setVisibility(View.INVISIBLE);
           holder.messageContentReceiver.setBackgroundResource(R.color.green_700);
            holder.messageContentReceiver.setText(chat.getMessage());
        }else{
            holder.messageContentReceiver.setVisibility(View.INVISIBLE);
            holder.messageContent.setBackgroundResource(R.color.blue_grey_500 );
            holder.messageContent.setText(chat.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return (null != mChattings ? mChattings.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.MessageContent) TextView messageContent;
        @Bind(R.id.MessageContentFrom) TextView messageContentReceiver;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
