package Teste;
import DAO.UsuarioDAO;
import Model.ModelUsuario;

import java.sql.SQLException;
import java.util.Scanner;

import static DAO.UsuarioDAO.isEmailValido;
import static DAO.UsuarioDAO.isIdValido;

public class TesteUsuario {
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        menu();
    }
        public static void menu() throws SQLException, ClassNotFoundException {
            Scanner scanner = new Scanner(System.in);
            ModelUsuario modelUsuario = new ModelUsuario();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            System.out.println("\nEscolha uma das opções abaixo: ");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Deletar");
            System.out.println("4 - Listar");
            System.out.println("0 - Sair");

            int opcao = scanner.nextInt();

            switch(opcao){
                case 1:
                    System.out.println("Informe o email: ");
                    modelUsuario.setEmail(scanner.next());
                    System.out.println("Informe a senha: ");
                    modelUsuario.setSenha(scanner.next());
                    System.out.println("Informe o nome: ");
                    modelUsuario.setNome(scanner.next());
                    System.out.println("Informe o sobrenome: ");
                    modelUsuario.setSobrenome(scanner.next());
                    System.out.println("Informe o telefone: ");
                    modelUsuario.setTelefone(scanner.next());

                    System.out.println("Você tem certeza que os dados estão corretos? ");
                    String respostaCadastrar = scanner.next();

                    if(respostaCadastrar.equals("sim") || respostaCadastrar.equals("Sim")){
                        usuarioDAO.salvar(modelUsuario);
                        System.out.println("Cadastro concluído!");
                    } else if (respostaCadastrar.equals("não") || respostaCadastrar.equals("Não")){
                        System.out.println("Redirecionando ao menu");
                        menu();
                    } else {
                        throw new Error("Essa informação é inválida!");
                    }

                    menu();

                case 2:
                    System.out.println("Informe o email a ser atualizado: ");
                    modelUsuario.setEmail(scanner.next());
                    System.out.println("Informe a senha a ser atualizada: ");
                    modelUsuario.setSenha(scanner.next());
                    System.out.println("Informe o nome a ser atualizado: ");
                    modelUsuario.setNome(scanner.next());
                    System.out.println("Informe o sobrenome a ser atualizado: ");
                    modelUsuario.setSobrenome(scanner.next());
                    System.out.println("Informe o telefone a ser atualizado: ");
                    modelUsuario.setTelefone(scanner.next());
                    System.out.println("Qual usuário será atualizado? ");
                    int idUsuario = scanner.nextInt();

                    ModelUsuario idValidacao = new ModelUsuario();
                    idValidacao.setId(idUsuario);

                    if(isIdValido(idValidacao)){
                        System.out.println("Você tem certeza que os dados estão corretos? ");
                        String respostaAtualizar = scanner.next();

                        if(respostaAtualizar.equals("sim") || respostaAtualizar.equals("Sim")){
                            modelUsuario.setId(idUsuario);
                            usuarioDAO.atualizar(modelUsuario);
                            System.out.println("Atualização concluída!");
                        } else if (respostaAtualizar.equals("não") || respostaAtualizar.equals("Não")){
                            System.out.println("Redirecionando ao menu");
                            menu();
                        } else {
                            throw new Error("Essa informação é inválida!");
                        }
                    } else {
                        throw new Error("Esse usuário não existe!");
                    }

                    menu();

                case 3:
                    System.out.println("Qual usuário será deletado? ");
                    String emailDeletar = scanner.next();

                    ModelUsuario emailDelete = new ModelUsuario();
                    emailDelete.setEmail(emailDeletar);

                    if(isEmailValido(emailDelete)) {
                        System.out.println("Você tem certeza que quer deletar esse usuário? ");
                        String respostaDeletar = scanner.next();

                        if (respostaDeletar.equals("Sim") || respostaDeletar.equals("sim")) {
                            modelUsuario.setEmail(emailDeletar);
                            usuarioDAO.deletar(modelUsuario);
                            System.out.println("Usuário deletado com sucesso.");

                        } else if (respostaDeletar.equals("Não") || respostaDeletar.equals("não")) {
                            System.out.println("Redirecionando ao menu.");
                            menu();

                        } else {
                            throw new Error("Informação inválida");
                        }
                    } else {
                        throw new Error("Esse usuário não existe.");
                    }

                    menu();

                case 4:
                    System.out.println("Os usuários são:\n");
                    UsuarioDAO listarUsuarios = new UsuarioDAO();

                    for(ModelUsuario user : listarUsuarios.cosnsultar()){
                        System.out.println("-----------------------------------");
                        System.out.println("ID: " + user.getId());
                        System.out.println("Email: " + user.getEmail());
                        System.out.println("Senha: " + user.getSenha());
                        System.out.println("Nome: " + user.getNome());
                        System.out.println("Sobrenome: " + user.getSobrenome());
                        System.out.println("Telefone: " + user.getTelefone());
                    }

                    menu();

                case 0:
                    System.exit(5);
            }
    }
}
