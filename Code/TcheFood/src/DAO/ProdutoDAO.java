package DAO;

import Model.ModelCategoriaProduto;
import Model.ProdutoModel;
import java.sql.Connection;
import infra.ConexaoMYSQL;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProdutoDAO 
{
    public void salvar(ProdutoModel produto)
    {
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("INSERT INTO tb_produto(categoria_produto_id, nome, descricao, preco, imagem) VALUES(?, ?, ?, ?, ?)");
            stmt.setInt(1, produto.getCategoriaProduto().getId());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setDouble(4, produto.getPreco());
            stmt.setString(5, produto.getImagem());
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletar(ProdutoModel produto)
    {
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("DELETE FROM tb_produto WHERE id = ?");
            stmt.setInt(1, produto.getId());
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
            
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void atualizar(ProdutoModel produto)
    {
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("UPDATE tb_produto SET categoria_produto_id = ?, nome = ?, descricao = ?, preco = ?, imagem = ? WHERE id = ?");
            stmt.setInt(1, produto.getCategoriaProduto().getId());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setDouble(4, produto.getPreco());
            stmt.setString(5, produto.getImagem());
            stmt.setInt(6, produto.getId());
            
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<ProdutoModel> consultar()
    {
        ArrayList<ProdutoModel> produtos = new ArrayList<ProdutoModel>();
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, categoria_produto_id, nome, descricao, preco, imagem FROM tb_produto");
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next())
            {
                ProdutoModel produto = new ProdutoModel();
                
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setImagem(rs.getString("imagem"));
                
                //pegar categoria do produto
                ModelCategoriaProduto categoriaProduto = CategoriaProdutoDAO.getCategoria(rs.getInt("categoria_produto_id"));
                                
                produto.setCategoriaProduto(categoriaProduto);
                
                produtos.add(produto);
            }
            
            stmt.close();
            conn.close();
            
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return produtos;
    }
    
    public ProdutoModel obterProdutoPorID(int id)
    {
        ProdutoModel produto = null;
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, categoria_produto_id, nome, descricao, preco, imagem FROM tb_produto WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            if(rs.next())
            {
                produto = new ProdutoModel();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setImagem(rs.getString("imagem"));
                
                //pegar categoria do produto
                ModelCategoriaProduto categoriaProduto = CategoriaProdutoDAO.getCategoria(rs.getInt("categoria_produto_id"));
                
                produto.setCategoriaProduto(categoriaProduto);
            }
            
            stmt.close();
            conn.close();
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return produto;
    }
    
    
    
    
    
    
    
    
    
    
    public ArrayList<ProdutoModel> pegarProdutosPorCategoriaComFiltro(int categoria, String filtro)
    {
        ArrayList<ProdutoModel> produtos = new ArrayList<>();
        System.out.println(filtro);
        
        try 
        {
          ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
          Connection conn = conexaoMYSQL.obterConexao();
          PreparedStatement stmt = null;
          ResultSet rs = null;
          
          //WHERE nome LIKE '%' mostra todos
          stmt = conn.prepareStatement("SELECT id, categoria_produto_id, nome, descricao, preco, imagem FROM tb_produto WHERE categoria_produto_id = ? AND nome LIKE ? LIMIT 50");
          stmt.setInt(1, categoria);
          stmt.setString(2, '%'+filtro+'%');
          rs = stmt.executeQuery();
          
          while(rs.next())
          {
            ProdutoModel produto = new ProdutoModel();
            produto.setId(rs.getInt("id"));

            //pegar categoria do produto
            ModelCategoriaProduto categoriaProduto = CategoriaProdutoDAO.getCategoria(rs.getInt("categoria_produto_id"));

            produto.setCategoriaProduto(categoriaProduto);
              
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setPreco(rs.getDouble("preco"));
            produto.setImagem(rs.getString("imagem"));

            produtos.add(produto);
          }
          
          stmt.close();
          conn.close();
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("Lenght no DAO:" + produtos.size());
        return produtos;
    }
    
    
    
    
    
    
    
    public ArrayList<ProdutoModel> pegarProdutosPorCategoria(int categoria)
    {
        ArrayList<ProdutoModel> produtos = new ArrayList<>();
                
        try 
        {
          ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
          Connection conn = conexaoMYSQL.obterConexao();
          PreparedStatement stmt = null;
          ResultSet rs = null;
          
          stmt = conn.prepareStatement("SELECT id, categoria_produto_id, nome, descricao, preco, imagem FROM tb_produto WHERE categoria_produto_id = ? LIMIT 50");
          stmt.setInt(1, categoria);
          rs = stmt.executeQuery();
          
          while(rs.next())
          {
              ProdutoModel produto = new ProdutoModel();
              produto.setId(rs.getInt("id"));
              
              ModelCategoriaProduto categoriaProduto = new ModelCategoriaProduto();
              categoriaProduto.setId(rs.getInt("categoria_produto_id"));
              //categoriaProduto.setDescricao --- pegar categoriaProduto, ver como eu fiz no tchefood gui
              produto.setCategoriaProduto(categoriaProduto);
              
              produto.setNome(rs.getString("nome"));
              produto.setDescricao(rs.getString("descricao"));
              produto.setPreco(rs.getDouble("preco"));
              produto.setImagem(rs.getString("imagem"));
              
              produtos.add(produto);
          }
          
          stmt.close();
          conn.close();
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return produtos;
    }
    
    public int obterTotalRegistros(String filtroCategoria, String filtroNome) throws ClassNotFoundException //depois usar filtros aqui tbm
    {
        int totalRegistros = 0;
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM tb_produto WHERE categoria_produto_id LIKE ? AND nome LIKE ?");
            stmt.setString(1, filtroCategoria);
            stmt.setString(2, filtroNome);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                totalRegistros = rs.getInt("COUNT(*)");
            }
            
            stmt.close();
            conn.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return totalRegistros;
    }
    
    public ArrayList<ProdutoModel> obterCardapio(int offset, String filtroCategoria, String filtroNome)
    {
        ArrayList<ProdutoModel> produtos = new ArrayList<ProdutoModel>();
        
        String trim = filtroNome.trim();
        filtroNome = "%" + trim + "%";
        //System.out.println("Filtro Nome: " + filtroNome);
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, categoria_produto_id, nome, descricao, preco, imagem FROM tb_produto WHERE categoria_produto_id LIKE ? AND nome LIKE ? LIMIT 16 OFFSET ?");
            stmt.setString(1, filtroCategoria);
            stmt.setString(2, filtroNome);
            stmt.setInt(3, offset);
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next())
            {
                ProdutoModel produto = new ProdutoModel();
                
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setImagem(rs.getString("imagem"));
                
                //pegar categoria do produto
                ModelCategoriaProduto categoriaProduto = CategoriaProdutoDAO.getCategoria(rs.getInt("categoria_produto_id"));
                                
                produto.setCategoriaProduto(categoriaProduto);
                
                produtos.add(produto);
            }
            
            stmt.close();
            conn.close();
            
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return produtos;
    }
    
    public ArrayList<ProdutoModel> filtrarProdutos(String categoria, String nomeDescricao, Double valorInicial, Double valorFinal)
    {
        ArrayList<ProdutoModel> produtos = new ArrayList<ProdutoModel>();
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            String trim = nomeDescricao.trim();
            nomeDescricao = "%" + trim + "%";
        
            String query = "SELECT id, categoria_produto_id, nome, descricao, preco, imagem FROM tb_produto WHERE CATEGORIA_PRODUTO_ID LIKE ? AND (NOME LIKE ? OR DESCRICAO LIKE ?)";
            
            if(valorInicial == null && valorFinal == null)
            {
                stmt = conn.prepareStatement(query);
                stmt.setString(1, categoria);
                stmt.setString(2, nomeDescricao);
                stmt.setString(3, nomeDescricao);
            }
            else if(valorInicial != null && valorFinal == null)
            {
                query += " AND preco >= ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, categoria);
                stmt.setString(2, nomeDescricao);
                stmt.setString(3, nomeDescricao);
                stmt.setDouble(4, valorInicial);
            }
            else if(valorInicial == null && valorFinal != null)
            {
                query += " AND preco <= ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, categoria);
                stmt.setString(2, nomeDescricao);
                stmt.setString(3, nomeDescricao);
                stmt.setDouble(4, valorFinal);
            }
            else if(valorInicial != null && valorFinal != null)
            {
                query += " AND preco BETWEEN ? AND ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, categoria);
                stmt.setString(2, nomeDescricao);
                stmt.setString(3, nomeDescricao);
                stmt.setDouble(4, valorInicial);
                stmt.setDouble(5, valorFinal);
            }
            
            

            stmt.executeQuery();
            
            //System.out.println(query + " categpria " + categoria + " nomeDesc " + nomeDescricao + " precoIni " + valorInicial + " precoFin " + valorFinal);
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next())
            {
                ProdutoModel produto = new ProdutoModel();
                
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setImagem(rs.getString("imagem"));
                
                //pegar categoria do produto
                ModelCategoriaProduto categoriaProduto = CategoriaProdutoDAO.getCategoria(rs.getInt("categoria_produto_id"));
                                
                produto.setCategoriaProduto(categoriaProduto);
                
                produtos.add(produto);
            }
            
            stmt.close();
            conn.close();
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return produtos;
    }
    
    public ArrayList<ProdutoModel> obterTodoCardapio()
    {
        ArrayList<ProdutoModel> produtos = new ArrayList<ProdutoModel>();
        
        try 
        {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection conn = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;
            
            stmt = conn.prepareStatement("SELECT id, categoria_produto_id, nome, descricao, preco, imagem FROM tb_produto");
            stmt.executeQuery();
            
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next())
            {
                ProdutoModel produto = new ProdutoModel();
                
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setImagem(rs.getString("imagem"));
                
                //pegar categoria do produto
                ModelCategoriaProduto categoriaProduto = CategoriaProdutoDAO.getCategoria(rs.getInt("categoria_produto_id"));
                                
                produto.setCategoriaProduto(categoriaProduto);
                
                produtos.add(produto);
            }
            
            stmt.close();
            conn.close();
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return produtos;
    }
}
