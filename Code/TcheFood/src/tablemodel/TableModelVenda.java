package tablemodel;

import Model.ItemVendaModel;
import Model.ModelItensPedido;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import view.itensPedidos.JDialogRelatorioVendas;


public class TableModelVenda extends AbstractTableModel
{
    private ArrayList<ItemVendaModel> itensVenda;
    private String[] columns = new String[] {"ID do Produto", " Nome do Produto", "Quantidade Vendida", "Total Vendido"};
    
    
    public TableModelVenda(ArrayList<ItemVendaModel> itensVenda)
    {
        this.fireTableRowsUpdated(0, 0);
        this.itensVenda = itensVenda;
        calcularQtdeVendida();
        calcularTotalVendido();
    }

    @Override
    public int getRowCount() 
    {
        return itensVenda.size();
    }

    @Override
    public int getColumnCount() 
    {
        return 4;
    }
    
    @Override
    public String getColumnName(int column) 
    {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        ItemVendaModel itemVenda = itensVenda.get(rowIndex);
        switch (columnIndex) 
        {
            case 0: return itemVenda.getIdProduto();
            case 1: return itemVenda.getNomeProduto();
            case 2: return itemVenda.getQuantidadeVendida();
            case 3: return "R$ " + String.format("%.2f", itemVenda.getValorVendido());
            //case 3: return itemVenda.getValorVendido();
        }
        return null;
    }    
    
    @Override
    public Class<?> getColumnClass(int columnIndex) 
    {
        if (itensVenda.isEmpty()) 
        {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
    
    private void calcularQtdeVendida()
    {
        int totalQtde = 0;
        
        for(int i = 0; i < itensVenda.size(); i++)
        {
            totalQtde += itensVenda.get(i).getQuantidadeVendida();
        }
        
        JDialogRelatorioVendas.jLabelQtdeTotal.setText("Quantidade Total: " + totalQtde);
        //System.out.println("Qtde: " + totalQtde);
    }
    
    private void calcularTotalVendido()
    {
        double totalValor = 0.0;
        
        for(int i = 0; i < itensVenda.size(); i++)
        {
            totalValor += itensVenda.get(i).getValorVendido();
        }
        
        JDialogRelatorioVendas.jLabelValorTotal.setText("Valor Total: R$ " + String.format("%.2f",totalValor));
        //System.out.println("TOTAL: " + totalValor);
    }
}
