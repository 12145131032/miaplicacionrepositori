package com.desarrollo1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ListadeProductos extends AppCompatActivity {
    private static final String TAGLOG = "firebase-db";

    private TextView lblCielo;
    private TextView lblTemperatura;
    private TextView lblHumedad;

    private Button btnEliminarListener;

    // private DatabaseReference dbCielo;
    private DatabaseReference dbPrediccion;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listade_productos);
        lblCielo = (TextView)findViewById(R.id.lblCielo);
        lblTemperatura = (TextView)findViewById(R.id.lblTemperatura);
        lblHumedad = (TextView)findViewById(R.id.lblHumedad);
         btnEliminarListener = (Button)findViewById(R.id.btnEliminarListener);

        /*
        dbCielo =
            FirebaseDatabase.getInstance().getReference()
                .child("prediccion-hoy")
                    .child("cielo");

        dbCielo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valor = dataSnapshot.getValue().toString();
                lblCielo.setText(valor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        });
        */

        dbPrediccion =
                FirebaseDatabase.getInstance().getReference()
                        .child("Producto");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Prediccion pred = dataSnapshot.getValue(Prediccion.class);
                lblCielo.setText(pred.getCielo());

                lblTemperatura.setText(pred.getTemperatura() + "ºC");
                lblHumedad.setText(pred.getHumedad() + "%");

                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        dbPrediccion.addValueEventListener(eventListener);

        btnEliminarListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbPrediccion.removeEventListener(eventListener);
            }
        });


        DatabaseReference dbDiasSemana =
                FirebaseDatabase.getInstance().getReference()
                        .child("dias-semana");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAGLOG, "onChildAdded: {" + dataSnapshot.getKey() + ": " + dataSnapshot.getValue() + "}");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAGLOG, "onChildChanged: {" + dataSnapshot.getKey() + ": " + dataSnapshot.getValue() + "}");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAGLOG, "onChildRemoved: {" + dataSnapshot.getKey() + ": " + dataSnapshot.getValue() + "}");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAGLOG, "onChildMoved: " + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };
        Query diasSemanaPorValor =
                FirebaseDatabase.getInstance().getReference()
                        .child("dias-semana")
                        .orderByValue();

        dbDiasSemana.addChildEventListener(childEventListener);
        diasSemanaPorValor .addChildEventListener(childEventListener);


    }
}