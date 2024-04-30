/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.ModelFormaPagamento;
import Model.ModelPedido;
import Model.ModelUsuario;
import infra.ConexaoMYSQL;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author adriano
 */
public class PedidoDAO {
     public static void salvar(ModelPedido pedido) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("INSERT INTO tb_pedido(usuario_id, forma_pagamento_id, status_pagamento, status_pedido, total) VALUES (?, ?, ?, ?, ?)");

            stmt.setInt(1, pedido.getUsuarioId().getId());
            stmt.setInt(2, pedido.getFormaPagamentoId().getId());
            stmt.setInt(3, pedido.getStatusPagamento());
            stmt.setInt(4, pedido.getStatusPedido());
            stmt.setDouble(5, pedido.getTotal());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }
    }

    public static void atualizar(ModelPedido pedido) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("UPDATE tb_pedido SET usuario_id = ?, forma_pagamento_id = ?, data_hora = ?, status_pagamento = ?, status_pedido = ?, total = ? WHERE id = ?");

            stmt.setInt(1, pedido.getUsuarioId().getId());
            stmt.setInt(2, pedido.getFormaPagamentoId().getId());
            stmt.setDate(3, (Date) pedido.getDataHora());
            stmt.setInt(4, pedido.getStatusPagamento());
            stmt.setInt(5, pedido.getStatusPedido());
            stmt.setDouble(6, pedido.getTotal());
            stmt.setInt(7, pedido.getId());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }
    }

    public static void deletar(ModelPedido pedido) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("DELETE FROM tb_pedido WHERE id = ?");

            stmt.setInt(1, pedido.getId());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (Exception e3){
            throw new RuntimeException(e3);
        }
    }


    public static ArrayList<ModelPedido> consultar(String lista) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            String SQL = "SELECT id, usuario_id, forma_pagamento_id, data_hora, status_pagamento, status_pedido, total FROM tb_pedido";
            
            if(!lista.isEmpty()){
                SQL += " WHERE data_hora LIKE ?";
            }
            
            stmt = con.prepareStatement(SQL);
            
            if(!lista.isEmpty()){
                stmt.setString(1, "%" + lista + "%");
            }
            
            ResultSet rs = stmt.executeQuery();

            ArrayList<ModelPedido> modelPedido = new ArrayList<>();

            while (rs.next()) {
                ModelPedido pedido = new ModelPedido();

                pedido.setId(rs.getInt("id"));
                pedido.setDataHora(rs.getDate("data_hora"));
                pedido.setStatusPagamento(rs.getInt("status_pagamento"));
                pedido.setStatusPedido(rs.getInt("status_pedido"));
                pedido.setTotal(rs.getDouble("total"));

                ModelUsuario usuarioId = new ModelUsuario();
                usuarioId.setId(rs.getInt("usuario_id"));

                pedido.setUsuarioId(usuarioId);

                ModelFormaPagamento pagamentoID = new ModelFormaPagamento();
                pagamentoID.setId(rs.getInt("forma_pagamento_id"));

                pedido.setFormaPagamentoId(pagamentoID);
                modelPedido.add(pedido);

            }
            return modelPedido;

        } catch (Exception e4){
            throw new RuntimeException(e4);
        }
    }


    public static boolean isIdValido(ModelPedido pedido) {
        try {
            ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
            Connection con = conexaoMsql.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("SELECT id FROM tb_pedido WHERE id = ?");

            stmt.setInt(1, pedido.getId());
            ResultSet rs = stmt.executeQuery();

            boolean idExist = rs.next();

            return idExist;

        } catch (Exception e5){
            throw new RuntimeException(e5);
        }
    }


    public static int getPedidoPorId(int pedidoId) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int categoria = -1;

        stmt = con.prepareStatement("SELECT id FROM tb_pedido WHERE id = ?");
        stmt.setInt(1, pedidoId);
        rs = stmt.executeQuery();

        if (rs.next()) {
            categoria = rs.getInt("id");
        }

        return categoria;

        } catch(Exception e6){
            throw new RuntimeException(e6);
        }
    }
    
    
    public static ModelPedido obterPedidoPorId(int idPedido)
    {
        ModelPedido pedido = null;
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, usuario_id, forma_pagamento_id, data_hora, status_pagamento, status_pedido, total FROM tb_pedido WHERE id = ?");
            stmt.setInt(1, idPedido);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            if(rs.next())
            {
                pedido = new ModelPedido();

                pedido.setId(rs.getInt("id"));
                pedido.setDataHora(rs.getDate("data_hora"));
                pedido.setStatusPagamento(rs.getInt("status_pagamento"));
                pedido.setStatusPedido(rs.getInt("status_pedido"));
                pedido.setTotal(rs.getDouble("total"));

                //mudei aqui pra pegar o usuario completo, pq quis por o nome no textFieldUsuario no pedido, antes ficava null
                ModelUsuario usuarioId = DAOUsuario.obterUsuarioPorID(rs.getInt("usuario_id"));
                //usuarioId.setId(rs.getInt("usuario_id"));

                pedido.setUsuarioId(usuarioId);

                //mudei aqui, mesma coisa de cima, pra pegar o objeto ModelFormaPagamento pra pegar o nome/descreicao tbm
                ModelFormaPagamento pagamentoID = FormaPagamentoDAO.obterFormaPagamento(rs.getInt("forma_pagamento_id"));
                //pagamentoID.setId(rs.getInt("forma_pagamento_id"));
                pedido.setFormaPagamentoId(pagamentoID);
            }
            
            conn.close();
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("Class not found");
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQL Exception");
        }
        
        return pedido;
    }
    
    
    public static ModelPedido carregarPedido(int idUsuario)
    {
        ModelPedido pedido = null;
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, usuario_id, forma_pagamento_id, data_hora, status_pagamento, status_pedido, total FROM tb_pedido WHERE usuario_id = ? AND status_pedido = 0");
            stmt.setInt(1, idUsuario);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            if(rs.next())
            {
                pedido = new ModelPedido();

                pedido.setId(rs.getInt("id"));
                pedido.setDataHora(rs.getDate("data_hora"));
                pedido.setStatusPagamento(rs.getInt("status_pagamento"));
                pedido.setStatusPedido(rs.getInt("status_pedido"));
                pedido.setTotal(rs.getDouble("total"));

                //mudei aqui pra pegar o usuario completo, pq quis por o nome no textFieldUsuario no pedido, antes ficava null
                ModelUsuario usuarioId = DAOUsuario.obterUsuarioPorID(rs.getInt("usuario_id"));
                //usuarioId.setId(rs.getInt("usuario_id"));

                pedido.setUsuarioId(usuarioId);

                //mudei aqui, mesma coisa de cima, pra pegar o objeto ModelFormaPagamento pra pegar o nome/descreicao tbm
                ModelFormaPagamento pagamentoID = FormaPagamentoDAO.obterFormaPagamento(rs.getInt("forma_pagamento_id"));
                //pagamentoID.setId(rs.getInt("forma_pagamento_id"));
                pedido.setFormaPagamentoId(pagamentoID);
            }
            
            conn.close();
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("Class not found");
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQL Exception");
        }
        
        return pedido;
    }
    
       
    public static int checarPedidoPendente(int idUsuario) throws ClassNotFoundException //depois usar filtros aqui tbm
    {
        int totalRegistros = 0;
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM TB_PEDIDO WHERE USUARIO_ID = ? AND STATUS_PEDIDO = 0;");
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                totalRegistros = rs.getInt("COUNT(*)");
            }
            
            conn.close();
        } 
        catch (SQLException ex) 
        {
            java.util.logging.Logger.getLogger(ProdutoDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        return totalRegistros;
    }
    
    public static void deletarPedidoPendente(int idUsuario) 
    {
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("DELETE FROM tb_pedido WHERE usuario_id = ? AND status_pedido = 0");
            stmt.setInt(1, idUsuario);

            stmt.executeUpdate();

            stmt.close();
            con.close();
        } 
        catch (Exception e3)
        {
            throw new RuntimeException(e3);
        }
    }

    public static int obterIdUltimoPedido() throws ClassNotFoundException
    {
        int id = -1;
        
         try 
         {
             ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
             Connection conn = conexaoMYSQL.obterConexao();
             PreparedStatement stmt = null;
             
             stmt = conn.prepareStatement("SELECT MAX(id) FROM tb_pedido");
             
             ResultSet rs = stmt.executeQuery();
             
            if(rs.next())
            {
                id = rs.getInt("MAX(id)");
            }
            
            conn.close();
         } 
         catch (SQLException ex) 
         {
             java.util.logging.Logger.getLogger(PedidoDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         }
         
         return id;
    }
    
    public static ModelPedido carregarUltimoPedido()
    {
        ModelPedido pedido = null;
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, usuario_id, forma_pagamento_id, data_hora, status_pagamento, status_pedido, total FROM tb_pedido ORDER BY id DESC LIMIT 1");
            //stmt = conn.prepareStatement("SELECT id, usuario_id, forma_pagamento_id, data_hora, status_pagamento, status_pedido, total FROM tb_pedido WHERE id = (SELECT MAX(id) FROM tb_pedido");
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            if(rs.next())
            {
                pedido = new ModelPedido();

                pedido.setId(rs.getInt("id"));
                pedido.setDataHora(rs.getDate("data_hora"));
                pedido.setStatusPagamento(rs.getInt("status_pagamento"));
                pedido.setStatusPedido(rs.getInt("status_pedido"));
                pedido.setTotal(rs.getDouble("total"));

                //mudei aqui pra pegar o usuario completo, pq quis por o nome no textFieldUsuario no pedido, antes ficava null
                ModelUsuario usuarioId = DAOUsuario.obterUsuarioPorID(rs.getInt("usuario_id"));
                //usuarioId.setId(rs.getInt("usuario_id"));

                pedido.setUsuarioId(usuarioId);

                //mudei aqui, mesma coisa de cima, pra pegar o objeto ModelFormaPagamento pra pegar o nome/descreicao tbm
                ModelFormaPagamento pagamentoID = FormaPagamentoDAO.obterFormaPagamento(rs.getInt("forma_pagamento_id"));
                //pagamentoID.setId(rs.getInt("forma_pagamento_id"));
                pedido.setFormaPagamentoId(pagamentoID);
            }
            
            conn.close();
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("Class not found");
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQL Exception");
        }
        
        return pedido;
    }
}
