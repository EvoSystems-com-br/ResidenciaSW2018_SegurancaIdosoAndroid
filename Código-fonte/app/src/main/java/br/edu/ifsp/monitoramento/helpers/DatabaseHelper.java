package br.edu.ifsp.monitoramento.helpers;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import br.edu.ifsp.monitoramento.models.DaoMaster;
import br.edu.ifsp.monitoramento.models.DaoSession;

/**
 * @author Denis Magno
 */
public class DatabaseHelper {

    public DaoSession daoSession;

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "monitoramento", null);
        Database db = helper.getWritableDb();

        this.daoSession = new DaoMaster(db).newSession();
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);

        return instance;
    }
}