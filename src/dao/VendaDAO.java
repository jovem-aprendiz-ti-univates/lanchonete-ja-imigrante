package dao;

import apoio.ConexaoBD;
import apoio.Formatacao;
import apoio.IDAO_T;
import entidade.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class VendaDAO implements IDAO_T<Venda> {

    ResultSet resultadoQ = null;

    @Override
    public String salvar(Venda o) {

        String retornoConsulta = "";

        try {
            String sql = ""
                    + "INSERT INTO vendas (data, clientes_id, colaboradores_id) VALUES ("
                    + "'" + o.getData() + "',"
                    + " " + o.getClientes_id() + " ,"
                    + " " + o.getColaboradores_id() + " ) returning id";

            System.out.println("sql: " + sql);

            resultadoQ = ConexaoBD.executeQuery(sql);

            if (resultadoQ.next()) {
                retornoConsulta = resultadoQ.getString(1);
            }

        } catch (SQLException e) {
            System.out.println("Erro inserir estado = " + e);
            retornoConsulta = "ERROR:" + e.toString();
        }

        return retornoConsulta;

    }

    @Override
    public String atualizar(Venda o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Venda> consultarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Venda> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Venda consultarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Consulta no banco de dados e popula uma tabela
     *
     * @param tabela: nome da tabela a ser populada
     * @param dataInicio: data de inicio das vendas
     * @param dataFim: data fim das vendas
     */
    public void popularTabela(JTable tabela, String dataInicio, String dataFim) {
        // dados da tabela
        Object[][] dadosTabela = null;
        int colunas = 7;

        // cabecalho da tabela
        Object[] cabecalho = new Object[colunas];
        cabecalho[0] = "Código";
        cabecalho[1] = "Data";
        cabecalho[2] = "Cliente Id";
        cabecalho[3] = "Cliente Nome";
        cabecalho[4] = "Colaborador Id";
        cabecalho[5] = "Colaborador Nome";
        cabecalho[6] = "Total";

        // cria matriz de acordo com nº de registros da tabela
        try {
            String sql = ""
                    + "SELECT count(*) FROM vendas, clientes, colaboradores "
                    + "WHERE  "
                    + "vendas.clientes_id = clientes.id AND "
                    + "vendas.colaboradores_id = colaboradores.id AND "
                    + "vendas.data BETWEEN '" + dataInicio + "' AND '" + dataFim + "'";

            resultadoQ = ConexaoBD.executeQuery(sql);

            resultadoQ.next();

            dadosTabela = new Object[resultadoQ.getInt(1)][colunas];

        } catch (SQLException e) {
            System.out.println("Erro ao consultar venda: " + e);
        }

        int lin = 0;

        // efetua consulta na tabela
        try {
            String sql = ""
                    + "SELECT vendas.*, colaboradores.id, colaboradores.nome as colaborador, clientes.id, clientes.nome as cliente, (SELECT SUM (quantidade * preco) AS total from venda_produto WHERE vendas_id = vendas.id) "
                    + "FROM vendas, colaboradores, clientes "
                    + "WHERE  "
                    + "vendas.clientes_id = clientes.id AND "
                    + "vendas.colaboradores_id = colaboradores.id AND "
                    + "vendas.data BETWEEN '" + dataInicio + "' AND '" + dataFim + "'";

            resultadoQ = ConexaoBD.executeQuery(sql);

            while (resultadoQ.next()) {

                dadosTabela[lin][0] = resultadoQ.getInt("id");
                dadosTabela[lin][1] = Formatacao.ajustaDataDMA(resultadoQ.getString("data"));
                dadosTabela[lin][2] = resultadoQ.getInt("clientes_id");
                dadosTabela[lin][3] = resultadoQ.getString("cliente");
                dadosTabela[lin][4] = resultadoQ.getInt("colaboradores_id");
                dadosTabela[lin][5] = resultadoQ.getString("colaborador");
                dadosTabela[lin][6] = Formatacao.formatarDecimal(resultadoQ.getDouble("total"));

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
                    column.setPreferredWidth(70);
                    break;
                case 2:
                    column.setPreferredWidth(30);
                    break;
                case 3:
                    column.setPreferredWidth(200);
                    break;
                case 4:
                    column.setPreferredWidth(60);
                    break;
            }
        }
        // renderizacao das linhas da tabela = mudar a cor
//        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                    boolean isSelected, boolean hasFocus, int row, int column) {
//                super.getTableCellRendererComponent(table, value, isSelected,
//                        hasFocus, row, column);
//                if (row % 2 == 0) {
//                    setBackground(Color.WHITE);
//                } else {
//                    setBackground(Color.LIGHT_GRAY);
//                }
//                return this;
//            }
//        });
    }

}
