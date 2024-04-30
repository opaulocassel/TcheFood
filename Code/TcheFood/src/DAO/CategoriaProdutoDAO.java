/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import Model.ModelCategoriaProduto;
import infra.ConexaoMYSQL;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author adriano
 */
public class CategoriaProdutoDAO {
    
     public static void salvar(ModelCategoriaProduto categoriaProduto) throws SQLException, ClassNotFoundException {
       
         ConexaoMYSQL conexaoMysql = new ConexaoMYSQL();
        Connection con = conexaoMysql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("INSERT INTO tb_categoria_produto(descricao) VALUES (?)");

        stmt.setString(1, categoriaProduto.getDescricao());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        
    }

    public static void atualizar(ModelCategoriaProduto categoriaProduto) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("UPDATE tb_categoria_produto SET descricao = ? WHERE id = ?");

        stmt.setString(1, categoriaProduto.getDescricao());
        stmt.setInt(2, categoriaProduto.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e2){
            throw new RuntimeException(e2);
        }
    }

    public static void deletar(ModelCategoriaProduto categoriaProduto) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("DELETE FROM tb_categoria_produto WHERE id = ?");

        stmt.setInt(1, categoriaProduto.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e3){
            throw new RuntimeException(e3);
        }
    }

    public static ArrayList<ModelCategoriaProduto> consultar(String consulta) throws ClassNotFoundException, SQLException {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;
        
        stmt = con.prepareStatement("SELECT id, descricao FROM tb_categoria_produto WHERE descricao LIKE ?");
        stmt.setString(1, "%" + consulta + "%");
        ResultSet rs = stmt.executeQuery();

        ArrayList<ModelCategoriaProduto> categoriaProduto = new ArrayList<>();

        while(rs.next()){
            ModelCategoriaProduto modelCategoriaProduto = new ModelCategoriaProduto();

            modelCategoriaProduto.setId(rs.getInt("id"));
            modelCategoriaProduto.setDescricao(rs.getString("descricao"));

            categoriaProduto.add(modelCategoriaProduto);
        }
        return categoriaProduto;
    }

    public static boolean isIdValido(ModelCategoriaProduto categoriaProduto) {
        try {
        ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
        Connection con = conexaoMsql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id FROM tb_categoria_produto WHERE id = ?");

        stmt.setInt(1, categoriaProduto.getId());
        ResultSet rs = stmt.executeQuery();

        boolean idExist = rs.next();

        return idExist;

        } catch(Exception e5){
            throw new RuntimeException(e5);
        }
    }

    public static int getCategoriaPorId(int clienteId) throws SQLException, ClassNotFoundException {
       
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int categoria = -1;

        stmt = con.prepareStatement("SELECT id FROM tb_categoria_produto WHERE id = ?");
        stmt.setInt(1, clienteId);
        rs = stmt.executeQuery();

        if (rs.next()) {
            categoria = rs.getInt("id");
        }

        return categoria;

        
    }
    
    public static ArrayList<ModelCategoriaProduto> obterTodasCategorias() {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id, descricao FROM tb_categoria_produto");

        ResultSet rs = stmt.executeQuery();

        ArrayList<ModelCategoriaProduto> categoriaProduto = new ArrayList<>();

        while(rs.next()){
            ModelCategoriaProduto modelCategoriaProduto = new ModelCategoriaProduto();

            modelCategoriaProduto.setId(rs.getInt("id"));
            modelCategoriaProduto.setDescricao(rs.getString("descricao"));

            categoriaProduto.add(modelCategoriaProduto);
        }
        return categoriaProduto;

        } catch(Exception e4){
            throw new RuntimeException(e4);
        }
    }
    
    public static ModelCategoriaProduto getCategoria(int idCategoria) throws SQLException, ClassNotFoundException {
       
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ModelCategoriaProduto categoria = null;

        stmt = con.prepareStatement("SELECT id, descricao FROM tb_categoria_produto WHERE id = ?");
        stmt.setInt(1, idCategoria);
        rs = stmt.executeQuery();

        if (rs.next()) {
            categoria = new ModelCategoriaProduto();
            categoria.setId(rs.getInt("id"));
            categoria.setDescricao(rs.getString("descricao"));
        }

        return categoria;
    }
}
