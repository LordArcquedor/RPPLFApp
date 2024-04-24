package rp.plf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;


public class AfficherProfilJoueurActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    Bundle bundle;
    TextView textView35,role,infoP;
    ImageView banniere,imageView2;
    LinearLayout Linear;
    private ImageView imageView;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),SocialActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_profil_joueur);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView35 = findViewById(R.id.textView35);
        banniere = findViewById(R.id.banniere);
        imageView2 = findViewById(R.id.imageView2);
        role = findViewById(R.id.role);
        infoP = findViewById(R.id.infoP);
        Linear = findViewById(R.id.linear3);

        bundle = getIntent().getExtras();
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();

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
                    case R.id.parametre:
                    {
                        Intent intent = new Intent(getApplicationContext(),ParametreActivity.class);
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
                        Intent intent = new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        textView35.setText(bundle.getString("PSEUDO"));
        Thread thread1= new Thread(new Runnable() {
            @Override
            public void run() {
                //Affichage du pseudo
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("utilisateurs");
                Query query = usersRef.orderByChild("pseudo").equalTo(textView35.getText().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Utilisateur utilisateur = userSnapshot.getValue(Utilisateur.class);
                            role.setText(utilisateur.getRole());
                            infoP.setText(utilisateur.getInfoD());
                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            int screenWidth = displayMetrics.widthPixels;

                            Picasso.get()
                                    .load(utilisateur.getBanniere())
                                    .centerCrop()
                                    .resize(screenWidth-15, 240)
                                    .into(banniere);
                            Picasso.get()
                                    .load(utilisateur.getPhoto())
                                    .centerCrop()
                                    .resize(180, 180)
                                    .into(imageView2);


                            String badgesRecup = utilisateur.getBadge();
                            if(badgesRecup.contains("3")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp= BitmapFactory.decodeResource(getResources(),R.drawable.acier);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);

                            }
                            if(badgesRecup.contains("1")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.eaub);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("2")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.feu);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("4")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.drake);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("5")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.elec);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("6")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.bug);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("7")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.leaf);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("8")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.psy);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("9")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.solb);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("a")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.dark);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("b")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.combat);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("c")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.feeb);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("d")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.ice);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("e")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.normal);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("f")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.poison);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("g")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.roche);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("h")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.ghost);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
                            }
                            if(badgesRecup.contains("i")){
                                Bitmap bmp;
                                int width=130;
                                int height=130;
                                bmp=BitmapFactory.decodeResource(getResources(),R.drawable.fly);
                                bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                                imageView = new ImageView(getApplicationContext());
                                imageView.setImageBitmap(bmp);
                                Linear.addView(imageView);
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
        runOnUiThread(thread1);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}