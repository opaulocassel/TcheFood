package DAO;

import Infra.ConexaoMYSQL;
import Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioEnderecoDAO {

    public static void salvar(ModelUsuarioEndereco usuarioEndereco) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("INSERT INTO tb_usuario_endereco(usuario_id, categoria_endereco_id, padrao, rua1, rua2, cidade, estado, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            stmt.setInt(1, usuarioEndereco.getUsuarioId().getId());
            stmt.setInt(2, usuarioEndereco.getCategoriaEnderecoId().getId());
            stmt.setBoolean(3, usuarioEndereco.isPadrao());
            stmt.setString(4, usuarioEndereco.getRua1());
            stmt.setString(5, usuarioEndereco.getRua2());
            stmt.setString(6, usuarioEndereco.getCidade());
            stmt.setString(7, usuarioEndereco.getEstado());
            stmt.setString(8, usuarioEndereco.getPais());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }
    }

    public static void atualizar(ModelUsuarioEndereco usuarioEndereco) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("UPDATE tb_usuario_endereco SET usuario_id = ?, categoria_endereco_id = ?, padrao = ?, rua1 = ?, rua2 = ?, cidade = ?, estado = ?, pais = ? WHERE id = ?");

            stmt.setInt(1, usuarioEndereco.getUsuarioId().getId());
            stmt.setInt(2, usuarioEndereco.getCategoriaEnderecoId().getId());
            stmt.setBoolean(3, usuarioEndereco.isPadrao());
            stmt.setString(4, usuarioEndereco.getRua1());
            stmt.setString(5, usuarioEndereco.getRua2());
            stmt.setString(6, usuarioEndereco.getCidade());
            stmt.setString(7, usuarioEndereco.getEstado());
            stmt.setString(8, usuarioEndereco.getPais());
            stmt.setInt(9, usuarioEndereco.getId());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (Exception e2){
            throw new RuntimeException(e2);
        }
    }

    public static void deletar(ModelUsuarioEndereco usuarioEndereco) {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("DELETE FROM tb_usuario_endereco WHERE id = ?");

            stmt.setInt(1, usuarioEndereco.getId());

            stmt.executeUpdate();

            stmt.close();
            con.close();

        } catch (Exception e3){
            throw new RuntimeException(e3);
        }
    }


    public static ArrayList<ModelUsuarioEndereco> consultar() {
        try {
            ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
            Connection con = conexaoMYSQL.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("SELECT id, usuario_id, categoria_endereco_id, padrao, rua1, rua2, cidade, estado, pais FROM tb_usuario_endereco");

            ResultSet rs = stmt.executeQuery();

            ArrayList<ModelUsuarioEndereco> modelUsuarioEnderecoArray = new ArrayList<>();

            while (rs.next()) {
                ModelUsuarioEndereco modelUsuarioEndereco = new ModelUsuarioEndereco();

                modelUsuarioEndereco.setId(rs.getInt("id"));
                modelUsuarioEndereco.setPadrao(rs.getBoolean("padrao"));
                modelUsuarioEndereco.setRua1(rs.getString("rua1"));
                modelUsuarioEndereco.setRua2(rs.getString("rua2"));
                modelUsuarioEndereco.setCidade(rs.getString("cidade"));
                modelUsuarioEndereco.setEstado(rs.getString("estado"));
                modelUsuarioEndereco.setPais(rs.getString("pais"));

                ModelUsuario usuarioId = new ModelUsuario();
                usuarioId.setId(rs.getInt("usuario_id"));

                modelUsuarioEndereco.setUsuarioId(usuarioId);
                modelUsuarioEnderecoArray.add(modelUsuarioEndereco);

                ModelCategoriaEndereco enderecoId = new ModelCategoriaEndereco();
                enderecoId.setId(rs.getInt("categoria_endereco_id"));

                modelUsuarioEndereco.setCategoriaEnderecoId(enderecoId);
                modelUsuarioEnderecoArray.add(modelUsuarioEndereco);

            }
            return modelUsuarioEnderecoArray;

        } catch (Exception e4){
            throw new RuntimeException(e4);
        }
    }


    public static boolean isIdValido(ModelUsuarioEndereco usuarioEndereco) {
        try {
            ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
            Connection con = conexaoMsql.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("SELECT id FROM tb_usuario_endereco WHERE id = ?");

            stmt.setInt(1, usuarioEndereco.getId());
            ResultSet rs = stmt.executeQuery();

            boolean idExist = rs.next();

            return idExist;

        } catch (Exception e5){
            throw new RuntimeException(e5);
        }
    }

}
