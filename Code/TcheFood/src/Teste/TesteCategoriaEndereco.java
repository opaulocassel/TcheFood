package Teste;

import DAO.CategoriaEnderecoDAO;
import DAO.CategoriaProdutoDAO;
import Model.ModelCategoriaEndereco;
import Model.ModelCategoriaProduto;

import java.sql.SQLException;
import java.util.Scanner;

import static DAO.CategoriaProdutoDAO.isIdValido;

public class TesteCategoriaEndereco {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        menu();
    }

    public static void menu() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        ModelCategoriaEndereco modelCategoriaEndereco = new ModelCategoriaEndereco();
        CategoriaEnderecoDAO categoriaEnderecoDAO = new CategoriaEnderecoDAO();

        System.out.println("\nEscolha uma das opções abaixo");
        System.out.println("1 - Adicionar descrição do endereço");
        System.out.println("2 - Atualizar descrição do endereço");
        System.out.println("3 - Deletar endereço");
        System.out.println("4 - Listar endereços das categorias");
        System.out.println("0 - Sair");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.println("Informe a descrição do endereco: ");
                modelCategoriaEndereco.setDescricao(scanner.next());

                System.out.println("Você quer seguir com essa descrição? ");
                String respostaSalvar = scanner.next();

                if (respostaSalvar.equals("Sim") || respostaSalvar.equals("sim")) {
                    categoriaEnderecoDAO.salvar(modelCategoriaEndereco);
                    System.out.println("Descrição inserida com sucesso!");

                } else if (respostaSalvar.equals("Não") || respostaSalvar.equals("não")) {
                    System.out.println("Redirecionando ao menu");
                    menu();

                } else {
                    throw new Error("Essa informação é inválida");
                }

                menu();

            case 2:
                System.out.println("Atualização da descrição do endereço: ");
                modelCategoriaEndereco.setDescricao(scanner.next());

                System.out.println("Qual endereço terá a descrição alterada?");
                modelCategoriaEndereco.setId(scanner.nextInt());

                if (categoriaEnderecoDAO.isIdValido(modelCategoriaEndereco)) {
                    System.out.println("Você quer prosseguir com essa atualização? ");
                    String respostaAtualizar = scanner.next();

                    if (respostaAtualizar.equals("Sim") || respostaAtualizar.equals("sim")) {
                        categoriaEnderecoDAO.atualizar(modelCategoriaEndereco);
                        System.out.println("Descrição atualizada com sucesso!");

                    } else if (respostaAtualizar.equals("Não") || respostaAtualizar.equals("não")) {
                        System.out.println("Redirecionando ao menu");
                        menu();

                    } else {
                        throw new Error("Essa informação é inválida!");
                    }
                } else {
                    throw new Error("Essa categoria não existe");
                }

                menu();

            case 3:
                System.out.println("Qual endereço será deletado? ");
                modelCategoriaEndereco.setId(scanner.nextInt());

                if (categoriaEnderecoDAO.isIdValido(modelCategoriaEndereco)) {
                    System.out.println("Você quer deletar esse endereço? ");
                    String respostaDeletar = scanner.next();

                    if (respostaDeletar.equals("Sim") || respostaDeletar.equals("sim")) {
                        categoriaEnderecoDAO.deletar(modelCategoriaEndereco);
                        System.out.println("endereço deletado com sucesso!");

                    } else if (respostaDeletar.equals("Não") || respostaDeletar.equals("não")) {
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
                System.out.println("As categorias são: ");

                for (ModelCategoriaEndereco categoria : categoriaEnderecoDAO.consultar()) {
                    System.out.println("-----------------------------------");
                    System.out.println("ID: " + categoria.getId());
                    System.out.println("Descrição: " + categoria.getDescricao());
                    System.out.println("-----------------------------------");
                }

                menu();

            case 0:
                System.exit(5);
        }
    }
}
