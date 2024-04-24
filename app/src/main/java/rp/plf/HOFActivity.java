package rp.plf;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
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

public class HOFActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT, nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    ImageView imageView6;
    TextView nomP, saisonP;
    Button button8,button7;
    int a = 0;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), InfoUtileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hofactivity);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        imageView6 = findViewById(R.id.imageView6);
        nomP = findViewById(R.id.nom);
        saisonP = findViewById(R.id.saison);
        button8 = findViewById(R.id.button8);
        button7 = findViewById(R.id.button7);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");
        ArrayList<HOF> liste = new ArrayList<>();
//
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("HOF");

//        Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot lc  = userSnapshot.child("LienCarte");
                    DataSnapshot dnom  = userSnapshot.child("Nom");
                    DataSnapshot saison  = userSnapshot.child("Saison");

                    String lien = lc.getValue(String.class);
                    String nom = dnom.getValue(String.class);
                    int SSaison = saison.getValue(Integer.class);

                    liste.add(new HOF(nom,lien,SSaison));

                }
                Picasso.get()
                        .load(liste.get(a).getLienCarte())
                        .centerCrop()
                        .resize(350, 560)
                        .into(imageView6);
                nomP.setText(liste.get(a).getNom());
                saisonP.setText("SAISON "+liste.get(a).getSaison());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });
//
//        Picasso.get()
//                .load(liste.get(a).getLienCarte())
//                .centerCrop()
//                .resize(350, 560)
//                .into(imageView6);
//        nomP.setText(liste.get(a).getNom());
//        saisonP.setText("SAISON "+liste.get(a).getSaison());

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=a-1;
                if(a<0){
                    a=liste.size()-1;
                }
                Picasso.get()
                        .load(liste.get(a).getLienCarte())
                        .centerCrop()
                        .resize(350, 560)
                        .into(imageView6);
                nomP.setText(liste.get(a).getNom());
                saisonP.setText("SAISON "+liste.get(a).getSaison());
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=a+1;
                if(a>liste.size()-1){
                    a=0;
                }
                Picasso.get()
                        .load(liste.get(a).getLienCarte())
                        .centerCrop()
                        .resize(350, 560)
                        .into(imageView6);
                nomP.setText(liste.get(a).getNom());
                saisonP.setText("SAISON "+liste.get(a).getSaison());
            }
        });







        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.parametre:
                    {
                        Intent intent = new Intent(getApplicationContext(),ParametreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.profil: {
                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.calendrier: {
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.social: {
                        Intent intent = new Intent(getApplicationContext(), SocialActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.messagerie: {
                        Intent intent = new Intent(getApplicationContext(), MessagerieActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.infoutiles: {
                        Intent intent = new Intent(getApplicationContext(), InfoUtileActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.collection: {

                        Intent intent = new Intent(getApplicationContext(), CollectionActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.boutique: {
                        Intent intent = new Intent(getApplicationContext(), BoutiqueActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.deco: {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                        break;

                    }

                }
                return false;
            }
        });


        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            View headerView = navigationView.getHeaderView(0);
            emailT = headerView.findViewById(R.id.email);
            emailT.setText(user.getEmail());
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Affichage du pseudo
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("utilisateurs");
                Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            DataSnapshot pseudoSnapshot = userSnapshot.child("pseudo");

                            View headerView = navigationView.getHeaderView(0);
                            nomT = headerView.findViewById(R.id.Your_name);
                            nomT.setText(pseudoSnapshot.getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });
            }
        });
        thread.start();
        runOnUiThread(thread);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

