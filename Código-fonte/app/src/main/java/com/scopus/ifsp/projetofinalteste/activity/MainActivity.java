package com.scopus.ifsp.projetofinalteste.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.scopus.ifsp.projetofinalteste.App;
import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.adapter.RemedioAdapter;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.Idoso;
import com.scopus.ifsp.projetofinalteste.model.IdosoDao;
import com.scopus.ifsp.projetofinalteste.model.Remedio;
import com.scopus.ifsp.projetofinalteste.model.RemedioDao;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //Constantes
    public static final int MY_REQUEST_CODE_CADASTRO_SETTINGS = 2;

    String TAG = "MainActivity.class";
    //Lista de Remédios
    RemedioAdapter adapter;
    Remedio remediosEditado = null;
    RecyclerView recyclerView;
    EditText edtNomeFarmaceutico, edtEndereco, edtInstituicao, edtRp, edtCpf, edtData;
    EditText edtNomeRemedio, edtConcentracao, edtFormaFarmaceutica, edtquantidade, edtfrequencia, edtorientacaoextra;


    Spinner spin = null;
    ArrayAdapter<CharSequence> adapterSpinner;


    private boolean verificarPrimeiroAcesso() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        IdosoDao idosoDao = daoSession.getIdosoDao();

        Idoso idoso = idosoDao.load((long) 1);

        if (idoso != null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_item:
                Intent intent = new Intent(MainActivity.this, MapaActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (verificarPrimeiroAcesso()) {
            Intent intent = new Intent(MainActivity.this, CadastroGeralActivity.class);
            startActivityForResult(intent, MY_REQUEST_CODE_CADASTRO_SETTINGS);
        }

        //Pegua intent do adapter
        final Intent intent = getIntent();
        spin = findViewById(R.id.fSpinner);

        //Objetos content_cadastroremedio
        edtNomeFarmaceutico = findViewById(R.id.edtFarmaceutico);
        edtInstituicao = findViewById(R.id.edtInstituicao);
        edtEndereco = findViewById(R.id.edtEndereco);
        edtRp = findViewById(R.id.edtRP);
        edtCpf = findViewById(R.id.edtCPF);
        edtData = findViewById(R.id.edtData);
        edtNomeRemedio = findViewById(R.id.edtNomeRemedio);
        edtConcentracao = findViewById(R.id.edtConcentracao);
        edtFormaFarmaceutica = findViewById(R.id.edtFormaFarmaceutica);
        edtquantidade = findViewById(R.id.edtQtd);
        edtfrequencia = findViewById(R.id.txtFrequencia);
        edtorientacaoextra = findViewById(R.id.txtOrientacaoExtra);

        //Coloca valores no spinner
        adapterSpinner = ArrayAdapter.createFromResource(
                this, R.array.frequencia, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapterSpinner);

        //Se intent conter extra "remedio",
        // colocar valores do textview para exibição em informação e EditText para edição de remedio

        if (intent.hasExtra("remedio")) {

            findViewById(R.id.includeFormRemedio).setVisibility(View.VISIBLE);
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);


            remediosEditado = (Remedio) intent.getSerializableExtra("remedio");

            //Preenchimento dos dados no content_cadastroremedio
            edtNomeFarmaceutico.setText(remediosEditado.getNome_farmaceutico());
            edtEndereco.setText(remediosEditado.getEndereco());
            edtInstituicao.setText(remediosEditado.getInstituicao());
            edtCpf.setText(remediosEditado.getCpf_cnpj());
            edtData.setText(remediosEditado.getData_pedido());
            edtfrequencia.setText(remediosEditado.getFrequencia().substring(0, 2));
            edtNomeRemedio.setText(remediosEditado.getNome_remedio());
            edtConcentracao.setText(remediosEditado.getConcentracao());
            edtFormaFarmaceutica.setText(remediosEditado.getForma_farmaceutica());
            edtorientacaoextra.setText(remediosEditado.getOrientacao_extra());
            edtquantidade.setText(remediosEditado.getQuantidade());
            edtRp.setText(remediosEditado.getRP());

            spin.setSelection(adapterSpinner.getPosition((remediosEditado.getFrequencia()).substring(edtfrequencia.length() + 7)));
        }

        final TextView btnCancelar = findViewById(R.id.btCancel2);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar_voltar();
            }
        });

        Button btnSalvar = findViewById(R.id.btContinuar2);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EditText txtNomeRemedio = findViewById(R.id.edtNomeRemedio);
                EditText txtQtd = findViewById(R.id.edtQtd);
                EditText txtFrequencia = findViewById(R.id.txtFrequencia);
                EditText txtConcentracao = findViewById(R.id.edtConcentracao);
                EditText txtFormaFarmaceutica = findViewById(R.id.edtFormaFarmaceutica);
                EditText txtNomeFarmaceutico = findViewById(R.id.edtFarmaceutico);
                EditText txtInstituicao = findViewById(R.id.edtInstituicao);
                EditText txtEndereco = findViewById(R.id.edtEndereco);
                EditText txtRp = findViewById(R.id.edtRP);
                EditText txtCpf = findViewById(R.id.edtCPF);
                EditText txtData = findViewById(R.id.edtData);
                EditText txtOrientacaoExtra = findViewById(R.id.txtOrientacaoExtra);

                //pegando os valores
                String nome = txtNomeRemedio.getText().toString();
                String quantidade = txtQtd.getText().toString();
                String sp = spin.getSelectedItem().toString();
                String frequencia = txtFrequencia.getText().toString() + " x por " + sp;
                String concentracao = txtConcentracao.getText().toString();
                String forma_farmaceutica = txtFormaFarmaceutica.getText().toString();
                String nome_farmaceutico = txtNomeFarmaceutico.getText().toString();
                String instituicao = txtInstituicao.getText().toString();
                String endereco = txtEndereco.getText().toString();
                String Rp = txtRp.getText().toString();
                String Cpf = txtCpf.getText().toString();
                String Data = txtData.getText().toString();
                String orientacao_extra = txtOrientacaoExtra.getText().toString();

                //Salvando Dados
                DaoSession daoSession = ((App) getApplication()).getDaoSession();

                RemedioDao remedioDao = daoSession.getRemedioDao();

                boolean sucesso;
                boolean novo = false;

                //Se for um remédio existente, atualizar dados remédio
                if (remediosEditado != null) {
                    Remedio remedio = new Remedio();
                    remedio.setId(remediosEditado.getId());
                    remedio.setNome_remedio(nome);
                    remedio.setQuantidade(quantidade);
                    remedio.setFrequencia(frequencia);
                    remedio.setConcentracao(concentracao);
                    remedio.setForma_farmaceutica(forma_farmaceutica);
                    remedio.setInstituicao(instituicao);
                    remedio.setEndereco(endereco);
                    remedio.setRP(Rp);
                    remedio.setCpf_cnpj(Cpf);
                    remedio.setData_pedido(Data);
                    remedio.setOrientacao_extra(orientacao_extra);

                    remedioDao.insertOrReplace(remedio);
                    sucesso = true;
                } else {
                    Remedio remedio = new Remedio();
                    remedio.setNome_remedio(nome);
                    remedio.setQuantidade(quantidade);
                    remedio.setFrequencia(frequencia);
                    remedio.setConcentracao(concentracao);
                    remedio.setForma_farmaceutica(forma_farmaceutica);
                    remedio.setInstituicao(instituicao);
                    remedio.setEndereco(endereco);
                    remedio.setRP(Rp);
                    remedio.setCpf_cnpj(Cpf);
                    remedio.setData_pedido(Data);
                    remedio.setOrientacao_extra(orientacao_extra);

                    remedioDao.insert(remedio);
                    sucesso = true;

                    if (sucesso) {
                        novo = true;
                    }
                }

                Remedio remedio = new Remedio();
                if (sucesso) {

                    //Se salvo com sucesso, e for um novo item, retorna a id do ultimo item adicionado
                    if (novo) {
                        List<Remedio> remedios = remedioDao.loadAll();
                        remedio = remedios.get(remedios.size() - 1);

                        adapter.insertItem(remedio);

                    } else {
                        //Se for apenas a atualização, busca o remedio pela sua id e retorna os dados atualizados
                        remedio = remedioDao.load(remediosEditado.getId());
                        adapter.atualizarRemedios(remedio);
                    }

                    //limpa os campos
                    txtNomeRemedio.setText("");
                    txtQtd.setText("");
                    txtFrequencia.setText("");
                    txtConcentracao.setText("");
                    txtFormaFarmaceutica.setText("");
                    txtNomeFarmaceutico.setText("");
                    txtInstituicao.setText("");
                    txtEndereco.setText("");
                    txtRp.setText("");
                    txtCpf.setText("");
                    txtData.setText("");
                    txtOrientacaoExtra.setText("");

                    Snackbar.make(view, "Salvo!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);

                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                    findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    findViewById(R.id.includeFormRemedio).setVisibility(View.INVISIBLE);

                    try {

                        Intent intentAlarme = new Intent(getApplicationContext(), AlarmActivity.class);
                        intentAlarme.putExtra("remedio", remedio);
                        startActivity(intentAlarme);

                    } catch (Exception e) {
                        Log.i(TAG, "onClick: " + e + " IdRemedio " + remedio.getId());
                    }
                } else {
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        configurarRecycler();

    }

    public void cancelar_voltar() {


        findViewById(R.id.includeFormRemedio).setVisibility(View.INVISIBLE);
        findViewById(R.id.includemain).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

        EditText txtNomeRemedio = findViewById(R.id.edtNomeRemedio);
        EditText txtQtd = findViewById(R.id.edtQtd);
        EditText txtFrequencia = findViewById(R.id.txtFrequencia);
        EditText txtNomeFarmaceutico = findViewById(R.id.edtFarmaceutico);
        EditText txtInstituicao = findViewById(R.id.edtInstituicao);
        EditText txtRp = findViewById(R.id.edtRP);
        EditText txtCpf = findViewById(R.id.edtCPF);
        EditText txtData = findViewById(R.id.edtData);
        EditText txtOrientacaoExtra = findViewById(R.id.txtOrientacaoExtra);


        //limpa os campos
        txtNomeRemedio.setText("");
        txtQtd.setText("");
        txtFrequencia.setText("");
        txtNomeFarmaceutico.setText("");
        txtInstituicao.setText("");
        txtRp.setText("");
        txtCpf.setText("");
        txtData.setText("");
        txtOrientacaoExtra.setText("");

        remediosEditado = null;

        Snackbar.make(Objects.requireNonNull(getCurrentFocus()), "Ação Cancelada", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void addButtonClick(View view) {


        findViewById(R.id.includeFormRemedio).setVisibility(View.VISIBLE);
        findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    private void configurarRecycler() {
        //configurando o gerenciador de Layout para ser uma lista
        recyclerView = findViewById(R.id.listRecycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Adiciona o adapter que irá anexar os objetos à lista

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        RemedioDao remedioDao = daoSession.getRemedioDao();
        adapter = new RemedioAdapter(this, remedioDao.loadAll());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MY_REQUEST_CODE_CADASTRO_SETTINGS:
                if (resultCode == RESULT_OK) {
                    Log.e(TAG, "Cadastro Provider - Cadastrou!");
                }
                break;
        }
    }
}
