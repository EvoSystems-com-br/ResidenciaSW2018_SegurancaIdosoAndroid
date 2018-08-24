package com.ifsp.detectorqueda.helpers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.beans.ContatoEmergencia;

import java.util.List;

public class ContatoEmergenciaListAdapter extends BaseAdapter {
    private final List<ContatoEmergencia> contatosEmergencia;
    private final Activity act;

    public ContatoEmergenciaListAdapter(List<ContatoEmergencia> contatosEmergencia, Activity act) {
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
        return this.contatosEmergencia.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.listview_contatos_emergencia_adapter, parent, false);

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
