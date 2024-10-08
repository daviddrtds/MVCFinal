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


    /**
     * Conta o número de bilhetes vendidos para uma atração específica.
     *
     * @param estaAtracao A atração para a qual serão contadas as vendas de bilhetes.
     * @return O número de bilhetes vendidos para a atração fornecida.
     */
    public int vendasEstaAtracao(Atracao estaAtracao) {
        int count = 0;
        for (Venda i : vendasRepository.getVendasArray()) {
            if (i.getIdAtracao() == estaAtracao.getId()) {
                count++;
            }
        }
        return count;
    }


    /**
     * Encontra a atração com o maior número de bilhetes vendidos.
     *
     * @return A atração com mais bilhetes vendidos.
     */
    public Atracao maisBilhetes() {
        double countMaior = 0;
        double count;
        Atracao favorita = null;

        for (Atracao i : atracoesRepository.getAtracaoArray()) {
            count = this.vendasEstaAtracao(i);  //chama o metodo vendasEstaAtracao() para cada atracao do array
            if (countMaior <= count) {
                favorita = i;
                countMaior = count;
            }
        }
        return favorita;
    }


    /**
     * Calcula o lucro para uma atração específica, considerando as vendas de bilhetes
     * e os custos de manutenção.
     *
     * @param estaAtracao A atração para a qual o lucro será calculado.
     * @return O lucro para a atração fornecida.
     */
    public double lucroEstaAtracao(Atracao estaAtracao) {
        double count = 0;
        double custoBilhete = 0;
        double custoTresMeses = 0;

        for (Custo k : custosRepository.getCustoArray()) {    //informaçao dos custos por atraçao
            if (k.getIdAtracao() == estaAtracao.getId()) {
                custoBilhete = k.getCustoManutencao();
                custoTresMeses = k.getCustoFixoMes() * 3;
            }
        }

        for (Venda i : vendasRepository.getVendasArray()) {    //para cada venda, procura o id da atraçao que vem por parametro
            if (i.getIdAtracao() == estaAtracao.getId()) {
                if (i.getClienteTipo().equals("crianca")) {    //confirma tipo de comprador
                    count += estaAtracao.getPrecoCrianca();

                } else {
                    count += estaAtracao.getPrecoAdulto();
                }
                count -= custoBilhete;                           //tira custo por bilhete
            }
        }
        count -= custoTresMeses;                                 //tira custo dos 3 meses de manutençao
        return count;
    }


    /**
     * Encontra a atração que gera mais lucro.
     *
     * @return A atração com maior lucro.
     */
    public Atracao maisLucro() {
        double count;
        double countMaior = 0;
        Atracao maisLucro = null;

        for (Atracao i : atracoesRepository.getAtracaoArray()) {  //corre cada atraçao e chama o metodo lucroEstaAtracao()
            count = lucroEstaAtracao(i);
            if (countMaior <= count) {              //guarda a que tem o valor maior de lucro
                maisLucro = i;                      //guarda atraçao correspondente
                countMaior = count;
            }
        }
        return maisLucro;                           //retorna atraçao
    }


    /**
     * Encontra a atração que gera menos lucro.
     *
     * @return A atração com menor lucro.
     */
    public Atracao menosLucro() {
        double count;
        double countMenor = 0;
        Atracao menosLucro = null;

        for (Atracao i : atracoesRepository.getAtracaoArray()) {        //igual ao maisLucro() mas guarda o menor valor
            count = lucroEstaAtracao(i);
            if (countMenor == 0 || countMenor >= count) {
                menosLucro = i;
                countMenor = count;
            }
        }
        return menosLucro;
    }


    /**
     * Calcula o custo de funcionamento de uma atração por segundo.
     *
     * @param estaAtracao A atração para a qual o custo por segundo será calculado.
     * @return O custo de funcionamento da atração por segundo.
     */
    public double custoSegundo(Atracao estaAtracao) {

        int diasMes = 30;
        int meses = 3;
        int segundosDia = 24 * 60 * 60;
        int segundos3Meses = diasMes * meses * segundosDia;   //total de segundos em 3 meses


        int tempoVolta = estaAtracao.getSegundos();    //tempo da volta na atraçao
        double custo3Meses;
        double custoPorVolta;
        double total = 0;
        double custoPorSegundo;


        for (Custo i : custosRepository.getCustoArray()) {          //procura o id da atraçao nos custos

            custo3Meses = i.getCustoFixoMes() * 3;                  //multiplica custo fixo pelos 3 meses
            custoPorVolta = i.getCustoManutencao() / tempoVolta;    //divide o custo de manutençao decada volta pela duração da mesma

            if (i.getIdAtracao() == estaAtracao.getId()) {           //encontra id

                custoPorSegundo = (custo3Meses / segundos3Meses);        //custo de cada segundo
                total = (tempoVolta * custoPorSegundo) + custoPorVolta;   //tempo da volta x custo de cada segundo, mais o custo em segundos de cada volta

            }
        }
        return total;
    }


    /**
     * Encontra a atração com o menor custo de manutenção por segundo de operação.
     *
     * @return A atração com o menor custo de manutenção.
     */
    public Atracao atracaoQueCustaMenosParaManter() {

        double custoSegundo;
        double custoMenor = 0;
        Atracao atracaoMaisBarataDeManter = null;

        for (Atracao i : atracoesRepository.getAtracaoArray()) {  //corre as atraçoes e chama custoSegundo() em cada uma
            custoSegundo = this.custoSegundo(i);
            if (custoMenor == 0 || custoMenor >= custoSegundo) {   //guarda o valor mais baixo
                atracaoMaisBarataDeManter = i;                     //guarda a atraçao
                custoMenor = custoSegundo;
            }
        }

        return atracaoMaisBarataDeManter;
    }
}



