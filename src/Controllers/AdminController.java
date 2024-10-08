package Controllers;

import Model.Atracao;
import Model.Custo;
import Model.Venda;
import Repositories.AtracoesRepository;
import Repositories.CustosRepository;
import Repositories.VendasRepository;


import java.io.FileNotFoundException;


public class AdminController {

    private AtracoesRepository atracoesRepository;
    private CustosRepository custosRepository;
    private VendasRepository vendasRepository;


    public AdminController() throws FileNotFoundException {
        this.atracoesRepository = new AtracoesRepository();
        this.vendasRepository = new VendasRepository();
        this.custosRepository = new CustosRepository();
    }


    public int vendasEstaAtracao(Atracao estaAtracao) {
        int count = 0;
        for (Venda i : vendasRepository.getVendasArray()) {
            if (i.getIdAtracao() == estaAtracao.getId()) {
                count++;
            }
        }
        return count;
    }


    public Atracao maisBilhetes() {
        double countMaior = 0;
        double count;
        Atracao favorita = null;

        for (Atracao i : atracoesRepository.getAtracaoArray()) {
            count = this.vendasEstaAtracao(i);
            if (countMaior <= count) {
                favorita = i;
                countMaior = count;
            }
        }
        return favorita;
    }


    public double lucroEstaAtracao(Atracao estaAtracao) {
        double count = 0;
        double custoBilhete = 0;
        double custoTresMeses = 0;

        for (Custo k : custosRepository.getCustoArray()) {
            if (k.getIdAtracao() == estaAtracao.getId()) {
                custoBilhete = k.getCustoManutencao();
                custoTresMeses = k.getCustoFixoMes() * 3;
            }
        }

        for (Venda i : vendasRepository.getVendasArray()) {
            if (i.getIdAtracao() == estaAtracao.getId()) {
                if (i.getClienteTipo().equals("crianca")) {
                    count += estaAtracao.getPrecoCrianca();

                } else {
                    count += estaAtracao.getPrecoAdulto();
                }
                count -= custoBilhete;
            }
        }
        count -= custoTresMeses;
        return count;
    }


    public Atracao maisLucro() {
        double count;
        double countMaior = 0;
        Atracao maisLucro = null;

        for (Atracao i : atracoesRepository.getAtracaoArray()) {
            count = lucroEstaAtracao(i);
            if (countMaior <= count) {
                maisLucro = i;
                countMaior = count;
            }
        }
        return maisLucro;
    }


    public Atracao menosLucro() {
        double count;
        double countMenor = 0;
        Atracao menosLucro = null;

        for (Atracao i : atracoesRepository.getAtracaoArray()) {
            count = lucroEstaAtracao(i);
            if (countMenor == 0 || countMenor >= count) {
                menosLucro = i;
                countMenor = count;
            }
        }
        return menosLucro;
    }


    public double custoSegundo(Atracao estaAtracao) {

        int diasMes = 30;
        int meses = 3;
        int segundosDia = 24 * 60 * 60;
        int segundos3Meses = diasMes * meses * segundosDia;
        int tempoVolta = estaAtracao.getSegundos();
        double custo3Meses;
        double custoPorVolta;
        double total = 0;
        double custoPorSegundo;


        for (Custo i : custosRepository.getCustoArray()) {

            custo3Meses = i.getCustoFixoMes() * 3;
            custoPorVolta = i.getCustoManutencao() / tempoVolta;


            if (i.getIdAtracao() == estaAtracao.getId()) {

                custoPorSegundo = (custo3Meses / segundos3Meses);
                total = (tempoVolta * custoPorSegundo) + custoPorVolta;


            }
        }
        return total;
    }


    public Atracao atracaoQueCustaMenosParaManter() {

        double custoSegundo;
        double custoMenor = 0;
        Atracao atracaoMaisBarataDeManter = null;

        for (Atracao i : atracoesRepository.getAtracaoArray()) {
            custoSegundo = this.custoSegundo(i);
            if (custoMenor == 0 || custoMenor >= custoSegundo) {
                atracaoMaisBarataDeManter = i;
                custoMenor = custoSegundo;
            }
        }

        return atracaoMaisBarataDeManter;
    }


