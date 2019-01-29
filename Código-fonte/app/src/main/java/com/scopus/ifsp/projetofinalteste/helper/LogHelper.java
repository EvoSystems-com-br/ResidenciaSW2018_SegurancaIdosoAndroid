package com.scopus.ifsp.projetofinalteste.helper;

import android.content.Context;
import android.os.FileUriExposedException;
import android.util.Log;

import com.scopus.ifsp.projetofinalteste.model.Acelerometro;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Denis Magno
 */
public class LogHelper {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    /**
     * Cadastra erros do sistema em arquivo de LOG do sistema para posterior visualização.
     *
     * @param contexto Contexto do sistema que chamou a função.
     * @param titulo   Título do erro do sistema.
     * @param corpo    Corpo do erro do sistema(Descrição).
     * @author Denis Magno.
     */
    public void cadastrarErro(Context contexto, String titulo, String corpo) {
        try {
            FileOutputStream arquivo = contexto.openFileOutput("logErrors.txt", Context.MODE_APPEND);

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
            String linha = f.format(new Date()) + " - " + titulo + ": " + corpo + "\n";

            arquivo.write(linha.getBytes());
            arquivo.close();
        } catch (FileNotFoundException ex) {
            Log.e("ERROR FILE", "Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        } catch (FileUriExposedException ex) {
            Log.e("ERROR FILE", "Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        } catch (IOException ex) {
            Log.e("ERROR FILE", "Erro ao escrever no arquivo:");
            Log.e("EXCEPTION", ex.getMessage());
        }
    }

    /**
     * Cadastra possível queda em arquivo de LOG do sistema para posterior visualização.
     *
     * @param contexto Contexto do sistema que chamou a função.
     * @author Denis Magno.
     */
    public void cadastrarQueda(Context contexto) {
        try {
            FileOutputStream arquivo = contexto.openFileOutput("logQuedas.txt", Context.MODE_APPEND);

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
            String linha = f.format(new Date()) + " - Possível queda registrada!\n";

            arquivo.write(linha.getBytes());
            arquivo.close();
        } catch (FileNotFoundException ex) {
            Log.e("ERROR FILE", "Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        } catch (FileUriExposedException ex) {
            Log.e("ERROR FILE", "Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        } catch (IOException ex) {
            Log.e("ERROR FILE", "Erro ao escrever no arquivo:");
            Log.e("EXCEPTION", ex.getMessage());
        }
    }

    /**
     * Cadastra janela de dados de possível queda em arquivo de LOG no formato .csv  para posterior
     * visualização e análise.
     *
     * @param contexto Contexto do sistema que chamou a função.
     * @param janela   Lista de dados do acelerômetro recebidos daquela janela
     * @author Denis Magno.
     */
    public void cadastrarJanelaQueda(Context contexto, List<Acelerometro> janela) {
        try {
            FileOutputStream arquivo = contexto.openFileOutput("logJanelaQueda.csv", Context.MODE_APPEND);

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
            arquivo.write(f.format(new Date()).toString().getBytes());
            arquivo.write("\n".getBytes());
            arquivo.write("EixoX;EixoY;EixoZ;Magnitude;TimeStamp\n".getBytes());

            for (int i = 0; i < janela.size(); i++) {
                String linha = janela.get(i).getxAxis() + ";" +
                        "" + janela.get(i).getyAxis() + ";" +
                        "" + janela.get(i).getzAxis() + ";" +
                        "" + janela.get(i).getMagnitudeAceleracao() + ";" +
                        "" + janela.get(i).getTempo() + ";\n";

                arquivo.write(linha.getBytes());
            }
            arquivo.write("\n".getBytes());

            arquivo.close();
        } catch (FileNotFoundException ex) {
            Log.e("ERROR FILE", "Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        } catch (FileUriExposedException ex) {
            Log.e("ERROR FILE", "Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        } catch (IOException ex) {
            Log.e("ERROR FILE", "Erro ao escrever no arquivo:");
            Log.e("EXCEPTION", ex.getMessage());
        }
    }

    /**
     * Cadastra um dado recebido do acelerômetro de possível queda em arquivo de LOG no
     * formato .csv  para posterior visualização e análise.
     *
     * @param contexto     Contexto do sistema que chamou a função.
     * @param acelerometro Dados do acelerômetro
     * @author Denis Magno.
     */
    public void cadastrarDadoAcelerometro(Context contexto, Acelerometro acelerometro) {
        try {
            FileOutputStream arquivo = contexto.openFileOutput("logDadoAcelerometro.csv", Context.MODE_APPEND);

            String linha = acelerometro.getxAxis() + ";" + acelerometro.getyAxis() + ";" + acelerometro.getzAxis() + ";" + acelerometro.getMagnitudeAceleracao() + ";" + acelerometro.getTempo() + ";\n";

            arquivo.write(linha.getBytes());

            arquivo.close();
        } catch (FileNotFoundException ex) {
            Log.e("ERROR FILE", "Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        } catch (FileUriExposedException ex) {
            Log.e("ERROR FILE", "Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        } catch (IOException ex) {
            Log.e("ERROR FILE", "Erro ao escrever no arquivo:");
            Log.e("EXCEPTION", ex.getMessage());
        }
    }
}