package com.example.projeto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto1.DAO.DAO;
import com.example.projeto1.OBJETOS.Usuario;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    EditText editLogin;
    EditText editSenha;
    Button botaoEntrar;
    TextView textCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        botaoEntrar = findViewById(R.id.botaoEntrar);
        editLogin = findViewById(R.id.editLogin);
        editSenha = findViewById(R.id.editSenha);
        textCadastro = findViewById(R.id.textCadastro);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioLogin(editLogin.getText().toString());
                usuario.setUsuarioSenha(editSenha.getText().toString());
                login(usuario);
            }
        });

        textCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });
    }


    private void login(Usuario usuario){

        DAO dao = new DAO(this);
        Usuario autentica = dao.autentica(usuario);

        try{
            if(autentica.getUsuarioLocador() == 0){
                Intent intent = new Intent(getApplicationContext(), MainActivityUsuario.class);
                intent.putExtra("usuario", usuario.getUsuarioLogin());
                intent.putExtra("locador", "0");
                startActivity(intent);
            } else if(autentica.getUsuarioLocador() == 1){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("usuario",  usuario.getUsuarioLogin());
                intent.putExtra("locador",  "1");
                startActivity(intent);
            }

        } catch (Exception e){
            Log.d("erro", e.toString());
            Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
        }
    }
}