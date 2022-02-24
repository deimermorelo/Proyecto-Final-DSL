package com.example.prueba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {
    //Clase nesesaria para la creación del RecyclerView de la Actividad Menu

    Context context;
    String[] nombres, precios, tipos;
    int[] images;

    public Adaptador(Context cont, String[] arr1, String[] arr2, String[] arr3, int[] img) {
        context = cont;
        nombres = arr1;
        precios = arr2;
        tipos = arr3;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text1.setText(nombres[position]);
        holder.text2.setText("$" + precios[position]);
        holder.imageView.setImageResource(images[position]);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodActivity.class);
                intent.putExtra("data1", nombres[holder.getAdapterPosition()]);
                intent.putExtra("data2", precios[holder.getAdapterPosition()]);
                intent.putExtra("data3", tipos[holder.getAdapterPosition()]);
                intent.putExtra("img", images[holder.getAdapterPosition()]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text1, text2;
        ImageView imageView;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.titulo_card);
            text2 = itemView.findViewById(R.id.precio_card);
            imageView = itemView.findViewById(R.id.image_card);
            cardView = itemView.findViewById(R.id.card_view);

        }
    }
}
