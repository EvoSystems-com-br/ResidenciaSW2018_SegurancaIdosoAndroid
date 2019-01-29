package com.scopus.ifsp.projetofinalteste.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.scopus.ifsp.projetofinalteste.bussiness.Localizacao;
import com.scopus.ifsp.projetofinalteste.receiver.EntradaSaidaReceiver;

/**
 * @author Denis Magno
 */
public class LocalizacaoService extends IntentService {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private Localizacao localizacao;

    private LocationCallback locationCallback;

    public LocalizacaoService() {
        super(LocalizacaoService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Iniciando serviço...");

        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.e(TAG, "LocationResult = null");
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    Intent broadCastIntent = new Intent();
                    broadCastIntent.setAction(EntradaSaidaReceiver.BROADCAST_LOCATION_ACTION);

                    broadCastIntent.putExtra("latitude", "" + location.getLatitude());
                    broadCastIntent.putExtra("longitude", "" + location.getLongitude());

                    sendBroadcast(broadCastIntent);
                }
            }
        };

        this.localizacao = new Localizacao(this, this.locationCallback);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "Serviço \"Localização\" iniciado!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Serviço iniciado");

        onHandleIntent(intent);

        return Service.START_STICKY;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "Iniciando onHandleIntent...");
        this.localizacao.startLocationUpdates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Serviço \"Localização\" parado!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Serviço parado");
        this.localizacao.stopLocationUpdates();
    }
}