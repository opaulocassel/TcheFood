/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author adriano
 */
public class ModelItensPedido {
    
     private int id;
    private ModelPedido pedidoId;
    private ProdutoModel produtoId;
    private int quantidade;
    private String opcional;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelPedido getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(ModelPedido pedidoId) {
        this.pedidoId = pedidoId;
    }

    public ProdutoModel getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(ProdutoModel produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getOpcional() 
    {
        return opcional;
    }

    public void setOpcional(String opcional) 
    {
        this.opcional = opcional;
    }


}
