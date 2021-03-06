package br.edu.ifsp.monitoramento.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.monitoramento.R;
import br.edu.ifsp.monitoramento.models.ContatoEmergencia;

/**
 * @author Denis Magno
 */
public class FichaMedicaContatoEmergenciaListAdapter extends BaseAdapter {
    private final List<ContatoEmergencia> contatosEmergencia;
    private final Activity act;

    public FichaMedicaContatoEmergenciaListAdapter(List<ContatoEmergencia> contatosEmergencia, Activity act) {
        this.contatosEmergencia = contatosEmergencia;
        this.act = act;
    }

    @Override
    public int getCount() {
        return this.contatosEmergencia.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contatosEmergencia.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_ficha_medica_contato_emergencia, parent, false);

        ContatoEmergencia contatoEmergencia = this.contatosEmergencia.get(position);

        TextView nome = (TextView) view.findViewById(R.id.txtNomeContatoEmergencia);
        TextView numero = (TextView) view.findViewById(R.id.txtNumeroContatoEmergencia);
        TextView parentesco = (TextView) view.findViewById(R.id.txtParentescoContatoEmergencia);

        nome.setText(contatoEmergencia.getNome().toString());
        numero.setText(contatoEmergencia.getNumero().toString());
        parentesco.setText(contatoEmergencia.getParentesco().toString());

        return view;
    }
}