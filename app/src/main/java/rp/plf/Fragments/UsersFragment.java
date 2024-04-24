package rp.plf.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rp.plf.R;
import rp.plf.User;
import rp.plf.UserAdapter;
import rp.plf.Utilisateur;

public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<Utilisateur> mUsers;
    EditText search_users;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users,container,false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();

        readUsers();

        search_users = view.findViewById(R.id.search_user);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void searchUsers(String s) {

        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("utilisateurs").orderByChild("pseudo")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Utilisateur user = snapshot.getValue(Utilisateur.class);
                    assert user != null;
                    assert fuser != null;
                    if(!user.getuId().equals(fuser.getUid())){
                        mUsers.add(user);
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers, false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("utilisateurs");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_users.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Utilisateur utilisateur = snapshot.getValue(Utilisateur.class);

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

                        assert utilisateur != null;
                        assert firebaseUser != null;


                        if (firebaseUser.getUid() != null && utilisateur.getuId() != null && !utilisateur.getuId().equals(firebaseUser.getUid())) {
                            mUsers.add(utilisateur);
                        }

//                        if (!utilisateur.getuId().equals(firebaseUser.getUid())) {
//                            mUsers.add(utilisateur);
//                        }
                    }
                    userAdapter = new UserAdapter(getContext(), mUsers, false);
                    recyclerView.setAdapter(userAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}