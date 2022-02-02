package com.example.gohealthlyapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class search_fragment  extends Fragment implements View.OnClickListener {
    private Button btn_search;
    private EditText inputText;
    private RecyclerView searchList;
    private View v;
    private String search_txt;

    @Nullable
    @Override
    //inflate the layout for this fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.search_fragment, container, false);
        btn_search = v.findViewById(R.id.btn_search);
        inputText = v.findViewById(R.id.txt_search);
        searchList = v.findViewById(R.id.recycler_id);
        searchList.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_search.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_search) {
            search_txt = inputText.getText().toString(); //récupérer le texte inséré par l'utilisateur
            onStart();

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference equipement_medic = FirebaseDatabase.getInstance().getReference().child("equipements_médicaments");
        FirebaseRecyclerOptions<equipement_medicament> options = new FirebaseRecyclerOptions.Builder<equipement_medicament>().setQuery(equipement_medic.orderByChild("name").startAt(search_txt), equipement_medicament.class).build();
        EquipementAdapter adapter = new EquipementAdapter(options) ;

        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}
