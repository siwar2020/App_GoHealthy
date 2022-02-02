package com.example.gohealthlyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class orderAdapter extends FirebaseRecyclerAdapter<order, orderAdapter.orderViewHolder> {

    private OnItemClickListener listener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public orderAdapter(@NonNull FirebaseRecyclerOptions<order> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull orderViewHolder holder, int i, @NonNull order model) {
        holder.txt_name_user.setText(model.getName());
        holder.txt_phone.setText(model.getPhone());
        holder.txt_address.setText(model.getAddress());
        holder.txt_time.setText(model.getTime());

    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false);
        orderViewHolder holder = new orderViewHolder(view);
        return holder;
    }


    public class orderViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_name_user, txt_phone, txt_price, txt_address, txt_time;
        public Button validate_order;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_user = itemView.findViewById(R.id.name_user);
            txt_phone = itemView.findViewById(R.id.phone_user);
            txt_price = itemView.findViewById(R.id.price_);
            txt_address = itemView.findViewById(R.id.address_);
            txt_time = itemView.findViewById(R.id.date_time_order);


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