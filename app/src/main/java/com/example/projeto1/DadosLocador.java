package com.example.projeto1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DadosLocador extends AppCompatActivity {

    Intent intent;
    TextView textNome;
    TextView textEmail;
    TextView textTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_locador);

        intent = getIntent();

        textNome = findViewById(R.id.textViewNomeLocador);
        textEmail = findViewById(R.id.textViewEmailLocador);
        textTelefone = findViewById(R.id.textViewTelefoneLocador);

        textNome.setText(intent.getStringExtra("nome"));
        textEmail.setText(intent.getStringExtra("email"));
        textTelefone.setText(intent.getStringExtra("telefone"));

        textTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel",
                        intent.getStringExtra("telefone"),
                        null));
                if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(DadosLocador.this,  new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    startActivity(intent2);

                }
            }
        });


    }
}