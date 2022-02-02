package com.example.gohealthlyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

public class EquipementAdapter extends FirebaseRecyclerAdapter<equipement_medicament, EquipementAdapter.EquipementViewHolder> {

    private OnItemClickListener listener;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EquipementAdapter(@NonNull FirebaseRecyclerOptions<equipement_medicament> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EquipementViewHolder holder, int i, @NonNull equipement_medicament model) {
        holder.txt_name_equi.setText(model.getName());
        holder.txt_description.setText(model.getDescription());
        holder.txt_price.setText("price "+model.getPrice()+" DT");
        try{
            if (!model.getEncoreDisponible()){
                holder.txt_dispo.setText("Non Disponible");



            }
        }catch (Exception e){
            }
        Picasso.get().load(model.getUrl_im()).into(holder.imageView);}


    @NonNull
    @Override
    public EquipementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.equipement_items,parent,false);
        EquipementViewHolder holder = new EquipementViewHolder(view);
        return holder ;
    }

    public class EquipementViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name_equi, txt_description, txt_price,txt_dispo;
        public ImageView imageView;



        public EquipementViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_equi);
            txt_name_equi = itemView.findViewById(R.id.name_equi);
            txt_description = itemView.findViewById(R.id.description_equi);
            txt_price = itemView.findViewById(R.id.price_equi);
            txt_dispo=itemView.findViewById(R.id.dispo) ;


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION && listener != null) {

                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }  });

            }

    }
    public interface OnItemClickListener {


        void onItemClick(DataSnapshot documentSnapshot, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;

    }

}
