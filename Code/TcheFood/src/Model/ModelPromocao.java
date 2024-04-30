package Model;

public class ModelPromocao {
    private int id;
    private ModelProduto produtoId;
    private String descricao;
    private double percentual;
    private boolean ativo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelProduto getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(ModelProduto produtoId) {
        this.produtoId = produtoId;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
