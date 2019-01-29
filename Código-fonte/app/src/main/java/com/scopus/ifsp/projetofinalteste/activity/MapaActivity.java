package com.scopus.ifsp.projetofinalteste.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.adapter.HistoricoListAdapter;
import com.scopus.ifsp.projetofinalteste.model.Dado;
import com.scopus.ifsp.projetofinalteste.model.DadoDao;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Idoso;
import com.scopus.ifsp.projetofinalteste.model.IdosoDao;
import com.scopus.ifsp.projetofinalteste.receiver.EntradaSaidaReceiver;
import com.scopus.ifsp.projetofinalteste.receiver.QuedaReceiver;
import com.scopus.ifsp.projetofinalteste.service.LocalizacaoService;
import com.scopus.ifsp.projetofinalteste.util.Permission;
import com.scopus.ifsp.projetofinalteste.util.Service;

import java.util.ArrayList;
import java.util.List;

public class MapaActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private Idoso idoso;
    //Constantes
    public static final int MY_PERMISSIONS_ALL = 1;
    public static final int MY_REQUEST_CODE_CADASTRO_SETTINGS = 2;
    public static final int MY_REQUEST_CODE_LOCATION_SETTINGS = 3;

    //ComponentesGráficos
    public TextView txtStatus;
    public TextView txtLatitude;
    public TextView txtLongitude;
    public TextView txtDistancia;
    public ListView ltvHistorico;

    public List<Dado> dados;
    public HistoricoListAdapter adapterHistorico;

    //Componentes BD
    private IdosoDao idosoDao;
    private DadoDao dadoDao;

    //Google Maps API
    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    public MarkerOptions homeMarker;

    //IntentServices
    private Intent servicoLocalizacao;

    //BroadcastReceivers
    private EntradaSaidaReceiver entradaSaidaReceiver;
    private QuedaReceiver quedaReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        iniciarComponentesBD();
        iniciarComponentesGraficos();
        iniciarEventos();
        solicitarPermissoes();
        registrarReceivers();

        if (!isLocationEnabled()) {
            ativarLocation();
        }

        if (!isLocationRunning()) {
            iniciarServicoLocalizacao();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void iniciarComponentesGraficos() {
        this.txtStatus = findViewById(R.id.txtStatus);
        this.txtLatitude = findViewById(R.id.txtLatitude);
        this.txtLongitude = findViewById(R.id.txtLongitude);
        this.txtDistancia = findViewById(R.id.txtDistancia);
        this.ltvHistorico = findViewById(R.id.ltvHistorico);
    }

    private void iniciarEventos() {
        Idoso idoso = this.idosoDao.load((long) 1);

        if (idoso != null) {
            this.dados = this.dadoDao.queryBuilder().where(DadoDao.Properties.IdosoId.eq(idoso.getId())).orderDesc(DadoDao.Properties.Id).list();
        } else {
            this.dados = new ArrayList<Dado>();
        }

        this.adapterHistorico = new HistoricoListAdapter(this.dados, this);
        this.ltvHistorico.setAdapter(this.adapterHistorico);
        this.ltvHistorico.setDivider(getDrawable(R.drawable.divider_list_cadastro));
        this.ltvHistorico.setDividerHeight(1);

        this.mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        this.mapFragment.getMapAsync(this);
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this) //Be aware of state of the connection
                .addOnConnectionFailedListener(this) //Be aware of failures
                .addApi(LocationServices.API)
                .build();

        //Tentando conexão com o Google API. Se a tentativa for bem sucessidade, o método onConnected() será chamado, senão, o método onConnectionFailed() será chamado.
        this.googleApiClient.connect();

        this.entradaSaidaReceiver = new EntradaSaidaReceiver(this);
        this.quedaReceiver = new QuedaReceiver();
    }

    private void iniciarComponentesBD() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        this.idosoDao = daoSession.getIdosoDao();
        this.dadoDao = daoSession.getDadoDao();
    }

    private void solicitarPermissoes() {
        String[] permissoes = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.SEND_SMS
        };

        if (!Permission.hasPermissions(this, permissoes)) {
            ActivityCompat.requestPermissions(this, permissoes, MapaActivity.MY_PERMISSIONS_ALL);
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLocationRunning() {
        if (Service.isServiceRunning(this, LocalizacaoService.class.getName())) {
            return true;
        } else {
            return false;
        }
    }

    private void ativarLocation() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(callGPSSettingIntent, MY_REQUEST_CODE_LOCATION_SETTINGS);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog mNoGpsDialog = builder.setMessage(R.string.main_alert_dialog_localizacao_txtTitulo)
                .setPositiveButton(R.string.main_alert_dialog_localizacao_btnPositivo, dialogClickListener)
                .setNegativeButton(R.string.main_alert_dialog_localizacao_btnNegativo, dialogClickListener)
                .create();
        mNoGpsDialog.show();
    }

    private void marcarLocalSeguro(LatLng latLng) {
        GoogleMapOptions options = new GoogleMapOptions();

        this.mMap.clear();

        this.homeMarker.position(latLng);
        this.homeMarker.title("Local seguro");
        this.mMap.addMarker(this.homeMarker);

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(15);

        this.mMap.addCircle(circleOptions);

        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ALL:
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        solicitarPermissoes();
                    }
                }
                break;

        }
    }

    private void iniciarServicoLocalizacao() {
        this.servicoLocalizacao = new Intent(this, LocalizacaoService.class);
        startService(servicoLocalizacao);
    }

    private void registrarReceivers() {
        IntentFilter entradaSaidaFilter = new IntentFilter();
        entradaSaidaFilter.addAction(EntradaSaidaReceiver.BROADCAST_LOCATION_ACTION);
        registerReceiver(this.entradaSaidaReceiver, entradaSaidaFilter);

        IntentFilter quedaFilter = new IntentFilter();
        quedaFilter.addAction(QuedaReceiver.BROADCAST_QUEDA_ACTION);
        registerReceiver(this.quedaReceiver, quedaFilter);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.homeMarker = new MarkerOptions();

        this.mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marcarLocalSeguro(latLng);
            }
        });

        LatLng latLng = new LatLng(-23.43907555, -46.53751691);
        marcarLocalSeguro(latLng);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isLocationRunning()) {
            stopService(servicoLocalizacao);
        }

        unregisterReceiver(this.entradaSaidaReceiver);
        unregisterReceiver(this.quedaReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MY_REQUEST_CODE_LOCATION_SETTINGS:
                if (isLocationEnabled()) {
                    Log.e(TAG, "Location Provider - Ativou!");
                } else {
                    Log.e(TAG, "Location Provider - Não ativou!");
                }
                break;

            case MY_REQUEST_CODE_CADASTRO_SETTINGS:
                if (resultCode == RESULT_OK) {
                    Log.e(TAG, "Cadastro Provider - Cadastrou!");

                    if (!isLocationEnabled()) {
                        ativarLocation();
                    }

                    if (!isLocationRunning()) {
                        iniciarServicoLocalizacao();
                    }
                }
                break;
        }
    }
}