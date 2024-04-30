package Model;


public class ItemVendaModel 
{
    private int idProduto;
    private String nomeProduto;
    private int quantidadeVendida;
    private double valorVendido;
    
    
    public ItemVendaModel()
    {

    }

    public int getIdProduto() 
    {
        return idProduto;
    }

    public void setIdProduto(int idProduto) 
    {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() 
    {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) 
    {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidadeVendida() 
    {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) 
    {
        this.quantidadeVendida = quantidadeVendida;
    }

    public double getValorVendido() 
    {
        return valorVendido;
    }

    public void setValorVendido(double valorVendido) 
    {
        this.valorVendido = valorVendido;
    }
    
}
