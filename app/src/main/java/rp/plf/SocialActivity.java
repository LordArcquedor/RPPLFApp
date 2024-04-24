package rp.plf;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collections;

public class SocialActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    Button button2;
    EditText editTextTextPersonName;
    LinearLayout Linear12;
    HorizontalScrollView scrollView;
    ImageView sugg1,sugg2,sugg3,sugg4,sugg5;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        button2= findViewById(R.id.button2);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        sugg1 = findViewById(R.id.sugg1);
        sugg2 = findViewById(R.id.sugg2);
        sugg3 = findViewById(R.id.sugg3);
        sugg4 = findViewById(R.id.sugg4);
        sugg5 = findViewById(R.id.sugg5);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");

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


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechercherJoueur();
            }
        });

        mettreLesSuggestions();

    }



    private void rechercherJoueur() {
        String pseudoRecherche = editTextTextPersonName.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");

        Query query = usersRef.orderByChild("pseudo");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Linear12 = findViewById(R.id.Linear12);
                Linear12.removeAllViews();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String pseudo = userSnapshot.child("pseudo").getValue(String.class);
                    if (pseudo != null && pseudo.contains(pseudoRecherche)) {
                        Button button = new Button(getApplicationContext());
                        button.setText(pseudo);
                        Linear12.addView(button);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("PSEUDO",pseudo);
                                Intent intent = new Intent(getApplicationContext(),AfficherProfilJoueurActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mettreLesSuggestions() {
        scrollView = findViewById(R.id.scrollView);

        ArrayList<Utilisateur> listeJoueur = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");


        //Récupération des avatars
        Query query = usersRef.orderByChild("pseudo");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot avatarsSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot pseudoS = avatarsSnapshot.child("pseudo");
                    DataSnapshot photoD = avatarsSnapshot.child("photo");


                    Utilisateur user =new Utilisateur();
                    user.setPhoto(photoD.getValue(String.class));
                    user.setPseudo(pseudoS.getValue(String.class));

//                    Utilisateur user = avatarsSnapshot.getValue(Utilisateur.class);
                    listeJoueur.add(user);
                }
                Collections.shuffle(listeJoueur);
                int desiredWidth = 100;
                int desiredHeight = 100;
                Picasso.get()
                        .load(listeJoueur.get(0).getPhoto())
                        .resize(desiredWidth, desiredHeight)
                        .centerCrop()
                        .into(sugg1);

                Picasso.get()
                        .load(listeJoueur.get(1).getPhoto())
                        .resize(desiredWidth, desiredHeight)
                        .centerCrop()
                        .into(sugg2);
                Picasso.get()
                        .load(listeJoueur.get(2).getPhoto())
                        .resize(desiredWidth, desiredHeight)
                        .centerCrop()
                        .into(sugg3);
                Picasso.get()
                        .load(listeJoueur.get(3).getPhoto())
                        .resize(desiredWidth, desiredHeight)
                        .centerCrop()
                        .into(sugg4);
                Picasso.get()
                        .load(listeJoueur.get(4).getPhoto())
                        .resize(desiredWidth, desiredHeight)
                        .centerCrop()
                        .into(sugg5);

                sugg1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(SocialActivity.this,listeJoueur.get(0).getPseudo() , Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                sugg1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("PSEUDO",listeJoueur.get(0).getPseudo());
                        Intent intent = new Intent(getApplicationContext(),AfficherProfilJoueurActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });


                sugg2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(SocialActivity.this,listeJoueur.get(1).getPseudo() , Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                sugg2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("PSEUDO",listeJoueur.get(1).getPseudo());
                        Intent intent = new Intent(getApplicationContext(),AfficherProfilJoueurActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });
                sugg3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(SocialActivity.this,listeJoueur.get(2).getPseudo() , Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                sugg3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("PSEUDO",listeJoueur.get(2).getPseudo());
                        Intent intent = new Intent(getApplicationContext(),AfficherProfilJoueurActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });
                sugg4.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(SocialActivity.this,listeJoueur.get(3).getPseudo() , Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                sugg4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("PSEUDO",listeJoueur.get(3).getPseudo());
                        Intent intent = new Intent(getApplicationContext(),AfficherProfilJoueurActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });
                sugg5.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(SocialActivity.this,listeJoueur.get(4).getPseudo() , Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                sugg5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("PSEUDO",listeJoueur.get(4).getPseudo());
                        Intent intent = new Intent(getApplicationContext(),AfficherProfilJoueurActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });





    }
}

        //Trouver la liste des utilisateurs
        //Prendre 4 aléatoires, mettre l'image et le nom sur chaque
        //Le rendre clickable
        //renvoyer vers l'activité

