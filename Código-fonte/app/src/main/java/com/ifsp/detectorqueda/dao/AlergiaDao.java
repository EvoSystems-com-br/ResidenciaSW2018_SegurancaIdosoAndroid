package com.ifsp.detectorqueda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ifsp.detectorqueda.beans.Alergia;
import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AlergiaDao {
    public static final String TABELA = "alergia";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_DESCRICAO = "descricao";
    public static final String CAMPO_IDIDOSO = "id_idoso";

    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public AlergiaDao(Context context){
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public Alergia criar(Long id_idoso, Alergia alergia){
        if(alergia.isEmpty()){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }

        db = databaseHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(AlergiaDao.CAMPO_DESCRICAO, alergia.getDescricao());
        valores.put(AlergiaDao.CAMPO_IDIDOSO, id_idoso);

        long resultado = db.insert(AlergiaDao.TABELA, null, valores);

        db.close();

        if (resultado != -1) {
            alergia.setId(resultado);
            return alergia;
        } else {
            return null;
        }
    }

    public Alergia obter(Long id){
        if(id < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }

        db = databaseHelper.getReadableDatabase();

        String[] campos = {
                AlergiaDao.CAMPO_ID,
                AlergiaDao.CAMPO_DESCRICAO,
                AlergiaDao.CAMPO_IDIDOSO
        };

        String where = AlergiaDao.CAMPO_ID + "=" + id;

        Cursor cursor = db.query(AlergiaDao.TABELA, campos, where, null, null, null, null, null);

        Alergia alergia = new Alergia();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();

            alergia.setId(Long.parseLong(cursor.getString(0)));
            alergia.setDescricao(cursor.getString(1));
            alergia.getIdoso().setId(cursor.getLong(2));
        }

        cursor.close();
        db.close();

        return alergia;
    }

    public List<Alergia> obter(){
        Cursor cursor;
        String[] campos = {
                AlergiaDao.CAMPO_ID,
                AlergiaDao.CAMPO_DESCRICAO,
                AlergiaDao.CAMPO_IDIDOSO
        };

        db = databaseHelper.getReadableDatabase();
        cursor = db.query(AlergiaDao.TABELA, campos, null, null, null, null, null, null);

        List<Alergia> alergias = new ArrayList<Alergia>();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                Alergia alergia = new Alergia();
                alergia.setId(Long.parseLong(cursor.getString(0)));
                alergia.setDescricao(cursor.getString(1));

                alergias.add(alergia);
                cursor.moveToNext();
            }while(cursor.isLast());
        }

        cursor.close();
        db.close();
        return alergias;
    }

    public List<Alergia> obter(Idoso idoso){
        if(idoso.getId() < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido");
        }

        db = databaseHelper.getReadableDatabase();

        String[] campos = {
                AlergiaDao.CAMPO_ID,
                AlergiaDao.CAMPO_DESCRICAO,
                AlergiaDao.CAMPO_IDIDOSO
        };

        String where = AlergiaDao.CAMPO_IDIDOSO + "=" + idoso.getId();

        Cursor cursor = db.query(AlergiaDao.TABELA, campos, where, null, null, null, null, null);

        List<Alergia> alergias = new ArrayList<Alergia>();
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                Alergia alergia = new Alergia();
                alergia.setId(Long.parseLong(cursor.getString(0)));
                alergia.setDescricao(cursor.getString(1));

                alergias.add(alergia);
                cursor.moveToNext();
            }while(cursor.isLast());
        }

        cursor.close();
        db.close();

        return alergias;
    }

    public Alergia editar(Alergia alergia){
        if(alergia.isEmpty() || !alergia.isEntity()){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }
        if(validaAlergia(alergia.getId())){
            throw new Resources.NotFoundException("Não encontrou nenhum usuário com esse id!");
        }

        db = databaseHelper.getWritableDatabase();

        String where = AlergiaDao.CAMPO_ID + "=" + alergia.getId();

        ContentValues valores = new ContentValues();
        valores.put(AlergiaDao.CAMPO_DESCRICAO, alergia.getDescricao());
        valores.put(AlergiaDao.CAMPO_IDIDOSO, alergia.getIdoso().getId());

        long resultado = db.update(AlergiaDao.TABELA, valores, where,null);

        db.close();

        if(resultado > 0){
            return alergia;
        }else{
            return null;
        }
    }

    public boolean excluir(Long id) {
        if(id < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }
        if(validaAlergia(id)){
            throw new Resources.NotFoundException("Não encontrou nenhuma alergia com esse id!");
        }

        db = databaseHelper.getReadableDatabase();

        String where = AlergiaDao.CAMPO_ID + "=" + id;

        long resultado = db.delete(AlergiaDao.TABELA,where,null);

        db.close();

        if(resultado > 0){
            return true;
        }else{
            return false;
        }
    }

    private boolean validaAlergia(Long id){
        Alergia alergia = obter(id);
        if(alergia == null){
            return true;
        }else{
            return false;
        }
    }
}
