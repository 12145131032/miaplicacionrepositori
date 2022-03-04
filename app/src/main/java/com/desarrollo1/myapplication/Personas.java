package com.desarrollo1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Personas extends AppCompatActivity {
    private EditText mEditTextMensaje;
    private Button mBtnCreadorDatos;
private Spinner spinner1;
    private EditText meditTextCedula;
private EditText meditTextProvincia;


    private Button mbtnRegresar;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas);


        spinner1 = (Spinner) findViewById(R.id.spinner1);

        String[] respuesta = {"Ecuador", "Venezuela", "Colombia"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, respuesta);
        spinner1.setAdapter(adapter);





        mEditTextMensaje = (EditText) findViewById(R.id.editTextMensaje);
        mBtnCreadorDatos = (Button) findViewById(R.id.btnCrearDatos);

        meditTextCedula = (EditText) findViewById(R.id.editTextCedula);
        meditTextProvincia = (EditText) findViewById(R.id.editTextProvincia);

        mbtnRegresar = (Button) findViewById(R.id.btnRegresar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mBtnCreadorDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String mensaje = mEditTextMensaje.getText().toString();
                //  mDatabase.child("persona").setValue(mensaje);
                String cedula = meditTextCedula.getText().toString();
                mDatabase.child("persona").setValue(cedula);
                Map<String, Object> personaMap = new HashMap<>();
                personaMap.put("cedula", "" + mEditTextMensaje.getText().toString());
                personaMap.put("Nombre", "" + meditTextCedula.getText().toString());
                personaMap.put("provincia", "" + meditTextProvincia.getText().toString());
                mDatabase.child("Persona").setValue(personaMap);
                mDatabase.child("Usuario").child("Administrador").push().setValue(personaMap);

            }
        });




      //   mbtnRegresar.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
   //     regresar();
      //  }
    //    });
        }
public void regresar(){
        Intent i=new Intent (this,LoginActivity.class);
        startActivity(i);

        }
    public void mostrar (View view){
        String seleccionado = spinner1.getSelectedItem().toString();
        if (seleccionado.equals("Ecuador ")) {

            // tv_respuesta.setText("Si eres inteligente ");
        } else if (seleccionado.equals("Venezuela")) {
            // tv_respuesta.setText("Si eres Experto ");


        } else if (seleccionado.equals("Colombia")) {
            //  tv_respuesta.setText("Si eres Sabio ");

        }

    }

}
