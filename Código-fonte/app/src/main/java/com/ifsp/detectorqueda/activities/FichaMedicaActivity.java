package com.ifsp.detectorqueda.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.Alergia;
import com.ifsp.detectorqueda.beans.ContatoEmergencia;
import com.ifsp.detectorqueda.beans.Idoso;
import com.ifsp.detectorqueda.beans.Medicamento;
import com.ifsp.detectorqueda.dao.IdosoDao;
import com.ifsp.detectorqueda.helpers.AlergiaListAdapter;
import com.ifsp.detectorqueda.helpers.ContatoEmergenciaListAdapter;
import com.ifsp.detectorqueda.helpers.MedicamentoListAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class FichaMedicaActivity extends AppCompatActivity {
    private TextView txtNome;
    private TextView txtDataNascimento;
    private TextView txtIdade;
    private TextView txtGrupoSanguineo;
    private TextView txtAltura;
    private TextView txtPeso;
    private TextView txtEndereco;
    private ListView ltvAlergias;
    private ListView ltvMedicamentos;
    private ListView ltvContatosEmergencia;

    private List<Alergia> alergias;
    private AlergiaListAdapter adapterAlergia;

    private List<Medicamento> medicamentos;
    private MedicamentoListAdapter adapterMedicamento;

    private List<ContatoEmergencia> contatosEmergencia;
    private ContatoEmergenciaListAdapter adapterContatoEmergencia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ficha_medica);

        iniciarComponentesGraficos();
        iniciarEventos();
        preencherDados();
    }

    private void iniciarComponentesGraficos(){
        this.txtNome = (TextView)this.findViewById(R.id.txtNome);
        this.txtDataNascimento = (TextView)this.findViewById(R.id.txtDataNascimento);
        this.txtIdade = (TextView)this.findViewById(R.id.txtIdade);
        this.txtGrupoSanguineo = (TextView)this.findViewById(R.id.txtGrupoSanguineo);
        this.txtPeso = (TextView)this.findViewById(R.id.txtPeso);
        this.txtAltura = (TextView)this.findViewById(R.id.txtAltura);
        this.txtEndereco = (TextView)this.findViewById(R.id.txtEndereco);

        this.ltvAlergias = (ListView) this.findViewById(R.id.ltvAlergias);
        this.ltvMedicamentos = (ListView)this.findViewById(R.id.ltvMedicamentos);
        this.ltvContatosEmergencia = (ListView)this.findViewById(R.id.ltvContatosEmergencia);
    }

    private void iniciarEventos(){
        this.alergias = new ArrayList<Alergia>();
        this.adapterAlergia = new AlergiaListAdapter(alergias, this);
        ltvAlergias.setAdapter(this.adapterAlergia);

        this.medicamentos = new ArrayList<Medicamento>();
        this.adapterMedicamento = new MedicamentoListAdapter(medicamentos, this);
        ltvMedicamentos.setAdapter(this.adapterMedicamento);

        this.contatosEmergencia = new ArrayList<ContatoEmergencia>();
        this.adapterContatoEmergencia = new ContatoEmergenciaListAdapter(contatosEmergencia, this);
        ltvContatosEmergencia.setAdapter(this.adapterContatoEmergencia);
    }

    private void preencherDados(){
        Idoso idoso = new IdosoDao(this).obter((long)1);

        this.txtNome.setText(idoso.getNome());
        this.txtDataNascimento.setText(idoso.getDataNascimento());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dataNasc = simpleDateFormat.parse(idoso.getDataNascimento());
            this.txtIdade.setText("("+calculaIdade(dataNasc)+")");
        }catch(ParseException ex){
            throw new RuntimeException(ex);
        }

        this.txtGrupoSanguineo.setText(idoso.getGrupoSanguineo());
        this.txtPeso.setText(idoso.getPeso().toString());
        this.txtAltura.setText(idoso.getAltura().toString());

        String endereco = idoso.getLogradouro()+", "+idoso.getNumero()+", "+idoso.getBairro()+", " +
                idoso.getCidade()+" - "+idoso.getEstado()+"; "+idoso.getCep()+" - "+idoso.getComplemento();
        this.txtEndereco.setText(endereco);

        this.alergias.addAll(idoso.getAlergias());
        adapterAlergia.notifyDataSetChanged();

        this.medicamentos.addAll(idoso.getMedicamentos());
        adapterMedicamento.notifyDataSetChanged();

        this.contatosEmergencia.addAll(idoso.getContatosEmergencia());
        adapterContatoEmergencia.notifyDataSetChanged();
    }

    private int calculaIdade(Date dataNasc){
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
