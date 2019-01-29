package com.scopus.ifsp.projetofinalteste.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Idoso;
import com.scopus.ifsp.projetofinalteste.model.IdosoDao;

/**
 * @author Denis Magno
 */
public class EnvioMensagemWhatsappService extends Service {
    private Idoso idoso;

    //Componentes BD
    private IdosoDao idosoDao;

    @Override
    public void onCreate() {
        this.iniciarComponentesBD();
        this.idoso = this.idosoDao.load((long) 1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        enviarMensagem(EnvioMensagemService.ENVIO_MENSAGEM_NAO_ESTA_BEM);
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void iniciarComponentesBD() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        this.idosoDao = daoSession.getIdosoDao();
    }

    private void enviarMensagem(int tipo) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("text/plain");
        sendIntent.putExtra("jid", "55" + (idoso.getContatosEmergencia().get(0).getNumero()) + "@s.whatsapp.net");

        switch (tipo) {
            case EnvioMensagemService.ENVIO_MENSAGEM_NAO_ESTA_BEM:
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Foi detectado uma queda e idoso informou que não está bem!");
                break;
            case EnvioMensagemService.ENVIO_MENSAGEM_NAO_RESPONDE:
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Foi detectado uma queda e idoso não respondeu!");
                break;
        }

        startActivity(Intent.createChooser(sendIntent, "Share with"));
    }
}