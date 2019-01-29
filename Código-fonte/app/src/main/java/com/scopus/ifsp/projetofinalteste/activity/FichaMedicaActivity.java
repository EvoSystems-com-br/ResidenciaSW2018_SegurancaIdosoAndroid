package com.scopus.ifsp.projetofinalteste.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.adapter.FichaMedicaAlergiaListAdapter;
import com.scopus.ifsp.projetofinalteste.adapter.FichaMedicaContatoEmergenciaListAdapter;
import com.scopus.ifsp.projetofinalteste.adapter.FichaMedicaRemedioListAdapter;
import com.scopus.ifsp.projetofinalteste.model.Alergia;
import com.scopus.ifsp.projetofinalteste.model.ContatoEmergencia;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Idoso;
import com.scopus.ifsp.projetofinalteste.model.IdosoDao;
import com.scopus.ifsp.projetofinalteste.model.Remedio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Denis Magno
 */
public class FichaMedicaActivity extends AppCompatActivity {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private TextView txtNome;
    private TextView txtSobrenome;
    private TextView txtDataNascimento;
    private TextView txtIdade;
    private TextView txtGrupoSanguineo;
    private TextView txtAltura;
    private TextView txtPeso;
    private TextView txtEndereco;
    private ListView ltvAlergias;
    private ListView ltvremedios;
    private ListView ltvContatosEmergencia;

    private List<Alergia> alergias;
    private FichaMedicaAlergiaListAdapter adapterAlergia;

    private List<Remedio> remedios;
    private FichaMedicaRemedioListAdapter adapterremedio;

    private List<ContatoEmergencia> contatosEmergencia;
    private FichaMedicaContatoEmergenciaListAdapter adapterContatoEmergencia;

    //Componentes BD
    private IdosoDao idosoDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ficha_medica);

        iniciarComponentesBD();
        iniciarComponentesGraficos();
        iniciarEventos();
        preencherDados();
    }

    private void iniciarComponentesGraficos() {
        this.txtNome = (TextView) this.findViewById(R.id.txtNome);
        this.txtSobrenome = (TextView) this.findViewById(R.id.txtSobrenome);
        this.txtDataNascimento = (TextView) this.findViewById(R.id.txtDataNascimento);
        this.txtIdade = (TextView) this.findViewById(R.id.txtIdade);
        this.txtGrupoSanguineo = (TextView) this.findViewById(R.id.txtGrupoSanguineo);
        this.txtPeso = (TextView) this.findViewById(R.id.txtPeso);
        this.txtAltura = (TextView) this.findViewById(R.id.txtAltura);
        this.txtEndereco = (TextView) this.findViewById(R.id.txtEndereco);

        this.ltvAlergias = (ListView) this.findViewById(R.id.ltvAlergias);
        this.ltvremedios = (ListView) this.findViewById(R.id.ltvMedicamentos);
        this.ltvContatosEmergencia = (ListView) this.findViewById(R.id.ltvContatosEmergencia);
    }

    private void iniciarEventos() {
        this.alergias = new ArrayList<Alergia>();
        this.adapterAlergia = new FichaMedicaAlergiaListAdapter(alergias, this);
        this.ltvAlergias.setAdapter(this.adapterAlergia);
        this.ltvAlergias.setDivider(getDrawable(R.drawable.divider_list_ficha_medica));
        this.ltvAlergias.setDividerHeight(1);

        this.remedios = new ArrayList<Remedio>();
        this.adapterremedio = new FichaMedicaRemedioListAdapter(remedios, this);
        this.ltvremedios.setAdapter(this.adapterremedio);
        this.ltvremedios.setDivider(getDrawable(R.drawable.divider_list_ficha_medica));
        this.ltvremedios.setDividerHeight(1);

        this.contatosEmergencia = new ArrayList<ContatoEmergencia>();
        this.adapterContatoEmergencia = new FichaMedicaContatoEmergenciaListAdapter(contatosEmergencia, this);
        this.ltvContatosEmergencia.setAdapter(this.adapterContatoEmergencia);
        this.ltvContatosEmergencia.setDivider(getDrawable(R.drawable.divider_list_ficha_medica));
        this.ltvContatosEmergencia.setDividerHeight(1);
    }

    private void iniciarComponentesBD() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        this.idosoDao = daoSession.getIdosoDao();
    }

    private void preencherDados() {
        Idoso idoso = idosoDao.load((long) 1);

        this.txtNome.setText(idoso.getNome());
        this.txtSobrenome.setText(idoso.getSobrenome());
        this.txtDataNascimento.setText(idoso.getDataNascimento());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dataNasc = simpleDateFormat.parse(idoso.getDataNascimento());
            this.txtIdade.setText("(" + calculaIdade(dataNasc) + ")");
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        this.txtGrupoSanguineo.setText(idoso.getGrupoSanguineo());
        this.txtPeso.setText(idoso.getPeso().toString());
        this.txtAltura.setText(idoso.getAltura().toString());

        String endereco;

        if (idoso.getComplemento().isEmpty()) {
            endereco = idoso.getLogradouro() + ", " + idoso.getNumero() + ", " + idoso.getBairro() + ", " +
                    idoso.getCidade() + " - " + idoso.getEstado() + "; " + idoso.getCep();
        } else {
            endereco = idoso.getLogradouro() + ", " + idoso.getNumero() + ", " + idoso.getBairro() + ", " +
                    idoso.getCidade() + " - " + idoso.getEstado() + "; " + idoso.getCep() + " - " + idoso.getComplemento();
        }

        this.txtEndereco.setText(endereco);

        this.alergias.addAll(idoso.getAlergias());
        ConstraintLayout.LayoutParams aParams = (ConstraintLayout.LayoutParams) this.ltvAlergias.getLayoutParams();
        aParams.height = this.alergias.size() * 90;
        this.ltvAlergias.setLayoutParams(aParams);
        adapterAlergia.notifyDataSetChanged();

        this.remedios.addAll(idoso.getRemedios());
        ConstraintLayout.LayoutParams mParams = (ConstraintLayout.LayoutParams) this.ltvremedios.getLayoutParams();
        mParams.height = this.remedios.size() * 230;
        this.ltvremedios.setLayoutParams(mParams);
        adapterremedio.notifyDataSetChanged();

        this.contatosEmergencia.addAll(idoso.getContatosEmergencia());
        ConstraintLayout.LayoutParams cParams = (ConstraintLayout.LayoutParams) this.ltvContatosEmergencia.getLayoutParams();
        cParams.height = this.contatosEmergencia.size() * 230;
        this.ltvContatosEmergencia.setLayoutParams(cParams);
        adapterContatoEmergencia.notifyDataSetChanged();
    }

    private int calculaIdade(Date dataNasc) {
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNasc);

        Calendar hoje = Calendar.getInstance();
        int age = hoje.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        dateOfBirth.add(Calendar.YEAR, age);

        if (hoje.before(dateOfBirth)) {
            age--;
        }

        return age;
    }
}