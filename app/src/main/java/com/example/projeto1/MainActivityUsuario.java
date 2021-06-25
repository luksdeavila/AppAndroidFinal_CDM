package com.example.projeto1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.projeto1.ADAPTER.RecyclerViewAdapter;
import com.example.projeto1.ADAPTER.RecyclerViewAdapterUsuario;
import com.example.projeto1.DAO.DAO;
import com.example.projeto1.OBJETOS.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class MainActivityUsuario extends AppCompatActivity {

    Context context;

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerViewAdapterUsuario accessRecyclerViewAdapter;

    List<Veiculo> veiculos = new ArrayList<>();
    List<Integer> id = new ArrayList<Integer>();
    List<String> marcas = new ArrayList<String>();
    List<String> modelos = new ArrayList<String>();
    List<String> placas = new ArrayList<String>();
    List<String> diarias = new ArrayList<String>();
    List<String> proprietarios = new ArrayList<String>();
    List<String> imagens = new ArrayList<String>();
    Integer[] dados_id = new Integer[] {};
    String[] dados_marcas = new String[] {};
    String[] dados_modelos = new String[] {};
    String[] dados_placas = new String[] {};
    String[] dados_diarias = new String[] {};
    String[] dados_proprietarios = new String[] {};
    String[] dados_imagens = new String[] {};

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuario);

        context = getApplicationContext();


        intent = getIntent();

        recyclerView = findViewById(R.id.recyclerViewUsuario);

        buscaNoBanco();
    }

    private void buscaNoBanco() {
        DAO dao = new DAO(getApplicationContext());

        veiculos = dao.buscaVeiculo(intent.getStringExtra("usuario"),
                intent.getStringExtra("locador"), "0");

        id = new ArrayList<>();
        modelos = new ArrayList<>();
        diarias = new ArrayList<>();
        marcas = new ArrayList<>();
        placas = new ArrayList<>();
        proprietarios = new ArrayList<>();
        imagens = new ArrayList<>();

        dados_modelos = new String[] {};
        dados_id = new Integer[] {};
        dados_diarias = new String[] {};
        dados_marcas = new String[] {};
        dados_placas = new String[] {};
        dados_proprietarios = new String[] {};
        dados_imagens = new String[] {};

        for(Veiculo veiculoEncontrado : veiculos){
            id.add(veiculoEncontrado.getId());
            modelos.add(veiculoEncontrado.getModelo());
            diarias.add(String.valueOf(veiculoEncontrado.getDiaria()));
            marcas.add(veiculoEncontrado.getMarca());
            placas.add(veiculoEncontrado.getPlaca());
            proprietarios.add(veiculoEncontrado.getProprietario());
            imagens.add(String.valueOf(veiculoEncontrado.getImagem()));
        }

        dados_modelos = modelos.toArray(new String[0]);
        dados_id = id.toArray(new Integer[0]);
        dados_diarias = diarias.toArray(new String[0]);
        dados_marcas = marcas.toArray(new String[0]);
        dados_placas = placas.toArray(new String[0]);
        dados_proprietarios = proprietarios.toArray(new String[0]);
        dados_imagens = imagens.toArray(new String[0]);

        recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        preencheRecycler();

    }
    private void preencheRecycler() {
        accessRecyclerViewAdapter.veiculosParaRemover = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapterUsuario(context,
                dados_id,
                dados_modelos,
                dados_placas,
                dados_marcas,
                dados_diarias,
                dados_proprietarios,
                dados_imagens,
                intent.getStringExtra("usuario"));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_usuario, menu);


        MenuItem addProduct = menu.findItem(R.id.menu_ver_reservas);

        addProduct.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent2 = new Intent(context, ReservasUsuario.class);
                intent2.putExtra("usuario", intent.getStringExtra("usuario"));
                startActivity(intent2);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume(){
        super.onResume();
        buscaNoBanco();
    }

}