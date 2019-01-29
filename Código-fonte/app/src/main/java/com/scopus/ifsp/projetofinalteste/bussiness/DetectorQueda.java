package com.scopus.ifsp.projetofinalteste.bussiness;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.scopus.ifsp.projetofinalteste.bussiness.interfaces.IDetectorQuedaCallback;
import com.scopus.ifsp.projetofinalteste.model.Acelerometro;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Magno
 */
public class DetectorQueda implements SensorEventListener {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    //Constantes
    private static int TAMANHO_MAXIMO_JANELA = 300;
    private static int ELEMENTO_INICIAL = DetectorQueda.TAMANHO_MAXIMO_JANELA / 2;
    private static double QUEDA_LIVRE = 0.3;
    private static double MAGNITUDE_ACELERACAO = 2;

    public static int RESULT_QUEDA_NEGATIVO = 0;
    public static int RESULT_QUEDA_POSITIVO = 1;
    public static int RESULT_QUEDA_JANELA_INVALIDA = -1;
    public static int RESULT_QUEDA_ULTRAPASSOU_JANELA = -2;

    private Context contexto;
    private IDetectorQuedaCallback detectorQuedaCallback;
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private List<Acelerometro> janela;

    /**
     * Inicializa os objetos necessários para deteção da queda.
     *
     * @param contexto Contexto do sistema que chamou a função.
     * @author Denis Magno.
     */
    public DetectorQueda(Context contexto, IDetectorQuedaCallback detectorQuedaCallback) {
        this.contexto = contexto;
        this.detectorQuedaCallback = detectorQuedaCallback;
        this.janela = new ArrayList<Acelerometro>();
        this.janela.clear();
    }

