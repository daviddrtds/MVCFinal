package Controllers;

import Model.User;
import Repositories.UserRepository;

import java.io.FileNotFoundException;


public class LoginController {
    private UserRepository usersRepository;

    //recebe uma lista de users como parametro para poder fazer a comparação mais tarde se pelo login é adm ou eng
    public LoginController() throws FileNotFoundException {
        this.usersRepository = new UserRepository();
    }

    /**
     * Método que valida o Login
     *
     * @param usernameInput
     * @param passwordInput
     * @return 0 - Inválido | 1 - ADMIN | 2 - ENGENHEIRO
     */
    public int validateLogin(String usernameInput, String passwordInput) {
//Abre o repositorio que tem o scanner lendo todas as linhas e bota tudo num array de user, depois faz comparação dos inputs com os valores do ficheiro no repositorio, devolvendo o devido login
        for (User userAtual : usersRepository.getUserArray()) {
            if (usernameInput.equals(userAtual.getUsername()) && passwordInput.equals(userAtual.getPassword())) {

                if (userAtual.getLoginType().equals("ADMIN")) {
                    return 1;
                }

                if (userAtual.getLoginType().equals("ENG")) {
                    return 2;
                }
            }
        }
        return 0;
    }
}