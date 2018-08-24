package com.ifsp.detectorqueda.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.dao.IdosoDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnIniciarDeteccao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentesGraficos();
        iniciarEventos();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(verificarPrimeiroAcesso()){
            Intent intent = new Intent(MainActivity.this, CadastroGeralActivity.class);
            startActivity(intent);
        }
    }

    public void iniciarComponentesGraficos(){
        this.btnIniciarDeteccao = (Button) findViewById(R.id.btnIniciarDeteccao);
    }

    public void iniciarEventos(){
        this.btnIniciarDeteccao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, DetectorActivity.class);
                startActivity(in);
            }
        });
    }

    private boolean verificarPrimeiroAcesso(){
        List<Idoso> idosos = new IdosoDao(this).obter();
        if(idosos.size() > 0){
            return false;
        }else{
            return true;
        }
    }
}
