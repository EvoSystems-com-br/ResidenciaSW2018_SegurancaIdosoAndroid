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
import br.edu.ifsp.monitoramento.adapters.CadastroAlergiaListAdapter;
import br.edu.ifsp.monitoramento.models.Alergia;
import br.edu.ifsp.monitoramento.models.Idoso;

/**
 * @author Denis Magno
 */
public class CadastroAlergiaActivity extends AppCompatActivity {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private Idoso idoso;

    private TextView txtInformativo;
    private EditText edtDescricaoAlergia;

    private Button btnProximo;
    private ImageButton btnAddAlergia;

    private ListView ltvAlergias;

    private List<Alergia> alergias;
    private CadastroAlergiaListAdapter adapterAlergia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alergia);

        iniciarComponentesGraficos();
        iniciarEventos();
    }

    public void iniciarComponentesGraficos() {
        this.txtInformativo = findViewById(R.id.txtInformativo);
        this.edtDescricaoAlergia = findViewById(R.id.edtDescricaoAlergia);
        this.btnProximo = findViewById(R.id.btnProximo);
        this.btnAddAlergia = findViewById(R.id.btnAddAlergia);
        this.ltvAlergias = findViewById(R.id.ltvAlergias);
    }

    public void iniciarEventos() {
        this.idoso = (Idoso) getIntent().getSerializableExtra("idoso");

        this.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idoso.setAlergias(alergias);

                Intent intent = new Intent(CadastroAlergiaActivity.this, CadastroMedicamentoActivity.class);
                intent.putExtra("idoso", idoso);
                startActivityForResult(intent, CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
            }
        });

        this.btnAddAlergia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificarCampos()) {
                    Alergia alergia = new Alergia();
                    alergia.setDescricao(edtDescricaoAlergia.getText().toString());

                    alergias.add(0, alergia);
                    adapterAlergia.notifyDataSetChanged();

                    if (txtInformativo.getVisibility() == TextView.INVISIBLE) {
                        txtInformativo.setVisibility(TextView.VISIBLE);
                    }

                    edtDescricaoAlergia.setText("");
                }
            }
        });

        this.ltvAlergias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alergias.remove(position);
                adapterAlergia.notifyDataSetChanged();

                if (alergias.isEmpty()) {
                    txtInformativo.setVisibility(TextView.INVISIBLE);
                }
            }
        });

        this.alergias = new ArrayList<Alergia>();
        this.adapterAlergia = new CadastroAlergiaListAdapter(this.alergias, this);
        this.ltvAlergias.setAdapter(this.adapterAlergia);
        this.ltvAlergias.setDivider(getDrawable(R.drawable.divider_list_cadastro));
        this.ltvAlergias.setDividerHeight(1);
    }

    private boolean verificarCampos() {
        boolean aux = false;

        if (edtDescricaoAlergia.getText().toString().isEmpty()) {
            edtDescricaoAlergia.setError("Preencha este campo!");
            edtDescricaoAlergia.requestFocus();

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