package com.ifsp.detectorqueda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ifsp.detectorqueda.beans.Alergia;
import com.ifsp.detectorqueda.beans.ContatoEmergencia;
import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.beans.Medicamento;
import com.ifsp.detectorqueda.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class IdosoDao {
    public static final String TABELA = "idoso";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_DATANASCIMENTO = "dataNascimento";
    public static final String CAMPO_GRUPOSANGUINEO = "grupoSanguineo";
    public static final String CAMPO_ALTURA = "altura";
    public static final String CAMPO_PESO = "peso";
    public static final String CAMPO_LOGRADOURO = "logradouro";
    public static final String CAMPO_NUMERO = "numero";
    public static final String CAMPO_CEP = "cep";
    public static final String CAMPO_BAIRRO = "bairro";
    public static final String CAMPO_CIDADE = "cidade";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_COMPLEMENTO = "complemento";

    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    private Context context;

    public IdosoDao(Context context){
        this.context = context;
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    public Idoso criar(Idoso idoso){
        if(idoso.isEmpty()){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }

        db = databaseHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(IdosoDao.CAMPO_NOME, idoso.getNome());
        valores.put(IdosoDao.CAMPO_DATANASCIMENTO, idoso.getDataNascimento());
        valores.put(IdosoDao.CAMPO_GRUPOSANGUINEO, idoso.getGrupoSanguineo());
        valores.put(IdosoDao.CAMPO_ALTURA, idoso.getAltura());
        valores.put(IdosoDao.CAMPO_PESO, idoso.getPeso());
        valores.put(IdosoDao.CAMPO_LOGRADOURO, idoso.getLogradouro());
        valores.put(IdosoDao.CAMPO_NUMERO, idoso.getNumero());
        valores.put(IdosoDao.CAMPO_CEP, idoso.getCep());
        valores.put(IdosoDao.CAMPO_BAIRRO, idoso.getBairro());
        valores.put(IdosoDao.CAMPO_CIDADE, idoso.getCidade());
        valores.put(IdosoDao.CAMPO_ESTADO, idoso.getEstado());
        valores.put(IdosoDao.CAMPO_COMPLEMENTO, idoso.getComplemento());

        long resultado = db.insert(IdosoDao.TABELA, null, valores);

        if(resultado != -1){
            idoso.setId(resultado);

            List<Alergia> alergias = idoso.getAlergias();
            if(alergias.size() > 0){
                for(Alergia alergia : alergias){
                    new AlergiaDao(this.context).criar(resultado, alergia);
                }
            }

            List<Medicamento> medicamentos = idoso.getMedicamentos();
            if(medicamentos.size() > 0){
                for(Medicamento medicamento : medicamentos){
                    new MedicamentoDao(this.context).criar(resultado, medicamento);
                }
            }

            List<ContatoEmergencia> contatosEmergencia = idoso.getContatosEmergencia();
            if(contatosEmergencia.size() > 0){
                for(ContatoEmergencia contatoEmergencia : contatosEmergencia){
                    new ContatoEmergenciaDao(this.context).criar(resultado, contatoEmergencia);
                }
            }
        }else{
            db.close();

            return null;
        }

        db.close();

        return idoso;
    }

    public Idoso obter(Long id){
        if(id < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }

        db = databaseHelper.getReadableDatabase();

        String[] campos = {
                IdosoDao.CAMPO_ID,
                IdosoDao.CAMPO_NOME,
                IdosoDao.CAMPO_DATANASCIMENTO,
                IdosoDao.CAMPO_GRUPOSANGUINEO,
                IdosoDao.CAMPO_ALTURA,
                IdosoDao.CAMPO_PESO,
                IdosoDao.CAMPO_LOGRADOURO,
                IdosoDao.CAMPO_NUMERO,
                IdosoDao.CAMPO_CEP,
                IdosoDao.CAMPO_BAIRRO,
                IdosoDao.CAMPO_CIDADE,
                IdosoDao.CAMPO_ESTADO,
                IdosoDao.CAMPO_COMPLEMENTO
        };

        String where = IdosoDao.CAMPO_ID + "=" + id;

        Cursor cursor = db.query(IdosoDao.TABELA, campos, where, null, null, null, null, null);

        Idoso idoso = new Idoso();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();

            idoso.setId(Long.parseLong(cursor.getString(0)));
            idoso.setNome(cursor.getString(1));
            idoso.setDataNascimento(cursor.getString(2));
            idoso.setGrupoSanguineo(cursor.getString(3));
            idoso.setAltura(Double.parseDouble(cursor.getString(4)));
            idoso.setPeso(Double.parseDouble(cursor.getString(5)));
            idoso.setLogradouro(cursor.getString(6));
            idoso.setNumero(cursor.getString(7));
            idoso.setCep(Integer.parseInt(cursor.getString(8)));
            idoso.setBairro(cursor.getString(9));
            idoso.setCidade(cursor.getString(10));
            idoso.setEstado(cursor.getString(11));
            idoso.setComplemento(cursor.getString(12));
            idoso.setAlergias(new AlergiaDao(context).obter(idoso));
            idoso.setMedicamentos(new MedicamentoDao(context).obter(idoso));
            idoso.setContatosEmergencia(new ContatoEmergenciaDao(context).obter(idoso));
        }

        cursor.close();
        db.close();

        return idoso;
    }

    public List<Idoso> obter(){
        Cursor cursor;
        String[] campos = {
                IdosoDao.CAMPO_ID,
                IdosoDao.CAMPO_NOME,
                IdosoDao.CAMPO_DATANASCIMENTO,
                IdosoDao.CAMPO_GRUPOSANGUINEO,
                IdosoDao.CAMPO_ALTURA,
                IdosoDao.CAMPO_PESO,
                IdosoDao.CAMPO_LOGRADOURO,
                IdosoDao.CAMPO_NUMERO,
                IdosoDao.CAMPO_CEP,
                IdosoDao.CAMPO_BAIRRO,
                IdosoDao.CAMPO_CIDADE,
                IdosoDao.CAMPO_ESTADO,
                IdosoDao.CAMPO_COMPLEMENTO
        };

        db = databaseHelper.getReadableDatabase();
        cursor = db.query(IdosoDao.TABELA, campos, null, null, null, null, null, null);

        List<Idoso> idosos = new ArrayList<Idoso>();

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                Idoso idoso = new Idoso();
                idoso.setId(Long.parseLong(cursor.getString(0)));
                idoso.setNome(cursor.getString(1));
                idoso.setDataNascimento(cursor.getString(2));
                idoso.setGrupoSanguineo(cursor.getString(3));
                idoso.setAltura(Double.parseDouble(cursor.getString(4)));
                idoso.setPeso(Double.parseDouble(cursor.getString(5)));
                idoso.setLogradouro(cursor.getString(6));
                idoso.setNumero(cursor.getString(7));
                idoso.setCep(Integer.parseInt(cursor.getString(8)));
                idoso.setBairro(cursor.getString(9));
                idoso.setCidade(cursor.getString(10));
                idoso.setEstado(cursor.getString(11));
                idoso.setComplemento(cursor.getString(12));
                idoso.setAlergias(new AlergiaDao(context).obter(idoso));
                idoso.setMedicamentos(new MedicamentoDao(context).obter(idoso));
                idoso.setContatosEmergencia(new ContatoEmergenciaDao(context).obter(idoso));

                idosos.add(idoso);
                cursor.moveToNext();
            }while(cursor.isLast());
        }

        cursor.close();
        db.close();
        return idosos;
    }

    public Idoso editar(Idoso idoso){
        if(idoso.isEmpty() || !idoso.isEntity()){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }
        if(validaIdoso(idoso.getId())){
            throw new Resources.NotFoundException("Não encontrou nenhum usuário com esse id!");
        }

        db = databaseHelper.getWritableDatabase();

        String where = IdosoDao.CAMPO_ID + "=" + idoso.getId();

        ContentValues valores = new ContentValues();
        valores.put(IdosoDao.CAMPO_NOME, idoso.getNome());
        valores.put(IdosoDao.CAMPO_DATANASCIMENTO, idoso.getDataNascimento());
        valores.put(IdosoDao.CAMPO_GRUPOSANGUINEO, idoso.getGrupoSanguineo());
        valores.put(IdosoDao.CAMPO_ALTURA, idoso.getAltura());
        valores.put(IdosoDao.CAMPO_PESO, idoso.getPeso());
        valores.put(IdosoDao.CAMPO_LOGRADOURO, idoso.getLogradouro());
        valores.put(IdosoDao.CAMPO_NUMERO, idoso.getNumero());
        valores.put(IdosoDao.CAMPO_CEP, idoso.getCep());
        valores.put(IdosoDao.CAMPO_BAIRRO, idoso.getBairro());
        valores.put(IdosoDao.CAMPO_CIDADE, idoso.getCidade());
        valores.put(IdosoDao.CAMPO_ESTADO, idoso.getEstado());
        valores.put(IdosoDao.CAMPO_COMPLEMENTO, idoso.getComplemento());

        long resultado = db.update(IdosoDao.TABELA, valores, where,null);

        db.close();

        if(resultado > 0){
            return idoso;
        }else{
            return null;
        }
    }

    public boolean excluir(Long id) {
        if(id < 0){
            throw new IllegalArgumentException("Argumento do parâmetro inválido: Necessário preencher todos os campos!");
        }
        if(validaIdoso(id)){
            throw new Resources.NotFoundException("Não encontrou nenhum usuário com esse id!");
        }

        db = databaseHelper.getReadableDatabase();

        String where = IdosoDao.CAMPO_ID + "=" + id;

        long resultado = db.delete(IdosoDao.TABELA,where,null);

        db.close();

        if(resultado > 0){
            return true;
        }else{
            return false;
        }
    }

    private boolean validaIdoso(Long id){
        Idoso idoso = obter(id);
        if(idoso == null){
            return true;
        }else{
            return false;
        }
    }
}

