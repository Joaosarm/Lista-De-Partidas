package br.com.joaosarmento.listadepartidasapi.DTOs;

import java.util.Comparator;

public class ListaDeFreguesesComparator implements Comparator<ListaDeFreguesesDTO> {
    @Override
    public int compare(ListaDeFreguesesDTO listaUm, ListaDeFreguesesDTO listaDois){
        int diferencaPontuacaoListaUm = listaUm.getQuantidadeVitorias() - listaUm.getQuantidadeDerrotas();
        int diferencaPontuacaoListaDois = listaDois.getQuantidadeVitorias() - listaDois.getQuantidadeDerrotas();

        return diferencaPontuacaoListaDois - diferencaPontuacaoListaUm;
    }
}
