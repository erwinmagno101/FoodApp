package com.example.foodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResAdapter extends RecyclerView.Adapter<ResVH>{

    List<String> id, email, mobile, date, time, guest, tableres, request;


    public ResAdapter(List<String> id, List<String> email, List<String> mobile, List<String> date, List<String> time, List<String> guest, List<String> tableres, List<String> request) {

        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.date = date;
        this.time = time;
        this.guest = guest;
        this.tableres = tableres;
        this.request = request;
    }

    @NonNull
    @Override
    public ResVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item4, parent, false);

        return new ResVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ResVH holder, int position) {
        holder.item4email.setText(email.get(position));
        holder.item4phone.setText(mobile.get(position));
        holder.item4Date.setText(date.get(position));
        holder.item4Time.setText(time.get(position));
        holder.item4Guest.setText(guest.get(position));
        holder.item4Table.setText(tableres.get(position));
        holder.item4Special.setText(request.get(position));

    }

    @Override
    public int getItemCount() {
        return email.size();
    }

}

class ResVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView item4email, item4phone, item4Date, item4Time, item4Guest, item4Table, item4Special;
    Button btncancel;
    DBHelper2 DB;

    private ResAdapter adapter;


    public ResVH(@NonNull View itemView) {
        super(itemView);

        DB = new DBHelper2(itemView.getContext());
        item4email = itemView.findViewById(R.id.item4email);
        item4phone = itemView.findViewById(R.id.item4phone);
        item4Date = itemView.findViewById(R.id.item4Date);
        item4Time = itemView.findViewById(R.id.item4Time);
        item4Guest = itemView.findViewById(R.id.item4Guest);
        item4Table = itemView.findViewById(R.id.item4Table);
        item4Special = itemView.findViewById(R.id.item4Special);
        btncancel = itemView.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(this);
    }

    public ResVH linkAdapter(ResAdapter adapter){
        this.adapter = adapter;
        return this;
    }


    @Override
    public void onClick(View view) {
        if (view == btncancel){
            DB.deleteReservations(adapter.id.get(getAdapterPosition()));
            adapter.email.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
            Toast.makeText(view.getContext(), "Reservation Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
