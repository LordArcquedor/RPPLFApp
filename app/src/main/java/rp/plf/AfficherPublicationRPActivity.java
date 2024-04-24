package rp.plf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public class AfficherPublicationRPActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextView emailT, nomT;
    FirebaseUser user;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private TextView ContenuPost,titrePubli,auteurPost;
    public ImageView logoRP;
    public ImageView imagePubli;
    public String titre,titreModif;
    ProgressDialog progressDialog;
    Bundle bundle;
    Handler mainHandler = new Handler();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_publication_rpactivity);
        bundle = getIntent().getExtras();
        ContenuPost = findViewById(R.id.ContenuPostR);
        titrePubli = findViewById(R.id.titrePubliR);
        auteurPost = findViewById(R.id.auteurPostR);
        imagePubli = findViewById(R.id.imagePubli);



        auth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");


//        Thread thread3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
                afficherContenu();
//            }
//        });
//        thread3.start();
//        runOnUiThread(thread3);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.profil: {
                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.calendrier: {
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.social: {
                        Intent intent = new Intent(getApplicationContext(), SocialActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.messagerie: {
                        Intent intent = new Intent(getApplicationContext(), MessagerieActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.infoutiles: {
                        Intent intent = new Intent(getApplicationContext(), InfoUtileActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.collection: {
                        Intent intent = new Intent(getApplicationContext(), CollectionActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.boutique: {
                        Intent intent = new Intent(getApplicationContext(), BoutiqueActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.deco: {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                        break;

                    }
                    case R.id.parametre: {
                        Intent intent = new Intent(getApplicationContext(), ParametreActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }

                }
                return false;
            }
        });
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
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
                            DataSnapshot pseudoSnapshot = userSnapshot.child("pseudo");

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

    private void afficherContenu() {
        ArrayList<Publication> listePubli = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("publications");
//        Query query = usersRef.orderByChild("type").equalTo("RP");
        Query query = usersRef.orderByChild("date");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    Publication publication = userSnapshot.getValue(Publication.class);
                    DataSnapshot pseudoSnapshot = userSnapshot.child("titre");
                    DataSnapshot pseudoSnapshot2 = userSnapshot.child("contenu");
                    DataSnapshot pseudoSnapshot3 = userSnapshot.child("auteur");
                    DataSnapshot pseudoSnapshot4 = userSnapshot.child("image");
                    DataSnapshot pseudoSnapshot5 = userSnapshot.child("date");
                    DataSnapshot pseudoSnapshot6 = userSnapshot.child("type");

                    String contenu = pseudoSnapshot2.getValue(String.class);
                    String titre = pseudoSnapshot.getValue(String.class);
                    String auteur = pseudoSnapshot3.getValue(String.class);
                    String image = pseudoSnapshot4.getValue(String.class);
                    String date = pseudoSnapshot5.getValue(String.class);
                    String type = pseudoSnapshot6.getValue(String.class);

                    if(type.equals("RP")){
                        listePubli.add(new Publication(titre,auteur,contenu,image,"RP",date));
                    }


                }
                Collections.reverse(listePubli);


                ContenuPost.setText(listePubli.get(bundle.getInt("numeroPubli")).getContenu());
                titrePubli.setText(listePubli.get(bundle.getInt("numeroPubli")).getTitre());
                auteurPost.setText(listePubli.get(bundle.getInt("numeroPubli")).getAuteur());
                if (listePubli.get(bundle.getInt("numeroPubli")).getImage().isEmpty()) {
                    imagePubli.setVisibility(View.GONE);
                }else{
                    String url = listePubli.get(bundle.getInt("numeroPubli")).getImage();
                    Picasso.get()
                            .load(url)
                            .centerCrop()
                            .resize(350, 350)
                            .into(imagePubli);

//                    imagePubli.setImageBitmap(null);
//                    new FetchImage(url).start();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }



    class FetchImage extends Thread{
        String URL ;
        Bitmap bitmap;

        FetchImage(String URL){
            this.URL = URL;
        }
        @Override
        public void run(){
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(AfficherPublicationRPActivity.this);
//                    progressDialog.setMessage("Téléchargement de l'image");
//                    progressDialog.setCancelable(false);
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
                    imagePubli.setImageBitmap(bitmap);
                }
            });
        }
    }


}