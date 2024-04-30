package tablemodel;

import Model.ProdutoModel;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;


public class TableModelProduto extends AbstractTableModel 
{
    private ArrayList<ProdutoModel> produtos;
    private String[] columns = new String[] {"ID", "Categoria", "Foto", "Nome", "Descrição", "Preço"};

    
    public TableModelProduto(ArrayList<ProdutoModel> produtos)
    {
        this.fireTableRowsUpdated(0, 0);
        this.produtos = produtos;
    }

    @Override
    public int getRowCount() 
    {
        return produtos.size();
    }

    @Override
    public int getColumnCount() 
    {
        return 6;
    }
    
    @Override
    public String getColumnName(int column) 
    {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        ProdutoModel produto = produtos.get(rowIndex);
        switch (columnIndex) 
        {
            case 0: return produto.getId();
            case 1: return produto.getCategoriaProduto().getId() + " - " + produto.getCategoriaProduto().getDescricao();
            case 2: return produto.getImagem();
            case 3: return produto.getNome();
            case 4: return produto.getDescricao();
            case 5: return "R$ " + String.format("%.2f", produto.getPreco());
        }
        return null;
    }
}
