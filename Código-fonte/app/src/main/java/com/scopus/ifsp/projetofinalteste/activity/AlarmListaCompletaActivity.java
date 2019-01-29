package com.scopus.ifsp.projetofinalteste.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.adapter.AlarmAdapter;
import com.scopus.ifsp.projetofinalteste.model.AlarmeDao;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Remedio;

public class AlarmListaCompletaActivity extends AppCompatActivity {

    String TAG = "AlarmListaCompletaActivity.class";
    AlarmAdapter adapter;
    RecyclerView recyclerView;
    Remedio remedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_lista_completa);

        Intent intent = getIntent();
        if (intent.hasExtra("remedio")){
            remedio = (Remedio) intent.getSerializableExtra("remedio");
        } else {
            Log.i(TAG, "onCreate: Erro ao receber dados");
        }

        //Componentes BD
        DaoSession daoSession = ((App) this.getApplicationContext()).getDaoSession();
        AlarmeDao alarmeDao = daoSession.getAlarmeDao();

        adapter = new AlarmAdapter(this, alarmeDao.loadAll());
        configurarRecycler(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_listacompleta_alarme, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.listaAlarm_VoltarItem:
                Intent intent = new Intent(this, AlarmActivity.class);
                intent.putExtra("remedio", remedio);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void configurarRecycler(AlarmAdapter alarmAdapter){
        //configurando o gerenciador de Layout para ser uma lista
        recyclerView = findViewById(R.id.RecyclerViewCompleta);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(alarmAdapter);

    }

}
