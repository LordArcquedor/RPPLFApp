package rp.plf;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.security.Timestamp;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CreerPublicationRPActivity extends AppCompatActivity {

    private EditText editTextTextMultiLineContenu,editTextTextUrl,titre;
    private TextView editTextTextPersonName;
    private Button validerPubli;
    DrawerLayout drawerLayout;
    FirebaseUser user;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth auth;
    TextView emailT,nomT;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ModoActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_publication_rpactivity);

        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextMultiLineContenu = findViewById(R.id.editTextTextMultiLineContenu);
        editTextTextUrl = findViewById(R.id.editTextTextUrl);
        validerPubli = findViewById(R.id.validerPubli);
        titre = findViewById(R.id.EditTextTitre);
        auth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.nav_view);

        recupererPseudoJoueurActif();

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
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




        validerPubli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterPost();

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


    private void recupererPseudoJoueurActif() {
        user = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");
        Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot pseudo= null;
                String pseudoJoueur=null;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    pseudo  = userSnapshot.child("pseudo");
                }
                pseudoJoueur = pseudo.getValue(String.class);
                editTextTextPersonName.setText(pseudoJoueur);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });



    }

    private void ajouterPost() {
        if(editTextTextPersonName.getText().toString().equals("")){
            Toast.makeText(this, "Merci de relancer la page", Toast.LENGTH_SHORT).show();
        }else if(titre.getText().toString().equals("")){
            Toast.makeText(this, "Le titre de la publication ne doit pas être vide", Toast.LENGTH_SHORT).show();
        }
        else if(editTextTextMultiLineContenu.getText().toString().equals("")){
            Toast.makeText(this, "Le contenu de la publication ne doit pas être vide", Toast.LENGTH_SHORT).show();
        }        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersRef = database.getReference("publications");
            String stringTitre = titre.getText().toString();
            String contenuPost = editTextTextMultiLineContenu.getText().toString();
            String image = editTextTextUrl.getText().toString();
            String auteur = editTextTextPersonName.getText().toString();


            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
            String date = dateFormat.format(Calendar.getInstance().getTime());

            if(image.isEmpty()){
                image = "";
            }

            Publication publication = new Publication(stringTitre,"RPPLF",contenuPost,image,"RP",date);
            usersRef.child(publication.getTitre()).setValue(publication);
            Toast.makeText(this, "Publication publiée avec succès", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ModoActivity.class);
            startActivity(intent);
            finish();
        }

    }
}