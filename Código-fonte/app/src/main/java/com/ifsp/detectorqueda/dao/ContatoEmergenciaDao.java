package com.ifsp.detectorqueda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ifsp.detectorqueda.beans.ContatoEmergencia;
import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ContatoEmergenciaDao {
    public static final String TABELA = "contatoEmergencia";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_NUMERO = "numero";
    public static final String CAMPO_PARENTESCO = "parentesco";
    public static final String CAMPO_IDIDOSO = "id_idoso";

    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public ContatoEmergenciaDao(Context context){
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public ContatoEmergencia criar(Long id_idoso, ContatoEmergencia contatoEmergencia){
        if(contatoEmergencia.isEmpty()){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }

        db = databaseHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContatoEmergenciaDao.CAMPO_NOME, contatoEmergencia.getNome());
        valores.put(ContatoEmergenciaDao.CAMPO_NUMERO, contatoEmergencia.getNumero());
        valores.put(ContatoEmergenciaDao.CAMPO_PARENTESCO, contatoEmergencia.getParentesco());
        valores.put(ContatoEmergenciaDao.CAMPO_IDIDOSO, id_idoso);

        long resultado = db.insert(ContatoEmergenciaDao.TABELA, null, valores);

        db.close();

        if (resultado != -1) {
            contatoEmergencia.setId(resultado);
            return contatoEmergencia;
        } else {
            return null;
        }
    }

    public ContatoEmergencia obter(Long id){
        if(id < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }

        db = databaseHelper.getReadableDatabase();

        String[] campos = {
                ContatoEmergenciaDao.CAMPO_ID,
                ContatoEmergenciaDao.CAMPO_NOME,
                ContatoEmergenciaDao.CAMPO_NUMERO,
                ContatoEmergenciaDao.CAMPO_PARENTESCO,
                ContatoEmergenciaDao.CAMPO_IDIDOSO
        };

        String where = ContatoEmergenciaDao.CAMPO_ID + "=" + id;

        Cursor cursor = db.query(ContatoEmergenciaDao.TABELA, campos, where, null, null, null, null, null);

        ContatoEmergencia contatoEmergencia = new ContatoEmergencia();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();

            contatoEmergencia.setId(Long.parseLong(cursor.getString(0)));
            contatoEmergencia.setNome(cursor.getString(1));
            contatoEmergencia.setNumero(Long.parseLong(cursor.getString(2)));
            contatoEmergencia.setParentesco(cursor.getString(3));
            contatoEmergencia.getIdoso().setId(cursor.getLong(4));
        }

        cursor.close();
        db.close();

        return contatoEmergencia;
    }

    public List<ContatoEmergencia> obter(Idoso idoso){
        if(idoso.getId() < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido!");
        }

        db = databaseHelper.getReadableDatabase();

        String[] campos = {
                ContatoEmergenciaDao.CAMPO_ID,
                ContatoEmergenciaDao.CAMPO_NOME,
                ContatoEmergenciaDao.CAMPO_NUMERO,
                ContatoEmergenciaDao.CAMPO_PARENTESCO,
                ContatoEmergenciaDao.CAMPO_IDIDOSO
        };

        String where = ContatoEmergenciaDao.CAMPO_IDIDOSO + "=" + idoso.getId();

        Cursor cursor = db.query(ContatoEmergenciaDao.TABELA, campos, where, null, null, null, null, null);

        List<ContatoEmergencia> contatosEmergencia = new ArrayList<ContatoEmergencia>();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                ContatoEmergencia contatoEmergencia = new ContatoEmergencia();
                contatoEmergencia.setId(Long.parseLong(cursor.getString(0)));
                contatoEmergencia.setNome(cursor.getString(1));
                contatoEmergencia.setNumero(Long.parseLong(cursor.getString(2)));
                contatoEmergencia.setParentesco(cursor.getString(3));

                contatosEmergencia.add(contatoEmergencia);
                cursor.moveToNext();
            }while(cursor.isLast());
        }

        cursor.close();
        db.close();

        return contatosEmergencia;
    }

    public List<ContatoEmergencia> obter(){
        Cursor cursor;
        String[] campos = {
                ContatoEmergenciaDao.CAMPO_ID,
                ContatoEmergenciaDao.CAMPO_NOME,
                ContatoEmergenciaDao.CAMPO_NUMERO,
                ContatoEmergenciaDao.CAMPO_PARENTESCO,
                ContatoEmergenciaDao.CAMPO_IDIDOSO
        };

        db = databaseHelper.getReadableDatabase();
        cursor = db.query(ContatoEmergenciaDao.TABELA, campos, null, null, null, null, null, null);

        List<ContatoEmergencia> contatosEmergencia = new ArrayList<ContatoEmergencia>();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                ContatoEmergencia contatoEmergencia = new ContatoEmergencia();
                contatoEmergencia.setId(Long.parseLong(cursor.getString(0)));
                contatoEmergencia.setNome(cursor.getString(1));
                contatoEmergencia.setNumero(Long.parseLong(cursor.getString(2)));
                contatoEmergencia.setParentesco(cursor.getString(3));

                contatosEmergencia.add(contatoEmergencia);
                cursor.moveToNext();
            }while(cursor.isLast());
        }

        cursor.close();
        db.close();
        return contatosEmergencia;
    }

    public ContatoEmergencia editar(ContatoEmergencia contatoEmergencia){
        if(contatoEmergencia.isEmpty() || !contatoEmergencia.isEntity()){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }
        if(validaContatoEmergencia(contatoEmergencia.getId())){
            throw new Resources.NotFoundException("Não encontrou nenhum usuário com esse id!");
        }

        db = databaseHelper.getWritableDatabase();

        String where = ContatoEmergenciaDao.CAMPO_ID + "=" + contatoEmergencia.getId();

        ContentValues valores = new ContentValues();
        valores.put(ContatoEmergenciaDao.CAMPO_NOME, contatoEmergencia.getNome());
        valores.put(ContatoEmergenciaDao.CAMPO_NUMERO, contatoEmergencia.getNumero());
        valores.put(ContatoEmergenciaDao.CAMPO_PARENTESCO, contatoEmergencia.getParentesco());
        valores.put(ContatoEmergenciaDao.CAMPO_IDIDOSO, contatoEmergencia.getIdoso().getId());

        long resultado = db.update(ContatoEmergenciaDao.TABELA, valores, where,null);

        db.close();

        if(resultado > 0){
            return contatoEmergencia;
        }else{
            return null;
        }
    }

    public boolean excluir(Long id) {
        if(id < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }
        if(validaContatoEmergencia(id)){
            throw new Resources.NotFoundException("Não encontrou nenhum contato de emergência com esse id!");
        }

        db = databaseHelper.getReadableDatabase();

        String where = ContatoEmergenciaDao.CAMPO_ID + "=" + id;

        long resultado = db.delete(ContatoEmergenciaDao.TABELA,where,null);

        db.close();

        if(resultado > 0){
            return true;
        }else{
            return false;
        }
    }

    private boolean validaContatoEmergencia(Long id){
        ContatoEmergencia contatoEmergencia = obter(id);
        if(contatoEmergencia == null){
            return true;
        }else{
            return false;
        }
    }
}
