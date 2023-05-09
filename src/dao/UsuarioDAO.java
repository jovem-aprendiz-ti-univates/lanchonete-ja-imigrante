package dao;

import apoio.ConexaoBD;
import apoio.IDAO_T;
import entidade.Usuario;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO implements IDAO_T<Usuario> {

    ResultSet resultadoQ = null;

    @Override
    public String salvar(Usuario o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String atualizar(Usuario o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> consultarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario consultarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Usuario login(String nome, String senha) {
        Usuario i = null;

        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from usuarios "
                    + "where  "
                    + "nome = '" + nome + "' AND senha = md5('" + senha + "')";
           
            System.out.println(sql);
            resultadoQ = st.executeQuery(sql);

            if (resultadoQ.next()) {
                i = new Usuario();

                i.setNome(resultadoQ.getString("nome"));
                i.setSenha(resultadoQ.getString("senha"));
                i.setIdusuario(resultadoQ.getInt("id"));
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar usuario: " + e);
        }
        return i;
    }

}
