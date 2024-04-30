package Teste;
import DAO.CategoriaProdutoDAO;
import Model.ModelCategoriaProduto;
import java.sql.SQLException;
import java.util.Scanner;
import static DAO.CategoriaProdutoDAO.isIdValido;
public class TesteCategoriaProduto {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        menu();
    }

    public static void menu() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        ModelCategoriaProduto modelCategoriaProduto = new ModelCategoriaProduto();
        CategoriaProdutoDAO categoriaProdutoDAO = new CategoriaProdutoDAO();

        System.out.println("\nEscolha uma das opções abaixo");
        System.out.println("1 - Adicionar descrição do produto");
        System.out.println("2 - Atualizar descrição do produto");
        System.out.println("3 - Deletar descrição do produto");
        System.out.println("4 - Listar categorias dos produtos");
        System.out.println("0 - Sair");

        int option = scanner.nextInt();

        switch(option){
            case 1:
                System.out.println("Informe a descrição do produto: ");
                modelCategoriaProduto.setDescricao(scanner.next());

                System.out.println("Você quer seguir com essa descrição? ");
                String respostaSalvar = scanner.next();

                if(respostaSalvar.equals("Sim") || respostaSalvar.equals("sim")){
                    categoriaProdutoDAO.salvar(modelCategoriaProduto);
                    System.out.println("Descrição inserida com sucesso!");
                } else if (respostaSalvar.equals("Não") || respostaSalvar.equals("não")){
                    System.out.println("Redirecionando ao menu");
                    menu();
                } else {
                    throw new Error("Essa informação é inválida");
                }

                menu();

            case 2:
                System.out.println("Atualização da categoria: ");
                modelCategoriaProduto.setDescricao(scanner.next());
                System.out.println("Qual categoria terá a descrição alterada?");
                modelCategoriaProduto.setId(scanner.nextInt());

                if(isIdValido(modelCategoriaProduto)){
                    System.out.println("Você quer prosseguir com a atualização? ");
                    String respostaAtualizar = scanner.next();

                    if(respostaAtualizar.equals("Sim") || respostaAtualizar.equals("sim")){
                        categoriaProdutoDAO.atualizar(modelCategoriaProduto);
                        System.out.println("Descrição atualizada com sucesso!");
                    } else if (respostaAtualizar.equals("Não") || respostaAtualizar.equals("não")){
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
                System.out.println("Qual categoria será deletada? ");
                modelCategoriaProduto.setId(scanner.nextInt());

                if(isIdValido(modelCategoriaProduto)){
                    System.out.println("Você quer deletar essa categoria? ");
                    String respostaDeletar = scanner.next();

                    if (respostaDeletar.equals("Sim") || respostaDeletar.equals("sim")){
                        categoriaProdutoDAO.deletar(modelCategoriaProduto);
                        System.out.println("Categoria deletada com sucesso!");
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
                System.out.println("As categorias são: ");

                for(ModelCategoriaProduto categoria : categoriaProdutoDAO.consultar()){
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
