package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class view_post_fragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private DatabaseReference equipements_medicaments;
    private Button btn_search;
    private EditText inputText;
    private String search_txt;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_post_fragement, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        equipements_medicaments = mDatabase.child("equipements_médicaments");
        btn_search = rootView.findViewById(R.id.btn_search);
        inputText = rootView.findViewById(R.id.txt_search);
        recyclerView =rootView.findViewById(R.id.recycler_id);
        btn_search.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        equipements_medicaments = mDatabase.child("equipements_médicaments");
        FirebaseRecyclerOptions<equipement_medicament> options = new FirebaseRecyclerOptions.Builder<equipement_medicament>()
                .setQuery(equipements_medicaments.orderByChild("equi_state").equalTo("Approved"), equipement_medicament.class)
                .build();
        EquipementAdapter adapter=new EquipementAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new EquipementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
                String id=documentSnapshot.getKey();
                Intent intent =  new Intent(getActivity(),EquipementsDetails.class);
                intent.putExtra("pid",id);
                startActivity(intent);

            }
        });
        adapter.startListening();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_search) {
            search_txt = inputText.getText().toString(); //récupérer le texte inséré par l'utilisateur
            DatabaseReference equipement_medic = FirebaseDatabase.getInstance().getReference().child("equipements_médicaments");
            FirebaseRecyclerOptions<equipement_medicament> options = new FirebaseRecyclerOptions.Builder<equipement_medicament>().setQuery(equipement_medic.orderByChild("name").startAt(search_txt).endAt(search_txt), equipement_medicament.class).build();
            EquipementAdapter adapter = new EquipementAdapter(options) ;

            recyclerView.setAdapter(adapter);
            adapter.startListening();

        }
    }
}
