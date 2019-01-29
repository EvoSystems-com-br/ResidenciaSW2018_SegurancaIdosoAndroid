package com.scopus.ifsp.projetofinalteste.bussiness;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.scopus.ifsp.projetofinalteste.util.Haversine;

/**
 * @author Denis Magno
 */
public class Localizacao {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private FusedLocationProviderClient providerClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    public Localizacao(Context context, LocationCallback locationCallback) {
        providerClient = LocationServices.getFusedLocationProviderClient(context);

        this.locationCallback = locationCallback;

        this.locationRequest = LocationRequest.create();
        this.locationRequest.setInterval(5000);
        this.locationRequest.setFastestInterval(500);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startLocationUpdates() {
        Log.e(TAG, "Iniciando locationUpdates...");

        try {
            providerClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException e) {
            throw new RuntimeException("Problema de segurança");
        }
    }

    public static Double isInside(LatLng localSeguro, LatLng localAtual/*, Double raio*/) {

        Double resultado = Haversine.distance(localSeguro, localAtual);

        //Converte em metros
        resultado = resultado * 1000;

        return resultado;
    }

    public void stopLocationUpdates() {
        Log.e(TAG, "Parando locationUpdates");
        this.providerClient.removeLocationUpdates(this.locationCallback);
    }
}