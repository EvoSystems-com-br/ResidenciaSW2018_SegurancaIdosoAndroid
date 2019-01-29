package com.scopus.ifsp.projetofinalteste.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.activity.MapaActivity;
import com.scopus.ifsp.projetofinalteste.bussiness.Localizacao;
import com.scopus.ifsp.projetofinalteste.model.Dado;
import com.scopus.ifsp.projetofinalteste.model.DadoDao;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.service.DetectorQuedaService;
import com.scopus.ifsp.projetofinalteste.util.Service;

import java.util.Date;

/**
 * @author Denis Magno
 */
public class EntradaSaidaReceiver extends BroadcastReceiver {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    public static final String BROADCAST_LOCATION_ACTION = EntradaSaidaReceiver.class.getName();

    private MapaActivity main;

    private Intent servicoDeteccaoQueda;

    //Componentes BD
    private DadoDao dadoDao;

    public EntradaSaidaReceiver(Activity act) {
        this.main = (MapaActivity) act;

        this.servicoDeteccaoQueda = new Intent(act, DetectorQuedaService.class);

        iniciarComponentesBD(act);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String latitude = intent.getStringExtra("latitude");
        main.txtLatitude.setText(latitude + "; ");

        String longitude = intent.getStringExtra("longitude");
        main.txtLongitude.setText(longitude + "; ");

        LatLng localAtual = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        Double distancia = Localizacao.isInside(main.homeMarker.getPosition(), localAtual);
        main.txtDistancia.setText(String.format("%.2f metros", distancia));

        if (distancia < 20) {
            if (Service.isServiceRunning(context, DetectorQuedaService.class.getName())) {
                context.stopService(this.servicoDeteccaoQueda);
            }

            main.txtStatus.setText("Usuário está dentro de sua residência!");
            this.cadastrarEntrada(context);
        } else {
            if (!Service.isServiceRunning(context, DetectorQuedaService.class.getName())) {
                context.startService(this.servicoDeteccaoQueda);
            }

            main.txtStatus.setText("Usuário está fora de sua residência!");
            this.cadastrarSaida(context);
        }
    }

    private void cadastrarEntrada(Context context) {
        if (main.dados.isEmpty() || (main.dados.get(0).getStatus() != Dado.EnumStatus.ENTROU.getValor())) {
            Dado dado = new Dado();
            dado.setIdosoId((long) 1);
            dado.setDatetime(new Date());
            dado.setStatus(Dado.EnumStatus.ENTROU.getValor());

            dado.setId(dadoDao.insert(dado));

            if (dado.getId() != 0) {
                main.dados.add(0, dado);
                main.adapterHistorico.notifyDataSetChanged();
            } else {
                Log.e(TAG, "Não criou entrada");
            }
        }
    }

    private void cadastrarSaida(Context context) {
        if (main.dados.isEmpty() || (main.dados.get(0).getStatus() != Dado.EnumStatus.SAIU.getValor())) {
            Dado dado = new Dado();
            dado.setIdosoId((long) 1);
            dado.setDatetime(new Date());
            dado.setStatus(Dado.EnumStatus.SAIU.getValor());

            Long result = dadoDao.insert(dado);

            if (result != 0) {
                main.dados.add(0, dado);
                main.adapterHistorico.notifyDataSetChanged();
            } else {
                Log.e(TAG, "Não criou saída");
            }
        }
    }

    public void iniciarComponentesBD(Activity act) {
        DaoSession daoSession = ((App) act.getApplicationContext()).getDaoSession();

        this.dadoDao = daoSession.getDadoDao();
    }
}