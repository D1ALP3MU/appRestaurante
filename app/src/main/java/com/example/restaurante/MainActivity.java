package com.example.restaurante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Instanciar Firebase
    EditText ident, fullname, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instanciar  referenciar los IDs del archivo xml
        ident = (EditText) findViewById(R.id.etIdent);
        fullname = (EditText) findViewById(R.id.etfullname);
        email = (EditText) findViewById(R.id.etemail);
        password = (EditText) findViewById(R.id.etpassword);
        Button btnsave = findViewById(R.id.btnsave);
        Button btnsearch = findViewById(R.id.btnsearch);
        Button btnedit = findViewById(R.id.btnedit);
        Button btndelete = findViewById(R.id.btndelete);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCustomer(ident.getText().toString(), fullname.getText().toString(), email.getText().toString(), password.getText().toString());
            }
        });

    }

    private void saveCustomer(String sident, String sfullname, String semail, String spassword) {
        Map<String, Object> customer = new HashMap<>();
        customer.put("Ident", sident);
        customer.put("Fullname", sfullname);
        customer.put("Email", semail);
        customer.put("Password", spassword);

        db.collection("customer")
                .add(customer)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Cliente agregado con éxito...", Toast.LENGTH_SHORT).show();

                        //Limpiar las cajas de texto
                        ident.setText("");
                        fullname.setText("");
                        email.setText("");
                        password.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error! el cliente no se agregó...", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}