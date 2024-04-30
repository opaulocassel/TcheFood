package tablemodel;


import Model.ModelItensPedido;
import Model.ProdutoModel;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import view.pedidos.JDialogPedidoCardapio;


public class TableModelItensPedidoLista extends AbstractTableModel 
{
    private ArrayList<ModelItensPedido> itensPedido;
    private String[] columns = new String[] {"ID", "Pedido ID", "Produto ID", "Quantidade", "Opcional"};
    
    
    public TableModelItensPedidoLista(ArrayList<ModelItensPedido> itensPedido)
    {
        this.fireTableRowsUpdated(0, 0);
        this.itensPedido = itensPedido;
    }

    @Override
    public int getRowCount() 
    {
        return itensPedido.size();
    }

    @Override
    public int getColumnCount() 
    {
        return 5;
    }
    
    @Override
    public String getColumnName(int column) 
    {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        ModelItensPedido itemPedido = itensPedido.get(rowIndex);
        switch (columnIndex) 
        {
            case 0: return itemPedido.getId();
            case 1: return itemPedido.getPedidoId().getId();
            case 2: return itemPedido.getProdutoId().getId() + " - " + itemPedido.getProdutoId().getNome();
            case 3: return itemPedido.getQuantidade();
            case 4: return itemPedido.getOpcional();
        }
        return null;
    }
}
