package Teste;

import DAO.*;
import Model.*;

import java.sql.SQLException;
import java.util.Scanner;

import static DAO.ProdutoDAO.isIdValido;

public class TesteUsuarioEndereco {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        menu();
    }

    public static void menu() throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);

        ModelUsuarioEndereco usuarioEndereco = new ModelUsuarioEndereco();
        int usuario;
        int categoriaEndereco;

        System.out.println("\nEscolha uma das opções abaixo");
        System.out.println("1 - Adicionar endereço de usuário");
        System.out.println("2 - Atualizar endereço de usuário");
        System.out.println("3 - Deletar endereço de usuário");
        System.out.println("4 - Listar endereço de usuários");
        System.out.println("0 - Sair");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.println("Informe o id do endereço do usuário: ");
                usuario = UsuarioDAO.getUsuarioPorId(scanner.nextInt());

                System.out.println("Informe o id do endereço da categoria: ");
                categoriaEndereco = CategoriaEnderecoDAO.getCategoriaEnderecoPorId(scanner.nextInt());


                if (usuario != -1 && categoriaEndereco != -1) {
                    ModelCategoriaEndereco categoriaEndereco2 = new ModelCategoriaEndereco();
                    categoriaEndereco2.setId(categoriaEndereco);
                    usuarioEndereco.setCategoriaEnderecoId(categoriaEndereco2);

                    ModelUsuario usuario2 = new ModelUsuario();
                    usuario2.setId(usuario);
                    usuarioEndereco.setUsuarioId(usuario2);

                    System.out.println("Qual é o padrão? ");
                    usuarioEndereco.setPadrao(scanner.nextBoolean());

                    System.out.println("Nome da rua 1: ");
                    usuarioEndereco.setRua1(scanner.next());

                    System.out.println("Nome da rua 1: ");
                    usuarioEndereco.setRua2(scanner.next());

                    System.out.println("Nome da cidade: ");
                    usuarioEndereco.setCidade(scanner.next());

                    System.out.println("Nome do estado: ");
                    usuarioEndereco.setEstado(scanner.next());

                    System.out.println("Nome do país: ");
                    usuarioEndereco.setPais(scanner.next());

                    UsuarioEnderecoDAO.salvar(usuarioEndereco);
                } else {
                    System.err.println("não encontrado.");
                }

                menu();
        }
    }
}

                /*
            case 2:
                System.out.println("Qual id da categoria que será alterado?");
                usuario = CategoriaProdutoDAO.getCategoriaPorId(scanner.nextInt());

                if(usuario != -1){
                    ModelCategoriaProduto categoriaProduto = new ModelCategoriaProduto();
                    categoriaProduto.setId(usuario);
                    produto.setCategoriaProdutoId(categoriaProduto);

                    System.out.println("Qual vai ser a alteração da descrição?");
                    produto.setDescricao(scanner.next());

                    System.out.println("Qual vai ser a alteração do preço?");
                    produto.setPreco(scanner.nextDouble());

                    System.out.println("Qual produto será alterado?");
                    produto.setId(scanner.nextInt());

                    if (isIdValido(produto)){
                        System.out.println("Você quer prosseguir com a atualização? ");
                        String respostaAtualizar = scanner.next();

                        if(respostaAtualizar.equals("Sim") || respostaAtualizar.equals("sim")){
                            ProdutoDAO.atualizar(produto);
                            System.out.println("Produto atualizado com sucesso!");
                        } else if (respostaAtualizar.equals("Não") || respostaAtualizar.equals("não")){
                            System.out.println("Redirecionando ao menu");
                            menu();
                        } else {
                            System.err.println("Informação inválida!");
                        }
                    } else {
                        System.err.println("Esse id não existe!");
                    }
                } else {
                    System.err.println("Categoria não encontrada.");
                }

                menu();

            case 3:
                System.out.println("Qual prduto será deletado? ");
                produto.setId(scanner.nextInt());

                if(ProdutoDAO.isIdValido(produto)){
                    System.out.println("Você quer deletar esse produto? ");
                    String respostaDeletar = scanner.next();

                    if (respostaDeletar.equals("Sim") || respostaDeletar.equals("sim")){
                        ProdutoDAO.deletar(produto);
                        System.out.println("Produto deletada com sucesso!");
                    } else if (respostaDeletar.equals("Não") || respostaDeletar.equals("não")){
                        System.out.println("Redirecionando ao menu");
                        menu();
                    } else {
                        throw new Error("Essa informação é inválida!");
                    }
                } else {
                    throw new Error("Essa categoria não existe!");
                }

                menu();

            case 4:
                System.out.println("Os usuários são:\n");
                ProdutoDAO listarUsuarios = new ProdutoDAO();

                for(ModelProduto user : listarUsuarios.consultar()){
                    System.out.println("-----------------------------------");
                    System.out.println("ID: " + user.getId());
                    System.out.println("Categoria ID: " + user.getCategoriaProdutoId().getId());
                    System.out.println("Descrição: " + user.getDescricao());
                    System.out.println("Preço: " + user.getPreco());
                }

                menu();

            case 0:
                System.exit(5);
        }
    }
}
*/