import Views.AdminView;
import Views.LoginView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AdminView novo = new AdminView();
        novo.menu();
    }
}