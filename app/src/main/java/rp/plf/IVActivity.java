package rp.plf;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class IVActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView textView25;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),JeDebuteActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivactivity);



        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");
        textView25 = findViewById(R.id.textView25);

        auth = FirebaseAuth.getInstance();

        textView25.setText("Le terme IV est l'abréviation d'Individual Value. Pour chaque statistique, les IV sont compris entre 0 et 31 et sont déterminés lorsque le Pokémon est créé (soit lors de l'ouverture d'un combat dans les hautes herbes, soit lors de son éclosion). 1 IV correspond à 1 point dans la statistique correspondante.\n" +
                "\n" +
                "Les IV sont des valeurs distribuées au Pokémon à sa naissance dans le jeu, c'est à dire lorsqu'il éclot ou si vous rencontrez un Pokémon sauvage pour la première fois. Si vous faites éclore 5 Salamèche, vous pourrez constater que leurs statistiques sont différentes : l'un aura plus d'Attaque, l'autre moins de Vitesse... si les IV n'existaient pas, ces Salamèche auraient tous les mêmes statistiques. En gros, les IV définissent si votre Pokémon est naturellement doué ou pas.\n" +
                "Les IV se mesurent sur une échelle de 0 à 31 pour une statistique. Si un Pokémon a 2 IV en Attaque, il sera moins fort physiquement que ses congénères de la même espèce. En revanche, s'il a 30 IV, il aura une meilleure statistique en Attaque que la moyenne.\n" +
                "Pourquoi de 0 à 31 ? Parce que, au niveau 100, les variations de statistique vont de 0 à 31 points ! Concrètement, un Pokémon qui a 0 IV en Défense aura, au niveau 100, par exemple 300, alors qu'un Pokémon de la même espèce qui a 31 IV aura 331 de Défense.\n" +
                "Notez aussi que les EV et la nature influencent ses statistiques finales, le calcul peut donc être faussé si vous ne connaissez pas précisément ses EV (voir plus bas).\n" +
                "\n" +
                "On peut pas maintenant modifier les IV d'un Pokémon. Certains objets en jeu permettent d'augmenter les IV au maximum!\n" +
                "Un Pokémon peut aussi transmettre une partie de son génome à sa descendance lors de reproduction.");


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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}