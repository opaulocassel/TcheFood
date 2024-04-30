package Teste;

import DAO.CategoriaProdutoDAO;
import DAO.ProdutoDAO;
import DAO.PromocaoDAO;
import Model.ModelCategoriaProduto;
import Model.ModelPromocao;
import Model.ModelProduto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static DAO.PromocaoDAO.isIdValido;

public class TestePromocao {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        menu();
    }

    public static void menu() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        ModelPromocao modelPromocao = new ModelPromocao();
        int promocao;

        System.out.println("\nEscolha uma das opções abaixo");
        System.out.println("1 - Adicionar promoção");
        System.out.println("2 - Atualizar promoção");
        System.out.println("3 - Deletar promoção");
        System.out.println("4 - Listar promoções");
        System.out.println("0 - Sair");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.println("Informe o id do produto: ");
                promocao = ProdutoDAO.getProdutoPorId(scanner.nextInt());

                if (promocao != -1) {
                    ModelProduto produto = new ModelProduto();
                    produto.setId(promocao);
                    modelPromocao.setProdutoId(produto);

                    System.out.println("Qual a descrição da promoção? ");
                    modelPromocao.setDescricao(scanner.next());

                    System.out.println("Qual o percentual da promoção? ");
                    modelPromocao.setPercentual(scanner.nextDouble());

                    System.out.println("Qual a situação do pruduto?");
                    modelPromocao.setAtivo(scanner.nextBoolean());

                    PromocaoDAO.salvar(modelPromocao);
                } else {
                    System.err.println("Produto não encontrado.");
                }

                menu();

            case 2:
                System.out.println("Qual id do produto será alterado?");
                promocao = ProdutoDAO.getProdutoPorId(scanner.nextInt());

                if(promocao != -1){
                    ModelProduto produto = new ModelProduto();
                    produto.setId(promocao);
                    modelPromocao.setProdutoId(produto);

                    System.out.println("Qual vai ser a alteração da descrição?");
                    modelPromocao.setDescricao(scanner.next());

                    System.out.println("Qual vai ser a alteração do percentual?");
                    modelPromocao.setPercentual(scanner.nextDouble());

                    System.out.println("Qual a situação da promoção?");
                    modelPromocao.setAtivo(scanner.nextBoolean());

                    System.out.println("Qual promoção será alterada?");
                    modelPromocao.setId(scanner.nextInt());

                    if (isIdValido(modelPromocao)){
                        System.out.println("Você quer prosseguir com a atualização? ");
                        String respostaAtualizar = scanner.next();

                        if(respostaAtualizar.equals("Sim") || respostaAtualizar.equals("sim")){
                            PromocaoDAO.atualizar(modelPromocao);
                            System.out.println("Promoção atualizada com sucesso!");

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
                    System.err.println("Promoção não encontrada.");
                }

                menu();

            case 3:
                System.out.println("Qual promoção será deletada? ");
                modelPromocao.setId(scanner.nextInt());

                if(isIdValido(modelPromocao)){
                    System.out.println("Você quer deletar essa promoção? ");
                    String respostaDeletar = scanner.next();

                    if (respostaDeletar.equals("Sim") || respostaDeletar.equals("sim")){
                        PromocaoDAO.deletar(modelPromocao);
                        System.out.println("Promoção deletada com sucesso!");
                    } else if (respostaDeletar.equals("Não") || respostaDeletar.equals("não")){
                        System.out.println("Redirecionando ao menu");
                        menu();
                    } else {
                        throw new Error("Essa informação é inválida!");
                    }
                } else {
                    throw new Error("Essa promoção não existe!");
                }

                menu();

            case 4:
                System.out.println("As promoções são:\n");
                PromocaoDAO listarPromocoes = new PromocaoDAO();

                for(ModelPromocao user : listarPromocoes.consultar()){
                    System.out.println("-----------------------------------");
                    System.out.println("ID: " + user.getId());
                    System.out.println("Produto ID: " + user.getProdutoId().getId());
                    System.out.println("Descrição: " + user.getDescricao());
                    System.out.println("Percentual: " + user.getPercentual());
                    System.out.println("Situação: " + user.isAtivo());
                }

                menu();

            case 0:
                System.exit(5);
        }
    }
}
