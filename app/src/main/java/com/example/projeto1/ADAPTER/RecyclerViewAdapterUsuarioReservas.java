package com.example.projeto1.ADAPTER;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto1.DAO.DAO;
import com.example.projeto1.DadosLocador;
import com.example.projeto1.MainActivityUsuario;
import com.example.projeto1.OBJETOS.Usuario;
import com.example.projeto1.R;
import com.example.projeto1.ReservasUsuario;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapterUsuarioReservas extends RecyclerView.Adapter<RecyclerViewAdapterUsuarioReservas.ViewHolder>{

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


    public RecyclerViewAdapterUsuarioReservas(Context contextReceived,
                                              Integer[] idReceived,
                                              String[] modelosRecebidos,
                                              String[] placasRecebidas,
                                              String[] marcasRecebidas,
                                              String[] diariasRecebidas,
                                              String[] proprietariosRecebidos,
                                              String[] imagensRecebidas,
                                              String usuarioRecebido){

        context = contextReceived;
        modelos.addAll(Arrays.asList(modelosRecebidos));
        id = idReceived;
        placas = placasRecebidas;
        marcas = marcasRecebidas;
        diarias = diariasRecebidas;
        proprietarios = proprietariosRecebidos;
        imagens = imagensRecebidas;
        usuario = usuarioRecebido;
        this.context = contextReceived;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textMarca;
        public TextView textPlaca;
        public TextView textModelo;
        public TextView textDiaria;
        public TextView textProprietario;
        public ImageView icone;
        public Button botaoCancelarReserva;
        public Button botaoContato;

        public ViewHolder(View itemView) {
            super(itemView);
            textMarca = itemView.findViewById(R.id.itens_recyclerviewadapter_marca);
            textModelo = itemView.findViewById(R.id.itens_recyclerviewadapter_modelo);
            textPlaca = itemView.findViewById(R.id.itens_recyclerviewadapter_placa);
            textDiaria = itemView.findViewById(R.id.itens_recyclerviewadapter_diaria);
            textProprietario = itemView.findViewById(R.id.itens_recyclerviewadapter_proprietario);
            icone = itemView.findViewById(R.id.itens_recyclerviewadapter_icone);
            botaoCancelarReserva = itemView.findViewById(R.id.itens_recyclerviewadapter_button_cancelar);
            botaoContato = itemView.findViewById(R.id.itens_recyclerviewadapter_button_contato);


        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapterUsuarioReservas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewOnCreate = LayoutInflater.from(context).inflate(R.layout.itens_recyclerviewadapter_usuario_reservas, parent, false);
        viewHolderLocal = new ViewHolder(viewOnCreate);


        return viewHolderLocal;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterUsuarioReservas.ViewHolder holder, final int position) {
        holder.textModelo.setText(modelos.get(position));

        DecimalFormat formatMoney = new DecimalFormat("'R$' #,##");
        holder.textDiaria.setText(formatMoney.format(new BigDecimal(diarias[position])));

        holder.textMarca.setText(marcas[position]);
        holder.textPlaca.setText(placas[position]);

        if(imagens[position].equals("") || imagens[position].equals("null")){

            holder.icone.setImageResource(R.mipmap.ic_sem_imagem);

        } else {
            byte[] imageInBytes;
            imageInBytes = Base64.decode(imagens[position], Base64.DEFAULT);
            Bitmap imageDecoded = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
            holder.icone.setImageBitmap(imageDecoded);
        }

        holder.botaoCancelarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAO dao = new DAO(context);
                dao.cancelaReserva(id[position]);
                dao.close();

                Intent intent = new Intent(context, MainActivityUsuario.class);
                intent.putExtra("usuario", usuario);
                view.getContext().startActivity(intent);

            }
        });

        holder.botaoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO dao = new DAO(context);
                Usuario locador = dao.buscaLocador(proprietarios[position]);
                dao.close();
                Intent intent = new Intent(context, DadosLocador.class);
                intent.putExtra("nome", locador.getUsuarioLogin());
                intent.putExtra("email", locador.getUsuarioEmail());
                intent.putExtra("telefone", locador.getUsuarioTelefone());
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelos.size();
    }

}

