package rp.plf;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextPassword2,pseudoInApp;
    Button buttonRegister;
    FirebaseAuth mAuth;
    TextView textViewRegister;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.emailInscription);
        pseudoInApp = findViewById(R.id.pseudoInApp);
        editTextPassword = findViewById(R.id.mdpInscription);
        editTextPassword2 = findViewById(R.id.mdpInscription2);
        buttonRegister = findViewById(R.id.buttonInscription);
        textViewRegister = findViewById(R.id.AlreadyAcc);
        
        
            textViewRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    finish();
                }
            });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, password2, pseudo;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                password2 = String.valueOf(editTextPassword2.getText());
                pseudo = String.valueOf(pseudoInApp.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this, "Merci de rentrer un email", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pseudo)){
                    Toast.makeText(Register.this, "Merci de rentrer votre pseudo pour l'application", Toast.LENGTH_SHORT).show();
                }
                if(pseudo.contains("*")||pseudo.contains("/")||pseudo.contains(".")){
                    Toast.makeText(Register.this, "Caractère illégal dans le pseudo", Toast.LENGTH_SHORT).show();
                }
                if(password.length()<6){
                    Toast.makeText(Register.this, "Le mot de passe doit contenir au minimum 6 caractères", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Merci de rentrer un mot de passe", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password2)){
                    Toast.makeText(Register.this, "Merci de confirmer le mot de passe", Toast.LENGTH_SHORT).show();
                }
                if(!password.equals(password2)){
                    Toast.makeText(Register.this, "Les deux mots de passes sont différents", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference usersRef = database.getReference("utilisateurs");

                                        ArrayList<String> bannieres = new ArrayList<>();
                                        ArrayList<String> avatars = new ArrayList<>();
                                        bannieres.add("https://i.goopics.net/tcuij2.png");

                                        avatars.add("https://i.postimg.cc/7YxbDF9R/un.png");
                                        avatars.add("https://i.postimg.cc/d0TV4wQh/trois.png");
                                        avatars.add("https://i.postimg.cc/XqWqrmbp/treize.png");
                                        avatars.add("https://i.postimg.cc/nhWL4Cx8/six.png");
                                        avatars.add("https://i.postimg.cc/6pq34CDL/sept.png");
                                        avatars.add("https://i.postimg.cc/yNJkt0jY/quinze.png");
                                        avatars.add("https://i.postimg.cc/50LjN1Dh/quatre.png");
                                        avatars.add("https://i.postimg.cc/nzD9jq9w/quatorze.png");
                                        avatars.add("https://i.postimg.cc/3NQ4gWgr/onze.png");
                                        avatars.add("https://i.postimg.cc/pdyFvwWv/neuf.png");
                                        avatars.add("https://i.postimg.cc/J7jBRcLs/huit.png");
                                        avatars.add("https://i.postimg.cc/8zZ6qpVw/douze.png");
                                        avatars.add("https://i.postimg.cc/Pqc85SJw/dix.png");
                                        avatars.add("https://i.postimg.cc/yYB3xg1v/deux.png");
                                        avatars.add("https://i.postimg.cc/kGNVw9cQ/cinq.png");

                                        String uId = mAuth.getCurrentUser().getUid();
                                        Utilisateur newUser = new Utilisateur(pseudo,email,"Challenger","","https://img1.freepng.fr/20180319/row/kisspng-computer-icons-google-account-user-profile-iconfin-png-icons-download-profile-5ab0301d8907a6.3404305715214960935613.jpg","https://i.goopics.net/tcuij2.png","",bannieres,avatars,uId,"offline","0");
                                        usersRef.child(newUser.getuId()).setValue(newUser);


                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                        Toast.makeText(Register.this, "Compte créé avec succès.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Register.this, "Echec de la création du compte.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }




            }
        });
    }
}