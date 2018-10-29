package br.edu.ifsp.monitoramento.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.monitoramento.R;
import br.edu.ifsp.monitoramento.adapters.CadastroMedicamentoListAdapter;
import br.edu.ifsp.monitoramento.models.Idoso;
import br.edu.ifsp.monitoramento.models.Medicamento;

/**
 * @author Denis Magno
 */
public class CadastroMedicamentoActivity extends AppCompatActivity {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private Idoso idoso;

    private TextView txtInformativo;

    private EditText edtNomeMedicamento;
    private EditText edtQtdeMedicamento;
    private EditText edtFrequenciaMedicamento;

    private Button btnProximo;
    private ImageButton btnAddMedicamento;

    private ListView ltvMedicamentos;

    private List<Medicamento> medicamentos;
    private CadastroMedicamentoListAdapter adapterMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_medicamento);

        iniciarComponentesGraficos();
        iniciarEventos();
    }

    public void iniciarComponentesGraficos() {
        this.txtInformativo = findViewById(R.id.txtInformativo);
        this.edtNomeMedicamento = findViewById(R.id.edtNomeMedicamento);
        this.edtQtdeMedicamento = findViewById(R.id.edtQtdeMedicamento);
        this.edtFrequenciaMedicamento = findViewById(R.id.edtFrequenciaMedicamento);
        this.btnProximo = findViewById(R.id.btnProximo);
        this.btnAddMedicamento = findViewById(R.id.btnAddMedicamento);
        this.ltvMedicamentos = findViewById(R.id.ltvMedicamentos);
    }

    public void iniciarEventos() {
        this.idoso = (Idoso) getIntent().getSerializableExtra("idoso");

        this.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idoso.setMedicamentos(medicamentos);

                Intent intent = new Intent(CadastroMedicamentoActivity.this, CadastroContatoEmergenciaActivity.class);
                intent.putExtra("idoso", idoso);
                startActivityForResult(intent, CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
            }
        });

        this.btnAddMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificarCampos()) {
                    Medicamento medicamento = new Medicamento();
                    medicamento.setNome(edtNomeMedicamento.getText().toString());
                    medicamento.setQuantidade(edtQtdeMedicamento.getText().toString());
                    medicamento.setFrequencia(edtFrequenciaMedicamento.getText().toString());

                    medicamentos.add(0, medicamento);
                    adapterMedicamento.notifyDataSetChanged();

                    if (txtInformativo.getVisibility() == TextView.INVISIBLE) {
                        txtInformativo.setVisibility(TextView.VISIBLE);
                    }

                    edtNomeMedicamento.setText("");
                    edtQtdeMedicamento.setText("");
                    edtFrequenciaMedicamento.setText("");
                }
            }
        });

        this.ltvMedicamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicamentos.remove(position);
                adapterMedicamento.notifyDataSetChanged();

                if (medicamentos.isEmpty()) {
                    txtInformativo.setVisibility(TextView.INVISIBLE);
                }
            }
        });

        this.medicamentos = new ArrayList<Medicamento>();
        this.adapterMedicamento = new CadastroMedicamentoListAdapter(this.medicamentos, this);
        this.ltvMedicamentos.setAdapter(this.adapterMedicamento);
    }

    private boolean verificarCampos() {
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
            if (resultCode == RESULT_FIRST_USER) {
                this.setResult(CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
                this.finish();
            }
        }
    }
}