package br.edu.ifsp.monitoramento.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Denis Magno
 */
public class Haversine {
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public static double distance(LatLng startLocation, LatLng endLocation) {

        double dLat = Math.toRadians((endLocation.latitude - startLocation.latitude));
        double dLong = Math.toRadians((endLocation.longitude - startLocation.longitude));

        double startLat = Math.toRadians(startLocation.latitude);
        double endLat = Math.toRadians(endLocation.latitude);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}