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

public class EVActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView textView25;
    FirebaseAuth auth;
    TextView  emailT,nomT;
    FirebaseUser user;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),JeDebuteActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evactivity);

        textView25 = findViewById(R.id.textView25);
        auth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");
        auth = FirebaseAuth.getInstance();


        textView25.setText("Les EV, ou Effort Value, sont des « points » qu'un Pokémon peut obtenir, et qui permettent une augmentation de ses statistiques (au niveau 100, on a 1 point de statistique supplémentaire par « tranche » de 4 EV).\n" +
                "\nLe Principe:\n" +
                "Les EV sont des valeurs numériques, qui existent pour chacune des statistiques d'un Pokémon. Les EV représentent une augmentation (« bonus ») des statistiques de son Pokémon.\n" +
                "\n" +
                "Tout Pokémon sauvage, ou nouvellement obtenu (mis à part ceux échangés contre des \"vrais\" joueurs, qui conservent leur répartition d'origine) n'a aucun EV à la base.\n" +
                "\n" +
                "Le principal moyen de gagner des EV est de combattre. Chaque Pokémon vaincu rapporte une certaine quantité d'EV spécifique à son espèce (en général, ils correspondent à sa statistique de base la plus élevée, et les Pokémon plus évolués donnent plus d'EV ; un Pokémon adverse peut donner différents types d'EV). Chaque Pokémon recevant de l’expérience remporte le total d'EV, contrairement aux points d'expérience qui sont divisés entre les participants. Ainsi, les Pokémon peuvent gagner des EV même s'ils ne sont pas envoyés au combat grâce au Multi Exp. Certains objets et un statut particulier permettent aussi d'augmenter les EV gagnés en combat.\n" +
                "\n" +
                "À noter que les combats en link et les zones de combat ne rapportent pas d'EV. Seuls les combats apportant des points d'expérience apportent des EV." +
                "Limites\n" +
                "Il n'est pas possible de gagner des EV de manière illimitée.\n" +
                "\n" +
                "Pour chacune des statistiques, il est possible d'obtenir un maximum de 252 EV, tout en ayant un maximum de 510 EV répartis sur le total des statistiques.\n" +
                "\n" +
                "Avant la sixième génération, il était possible d'atteindre 255 EV par statistique, cependant les 3 derniers EV ne permettent pas de former un point de statistique supplémentaire, d'où l'inutilité de dépasser 252. Dans Pokémon X et Y, la limite a donc été réduite à 252 EV par statistique.\n" +
                "\n" +
                "Les 3 EV restants peuvent alors être mis sur une autre statistique, limitant le gaspillage.\n" +
                "\n" +
                "Les EV peuvent ainsi augmenter, au maximum, de 63, une statistique donnée, pour un total de 127 en plus pour 510 EV répartis.\n" +
                "\n" +
                "Avant la cinquième génération, un Pokémon ayant atteint son niveau 100 ne pouvait plus recevoir d'EV par le combat ; le seul autre moyen non dépendant de ce mode d'obtention étant alors d'utiliser des accélérateurs, cela limitait fortement leurs possibilités de placement si une répartition adéquate n'avait pas été obtenue auparavant ou si le Pokémon avait été obtenu directement au niveau 100.");


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
                    case R.id.parametre:
                    {
                        Intent intent = new Intent(getApplicationContext(),ParametreActivity.class);
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