package Views;

import Controllers.AdminController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AdminView {

    private AdminController adminController;

    public AdminView() throws FileNotFoundException {
        this.adminController = new AdminController();
    }


    public void menuAdmin() throws IOException {

        // Import Scanner
        Scanner input = new Scanner(System.in);

        // Declarar variáveis
        int opcao;
        int userInput;

        do {

            System.out.println();
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("------------------ Menu Administrador -----------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("1. Total de vendas    ---------------------------------");
            System.out.println("2. Total de Lucro    ----------------------------------");
            System.out.println("3. Vendas e Lucro por mês    --------------------------");
            System.out.println("4. Atração Favorita - Adultos    ----------------------");
            System.out.println("5. Atração Favorita - Crianças    ---------------------");
            System.out.println("6. Atração Favorita - Número de Bilhetes    -----------");
            System.out.println("7. Atração mais Lucrativa    --------------------------");
            System.out.println("8. Atração menos Lucrativa    -------------------------");
            System.out.println("9. Atração que custa menos por segundo à Empresa    ---");
            System.out.println("-------------------------------------------------------");
            System.out.println("10. Criar novo User");
            System.out.println("0. Sair");
            System.out.println();
            System.out.print("Selecione uma opção: ");
            opcao = input.nextInt();
            System.out.println();


            switch (opcao) {
                case 1: // Produto Mais Vendido - Unidades
                    mostrarTotalVendas();
                    break;
                case 2: // Produto Mais Vendido - Valor
                    mostrarTotalLucro();
                    break;
                case 3: // Venda Mais Unidades
                    mostrarLucroMes();
                    break;
                case 4: // Venda Mais Valor
                    mostrarMProcuradaAdultos();
                    break;
                case 5: // Total Vendas
                    mostrarMProcuradaCriancas();
                    break;

                case 6:
                    String maisBilhetesVendidos = adminController.maisBilhetes().getNome();
                    int quantidadeBilhetes = adminController.vendasEstaAtracao(adminController.maisBilhetes());
                    System.out.println("--->>");
                    System.out.println("--->> A Atraçao favorita foi " + maisBilhetesVendidos + " com " + quantidadeBilhetes + " bilhetes vendidos.");
                    System.out.println("--->>");
                    break;

                case 7:
                    String maisLucro = adminController.maisLucro().getNome();
                    double totalLucro = adminController.lucroEstaAtracao(adminController.maisLucro());
                    System.out.println("--->> ");
                    System.out.println("--->> A Atração que gerou MAIS lucro foi " + maisLucro + " com um total de " + totalLucro + "€.");
                    System.out.println("--->> ");
                    break;

                case 8:
                    String menosLucro = adminController.menosLucro().getNome();
                    double totalMenosLucro = adminController.lucroEstaAtracao(adminController.menosLucro());
                    System.out.println("--->>");
                    System.out.println("--->> A Atração que gerou MENOS lucro foi " + menosLucro + " com um total de " + totalMenosLucro + "€.");
                    System.out.println("--->>");
                    break;

                case 9:
                    double custoSec = adminController.custoSegundo(adminController.atracaoQueCustaMenosParaManter());
                    String atracaoMaisBarataDeManter = adminController.atracaoQueCustaMenosParaManter().getNome();
                    System.out.println("--->>");
                    System.out.println("--->> A Atração que custa menos para manter por segundo é " + atracaoMaisBarataDeManter + " com um custo de " + custoSec + " por segundo.");
                    System.out.println("--->>");
                    break;
                case 10:
                    do {

                        System.out.println("-------------------------------------------------------");
                        System.out.println("------------------- Criar novo User -------------------");
                        System.out.println("-------------------------------------------------------");
                        System.out.println();
                        System.out.println("Escolha o tipo de User    -----------------------------");
                        System.out.println("-------------------------------------------------------");
                        System.out.println("1. ADMIN    -------------------------------------------");
                        System.out.println("2. ENG    ---------------------------------------------");
                        System.out.println("-------------------------------------------------------");
                        System.out.println("0. Sair");
                        System.out.println();
                        System.out.print("Escolher: ");
                        userInput = input.nextInt();
                        System.out.println();

                        if (userInput == 1 || userInput == 2) {

                            System.out.print("Username: ");
                            String usernameInput = input.next();
                            System.out.print("Password: ");
                            String passwordInput = input.next();
                            System.out.println();
                            this.novoUser(userInput, usernameInput, passwordInput);
                            System.out.println("-------------------------------------------------------");
                            System.out.println("--------------- User criado com sucesso! --------------");
                            System.out.println("-------------------------------------------------------");
                            System.out.println();
                            break;
                        }
                    } while (userInput != 0);
                    break;


                case 0:
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
        } while (opcao != 0);

    }


    public void novoUser(int input, String username, String password) throws IOException {
        String loginType = "";

        if (input == 1) {
            loginType = "ADMIN";
        } else {
            loginType = "ENG";
        }

        FileWriter fw = new FileWriter(new File("Files/Cesaeland_logins.csv"), true);
        String novoU = loginType + "," + username + "," + password;
        fw.append("\n" + novoU);
        fw.flush();
    }


    public void mostrarTotalVendas() {
        System.out.println("--->>");
        System.out.println("--->> Total de todas as Vendas: " + adminController.totalVendas());
        System.out.println("--->>");
    }

    public void mostrarTotalLucro() {
        System.out.println("--->>");
        System.out.println("--->> Total de Lucro: " + adminController.lucroVendas());
        System.out.println("--->>");
    }

    public void mostrarLucroMes() {
        Double[][] lucromes = adminController.lucroMes();
        System.out.println("--->>");
        System.out.println("---- MES ----|---- Lucro ----|---- Total_Vendas ----");
        for (int i = 0; i < lucromes.length; i++) {
            System.out.println("---- " + lucromes[i][0] + " ----|---- " + lucromes[i][1] + " ----|---- " + lucromes[i][2] + " ----");
        }
        System.out.println("--->>");
    }

    public void mostrarMProcuradaAdultos() {
        System.out.println("--->>");
        System.out.println("--->> Mais procurada pelos Adultos: " + adminController.maisProcuradaAdultos().getNome());
        System.out.println("--->>");
    }

    public void mostrarMProcuradaCriancas() {
        System.out.println("--->>");
        System.out.println("--->> Mais procurada pelas Crianças: " + adminController.maisProcuradaCriancas().getNome());
        System.out.println("--->>");
    }


}
