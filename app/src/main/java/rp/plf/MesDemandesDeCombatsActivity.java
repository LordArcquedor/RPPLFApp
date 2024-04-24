package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MesDemandesDeCombatsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    LinearLayout linear;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 15;
    Boolean aDeja =false;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),GestionAreneActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_demandes_de_combats);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        linear = findViewById(R.id.linear);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");



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
                            actualiserMesDemandesDeCombat();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });
            }
        });
        runOnUiThread(thread);



    }

    private void actualiserMesDemandesDeCombat() {
        linear.removeAllViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Match");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    DataSnapshot adv  = userSnapshot.child("Adversaire");
                    DataSnapshot champ  = userSnapshot.child("Champion");
                    DataSnapshot date  = userSnapshot.child("Date");
                    DataSnapshot etat  = userSnapshot.child("Etat");
                    DataSnapshot heure  = userSnapshot.child("Heure");

                    String adversaire = adv.getValue(String.class);
                    String champion = champ.getValue(String.class);
                    String sDate = date.getValue(String.class);
                    String sEtat = etat.getValue(String.class);
                    String sHeure = heure.getValue(String.class);



                    if(nomT.getText().equals(champion) && sEtat.equals("Reserve")){
                        Button button = new Button(getApplicationContext());
                        button.setText("Demande de combat de "+adversaire+" le "+sDate+" à "+sHeure);
                        button.setTextColor(Color.BLACK);
                        button.setTextSize(15);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showConfirmationDialog(adversaire,champion,sDate,sEtat,sHeure);
                            }
                        });
                        linear.addView(button);

                    }


                }
                int numberOfButtons = countButtonsInLinearLayout(linear);
                if(numberOfButtons==0){
                    TextView textView = new TextView(getApplicationContext());
                    textView.setText("\uD83D\uDE25 Pas de demande de match \uD83D\uDE25");
                    textView.setTextSize(19);
                    textView.setTextColor(Color.RED);
                    linear.addView(textView);
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmationDialog(String adversaire, String champion, String sDate, String sEtat, String sHeure) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Avez vous effectué ce combat?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showConfirmationDialog2(adversaire,champion,sDate,sEtat,sHeure);
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


    private void showConfirmationDialog2(String adversaire, String champion, String sDate, String sEtat, String sHeure) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Résultat");
        builder.setMessage("Avez vous gagné ce combat?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                suppressionEnBase(adversaire,champion,sDate, sEtat,sHeure,champion);
                Intent intent = new Intent(getApplicationContext(),MesDemandesDeCombatsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                suppressionEnBase(adversaire,champion,sDate, sEtat,sHeure,adversaire);
                aDeja = false;
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("Resultats");
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        aDeja = false; // Initialiser à false ici

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            DataSnapshot date  = userSnapshot.child("Date");
                            DataSnapshot j1  = userSnapshot.child("Joueur1");
                            DataSnapshot j2  = userSnapshot.child("Joueur2");
                            DataSnapshot vainqueur  = userSnapshot.child("Vainqueur");

                            String joueur1 = j1.getValue(String.class);
                            String joueur2 = j2.getValue(String.class);
                            String sDate = date.getValue(String.class);
                            String sVainqueur = vainqueur.getValue(String.class);
                            if((joueur1.equals(adversaire) && joueur2.equals(champion) && sVainqueur.equals(adversaire))
                                    ||(joueur1.equals(champion) && joueur2.equals(adversaire) && sVainqueur.equals(adversaire)) ){
                                aDeja = true;
                            }
                        }
                        if(aDeja){
                            Toast.makeText(MesDemandesDeCombatsActivity.this, "Le joueur à déjà le badge", Toast.LENGTH_SHORT).show();
                            String monId = generateRandomString();
                            FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                            DatabaseReference newChildRef = database2.getReference("Resultats");
                            newChildRef.child(monId).child("Date").setValue(sDate);
                            newChildRef.child(monId).child("Joueur1").setValue(champion);
                            newChildRef.child(monId).child("Joueur2").setValue(adversaire);
                            newChildRef.child(monId).child("Vainqueur").setValue(adversaire);
                            Intent intent = new Intent(getApplicationContext(),MesDemandesDeCombatsActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.dismiss(); // Fermer la boîte de dialogue
                        }else{
                            updateBadge(adversaire);
                            String monId = generateRandomString();
                            FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                            DatabaseReference newChildRef = database3.getReference("Resultats");
                            newChildRef.child(monId).child("Date").setValue(sDate);
                            newChildRef.child(monId).child("Joueur1").setValue(champion);
                            newChildRef.child(monId).child("Joueur2").setValue(adversaire);
                            newChildRef.child(monId).child("Vainqueur").setValue(adversaire);
                            Intent intent = new Intent(getApplicationContext(),MesDemandesDeCombatsActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.dismiss(); // Fermer la boîte de dialogue

                        }
//                        listener.onBadgeCheckResult(aDeja);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });

//                checkSiDejaBadge(adversaire, champion, new BadgeCheckListener() {
//                    @Override
//                    public void onBadgeCheckResult(Boolean aDeja) {
//                        // Le résultat de la vérification est renvoyé ici
//                        if (aDeja) {
//                            Toast.makeText(MesDemandesDeCombatsActivity.this, "Le joueur à déjà le badge", Toast.LENGTH_SHORT).show();
//                            String monId = generateRandomString();
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//                            DatabaseReference newChildRef = database.getReference("Resultats");
//                            newChildRef.child(monId).child("Date").setValue(sDate);
//                            newChildRef.child(monId).child("Joueur1").setValue(champion);
//                            newChildRef.child(monId).child("Joueur2").setValue(adversaire);
//                            newChildRef.child(monId).child("Vainqueur").setValue(adversaire);
//                            dialog.dismiss(); // Fermer la boîte de dialogue
//                            // Le joueur a déjà un badge
//                            // Faites ici ce que vous voulez en cas de badge déjà attribué
//                        } else {
//                            updateBadge(adversaire);
//                            String monId = generateRandomString();
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//                            DatabaseReference newChildRef = database.getReference("Resultats");
//                            newChildRef.child(monId).child("Date").setValue(sDate);
//                            newChildRef.child(monId).child("Joueur1").setValue(champion);
//                            newChildRef.child(monId).child("Joueur2").setValue(adversaire);
//                            newChildRef.child(monId).child("Vainqueur").setValue(adversaire);
//                            Intent intent = new Intent(getApplicationContext(),MesDemandesDeCombatsActivity.class);
//                            startActivity(intent);
//                            finish();
//                            dialog.dismiss(); // Fermer la boîte de dialogue
//
//                            // Le joueur n'a pas encore de badge
//                            // Faites ici ce que vous voulez en cas de badge non attribué
//                        }
//                    }
//                });


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void updateBadge(String adversaire) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot badge  = userSnapshot.child("badge");
                    DataSnapshot pseudo  = userSnapshot.child("pseudo");

                    String sBadge = badge.getValue(String.class);
                    String sPseudo = pseudo.getValue(String.class);



                    if(sPseudo.equals(adversaire)){
                        String newBadgeValue ="";
                        switch (sBadge.length()){
                            case 0 :
                                newBadgeValue = sBadge + "a";
                                break;
                            case 1 :
                                newBadgeValue = sBadge + "b";
                                break;
                            case 2 :
                                newBadgeValue = sBadge + "c";
                                break;
                            case 3 :
                                newBadgeValue = sBadge + "d";
                                break;
                            case 4 :
                                newBadgeValue = sBadge + "e";
                                break;
                            case 5 :
                                newBadgeValue = sBadge + "f";
                                break;
                            case 6 :
                                newBadgeValue = sBadge + "g";
                                break;
                            case 7 :
                                newBadgeValue = sBadge + "h";
                                break;
                            case 8 :
                                newBadgeValue = sBadge + "i";
                                break;
                            case 9 :
                                newBadgeValue = sBadge + "j";
                                break;
                            case 10 :
                                newBadgeValue = sBadge + "k";
                                break;
                        }
                        userSnapshot.getRef().child("badge").setValue(newBadgeValue);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });
    }

    private void suppressionEnBase(String adversaireP, String champP, String dateP, String etatP, String heureP,String winner) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Match");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot adv  = userSnapshot.child("Adversaire");
                    DataSnapshot champ  = userSnapshot.child("Champion");
                    DataSnapshot date  = userSnapshot.child("Date");
                    DataSnapshot etat  = userSnapshot.child("Etat");
                    DataSnapshot heure  = userSnapshot.child("Heure");

                    String adversaire = adv.getValue(String.class);
                    String champion = champ.getValue(String.class);
                    String sDate = date.getValue(String.class);
                    String sEtat = etat.getValue(String.class);
                    String sHeure = heure.getValue(String.class);

                    if(adversaire.equals(adversaireP)&& champion.equals(champP) && sDate.equals(dateP)&&sEtat.equals(etatP)&&sHeure.equals(heureP)){
                        userSnapshot.getRef().removeValue();

//                        String monId = generateRandomString();
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference newChildRef = database.getReference("Resultats");
//                        newChildRef.child(monId).child("Date").setValue(sDate);
//                        newChildRef.child(monId).child("Joueur1").setValue(champion);
//                        newChildRef.child(monId).child("Joueur2").setValue(adversaire);
//                        newChildRef.child(monId).child("Vainqueur").setValue(winner);
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });
    }


    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public interface BadgeCheckListener {
        void onBadgeCheckResult(Boolean aDeja);
    }
}