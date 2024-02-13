package com.vedruna.alamofernandez;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Enumeración que representa el tipo de proveedor de inicio de sesión.
 * Puede ser BASIC o GOOGLE.
 *
 * @author Ricardo Alamo
 */
enum ProviderType {
    BASIC,
    GOOGLE
}

/**
 * Clase que gestiona el proceso de inicio de sesión en la aplicación.
 *
 * @author Ricardo Alamo
 */
public class login extends AppCompatActivity {

    final int GOOGLE_SING_IN = 100;

    private TextView mensaje;
    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mensaje = findViewById(R.id.ComprobacionLogin);

        user = findViewById(R.id.inputUser);
        password = findViewById(R.id.inputPassword);

    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Iniciar sesión".
     *
     * @param view La vista que ha sido clicada
     */
    public void onClick(View view) {
        String username = user.getText().toString();
        String contraseña = password.getText().toString();

        if (username.equals("admin") && contraseña.equals("admin")) {
            String usuario = username;
            Intent intent = new Intent(login.this, ContenedorFragmentActivity.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);

        } else {
            mensaje.setText("Usuario o contraseña incorrecta");
        }

    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Iniciar sesión con Google".
     *
     * @param view La vista que ha sido clicada
     */
    public void onClickGoogle(View view) {
        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("721668885112-0pdrfc4o5u2s3g05a8mem2rb9qj6pogl" +
                        ".apps.googleusercontent.com")
                .requestEmail()
                .build();

        GoogleSignInClient googleClient = GoogleSignIn.getClient(this, googleConf);
        googleClient.signOut();

        startActivityForResult(googleClient.getSignInIntent(), GOOGLE_SING_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SING_IN) {
            final Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            final GoogleSignInAccount account;
            try {
                account = task.getResult(ApiException.class);
                if (account != null) {
                    final AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String email = "";
                                if (account.getEmail() != null) {
                                    email = account.getEmail();
                                }
                                showHome(email, ProviderType.GOOGLE);
                            } else {
                                showAlert();
                            }
                        }
                    });
                }
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Método para mostrar una alerta en caso de error en el inicio de sesión.
     */
    private void showAlert() {
        Toast toast = Toast.makeText(this.getApplicationContext(),
                "Login error",
                Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Método para mostrar la pantalla principal de la aplicación después del inicio de sesión exitoso.
     *
     * @param email    El correo electrónico del usuario
     * @param provider El proveedor de inicio de sesión
     */
    private void showHome(String email, ProviderType provider) {

        Toast toast = Toast.makeText(this.getApplicationContext(),
                "Login correcto",
                Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent(this, ContenedorFragmentActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("provider", provider.name());
        startActivity(intent);
    }
}