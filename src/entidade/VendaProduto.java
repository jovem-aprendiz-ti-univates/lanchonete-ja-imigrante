/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidade;

/**
 *
 * @author mateus
 */
public class VendaProduto {
    private int vendas_id;
    private int produtos_id;
    private double quantidade;
    private double preco;

    public int getVendas_id() {
        return vendas_id;
    }

    public void setVendas_id(int vendas_id) {
        this.vendas_id = vendas_id;
    }

    public int getProdutos_id() {
        return produtos_id;
    }

    public void setProdutos_id(int produtos_id) {
        this.produtos_id = produtos_id;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
