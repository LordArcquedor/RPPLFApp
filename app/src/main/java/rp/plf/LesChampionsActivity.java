package rp.plf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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

import java.util.ArrayList;

public class LesChampionsActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView emailT,nomT;
    FirebaseUser user;
    FirebaseAuth auth;
    LinearLayout linear;
    private ImageView imageView;
    public ArrayList<Utilisateur> lesChampions;
    public int val;
    public Bitmap bmp;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),InfoUtileActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_champions);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        linear = findViewById(R.id.linear);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.logotranspa);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().setTitle("");


//        chargerChampions();

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
                            lesChampions = new ArrayList<>();
                            user = auth.getCurrentUser();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference usersRef = database.getReference("utilisateurs");
                            Query query = usersRef.orderByChild("role").equalTo("Champion");

                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    linear.removeAllViews();
                                    val=0;
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                                        Utilisateur utilisateur = userSnapshot.getValue(Utilisateur.class);
                                        DataSnapshot pseudoS = userSnapshot.child("pseudo");
                                        DataSnapshot photoD = userSnapshot.child("photo");
                                        DataSnapshot argent = userSnapshot.child("argent");
                                        DataSnapshot badge = userSnapshot.child("badge");
                                        DataSnapshot email = userSnapshot.child("email");
                                        DataSnapshot info = userSnapshot.child("infoD");
                                        DataSnapshot statu = userSnapshot.child("status");
                                        DataSnapshot uid = userSnapshot.child("uId");
                                        DataSnapshot role = userSnapshot.child("role");
                                        DataSnapshot bann = userSnapshot.child("banniere");

                                        Utilisateur utilisateur =new Utilisateur();
                                        utilisateur.setPhoto(photoD.getValue(String.class));
                                        utilisateur.setArgent((argent.getValue(String.class)));
                                        utilisateur.setBadge(badge.getValue(String.class));
                                        utilisateur.setEmail(email.getValue(String.class));
                                        utilisateur.setInfoD(info.getValue(String.class));
                                        utilisateur.setStatus(statu.getValue(String.class));
                                        utilisateur.setuId(uid.getValue(String.class));
                                        utilisateur.setRole(role.getValue(String.class));
                                        utilisateur.setBanniere(bann.getValue(String.class));
                                        utilisateur.setPseudo(pseudoS.getValue(String.class));
                                        lesChampions.add(utilisateur);
                                        if(!utilisateur.getPseudo().equals(nomT.getText())){
                                            FrameLayout frameLayout = new FrameLayout(getApplicationContext());
                                            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                                    FrameLayout.LayoutParams.MATCH_PARENT));

                                            DisplayMetrics displayMetrics = new DisplayMetrics();
                                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                                            int screenWidth = displayMetrics.widthPixels;
                                            int width=screenWidth;
                                            int height=130;
                                            switch (val){
                                                case 0:
                                                    bmp= BitmapFactory.decodeResource(getResources(),R.drawable.az);
                                                    break;
                                                case 1 :
                                                    bmp= BitmapFactory.decodeResource(getResources(),R.drawable.er);
                                                    break;
                                                case 2:
                                                    bmp= BitmapFactory.decodeResource(getResources(),R.drawable.rt);
                                                    break;
                                                case 3 :
                                                    bmp= BitmapFactory.decodeResource(getResources(),R.drawable.ty);
                                                    break;
                                                case 4:
                                                    bmp= BitmapFactory.decodeResource(getResources(),R.drawable.yu);
                                                    break;
                                                case 5 :
                                                    bmp= BitmapFactory.decodeResource(getResources(),R.drawable.ui);
                                                    break;
                                                case 6:
                                                    bmp= BitmapFactory.decodeResource(getResources(),R.drawable.io);
                                                    break;

                                                case 7 :
                                                    bmp= BitmapFactory.decodeResource(getResources(),R.drawable.qs);
                                                    break;
//                            case 8:
//                                bmp= BitmapFactory.decodeResource(getResources(),R.drawable.);
//                                break;
//                            case 9 :
//                                bmp= BitmapFactory.decodeResource(getResources(),R.drawable.psy);
//                                break;
//                            case 10 :
//                                bmp= BitmapFactory.decodeResource(getResources(),R.drawable.poison);
//                                break;
                                            }
//                        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.bannh);
                                            bmp=Bitmap.createScaledBitmap(bmp,width,height,true);

                                            imageView = new ImageView(getApplicationContext());
                                            imageView.setImageBitmap(bmp);
                                            imageView.setPadding(0,5,0,5);

                                            float cornerRadius = 20f; // Rayon des coins arrondis en pixels

// Obtenir l'image drawable de l'ImageView
                                            Drawable originalDrawable = imageView.getDrawable();

// Créer un drawable avec des coins arrondis
                                            Drawable roundedDrawable = getRoundedDrawable(originalDrawable, cornerRadius);

// Appliquer le drawable avec des coins arrondis à l'ImageView
                                            imageView.setImageDrawable(roundedDrawable);

                                            TextView textView = new TextView(getApplicationContext());
                                            textView.setText(utilisateur.getPseudo());
                                            textView.setTextSize(20);
                                            textView.setTextColor(Color.WHITE);
                                            textView.setGravity(Gravity.CENTER);

                                            FrameLayout.LayoutParams imageViewLayoutParams = new FrameLayout.LayoutParams(
                                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                                    FrameLayout.LayoutParams.MATCH_PARENT);
                                            frameLayout.addView(imageView, imageViewLayoutParams);

                                            FrameLayout.LayoutParams textViewLayoutParams = new FrameLayout.LayoutParams(
                                                    FrameLayout.LayoutParams.WRAP_CONTENT,
                                                    FrameLayout.LayoutParams.WRAP_CONTENT);
                                            textViewLayoutParams.gravity = Gravity.CENTER;
                                            frameLayout.addView(textView, textViewLayoutParams);






                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("nomChamp",utilisateur.getPseudo());
                                                    Intent intent = new Intent(getApplicationContext(),AfficherProfilChampionActivity.class);
                                                    intent.putExtras(bundle);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });

                                            linear.addView(frameLayout);


//                        linear.addView(imageView);

                                        }

                                        val+=1;
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
        });
        thread.start();
//        runOnUiThread(thread);



    }
    private Drawable getRoundedDrawable(Drawable drawable, float cornerRadius) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap roundedBitmap = getRoundedBitmap(bitmap, cornerRadius);
        return new BitmapDrawable(getResources(), roundedBitmap);
    }

    private Bitmap getRoundedBitmap(Bitmap bitmap, float cornerRadius) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = Color.WHITE;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}