package com.example.projeto1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.projeto1.DAO.DAO;
import com.example.projeto1.OBJETOS.Usuario;

public class CadastroActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editTelefone;
    EditText editLogin;
    EditText editSenha;
    Switch switchUsuario;
    Button botaoCadastrar;
    Button botaoCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editEmail = findViewById(R.id.editEmail);
        editTelefone = findViewById(R.id.editTelefone);

        editLogin = findViewById(R.id.editLogin);
        editSenha = findViewById(R.id.editSenha);
        switchUsuario = findViewById(R.id.switchUsuario);
        botaoCadastrar = findViewById(R.id.botaoCadastrar);
        botaoCancelar = findViewById(R.id.botaoCancelar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("usuario", editLogin.getText().toString() + " 1");
                Usuario usuario = new Usuario();
                usuario.setUsuarioEmail(editEmail.getText().toString());
                usuario.setUsuarioTelefone(editTelefone.getText().toString());
                usuario.setUsuarioLogin(editLogin.getText().toString());
                usuario.setUsuarioSenha(editSenha.getText().toString());
                if(switchUsuario.isChecked()){
                    usuario.setUsuarioLocador(1);
                } else {
                    usuario.setUsuarioLocador(0);
                }

                DAO dao = new DAO(getApplicationContext());
                Boolean registraUsuario = dao.registraUsuario(usuario);

                if (registraUsuario == true){
                    Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso !", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "E-mail ou nome de usuário já existe no banco de dados", Toast.LENGTH_LONG).show();

                }

            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Processo de cadastro cancelado",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}