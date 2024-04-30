package Model;


public class ProdutoModel 
{
    private int id;
    private ModelCategoriaProduto categoriaProduto;
    private String nome;
    private String descricao;
    private double preco;
    private String imagem;

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public ModelCategoriaProduto getCategoriaProduto() 
    {
        return categoriaProduto;
    }

    public void setCategoriaProduto(ModelCategoriaProduto categoriaProduto) 
    {
        this.categoriaProduto = categoriaProduto;
    }

    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public String getDescricao() 
    {
        return descricao;
    }

    public void setDescricao(String descricao) 
    {
        this.descricao = descricao;
    }

    public double getPreco() 
    {
        return preco;
    }

    public void setPreco(double preco) 
    {
        this.preco = preco;
    }

    public String getImagem() 
    {
        return imagem;
    }

    public void setImagem(String imagem) 
    {
        this.imagem = imagem;
    }
}
