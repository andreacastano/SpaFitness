package co.yolima.spafitness;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    EditText eUser, ePassword;
    Button bLogin;
    TextView tRegister;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String name="", password="", email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            Intent intent = new Intent(LogginActivity.this, MainActivity.class);
            intent.putExtra("nombre","name");
            intent.putExtra("email", "mail");
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_loggin);

        eUser = (EditText) findViewById(R.id.eUser);
        ePassword = (EditText) findViewById(R.id.ePassword);
        bLogin = (Button) findViewById(R.id.bEntrar);
        tRegister = (TextView) findViewById(R.id.tRegistro);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button_facebook);

        // Facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
                // ...
            }
        };


        tRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LogginActivity.this, RegistroActivity.class);
                startActivityForResult(myIntent,1234);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre, contrasena;
                nombre = eUser.getText().toString();
                contrasena = ePassword.getText().toString();
                if ((nombre.equals("") || contrasena.equals(""))){
                    Toast.makeText(getApplicationContext(), "Campos en blanco", Toast.LENGTH_SHORT).show();
                } else {
                    if ((name.equals(nombre) && password.equals(contrasena))){
                        Toast.makeText(getApplicationContext(),"registrado con email: "+email+" y password: "+password,Toast.LENGTH_SHORT).show();
                        mAuth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(LogginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Si falla el registro
                                        if(!task.isSuccessful()){
                                            Toast.makeText(LogginActivity.this, "Registro fallido...!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Intent intent = new Intent(LogginActivity.this, MainActivity.class);
                                            intent.putExtra("nombre",name);
                                            intent.putExtra("email", email);
                                            startActivity(intent);
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Fail, try again..!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    // Loguear el usuario en Firebase
    private void loguearFirebase(String correo, String pass) {

    }

    // Acceso fb - firebase
    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            String name,email;
                            name = mAuth.getCurrentUser().getDisplayName();
                            email = mAuth.getCurrentUser().getEmail();
                            Intent intent = new Intent(LogginActivity.this, MainActivity.class);
                            intent.putExtra("nombre", name);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_CANCELED)
            Toast.makeText(this, "Usuario No registrado", Toast.LENGTH_SHORT).show();
        else{
            if (requestCode == 1234 && resultCode == RESULT_OK){
                name = data.getExtras().getString("name");
                password = data.getExtras().getString("password");
                email = data.getExtras().getString("mail");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}