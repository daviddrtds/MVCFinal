package Controllers;

import Model.Atracao;
import Model.Custo;
import Model.Venda;
import Repositories.AtracoesRepository;
import Repositories.CustosRepository;
import Repositories.VendasRepository;


import java.io.FileNotFoundException;
import java.util.ArrayList;


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
     * metodo que retorna o valor total de vendas
     * *@return valor total
     */
    public Double totalVendas() {

        Double valortotal = 0.0;//valor total inicia a 0
        for (int linhaVenda = 0; linhaVenda < vendasRepository.getVendasArray().size(); linhaVenda++) { //corre a totalidade do array de vendas linha a linha
            for (int linhaatracoes = 0; linhaatracoes < atracoesRepository.getAtracaoArray().size(); linhaatracoes++) { //corre a totalidade do array de atrações linha a linha
                if ((atracoesRepository.getAtracaoArray().get(linhaatracoes).getId()) == (vendasRepository.getVendasArray().get(linhaVenda).getIdAtracao())) { //se o id da Atração na linha atual for igual ao id da posição da linha atual de Vendas
                    if (vendasRepository.getVendasArray().get(linhaVenda).getClienteTipo().equals("adulto")) { //se o tipo de cliente na linha atual do array de vendas for igual a "adulto"
                        valortotal = valortotal + atracoesRepository.getAtracaoArray().get(linhaatracoes).getPrecoAdulto(); //valor total é igual ao valor total anterior mais o preço de bilhete para adulto
                    } else {
                        valortotal = valortotal + atracoesRepository.getAtracaoArray().get(linhaatracoes).getPrecoCrianca(); //valor total é igual ao valor total anterior mais o preço de bilhete para criança
                    }
                }

            }
        }
        return valortotal;
    }

    /**
     * Metodo para calcular o lucro total
     * * @return lucrototal
     */
    public Double lucroVendas() {
        Double lucrototal = 0.0; //lucro total inicia a 0
        ArrayList<String> meses = new ArrayList<>(); //criado Array vazio para armazenar os meses
        for (int linhaVenda = 0; linhaVenda < vendasRepository.getVendasArray().size(); linhaVenda++) { //corre a totalidade do array de vendas linha a linha
            if (!meses.contains(vendasRepository.getVendasArray().get(linhaVenda).getDataMes())) { //corre a totalidade do array de meses linha a linha e ve se contem o mes atual, se não tiver:
                meses.add(vendasRepository.getVendasArray().get(linhaVenda).getDataMes()); //adiciona o mes a uma nova posição do array de meses.
            }
            for (int linhaatracoes = 0; linhaatracoes < atracoesRepository.getAtracaoArray().size(); linhaatracoes++) { //corre a totalidade do array de atrações linha a linha
                if ((atracoesRepository.getAtracaoArray().get(linhaatracoes).getId()) == (vendasRepository.getVendasArray().get(linhaVenda).getIdAtracao())) { //se o id da atração da posição atual da linha de vendas for igual ao id da atração da posição atual da linha de atrações
                    if (vendasRepository.getVendasArray().get(linhaVenda).getClienteTipo().equals("adulto")) { //se o tipo de cliente na linha atual do array de vendas for igual a "adulto"
                        lucrototal = lucrototal + atracoesRepository.getAtracaoArray().get(linhaatracoes).getPrecoAdulto(); //lucro total é igual ao lucro total atual mais o preço de bilhete para adulto
                    } else { //se o tipo de cliente na linha atual do array de vendas for igual a "criança"
                        lucrototal = lucrototal + atracoesRepository.getAtracaoArray().get(linhaatracoes).getPrecoCrianca(); //lucro total é igual ao lucro total atual mais o preço de bilhete para adulto
                    }
                    lucrototal = lucrototal - custosRepository.getCustoArray().get(linhaatracoes).getCustoManutencao(); //lucro total é igual ao lucro total atual menos o custo de manutenção da atração
                }
            }
        }
        for (int i = 0; i < custosRepository.getCustoArray().size(); i++) {//corre o array de custos linha a linha
            lucrototal = lucrototal - (custosRepository.getCustoArray().get(i).getCustoFixoMes() * meses.size()); //lucro total é igual ao lucro total anterior - (custo fixo mes da atração da linha atual*numero de meses)
        }

        return lucrototal;
    }

    /**
     * Metodo que devolve o lucro e valor total por mes
     * *@return meslucro[][]
     */
    public Double[][] lucroMes() {
        ArrayList<String> meses = new ArrayList<>();//array de Strings que guarda os meses
        double lucromes;
        double fixomes = 0;

        for (int linhaVenda = 0; linhaVenda < vendasRepository.getVendasArray().size(); linhaVenda++) {//corre a totalidade do array de vendas linha a linha
            if (!meses.contains(vendasRepository.getVendasArray().get(linhaVenda).getDataMes())) {//corre a totalidade do array de meses linha a linha e ve se contem o mes atual, se não tiver:
                meses.add(vendasRepository.getVendasArray().get(linhaVenda).getDataMes());////adiciona o mes a uma nova posição do array de meses.
            }
        }
        Double[][] meslucro = new Double[meses.size()][3];//criada matriz para guardar mes/lucromes/totalmes
        for (int m = 0; m < custosRepository.getCustoArray().size(); m++) {//corre a totalidade do array de Custos linha a linha
            fixomes = fixomes + custosRepository.getCustoArray().get(m).getCustoFixoMes();//valor fixo do mes soma todos os valores fixo de manutenção por atração
        }
        for (int i = 0; i < meses.size(); i++) {//corre a totalidade do array de meses linha a linha
            lucromes = 0;
            double totalmes = 0;
            meslucro[i][0] = (double) i;
            for (int k = 0; k < vendasRepository.getVendasArray().size(); k++) {//corre a totalidade do array de vendas linha a linha
                if (meses.get(i).equals(vendasRepository.getVendasArray().get(k).getDataMes())) {//se o mes atual do array meses for igual ao mes da posição atual da linha de vendas
                    for (int j = 0; j < atracoesRepository.getAtracaoArray().size(); j++) {//corre a totalidade do array de atraçoes
                        if (vendasRepository.getVendasArray().get(k).getIdAtracao() == atracoesRepository.getAtracaoArray().get(j).getId()) { // se o id da atração fo igual na linha atual de vendas e na linha atual de atrações
                            if (vendasRepository.getVendasArray().get(k).getClienteTipo().equals("adulto")) {//e tipo de client fo adulto
                                lucromes = lucromes + ((double) (atracoesRepository.getAtracaoArray().get(j).getPrecoAdulto() - custosRepository.getCustoArray().get(j).getCustoManutencao())); //lucro mes = ao lucromes + preço do bilhete adulto da atração atual menos o seu custo de manutenção
                                totalmes = totalmes + (double) (atracoesRepository.getAtracaoArray().get(j).getPrecoAdulto()); //total = ao total + preço do bilhete adulto da atração atual
                            } else { //se for criança
                                lucromes = lucromes + ((double) (atracoesRepository.getAtracaoArray().get(j).getPrecoCrianca() - custosRepository.getCustoArray().get(j).getCustoManutencao()));//lucro mes = ao lucromes + preço do bilhete criança da atração atual menos o seu custo de manutenção
                                totalmes = totalmes + (double) (atracoesRepository.getAtracaoArray().get(j).getPrecoCrianca()); //total = ao total + preço do bilhete criança da atração atual

                            }
                        }
                    }
                }
            }
            lucromes = lucromes - fixomes; // retira ao lucromes total o valor fixo de manutenção total mensal
            meslucro[i][1] = lucromes; // grava para a posição atual do mes o seu lucrototal
            meslucro[i][2] = totalmes;  // grava para a posição atual do mes o seu totalmes
        }
        return meslucro;
    }

    /**
     * Metodo que devolve a atração mais procurada por Adultos
     * *@return MaisProcurada
     */
    public Atracao maisProcuradaAdultos() {
        int count;
        int maiorcount = 0;
        Atracao MaisProcurada = null;
        for (int linhaAtracoes = 0; linhaAtracoes < this.atracoesRepository.getAtracaoArray().size(); linhaAtracoes++) {//corre array de atrações linha a linha
            count = 0;
            for (int linhaVendas = 0; linhaVendas < this.vendasRepository.getVendasArray().size(); linhaVendas++) {//corre array de vendas linha a linha
                if ((vendasRepository.getVendasArray().get(linhaVendas).getIdAtracao() == atracoesRepository.getAtracaoArray().get(linhaAtracoes).getId()) && (vendasRepository.getVendasArray().get(linhaVendas).getClienteTipo().equals("adulto"))) {
                    //se id de atração da linha de vendas igual ao id da atração da linha de atrações && tipo de Cliente na linha atual de vendas igual a "adulto"
                    count++;
                }
            }
            //comparador do maior numero de vendas registado com o numero de vendas atual
            if (maiorcount < count) {
                MaisProcurada = atracoesRepository.getAtracaoArray().get(linhaAtracoes);
                maiorcount = count;
            }
        }
        return MaisProcurada;

    }

    /**
     * Metodo que devolve a atração mais procurada por Adultos
     * *@return MaisProcurada
     */
    public Atracao maisProcuradaCriancas() {
        int count;
        int maiorcount = 0;
        Atracao MaisProcurada = null;
        for (int linhaAtracoes = 0; linhaAtracoes < this.atracoesRepository.getAtracaoArray().size(); linhaAtracoes++) {//corre array de atrações linha a linha
            count = 0;
            for (int linhaVendas = 0; linhaVendas < this.vendasRepository.getVendasArray().size(); linhaVendas++) {//corre array de vendas linha a linha
                if ((vendasRepository.getVendasArray().get(linhaVendas).getIdAtracao() == atracoesRepository.getAtracaoArray().get(linhaAtracoes).getId()) && (vendasRepository.getVendasArray().get(linhaVendas).getClienteTipo().equals("crianca"))) {
                    //se id de atração da linha de vendas igual ao id da atração da linha de atrações && tipo de Cliente na linha atual de vendas igual a "criança"
                    count++;
                }
            }
            //comparador do maior numero de vendas registado com o numero de vendas atual
            if (maiorcount < count) {
                MaisProcurada = atracoesRepository.getAtracaoArray().get(linhaAtracoes);
                maiorcount = count;
            }
        }
        return MaisProcurada;
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



