package Controllers;

import Model.Atracao;
import Model.Venda;
import Repositories.AtracoesRepository;
import Repositories.VendasRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class EngController {

    private VendasRepository vendasRepository;
    private AtracoesRepository atracoesRepository;

    public EngController() throws FileNotFoundException {
        this.vendasRepository = new VendasRepository();
        this.atracoesRepository = new AtracoesRepository();
    }

    /**
     * Função que permite aceder às próximas manutenções a serem feitas de acordo com o num de bilhetes vendidos
     *
     * @return string com as 3 próximas manutenções com ID, nome da atração e qtd de bilhetes que faltam para a próxima manutenção
     */
    // Cria um map que armazenará atração e bilhetes vendidos
    // Percorre o array de atrações e com base na atração percorre o array de vendas para somar quantidade de bilhetes por atração
    // Após somar todos os bilhetes vendidos para aquela atração, fazer operador de resto
    // Adiciona no map o resto da divisão por 50
    // Tendo agora quantidade de bilhetes vendidos por atração, percorrer essa lista
    public String proximaManutencao() {
        ArrayList<Atracao> proximasManutencoes = new ArrayList<>();
        StringBuilder resultado = new StringBuilder(); // armazenar o resultado final


        for (Atracao atracaoAtual : atracoesRepository.getAtracaoArray()) {
            int numBilhetes = 0;

            for (Venda vendaAtual : vendasRepository.getVendasArray()) {
                if (vendaAtual.getIdAtracao() == (atracaoAtual.getId())) {
                    numBilhetes++;
                }
            }

            int bilhetesFaltam = 50 - (numBilhetes % 50);
            atracaoAtual.setBilhetesVendidos(bilhetesFaltam);
            proximasManutencoes.add(atracaoAtual);
        }

        proximasManutencoes.sort((o1, o2) -> o1.getBilhetesVendidos() - o2.getBilhetesVendidos());

        for (int i = 0; i < 3; i++) {
            Atracao atracao = proximasManutencoes.get(i);
            resultado.append("ID: ").append(atracao.getId())
                    .append(" | Atração: ").append(atracao.getNome())
                    .append(" | Bilhetes Para a Próxima Manutenção: ").append(atracao.getBilhetesVendidos())
                    .append("\n");
        }

        return resultado.toString();
    }


    /**
     * Função que permite aceder às últimas manutenções feitas de acordo com o num de bilhetes vendidos
     *
     * @return string com as 3 últimas manutenções com ID, nome da atração e qtd de bilhetes vendidos desde a última manutenção
     */
    // Percorrer o array vendas
    // Ver se atração já existe
    // Se não existir, adicionar atração no hashmap e somar 1 bilhete vendido
    // Se existir, apenas somar 1 bilhete vendido
    // Verificar se bateu 50 vendas para realizar a manutenção
    // Se sim, zerar o contador e adicionar nova manutenção
    // Se não bateu, só ignorar
    public String manutencaoHistorico() {
        HashMap<Integer, Integer> atracaoRevisao = new HashMap<>();
        ArrayList<Integer> revisoes = new ArrayList<>();
        StringBuilder resultado = new StringBuilder(); // armazenar o resultado final

        for (Venda vendaAtual : vendasRepository.getVendasArray()) {
            int atracaoId = vendaAtual.getIdAtracao();
            atracaoRevisao.putIfAbsent(atracaoId, 0);
            atracaoRevisao.replace(atracaoId, atracaoRevisao.get(atracaoId) + 1);

            if (atracaoRevisao.get(atracaoId) == 50) {
                atracaoRevisao.replace(atracaoId, 0);
                revisoes.add(atracaoId);
            }
        }

        if (revisoes.isEmpty()) {
            return "Não há registo de manutenção.";
        }

        for (int i = revisoes.size() - 3; i < revisoes.size(); i++) {
            for (Atracao atracao : atracoesRepository.getAtracaoArray()) {
                if (revisoes.get(i) == (atracao.getId())) {
                    resultado.append("ID: ")
                            .append(atracao.getId())
                            .append(" | Atração: ")
                            .append(atracao.getNome())
                            .append("\nBilhetes Vendidos Após Ultima Manutenção: ")
                            .append(atracaoRevisao.get(atracao.getId()))
                            .append("\n\n");
                }
            }
        }
        return resultado.toString();
    }

}