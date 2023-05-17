package dao;

import apoio.ConexaoBD;
import apoio.IDAO_T;
import entidade.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class UsuarioDAO implements IDAO_T<Usuario> {

    ResultSet resultadoQ = null;

    @Override
    public String salvar(Usuario o) {

        try {
            String sql = ""
                    + "INSERT INTO usuarios (nome, senha, situacao) VALUES ("
                    + "'" + o.getNome() + "',"
                    + "md5('" + o.getSenha() + "'),"
                    + "'" + o.getSituacao() + "' "
                    + ")";

            System.out.println("sql: " + sql);

            ConexaoBD.executeUpdate(sql);

            return null;
        } catch (SQLException e) {
            System.out.println("Erro inserir usuario = " + e);
            return e.toString();
        }

    }

    @Override
    public String atualizar(Usuario o) {
        try {
            String sql = ""
                    + "UPDATE usuarios "
                    + "SET "
                    + "nome = '" + o.getNome() + "',"
                    + "senha = md5('" + o.getSenha() + "'),"
                    + "situacao = '" + o.getSituacao() + "' "
                    + "WHERE id = " + o.getId();

            System.out.println("sql: " + sql);

            ConexaoBD.executeUpdate(sql);

            return null;
        } catch (SQLException e) {
            System.out.println("Erro atualizar usuario = " + e);
            return e.toString();
        }
    }

    @Override
    public String excluir(int id) {
        try {
            String sql = ""
                    + "UPDATE usuarios "
                    + "SET "
                    + "situacao = 'I' "
                    + "WHERE id = " + id;

            System.out.println("sql: " + sql);

            ConexaoBD.executeUpdate(sql);

            return null;
        } catch (SQLException e) {
            System.out.println("Erro excluir/inativar usuario = " + e);
            return e.toString();
        }
    }

    @Override
    public ArrayList<Usuario> consultarTodos() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            String sql = ""
                    + "SELECT * FROM usuarios ";

            resultadoQ = ConexaoBD.executeQuery(sql);

            while (resultadoQ.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(resultadoQ.getInt("id"));
                usuario.setNome(resultadoQ.getString("nome"));
                usuario.setSenha(resultadoQ.getString("senha"));
                usuario.setSituacao(resultadoQ.getString("situacao").charAt(0));

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Erro consultar todos usuarios = " + e);
        }

        return usuarios;
    }

    @Override
    public ArrayList<Usuario> consultar(String criterio) {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            String sql = ""
                    + "SELECT * FROM usuarios "
                    + "WHERE  "
                    + "nome ILIKE '%" + criterio + "%' "
                    + "ORDER BY nome";

            resultadoQ = ConexaoBD.executeQuery(sql);

            while (resultadoQ.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(resultadoQ.getInt("id"));
                usuario.setNome(resultadoQ.getString("nome"));
                usuario.setSenha(resultadoQ.getString("senha"));
                usuario.setSituacao(resultadoQ.getString("situacao").charAt(0));

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Erro consultar todos usuarios = " + e);
        }

        return usuarios;
    }

    @Override
    public Usuario consultarId(int id) {
        Usuario u = null;

        try {
            String sql = ""
                    + "SELECT * FROM usuarios "
                    + "WHERE  "
                    + "id = " + id;

            System.out.println("sql: " + sql);

            resultadoQ = ConexaoBD.executeQuery(sql);

            if (resultadoQ.next()) {
                u = new Usuario();

                u.setId(id);
                u.setNome(resultadoQ.getString("nome"));
                u.setSenha(resultadoQ.getString("senha"));
                u.setSituacao(resultadoQ.getString("situacao").charAt(0));
            }

        } catch (SQLException e) {
            System.out.println("Erro consultar usuario = " + e);
        }
        return u;
    }

    public Usuario login(String nome, String senha) {
        Usuario i = null;

        try {
            String sql = ""
                    + "SELECT * FROM usuarios "
                    + "WHERE  "
                    + "nome = '" + nome + "' AND senha = md5('" + senha + "') "
                    + "AND situacao = 'A'";

            System.out.println(sql);

            resultadoQ = ConexaoBD.executeQuery(sql);

            if (resultadoQ.next()) {
                i = new Usuario();

                i.setNome(resultadoQ.getString("nome"));
                i.setSenha(resultadoQ.getString("senha"));
                i.setId(resultadoQ.getInt("id"));
                i.setSituacao(resultadoQ.getString("situacao").charAt(0));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar usuario: " + e);
        }

        return i;
    }

    /**
     * Consulta no banco de dados e popula uma tabela
     *
     * @param tabela: nome da tabela a ser populada
     * @param criterio: criterio de busca no banco
     */
    public void popularTabela(JTable tabela, String criterio) {
        // dados da tabela
        Object[][] dadosTabela = null;
        int colunas = 3;

        // cabecalho da tabela
        Object[] cabecalho = new Object[colunas];
        cabecalho[0] = "Código";
        cabecalho[1] = "Nome";
        cabecalho[2] = "Situação";

        // cria matriz de acordo com nº de registros da tabela
        try {
            String sql = ""
                    + "SELECT count(*) FROM usuarios "
                    + "WHERE  "
                    + "nome ILIKE '%" + criterio + "%'";

            resultadoQ = ConexaoBD.executeQuery(sql);

            resultadoQ.next();

            dadosTabela = new Object[resultadoQ.getInt(1)][colunas];

        } catch (Exception e) {
            System.out.println("Erro ao consultar usuario: " + e);
        }

        int lin = 0;

        // efetua consulta na tabela
        try {
            String sql = ""
                    + "SELECT id, nome, situacao FROM usuarios "
                    + "WHERE  "
                    + "nome ILIKE '%" + criterio + "%' "
                    + "ORDER BY nome";

            resultadoQ = ConexaoBD.executeQuery(sql);

            while (resultadoQ.next()) {

                dadosTabela[lin][0] = resultadoQ.getInt("id");
                dadosTabela[lin][1] = resultadoQ.getString("nome");
                dadosTabela[lin][2] = resultadoQ.getString("situacao");

                // caso a coluna precise exibir uma imagem
//                if (resultadoQ.getBoolean("Situacao")) {
//                    dadosTabela[lin][2] = new ImageIcon(getClass().getClassLoader().getResource("Interface/imagens/status_ativo.png"));
//                } else {
//                    dadosTabela[lin][2] = new ImageIcon(getClass().getClassLoader().getResource("Interface/imagens/status_inativo.png"));
//                }
                lin++;
            }
        } catch (SQLException e) {
            System.out.println("problemas para popular tabela...");
            System.err.println(e);
        }

        // configuracoes adicionais no componente tabela
        tabela.setModel(new DefaultTableModel(dadosTabela, cabecalho) {
            @Override
            // quando retorno for FALSE, a tabela nao é editavel
            public boolean isCellEditable(int row, int column) {
                return false;
                /*  
                 if (column == 3) {  // apenas a coluna 3 sera editavel
                 return true;
                 } else {
                 return false;
                 }
                 */
            }

            // alteracao no metodo que determina a coluna em que o objeto ImageIcon devera aparecer
            @Override
            public Class getColumnClass(int column) {

                if (column == 2) {
//                    return ImageIcon.class;
                }
                return Object.class;
            }
        });

        // permite seleção de apenas uma linha da tabela
        tabela.setSelectionMode(0);

        // redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            column = tabela.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(17);
                    break;
                case 1:
                    column.setPreferredWidth(140);
                    break;
//                case 2:
//                    column.setPreferredWidth(14);
//                    break;
            }
        }
        // renderizacao das linhas da tabela = mudar a cor
//        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                    boolean isSelected, boolean hasFocus, int row, int column) {
//                super.getTableCellRendererComponent(table, value, isSelected,
//                        hasFocus, row, column);
//                if (row % 2 == 0) {
//                    setBackground(Color.GREEN);
//                } else {
//                    setBackground(Color.LIGHT_GRAY);
//                }
//                return this;
//            }
//        });
    }

}
