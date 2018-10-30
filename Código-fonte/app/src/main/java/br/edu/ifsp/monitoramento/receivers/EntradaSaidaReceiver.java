package br.edu.ifsp.monitoramento.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import br.edu.ifsp.monitoramento.App;
import br.edu.ifsp.monitoramento.activities.MainActivity;
import br.edu.ifsp.monitoramento.bussiness.Localizacao;
import br.edu.ifsp.monitoramento.models.Dado;
import br.edu.ifsp.monitoramento.models.DadoDao;
import br.edu.ifsp.monitoramento.models.DaoSession;
import br.edu.ifsp.monitoramento.services.DetectorQuedaService;
import br.edu.ifsp.monitoramento.utils.Service;

/**
 * @author Denis Magno
 */
public class EntradaSaidaReceiver extends BroadcastReceiver {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    public static final String BROADCAST_LOCATION_ACTION = EntradaSaidaReceiver.class.getName();

    private MainActivity main;

    private Intent servicoDeteccaoQueda;

    //Componentes BD
    private DadoDao dadoDao;

    public EntradaSaidaReceiver(Activity act) {
        this.main = (MainActivity) act;

        this.servicoDeteccaoQueda = new Intent(act, DetectorQuedaService.class);

        iniciarComponentesBD(act);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String latitude = intent.getStringExtra("latitude");
        main.txtLatitude.setText(latitude + " ;");

        String longitude = intent.getStringExtra("longitude");
        main.txtLongitude.setText(longitude + " ;");

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