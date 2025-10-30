package ec.edu.espe.soap_conuni_clmov_gro3.arguello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.espe.soap_conuni_clmov_gro3.R;
import ec.edu.espe.soap_conuni_clmov_gro3.arguello.servicios.LoginService;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private ProgressBar progressBar;
    private LoginService loginService;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        // Verificar si el usuario ya está logueado
        if (isUserLoggedIn()) {
            navigateToMainActivity();
            return;
        }
        
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progress_bar);

        // Inicializar servicio de login
        loginService = new LoginService();

        // Configurar el botón de login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Validar campos
        if (username.isEmpty()) {
            usernameInput.setError("El usuario es requerido");
            usernameInput.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordInput.setError("La contraseña es requerida");
            passwordInput.requestFocus();
            return;
        }

        // Mostrar progress bar y deshabilitar botón
        showLoading(true);

        // Realizar login en un hilo separado
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final boolean loginSuccess = loginService.login(username, password);
                    
                    // Volver al hilo principal para actualizar UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoading(false);
                            handleLoginResult(loginSuccess, username);
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoading(false);
                            Toast.makeText(LoginActivity.this, 
                                "Error al conectar con el servidor: " + e.getMessage(), 
                                Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void handleLoginResult(boolean success, String username) {
        if (success) {
            // Guardar estado de login
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.putString(KEY_USERNAME, username);
            editor.apply();

            Toast.makeText(this, "Bienvenido, " + username + "!", Toast.LENGTH_SHORT).show();
            
            // Navegar a MainActivity
            navigateToMainActivity();
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            passwordInput.setText("");
            passwordInput.requestFocus();
        }
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        loginButton.setEnabled(!show);
        usernameInput.setEnabled(!show);
        passwordInput.setEnabled(!show);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Deshabilitar el botón atrás en la pantalla de login
        // Para que el usuario no pueda volver sin autenticarse
        moveTaskToBack(true);
    }
}
