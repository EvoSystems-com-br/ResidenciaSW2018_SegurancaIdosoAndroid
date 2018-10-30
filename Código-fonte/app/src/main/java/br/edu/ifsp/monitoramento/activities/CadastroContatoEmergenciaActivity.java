package br.edu.ifsp.monitoramento.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.vicmikhailau.maskededittext.MaskedFormatter;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.monitoramento.App;
import br.edu.ifsp.monitoramento.R;
import br.edu.ifsp.monitoramento.adapters.CadastroContatoEmergenciaListAdapter;
import br.edu.ifsp.monitoramento.models.Alergia;
import br.edu.ifsp.monitoramento.models.AlergiaDao;
import br.edu.ifsp.monitoramento.models.ContatoEmergencia;
import br.edu.ifsp.monitoramento.models.ContatoEmergenciaDao;
import br.edu.ifsp.monitoramento.models.DaoSession;
import br.edu.ifsp.monitoramento.models.Idoso;
import br.edu.ifsp.monitoramento.models.IdosoDao;
import br.edu.ifsp.monitoramento.models.Medicamento;
import br.edu.ifsp.monitoramento.models.MedicamentoDao;

/**
 * @author Denis Magno
 */
public class CadastroContatoEmergenciaActivity extends AppCompatActivity {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private Idoso idoso;

    private TextView txtAlerta;
    private TextView txtInformativo;

    private EditText edtNome;
    private EditText edtNumero;
    private EditText edtParentesco;

    private Button btnFinalizar;
    private ImageButton btnAddContatoEmergencia;

    private ListView ltvContatosEmergencia;

    private List<ContatoEmergencia> contatosEmergencia;
    private CadastroContatoEmergenciaListAdapter adapterContatoEmergencia;

    //Componentes BD
    private IdosoDao idosoDao;
    private AlergiaDao alergiaDao;
    private MedicamentoDao medicamentoDao;
    private ContatoEmergenciaDao contatoEmergenciaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato_emergencia);

        iniciarComponentesBD();
        iniciarComponentesGraficos();
        iniciarEventos();
    }

    public void iniciarComponentesGraficos() {
        this.txtInformativo = findViewById(R.id.txtInformativo);
        this.txtAlerta = findViewById(R.id.txtAlerta);
        this.edtNome = findViewById(R.id.edtNome);
        this.edtNumero = findViewById(R.id.edtNumero);
        this.edtParentesco = findViewById(R.id.edtParentesco);
        this.btnFinalizar = findViewById(R.id.btnFinalizar);
        this.btnAddContatoEmergencia = findViewById(R.id.btnAddContatoEmergencia);
        this.ltvContatosEmergencia = findViewById(R.id.ltvContatosEmergencia);

        this.contatosEmergencia = new ArrayList<ContatoEmergencia>();
        this.adapterContatoEmergencia = new CadastroContatoEmergenciaListAdapter(this.contatosEmergencia, this);
        this.ltvContatosEmergencia.setAdapter(this.adapterContatoEmergencia);
    }

    public void iniciarEventos() {
        this.idoso = (Idoso) getIntent().getSerializableExtra("idoso");

        this.btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contatosEmergencia.size() == 0) {
                    txtAlerta.setText(R.string.cadastro_contato_emergencia_txt_alert_minimo);
                } else {

                    idoso.setId(idosoDao.insert(idoso));

                    for (Alergia alergia : idoso.getAlergias()) {
                        alergia.setIdosoId(idoso.getId());
                        alergiaDao.insert(alergia);
                    }

                    for (Medicamento medicamento : idoso.getMedicamentos()) {
                        medicamento.setIdosoId(idoso.getId());
                        medicamentoDao.insert(medicamento);
                    }

                    for (ContatoEmergencia contatoEmergencia : contatosEmergencia) {
                        contatoEmergencia.setIdosoId(idoso.getId());
                        contatoEmergenciaDao.insert(contatoEmergencia);
                    }

                    setResult(CadastroGeralActivity.REQUESTCODE_CADASTRO_FINISH);
                    finish();
                }
            }
        });

        this.btnAddContatoEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarCampos()) {
                    txtAlerta.setText(R.string.cadastro_contato_emergencia_txt_alert_campos);
                } else {
                    MaskedFormatter maskedFormatter = new MaskedFormatter("(##) # ####-####");

                    ContatoEmergencia contatoEmergencia = new ContatoEmergencia();
                    contatoEmergencia.setIdosoId(idoso.getId());
                    contatoEmergencia.setNome(edtNome.getText().toString());
                    contatoEmergencia.setNumero(Long.parseLong(maskedFormatter.formatString(edtNumero.getText().toString()).getUnMaskedString()));
                    contatoEmergencia.setParentesco(edtParentesco.getText().toString());

                    contatosEmergencia.add(contatoEmergencia);
                    adapterContatoEmergencia.notifyDataSetChanged();

                    if (txtInformativo.getVisibility() == TextView.INVISIBLE) {
                        txtInformativo.setVisibility(TextView.VISIBLE);
                    }

                    edtNome.setText("");
                    edtNumero.setText("");
                    edtParentesco.setText("");
                }
            }
        });

        this.ltvContatosEmergencia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contatosEmergencia.remove(position);
                adapterContatoEmergencia.notifyDataSetChanged();

                if (contatosEmergencia.isEmpty()) {
                    txtInformativo.setVisibility(TextView.INVISIBLE);
                }
            }
        });
    }

    private void iniciarComponentesBD() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        this.idosoDao = daoSession.getIdosoDao();
        this.alergiaDao = daoSession.getAlergiaDao();
        this.medicamentoDao = daoSession.getMedicamentoDao();
        this.contatoEmergenciaDao = daoSession.getContatoEmergenciaDao();
    }

    private boolean verificarCampos() {
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