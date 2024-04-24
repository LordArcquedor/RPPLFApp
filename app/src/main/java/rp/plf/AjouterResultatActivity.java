package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class AjouterResultatActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    private Spinner spinner;
    private ArrayList<String> data;
    Button validerButton;
    String formattedDate,winner;
    private String pseudoUtilisateur;
    Boolean isDeja;
    Bundle bundle ;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 15; // Longueur de la chaîne générée

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),GestionAreneActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_resultat);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        validerButton = findViewById(R.id.validerButton);
        bundle = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");

        data = new ArrayList<>();

        spinner = findViewById(R.id.spinner);

        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
        DatabaseReference usersRef3 = database3.getReference("utilisateurs");
        Query query = usersRef3.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot pseudoSnapshot  = userSnapshot.child("pseudo");

                    View headerView = navigationView.getHeaderView(0);
                    nomT = headerView.findViewById(R.id.Your_name);
                    String pseudo = pseudoSnapshot.getValue(String.class);
                    nomT.setText(pseudo);
                    Bundle bundle = new Bundle();
                    pseudoUtilisateur = pseudo;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });

        ArrayAdapter<String> contenuSpiner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        spinner.setAdapter(contenuSpiner);

        // Récupérez les données depuis Firebase et mettez à jour l'adaptateur lorsqu'elles sont disponibles
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contenuSpiner.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot pseudoSnapshot = userSnapshot.child("pseudo");
                    String pseudo = pseudoSnapshot.getValue(String.class);
                    if(!pseudo.equals(pseudoUtilisateur) && !pseudo.equals("Plateau de la RPPLF")){
                        contenuSpiner.add(pseudo);
                    }
                }
                contenuSpiner.notifyDataSetChanged(); // Notifiez à l'adaptateur que les données ont changé.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });



        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = findViewById(R.id.spinner);
                if (spinner.getSelectedItem() == null) {
                    Toast.makeText(AjouterResultatActivity.this, "Veuillez sélectionner un joueur.", Toast.LENGTH_SHORT).show();
                    return; // Quittez la méthode onClick pour éviter de continuer avec une sélection invalide.
                }
                String valeurSelectionnee = spinner.getSelectedItem().toString();

                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId == -1) {
                    Toast.makeText(AjouterResultatActivity.this, "Veuillez sélectionner une réponse entre oui et non.", Toast.LENGTH_SHORT).show();
                    return; // Quittez la méthode onClick pour éviter de continuer avec une sélection invalide.
                }

                String radioValue;
                if (selectedRadioButtonId == R.id.radioButtonOption1) {
                    radioValue = "Oui";
                    winner = nomT.getText().toString();
                } else {
                    radioValue = "Non";
                    winner = valeurSelectionnee;
                }
                LocalDate date = null;
                DateTimeFormatter formatter = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    date = LocalDate.now();
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    formattedDate = date.format(formatter);
                }
                if(radioValue.equals("Oui")){
                    ajoutResEnBase(valeurSelectionnee,nomT.getText().toString(),formattedDate, winner);
                    Intent intent = new Intent(getApplicationContext(),GestionAreneActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    if(winner.equals(valeurSelectionnee)){
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        // Référence à l'emplacement du match dans la base de données
                        DatabaseReference matchReference = databaseReference.child("Resultats");
                        MatchParams matchParams = new MatchParams();
                        matchParams.setTeam1(nomT.getText().toString());
                        matchParams.setTeam2(valeurSelectionnee);
                        matchParams.setvainqueur(valeurSelectionnee);
//                    Query query = matchReference.orderByChild("Vainqueur").equalTo(matchParams.getvainqueur());
                        matchReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean matchExists = false;
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    // Vérifier si le reste des paramètres du match correspond également
                                    MatchParams matchSnapshot = snapshot.getValue(MatchParams.class);
                                    DataSnapshot joueur1  = snapshot.child("Joueur1");
                                    DataSnapshot joueur2  = snapshot.child("Joueur2");
                                    DataSnapshot vainqueur  = snapshot.child("Vainqueur");
                                    String j1 = joueur1.getValue(String.class);
                                    String j2 = joueur2.getValue(String.class);
                                    String win = vainqueur.getValue(String.class);


                                    if (    j1.equals(matchParams.getTeam1()) &&
                                            j2.equals(matchParams.getTeam2()) &&
                                            win.equals(matchParams.getvainqueur())
                                            ||      j1.equals(matchParams.getTeam2()) &&
                                            j2.equals(matchParams.getTeam1()) &&
                                            win.equals(matchParams.getvainqueur())
                                    ){
                                        // Le match correspondant a été trouvé dans la base de données
                                        matchExists = true;
                                        break;
                                    }
                                }

                                if (matchExists) {
                                    // Le match existe dans la base de données
                                    Toast.makeText(AjouterResultatActivity.this, "Le joueur possède le badge.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Le match n'existe pas dans la base de données
                                    Toast.makeText(AjouterResultatActivity.this, "Le badge a été distribué", Toast.LENGTH_SHORT).show();
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
                                                if(sPseudo.equals(valeurSelectionnee)){
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
                                                    ajoutResEnBase(valeurSelectionnee,nomT.getText().toString(),formattedDate, winner);
                                                    Intent intent = new Intent(getApplicationContext(),GestionAreneActivity.class);
                                                    startActivity(intent);
                                                    finish();
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
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // En cas d'erreur lors de la lecture des données
                                // Vous pouvez gérer l'erreur ici si nécessaire
                            }
                        });

                    }




                }

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

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }


    private void ajoutResEnBase(String adversaireP, String champP, String dateP, String winner) {
//        Toast.makeText(this, "Ajout du résultat : OK", Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String monId = generateRandomString();
        DatabaseReference newChildRef = database.getReference("Resultats");
        newChildRef.child(monId).child("Date").setValue(dateP);
        newChildRef.child(monId).child("Joueur1").setValue(champP);
        newChildRef.child(monId).child("Joueur2").setValue(adversaireP);
        newChildRef.child(monId).child("Vainqueur").setValue(winner);

    }

}