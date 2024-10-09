import Views.AdminView;
import Controllers.LoginController;
import Views.LoginView;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {


        LoginController loginController = new LoginController();
        LoginView loginView = new LoginView(loginController);
        loginView.menu();

    }
}