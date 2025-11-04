package ec.edu.espe.soap_conuni_clmov_gro3.arguello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.espe.soap_conuni_clmov_gro3.R;
import ec.edu.espe.soap_conuni_clmov_gro3.arguello.servicios.LoginService;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
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

        Log.d(TAG, "Intento de login iniciado");
        Log.d(TAG, "Usuario: " + username);

        if (username.isEmpty()) {
            usernameInput.setError("El usuario es requerido");
            usernameInput.requestFocus();
            Log.w(TAG, "Campo usuario vacío");
            return;
        }

        if (password.isEmpty()) {
            passwordInput.setError("La contraseña es requerida");
            passwordInput.requestFocus();
            Log.w(TAG, "Campo contraseña vacío");
            return;
        }

        showLoading(true);
        Log.d(TAG, "Iniciando hilo de autenticación...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "Llamando al servicio de login...");
                    final boolean loginSuccess = loginService.login(username, password);
                    Log.d(TAG, "Servicio respondió: " + loginSuccess);
                    
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoading(false);
                            handleLoginResult(loginSuccess, username);
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Error en el proceso de login", e);
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
        Log.d(TAG, "Manejando resultado de login: " + success);
        if (success) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.putString(KEY_USERNAME, username);
            editor.apply();

            Log.d(TAG, "Login exitoso, navegando a MainActivity");
            Toast.makeText(this, "Bienvenido, " + username + "!", Toast.LENGTH_SHORT).show();
            
            navigateToMainActivity();
        } else {
            Log.w(TAG, "Login fallido - credenciales incorrectas");
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
