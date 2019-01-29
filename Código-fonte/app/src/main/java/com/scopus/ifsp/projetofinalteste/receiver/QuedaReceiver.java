package com.scopus.ifsp.projetofinalteste.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.scopus.ifsp.projetofinalteste.activity.AlertaQuedaActivity;
import com.scopus.ifsp.projetofinalteste.bussiness.DetectorQueda;
import com.scopus.ifsp.projetofinalteste.helper.LogHelper;


/**
 * @author Denis Magno
 */
public class QuedaReceiver extends BroadcastReceiver {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    public static final String BROADCAST_QUEDA_ACTION = QuedaReceiver.class.getName();

    private int i = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Integer result = Integer.parseInt(intent.getStringExtra("result"));

        if (result == DetectorQueda.RESULT_QUEDA_POSITIVO) {
            new LogHelper().cadastrarQueda(context);
            Toast.makeText(context, "Queda detectada", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Queda detectada!");

            Intent in = new Intent(context, AlertaQuedaActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);
        } else if (result == DetectorQueda.RESULT_QUEDA_JANELA_INVALIDA) {
            Log.e(TAG, "Tamanho da janela ainda não é o ideal para detecção! " + i);
            i++;
        }
    }
}