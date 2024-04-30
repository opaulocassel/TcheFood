/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ModelFormaPagamento;
import Model.ModelPedido;
import Model.ModelUsuario;
import infra.ConexaoMYSQL;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author adriano
 */
public class FormaPagamentoDAO {
     public static void salvar(ModelFormaPagamento pagamento) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("INSERT INTO tb_forma_pagamento(descricao) VALUES (?)");

        stmt.setString(1, pagamento.getDescricao());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }
    }

    public static void atualizar(ModelFormaPagamento pagamento) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("UPDATE tb_forma_pagamento SET descricao = ? WHERE id = ?");

        stmt.setString(1, pagamento.getDescricao());
        stmt.setInt(2, pagamento.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e2){
            throw new RuntimeException(e2);
        }
    }

    public static void deletar(ModelFormaPagamento pagamento) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("DELETE FROM tb_forma_pagamento WHERE id = ?");

        stmt.setInt(1, pagamento.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e3){
            throw new RuntimeException(e3);
        }
    }

    public static ArrayList<ModelFormaPagamento> consultar(String a) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id, nome, descricao FROM tb_forma_pagamento");
        
        ResultSet rs = stmt.executeQuery();

        ArrayList<ModelFormaPagamento> formaPagamento = new ArrayList<>();

        while(rs.next()){
            ModelFormaPagamento modelFormaPagamento = new ModelFormaPagamento();

            modelFormaPagamento.setId(rs.getInt("id"));
            modelFormaPagamento.setNome(rs.getString("nome"));
            modelFormaPagamento.setDescricao(rs.getString("descricao"));

            formaPagamento.add(modelFormaPagamento);
        }
        return formaPagamento;

        } catch(Exception e4){
            throw new RuntimeException(e4);
        }
    }

    public static boolean isIdValido(ModelFormaPagamento pagamento) {
        try {
        ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
        Connection con = conexaoMsql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id FROM tb_forma_pagamento WHERE id = ?");

        stmt.setInt(1, pagamento.getId());
        ResultSet rs = stmt.executeQuery();

        boolean idExist = rs.next();

        return idExist;

        } catch(Exception e5){
            throw new RuntimeException(e5);
        }
    }


    public static int getFormaPagamentoId(int formaPagamentoId) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int categoria = -1;

        stmt = con.prepareStatement("SELECT id FROM tb_forma_pagamento WHERE id = ?");
        stmt.setInt(1, formaPagamentoId);
        rs = stmt.executeQuery();

        if (rs.next()) {
            categoria = rs.getInt("id");
        }

        return categoria;

        } catch(Exception e6){
            throw new RuntimeException(e6);
        }
    }
    
    public static ModelFormaPagamento obterFormaPagamento(int id)
    {
        ModelFormaPagamento formaPagamento = null;
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, descricao FROM tb_forma_pagamento WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            if(rs.next())
            {
                formaPagamento = new ModelFormaPagamento();

                formaPagamento.setId(rs.getInt("id"));
                formaPagamento.setDescricao(rs.getString("descricao"));
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("Class not found");
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQL Exception");
        }
        
        return formaPagamento;
    }
    
    public static ArrayList<ModelFormaPagamento> obterTodasFormasPagamento()
    {
        ArrayList<ModelFormaPagamento> formasPagamento = new ArrayList<>();
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, descricao FROM tb_forma_pagamento");
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next())
            {
                ModelFormaPagamento formaPagamento = new ModelFormaPagamento();

                formaPagamento.setId(rs.getInt("id"));
                formaPagamento.setDescricao(rs.getString("descricao"));
                
                formasPagamento.add(formaPagamento);
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("Class not found");
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQL Exception");
        }
        
        return formasPagamento;
    }
    
}
