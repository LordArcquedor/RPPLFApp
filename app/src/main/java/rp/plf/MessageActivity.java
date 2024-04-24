package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rp.plf.Modele.ChatMessage;

public class MessageActivity extends AppCompatActivity {


    CircleImageView profile_image;
    TextView username;

    Bundle bundle12;
    public String userID;
    ImageButton btn_send;
    EditText text_send;
    FirebaseUser user;
    FirebaseAuth auth;
    public String pseudoPourMessage;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    Intent intent;

    ValueEventListener seenListener;
    MessageAdapter messageAdapter;
    List<ChatMessage> mchat;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);

        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        bundle12 = new Bundle();

//        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        intent = getIntent();
        userID = intent.getStringExtra("userID");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(),userID,msg);
                }else{
                    Toast.makeText(MessageActivity.this, "Vous ne pouvez pas envoyer un message vide.", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("utilisateurs").child(userID);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Utilisateur utilisateur = dataSnapshot.getValue(Utilisateur.class);
                username.setText(utilisateur.getPseudo());
                if(utilisateur.getPhoto().equals("")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Picasso.get()
                            .load(utilisateur.getPhoto())
                            .into(profile_image);
//                    Glide.with(MessageActivity.this).load(utilisateur.getPhoto()).into(profile_image);
                }
                readMessage(firebaseUser.getUid(),userID,utilisateur.getPhoto());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        seenMessage(userID);

    }

    private void seenMessage(final String userId){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Chats");
        seenListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren() ){
                    ChatMessage chat = snapshot.getValue(ChatMessage.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId)){
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("isseen",true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("isseen",false);

        reference.child("Chats").push().setValue(hashMap);

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid()).child(userID);
        if(receiver.equals("123456789")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelId = "mon_canal";
                CharSequence channelName = "Nom du Canal";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);


                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_baseline_catching_pokemon_24)
                        .setContentTitle("Nouveau message sur la Plateau de la RPPLF")
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Intent intent = new Intent(this, MessagerieActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(this);
                notificationManager2.notify(1, builder.build());



            }
        }

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    chatRef.child("id").setValue(userID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessage(String myId, String userid, String imageurl){
        mchat = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if(userID.equals("123456789")){
                        if(chatMessage.getReceiver().equals("123456789")){
                            mchat.add(chatMessage);
                        }
                    }
                    else if(chatMessage.getReceiver().equals(myId) && chatMessage.getSender().equals(userid) || chatMessage.getReceiver().equals(userid) && chatMessage.getSender().equals(myId)){
                        mchat.add(chatMessage);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void status(String status){
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("utilisateurs").child(user.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",status);
        databaseReference.updateChildren(hashMap);
    }

    @Override
    protected void onResume(){
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause(){
        super.onPause();
        databaseReference.removeEventListener(seenListener);
        status("offline");
    }
}

