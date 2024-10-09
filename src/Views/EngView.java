package Views;

import Controllers.EngController;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class EngView {

    private EngController engController;

    public EngView() throws FileNotFoundException {
        this.engController = new EngController();
    }

    public void menuEng() {
        Scanner input = new Scanner(System.in);
        int opcao;

        do {
            System.out.println();
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("------------------- Menu Engenheiro -------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("1. Consultar Próximas Manutenções    ------------------");
            System.out.println("2. Consultar Histórico de Manutenções    --------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("3. Sair");
            System.out.println();
            System.out.print("Insira a opção: ");
            opcao = input.nextInt();
            System.out.println();
            switch (opcao) {
                case 1: // Próximas Manutenções
                    this.proximaManutencao();
                    break;
                case 2: // Últimas Manutenções
                    this.historicoManutencao();
                    break;
                case 3:
                    break;
                default:
            }
            System.out.println("__________________________________________________");
        } while (opcao != 3);

    }

    private void proximaManutencao() {
        engController.proximaManutencao();
    }

    private void historicoManutencao() {
        engController.manutencaoHistorico();
    }


}