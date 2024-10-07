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

    public void menu() throws IOException {

        // Import Scanner
        Scanner input = new Scanner(System.in);

        // Declarar variáveis
        int opcao;
        int userInput;

        do {

            System.out.println();
            System.out.println("------------------------------------------");
            System.out.println("----------- Menu Administrador -----------");
            System.out.println("------------------------------------------");
            System.out.println("1. Total de vendas");
            System.out.println("2. Total de lucro");
            System.out.println("3. Vendas e lucro por mês");
            System.out.println("4. Atração Favorita Adultos");
            System.out.println("5. Atração Favorita Crianças");
            System.out.println("6. Atração Favorita");
            System.out.println("7. Atração mais lucrativa");
            System.out.println("8. Atração menos lucrativa");
            System.out.println("9. Atração que custa menos por segundo");
            System.out.println("------------------------------------------");
            System.out.println("10. Criar novo User");
            System.out.println("0. Sair");

            System.out.print("Selecione uma opção: ");
            opcao = input.nextInt();


            switch (opcao) {
                case 1: // Produto Mais Vendido - Unidades
                    break;
                case 2: // Produto Mais Vendido - Valor
                    break;
                case 3: // Venda Mais Unidades
                    break;
                case 4: // Venda Mais Valor
                    break;
                case 5: // Total Vendas
                    break;

                case 6:
                    System.out.println(adminController.favGeral06());
                    break;

                case 7:
                    System.out.println(adminController.atracaoMaisLucro07());
                    break;

                case 8:
                    System.out.println(adminController.atracaoMenosLucro08());
                    break;
                case 9:
                    System.out.println(adminController.menosCustoSegundo());
                    break;
                case 10:


                    do {

                        System.out.println("------------------------------");
                        System.out.println("------ Criar novo User -------");
                        System.out.println("------------------------------");
                        System.out.println();
                        System.out.println("Escola o tipo de User");
                        System.out.println("1. ADMIN");
                        System.out.println("2. ENG");
                        System.out.println("------------------------------");
                        System.out.println("0. Sair");
                        System.out.println();
                        System.out.print("Escolher: ");

                        userInput = input.nextInt();

                        if (userInput == 1 || userInput == 2) {

                            System.out.println("------ Insira o username -----");
                            System.out.println();
                            System.out.print("Inserir: ");
                            String usernameInput = input.next();

                            System.out.println("******** Insira a password *********");
                            System.out.println();
                            System.out.print("Inserir: ");
                            String passwordInput = input.next();

                            this.novoUser(userInput, usernameInput, passwordInput);
                            System.out.println("User criado com sucesso!");
                            System.out.println("__________________________________________________");
                            break;
                        }
                    } while (userInput != 0);

                default:
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
}
