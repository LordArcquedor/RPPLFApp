package rp.plf;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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


public class CalendarActivity extends AppCompatActivity {
    CustomCalendarView customCalendarView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        customCalendarView=(CustomCalendarView)findViewById(R.id.customCalendarView);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
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
                    case R.id.profil:
                    {
                        Intent intent = new Intent(getApplicationContext(),ProfilActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.calendrier:
                    {
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
                        Toast.makeText(CalendarActivity.this, "deco selected", Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}