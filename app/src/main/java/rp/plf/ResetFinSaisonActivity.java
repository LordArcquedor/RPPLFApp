package rp.plf;

import static rp.plf.AjouterResultatActivity.generateRandomString;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.util.ArrayList;

public class ResetFinSaisonActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FirebaseUser user;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth auth;
    TextView emailT,nomT;

    Spinner spinner;
    EditText editTextTextPersonName2,editTextTextPersonName3;
    Button button5;

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
        setContentView(R.layout.activity_reset_fin_saison);
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
        spinner = findViewById(R.id.spinner2);
        editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2);
        button5 = findViewById(R.id.button5);
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3);

        ArrayAdapter<String> contenuSpiner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        spinner.setAdapter(contenuSpiner);

        // Récupérez les données depuis Firebase et mettez à jour l'adaptateur lorsqu'elles sont disponibles
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contenuSpiner.clear();
                contenuSpiner.add("");
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot pseudoSnapshot = userSnapshot.child("pseudo");
                    String pseudo = pseudoSnapshot.getValue(String.class);
                    if(!pseudo.equals("Plateau de la RPPLF")){
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

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = findViewById(R.id.spinner2);
                String valeurSelectionnee ="";
                try{
                    valeurSelectionnee = spinner.getSelectedItem().toString();
                }catch(Exception e){
                    e.printStackTrace();
                }
                EditText editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2);
                String nom = editTextTextPersonName2.getText().toString();


                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId == -1) {
                    Toast.makeText(ResetFinSaisonActivity.this, "Merci de choisir si le maitre à perdu ou non", Toast.LENGTH_SHORT).show();
                    return; // Quittez la méthode onClick pour éviter de continuer avec une sélection invalide.
                }
                String radioValue="";
                if (selectedRadioButtonId == R.id.radioButtonOption1) {
                    radioValue = "Oui";
                } else {
                    radioValue = "Non";
                }




                if(radioValue.equals("Oui")){
                    if(nom.isEmpty() && valeurSelectionnee.equals("")){
                        Toast.makeText(ResetFinSaisonActivity.this, "Merci d'indiquer le nom du maitre", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ResetFinSaisonActivity.this, "Reset de la saison en cours...", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ResetFinSaisonActivity.this, "Les conseils 4 sont de nouveau challenger ...", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ResetFinSaisonActivity.this, "Les champions sont de nouveau challenger...", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ResetFinSaisonActivity.this, "Le maitre devient conseil 4.", Toast.LENGTH_SHORT).show();

                        String banniere = editTextTextPersonName3.getText().toString();
                        String pseudo = editTextTextPersonName2.getText().toString();

                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                        DatabaseReference usersRef2 = database2.getReference("Conseil");
                        usersRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    userSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // En cas d'erreur lors de la récupération des données
                            }
                        });



                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference usersRef = database.getReference("utilisateurs");
                        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    DataSnapshot pseudoSnapshot = userSnapshot.child("role");
                                    DataSnapshot pseudoSnapshot2 = userSnapshot.child("pseudo");

                                    String role = pseudoSnapshot.getValue(String.class);

                                    String pseudoJoueur = pseudoSnapshot2.getValue(String.class);

                                    Spinner spinner = findViewById(R.id.spinner2);
                                    String valeurSelectionnee ="";
                                    try{
                                        valeurSelectionnee = spinner.getSelectedItem().toString();
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }



                                    if(role.equals("Conseil 4") || role.equals("Champion")){
                                        userSnapshot.child("role").getRef().setValue("Challenger");
                                    }
                                    if(role.equals("Maitre")){
                                        userSnapshot.child("role").getRef().setValue("Conseil 4");

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        String monId = generateRandomString();
                                        DatabaseReference newChildRef = database.getReference("Conseil");
                                        newChildRef.child(monId).child("Lien").setValue(banniere);
                                        newChildRef.child(monId).child("Nom").setValue(valeurSelectionnee);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // En cas d'erreur lors de la récupération des données
                            }
                        });



                        FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                        DatabaseReference usersRef3 = database3.getReference("utilisateurs");
                        usersRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    DataSnapshot pseudoSnapshot2 = userSnapshot.child("pseudo");

                                    String pseudoJoueur = pseudoSnapshot2.getValue(String.class);

                                    Spinner spinner = findViewById(R.id.spinner2);
                                    String valeurSelectionnee ="";
                                    try{
                                        valeurSelectionnee = spinner.getSelectedItem().toString();
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                    if(pseudoJoueur.equals(valeurSelectionnee)){
                                        userSnapshot.child("role").getRef().setValue("Maitre");

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // En cas d'erreur lors de la récupération des données
                            }
                        });





                    }

                }else {

                        Toast.makeText(ResetFinSaisonActivity.this, "Reset de la saison en cours...", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ResetFinSaisonActivity.this, "Les conseils 4 sont de nouveau challenger ...", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ResetFinSaisonActivity.this, "Les champions sont de nouveau challenger...", Toast.LENGTH_SHORT).show();



                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                        DatabaseReference usersRef2 = database2.getReference("Conseil");
                        usersRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    userSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // En cas d'erreur lors de la récupération des données
                            }
                        });

                        String banniere = editTextTextPersonName3.getText().toString();
                        String pseudo = editTextTextPersonName2.getText().toString();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference usersRef = database.getReference("utilisateurs");
                        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                contenuSpiner.clear();
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    DataSnapshot pseudoSnapshot = userSnapshot.child("role");
                                    String role = pseudoSnapshot.getValue(String.class);
                                    if(role.equals("Conseil 4") || role.equals("Champion")){
                                        userSnapshot.child("role").getRef().setValue("Challenger");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // En cas d'erreur lors de la récupération des données
                            }
                        });

                    }

                Intent intent = new Intent(getApplicationContext(),ModoActivity.class);
                startActivity(intent);
                finish();

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

}