package com.ifsp.detectorqueda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.Alergia;
import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.helpers.AlergiaListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CadastroAlergiaActivity extends AppCompatActivity {
    private Idoso idoso;

    private EditText edtDescricaoAlergia;

    private Button btnProximo;
    private ImageButton btnAddAlergia;

    private ListView ltvAlergias;

    private List<Alergia> alergias;
    private AlergiaListAdapter adapterAlergia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alergia);

        iniciarComponentesGraficos();
        iniciarEventos();
    }

    public void iniciarComponentesGraficos(){
        this.edtDescricaoAlergia = (EditText) findViewById(R.id.edtDescricaoAlergia);
        this.btnProximo = (Button) findViewById(R.id.btnProximo);
        this.btnAddAlergia = (ImageButton) findViewById(R.id.btnAddAlergia);
        this.ltvAlergias = (ListView) findViewById(R.id.ltvAlergias);
    }

    public void iniciarEventos(){
        this.idoso = (Idoso) getIntent().getSerializableExtra("idoso");

        this.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroAlergiaActivity.this, CadastroMedicamentoActivity.class);
                idoso.setAlergias(alergias);
                intent.putExtra("idoso", idoso);
                startActivityForResult(intent, CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
            }
        });

        this.btnAddAlergia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificarCampos()) {
                    Alergia alergia = new Alergia();
                    alergia.setId((long) 0);
                    alergia.setDescricao(edtDescricaoAlergia.getText().toString());
                    idoso.getAlergias().add(0, alergia);

                    alergias.add(0, alergia);
                    adapterAlergia.notifyDataSetChanged();

                    edtDescricaoAlergia.setText("");
                }
            }
        });

        this.alergias = new ArrayList<Alergia>();
        this.adapterAlergia = new AlergiaListAdapter(this.alergias,this);
        this.ltvAlergias.setAdapter(this.adapterAlergia);
    }

    private boolean verificarCampos(){
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