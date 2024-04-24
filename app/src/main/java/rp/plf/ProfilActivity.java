package rp.plf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.io.IOException;
import java.io.InputStream;

public class ProfilActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView editTextTextMultiLineInfoPerso;
    NavigationView navigationView;
    ImageView img,banniere;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser user;
    Button buttonCreerPost,buttonModifierProfil,buttonPerformance,buttonGestionArene;
    TextView emailT,nomT,solde;
    private ImageView imageView;
    LinearLayout Linear;
    String rolePersonne="";
    Button admin;

    ActionBarDrawerToggle drawerToggle;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        img = findViewById(R.id.imageViewProfil);
        banniere = findViewById(R.id.banniere);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        buttonCreerPost = findViewById(R.id.buttonCreerPost);
        editTextTextMultiLineInfoPerso = findViewById(R.id.editTextTextMultiLineInfoPerso);
        buttonModifierProfil = findViewById(R.id.buttonModifProfil);
        Linear = findViewById(R.id.linear3);
        buttonPerformance = findViewById(R.id.buttonPerformance);
        buttonGestionArene = findViewById(R.id.buttonGestionArene);

        auth = FirebaseAuth.getInstance();
        solde = findViewById(R.id.solde);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");
        admin = findViewById(R.id.admin);


        buttonCreerPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreerPublicationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonModifierProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ModifierProfilActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                afficherImageProfilJoueur();
                afficherBanniereJoueur();
            }
        });
        runOnUiThread(thread);

        getMonRole(new RoleCallback() {
            @Override
            public void onRoleReceived(String role) {
                // Mettre à jour le TextView avec le rôle ici
                if (role.equals("Challenger")) {
                    buttonPerformance.setVisibility(View.VISIBLE);
                }else if(role.equals("Champion")){
                    buttonGestionArene.setVisibility(View.VISIBLE);
                }else if(role.equals("Admin")){
                    admin.setVisibility(View.VISIBLE);
                }
            }
        });


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ModoActivity.class);
                startActivity(intent);
                finish();
            }
        });



        buttonPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PerformancesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonGestionArene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GestionAreneActivity.class);
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
                        break;
                    }
                    case R.id.calendrier:
                    {
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
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

//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
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
                            solde.setText(pseudoSnapshot2.getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // En cas d'erreur lors de la récupération des données
                    }
                });
//            }
//        });
//        thread2.start();
////        runOnUiThread(thread2);
//


    }

    private void getMonRole(RoleCallback callback) {
        user = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");
        Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot role  = userSnapshot.child("role");
                    String sRole = role.getValue(String.class);
                    callback.onRoleReceived(sRole);


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });
    }


    private void afficherBanniereJoueur(){
        user = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");
        Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot banniereSnapshot= null;
                String url=null;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    banniereSnapshot  = userSnapshot.child("banniere");
                }
                url = banniereSnapshot.getValue(String.class);
                banniere.setImageBitmap(null);
                new FetchImage(url,banniere).start();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });


    }


    private void afficherImageProfilJoueur() {
        Linear.removeAllViews();
        user = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("utilisateurs");
        Query query = usersRef.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot photoSnapshot= null;
                DataSnapshot descriSnapshot=null;
                String url="";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Utilisateur utilisateur = userSnapshot.getValue(Utilisateur.class);
                    photoSnapshot  = userSnapshot.child("photo");
                    descriSnapshot = userSnapshot.child("infoD");

                    String badgesRecup = utilisateur.getBadge();


                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int screenWidth = displayMetrics.widthPixels;


                    if(badgesRecup.contains("a")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.un);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);

                    }
                    if(badgesRecup.contains("b")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.deux);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("c")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.douze);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("d")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.quinze);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("e")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.cinq);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("f")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.quatorze);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("g")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.feu);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("h")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.treize);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("i")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.solb);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("j")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.dark);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("k")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.combat);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("l")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.dix);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("m")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.ice);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("n")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.normal);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("o")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.poison);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("p")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.roche);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("q")){
                        Bitmap bmp;
                        int width=130;
                        int height=130;
                        bmp=BitmapFactory.decodeResource(getResources(),R.drawable.ghost);
                        bmp=Bitmap.createScaledBitmap(bmp,width,height,true);
                        imageView = new ImageView(getApplicationContext());
                        imageView.setImageBitmap(bmp);
                        Linear.addView(imageView);
                    }
                    if(badgesRecup.contains("r")){
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
                url = photoSnapshot.getValue(String.class);
                img.setImageBitmap(null);
                new FetchImage(url,img).start();
                editTextTextMultiLineInfoPerso.setText(descriSnapshot.getValue(String.class));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // En cas d'erreur lors de la récupération des données
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class FetchImage extends Thread{
        String URL ;
        Bitmap bitmap;
        ImageView imageView;

        FetchImage(String URL, ImageView imageView){
            this.URL = URL;
            this.imageView = imageView;
        }
        @Override
        public void run(){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(ProfilActivity.this);
//                    progressDialog.setMessage("Téléchargement de l'image");
//                    progressDialog.setCancelable(true);
//                    progressDialog.show();
                }
            });

            InputStream inputStream = null;
            try {
                inputStream = new java.net.URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }
    // Interface pour le callback
    interface RoleCallback {
        void onRoleReceived(String role);
    }
}