package com.ifsp.detectorqueda.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;

import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.dao.IdosoDao;

public class EnvioMensagemService extends Service {
    public static final int ENVIO_MENSAGEM_NAO_ESTA_BEM = 1;
    public static final int ENVIO_MENSAGEM_NAO_RESPONDE = 2;

    private Idoso idoso;

    private SmsManager smsManager;

    @Override
    public void onCreate() {
        this.smsManager = SmsManager.getDefault();
        this.idoso = new IdosoDao(getBaseContext()).obter((long) 1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        enviarMensagem(1);
        return Service.START_STICKY;
    }

    /**
     * Envia mensagem
     *
     * @author Denis Magno
     */
    private void enviarMensagem(int tipo){
        String texto = "";

        switch(tipo){
            case ENVIO_MENSAGEM_NAO_ESTA_BEM:
                texto = "Foi detectado uma queda e idoso informou que não está bem!";
                break;
            case ENVIO_MENSAGEM_NAO_RESPONDE:
                texto = "Foi detectado uma queda e idoso não respondeu!";
                break;
        }

        try {
            smsManager.sendTextMessage("55"+this.idoso.getContatosEmergencia().get(0).getNumero(), null, texto, null, null);
        }catch(Exception e){
            Log.e("Erro ao enviar mensagem", e.getMessage());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }
}
