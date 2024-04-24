package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class SupprimerPublicationActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FirebaseUser user;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth auth;
    TextView emailT,nomT;
    public LinearLayout Layout;
    public ArrayList<Publication> listePost;
    private Button rechercher, rechercherAuteur;
    private TextView contenuTitre;
    public String contientDansTitre;
    public Bundle bundle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ModoActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_publication);
        auth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.nav_view);
        user = auth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");


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
                    case R.id.parametre:
                    {
                        Intent intent = new Intent(getApplicationContext(),ParametreActivity.class);
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
                            showAlertDialog(titre);

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
                            showAlertDialog(titre);
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

    public void showAlertDialog(String nomPubli){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Supprimer publication");
        alert.setMessage("Voulez vous supprimer la publication Continuer ?");
        alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("publications");

                // Utilisez la méthode child() pour obtenir la référence à la publication spécifique
                DatabaseReference publicationRef = usersRef.child(nomPubli);

                publicationRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SupprimerPublicationActivity.this, "La publication a bien été supprimée", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ModoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SupprimerPublicationActivity.this, "Erreur lors de la suppression de la publication", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SupprimerPublicationActivity.this, "Suppression annulée", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }




}