package com.ifsp.detectorqueda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.beans.Medicamento;
import com.ifsp.detectorqueda.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoDao {
    public static final String TABELA = "medicamento";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_IDIDOSO = "id_idoso";

    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public MedicamentoDao(Context context){
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public Medicamento criar(Long id_idoso, Medicamento medicamento){
        if(medicamento.isEmpty()){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }

        db = databaseHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(MedicamentoDao.CAMPO_NOME, medicamento.getNome());
        valores.put(MedicamentoDao.CAMPO_IDIDOSO, id_idoso);

        long resultado = db.insert(MedicamentoDao.TABELA, null, valores);

        db.close();

        if (resultado != -1) {
            medicamento.setId(resultado);
            return medicamento;
        } else {
            return null;
        }
    }

    public Medicamento obter(Long id){
        if(id < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }

        db = databaseHelper.getReadableDatabase();

        String[] campos = {
                MedicamentoDao.CAMPO_ID,
                MedicamentoDao.CAMPO_NOME,
                MedicamentoDao.CAMPO_IDIDOSO
        };

        String where = MedicamentoDao.CAMPO_ID + "=" + id;

        Cursor cursor = db.query(MedicamentoDao.TABELA, campos, where, null, null, null, null, null);

        Medicamento medicamento = new Medicamento();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();

            medicamento.setId(Long.parseLong(cursor.getString(0)));
            medicamento.setNome(cursor.getString(1));
            medicamento.getIdoso().setId(cursor.getLong(2));
        }

        cursor.close();
        db.close();

        return medicamento;
    }

    public List<Medicamento> obter(Idoso idoso){
        if(idoso.getId() < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido!");
        }

        db = databaseHelper.getReadableDatabase();

        String[] campos = {
                MedicamentoDao.CAMPO_ID,
                MedicamentoDao.CAMPO_NOME,
                MedicamentoDao.CAMPO_IDIDOSO
        };

        String where = MedicamentoDao.CAMPO_IDIDOSO + "=" + idoso.getId();

        Cursor cursor = db.query(MedicamentoDao.TABELA, campos, where, null, null, null, null, null);

        List<Medicamento> medicamentos = new ArrayList<Medicamento>();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                Medicamento medicamento = new Medicamento();
                medicamento.setId(Long.parseLong(cursor.getString(0)));
                medicamento.setNome(cursor.getString(1));

                medicamentos.add(medicamento);
                cursor.moveToNext();
            }while(cursor.isLast());
        }

        cursor.close();
        db.close();

        return medicamentos;
    }

    public List<Medicamento> obter(){
        Cursor cursor;
        String[] campos = {
                MedicamentoDao.CAMPO_ID,
                MedicamentoDao.CAMPO_NOME,
                MedicamentoDao.CAMPO_IDIDOSO
        };

        db = databaseHelper.getReadableDatabase();
        cursor = db.query(MedicamentoDao.TABELA, campos, null, null, null, null, null, null);

        List<Medicamento> medicamentos = new ArrayList<Medicamento>();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                Medicamento medicamento = new Medicamento();
                medicamento.setId(Long.parseLong(cursor.getString(0)));
                medicamento.setNome(cursor.getString(1));

                medicamentos.add(medicamento);
                cursor.moveToNext();
            }while(cursor.isLast());
        }

        cursor.close();
        db.close();
        return medicamentos;
    }

    public Medicamento editar(Medicamento medicamento){
        if(medicamento.isEmpty() || !medicamento.isEntity()){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }
        if(validaMedicamento(medicamento.getId())){
            throw new Resources.NotFoundException("Não encontrou nenhum usuário com esse id!");
        }

        db = databaseHelper.getWritableDatabase();

        String where = MedicamentoDao.CAMPO_ID + "=" + medicamento.getId();

        ContentValues valores = new ContentValues();
        valores.put(MedicamentoDao.CAMPO_NOME, medicamento.getNome());
        valores.put(MedicamentoDao.CAMPO_IDIDOSO, medicamento.getIdoso().getId());

        long resultado = db.update(MedicamentoDao.TABELA, valores, where,null);

        db.close();

        if(resultado > 0){
            return medicamento;
        }else{
            return null;
        }
    }

    public boolean excluir(Long id) {
        if(id < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }
        if(validaMedicamento(id)){
            throw new Resources.NotFoundException("Não encontrou nenhum medicamento com esse id!");
        }

        db = databaseHelper.getReadableDatabase();

        String where = MedicamentoDao.CAMPO_ID + "=" + id;

        long resultado = db.delete(MedicamentoDao.TABELA,where,null);

        db.close();

        if(resultado > 0){
            return true;
        }else{
            return false;
        }
    }

    private boolean validaMedicamento(Long id){
        Medicamento medicamento = obter(id);
        if(medicamento == null){
            return true;
        }else{
            return false;
        }
    }
}
