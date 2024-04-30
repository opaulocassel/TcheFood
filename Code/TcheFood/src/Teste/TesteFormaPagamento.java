package Teste;

import DAO.FormaPagamentoDAO;
import Model.ModelFormaPagamento;

import java.util.Scanner;

public class TesteFormaPagamento {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        ModelFormaPagamento paga = new ModelFormaPagamento();


        paga.setDescricao(scanner.next());
    }
}
