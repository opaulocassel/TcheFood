/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ItemVendaModel;
import Model.ModelFormaPagamento;
import Model.ModelItensPedido;
import Model.ModelPedido;
import Model.ModelUsuario;
import Model.ProdutoModel;
import infra.ConexaoMYSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adriano
 */
public class ItensPedidoDAO {
    
     public static void salvar(ModelItensPedido item) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("INSERT INTO tb_itens_pedido(pedido_id, produto_id, quantidade, opcional) VALUES (?, ?, ?, ?)");

            stmt.setInt(1, item.getPedidoId().getId());
            stmt.setInt(2, item.getProdutoId().getId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setString(4, item.getOpcional());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }

    }

    public static void atualizar(ModelItensPedido item) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("UPDATE tb_itens_pedido SET pedido_id = ?, produto_id = ?, quantidade = ?, opcional = ? WHERE id = ?");

            stmt.setInt(1, item.getPedidoId().getId());
            stmt.setInt(2, item.getProdutoId().getId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setString(4, item.getOpcional());
            stmt.setInt(5, item.getId());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }
    }

    public static void deletar(ModelItensPedido item) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("DELETE FROM tb_itens_pedido WHERE id = ?");

            stmt.setInt(1, item.getId());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (Exception e3){
            throw new RuntimeException(e3);
        }
    }


    public static ArrayList<ModelItensPedido> consultar(String pesquisa) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("SELECT id, pedido_id, produto_id, quantidade, opcional FROM tb_itens_pedido");

            ResultSet rs = stmt.executeQuery();

            ArrayList<ModelItensPedido> modelItensPedido = new ArrayList<>();

            while (rs.next()) {
                ModelItensPedido item = new ModelItensPedido();

                item.setId(rs.getInt("id"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setOpcional(rs.getString("opcional"));

                ModelPedido pedidoId = new ModelPedido();
                pedidoId.setId(rs.getInt("pedido_id"));
                item.setPedidoId(pedidoId);

                ProdutoModel produtoId = new ProdutoModel();
                produtoId.setId(rs.getInt("produto_id"));
                item.setProdutoId(produtoId);

                modelItensPedido.add(item);

            }
            return modelItensPedido;

        } catch (Exception e4){
            throw new RuntimeException(e4);
        }
    }


    public static boolean isIdValido(ModelItensPedido item) {
        try {
            ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
            Connection con = conexaoMsql.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("SELECT id FROM tb_itens_pedido WHERE id = ?");

            stmt.setInt(1, item.getId());
            ResultSet rs = stmt.executeQuery();

            boolean idExist = rs.next();

            return idExist;

        } 
        catch (Exception e5){
            throw new RuntimeException(e5);
        }
    }
    
    public static ArrayList<ModelItensPedido> carregarItensDoPedido(int idPedido)
    {
        ArrayList<ModelItensPedido> itensDoPedido = new ArrayList<>();
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, pedido_id, produto_id, quantidade, opcional FROM tb_itens_pedido WHERE pedido_id = ?");
            stmt.setInt(1, idPedido);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next())
            {
                ModelItensPedido itemPedido = new ModelItensPedido();

                itemPedido.setId(rs.getInt("id"));
                itemPedido.setQuantidade(rs.getInt("quantidade"));
                itemPedido.setOpcional(rs.getString("opcional"));

                ModelPedido pedido = PedidoDAO.carregarPedido(rs.getInt("pedido_id"));
                itemPedido.setPedidoId(pedido);

                ProdutoDAO produtoDAO = new ProdutoDAO();
                ProdutoModel produto = produtoDAO.obterProdutoPorID(rs.getInt("produto_id"));
                itemPedido.setProdutoId(produto);
                
                itensDoPedido.add(itemPedido);
                //System.out.println("Item: " + itemPedido.getId() + " Nome: " + itemPedido.getProdutoId().getNome());
            }
            
            stmt.close();
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
        
        return itensDoPedido;
    }
    
    
    public static ModelItensPedido obterItem(int idItem)
    {
        ModelItensPedido itemPedido = null;
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, pedido_id, produto_id, quantidade, opcional FROM tb_itens_pedido WHERE id = ?");
            stmt.setInt(1, idItem);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            if(rs.next())
            {
                itemPedido = new ModelItensPedido();

                itemPedido.setId(rs.getInt("id"));
                itemPedido.setQuantidade(rs.getInt("quantidade"));
                itemPedido.setOpcional(rs.getString("opcional"));

                ModelPedido pedido = PedidoDAO.carregarPedido(rs.getInt("pedido_id"));
                itemPedido.setPedidoId(pedido);

                ProdutoDAO produtoDAO = new ProdutoDAO();
                ProdutoModel produto = produtoDAO.obterProdutoPorID(rs.getInt("produto_id"));
                itemPedido.setProdutoId(produto);
                
            }
            
            stmt.close();
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
        
        return itemPedido;
    }
    
    public static ArrayList<ItemVendaModel> ordenarMaisVendidos(String categoria, String nome)
    {
        
        ArrayList<ItemVendaModel> itensVendas = new ArrayList<>();
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            String trim = nome.trim();
            nome = "%" + trim + "%";

            //stmt = conn.prepareStatement("SELECT P.ID, P.NOME, P.CATEGORIA_PRODUTO_ID, SUM(I.QUANTIDADE) AS QUANTIDADE, (P.PRECO * SUM(I.QUANTIDADE)) AS VALOR FROM TB_ITENS_PEDIDO I INNER JOIN TB_PRODUTO P ON I.PRODUTO_ID = P.ID GROUP BY P.ID HAVING P.NOME LIKE ? AND P.CATEGORIA_PRODUTO_ID LIKE ? ORDER BY QUANTIDADE DESC, VALOR DESC");
            stmt = conn.prepareStatement("SELECT P.ID, P.NOME, P.CATEGORIA_PRODUTO_ID, SUM(I.QUANTIDADE) AS QUANTIDADE, (P.PRECO * SUM(I.QUANTIDADE)) AS VALOR FROM TB_ITENS_PEDIDO I INNER JOIN TB_PRODUTO P ON I.PRODUTO_ID = P.ID WHERE P.NOME LIKE ? AND P.CATEGORIA_PRODUTO_ID LIKE ? GROUP BY P.ID ORDER BY QUANTIDADE DESC, VALOR DESC");
            //stmt = conn.prepareStatement("SELECT P.ID, P.NOME, SUM(I.QUANTIDADE) AS QUANTIDADE, (P.PRECO * QUANTIDADE) AS VALOR FROM TB_ITENS_PEDIDO I INNER JOIN TB_PRODUTO P ON I.PRODUTO_ID = P.ID GROUP BY P.ID ORDER BY QUANTIDADE DESC, VALOR DESC");
            //stmt = conn.prepareStatement("SELECT P.ID, P.NOME, SUM(I.QUANTIDADE) AS QUANTIDADE, (P.PRECO * QUANTIDADE) AS VALOR FROM TB_ITENS_PEDIDO I INNER JOIN TB_PRODUTO P ON I.PRODUTO_ID = P.ID GROUP BY P.ID");
            stmt.setString(1, nome);
            stmt.setString(2, categoria);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();

            while(rs.next())
            {
                ItemVendaModel itemVenda = new ItemVendaModel();

                itemVenda.setIdProduto(rs.getInt("P.ID"));
                itemVenda.setNomeProduto(rs.getString("P.NOME"));
                itemVenda.setQuantidadeVendida(rs.getInt("QUANTIDADE"));
                itemVenda.setValorVendido(rs.getDouble("VALOR"));

                itensVendas.add(itemVenda);
            }
         
            stmt.close();
            conn.close();
        } 
        catch (SQLException | ClassNotFoundException ex) 
        {
            Logger.getLogger(ItensPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return itensVendas;
    }
    
    public static ArrayList<ItemVendaModel> ordenarMaisValor(String categoria, String nome)
    {
        
        ArrayList<ItemVendaModel> itensVendas = new ArrayList<>();
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            String trim = nome.trim();
            nome = "%" + trim + "%";

            //stmt = conn.prepareStatement("SELECT P.ID, P.NOME, P.CATEGORIA_PRODUTO_ID, SUM(I.QUANTIDADE) AS QUANTIDADE, (P.PRECO * SUM(I.QUANTIDADE)) AS VALOR FROM TB_ITENS_PEDIDO I INNER JOIN TB_PRODUTO P ON I.PRODUTO_ID = P.ID GROUP BY P.ID HAVING P.NOME LIKE ? AND P.CATEGORIA_PRODUTO_ID LIKE ? ORDER BY VALOR DESC, QUANTIDADE DESC");
            stmt = conn.prepareStatement("SELECT P.ID, P.NOME, P.CATEGORIA_PRODUTO_ID, SUM(I.QUANTIDADE) AS QUANTIDADE, (P.PRECO * SUM(I.QUANTIDADE)) AS VALOR FROM TB_ITENS_PEDIDO I INNER JOIN TB_PRODUTO P ON I.PRODUTO_ID = P.ID WHERE P.NOME LIKE ? AND P.CATEGORIA_PRODUTO_ID LIKE ? GROUP BY P.ID ORDER BY VALOR DESC, QUANTIDADE DESC");
            stmt.setString(1, nome);
            stmt.setString(2, categoria);
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();

            while(rs.next())
            {
                ItemVendaModel itemVenda = new ItemVendaModel();

                itemVenda.setIdProduto(rs.getInt("P.ID"));
                itemVenda.setNomeProduto(rs.getString("P.NOME"));
                itemVenda.setQuantidadeVendida(rs.getInt("QUANTIDADE"));
                itemVenda.setValorVendido(rs.getDouble("VALOR"));

                itensVendas.add(itemVenda);
            }
            
            stmt.close();
            conn.close();
         
        } 
        catch (SQLException | ClassNotFoundException ex) 
        {
            Logger.getLogger(ItensPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return itensVendas;
    }
    
    public static ArrayList<ModelItensPedido> obterTodos()
    {
        ArrayList<ModelItensPedido> itensDoPedido = new ArrayList<>();
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, pedido_id, produto_id, quantidade, opcional FROM tb_itens_pedido");
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next())
            {
                ModelItensPedido itemPedido = new ModelItensPedido();

                itemPedido.setId(rs.getInt("id"));
                itemPedido.setQuantidade(rs.getInt("quantidade"));
                itemPedido.setOpcional(rs.getString("opcional"));

                ModelPedido pedido = PedidoDAO.obterPedidoPorId(rs.getInt("pedido_id"));
                itemPedido.setPedidoId(pedido);

                ProdutoDAO produtoDAO = new ProdutoDAO();
                ProdutoModel produto = produtoDAO.obterProdutoPorID(rs.getInt("produto_id"));
                itemPedido.setProdutoId(produto);
                
                itensDoPedido.add(itemPedido);
                //System.out.println("Item: " + itemPedido.getId() + " Nome: " + itemPedido.getProdutoId().getNome());
            }
            
            stmt.close();
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
        
        return itensDoPedido;
    }
}
