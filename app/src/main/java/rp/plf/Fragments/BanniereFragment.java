package rp.plf.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rp.plf.BannierAdapter;
import rp.plf.Modele.Banniere;
import rp.plf.R;
import rp.plf.UserAdapter;
import rp.plf.Utilisateur;

public class BanniereFragment  extends Fragment {
    private RecyclerView recyclerView;

    private BannierAdapter userAdapter;
    private List<Banniere> mBanniere;
    FirebaseUser fuser;
    DatabaseReference reference;
    private List<Banniere> banniereList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_item,container,false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("AvatarDispos");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    DataSnapshot banniere = snapshot.child("Lien");
                    DataSnapshot prix = snapshot.child("Prix");

                    String sBanniere = banniere.getValue(String.class);
                    String sPrix = prix.getValue(String.class);
                    TextView textView = view.findViewById(R.id.username); // Remplacez avec l'ID réel de votre TextView
                    textView.setText("Lien : " + sBanniere + ", Prix : " + sPrix);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;


}}
