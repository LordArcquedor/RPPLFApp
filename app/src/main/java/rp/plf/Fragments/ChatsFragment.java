package rp.plf.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rp.plf.Modele.ChatList;
import rp.plf.Modele.ChatMessage;
import rp.plf.R;
import rp.plf.UserAdapter;
import rp.plf.Utilisateur;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<Utilisateur> mUsers;
    FirebaseUser fuser;
    DatabaseReference reference;
    private List<String> usersList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats,container,false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatMessage chat = snapshot.getValue(ChatMessage.class);
                    if (chat != null && chat.getSender() != null && chat.getReceiver() != null) {
                        if(chat.getSender().equals(fuser.getUid())){
                            usersList.add(chat.getReceiver());
                        }
                        if(chat.getReceiver().equals(fuser.getUid())){
                            usersList.add(chat.getSender());
                        }
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }



    private void readChats() {
        mUsers = new ArrayList<>();

        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("utilisateurs");

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

//                    Utilisateur utilisateur = snapshot.getValue(Utilisateur.class);

                    DataSnapshot pseudoS = snapshot.child("pseudo");
                    DataSnapshot photoD = snapshot.child("photo");
                    DataSnapshot argent = snapshot.child("argent");
                    DataSnapshot badge = snapshot.child("badge");
                    DataSnapshot email = snapshot.child("email");
                    DataSnapshot info = snapshot.child("infoD");
                    DataSnapshot statu = snapshot.child("status");
                    DataSnapshot uid = snapshot.child("uId");
                    DataSnapshot role = snapshot.child("role");
                    DataSnapshot bann = snapshot.child("banniere");

                    Utilisateur utilisateur =new Utilisateur();
                    utilisateur.setPhoto(photoD.getValue(String.class));
                    utilisateur.setArgent((argent.getValue(String.class)));
                    utilisateur.setBadge(badge.getValue(String.class));
                    utilisateur.setEmail(email.getValue(String.class));
                    utilisateur.setInfoD(info.getValue(String.class));
                    utilisateur.setStatus(statu.getValue(String.class));
                    utilisateur.setuId(uid.getValue(String.class));
                    utilisateur.setRole(role.getValue(String.class));
                    utilisateur.setBanniere(bann.getValue(String.class));
                    utilisateur.setPseudo(pseudoS.getValue(String.class));

                    if (utilisateur != null && utilisateur.getuId() != null) {
                        for(String id : usersList){
                            if(utilisateur.getuId().equals(id)){
                                if(mUsers.isEmpty()){
                                    mUsers.add(utilisateur);
                                } else {
                                    boolean userExists = false;
                                    for (Utilisateur utilisateur1 : mUsers) {
                                        if (utilisateur1 != null && utilisateur.getuId().equals(utilisateur1.getuId())) {
                                            userExists = true;
                                            break;
                                        }
                                    }
                                    if (!userExists) {
                                        mUsers.add(utilisateur);
                                    }
                                }
                            }
                        }
                    }
                }

                Utilisateur rpplf =new Utilisateur();
                rpplf.setPhoto("https://i.postimg.cc/xjFfJ7Sx/logorp.jpg");
                rpplf.setArgent("999999");
                rpplf.setBadge("0123456789");
                rpplf.setEmail("rpplf@gmail.com");
                rpplf.setInfoD("RP ACC");
                rpplf.setStatus("online");
                rpplf.setuId("123456789");
                rpplf.setRole("Plateau");
                rpplf.setBanniere("https://i.postimg.cc/xjFfJ7Sx/logorp.jpg");
                rpplf.setPseudo("Plateau de la RPPLF");


                boolean go = true;
                for(Utilisateur elem : mUsers){
                    if(elem.getPseudo().equals("Plateau de la RPPLF")){
                        go = false;
                    }
                }
                if(go){
                    mUsers.add(rpplf);
                }



                userAdapter = new UserAdapter(getContext(), mUsers,true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}