package rp.plf;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import rp.plf.Modele.ChatMessage;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private List<ChatMessage> mChatMessages;
    private String imageurl;


    public static final int MSG_TYPE_LEFT=1;
    public static final int MSG_TYPE_RIGHT=0;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context mContext, List<ChatMessage> mChatMessages, String imageurl){
        this.mContext = mContext;
        this.mChatMessages = mChatMessages;
        this.imageurl = imageurl;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        ChatMessage chatMessage = mChatMessages.get(position);
        holder.show_msg.setText(chatMessage.getMessage());

        if(imageurl.equals("")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else{
            String url = "";

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersRef = database.getReference("utilisateurs");
            Query query = usersRef.orderByChild("uId").equalTo(chatMessage.getSender());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        DataSnapshot pseudoSnapshot  = userSnapshot.child("photo");
                        DataSnapshot pseudoSnapshot2  = userSnapshot.child("pseudo");
                        String pseudo = pseudoSnapshot2.getValue(String.class);

                        try{
                            holder.nomEnvoyeur.setText(pseudo);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        String url = pseudoSnapshot.getValue(String.class);
                        Picasso.get()
                                .load(url)
                                .into(holder.profile_image);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // En cas d'erreur lors de la récupération des données
                }
            });
//            Picasso.get()
//                    .load(maVraiURL)
//                    .into(holder.profile_image);
//            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if(position == mChatMessages.size()-1){
            if(chatMessage.getIsseen()){
                holder.text_seen.setText("Vu");
            }else{
                holder.text_seen.setText("Délivré");
            }
        }else{
            holder.text_seen.setVisibility(View.GONE);
        }


    }

    private String getMonImageGraceALUID(String sender) {
        String url = "";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");
        Query query = usersRef.orderByChild("uId").equalTo(sender);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot pseudoSnapshot  = userSnapshot.child("photo");
                    String url = pseudoSnapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });
        return url;
    }


    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_msg, text_seen, nomEnvoyeur;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);
            show_msg = itemView.findViewById(R.id.show_msg);
            profile_image = itemView.findViewById(R.id.profile_image);
            text_seen = itemView.findViewById(R.id.text_seen);
            nomEnvoyeur = itemView.findViewById(R.id.nomEnvoyeur);

        }
    }



    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        if(mChatMessages.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
