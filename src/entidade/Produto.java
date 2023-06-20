/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidade;

/**
 *
 * @author mateus.roveda
 */
public class Produto {
    private int id;
    private String descricao;
    private String codigobarras;
    private double qtdestoque;
    private double preco;
    private String datacadastro;
    private double estoqueminimo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigobarras() {
        return codigobarras;
    }

    public void setCodigobarras(String codigobarras) {
        this.codigobarras = codigobarras;
    }

    public double getQtdestoque() {
        return qtdestoque;
    }

    public void setQtdestoque(double qtdestoque) {
        this.qtdestoque = qtdestoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(String datacadastro) {
        this.datacadastro = datacadastro;
    }

    public double getEstoqueminimo() {
        return estoqueminimo;
    }

    public void setEstoqueminimo(double estoqueminimo) {
        this.estoqueminimo = estoqueminimo;
    }
}
