package rp.plf;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

public class RechercherPublicationActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    public LinearLayout Layout;
    public ArrayList<Publication> listePost;
    private Button rechercher, rechercherAuteur;
    private TextView contenuTitre;
    public String contientDansTitre;
    public Bundle bundle;
    String maVariable;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_publication);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");

        bundle = getIntent().getExtras();

        rechercher = findViewById(R.id.buttonRechercherValider);
        contenuTitre = findViewById(R.id.contenuTitre);
        rechercherAuteur = findViewById(R.id.buttonRechercherAuteur);



        rechercherAuteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Layout = findViewById(R.id.Linear2);
                try{
                    Layout.removeAllViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        recupererDonneesPublicationParAuteur();
                    }
                });
                thread.start();
            }
        });

        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Layout = findViewById(R.id.Linear2);

                try{
                    Layout.removeAllViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        recupererDonneesPublication();
                    }
                });
                thread2.start();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.parametre:
                    {
                        Intent intent = new Intent(getApplicationContext(),ParametreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.home:
                    {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                        break;

                    }

                }
                return false;
            }
        });


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



    private ArrayList<Publication> recupererDonneesPublicationParAuteur() {
        listePost = new ArrayList<>();
        contientDansTitre = contenuTitre.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference publicationsRef = database.getReference("publications");
        String chaine =contientDansTitre;
        Query query = publicationsRef.orderByChild("auteur").startAt(chaine).endAt(chaine+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot auteurSnapshot  = userSnapshot.child("auteur");
                    DataSnapshot titreSnapshot = userSnapshot.child("titre");
                    String titre = titreSnapshot.getValue(String.class);
                    String auteur = auteurSnapshot.getValue(String.class);
                    listePost.add(new Publication(titre,auteur,"","","",null));
                }
                Layout = findViewById(R.id.Linear2);
                for(int i =0; i<listePost.size();i++){
                    Button button = new Button(getApplicationContext());
                    button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    button.setText("\uD83D\uDC49"+" "+listePost.get(i).getAuteur()+" - \uD83D\uDCDD "+listePost.get(i).getTitre());
                    Layout.addView(button);
                    String titre = listePost.get(i).getTitre();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            bundle.putString("pseudo","123");
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("titrePublication",titre);
                            Intent intent = new Intent(getApplicationContext(), AfficherPublicationRecherchee.class);
                            intent.putExtras(bundle2);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                if(listePost.size()==0){
                    TextView tv = new TextView(getApplicationContext());
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    tv.setTextColor(Color.RED);
                    tv.setText("Pas de résultat pour votre recherche");
                    Layout.addView(tv);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });
        return listePost;

    }

    /**
     * Récupère en base les publications avec un nom semblable à celui rentré par l'utilisateur
     * @return une liste de publications avec un titre semblable à l'entrée
     */
    private ArrayList<Publication> recupererDonneesPublication() {
        listePost = new ArrayList<>();
        contientDansTitre = contenuTitre.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference publicationsRef = database.getReference("publications");
        String chaine =contientDansTitre;
        Query query = publicationsRef.orderByChild("titre").startAt(chaine).endAt(chaine+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot auteurSnapshot  = userSnapshot.child("auteur");
                    DataSnapshot titreSnapshot = userSnapshot.child("titre");
                    String titre = titreSnapshot.getValue(String.class);
                    String auteur = auteurSnapshot.getValue(String.class);
                    listePost.add(new Publication(titre,auteur,"","","",null));
                }
                Layout = findViewById(R.id.Linear2);
                for(int i =0; i<listePost.size();i++){
                    Button button = new Button(getApplicationContext());
                    button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    button.setText("\uD83D\uDC49"+" "+listePost.get(i).getAuteur()+" - \uD83D\uDCDD "+listePost.get(i).getTitre());
                    Layout.addView(button);
                    String titre = listePost.get(i).getTitre();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("titrePublication",titre);
                            Intent intent = new Intent(getApplicationContext(), AfficherPublicationRecherchee.class);
                            intent.putExtras(bundle2);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                if(listePost.size()==0){
                    TextView tv = new TextView(getApplicationContext());
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    tv.setTextColor(Color.RED);
                    tv.setText("Pas de résultat pour votre recherche");
                    Layout.addView(tv);
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });
        return listePost;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}