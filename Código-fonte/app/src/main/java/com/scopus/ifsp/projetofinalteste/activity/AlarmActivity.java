package com.scopus.ifsp.projetofinalteste.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.adapter.AlarmAdapter;
import com.scopus.ifsp.projetofinalteste.model.AlarmeDao;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Remedio;

public class AlarmActivity extends AppCompatActivity {

    String TAG = "AlarmActivity.class";
    AlarmAdapter adapter;
    RecyclerView recyclerView;
    Remedio remedio;
    int idRemedio = 0;
    Button btConcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        final Intent intent = getIntent();

        android.support.design.widget.FloatingActionButton fabAlarm = findViewById(R.id.fab_alarm);
        fabAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.hasExtra("remedio")){
                    Intent alarmeSet = new Intent(AlarmActivity.this, AddReminderActivity.class);
                    Remedio remedio = (Remedio) intent.getSerializableExtra("remedio");
                    alarmeSet.putExtra("remedioAlarme", remedio);
                    startActivity(alarmeSet);
                }
            }
        });

        btConcluir = findViewById(R.id.btConcluirAdd);
        btConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (intent.hasExtra("remedio")){
            try{

                remedio = (Remedio) intent.getSerializableExtra("remedio");
                idRemedio = (int) (long)remedio.getId();

                //Componentes BD
                DaoSession daoSession = ((App) this.getApplicationContext()).getDaoSession();
                AlarmeDao alarmeDao = daoSession.getAlarmeDao();

                adapter = new AlarmAdapter(this,alarmeDao._queryRemedio_Alarmes((long)idRemedio));
                configurarRecycler(adapter);
            }catch (Exception e){
                Log.i(TAG, "onCreate: " + e);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_lista_alarme, menu);
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
            // Respond to a click on the "Save" menu option
            case R.id.listaAlarm_item:
                    Intent intent = new Intent(this,AlarmListaCompletaActivity.class);
                    intent.putExtra("remedio",remedio);
                    startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void configurarRecycler(AlarmAdapter alarmAdapter){
        //configurando o gerenciador de Layout para ser uma lista
        recyclerView = findViewById(R.id.listView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(alarmAdapter);

    }
}
