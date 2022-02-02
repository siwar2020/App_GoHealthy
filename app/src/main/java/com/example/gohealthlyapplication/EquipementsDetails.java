package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EquipementsDetails extends AppCompatActivity {
    private FloatingActionButton addToChart;
    private Button next;
    private ImageView im_equip;
    private TextView name_equip, description_equip, prix_equip;
    String EquipementId ="";
    private DatabaseReference mDatabase;
    private DatabaseReference equipements_medicaments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipements_details);
        next=findViewById(R.id.button_next);
        im_equip = findViewById(R.id.image_details);
        name_equip = findViewById(R.id.name_details);
        description_equip = findViewById(R.id.description_details);
        prix_equip = findViewById(R.id.prix_details);
        EquipementId = getIntent().getStringExtra("pid");
        GetEquipementDetails(EquipementId);

        /*addToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EquipementsDetails.this,Buy_an_equi.class);
                intent.putExtra("pid",EquipementId);
                startActivity(intent);
                finish();
            }
        });*/
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipementsDetails.this, Buy_an_equi.class);
                intent.putExtra("pid",EquipementId);
                startActivity(intent);



            }
        });


    }

    private void GetEquipementDetails(String id_) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        equipements_medicaments = mDatabase.child("equipements_médicaments");
        equipements_medicaments.child(id_).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    equipement_medicament equip =snapshot.getValue(equipement_medicament.class);
                    name_equip.setText(equip.getName());
                    description_equip.setText(equip.getDescription());
                    prix_equip.setText(equip.getPrice());
                    Picasso.get().load(equip.getUrl_im()).into(im_equip);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}