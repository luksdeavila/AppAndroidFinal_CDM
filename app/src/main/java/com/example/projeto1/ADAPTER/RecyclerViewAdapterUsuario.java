package com.example.projeto1.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto1.AddVeiculo;
import com.example.projeto1.DAO.DAO;
import com.example.projeto1.MainActivity;
import com.example.projeto1.MainActivityUsuario;
import com.example.projeto1.R;
import com.example.projeto1.ReservasUsuario;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapterUsuario extends RecyclerView.Adapter<RecyclerViewAdapterUsuario.ViewHolder>{

    Context context;
    List<String> modelos = new ArrayList<String>();
    public static List<Integer> veiculosParaRemover = new ArrayList<Integer>();

    Integer[] id;
    String[] marcas;
    String[] placas;
    String[] diarias;
    String[] proprietarios;
    String[] imagens;
    String usuario;

    View viewOnCreate;
    ViewHolder viewHolderLocal;


    public RecyclerViewAdapterUsuario(Context contextReceived,
                                      Integer[] idReceived,
                                      String[] modelosRecebidos,
                                      String[] placasRecebidas,
                                      String[] marcasRecebidas,
                                      String[] diariasRecebidas,
                                      String[] proprietariosRecebidos,
                                      String[] imagensRecebidas,
                                      String usuarioRecebido) {

        context = contextReceived;
        modelos.addAll(Arrays.asList(modelosRecebidos));
        id = idReceived;
        placas = placasRecebidas;
        marcas = marcasRecebidas;
        diarias = diariasRecebidas;
        proprietarios = proprietariosRecebidos;
        imagens = imagensRecebidas;
        usuario = usuarioRecebido;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textMarca;
        public TextView textPlaca;
        public TextView textModelo;
        public TextView textDiaria;
        public TextView textProprietario;
        public ImageView icone;
        public Button botao;

        public ViewHolder(View itemView) {
            super(itemView);
            textMarca = itemView.findViewById(R.id.itens_recyclerviewadapter_marca);
            textModelo = itemView.findViewById(R.id.itens_recyclerviewadapter_modelo);
            textPlaca = itemView.findViewById(R.id.itens_recyclerviewadapter_placa);
            textDiaria = itemView.findViewById(R.id.itens_recyclerviewadapter_diaria);
            textProprietario = itemView.findViewById(R.id.itens_recyclerviewadapter_proprietario);
            icone = itemView.findViewById(R.id.itens_recyclerviewadapter_icone);
            botao = itemView.findViewById(R.id.itens_recyclerviewadapter_button);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapterUsuario.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewOnCreate = LayoutInflater.from(context).inflate(R.layout.itens_recyclerviewadapter_usuario, parent, false);
        viewHolderLocal = new ViewHolder(viewOnCreate);
        return viewHolderLocal;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterUsuario.ViewHolder holder, final int position) {
        holder.textModelo.setText(modelos.get(position));

        DecimalFormat formatMoney = new DecimalFormat("'R$' #,##");
        holder.textDiaria.setText(formatMoney.format(new BigDecimal(diarias[position])));

        holder.textMarca.setText(marcas[position]);
        holder.textPlaca.setText(placas[position]);
        holder.textProprietario.setText(proprietarios[position]);

        if(imagens[position].equals("") || imagens[position].equals("null")){

            holder.icone.setImageResource(R.mipmap.ic_sem_imagem);

        } else {
            byte[] imageInBytes;
            imageInBytes = Base64.decode(imagens[position], Base64.DEFAULT);
            Bitmap imageDecoded = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
            holder.icone.setImageBitmap(imageDecoded);
        }

        holder.botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAO dao = new DAO(context);
                dao.reservaVeiculo(id[position], usuario);
                dao.close();

                Intent intent = new Intent(context, ReservasUsuario.class);
                intent.putExtra("usuario", usuario);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelos.size();
    }

}

