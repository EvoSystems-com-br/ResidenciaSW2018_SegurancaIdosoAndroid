package com.scopus.ifsp.projetofinalteste.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Idoso;
import com.scopus.ifsp.projetofinalteste.model.IdosoDao;

/**
 * @author Denis Magno
 */
public class EnvioMensagemService extends Service {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    public static final int ENVIO_MENSAGEM_NAO_ESTA_BEM = 1;
    public static final int ENVIO_MENSAGEM_NAO_RESPONDE = 2;

    private Idoso idoso;

    private SmsManager smsManager;

    //Componentes BD
    private IdosoDao idosoDao;

    @Override
    public void onCreate() {
        this.smsManager = SmsManager.getDefault();
        iniciarComponentesBD();
        this.idoso = this.idosoDao.load((long) 1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        enviarMensagem(1);
        return Service.START_STICKY;
    }

    private void iniciarComponentesBD() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        this.idosoDao = daoSession.getIdosoDao();
    }

    /**
     * Envia mensagem
     *
     * @author Denis Magno
     */
    private void enviarMensagem(int tipo) {
        String texto = "";

        switch (tipo) {
            case ENVIO_MENSAGEM_NAO_ESTA_BEM:
                texto = "Foi detectado uma queda e idoso informou que não está bem!";
                break;
            case ENVIO_MENSAGEM_NAO_RESPONDE:
                texto = "Foi detectado uma queda e idoso não respondeu!";
                break;
        }

        try {
            smsManager.sendTextMessage("55" + this.idoso.getContatosEmergencia().get(0).getNumero(), null, texto, null, null);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao enviar mensagem: " + e.getMessage());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}