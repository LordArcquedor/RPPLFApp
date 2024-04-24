package rp.plf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public class CollectionAvatar extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    TextView textView16;
    ScrollView scrollView;
    ArrayList<String> lesAvatarsExistants ;
    LinearLayout linear3;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),CollectionActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_avatar);

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
        scrollView = findViewById(R.id.scrollView);
        linear3 = findViewById(R.id.linear3);

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
                lesAvatarsExistants = new ArrayList<>();
                lesAvatarsExistants.add("https://i.postimg.cc/7YxbDF9R/un.png");
                lesAvatarsExistants.add("https://i.postimg.cc/d0TV4wQh/trois.png");
                lesAvatarsExistants.add("https://i.postimg.cc/XqWqrmbp/treize.png");
                lesAvatarsExistants.add("https://i.postimg.cc/nhWL4Cx8/six.png");
                lesAvatarsExistants.add("https://i.postimg.cc/6pq34CDL/sept.png");
                lesAvatarsExistants.add("https://i.postimg.cc/yNJkt0jY/quinze.png");
                lesAvatarsExistants.add("https://i.postimg.cc/50LjN1Dh/quatre.png");
                lesAvatarsExistants.add("https://i.postimg.cc/nzD9jq9w/quatorze.png");
                lesAvatarsExistants.add("https://i.postimg.cc/3NQ4gWgr/onze.png");
                lesAvatarsExistants.add("https://i.postimg.cc/pdyFvwWv/neuf.png");
                lesAvatarsExistants.add("https://i.postimg.cc/J7jBRcLs/huit.png");
                lesAvatarsExistants.add("https://i.postimg.cc/8zZ6qpVw/douze.png");
                lesAvatarsExistants.add("https://i.postimg.cc/Pqc85SJw/dix.png");
                lesAvatarsExistants.add("https://i.postimg.cc/yYB3xg1v/deux.png");
                lesAvatarsExistants.add("https://i.postimg.cc/kGNVw9cQ/cinq.png");

                lesAvatarsExistants.add("https://i.postimg.cc/mDYy4ZzL/aaa.png");
                lesAvatarsExistants.add("https://i.postimg.cc/L663dpHS/bbb.png");
                lesAvatarsExistants.add("https://i.postimg.cc/J7Q5dnCK/ccc.png");
                lesAvatarsExistants.add("https://i.postimg.cc/fyMjR4mx/ddd.png");
                lesAvatarsExistants.add("https://i.postimg.cc/hPybc5jd/fff.png");
                lesAvatarsExistants.add("https://i.postimg.cc/DZkPR4zp/ggg.png");
                lesAvatarsExistants.add("https://i.postimg.cc/mkV3cdyK/hhh.png");
                lesAvatarsExistants.add("https://i.postimg.cc/L8JkX3Y0/iii.png");
                lesAvatarsExistants.add("https://i.postimg.cc/dVg2TZGQ/jjj.png");
                lesAvatarsExistants.add("https://i.postimg.cc/9fTGPXBd/kkk.png");
                lesAvatarsExistants.add("https://i.postimg.cc/C1sktvXd/lll.png");
                lesAvatarsExistants.add("https://i.postimg.cc/gJW3g9Mf/mmm.png");
                lesAvatarsExistants.add("https://i.postimg.cc/c1TfBbfb/nnn.png");
                lesAvatarsExistants.add("https://i.postimg.cc/NFNTGKgc/ooo.png");
                lesAvatarsExistants.add("https://i.postimg.cc/2yjhfQNG/ppp.png");
                lesAvatarsExistants.add("https://i.postimg.cc/7Y60snVY/qqq.png");




                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("utilisateurs");
                Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            DataSnapshot pseudoSnapshot  = userSnapshot.child("pseudo");

                            Utilisateur utilisateur = userSnapshot.getValue(Utilisateur.class);
                            ArrayList<String> lesAvatarDeLUser = new ArrayList<>();
                            lesAvatarDeLUser.addAll(utilisateur.getMesAvatars());

                            for(String elem : lesAvatarsExistants){
                                if(lesAvatarDeLUser.contains(elem)){
                                    linear3 = findViewById(R.id.linear3);
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    int desiredWidth = 450;
                                    int desiredHeight = 450;

                                    Picasso.get()
                                            .load(elem)
                                            .resize(desiredWidth, desiredHeight)
                                            .centerCrop()
                                            .into(imageView);
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(CollectionAvatar.this, "Possédé", Toast.LENGTH_SHORT).show();
                                        }
                                    });
//
                                    linear3.addView(imageView);
                                }else{
                                    linear3 = findViewById(R.id.linear3);
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    int desiredWidth = 450;
                                    int desiredHeight = 450;
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
                                            Toast.makeText(CollectionAvatar.this, "Non possédée", Toast.LENGTH_SHORT).show();
                                        }
                                    });
//
                                    linear3.addView(imageView);
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
//        runOnUiThread(thread);



    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}