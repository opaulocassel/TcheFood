package DAO;
import Infra.ConexaoMYSQL;
import Model.ModelUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {
    public static void salvar(ModelUsuario user) {
        try {
        ConexaoMYSQL conexaoMysql = new ConexaoMYSQL();
        Connection con = conexaoMysql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement
                ("INSERT INTO tb_usuario(email, senha, nome, sobrenome, telefone) VALUES (?, ?, ?, ?, ?)");

        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getSenha());
        stmt.setString(3, user.getNome());
        stmt.setString(4, user.getSobrenome());
        stmt.setString(5, user.getTelefone());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e1){
            throw new RuntimeException(e1);
        }
    }

    public static void atualizar(ModelUsuario user) {
        try {
        ConexaoMYSQL conexaoMysql = new ConexaoMYSQL();
        Connection con = conexaoMysql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("UPDATE tb_usuario SET email = ?, senha = ?, nome = ?, sobrenome = ?, telefone = ? WHERE id = ?");

        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getSenha());
        stmt.setString(3, user.getNome());
        stmt.setString(4, user.getSobrenome());
        stmt.setString(5, user.getTelefone());
        stmt.setInt(6, user.getId());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e2){
            throw new RuntimeException(e2);
        }
    }

    public static void deletar(ModelUsuario user) {
        try {
        ConexaoMYSQL conexaoMysql = new ConexaoMYSQL();
        Connection con = conexaoMysql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("DELETE FROM tb_usuario WHERE email = ?");

        stmt.setString(1, user.getEmail());

        stmt.executeUpdate();

        stmt.close();
        con.close();

        } catch(Exception e3){
            throw new RuntimeException(e3);
        }
    }


    public static ArrayList<ModelUsuario> cosnsultar() {
        try {
        ConexaoMYSQL conexaoMysql = new ConexaoMYSQL();
        Connection con = conexaoMysql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id, email, senha, nome, sobrenome, telefone FROM tb_usuario");

        ResultSet rs = stmt.executeQuery();

        ArrayList<ModelUsuario> usuarioList = new ArrayList<>();

        while(rs.next()){
            ModelUsuario modelUsuario = new ModelUsuario();

            modelUsuario.setId(rs.getInt("id"));
            modelUsuario.setEmail(rs.getString("email"));
            modelUsuario.setSenha(rs.getString("senha"));
            modelUsuario.setNome(rs.getString("nome"));
            modelUsuario.setSobrenome(rs.getString("sobrenome"));
            modelUsuario.setTelefone(rs.getString("telefone"));

            usuarioList.add(modelUsuario);
        }

        return usuarioList;

        } catch(Exception e4){
            throw new RuntimeException(e4);
        }
    }

    public static boolean isIdValido(ModelUsuario user) {
        try {
        ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
        Connection con = conexaoMsql.obterConexao();
        PreparedStatement stmt = null;

        stmt = con.prepareStatement("SELECT id FROM tb_usuario WHERE id = ?");

        stmt.setInt(1, user.getId());
        ResultSet rs = stmt.executeQuery();

        boolean idExist = rs.next();

        return idExist;

        } catch(Exception e5){
            throw new RuntimeException(e5);
        }
    }

    public static boolean isEmailValido(ModelUsuario user) {
        try {
            ConexaoMYSQL conexaoMsql = new ConexaoMYSQL();
            Connection con = conexaoMsql.obterConexao();
            PreparedStatement stmt = null;

            stmt = con.prepareStatement("SELECT email FROM tb_usuario WHERE email = ?");

            stmt.setString(1, user.getEmail());
            ResultSet rs = stmt.executeQuery();

            boolean emailExists = rs.next();

            return emailExists;

        } catch(Exception e6){
            throw new RuntimeException(e6);
        }
    }


    public static int getUsuarioPorId(int usuarioId) {
        try {
        ConexaoMYSQL conexaoMYSQL = new ConexaoMYSQL();
        Connection con = conexaoMYSQL.obterConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int categoria = -1;

        stmt = con.prepareStatement("SELECT id FROM tb_usuario WHERE id = ?");
        stmt.setInt(1, usuarioId);
        rs = stmt.executeQuery();

        if (rs.next()) {
            categoria = rs.getInt("id");
        }

        return categoria;

        } catch(Exception e7){
            throw new RuntimeException(e7);
        }
    }

}
