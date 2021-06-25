package com.example.projeto1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.projeto1.OBJETOS.Usuario;
import com.example.projeto1.OBJETOS.Veiculo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DAO extends SQLiteOpenHelper {
    public DAO(Context context) {

        super(context,
                "projeto1",
                null,

                24);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_veiculo = "CREATE TABLE veiculo (" +
                "veiculoId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "veiculoMarca TEXT, " +
                "veiculoModelo TEXT, " +
                "veiculoPlaca TEXT, " +
                "veiculoProprietario TEXT, " +
                "veiculoReservado TEXT, " +
                "veiculoDiaria REAL, " +
                "veiculoImagem TEXT);";

        String sql_usuario = "CREATE TABLE usuario (" +
                "usuarioId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuarioLogin TEXT UNIQUE, " +
                "usuarioSenha TEXT, " +
                "usuarioLocador Integer, " +
                "usuarioTelefone TEXT, " +
                "usuarioEmail TEXT UNIQUE);";
        db.execSQL(sql_usuario);
        db.execSQL(sql_veiculo);

        ContentValues veiculo = new ContentValues();
        ContentValues usuario = new ContentValues();

        veiculo.put("veiculoMarca", "Ford");
        veiculo.put("veiculoModelo", "Focus");
        veiculo.put("veiculoPlaca", "FOC-1234");
        veiculo.put("veiculoProprietario", "admin");
        veiculo.put("veiculoDiaria", String.valueOf(new BigDecimal(99.00)));
        db.insert("veiculo", null, veiculo );

        veiculo.put("veiculoMarca", "Fiat");
        veiculo.put("veiculoModelo", "Uno");
        veiculo.put("veiculoPlaca", "FAT-2468");
        veiculo.put("veiculoProprietario", "admin");
        veiculo.put("veiculoDiaria", String.valueOf(new BigDecimal(79.00)));
        db.insert("veiculo", null, veiculo );

        veiculo.put("veiculoMarca", "Volkswagen");
        veiculo.put("veiculoModelo", "GOL");
        veiculo.put("veiculoPlaca", "VKN-4816");
        veiculo.put("veiculoProprietario", "admin");
        veiculo.put("veiculoDiaria", String.valueOf(new BigDecimal(89.00)));
        db.insert("veiculo", null, veiculo );

        usuario.put("usuarioLogin", "admin");
        usuario.put("usuarioSenha", "admin");
        usuario.put("usuarioEmail", "admin@locaapp.com.br");
        usuario.put("usuarioLocador", 1);
        usuario.put("usuarioTelefone", "988887777");
        db.insert("usuario", null, usuario);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_drop_veiculo = "DROP TABLE IF EXISTS veiculo";
        String sql_drop_usuario = "DROP TABLE IF EXISTS usuario";
        db.execSQL(sql_drop_usuario);
        db.execSQL(sql_drop_veiculo);
        onCreate(db);
    }

    public List<Veiculo> buscaVeiculo(String usuario, String locador, String reserva) {

        String sql = "";

        if(locador.equals("0")){
            if(reserva.equals("0")){
                sql = "SELECT * FROM veiculo WHERE veiculoReservado is NULL or veiculoReservado = '';";
            } else {
                sql = "SELECT * FROM veiculo WHERE veiculoReservado = '" + usuario + "';";
            }

        } else if(locador.equals("1")){
            sql = "SELECT * FROM veiculo WHERE veiculoProprietario = '" + usuario + "';";
        }

        SQLiteDatabase db = getReadableDatabase();

        List<Veiculo> veiculos = new ArrayList<Veiculo>();
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()) {
            Veiculo veiculo = new Veiculo();
            veiculo.setId(Integer.valueOf(c.getString(c.getColumnIndex("veiculoId"))));
            veiculo.setModelo(c.getString(c.getColumnIndex("veiculoModelo")));
            veiculo.setMarca(c.getString(c.getColumnIndex("veiculoMarca")));
            veiculo.setPlaca(c.getString(c.getColumnIndex("veiculoPlaca")));
            veiculo.setDiaria(c.getString(c.getColumnIndex("veiculoDiaria")));
            veiculo.setProprietario(c.getString(c.getColumnIndex("veiculoProprietario")));
            veiculo.setReservado(c.getString(c.getColumnIndex("veiculoReservado")));
            veiculo.setImagem(c.getString(c.getColumnIndex("veiculoImagem")));
            veiculos.add(veiculo);
        }
        return veiculos;
    }

    public Usuario autentica(Usuario usuario){


        SQLiteDatabase db = getReadableDatabase();
        String sql_busca_usuarios = "SELECT * from usuario WHERE usuarioLogin = " + "'" + usuario.getUsuarioLogin() + "'";
        Cursor c = db.rawQuery(sql_busca_usuarios, null);
        while (c.moveToNext()){
            if(usuario.getUsuarioLogin().equals(c.getString(c.getColumnIndex("usuarioLogin")))){
                if(usuario.getUsuarioSenha().equals(c.getString(c.getColumnIndex("usuarioSenha")))){
                    Usuario usuarioLogado = new Usuario();
                    usuarioLogado.setUsuarioEmail(c.getString(c.getColumnIndex("usuarioEmail")));
                    usuarioLogado.setUsuarioTelefone(c.getString(c.getColumnIndex("usuarioTelefone")));
                    usuarioLogado.setUsuarioLogin(c.getString(c.getColumnIndex("usuarioLogin")));
                    usuarioLogado.setUsuarioLocador(c.getInt(c.getColumnIndex("usuarioLocador")));
                    return usuarioLogado;
                }
            }
        }
        db.close();
        c.close();
        return null;
    }

    public Usuario buscaLocador(String proprietario){

        SQLiteDatabase db = getReadableDatabase();
        String sql_busca_usuarios = "SELECT * from usuario WHERE usuarioLogin = " + "'" + proprietario + "'";
        Cursor c = db.rawQuery(sql_busca_usuarios, null);
        while (c.moveToNext()){
            if(proprietario.equals(c.getString(c.getColumnIndex("usuarioLogin")))){
                    Usuario locador = new Usuario();
                    locador.setUsuarioEmail(c.getString(c.getColumnIndex("usuarioEmail")));
                    locador.setUsuarioTelefone(c.getString(c.getColumnIndex("usuarioTelefone")));
                    locador.setUsuarioLogin(c.getString(c.getColumnIndex("usuarioLogin")));
                    locador.setUsuarioLocador(c.getInt(c.getColumnIndex("usuarioLocador")));
                    return locador;
            }
        }
        db.close();
        c.close();
        return null;
    }

    public boolean registraUsuario(Usuario usuario) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = new ContentValues();
        Log.d("Locador", usuario.getUsuarioLocador().toString());
        data.put("usuarioLogin", usuario.getUsuarioLogin());
        data.put("usuarioSenha", usuario.getUsuarioSenha());
        data.put("usuarioEmail", usuario.getUsuarioEmail());
        data.put("usuarioLocador", usuario.getUsuarioLocador());
        data.put("usuarioTelefone", usuario.getUsuarioTelefone());


        try{
            db.insertOrThrow("usuario", null, data );
        } catch (SQLiteConstraintException e) {
            Log.d("erro", e.toString());
            return false;
        }

        return true;

    }

    public void insereVeiculo(Veiculo veiculo) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = new ContentValues();

        data.put("veiculoId", veiculo.getId());
        data.put("veiculoMarca", veiculo.getMarca());
        data.put("veiculoModelo", veiculo.getModelo());
        data.put("veiculoPlaca", veiculo.getPlaca());
        data.put("veiculoProprietario", veiculo.getProprietario());
        data.put("veiculoDiaria", veiculo.getDiaria());

        if(!veiculo.getImagem().equals("")){
            data.put("veiculoImagem", veiculo.getImagem());
        }

        try{

            db.insertOrThrow("veiculo", null, data );
        } catch (SQLiteConstraintException e) {
            data.put("veiculoId", veiculo.getId());
            db.update("veiculo", data, "veiculoId = ?", new String[]{String.valueOf(veiculo.getId())});
        }
    }

    public void reservaVeiculo(Integer id, String usuario) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = new ContentValues();

        data.put("veiculoReservado", usuario);
        try{
            db.update("veiculo", data, "veiculoId = ?", new String[]{String.valueOf(id)});
        } catch (SQLiteConstraintException e) {
            Log.d("Erro: ", e.toString());
        }

    }

    public void cancelaReserva(Integer id) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = new ContentValues();

        data.put("veiculoReservado", "");
        try{
            db.update("veiculo", data, "veiculoId = ?", new String[]{String.valueOf(id)});
        } catch (SQLiteConstraintException e) {
            Log.d("Erro: ", e.toString());
        }

    }

    public void removeVeiculo(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM veiculo WHERE veiculoId = " + "'" + id + "'";
        db.execSQL(sql);
    }

}
