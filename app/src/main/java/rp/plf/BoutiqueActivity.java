package rp.plf;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.billingclient.api.BillingClient;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalService;

import java.util.ArrayList;


public class BoutiqueActivity extends AppCompatActivity {
//    private static final String YOUR_CLIENT_ID = "ATVJU1AZD02o1MwRUK8-jGKFPCcXwwPq9Kc8cUhcM6LFJk9_vS8WarxslNKe33mY9dxEzZbwlu-NJHJV";
    private BillingClient billingClient;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT,argent;
    FirebaseUser user;
    FirebaseAuth auth;
    Button button13,button14,button5;
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
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy(){
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");



        button14 = findViewById(R.id.button14);
        button5 = findViewById(R.id.button123);
        button13 = findViewById(R.id.button13);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RechargerGemmeActivity.class);
                startActivity(intent);
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AcheterAvatarActivity.class);
                startActivity(intent);
            }
        });


        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AcheterBanniereActivity.class);
                startActivity(intent);
            }
        });


        argent = findViewById(R.id.argent);

//        button6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openPayPalPayment();
//            }
//        });
//
//
//        button13.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openPayPalPayment1000();
//
//            }
//        });
//
//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openPayPalPayment100();
//
//            }
//        });


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
            Intent intent2 = new Intent(getApplicationContext(), Login.class);
            startActivity(intent2);
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
                            DataSnapshot pseudoSnapshot2  = userSnapshot.child("argent");

                            View headerView = navigationView.getHeaderView(0);
                            nomT = headerView.findViewById(R.id.Your_name);
                            nomT.setText(pseudoSnapshot.getValue(String.class));
                            argent.setText("MON SOLDE : "+pseudoSnapshot2.getValue(String.class)+ " \uD83D\uDC8E");
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



    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PAYPAL_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                Toast.makeText(this, "Le paiement a été effectué avec succès", Toast.LENGTH_SHORT).show();
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference usersRef = database.getReference("utilisateurs");
//                Query query = usersRef.equalTo(user.getUid());
//                query.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                            DataSnapshot pseudoSnapshot  = userSnapshot.child("argent");
//                            int argentActuel =pseudoSnapshot.getValue(int.class);
//                            database.getReference("utilisateurs").child(user.getUid()).child("argent").setValue(argentActuel+value);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // En cas d'erreur lors de la récupération des données
//                    }
//                });
//
//                Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
//                startActivity(intent);
//                finish();
//
//            } else {
//                Toast.makeText(this, "Le paiement a échoué", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }
//    }
    public void showAlertDialog(String prix, String lien){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Achat de bannière");
        alert.setMessage("Vous allez acheter la bannière pour "+prix+" cristaux? Continuer ?");
        alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("utilisateurs");
                Query query = usersRef.orderByChild("uId").equalTo(user.getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> ban = new ArrayList<>();
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Utilisateur utilisateur = userSnapshot.getValue(Utilisateur.class);

                            ban.addAll(utilisateur.getMesBannieres());
                            String sousous = utilisateur.getArgent();
                            if( Integer.parseInt(sousous)<Integer.parseInt(prix)){
                                Toast.makeText(BoutiqueActivity.this, "Solde insuffisant", Toast.LENGTH_SHORT).show();
                            }else{
                                if(!ban.contains(lien)){
                                    ban.add(lien);
                                    database.getReference("utilisateurs").child(user.getUid()).child("mesBannieres").setValue(ban);
                                    database.getReference("utilisateurs").child(user.getUid()).child("argent").setValue(String.valueOf(Integer.parseInt(sousous)-Integer.parseInt(prix)));
                                    Toast.makeText(BoutiqueActivity.this, "Achat effectué avec succès ! La bannière est consultable sur votre profil", Toast.LENGTH_SHORT).show();
                                    break;
                                }else{
                                    Toast.makeText(BoutiqueActivity.this, "Vous posséder déjà la bannière", Toast.LENGTH_SHORT).show();
                                    break;
                                }


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
        alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BoutiqueActivity.this, "Achat annulé", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }
}

