package dao;

import apoio.ConexaoBD;
import apoio.IDAO_T;
import entidade.VendaProduto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class VendaProdutoDAO implements IDAO_T<VendaProduto> {

    ResultSet resultadoQ = null;

    @Override
    public String salvar(VendaProduto o) {

        try {
            String sql = ""
                    + "INSERT INTO venda_produto (vendas_id, produtos_id, quantidade, preco) VALUES ("
                    + " " + o.getVendas_id() + " ,"
                    + " " + o.getProdutos_id() + " ,"
                    + " " + o.getQuantidade() + " ,"
                    + " " + o.getPreco() + " )";

            System.out.println("sql: " + sql);

            ConexaoBD.executeUpdate(sql);

            return null;
        } catch (SQLException e) {
            System.out.println("Erro inserir Venda Produto = " + e);
            return e.toString();
        }

    }

    @Override
    public String atualizar(VendaProduto o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String excluir(int vendaId, int produtoId) {
        try {
            String sql = "DELETE "
                    + "FROM venda_produto "
                    + "where "
                    + "vendas_id = " + vendaId + " AND "
                    + "produtos_id = " + produtoId;

            System.out.println("SQL: " + sql);

            ConexaoBD.executeUpdate(sql);

            return null;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir Venda Produto: " + e);
            return e.toString();
        }
    }

    @Override
    public ArrayList<VendaProduto> consultarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<VendaProduto> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public VendaProduto consultarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Consulta no banco de dados e popula uma tabela
     *
     * @param tabela: nome da tabela a ser populada
     * @param criterio
     * @param idVenda
     */
    public void popularTabela(JTable tabela, String criterio, int idVenda) {
        // dados da tabela
        Object[][] dadosTabela = null;
        int colunas = 5;

        // cabecalho da tabela
        Object[] cabecalho = new Object[colunas];
        cabecalho[0] = "Venda ID";
        cabecalho[1] = "Produto ID";
        cabecalho[2] = "Descrição";
        cabecalho[3] = "Quantidade";
        cabecalho[4] = "Valor";

        // cria matriz de acordo com nº de registros da tabela
        try {
            String sql = ""
                    + "SELECT count(*) "
                    + "FROM produtos, venda_produto "
                    + "WHERE  "
                    + "venda_produto.produtos_id = produtos.id AND "
                    + "venda_produto.vendas_id = " + idVenda;

            resultadoQ = ConexaoBD.executeQuery(sql);

            resultadoQ.next();

            dadosTabela = new Object[resultadoQ.getInt(1)][colunas];

        } catch (SQLException e) {
            System.out.println("Erro ao consultar Venda Produto: " + e);
        }

        int lin = 0;

        // efetua consulta na tabela
        try {
            String sql = ""
                    + "SELECT venda_produto.vendas_id, venda_produto.produtos_id, venda_produto.quantidade, venda_produto.preco, produtos.descricao "
                    + "FROM venda_produto, produtos "
                    + "WHERE "
                    + "venda_produto.produtos_id = produtos.id AND "
                    + "venda_produto.vendas_id = " + idVenda;

            resultadoQ = ConexaoBD.executeQuery(sql);

            while (resultadoQ.next()) {

                dadosTabela[lin][0] = resultadoQ.getInt("vendas_id");
                dadosTabela[lin][1] = resultadoQ.getInt("produtos_id");
                dadosTabela[lin][2] = resultadoQ.getString("descricao");
                dadosTabela[lin][3] = resultadoQ.getDouble("quantidade");
                dadosTabela[lin][4] = resultadoQ.getDouble("preco");

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
//                if(row % 2 == 0) {
//                    setBackground(Color.WHITE);
//                } else {
//                    setBackground(Color.LIGHT_GRAY);
//                }
//                return this;
//            }
//        });
    }

}
