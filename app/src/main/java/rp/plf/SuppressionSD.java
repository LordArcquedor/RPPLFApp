package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class SuppressionSD extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FirebaseUser user;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth auth;
    TextView emailT,nomT;
    LinearLayout linear, linear22;


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
        setContentView(R.layout.activity_suppression_sd);
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
        linear = findViewById(R.id.linear);
        linear22 = findViewById(R.id.linear22);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("ShowdownTeam");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linear.removeAllViews();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot pseudoSnapshot  = userSnapshot.child("lien");
                    DataSnapshot pseudoSnapshot2  = userSnapshot.child("auteur");
                    DataSnapshot pseudoSnapshot3  = userSnapshot.child("nomEquipe");
                    DataSnapshot pseudoSnapshot4  = userSnapshot.child("paste");


                    String lien = pseudoSnapshot.getValue(String.class);
                    String nom = pseudoSnapshot2.getValue(String.class);
                    String nomTeam = pseudoSnapshot3.getValue(String.class);
                    String importLien = pseudoSnapshot4.getValue(String.class);

                    Button button = new Button(getApplicationContext());
                    button.setText("✏️️  "+nom +" : "+nomTeam);
                    button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showConfirmationDialog2(nomTeam);
                        }
                    });
                    linear.addView(button);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });



        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference usersRef2 = database2.getReference("ShodowmnMatch");
        usersRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linear22.removeAllViews();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot pseudoSnapshot  = userSnapshot.child("lien");
                    DataSnapshot pseudoSnapshot2  = userSnapshot.child("auteur");
                    DataSnapshot pseudoSnapshot3  = userSnapshot.child("nomMatch");

                    String lien = pseudoSnapshot.getValue(String.class);
                    String nom = pseudoSnapshot2.getValue(String.class);
                    String titre = pseudoSnapshot3.getValue(String.class);

                    Button button = new Button(getApplicationContext());
                    button.setText("✏️️  "+nom +" : "+titre);
                    button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showConfirmationDialog(titre);
                        }
                    });
                    linear22.addView(button);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
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

    private void showConfirmationDialog(String titre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce match ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("ShodowmnMatch");
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                            DataSnapshot nomMatch  = userSnapshot.child("nomMatch");

                            String nom = nomMatch.getValue(String.class);

                            if(nom.equals(titre)){
                                userSnapshot.getRef().removeValue();
                                Toast.makeText(SuppressionSD.this, "Le match a bien été supprimé", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ModoActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action à effectuer si l'utilisateur clique sur le bouton "Non"
                dialog.dismiss(); // Fermer la boîte de dialogue
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showConfirmationDialog2(String nom) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce match ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("ShowdownTeam");
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                            DataSnapshot nomMatch  = userSnapshot.child("nomEquipe");

                            String nomDuMatch = nomMatch.getValue(String.class);

                            if(nomDuMatch.equals(nom)){
                                userSnapshot.getRef().removeValue();
                                Toast.makeText(SuppressionSD.this, "L'équipe a bien été supprimé", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ModoActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Action à effectuer si l'utilisateur clique sur le bouton "Non"
                dialog.dismiss(); // Fermer la boîte de dialogue
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}