    /**
     * Solicita o serviço do sensor do celular, seleciona o sensor a ser utilizado(Acelerômetro) e
     * inicia uma instância de SensorEventListener para o sensor solicitado.
     *
     * @return Verdadeiro se iniciar normalmente o sensor. Falso se ocorrer algum erro na iniciação
     * do sensor.
     * @author Denis Magno.
     */
    public boolean iniciar() {
        this.sensorManager = (SensorManager) this.contexto.getSystemService(Context.SENSOR_SERVICE);

        this.acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (this.sensorManager.registerListener((SensorEventListener) this, this.acelerometro, SensorManager.SENSOR_DELAY_FASTEST)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Para os sensores, uma vez iniciados.
     *
     * @return Verdadeiro se parar normalmente os sensores. Falso se ocorrer algum erro na parada
     * dos sensores.
     * @author Denis Magno.
     */
    public boolean parar() {
        if (this.sensorManager != null && this.acelerometro != null) {
            this.sensorManager.unregisterListener(this, this.acelerometro);

            return true;
        } else {
            return false;
        }
    }

    /**
     * Escuta o sensor proposto para detecção(Acelerômetro).
     * É chamada automaticamente caso o sensor receba um novo Evento(Seus valores sejam alterados).
     *
     * @param event Evento do sensor alterado.
     * @author Denis Magno.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //Monta janela com dados estruturados no objeto 'Acelerometro' vindos do sensor definido.
        this.addItemJanela(new Acelerometro(event.values[0], event.values[1], event.values[2], event.timestamp));

        // Roda algoritmo de detecção de queda para janela montada.
        Integer resultado = this.detectaQuedaLivre();

        // Retorna resultado do algoritmo para callback;
        this.detectorQuedaCallback.onQuedaResult(resultado);
    }

    /**
     * Verifica se houve um movimento de queda livre na janela obtida
     *
     * @return 1 se for detectado uma queda. 0 se não for detectado uma queda. -1 se tamanho da janela
     * ainda não é ideal para verificação. -2 se ultrapassou o tamanho máximo da janela.
     * @author Denis Magno.
     */
    private int detectaQuedaLivre() {
        //Verifica se janela já possui tamanho ideal para começar detecção.
        if (!this.verificaTamanhoJanela()) {
            return -1;
        }

        //new LogHelper().cadastrarJanelaQueda(contexto, this.janela);

        //Verifica se elemento escolhido como inicial inserido na janela atingiu aceleração baixa(Queda livre).
        if (!(this.janela.get(DetectorQueda.ELEMENTO_INICIAL).getMagnitudeAceleracao() < DetectorQueda.QUEDA_LIVRE)) {
            return 0;
        }

        //Define que foi encontrado uma queda livre na janela e onde foi encontrada essa queda.
        int quedaLivre = DetectorQueda.ELEMENTO_INICIAL;

        // Percorre janela inteira, até que encontre o elemento com menor valor de queda livre.
        while (quedaLivre >= 0) {
            //Verifica se próximo elemento da janela é válido.
            if (!this.verificaProxElemJanela(quedaLivre)) {
                return -2;
            }

            //Verifica se próximo elemento da janela é menor do que elemento anterior(Se atingiu menor nível de queda livre)
            if (this.janela.get(quedaLivre).getMagnitudeAceleracao() < this.janela.get(quedaLivre - 1).getMagnitudeAceleracao()) {
                //Define próximo elemento da janela como sendo o pico da queda livre
                quedaLivre--;
            } else {
                //Sai do laço de repetição pois encontrou o menor nível de queda livre.
                break;
            }
        }

        //  Define menor elemento da janela como sendo o pico da queda livre para começar a procurar
        // a ultrapassagem da aceleração da magnitude para definir a possível queda a partir da queda livre.
        int ultrapassagemMagnitude = quedaLivre;

        //  Percorre janela inteira, até que encontre o elemento com maior valor de aceleração da magnitude,
        // a partir da queda livre já identificada no laço anterior.
        while (ultrapassagemMagnitude >= 0) {
            //Verifica se próximo elemento da janela é válido.
            if (!this.verificaProxElemJanela(ultrapassagemMagnitude)) {
                return -2;
            }

            //Verifica se próximo elemento da janela é maior do que elemento anterior(Se atingiu seu maior nível de aceleração de magnitude)
            if (this.janela.get(ultrapassagemMagnitude).getMagnitudeAceleracao() > this.janela.get(ultrapassagemMagnitude - 1).getMagnitudeAceleracao()) {
                ultrapassagemMagnitude--;
            } else {
                // Sai do laço de repetição, pois encontrou a aceleração mais alta.
                break;
            }
        }

        //  Verifica se ultrapassagem da aceleração da magnitude é alta o bastante para definirmos
        // como uma queda.
        if (this.janela.get(ultrapassagemMagnitude).getMagnitudeAceleracao() > DetectorQueda.MAGNITUDE_ACELERACAO) {
            //  Descarta aquela janela se encontrar uma queda para evitar falsos positivos que podem
            // acontecer com dados posteriores a queda livre encontrada.
            this.janela.clear();
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Adiciona um novo item na janela de deteccção de queda.
     *
     * @param dadosAcelerometro Dados do acelerômetro.
     * @author Denis Magno.
     */
    private void addItemJanela(Acelerometro dadosAcelerometro) {
        //Remove último elemento caso a janela já tenha atingido seu valor total
        if (this.janela.size() == DetectorQueda.TAMANHO_MAXIMO_JANELA) {
            this.janela.remove(DetectorQueda.TAMANHO_MAXIMO_JANELA - 1);
        }

        //Adiciona dados capturados do acelerometro no primeiro elemento da lista.
        this.janela.add(0, dadosAcelerometro);
    }

    /**
     * Verifica se janela atingiu o tamanho ideal
     *
     * @return Retorna verdadeiro se janela atingiu tamanho ideal, e falso se não atingiu tamanho ideal.
     * @author Denis Magno
     */
    private boolean verificaTamanhoJanela() {
        if (this.janela.size() == DetectorQueda.TAMANHO_MAXIMO_JANELA) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifica se próximo elemento da janela é válido
     *
     * @param i Elemento atual da janela
     * @return Retorna verdadeiro se elemento é válido, e falso se elemento não é válido.
     * @author Denis Magno
     */
    private boolean verificaProxElemJanela(int i) {
        if (i - 1 >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Não implementado.
     *
     * @param sensor   Não implementado
     * @param accuracy Não implementado
     * @author Denis Magno
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}