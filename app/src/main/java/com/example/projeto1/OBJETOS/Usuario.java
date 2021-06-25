package com.example.projeto1.OBJETOS;

public class Usuario {
    Integer id;
    String usuarioLogin;
    String usuarioSenha;
    String usuarioEmail;
    String usuarioTelefone;
    Integer usuarioLocador;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getUsuarioSenha() {
        return usuarioSenha;
    }

    public void setUsuarioSenha(String usuarioSenha) {
        this.usuarioSenha = usuarioSenha;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public Integer getUsuarioLocador() {
        return usuarioLocador;
    }

    public void setUsuarioLocador(Integer usuarioLocador) {
        this.usuarioLocador = usuarioLocador;
    }

    public String getUsuarioTelefone() {
        return usuarioTelefone;
    }

    public void setUsuarioTelefone(String usuarioTelefone) {
        this.usuarioTelefone = usuarioTelefone;
    }
}
