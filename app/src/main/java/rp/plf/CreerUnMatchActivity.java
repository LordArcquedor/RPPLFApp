package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class CreerUnMatchActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    Button valider;
    EditText editTextNumber,editTextNumber2;
    CalendarView calendarView;
    String selectedDate;
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
        Locale frenchLocale = new Locale(getString(R.string.locale_language), getString(R.string.locale_country));
        Locale.setDefault(frenchLocale);
        Configuration config = new Configuration();
        config.locale = frenchLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_creer_un_match);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        valider = findViewById(R.id.valider);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        calendarView = findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        calendarView.setDate(calendar.getTimeInMillis(), false, true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        selectedDate = dateFormat.format(calendar.getTime());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // Mettre à jour la variable avec la date sélectionnée
                selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);

                // Récupérer la date du jour
                Calendar currentDateCalendar = Calendar.getInstance();

                // Vérifier si la date sélectionnée est antérieure à la date du jour
                Calendar selectedDateCalendar = Calendar.getInstance();
                selectedDateCalendar.set(year, month, dayOfMonth);

                if (selectedDateCalendar.before(currentDateCalendar)) {
                    // La date sélectionnée est antérieure à la date du jour, réinitialiser le CalendarView à la date du jour
                    calendarView.setDate(currentDateCalendar.getTimeInMillis(), false, true);

                    // Mettre à jour la variable avec la date du jour
                    selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", currentDateCalendar.get(Calendar.DAY_OF_MONTH),
                            currentDateCalendar.get(Calendar.MONTH) + 1, currentDateCalendar.get(Calendar.YEAR));

                    showToast("La date sélectionnée ne peut pas être antérieure à la date du jour !");
                }
            }
        });

        valider.setOnClickListener(v -> {
            if(editTextNumber.getText().toString().isEmpty()){
                editTextNumber.setText("00");
            }
            if(editTextNumber2.getText().toString().isEmpty()){
                editTextNumber2.setText("00");
            }
            int heure = Integer.parseInt(editTextNumber.getText().toString());
            int minute = Integer.parseInt(editTextNumber2.getText().toString());

            if(heure>23){
                editTextNumber.setText("23");
            }
            if(minute>59){
                editTextNumber2.setText("59");
            }
            if(heure<10){
                editTextNumber.setText("0"+heure);
            }
            if(minute<10){
                editTextNumber2.setText("0"+minute);

            }
            String finalHeure = editTextNumber.getText()+":"+editTextNumber2.getText();
            Toast.makeText(this, ""+selectedDate+" "+finalHeure, Toast.LENGTH_SHORT).show();
            showConfirmationDialog(selectedDate,finalHeure);

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
//        thread.start();
        runOnUiThread(thread);



    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmationDialog(String date, String heure) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir créer ce match le "+date+" à "+heure+"?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String monId = generateRandomString();
                DatabaseReference newChildRef = database.getReference("Match");
                newChildRef.child(monId).child("Date").setValue(date);
                newChildRef.child(monId).child("Heure").setValue(heure);
                newChildRef.child(monId).child("Etat").setValue("Libre");
                newChildRef.child(monId).child("Adversaire").setValue("");
                newChildRef.child(monId).child("Champion").setValue(nomT.getText());


                Toast.makeText(CreerUnMatchActivity.this, "Le match a bien été créé le "+date+" à "+heure, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),GestionAreneActivity.class);
                startActivity(intent);
                finish();
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


}