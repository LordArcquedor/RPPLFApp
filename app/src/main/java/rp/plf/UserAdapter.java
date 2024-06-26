package rp.plf;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import rp.plf.Modele.ChatMessage;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<Utilisateur> mUsers;
    private boolean isChat;
    String theLastMessage;

    public UserAdapter(Context mContext, List<Utilisateur> mUsers, boolean isChat){
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Utilisateur user = mUsers.get(position);
        holder.username.setText(user.getPseudo());
        if(user.getPhoto().equals("")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(user.getPhoto()).into(holder.profile_image);
        }

        if(isChat){
            lastMessage(user.getuId(),holder.last_msg);
            }else{
            holder.last_msg.setVisibility(View.GONE);
        }

        if(isChat){
            if(user.getStatus().equals("online")){
                holder.img_off.setVisibility(View.GONE);
                holder.img_on.setVisibility(View.VISIBLE);
            }else{
                holder.img_off.setVisibility(View.VISIBLE);
                holder.img_on.setVisibility(View.GONE);
            }
        }else{
            holder.img_off.setVisibility(View.GONE);
            holder.img_on.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userID",user.getuId());
                mContext.startActivity(intent);
            }
        });

    }

    private void lastMessage(final String userId, final TextView last_msg){
        theLastMessage ="default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if(chatMessage.getReceiver().equals(firebaseUser.getUid()) && chatMessage.getSender().equals(userId)
                            || chatMessage.getReceiver().equals(userId) && chatMessage.getSender().equals(firebaseUser.getUid()) ){
                        theLastMessage = chatMessage.getMessage();
                    }

                }
                switch (theLastMessage){
                    case "default" :
                        last_msg.setText("Pas de message");
                        break;
                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;
        public ImageView img_on;
        public ImageView img_off;
        public TextView last_msg;

        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);
        }


    }


}
