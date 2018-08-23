package com.ifsp.detectorqueda.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ifsp.detectorqueda.dao.AlergiaDao;
import com.ifsp.detectorqueda.dao.ContatoEmergenciaDao;
import com.ifsp.detectorqueda.dao.IdosoDao;
import com.ifsp.detectorqueda.dao.MedicamentoDao;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "monitoramento";
    private static final int VERSAO = 1;

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.NOME_BANCO, null, DatabaseHelper.VERSAO);
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if (instance == null){
            instance = new DatabaseHelper(context.getApplicationContext());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IdosoEntity.CREATE_TABLE);
        db.execSQL(MedicamentoEntity.CREATE_TABLE);
        db.execSQL(AlergiaEntity.CREATE_TABLE);
        db.execSQL(ContatoEmergenciaEntity.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(IdosoEntity.DROP_TABLE);
        db.execSQL(MedicamentoEntity.DROP_TABLE);
        db.execSQL(AlergiaEntity.DROP_TABLE);
        db.execSQL(ContatoEmergenciaEntity.DROP_TABLE);

        onCreate(db);
    }

    private static class IdosoEntity {
        public static final String CREATE_TABLE = "CREATE TABLE "+ IdosoDao.TABELA+"(" +
                IdosoDao.CAMPO_ID+" integer primary key autoincrement," +
                IdosoDao.CAMPO_NOME+" varchar(150)," +
                IdosoDao.CAMPO_DATANASCIMENTO+" varchar(10)," +
                IdosoDao.CAMPO_GRUPOSANGUINEO+" varchar(3)," +
                IdosoDao.CAMPO_ALTURA+" decimal(3,2)," +
                IdosoDao.CAMPO_PESO+" decimal(5,2)," +
                IdosoDao.CAMPO_LOGRADOURO+" varchar(150)," +
                IdosoDao.CAMPO_NUMERO+" varchar(15)," +
                IdosoDao.CAMPO_CEP+" int," +
                IdosoDao.CAMPO_BAIRRO+" varchar(100)," +
                IdosoDao.CAMPO_CIDADE+" varchar(100)," +
                IdosoDao.CAMPO_ESTADO+" varchar(100)," +
                IdosoDao.CAMPO_COMPLEMENTO+" varchar(50)" + ")";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ IdosoDao.TABELA;
    }

    private static class MedicamentoEntity {
        public static final String CREATE_TABLE = "CREATE TABLE "+ MedicamentoDao.TABELA+"(" +
                MedicamentoDao.CAMPO_ID+" integer primary key autoincrement," +
                MedicamentoDao.CAMPO_NOME+" varchar(150)," +
                MedicamentoDao.CAMPO_IDIDOSO+" integer," +
                "FOREIGN KEY ("+MedicamentoDao.CAMPO_IDIDOSO+") REFERENCES "+IdosoDao.TABELA+"("+IdosoDao.CAMPO_ID+")" + ")";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ MedicamentoDao.TABELA;
    }

    private static class AlergiaEntity {
        public static final String CREATE_TABLE = "CREATE TABLE "+ AlergiaDao.TABELA+"(" +
                AlergiaDao.CAMPO_ID+" integer primary key autoincrement," +
                AlergiaDao.CAMPO_DESCRICAO+" varchar(150)," +
                AlergiaDao.CAMPO_IDIDOSO+" integer," +
                "FOREIGN KEY ("+MedicamentoDao.CAMPO_IDIDOSO+") REFERENCES "+IdosoDao.TABELA+"("+IdosoDao.CAMPO_ID+")" + ")";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ AlergiaDao.TABELA;
    }

    private static class ContatoEmergenciaEntity {
        public static final String CREATE_TABLE = "CREATE TABLE "+ ContatoEmergenciaDao.TABELA+"(" +
                ContatoEmergenciaDao.CAMPO_ID+" integer primary key autoincrement," +
                ContatoEmergenciaDao.CAMPO_NOME+" varchar(150)," +
                ContatoEmergenciaDao.CAMPO_NUMERO+" integer," +
                ContatoEmergenciaDao.CAMPO_PARENTESCO+" varchar(50)," +
                ContatoEmergenciaDao.CAMPO_IDIDOSO+" integer," +
                "FOREIGN KEY ("+MedicamentoDao.CAMPO_IDIDOSO+") REFERENCES "+IdosoDao.TABELA+"("+IdosoDao.CAMPO_ID+")" + ")";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ ContatoEmergenciaDao.TABELA;
    }
}