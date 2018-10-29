package br.edu.ifsp.monitoramento.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vicmikhailau.maskededittext.MaskedFormatter;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.monitoramento.R;
import br.edu.ifsp.monitoramento.adapters.StringSpinnerAdapter;
import br.edu.ifsp.monitoramento.models.Idoso;

/**
 * @author Denis Magno
 */
public class CadastroGeralActivity extends AppCompatActivity {
    private String TAG = "Custom - " + this.getClass().getSimpleName();
    public static final int REQUESTCODE_CADASTRO_FINISH = 1;

    private Idoso idoso;

    private TextView txtAlerta;

    private EditText edtNome;
    private EditText edtSobrenome;
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

    private StringSpinnerAdapter stringSpinnerAdapter;
    private StringSpinnerAdapter estadoSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_geral);

        iniciarComponentesGraficos();
        iniciarEventos();
    }

    private void iniciarComponentesGraficos() {
        this.txtAlerta = findViewById(R.id.txtAlerta);
        this.edtNome = findViewById(R.id.edtNome);
        this.edtSobrenome = findViewById(R.id.edtSobrenome);
        this.edtDataNascimento = findViewById(R.id.edtDataNascimento);
        this.spnGrupoSanguineo = findViewById(R.id.spnGrupoSanguineo);
        this.edtAltura = findViewById(R.id.edtAltura);
        this.edtPeso = findViewById(R.id.edtPeso);
        this.edtLogradouro = findViewById(R.id.edtLogradouro);
        this.edtNumero = findViewById(R.id.edtNumero);
        this.edtCep = findViewById(R.id.edtCep);
        this.edtBairro = findViewById(R.id.edtBairro);
        this.edtCidade = findViewById(R.id.edtCidade);
        this.spnEstado = findViewById(R.id.spnEstado);
        this.edtComplemento = findViewById(R.id.edtComplemento);

        this.btnProximo = findViewById(R.id.btnProximo);
    }

    private void iniciarEventos() {
        this.idoso = new Idoso();

        this.btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarCampos()) {
                    txtAlerta.setText(R.string.cadastro_geral_txt_alert_campos);
                } else {
                    MaskedFormatter maskedFormatter = new MaskedFormatter("#####-###");

                    idoso.setNome(edtNome.getText().toString());
                    idoso.setSobrenome(edtSobrenome.getText().toString());
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

        this.stringSpinnerAdapter = new StringSpinnerAdapter(getApplicationContext(), R.layout.item_spinner, criaGruposSanguineos());
        this.spnGrupoSanguineo.setAdapter(stringSpinnerAdapter);

        this.estadoSpinnerAdapter = new StringSpinnerAdapter(getApplicationContext(), R.layout.item_spinner, criaEstados());
        this.spnEstado.setAdapter(estadoSpinnerAdapter);
    }

    private List<String> criaGruposSanguineos() {
        List<String> gruposSanguineos = new ArrayList<String>();
        gruposSanguineos.add(getResources().getString(R.string.cadastro_geral_txt_hint_grupo_sanguineo));
        gruposSanguineos.add("A+");
        gruposSanguineos.add("A-");
        gruposSanguineos.add("B+");
        gruposSanguineos.add("B-");
        gruposSanguineos.add("O+");
        gruposSanguineos.add("O-");
        gruposSanguineos.add("AB+");
        gruposSanguineos.add("AB-");

        return gruposSanguineos;
    }

    private List<String> criaEstados() {
        List<String> estados = new ArrayList<String>();
        estados.add(getResources().getString(R.string.cadastro_geral_txt_hint_estado));
        estados.add("AC");
        estados.add("AL");
        estados.add("AP");
        estados.add("AM");
        estados.add("BA");
        estados.add("CE");
        estados.add("DF");
        estados.add("ES");
        estados.add("GO");
        estados.add("MA");
        estados.add("MT");
        estados.add("MS");
        estados.add("MG");
        estados.add("PA");
        estados.add("PB");
        estados.add("PR");
        estados.add("PE");
        estados.add("PI");
        estados.add("RJ");
        estados.add("RN");
        estados.add("RS");
        estados.add("RO");
        estados.add("RR");
        estados.add("SC");
        estados.add("SP");
        estados.add("SE");
        estados.add("TO");

        return estados;
    }

    private boolean verificarCampos() {
        boolean aux = false;

        if (spnEstado.getSelectedItem().equals(getResources().getString(R.string.cadastro_geral_txt_hint_estado))) {
            TextView error = (TextView) spnEstado.getSelectedView();
            error.setError("");
            error.setText("Selecione um estado!");
            spnEstado.requestFocus();

            aux = true;
        }

        if (edtCidade.getText().toString().isEmpty()) {
            edtCidade.setError("Preencha este campo!");
            edtCidade.requestFocus();

            aux = true;
        }

        if (edtBairro.getText().toString().isEmpty()) {
            edtBairro.setError("Preencha este campo!");
            edtBairro.requestFocus();

            aux = true;
        }

        if (edtCep.getText().toString().isEmpty()) {
            edtCep.setError("Preencha este campo!");
            edtCep.requestFocus();

            aux = true;
        }

        if (edtNumero.getText().toString().isEmpty()) {
            edtNumero.setError("Preencha este campo!");
            edtNumero.requestFocus();

            aux = true;
        }

        if (edtLogradouro.getText().toString().isEmpty()) {
            edtLogradouro.setError("Preencha este campo!");
            edtLogradouro.requestFocus();

            aux = true;
        }

        if (spnGrupoSanguineo.getSelectedItem().equals(getResources().getString(R.string.cadastro_geral_txt_hint_grupo_sanguineo))) {
            TextView error = (TextView) spnGrupoSanguineo.getSelectedView();
            error.setError("");
            error.setText("Selecione um grupo sanguíneo!");
            spnGrupoSanguineo.requestFocus();

            aux = true;
        }

        if (edtPeso.getText().toString().isEmpty()) {
            edtPeso.setError("Preencha este campo!");
            edtPeso.requestFocus();

            aux = true;
        }

        if (edtAltura.getText().toString().isEmpty()) {
            edtAltura.setError("Preencha este campo!");
            edtAltura.requestFocus();

            aux = true;
        }

        if (edtDataNascimento.getText().toString().isEmpty()) {
            edtDataNascimento.setError("Preencha este campo!");
            edtDataNascimento.requestFocus();

            aux = true;
        }

        if (edtNome.getText().toString().isEmpty()) {
            edtNome.setError("Preencha este campo!");
            edtNome.requestFocus();

            aux = true;
        }

        if (edtSobrenome.getText().toString().isEmpty()) {
            edtSobrenome.setError("Preencha este campo!");
            edtSobrenome.requestFocus();

            aux = true;
        }

        return aux;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            iniciarAlertaAbandono();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void iniciarAlertaAbandono() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        moveTaskToBack(true);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog mNoGpsDialog = builder.setMessage("Você está saíndo do aplicativo sem fazer um " +
                "cadastro. Da próxima vez que você entrar, esse cadastro será solicitado novamente.\n\n" +
                "Deseja realmente sair do aplicativo?")
                .setPositiveButton(R.string.main_alert_dialog_localizacao_btnPositivo, dialogClickListener)
                .setNegativeButton(R.string.main_alert_dialog_localizacao_btnNegativo, dialogClickListener)
                .create();
        mNoGpsDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH) {
            if (resultCode == RESULT_FIRST_USER) {
                Log.e(TAG, "onActivityResult");
                this.setResult(RESULT_OK);
                this.finish();
            }
        }
    }
}