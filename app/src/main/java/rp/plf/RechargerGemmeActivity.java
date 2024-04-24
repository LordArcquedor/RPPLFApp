package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class RechargerGemmeActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    Button button6,button123,button80;
    private static final String PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=vincent.aubert45130@gmail.com&item_name=Donation&amount=1.00&currency_code=EUR";
    private static final String PAYPAL_URL_100 = "https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=vincent.aubert45130@gmail.com&item_name=Donation&amount=9.00&currency_code=EUR";
    private static final String PAYPAL_URL_1000 = "https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=vincent.aubert45130@gmail.com&item_name=Donation&amount=80.00&currency_code=EUR";
    private static final int PAYPAL_REQUEST_CODE = 123; // Code de demande pour le résultat du paiement
    int value ;




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),BoutiqueActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharger_gemme);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();

        button80 = findViewById(R.id.button80);
        button6 = findViewById(R.id.button6);
        button123 = findViewById(R.id.button123);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPayPalPayment();
            }
        });


        button80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPayPalPayment1000();

            }
        });

        button123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPayPalPayment100();

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
                    case R.id.parametre:
                    {
                        Intent intent = new Intent(getApplicationContext(),ParametreActivity.class);
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

    private void openPayPalPayment() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Paiement des 10 cristaux");
        alert.setMessage("Vous allez être redirigé vers Paypal pour pouvoir faire le paiement de 1€. Continuer ?");
        alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(PAYPAL_URL));
                startActivityForResult(intent, PAYPAL_REQUEST_CODE);
                value = 10;
            }
        });
        alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RechargerGemmeActivity.this, "Vous n'êtes pas redirigé", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();

    }
    private void openPayPalPayment100() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Paiement des 100 cristaux");
        alert.setMessage("Vous allez être redirigé vers Paypal pour pouvoir faire le paiement de 9€. Continuer ?");
        alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(PAYPAL_URL_100));
                startActivityForResult(intent, PAYPAL_REQUEST_CODE);
                value = 100;
            }
        });
        alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RechargerGemmeActivity.this, "Vous n'êtes pas redirigé", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();


    }
    private void openPayPalPayment1000() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Paiement des 1000 cristaux");
        alert.setMessage("Vous allez être redirigé vers Paypal pour pouvoir faire le paiement de 80€. Continuer ?");
        alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(PAYPAL_URL_1000));
                startActivityForResult(intent, PAYPAL_REQUEST_CODE);
                value = 1000;
            }
        });
        alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RechargerGemmeActivity.this, "Vous n'êtes pas redirigé", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Le paiement a été effectué avec succès", Toast.LENGTH_SHORT).show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("utilisateurs");
                Query query = usersRef.equalTo(user.getUid());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            DataSnapshot pseudoSnapshot  = userSnapshot.child("argent");
                            int argentActuel =pseudoSnapshot.getValue(int.class);
                            database.getReference("utilisateurs").child(user.getUid()).child("argent").setValue(argentActuel+value);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });

                Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Le paiement a échoué", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BoutiqueActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}