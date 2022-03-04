package com.desarrollo1.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegistrar;


    private Spinner spinner;

    private GoogleApiClient apiClient;
    private static final int RC_SIGN_IN = 1001;

    private ProgressDialog progressDialog;
    private static final String TAG = "MainActivity";

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        spinner = (Spinner) findViewById(R.id.spinner);

        String[] respuesta = {"Persona", "Producto", "Inventario"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, respuesta);
        spinner.setAdapter(adapter);

        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);



            btnLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String userE = etUser.getText().toString();
                    String passE = etPassword.getText().toString();
                    //Ahora validamos por si uno de los campos esta vacío
                    if (TextUtils.isEmpty(userE)) {
                        //por si falta correo
                        Toast.makeText(LoginActivity.this, "Inserte correo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(passE)) {
                        //por si falta password
                        Toast.makeText(LoginActivity.this, "Inserte contraseña", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //Ahora usamos el Auth para que se logee una vez registrado
                    auth.signInWithEmailAndPassword(userE, passE).
                            //Le pasamos la clase registro
                                    addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "A ocurrido un error", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(LoginActivity.this, MapsActivity.class);
                                    startActivity(i);
                                }
                            });
                }
            });

            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(LoginActivity.this, Registro.class);
                    startActivity(i);
                }
            });
    }
    public void mostrar (View view){
        String seleccionado = spinner.getSelectedItem().toString();
        if (seleccionado.equals("Persona")) {
            Intent i = new Intent(LoginActivity.this, Personas.class);
            startActivity(i);
            // tv_respuesta.setText("Si eres inteligente ");
        } else if (seleccionado.equals("Producto")) {
            // tv_respuesta.setText("Si eres Experto ");
            Intent i = new Intent(LoginActivity.this, Producto.class);
            startActivity(i);

        } else if (seleccionado.equals("Inventario")) {
            //  tv_respuesta.setText("Si eres Sabio ");
            Intent i = new Intent(LoginActivity.this, Inventario.class);
            startActivity(i);
        }

        }

    }



