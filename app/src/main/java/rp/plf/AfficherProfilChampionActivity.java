package rp.plf;


import android.app.AlertDialog;
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
import java.text.SimpleDateFormat;
import java.util.Date;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AfficherProfilChampionActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    TextView nomDresseur;
    LinearLayout linear;
    String nomChamp;
    Boolean aDeja = false;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),LesChampionsActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_profil_champion);
        linear = findViewById(R.id.linear);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        nomDresseur = findViewById(R.id.nomDresseur);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");
        auth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        nomChamp = bundle.getString("nomChamp");

//        afficherContenuMatch();

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
                    case R.id.parametre:
                    {
                        Intent intent = new Intent(getApplicationContext(),ParametreActivity.class);
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
                            nomDresseur.setText("Disponibilité de "+nomChamp);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference usersRef = database.getReference("Match");


                            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    linear.removeAllViews();

                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        DataSnapshot champion  = userSnapshot.child("Champion");
                                        DataSnapshot etat  = userSnapshot.child("Etat");
                                        DataSnapshot date  = userSnapshot.child("Date");
                                        DataSnapshot heure  = userSnapshot.child("Heure");



                                        String sChampion = champion.getValue(String.class);
                                        String sEtat = etat.getValue(String.class);
                                        String sDate = date.getValue(String.class);
                                        String sHeure = heure.getValue(String.class);

                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                        Date dateDuJour = new Date();

                                        try {
                                            Date currentDate = format.parse(sDate);

                                            // Étape 3 : Comparer les dates
                                            if (currentDate.before(dateDuJour)) {
                                                userSnapshot.getRef().removeValue();


                                            } else if (currentDate.after(dateDuJour)) {
                                            } else {
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }



                                        if(sEtat.equals("Libre") && sChampion.equals(nomChamp)){
                                            Button button = new Button(getApplicationContext());
                                            button.setPadding(10,5,10,5);
                                            button.setText("\uD83C\uDD9A " +" "+sDate+" à "+sHeure+" \uD83D\uDCA5");
                                            button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    showConfirmationDialog(userSnapshot);
                                                }


                                            });
                                            linear.addView(button);

                                        }

                                    }
                                    int numberOfButtons = countButtonsInLinearLayout(linear);
                                    if(numberOfButtons==0){
                                        TextView textView = new TextView(getApplicationContext());
                                        textView.setText("\uD83D\uDE25 Pas de disponibilité pour le moment \uD83D\uDE25");
                                        textView.setTextSize(19);
                                        textView.setTextColor(Color.RED);
                                        linear.addView(textView);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });
            }
        });
//        thread.start();
        runOnUiThread(thread);



    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showConfirmationDialog(DataSnapshot userSnapshot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir défier ce champion ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userSnapshot.getRef().child("Etat").setValue("Reserve");
                userSnapshot.getRef().child("Adversaire").setValue(nomT.getText());
                Toast.makeText(AfficherProfilChampionActivity.this, "Votre demande a été prise en compte.", Toast.LENGTH_LONG).show();
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

    private int countButtonsInLinearLayout(LinearLayout linearLayout) {
        int buttonCount = 0;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof Button) {
                buttonCount++;
            }
        }
        return buttonCount;
    }
}