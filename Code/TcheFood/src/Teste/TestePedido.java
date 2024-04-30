package Teste;

import DAO.*;
import Model.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

public class TestePedido {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        menu();
    }

    public static void menu() throws SQLException, ClassNotFoundException, ParseException {

        Scanner scanner = new Scanner(System.in);

        ModelPedido pedido = new ModelPedido();
        int usuario;
        int formaPagamento;
        String dataString;

        System.out.println("\nEscolha uma das opções abaixo");
        System.out.println("1 - Adicionar pedido");
        System.out.println("2 - Atualizar pedido");
        System.out.println("3 - Deletar pedido");
        System.out.println("4 - Listar pedidos");
        System.out.println("0 - Sair");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.println("Informe o id do usuário: ");
                usuario = UsuarioDAO.getUsuarioPorId(scanner.nextInt());

                System.out.println("Informe o id da forma de pagamento: ");
                formaPagamento = FormaPagamentoDAO.getFormaPagamentoId(scanner.nextInt());


                if (usuario != -1 && formaPagamento != -1) {
                    ModelFormaPagamento forma = new ModelFormaPagamento();
                    forma.setId(formaPagamento);
                    pedido.setFormaPagamentoId(forma);

                    ModelUsuario usuario2 = new ModelUsuario();
                    usuario2.setId(usuario);
                    pedido.setUsuarioId(usuario2);

                    /////////////////////////////////////////////////////////////////////////////

                    System.out.println("Qual é a data? ");
                    dataString = scanner.next();
                    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataFormatada = formatoData.parse(dataString);

                    java.sql.Date dataSql = new java.sql.Date(dataFormatada.getTime());
                    pedido.setDataHora(dataSql);

                    /////////////////////////////////////////////////////////////////////////////

                    System.out.println("status do pagamento: ");
                    pedido.setStatusPagamento(scanner.nextInt());

                    System.out.println("status do pedido: ");
                    pedido.setStatusPedido(scanner.nextInt());

                    System.out.println("Total: ");
                    pedido.setTotal(scanner.nextDouble());

                    PedidoDAO.salvar(pedido);
                } else {
                    System.err.println("não encontrado.");
                }

                menu();
        }
    }
}
