package Views;

import Controllers.ClientController;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClientView {

    private ClientController clientController;


    public ClientView() throws FileNotFoundException {
        this.clientController = new ClientController();
    }


    public void menuCliente() throws FileNotFoundException {

        // Import Scanner - Chamar função porque é esperado informação do teclado
        Scanner input = new Scanner(System.in);
        // Declarar variavel
        int opcaoCliente;

        do { // Apresenta MENU CLIENTE
            System.out.println();
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("--------------------- Menu Cliente --------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("1. Consultar Atrações Disponíveis    ------------------");
            System.out.println("2. Consultar Atrações Favoritas    --------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("3. Sair");
            System.out.println();
            System.out.print("Selecione uma opção: ");
            opcaoCliente = input.nextInt(); // Ler e guardar input do user (numero inteiro).

            switch (opcaoCliente) {
                case 1:
                    System.out.println();
                    System.out.println("-------------------------------------------------------");
                    System.out.println("----------------- Atrações Disponíveis ----------------");
                    System.out.println("-------------------------------------------------------");
                    this.imprimirTabelaAtracoes();
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    System.out.println("-------------------------------------------------------");
                    System.out.println("------------------ Atrações Favoritas -----------------");
                    System.out.println("-------------------------------------------------------");
                    this.imprimeAtracaoMaisProcuradaAdultos();
                    this.imprimeAtracaoMaisProcuradaCriancaS();
                    System.out.println();
                    break;
                case 3:
                    System.out.println("-------------------------------------------------------");
                    System.out.println("--------------------- Até Breve! ----------------------");
                    System.out.println("-------------------------------------------------------");
                    System.out.println();
                    break;
                default:
                    System.out.println("x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
                    System.out.println("x-x-x-x-x-x-x-x-x- Opção Inválida! -x-x-x-x-x-x-x-x-x-x-");
                    System.out.println("x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
                    System.out.println();
                    break;
            }
        } while (opcaoCliente != 3); // Enquanto o input do user é diferente de 3 (MENU INICIAL)// TERMINA A FUNCAO
    }


    public void imprimirTabelaAtracoes() throws FileNotFoundException {

        System.out.println("___________________________________________________________________");
        clientController.imprimirAtracoes();
        System.out.println("___________________________________________________________________");
    }

    public void imprimeAtracaoMaisProcurada() throws FileNotFoundException {
        // Imprimir resultado na consola (Atração mais procurada + total de vendas):
        System.out.println("\n-> ATRAÇÃO MAIS PROCURADA:");
        System.out.println("___________________________________________________________________");
        System.out.println(clientController.atracaoMaisProcurada().getNome());
        System.out.println("___________________________________________________________________");

    }

    public void imprimeAtracaoMaisProcuradaAdultos() throws FileNotFoundException {
        // Imprimir resultado na consola (Atração mais procurada + total de vendas):
        System.out.println("\n-> ATRAÇÃO MAIS PROCURADA ADULTOS:");
        System.out.println("___________________________________________________________________");
        System.out.println(clientController.atracaoMaisProcuradaAdultos().getNome());
        System.out.println("___________________________________________________________________");

    }

    public void imprimeAtracaoMaisProcuradaCriancaS() throws FileNotFoundException {
        // Imprimir resultado na consola (Atração mais procurada + total de vendas):
        System.out.println("\n-> ATRAÇÃO MAIS PROCURADA CRIANÇAS:");
        System.out.println("___________________________________________________________________");
        System.out.println("ATRAÇÃO: " + clientController.atracaoMaisProcuradaCriancas().getNome());
        System.out.println("___________________________________________________________________");

    }
}