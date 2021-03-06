package com.example.caro.microadmin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Caro on 31/05/2017.
 */

public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.ViewHolder>{
    private Context context;
    private  ArrayList<Producto> listaProductos;
    private  ArrayList<Producto> filtradaListaProductos;



    public InventarioAdapter(Context context, ArrayList<Producto> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos;
        this.filtradaListaProductos = new ArrayList<>();
        filtradaListaProductos.addAll(this.listaProductos);

    }

    public InventarioAdapter(Context context){
        this.context = context;
    }

    public void actualizarLista(ArrayList<Producto> prod){
        this.listaProductos = prod;
        this.filtradaListaProductos = prod;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nombre = filtradaListaProductos.get(position).getNombre();
        String precioUnitario = String.valueOf(filtradaListaProductos.get(position).getPrecioUnidad());
        String cantidad =  String.valueOf(filtradaListaProductos.get(position).getCantidad());
        holder.nombreProducto.setText(nombre);
        holder.idProducto = filtradaListaProductos.get(position).getIDProducto();
        holder.cantidad.setText(cantidad);
        holder.precioUnitario.setText(precioUnitario);
        Picasso.with(context).load(filtradaListaProductos.get(position).getURL()).into(holder.imagenProducto);


    }

    @Override
    public int getItemCount() {
        return filtradaListaProductos.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imagenProducto;
        public TextView nombreProducto;
        public TextView precioUnitario;
        public TextView cantidad;
        public int idProducto;

        public ViewHolder(View itemView) {
            super(itemView);
            imagenProducto = (ImageView) itemView.findViewById(R.id.img_producto);
            nombreProducto = (TextView) itemView.findViewById(R.id.tv_nombre_producto);
            precioUnitario = (TextView) itemView.findViewById(R.id.tv_precio_unit_inventario);
            cantidad = (TextView) itemView.findViewById(R.id.tv_cantidad);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    Intent intent = new Intent(context,MostrarProducto.class);
                    intent.putExtra("Producto",filtradaListaProductos.get(posicion));
                    intent.putExtra("posicion",posicion);
                    context.startActivity(intent);
                }
            });
        }


        @Override
        public void onClick(View v) {

        }



    }//Fin clase ViewHolder

    public void filterData(String query){
        query = query.toLowerCase();
        if(query.isEmpty()){
            filtradaListaProductos.clear();
            filtradaListaProductos.addAll(listaProductos);
        }else{
            ArrayList<Producto> nuevosHijos = new ArrayList<>();
            for (int i = 0;i<listaProductos.size();i++){

                Producto producto =listaProductos.get(i);
                String Nombre = producto.getNombre();

                if(Nombre.toLowerCase().contains(query)){
                    nuevosHijos.add(listaProductos.get(i));
                }


            }
            if (nuevosHijos.size()>0 && listaProductos.size() !=nuevosHijos.size() ){
                filtradaListaProductos = nuevosHijos;
            }
        }
        try {
            notifyDataSetChanged();
        }catch (Exception e){

        }

    }

}//fin clase adapter





