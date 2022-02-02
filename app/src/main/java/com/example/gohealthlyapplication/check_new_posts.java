package com.example.gohealthlyapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class check_new_posts extends AppCompatActivity {
    private RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unapprouved_posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_posts);
        recyclerview= findViewById(R.id.admin_new_posts);
        recyclerview.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        unapprouved_posts = FirebaseDatabase.getInstance().getReference().child("equipements_médicaments");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<equipement_medicament> options = new FirebaseRecyclerOptions.Builder<equipement_medicament>().setQuery(unapprouved_posts.orderByChild("equi_state").equalTo("Not approved"), equipement_medicament.class).build();
        EquipementAdapter adapter=new EquipementAdapter(options);
        recyclerview.setAdapter(adapter);
        adapter.startListening();
        adapter.setOnItemClickListener(new EquipementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
                String id=documentSnapshot.getKey();
                CharSequence choose_options []= new CharSequence[]{"Yes ","No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(check_new_posts.this);
                builder.setTitle("would you like to approve this post ?");
                builder.setItems(choose_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            approve_post(id);
                        }
                        if(which==1){

                        }

                    }
                });
                builder.show();
            }
        });
    }

    private void approve_post(String id) {
        unapprouved_posts.child(id).child("equi_state").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(check_new_posts.this,"post is approved successufly",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
