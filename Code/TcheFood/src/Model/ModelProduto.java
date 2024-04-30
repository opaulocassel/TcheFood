package Model;

public class ModelProduto {
    private int id;
    private ModelCategoriaProduto categoriaProdutoId;
    private String descricao;
    private double preco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelCategoriaProduto getCategoriaProdutoId() {
        return categoriaProdutoId;
    }

    public void setCategoriaProdutoId(ModelCategoriaProduto categoriaProdutoId) {
        this.categoriaProdutoId = categoriaProdutoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
