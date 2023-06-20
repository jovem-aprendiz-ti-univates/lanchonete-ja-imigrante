/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidade;

/**
 *
 * @author mateus.roveda
 */
public class Venda {
    private int id;
    private String data;
    private int clientes_id;
    private int colaboradores_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getClientes_id() {
        return clientes_id;
    }

    public void setClientes_id(int clientes_id) {
        this.clientes_id = clientes_id;
    }

    public int getColaboradores_id() {
        return colaboradores_id;
    }

    public void setColaboradores_id(int colaboradores_id) {
        this.colaboradores_id = colaboradores_id;
    }
}
