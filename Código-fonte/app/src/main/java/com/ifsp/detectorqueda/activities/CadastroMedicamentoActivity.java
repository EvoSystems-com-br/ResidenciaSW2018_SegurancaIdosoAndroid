package com.ifsp.detectorqueda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.beans.Medicamento;
import com.ifsp.detectorqueda.helpers.MedicamentoListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CadastroMedicamentoActivity extends AppCompatActivity {
    private Idoso idoso;

    private EditText edtNomeMedicamento;

    private Button btnProximo;
    private ImageButton btnAddMedicamento;

    private ListView ltvMedicamentos;

    private List<Medicamento> medicamentos;
    private MedicamentoListAdapter adapterMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_medicamento);

        iniciarComponentesGraficos();
        iniciarEventos();
    }

    public void iniciarComponentesGraficos(){
        this.edtNomeMedicamento = (EditText) findViewById(R.id.edtNomeMedicamento);
        this.btnProximo = (Button) findViewById(R.id.btnProximo);
        this.btnAddMedicamento = (ImageButton) findViewById(R.id.btnAddMedicamento);
        this.ltvMedicamentos = (ListView) findViewById(R.id.ltvMedicamentos);
    }

    public void iniciarEventos(){
        this.idoso = (Idoso) getIntent().getSerializableExtra("idoso");

        this.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroMedicamentoActivity.this, CadastroContatoEmergenciaActivity.class);
                idoso.setMedicamentos(medicamentos);
                intent.putExtra("idoso", idoso);
                startActivityForResult(intent, CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
            }
        });

        this.btnAddMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificarCampos()) {
                    Medicamento medicamento = new Medicamento();
                    medicamento.setId((long) 0);
                    medicamento.setNome(edtNomeMedicamento.getText().toString());
                    idoso.getMedicamentos().add(0, medicamento);

                    medicamentos.add(0, medicamento);
                    adapterMedicamento.notifyDataSetChanged();

                    edtNomeMedicamento.setText("");
                }
            }
        });

        this.medicamentos = new ArrayList<Medicamento>();
        this.adapterMedicamento = new MedicamentoListAdapter(this.medicamentos,this);
        this.ltvMedicamentos.setAdapter(this.adapterMedicamento);
    }

    private boolean verificarCampos(){
        boolean aux = false;

        if (edtNomeMedicamento.getText().toString().isEmpty()) {
            edtNomeMedicamento.setError("Preencha este campo!");
            edtNomeMedicamento.requestFocus();

            aux = true;
        }

        return aux;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH) {
            Log.e("ResultCode Int",""+resultCode);
            Log.e("ResultCode Ok",""+RESULT_CANCELED);
            Log.e("ResultCode Canceled",""+RESULT_CANCELED);
            if (resultCode == RESULT_FIRST_USER) {
                this.setResult(CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
                this.finish();
            }
        }
    }
}
