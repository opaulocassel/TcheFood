package DAO;

import Infra.ConexaoMYSQL;
import Model.ModelProduto;
import Model.ModelPromocao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromocaoDAO {

    public static void salvar(ModelPromocao promocao) {
        try {
        ConexaoMYSQL conexaoMysql = new ConexaoMYSQL();
        Connection con = conexaoMysql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("INSERT INTO tb_promocao(produto_id, descricao, percentual, ativo) VALUES (?, ?, ?, ?)");

        stmt.setInt(1, promocao.getProdutoId().getId());
        stmt.setString(2, promocao.getDescricao());
        stmt.setDouble(3, promocao.getPercentual());
        stmt.setBoolean(4, promocao.isAtivo());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }
    }

    public static void atualizar(ModelPromocao promocao) {
        try {
        ConexaoMYSQL conexaoMysql = new ConexaoMYSQL();
        Connection con = conexaoMysql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("UPDATE tb_promocao SET produto_id = ?, descricao = ?, percentual = ?, ativo = ? WHERE id = ?");

        stmt.setInt(1, promocao.getProdutoId().getId());
        stmt.setString(2, promocao.getDescricao());
        stmt.setDouble(3, promocao.getPercentual());
        stmt.setBoolean(4, promocao.isAtivo());
        stmt.setInt(5, promocao.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e2){
            throw new RuntimeException(e2);
        }
    }

    public static void deletar(ModelPromocao promocao) {
        try {
        ConexaoMYSQL conexaoMysql = new ConexaoMYSQL();
        Connection con = conexaoMysql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("DELETE FROM tb_promocao WHERE id = ?");

        stmt.setInt(1, promocao.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e3){
            throw new RuntimeException(e3);
        }
    }

    public static ArrayList<ModelPromocao> consultar() {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id, produto_id, descricao, percentual, ativo FROM tb_promocao");

        ResultSet rs = stmt.executeQuery();

        ArrayList<ModelPromocao> promocao = new ArrayList<>();

        while(rs.next()){
            ModelPromocao modelPromocao = new ModelPromocao();

            modelPromocao.setId(rs.getInt("id"));
            modelPromocao.setDescricao(rs.getString("descricao"));
            modelPromocao.setPercentual(rs.getDouble("percentual"));
            modelPromocao.setAtivo(rs.getBoolean("ativo"));

            ModelProduto produto = new ModelProduto();
            produto.setId(rs.getInt("produto_id"));

            modelPromocao.setProdutoId(produto);

            promocao.add(modelPromocao);
        }

        return promocao;

        } catch(Exception e4){
            throw new RuntimeException(e4);
        }
    }

    public static boolean isIdValido(ModelPromocao promocao) {
        try {
        ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
        Connection con = conexaoMsql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id FROM tb_promocao WHERE id = ?");

        stmt.setInt(1, promocao.getId());
        ResultSet rs = stmt.executeQuery();

        boolean idExist = rs.next();

        return idExist;

        } catch(Exception e5){
            throw new RuntimeException(e5);
        }
    }

}
