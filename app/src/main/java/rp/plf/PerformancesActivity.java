package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PerformancesActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    Button defier;
    LinearLayout linear;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performances);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        defier = findViewById(R.id.defier);
        linear = findViewById(R.id.linear);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");



//        recupererPerformances();


        defier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LesChampionsActivity.class);
                startActivity(intent);
                finish();
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
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference usersRef = database.getReference("Resultats");
//        Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
                            usersRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        DataSnapshot j1  = userSnapshot.child("Joueur1");
                                        DataSnapshot j2  = userSnapshot.child("Joueur2");
                                        DataSnapshot vainqueur  = userSnapshot.child("Vainqueur");
                                        DataSnapshot date = userSnapshot.child("Date");

                                        String joueur1 = j1.getValue(String.class);
                                        String joueur2 = j2.getValue(String.class);
                                        String SVainqueur = vainqueur.getValue(String.class);
                                        String sDate = date.getValue(String.class);


                                        if(joueur1.equals(nomT.getText())&&SVainqueur.equals(nomT.getText())){
                                            TextView textView = new TextView(getApplicationContext());
                                            textView.setText("Victoire contre "+joueur2+ " le "+sDate);
                                            textView.setTextColor(Color.GREEN);
                                            textView.setTextSize(15);
                                            linear.addView(textView);
                                        }else if(joueur2.equals(nomT.getText())&&SVainqueur.equals(nomT.getText())){
                                            TextView textView = new TextView(getApplicationContext());
                                            textView.setText("Victoire contre "+joueur1+ " le "+sDate);
                                            textView.setTextColor(Color.GREEN);
                                            textView.setTextSize(15);
                                            linear.addView(textView);
                                        }else if(joueur1.equals(nomT.getText())&&!SVainqueur.equals(nomT.getText())){
                                            TextView textView = new TextView(getApplicationContext());
                                            textView.setText("Défaite contre "+joueur2+ " le "+sDate);
                                            textView.setTextColor(Color.RED);
                                            textView.setTextSize(15);
                                            linear.addView(textView);
                                        }else if (joueur2.equals(nomT.getText())&&!SVainqueur.equals(nomT.getText())){
                                            TextView textView = new TextView(getApplicationContext());
                                            textView.setText("Défaite contre "+joueur1+ " le "+sDate);
                                            textView.setTextColor(Color.RED);
                                            textView.setTextSize(15);
                                            linear.addView(textView);
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
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });
            }


    private void recupererPerformances() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference usersRef = database.getReference("Resultats");
////        Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
//        usersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    DataSnapshot j1  = userSnapshot.child("Joueur1");
//                    DataSnapshot j2  = userSnapshot.child("Joueur2");
//                    DataSnapshot vainqueur  = userSnapshot.child("Vainqueur");
//                    DataSnapshot date = userSnapshot.child("Date");
//
//                    String joueur1 = j1.getValue(String.class);
//                    String joueur2 = j2.getValue(String.class);
//                    String SVainqueur = vainqueur.getValue(String.class);
//                    String sDate = date.getValue(String.class);
//
//
//                    if(joueur1.equals(nomT.getText())&&SVainqueur.equals(nomT.getText())){
//                        TextView textView = new TextView(getApplicationContext());
//                        textView.setText("Victoire contre "+joueur2+ " le "+sDate);
//                        textView.setTextColor(Color.GREEN);
//                        textView.setTextSize(15);
//                        linear.addView(textView);
//                    }else if(joueur2.equals(nomT.getText())&&SVainqueur.equals(nomT.getText())){
//                        TextView textView = new TextView(getApplicationContext());
//                        textView.setText("Victoire contre "+joueur1+ " le "+sDate);
//                        textView.setTextColor(Color.GREEN);
//                        textView.setTextSize(15);
//                        linear.addView(textView);
//                    }else if(joueur1.equals(nomT.getText())&&!SVainqueur.equals(nomT.getText())){
//                        TextView textView = new TextView(getApplicationContext());
//                        textView.setText("Défaite contre "+joueur2+ " le "+sDate);
//                        textView.setTextColor(Color.RED);
//                        textView.setTextSize(15);
//                        linear.addView(textView);
//                    }else if (joueur2.equals(nomT.getText())&&!SVainqueur.equals(nomT.getText())){
//                        TextView textView = new TextView(getApplicationContext());
//                        textView.setText("Défaite contre "+joueur1+ " le "+sDate);
//                        textView.setTextColor(Color.RED);
//                        textView.setTextSize(15);
//                        linear.addView(textView);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // En cas d'erreur lors de la récupération des données
//            }
//        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}