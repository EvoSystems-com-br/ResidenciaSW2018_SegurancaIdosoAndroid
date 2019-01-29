package com.scopus.ifsp.projetofinalteste.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.model.Remedio;

public class InfoRemedioActivity extends AppCompatActivity {
    TextView tvNomeFarmaceutico,tvInstituicao,tvRp,tvCpf,tvData,tvEndereco;
    TextView tvNomeRemedio,tvConcentracao,tvFormaFarmaceutica,tvquantidade,tvfrequencia, tvorientacaoextra;

    Remedio remediosEditado = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_remedio);

        //Objetos do content_info
        tvNomeFarmaceutico = findViewById(R.id.tvNomeFarmaceutico);
        tvInstituicao = findViewById(R.id.tvInstituicaoValue);
        tvEndereco = findViewById(R.id.tvEndereco);
        tvRp = findViewById(R.id.tvRpValue);
        tvCpf = findViewById(R.id.tvCpfValue);
        tvData = findViewById(R.id.tvDataValue);
        tvNomeRemedio = findViewById(R.id.tvNomeRemedioValue);
        tvConcentracao = findViewById(R.id.tvConcentracaoValue);
        tvFormaFarmaceutica = findViewById(R.id.tvFormaFarmaceuticaValue);
        tvquantidade = findViewById(R.id.tvQuantidadeValue);
        tvfrequencia = findViewById(R.id.tvFrequenciaValue);
        tvorientacaoextra = findViewById(R.id.tvOrientacaoExtra);

        Intent intent = getIntent();

        if (intent.hasExtra("remedio")){


            remediosEditado = (Remedio) intent.getSerializableExtra("remedio");

            //Preenchimento dos dados no content_info
            tvNomeRemedio.setText(remediosEditado.getNome_remedio());
            tvquantidade.setText(remediosEditado.getQuantidade());
            tvfrequencia.setText(remediosEditado.getFrequencia());
            tvConcentracao.setText(remediosEditado.getConcentracao());
            tvFormaFarmaceutica.setText(remediosEditado.getForma_farmaceutica());
            tvNomeFarmaceutico.setText((remediosEditado.getNome_farmaceutico()));
            tvInstituicao.setText(remediosEditado.getInstituicao());
            tvEndereco.setText(remediosEditado.getEndereco());
            tvRp.setText(remediosEditado.getRP());
            tvCpf.setText(remediosEditado.getCpf_cnpj());
            tvData.setText(remediosEditado.getData_pedido());
            tvorientacaoextra.setText(remediosEditado.getOrientacao_extra());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_remedio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_remedio:
                Intent intentRemedio = new Intent(this,MainActivity.class);
                intentRemedio.putExtra("remedio",remediosEditado);
                startActivity(intentRemedio);
                finish();
            break;
            case R.id.alarm_item:
                Intent intentAlarme = new Intent(this,AlarmActivity.class);
                intentAlarme.putExtra("remedio",remediosEditado);
                startActivity(intentAlarme);
                startActivity(intentAlarme);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
