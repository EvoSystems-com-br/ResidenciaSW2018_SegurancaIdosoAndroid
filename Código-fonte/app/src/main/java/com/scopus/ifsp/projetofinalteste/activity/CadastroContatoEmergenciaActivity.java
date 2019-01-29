package com.scopus.ifsp.projetofinalteste.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.adapter.CadastroContatoEmergenciaListAdapter;
import com.scopus.ifsp.projetofinalteste.model.Alergia;
import com.scopus.ifsp.projetofinalteste.model.AlergiaDao;
import com.scopus.ifsp.projetofinalteste.model.ContatoEmergencia;
import com.scopus.ifsp.projetofinalteste.model.ContatoEmergenciaDao;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Idoso;
import com.scopus.ifsp.projetofinalteste.model.IdosoDao;
import com.scopus.ifsp.projetofinalteste.model.Remedio;
import com.scopus.ifsp.projetofinalteste.model.RemedioDao;
import com.vicmikhailau.maskededittext.MaskedFormatter;

import java.util.ArrayList;
import java.util.List;

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
    private RemedioDao remedioDao;
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

                    for (Remedio remedio : idoso.getRemedios()) {
                        remedio.setIdosoId(idoso.getId());
                        remedioDao.insert(remedio);
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
        this.remedioDao = daoSession.getRemedioDao();
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