    public String favGeral06() {

        int count;
        String vendaMaior = null;
        int countMaior = 0;
        for (int i = 0; i < atracoesRepository.getAtracaoArray().size(); i++) {
            count = 0;
            for (int k = 0; k < vendasRepository.getVendasArray().size(); k++) {
                if (vendasRepository.getVendasArray().get(k).getIdAtracao() == atracoesRepository.getAtracaoArray().get(i).getId()) {
                    count++;
                }
            }
            if (countMaior <= count) {
                vendaMaior = atracoesRepository.getAtracaoArray().get(i).getNome();
                countMaior = count;
            }
        }
        String fraseFinal = "A atração favorita do público geral foi " + vendaMaior + " com um total de " + countMaior + " vendas!";
        return fraseFinal;
    }


    public String atracaoMaisLucro07() {

        double count;
        String vendaMaior = null;
        double countMaior = 0;

        for (int i = 0; i < atracoesRepository.getAtracaoArray().size(); i++) {

            count = 0;

            double custoBilhete = custosRepository.getCustoArray().get(i).getCustoManutencao();
            double custoTresMeses = (custosRepository.getCustoArray().get(i).getCustoFixoMes()) * 3;

            for (int k = 0; k < vendasRepository.getVendasArray().size(); k++) {
                if (vendasRepository.getVendasArray().get(k).getIdAtracao() == atracoesRepository.getAtracaoArray().get(i).getId()) {

                    if (vendasRepository.getVendasArray().get(k).getClienteTipo().equals("crianca")) {
                        count += atracoesRepository.getAtracaoArray().get(i).getPrecoCrianca();
                    } else {
                        count += atracoesRepository.getAtracaoArray().get(i).getPrecoAdulto();
                    }
                    count -= custoBilhete;
                }
            }
            count -= custoTresMeses;


            if (countMaior <= count) {
                vendaMaior = atracoesRepository.getAtracaoArray().get(i).getNome();
                countMaior = count;
            }
        }
        String fraseFinal = "A atração " + vendaMaior + " deu mais lucro com " + countMaior + "€";
        return fraseFinal;
    }


    public String atracaoMenosLucro08() {
        double count;
        String vendaMenor = null;
        double countMenor = 0;

        for (int i = 0; i < atracoesRepository.getAtracaoArray().size(); i++) {

            count = 0;

            double custoBilhete = custosRepository.getCustoArray().get(i).getCustoManutencao();
            double custoMes = (custosRepository.getCustoArray().get(i).getCustoFixoMes()) * 3;

            for (int k = 0; k < vendasRepository.getVendasArray().size(); k++) {
                if (vendasRepository.getVendasArray().get(k).getIdAtracao() == atracoesRepository.getAtracaoArray().get(i).getId()) {

                    if (vendasRepository.getVendasArray().get(k).getClienteTipo().equals("crianca")) {
                        count += atracoesRepository.getAtracaoArray().get(i).getPrecoCrianca();
                    } else {
                        count += atracoesRepository.getAtracaoArray().get(i).getPrecoAdulto();
                    }
                    count -= custoBilhete;
                }
            }

            count -= custoMes;

            if (countMenor == 0 || countMenor >= count) {
                vendaMenor = atracoesRepository.getAtracaoArray().get(i).getNome();
                countMenor = count;
            }
        }
        String fraseFinal = "A atração " + vendaMenor + " deu menos lucro com " + countMenor + "€";
        return fraseFinal;
    }


    public String menosCustoSegundo() {

        int diasMes = 30;
        int meses = 3;
        int segundosDia = 24 * 60 * 60;
        int segundos3Meses = diasMes * meses * segundosDia;


        double mensalPorSegundo;
        double custoSegundoAtracao;
        double custoBilhete;
        double totalSegundosCusto;
        double countMenor = 0;
        String countMenorNome = null;


        for (int i = 0; i < custosRepository.getCustoArray().size(); i++) {

            mensalPorSegundo = (double) custosRepository.getCustoArray().get(i).getCustoFixoMes() / segundos3Meses;
            custoSegundoAtracao = atracoesRepository.getAtracaoArray().get(i).getSegundos();
            custoBilhete = custosRepository.getCustoArray().get(i).getCustoManutencao() / custoSegundoAtracao;
            totalSegundosCusto = (mensalPorSegundo * custoSegundoAtracao) + custoBilhete;

            if (countMenor == 0 || countMenor >= totalSegundosCusto) {
                countMenorNome = atracoesRepository.getAtracaoArray().get(i).getNome();
                countMenor = totalSegundosCusto;
            }
        }


        String fraseFinal = "A Atração com menor custo por segundo é " + countMenorNome + " com um custo de " + countMenor + "€ por segundo.";
        return fraseFinal;

    }


}



