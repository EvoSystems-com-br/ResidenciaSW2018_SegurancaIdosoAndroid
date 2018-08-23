package com.ifsp.detectorqueda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.Idoso;
import com.vicmikhailau.maskededittext.MaskedFormatter;

public class CadastroGeralActivity extends AppCompatActivity {
    public static final int REQUESTCODE_CADASTRO_FINISH = 1;

    private Idoso idoso;

    private TextView txtAlerta;

    private EditText edtNome;
    private EditText edtDataNascimento;
    private Spinner spnGrupoSanguineo;
    private EditText edtAltura;
    private EditText edtPeso;
    private EditText edtLogradouro;
    private EditText edtNumero;
    private EditText edtCep;
    private EditText edtBairro;
    private EditText edtCidade;
    private Spinner spnEstado;
    private EditText edtComplemento;
    private Button btnProximo;

    private ArrayAdapter grupoSanguineoSpinnerAdapter;
    private ArrayAdapter estadoSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_geral);

        iniciarComponentesGraficos();
        iniciarEventos();
    }

    private void iniciarComponentesGraficos(){
        this.txtAlerta = (TextView) findViewById(R.id.txtAlerta);
        this.edtNome = (EditText) findViewById(R.id.edtNome);
        this.edtDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
        this.spnGrupoSanguineo = (Spinner) findViewById(R.id.spnGrupoSanguineo);
        this.edtAltura = (EditText) findViewById(R.id.edtAltura);
        this.edtPeso = (EditText) findViewById(R.id.edtPeso);
        this.edtLogradouro = (EditText) findViewById(R.id.edtLogradouro);
        this.edtNumero = (EditText) findViewById(R.id.edtNumero);
        this.edtCep = (EditText) findViewById(R.id.edtCep);
        this.edtBairro = (EditText) findViewById(R.id.edtBairro);
        this.edtCidade = (EditText) findViewById(R.id.edtCidade);
        this.spnEstado = (Spinner) findViewById(R.id.spnEstado);
        this.edtComplemento = (EditText) findViewById(R.id.edtComplemento);

        this.btnProximo = (Button) findViewById(R.id.btnProximo);
    }

    private void iniciarEventos(){
        this.idoso = new Idoso();

        this.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificarCampos()){
                    txtAlerta.setText(R.string.cadastro_geral_txt_alert_campos);
                }else {
                    MaskedFormatter maskedFormatter = new MaskedFormatter("#####-###");

                    idoso.setNome(edtNome.getText().toString());
                    idoso.setDataNascimento(edtDataNascimento.getText().toString());
                    idoso.setGrupoSanguineo(spnGrupoSanguineo.getSelectedItem().toString());
                    idoso.setAltura(Double.parseDouble(edtAltura.getText().toString()));
                    idoso.setPeso(Double.parseDouble(edtPeso.getText().toString()));
                    idoso.setLogradouro(edtLogradouro.getText().toString());
                    idoso.setNumero(edtNumero.getText().toString());
                    idoso.setCep(Integer.parseInt(maskedFormatter.formatString(edtCep.getText().toString()).getUnMaskedString()));
                    idoso.setBairro(edtBairro.getText().toString());
                    idoso.setCidade(edtCidade.getText().toString());
                    idoso.setEstado(spnEstado.getSelectedItem().toString());
                    idoso.setComplemento(edtComplemento.getText().toString());

                    Intent intent = new Intent(CadastroGeralActivity.this, CadastroAlergiaActivity.class);
                    intent.putExtra("idoso", idoso);
                    startActivityForResult(intent, CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
                }
            }
        });

        String[] gruposSanguineos = { "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-" };
        this.grupoSanguineoSpinnerAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1 ,gruposSanguineos);
        this.spnGrupoSanguineo.setAdapter(grupoSanguineoSpinnerAdapter);

        String[] estados = { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS"
                , "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        this.estadoSpinnerAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1 ,estados);
        this.spnEstado.setAdapter(estadoSpinnerAdapter);
    }

    private boolean verificarCampos(){
        boolean aux = false;

        if(edtCidade.getText().toString().isEmpty()){
            edtCidade.setError("Preencha este campo!");
            edtCidade.requestFocus();

            aux = true;
        }

        if(edtBairro.getText().toString().isEmpty()){
            edtBairro.setError("Preencha este campo!");
            edtBairro.requestFocus();

            aux = true;
        }

        if(edtCep.getText().toString().isEmpty()){
            edtCep.setError("Preencha este campo!");
            edtCep.requestFocus();

            aux = true;
        }

        if(edtNumero.getText().toString().isEmpty()){
            edtNumero.setError("Preencha este campo!");
            edtNumero.requestFocus();

            aux = true;
        }

        if(edtLogradouro.getText().toString().isEmpty()){
            edtLogradouro.setError("Preencha este campo!");
            edtLogradouro.requestFocus();

            aux = true;
        }

        if(edtPeso.getText().toString().isEmpty()){
            edtPeso.setError("Preencha este campo!");
            edtPeso.requestFocus();

            aux = true;
        }

        if(edtAltura.getText().toString().isEmpty()){
            edtAltura.setError("Preencha este campo!");
            edtAltura.requestFocus();

            aux = true;
        }

        if(edtDataNascimento.getText().toString().isEmpty()){
            edtDataNascimento.setError("Preencha este campo!");
            edtDataNascimento.requestFocus();

            aux = true;
        }

        if(edtNome.getText().toString().isEmpty()){
            edtNome.setError("Preencha este campo!");
            edtNome.requestFocus();

            aux = true;
        }

        return aux;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH) {
            if (resultCode == RESULT_FIRST_USER) {
                this.finish();
            }
        }
    }
}
