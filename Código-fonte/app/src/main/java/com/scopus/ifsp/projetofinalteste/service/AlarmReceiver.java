package com.scopus.ifsp.projetofinalteste.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.model.Alarme;
import com.scopus.ifsp.projetofinalteste.model.AlarmeDao;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTI_PRIMARY1 = 100;
    private NotificationHelper noti;

    private int ALARM_REQUEST_CODE = 10000;
    String TAG = "AlarmReceiver.class";

    @Override
    public void onReceive(Context context, Intent intent) {

        int idAlarme = 0;

        Bundle bundle = intent.getExtras();

        idAlarme = bundle.getInt("idAlarme");


        noti = new NotificationHelper(context);
        Notification.Builder nb = null;
        String title = "";
        String nome = "";
        String quantidade = "";
        String ultimaData = "";
        String horario = "";
        int ultimaDt_ativo = 0;

        if (idAlarme != 0){

            //Componentes BD
            DaoSession daoSession = ((App) context).getDaoSession();
            AlarmeDao alarmeDao = daoSession.getAlarmeDao();

            Alarme alarme = alarmeDao.load((long)idAlarme);
            nome = alarme.getRemedio().getNome_remedio();
            quantidade = alarme.getRemedio().getQuantidade();
            ultimaData = alarme.getUltima_data();
            horario = alarme.getTime();
            ultimaDt_ativo = alarme.getAtivar_ultima_data();

            if (ultimaDt_ativo == 1){

                assert ultimaData != null;
                if (verificaData(ultimaData, horario)){
                    title = "ULTIMO DIA!!!";

                    nb = noti.getNotification1(title, "Hora de tomar o remédio: " + nome + " \n " + "Quantidade: " + quantidade, idAlarme);
                    if (nb != null) {
                        noti.notify(NOTI_PRIMARY1 + idAlarme, nb);
                    }


                    try{

                        Intent alarmIntent = new Intent(context,AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE + idAlarme, alarmIntent, 0);
                        new AlarmScheduler().cancelOnlyAlarm(context, pendingIntent,idAlarme);

                    } finally {
                        alarme.setActive("true");
                        alarmeDao.insert(alarme);
                    }
                    Toast.makeText(context,"Último dia, \n Rémedio:" + nome + " \n Quantidade: " + quantidade, Toast.LENGTH_LONG).show();
                }
            }else {

                title = "Hora de Tomar remédio !!";
                nb = noti.getNotification1(title, "Remédio: " + nome + " \n " + "Quantidade: " + quantidade, idAlarme);
                if (nb != null) {
                    noti.notify(NOTI_PRIMARY1 + idAlarme, nb);
                }
            }
        } else {
            title = "Erro!!!";

            nb = noti.getNotification1(title, "Erro ao pegar dados !!", idAlarme);

            if (nb != null) {
                noti.notify(NOTI_PRIMARY1 + idAlarme, nb);
            }
        }

    }

    private boolean verificaData(String data, String Horario){
        Calendar mCalendar = Calendar.getInstance();
        int HojeMês, HojeAno, HojeDia, HojeMin, HojeHora;

        HojeMin = mCalendar.get(Calendar.MINUTE);
        HojeHora = mCalendar.get(Calendar.HOUR_OF_DAY);
        HojeAno = mCalendar.get(Calendar.YEAR);
        HojeMês = mCalendar.get(Calendar.MONTH)+1;
        HojeDia = mCalendar.get(Calendar.DAY_OF_MONTH);

        String diaDeHoje = verifica_data(HojeDia) + "/" + verifica_mes(HojeMês) + "/" + Integer.toString(HojeAno);
        String horario_hoje = verifica_Hora(HojeHora) + ":" + verifica_Minuto(HojeMin);

        if (data.equals(diaDeHoje) && Horario.equals(horario_hoje)){
            return true;
        } else {
            return false;
        }
    }


    public String verifica_Hora(int mHora) {
        if (mHora < 10) {
            return "0" + mHora;
        } else {
            return Integer.toString(mHora);
        }
    }


    public String verifica_Minuto(int mMinute) {
        if (mMinute < 10) {
            return "0" + mMinute;
        } else {
            return Integer.toString(mMinute);
        }
    }

    public String verifica_data(int mDate) {
        if (mDate < 10) {
            return "0" + Integer.toString(mDate);
        } else {
            return Integer.toString(mDate);
        }
    }

    public String verifica_mes(int mMes) {
        if (mMes < 10) {
            return "0" + mMes;
        } else {
            return Integer.toString(mMes);
        }
    }

}
