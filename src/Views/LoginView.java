package Views;

import Controllers.LoginController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LoginView {
    private LoginController loginController;

    public LoginView(LoginController loginController) throws FileNotFoundException {

        this.loginController = new LoginController();
    }

    public void menu() throws IOException {

        // Import Scanner
        Scanner input = new Scanner(System.in);

        // Declarar variáveis
        int opcao;

        do {
            System.out.println();
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("--------------- Bem-vindo ao CESAELand© ---------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("1. Prosseguir sem Login    ----------------------------");
            System.out.println("2. Efetuar Login    -----------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("3. Sair");
            System.out.println();
            System.out.print("Selecione uma opção: ");
            opcao = input.nextInt();

            switch (opcao) {
                case 1: // CLIENTE
                    ClientView clientView = new ClientView();
                    clientView.menuCliente();
                    break;

                case 2: // ADMIN ou ENG
                    menuLogin();
                    break;

                case 3:
                    System.out.println("------------------------------");
                    System.out.println("--------- Até Breve! ---------");
                    System.out.println("------------------------------");
                    System.out.println();
                    break;

                default:
            }

        } while (opcao != 3);

    }

    private void menuLogin() throws IOException {

        // Import Scanner
        Scanner input = new Scanner(System.in);

        // Declarar variáveis
        String usernameInput, passwordInput;


        do {

            System.out.println("------------------------------");
            System.out.println("----------- Login ------------");
            System.out.println("------------------------------");
            System.out.println();
            System.out.print("Username: ");
            usernameInput = input.next();
            System.out.print("Password: ");
            passwordInput = input.next();
            System.out.println();

            switch (loginController.validateLogin(usernameInput, passwordInput)) {
                case 0: // Inválido
                    System.out.println("x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x");
                    System.out.println("x-x-x- Acesso Inválido -x-x-x-x");
                    System.out.println("x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x");
                    System.out.println();
                    break;

                case 1: // ADMIN
                    AdminView adminView = new AdminView();
                    adminView.menuAdmin();
                    break;

                case 2: // ENGENHEIRO
                    EngView engView = new EngView();
                    engView.menuEng();
                    break;
            }

        } while (loginController.validateLogin(usernameInput, passwordInput) == 0);
    }
}