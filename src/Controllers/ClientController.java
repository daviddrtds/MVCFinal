package Controllers;

import Model.Atracao;
import Model.Venda;
import Repositories.AtracoesRepository;
import Repositories.VendasRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class ClientController {
    private AtracoesRepository atracoesRepository;

    // MÉTODO CONSTRUTOR
    public ClientController() throws FileNotFoundException {
        this.atracoesRepository = new AtracoesRepository();
    }

    // MÉTODO GETTER
    public AtracoesRepository getAtracoesRepository() {
        return atracoesRepository;
    }


    /**
     * Função para imprimir uma tabela com as atrações,
     */
    public void imprimirAtracoes() {
        System.out.println("| ID | Atração | Preço Adulto | Preço Criança | Duração(HH:MM:SS) |");
        System.out.println("___________________________________________________________________");
        for (Atracao atracao : atracoesRepository.getAtracaoArray()) {
            imprimeAtracao(atracao);
        }
        System.out.println("___________________________________________________________________");
    }

    /**
     * Função para imprimir uma atração e converter duração de segundos para horas, minutos e segundos
     *
     * @param atracao
     */
    private void imprimeAtracao(Atracao atracao) {
        int segundos = atracao.getSegundos();
        int horas = segundos / 3600;
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;

        System.out.printf("| %d | %s | €%.2f | €%.2f | %02d:%02d:%02d |\n",
                atracao.getId(),
                atracao.getNome(),
                atracao.getPrecoAdulto(),
                atracao.getPrecoCrianca(),
                horas,
                minutos,
                segundosRestantes);
    }


    /**
     * Função para obter a lista de atrações
     *
     * @return lista de atrações
     */
    public ArrayList<Atracao> getAtracoesArray() throws FileNotFoundException {
        return new AtracoesRepository().getAtracaoArray();
    }

    /**
     * Função para obter a lista de vendas
     *
     * @return lista de vendas
     */
    public ArrayList<Venda> getVendasArray() throws FileNotFoundException {
        return new VendasRepository().getVendasArray();
    }


    /**
     * Função para imprimir a atração mais procurada
     *
     * @return atração mais vendida
     */
    public Atracao atracaoMaisProcurada() throws FileNotFoundException {
        ArrayList<Venda> vendasArray = new VendasRepository().getVendasArray();
        ArrayList<Atracao> atracoesArray = new AtracoesRepository().getAtracaoArray();

        int[] contadorVendas = new int[getAtracoesRepository().getAtracaoArray().size()];

        // Iterar o Array de vendas, para cada venda ele guarda o IdAtração
        for (int i = 0; i < vendasArray.size(); i++) {
            Venda venda = vendasArray.get(i);
            int idAtracao = venda.getIdAtracao();
            contadorVendas[idAtracao - 1]++;
        }

        // Validar a atração com mais vendas
        int maxVendas = 0;
        Atracao atracaoMaisProcurada = null;

        for (int i = 0; i < contadorVendas.length; i++) {
            if (contadorVendas[i] > maxVendas) {
                maxVendas = contadorVendas[i];
                atracaoMaisProcurada = atracoesArray.get(i);
            }
        }
        return atracaoMaisProcurada;
    }


    /**
     * Função para imprimir a atração mais procurada por adultos,
     *
     * @return Atração para Adultos mais vendida
     */
    public Atracao atracaoMaisProcuradaAdultos() throws FileNotFoundException {
        ArrayList<Venda> vendasArray = new VendasRepository().getVendasArray();
        ArrayList<Atracao> atracoesArray = new AtracoesRepository().getAtracaoArray();

        int[] contadorVendasAdultos = new int[getAtracoesRepository().getAtracaoArray().size()];

        for (int i = 0; i < vendasArray.size(); i++) {
            Venda venda = vendasArray.get(i);
            int idAtracao = venda.getIdAtracao();
            String clienteTipo = venda.getClienteTipo();

            if (clienteTipo.equalsIgnoreCase("adulto")) {
                contadorVendasAdultos[idAtracao - 1]++;
            }
        }

        int maxVendasAdultos = 0;
        Atracao adultoAtracao = null;

        for (int i = 0; i < contadorVendasAdultos.length; i++) {
            if (contadorVendasAdultos[i] > maxVendasAdultos) {
                maxVendasAdultos = contadorVendasAdultos[i];
                adultoAtracao = atracoesArray.get(i);
            }
        }
        return adultoAtracao;
    }


    /**
     * Função para imprimir a atração mais procurada por crianças,
     *
     * @return atração para criança mais vendida
     */
    public Atracao atracaoMaisProcuradaCriancas() throws FileNotFoundException {

        ArrayList<Venda> vendasArray = new VendasRepository().getVendasArray();
        ArrayList<Atracao> atracoesArray = new AtracoesRepository().getAtracaoArray();
        int[] contadorVendasCriancas = new int[getAtracoesRepository().getAtracaoArray().size()];

        for (int i = 0; i < vendasArray.size(); i++) {
            Venda venda = vendasArray.get(i);
            int idAtracao = venda.getIdAtracao();
            String clienteTipo = venda.getClienteTipo();

            if (clienteTipo.equalsIgnoreCase("crianca")) {
                contadorVendasCriancas[idAtracao - 1]++;
            }
        }

        int maxVendasCriancas = 0;
        Atracao criancaAtracao = null;

        for (int i = 0; i < contadorVendasCriancas.length; i++) {
            if (contadorVendasCriancas[i] > maxVendasCriancas) {
                maxVendasCriancas = contadorVendasCriancas[i];
                criancaAtracao = atracoesArray.get(i);
            }
        }
        return criancaAtracao;
    }

}