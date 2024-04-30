package DAO;

import Infra.ConexaoMYSQL;
import Model.ModelCategoriaEndereco;
import Model.ModelCategoriaProduto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaEnderecoDAO {

    public static void salvar(ModelCategoriaEndereco endereco) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("INSERT INTO tb_categoria_endereco(descricao) VALUES (?)");

            stmt.setString(1, endereco.getDescricao());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }
    }

    public static void atualizar(ModelCategoriaEndereco endereco) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("UPDATE tb_categoria_endereco SET descricao = ? WHERE id = ?");

        stmt.setString(1, endereco.getDescricao());
        stmt.setInt(2, endereco.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e2){
            throw new RuntimeException(e2);
        }
    }

    public static void deletar(ModelCategoriaEndereco endereco) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("DELETE FROM tb_categoria_endereco WHERE id = ?");

        stmt.setInt(1, endereco.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e3){
            throw new RuntimeException(e3);
        }
    }

    public static ArrayList<ModelCategoriaEndereco> consultar() {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id, descricao FROM tb_categoria_endereco");

        ResultSet rs = stmt.executeQuery();

        ArrayList<ModelCategoriaEndereco> categoriaEndereco = new ArrayList<>();

        while(rs.next()){
            ModelCategoriaEndereco modelCategoriaEndereco = new ModelCategoriaEndereco();

            modelCategoriaEndereco.setId(rs.getInt("id"));
            modelCategoriaEndereco.setDescricao(rs.getString("descricao"));

            categoriaEndereco.add(modelCategoriaEndereco);
        }
        return categoriaEndereco;

        } catch(Exception e4){
            throw new RuntimeException(e4);
        }
    }

    public static boolean isIdValido(ModelCategoriaEndereco endereco) {
        try {
        ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
        Connection con = conexaoMsql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id FROM tb_categoria_endereco WHERE id = ?");

        stmt.setInt(1, endereco.getId());
        ResultSet rs = stmt.executeQuery();

        boolean idExist = rs.next();

        return idExist;

        } catch(Exception e5){
            throw new RuntimeException(e5);
        }
    }

    public static int getCategoriaEnderecoPorId(int categoriaEnderecoId) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int categoria = -1;

        stmt = con.prepareStatement("SELECT id FROM tb_categoria_endereco WHERE id = ?");
        stmt.setInt(1, categoriaEnderecoId);
        rs = stmt.executeQuery();

        if (rs.next()) {
            categoria = rs.getInt("id");
        }

        return categoria;

        } catch(Exception e6){
            throw new RuntimeException(e6);
        }
    }
}
