package com.ifsp.detectorqueda.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.ContatoEmergencia;
import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.dao.IdosoDao;
import com.ifsp.detectorqueda.helpers.ContatoEmergenciaListAdapter;
import com.vicmikhailau.maskededittext.MaskedFormatter;

import java.util.ArrayList;
import java.util.List;

public class CadastroContatoEmergenciaActivity extends AppCompatActivity {
    private Idoso idoso;

    private TextView txtAlerta;

    private EditText edtNome;
    private EditText edtNumero;
    private EditText edtParentesco;

    private Button btnFinalizar;
    private ImageButton btnAddContatoEmergencia;

    private ListView ltvContatosEmergencia;

    private List<ContatoEmergencia> contatosEmergencia;
    private ContatoEmergenciaListAdapter adapterContatoEmergencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato_emergencia);

        iniciarComponentesGraficos();
        iniciarEventos();
    }

    public void iniciarComponentesGraficos(){
        this.txtAlerta = (TextView) findViewById(R.id.txtAlerta);
        this.edtNome = (EditText) findViewById(R.id.edtNome);
        this.edtNumero = (EditText) findViewById(R.id.edtNumero);
        this.edtParentesco = (EditText) findViewById(R.id.edtParentesco);
        this.btnFinalizar = (Button) findViewById(R.id.btnFinalizar);
        this.btnAddContatoEmergencia = (ImageButton) findViewById(R.id.btnAddContatoEmergencia);
        this.ltvContatosEmergencia = (ListView) findViewById(R.id.ltvContatosEmergencia);

        this.contatosEmergencia = new ArrayList<ContatoEmergencia>();
        this.adapterContatoEmergencia = new ContatoEmergenciaListAdapter(this.contatosEmergencia,this);
        this.ltvContatosEmergencia.setAdapter(this.adapterContatoEmergencia);
    }

    public void iniciarEventos(){
        this.idoso = (Idoso) getIntent().getSerializableExtra("idoso");

        this.btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contatosEmergencia.size() == 0){
                    txtAlerta.setText(R.string.cadastro_contato_emergencia_txt_alert_minimo);
                }else{
                    Idoso result = new IdosoDao(getBaseContext()).criar(idoso);
                    if(result != null){
                        Toast.makeText(CadastroContatoEmergenciaActivity.this, "Criado com sucesso!", Toast.LENGTH_SHORT).show();
                        setResult(CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
                        finish();
                    }else{
                        Toast.makeText(CadastroContatoEmergenciaActivity.this, "Não foi possível criar!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        this.btnAddContatoEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificarCampos()){
                    txtAlerta.setText(R.string.cadastro_contato_emergencia_txt_alert_campos);
                }else {
                    MaskedFormatter maskedFormatter = new MaskedFormatter("(##) # ####-####");

                    ContatoEmergencia contatoEmergencia = new ContatoEmergencia();
                    contatoEmergencia.setId((long) 0);
                    contatoEmergencia.setNome(edtNome.getText().toString());
                    contatoEmergencia.setNumero(Long.parseLong(maskedFormatter.formatString(edtNumero.getText().toString()).getUnMaskedString()));
                    contatoEmergencia.setParentesco(edtParentesco.getText().toString());
                    idoso.getContatosEmergencia().add(contatoEmergencia);

                    contatosEmergencia.add(contatoEmergencia);
                    adapterContatoEmergencia.notifyDataSetChanged();

                    edtNome.setText("");
                    edtNumero.setText("");
                    edtParentesco.setText("");
                }
            }
        });


    }

    private boolean verificarCampos(){
        boolean aux = false;

        if (edtParentesco.getText().toString().isEmpty()) {
            edtParentesco.setError("Preencha este campo!");
            edtParentesco.requestFocus();

            aux = true;
        }

        if (edtNumero.getText().toString().isEmpty()) {
            edtNumero.setError("Preencha este campo!");
            edtNumero.requestFocus();

            aux = true;
        }

        if (edtNome.getText().toString().isEmpty()) {
            edtNome.setError("Preencha este campo!");
            edtNome.requestFocus();

            aux = true;
        }

        return aux;
    }
}
