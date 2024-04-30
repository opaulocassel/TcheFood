package tablemodel;


import Model.ModelItensPedido;
import Model.ProdutoModel;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import view.pedidos.JDialogPedidoCardapio;


public class TableModelItensPedido extends AbstractTableModel 
{
    private ArrayList<ModelItensPedido> itensPedido;
    private String[] columns = new String[] {"ID", "Produto", "Opcional", "Qtde", "Preço", "Sub Total"};
    
    //private JTextField jTextFieldTotalPedido;
    
    public TableModelItensPedido(ArrayList<ModelItensPedido> itensPedido)
    {
        this.fireTableRowsUpdated(0, 0);
        this.itensPedido = itensPedido;
        calcularTotal();
    }

    @Override
    public int getRowCount() 
    {
        return itensPedido.size();
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
        ModelItensPedido itemPedido = itensPedido.get(rowIndex);
        switch (columnIndex) 
        {
            case 0: return itemPedido.getId();
            case 1: return itemPedido.getProdutoId().getNome();
            case 2: return itemPedido.getOpcional();
            case 3: return itemPedido.getQuantidade();
            case 4: return "R$ " + String.format("%.2f", itemPedido.getProdutoId().getPreco());
            case 5: return  "R$ " + String.format("%.2f", itemPedido.getQuantidade() * itemPedido.getProdutoId().getPreco());
        }
        return null;
    }
    
    private void calcularTotal()
    {
        double total = 0.0;
        
        for(int i = 0; i < itensPedido.size(); i++)
        {
            //(double) Object subtotal = getValueAt(i, 5);
            //total += getValueAt(i, 5);
            total += itensPedido.get(i).getQuantidade() * itensPedido.get(i).getProdutoId().getPreco();
        }
        
        JDialogPedidoCardapio.jTextFieldTotalPedido.setText("R$ " + String.format("%.2f",total));
        System.out.println("TOTAL: " + total);
    }
}
