package rp.plf;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView  emailT,nomT;
    FirebaseUser user;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Button infoRPlast, infoRPlast2,infoRPlast3,infoRPlast4,infoRPlast5,infoJoueur,infoJoueur2,infoJoueur3,infoJoueur4,infoJoueur5;
    Bundle bundle = new Bundle();
    TextView textView11;
    List<Publication> cinqPremieresValeurs;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        infoRPlast = findViewById(R.id.infoRPlast);
        infoRPlast2 = findViewById(R.id.infoRPlast2);
        infoRPlast3 = findViewById(R.id.infoRPlast3);
        infoRPlast4 = findViewById(R.id.infoRPlast4);
        infoRPlast5 = findViewById(R.id.infoRPlast5);
        infoJoueur = findViewById(R.id.infoJoueur);
        infoJoueur2 = findViewById(R.id.infoJoueur2);
        infoJoueur3 = findViewById(R.id.infoJoueur3);
        infoJoueur4 = findViewById(R.id.infoJoueur4);
        infoJoueur5 = findViewById(R.id.infoJoueur5);
        textView11 = findViewById(R.id.textView11);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");


        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                mettreajourpostRP();
            }
        });
//        thread3.start();
        runOnUiThread(thread3);

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                mettreajourpost();
            }
        });
        thread2.start();
//        runOnUiThread(thread2);


        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                textView11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),RechercherPublicationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        thread4.start();
        runOnUiThread(thread4);


        infoRPlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationRPActivity.class);
                bundle.putInt("numeroPubli",0);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        infoRPlast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationRPActivity.class);
                bundle.putInt("numeroPubli",1);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        infoRPlast3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationRPActivity.class);
                bundle.putInt("numeroPubli",2);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        infoRPlast4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationRPActivity.class);
                bundle.putInt("numeroPubli",3);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        infoRPlast5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationRPActivity.class);
                bundle.putInt("numeroPubli",4);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });



        infoJoueur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationFUNActivity.class);
                bundle.putInt("numeroPubli",0);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        infoJoueur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationFUNActivity.class);
                bundle.putInt("numeroPubli",1);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        infoJoueur3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationFUNActivity.class);
                bundle.putInt("numeroPubli",2);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        infoJoueur4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationFUNActivity.class);
                bundle.putInt("numeroPubli",3);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        infoJoueur5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AfficherPublicationFUNActivity.class);
                bundle.putInt("numeroPubli",4);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                    {
                        break;
                    }
                    case R.id.parametre:
                    {
                        Intent intent = new Intent(getApplicationContext(),ParametreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.profil:
                    {
                        Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.calendrier:
                    {
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.social:
                    {
                        Intent intent = new Intent(getApplicationContext(),SocialActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.messagerie:
                    {
                        Intent intent = new Intent(getApplicationContext(),MessagerieActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.infoutiles:
                    {
                        Intent intent = new Intent(getApplicationContext(),InfoUtileActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.collection:
                    {
                        Intent intent = new Intent(getApplicationContext(),CollectionActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.boutique:
                    {
                        Intent intent = new Intent(getApplicationContext(),BoutiqueActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.deco:
                    {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    }

                }
                return false;
            }
        });

        user = auth.getCurrentUser();

        if(user == null ){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
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
                            DataSnapshot pseudoSnapshot  = userSnapshot.child("pseudo");

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

    private void mettreajourpostRP() {
        ArrayList<Publication> listePubli = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("publications");
        Query query2 = usersRef.orderByChild("date");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot publicationSnapshot : dataSnapshot.getChildren()) {
                    // Convertissez le snapshot en objet Publication
                    Publication publication = publicationSnapshot.getValue(Publication.class);
                    // Ajoutez la publication à la liste
                    listePubli.add(publication);
                }

                for(int i=0;i<listePubli.size();i++){
                    if(!listePubli.get(i).getType().equals("RP")){
                        listePubli.remove(listePubli.get(i));
                        i=i-1;
                    }
                }



                Collections.reverse(listePubli);


                infoRPlast.setText("⭐️ RP - "+listePubli.get(0).getTitre());
                infoRPlast2.setText("⭐️ RP - "+listePubli.get(1).getTitre());
                infoRPlast3.setText("⭐️ RP - "+listePubli.get(2).getTitre());
                infoRPlast4.setText("⭐️ RP - "+listePubli.get(3).getTitre());
                infoRPlast5.setText("⭐️ RP - "+listePubli.get(4).getTitre());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void mettreajourpost() {
        ArrayList<Publication> listePubli = new ArrayList<>();

//        HashMap map = new HashMap();
//        Comparateur comp = new Comparateur(map);
//        TreeMap map_apres = new TreeMap(comp);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("publications");
        Query query = usersRef.orderByChild("date");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot publicationSnapshot : dataSnapshot.getChildren()) {
                    // Convertissez le snapshot en objet Publication
                    Publication publication = publicationSnapshot.getValue(Publication.class);
                    // Ajoutez la publication à la liste
                    listePubli.add(publication);
                }

                for(int i=0;i<listePubli.size();i++){
                    if(!listePubli.get(i).getType().equals("FUN")){
                        listePubli.remove(listePubli.get(i));
                        i=i-1;
                    }
                }
                Collections.reverse(listePubli);

                infoJoueur.setText("\uD83D\uDCDD - "+listePubli.get(0).getTitre());
                infoJoueur2.setText("\uD83D\uDCDD - "+listePubli.get(1).getTitre());
                infoJoueur3.setText("\uD83D\uDCDD - "+listePubli.get(2).getTitre());
                infoJoueur4.setText("\uD83D\uDCDD - "+listePubli.get(3).getTitre());
                infoJoueur5.setText("\uD83D\uDCDD - "+listePubli.get(4).getTitre());

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}

