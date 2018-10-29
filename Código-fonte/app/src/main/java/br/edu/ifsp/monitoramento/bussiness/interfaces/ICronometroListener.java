package br.edu.ifsp.monitoramento.bussiness.interfaces;

/**
 * @author Denis Magno
 */
public interface ICronometroListener {
    /**
     * Função que é chamada quando o cronômetro atinge o valor mínimo.(0)
     *
     * @author Denis Magno
     */
    void onTimeOut();
}
