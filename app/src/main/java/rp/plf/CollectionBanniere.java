package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CollectionBanniere extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    TextView banns;
    ArrayList<String> lesBannieresExistantes ;
    LinearLayout linear;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),CollectionActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_banniere);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");
        auth = FirebaseAuth.getInstance();
        banns = findViewById(R.id.banns);
        linear = findViewById(R.id.linear);

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
                lesBannieresExistantes = new ArrayList<>();
                lesBannieresExistantes.add("https://i.goopics.net/tcuij2.png");
                lesBannieresExistantes.add("https://i.postimg.cc/63nDDLRn/io.png");
                lesBannieresExistantes.add("https://i.postimg.cc/K8jCZyBZ/qs.png");
                lesBannieresExistantes.add("https://i.postimg.cc/J73fxxRW/er.png");
                lesBannieresExistantes.add("https://i.postimg.cc/qRHn9g56/bannvingt.png");
                lesBannieresExistantes.add("https://i.postimg.cc/hvpdzsPV/banvingtcinq.png");
                lesBannieresExistantes.add("https://i.postimg.cc/FKL3W0DQ/banvingtdeux.png");
                lesBannieresExistantes.add("https://i.postimg.cc/B65DssrY/banvingtdix.png");
                lesBannieresExistantes.add("https://i.postimg.cc/tJX64wyX/banvingtneuf.png");
                lesBannieresExistantes.add("https://i.postimg.cc/PrVDdS7j/banvingtquatre.png");
                lesBannieresExistantes.add("https://i.postimg.cc/FFZcBrg1/banvingtsept.png");
                lesBannieresExistantes.add("https://i.postimg.cc/7YZ7vFm9/banvingtsix.png");
                lesBannieresExistantes.add("https://i.postimg.cc/YC4W01cm/banvingttrois.png");
                lesBannieresExistantes.add("https://i.postimg.cc/GmZsyHrg/banvingtun.png");
                lesBannieresExistantes.add("https://i.postimg.cc/5ymrQ3by/ban1.png");
                lesBannieresExistantes.add("https://i.postimg.cc/ZKcH8n39/ban0.png");
                lesBannieresExistantes.add("https://i.postimg.cc/Fs9852VL/ban2.png");
                lesBannieresExistantes.add("https://i.postimg.cc/htvYhTw4/ban3.png");
                lesBannieresExistantes.add("https://i.postimg.cc/bN3Mz3mF/ban4.png");
                lesBannieresExistantes.add("https://i.postimg.cc/Z5QMN0t8/ban5.png");
                lesBannieresExistantes.add("https://i.postimg.cc/nVKgWHBx/ban6.png");
                lesBannieresExistantes.add("https://i.postimg.cc/Y07VSVcT/ban7.png");
                lesBannieresExistantes.add("https://i.postimg.cc/9fvK60Lm/ban8.jpg");
                lesBannieresExistantes.add("https://i.postimg.cc/8CrnmDNq/banc4.png");
                lesBannieresExistantes.add("https://i.postimg.cc/nzbSd5LM/banchall.png");
                lesBannieresExistantes.add("https://i.postimg.cc/fyM2s9mN/banchamp.png");
                lesBannieresExistantes.add("https://i.postimg.cc/WbWWRt4B/bandonateur.png");
                lesBannieresExistantes.add("https://i.postimg.cc/V6849C7C/banmaitre.png");
                lesBannieresExistantes.add("https://i.postimg.cc/kgLT4ZKw/banmaitre2.png");
                lesBannieresExistantes.add("https://i.postimg.cc/d0d4TB9F/banromain.png");
                lesBannieresExistantes.add("https://i.postimg.cc/hPR5YbbK/banchamp.png");
                lesBannieresExistantes.add("https://i.postimg.cc/9MJXpH7t/bandonateur.png");
                lesBannieresExistantes.add("https://i.postimg.cc/NFd4GjWf/340112070-1292334568049417-8625815395395331051-n.png");
                lesBannieresExistantes.add("https://i.postimg.cc/FHdf1H2C/342878521-196728713219563-7546272949543319174-n.png");




                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("utilisateurs");
                Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        linear.removeAllViews();
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            DataSnapshot pseudoSnapshot  = userSnapshot.child("pseudo");

                            Utilisateur utilisateur = userSnapshot.getValue(Utilisateur.class);
                            ArrayList<String> lesBannieresDeLUser = new ArrayList<>();
                            lesBannieresDeLUser.addAll(utilisateur.getMesBannieres());

                            for(String elem : lesBannieresDeLUser){
                            }
                            for(String elem : lesBannieresExistantes){
                                if(lesBannieresDeLUser.contains(elem)){
                                    linear = findViewById(R.id.linear);
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    int desiredWidth = 1100;
                                    int desiredHeight = 250;

                                    Picasso.get()
                                            .load(elem)
                                            .resize(desiredWidth, desiredHeight)
                                            .centerCrop()
                                            .into(imageView);
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(CollectionBanniere.this, "Possédée", Toast.LENGTH_SHORT).show();
                                        }
                                    });
//
                                    linear.addView(imageView);
                                }else{
                                    linear = findViewById(R.id.linear);
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    int desiredWidth = 1100;
                                    int desiredHeight = 250;
// Créez une matrice de couleur pour convertir en niveaux de gris
                                    ColorMatrix colorMatrix = new ColorMatrix();
                                    colorMatrix.setSaturation(0); // Mettez la saturation à 0 pour la conversion en noir et blanc

// Créez un ColorFilter avec la matrice de couleur
                                    ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
                                    imageView.setColorFilter(colorFilter);

                                    Picasso.get()
                                            .load(elem)
                                            .resize(desiredWidth, desiredHeight)
                                            .centerCrop()
                                            .into(imageView);
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(CollectionBanniere.this, "Non possédée", Toast.LENGTH_SHORT).show();
                                        }
                                    });
//
                                    linear.addView(imageView);
                                }
                            }

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