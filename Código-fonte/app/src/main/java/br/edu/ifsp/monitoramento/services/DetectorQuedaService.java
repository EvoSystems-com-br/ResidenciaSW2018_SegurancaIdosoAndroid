package br.edu.ifsp.monitoramento.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import br.edu.ifsp.monitoramento.bussiness.DetectorQueda;
import br.edu.ifsp.monitoramento.bussiness.interfaces.IDetectorQuedaCallback;
import br.edu.ifsp.monitoramento.receivers.QuedaReceiver;

/**
 * @author Denis Magno
 */
public class DetectorQuedaService extends IntentService {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private DetectorQueda detector;

    private IDetectorQuedaCallback detectorQuedaCallback;

    public DetectorQuedaService() {
        super(DetectorQuedaService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.detectorQuedaCallback = new IDetectorQuedaCallback() {
            private final Intent broadCastIntent = new Intent();
            ;

            @Override
            public void onQuedaResult(Integer i) {
                this.broadCastIntent.setAction(QuedaReceiver.BROADCAST_QUEDA_ACTION);
                broadCastIntent.putExtra("result", i.toString());
                sendBroadcast(broadCastIntent);
            }
        };

        this.detector = new DetectorQueda(this, detectorQuedaCallback);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);

        return Service.START_STICKY;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (this.detector.iniciar()) {
            Toast.makeText(this, "Serviço \"Detecção de Queda\" iniciado!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Serviço \"Detecção de Queda\" iniciado!");
        } else {
            Toast.makeText(this, "Erro ao iniciar serviço de \"Detecção de Queda\"!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao iniciar serviço de \"Detecção de Queda\"!");
        }
    }

    @Override
    public void onDestroy() {
        if (this.detector.parar()) {
            Toast.makeText(this, "Serviço \"Detecção de Queda\" parado!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Serviço \"Detecção de Queda\" parado!");
        } else {
            Toast.makeText(this, "Erro ao parar serviço de \"Detecção de Queda\"!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao parar serviço de \"Detecção de Queda\"!");
        }
    }
}
