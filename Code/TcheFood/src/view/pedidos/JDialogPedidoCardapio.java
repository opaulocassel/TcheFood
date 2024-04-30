package view.pedidos;

import DAO.CategoriaProdutoDAO;
import DAO.FormaPagamentoDAO;
import DAO.ItensPedidoDAO;
import view.produto.JDialogProduto;
import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import Model.ModelCategoriaProduto;
import Model.ModelFormaPagamento;
import Model.ModelItensPedido;
import Model.ModelPedido;
import Model.ModelUsuario;
import Model.ProdutoModel;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import tablemodel.TableModelItensPedido;
import view.formapagamentos.jdCadastrarCartao;
import view.formapagamentos.jdCadastrarPix;
import view.itensPedidos.JDCadastrarItensPedidos;
import view.itensPedidos.JDialogEditarItemPedido;
import org.jdesktop.swingx.prompt.PromptSupport;


public class JDialogPedidoCardapio extends javax.swing.JDialog 
{
    private ArrayList<ProdutoModel> produtos = null;
    private ArrayList<JButton> btnProdutos = new ArrayList<>();
    private int pagina = 1;
    private int offset = 0;
    private int quantProdutos;
    private int idUsuario = -1;
    public ModelPedido modelPedido = null;
    private String filtroCategoria = "%";
    private String filtroNome = "%";
    ArrayList<ModelCategoriaProduto> categorias;
    private int formaPagamento = -1;
    
    public JDialogPedidoCardapio(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        carregarCategorias();
        popularBotoes();
        carregarCardapio();
        verificarQuantidadeRegistros();
        checarPaginacao();
        jPanelCardapio.setVisible(false);
        jButtonIncluir.setVisible(false);
    }
   
    
    private void popularBotoes()
    {
        btnProdutos.add(jButton1);
        btnProdutos.add(jButton2);
        btnProdutos.add(jButton3);
        btnProdutos.add(jButton4);
        btnProdutos.add(jButton5);
        btnProdutos.add(jButton6);
        btnProdutos.add(jButton7);
        btnProdutos.add(jButton8);
        btnProdutos.add(jButton9);
        btnProdutos.add(jButton10);
        btnProdutos.add(jButton11);
        btnProdutos.add(jButton12);
        btnProdutos.add(jButton13);
        btnProdutos.add(jButton14);
        btnProdutos.add(jButton15);
        btnProdutos.add(jButton16);
        
        for(JButton btn: btnProdutos)
        {
            btn.setVisible(false);
        }
    }
    
    private void verificarQuantidadeRegistros()
    {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        try 
        {
            quantProdutos = produtoDAO.obterTotalRegistros(filtroCategoria, filtroNome);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void carregarCardapio()
    {
        for(int i = 0; i < btnProdutos.size(); i++)
        {
            btnProdutos.get(i).setVisible(false);
            
            ActionListener[] actionListeners = btnProdutos.get(i).getActionListeners();
            
            for(ActionListener al : actionListeners)
            {
                btnProdutos.get(i).removeActionListener(al);
            }
        }

        
        ProdutoDAO produtoDAO = new ProdutoDAO();

        produtos = produtoDAO.obterCardapio(offset, filtroCategoria, filtroNome);
        
        for(int i = 0; i < produtos.size(); i++)
        {
            int id = produtos.get(i).getId();
            
            //mostrar o id e o nome no cardápio
            //btnProdutos.get(i).setText(produtos.get(i).getId() + " - "  + produtos.get(i).getNome());
            btnProdutos.get(i).setText(produtos.get(i).getNome());
            
            String nomeImagem = produtos.get(i).getImagem();
            
            try 
            {
                Image img;
                
                String caminhoImagem = System.getProperty("user.dir") + "\\src\\images/"; //pega até a pasta source

                String caminhoCompleto = caminhoImagem + nomeImagem;
        
                File checarImagemPresenteNoDiretorio = new File(caminhoCompleto);
                
                //System.out.println("caminho imagem: " + caminhoCompleto);
                
        
                if (!checarImagemPresenteNoDiretorio.exists()) 
                {
                    img = ImageIO.read(getClass().getResource("null.png"));
                }
                else
                {
                    String imagem = "/images/" + nomeImagem;
                    img = ImageIO.read(getClass().getResource(imagem));
                }
                
                ImageIcon icon = new javax.swing.ImageIcon(img.getScaledInstance(100, 80, Image.SCALE_SMOOTH));
                btnProdutos.get(i).setIcon(icon);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             btnProdutos.get(i).addActionListener(new java.awt.event.ActionListener() 
            {
                public void actionPerformed(java.awt.event.ActionEvent evt) 
                {
                    buttonActionPerformed(evt);
                }

                private void buttonActionPerformed(ActionEvent evt) 
                {
                    JDialogProduto detalheProduto = new JDialogProduto(null, true, id, modelPedido, jTableItensPedido);
                    detalheProduto.setVisible(true);
                }
            });
            
            btnProdutos.get(i).setVisible(true);
        }
    }
    
    //se for recuperar pedido pendente
    private TableModelItensPedido initTable() 
    {
        return new TableModelItensPedido(ItensPedidoDAO.carregarItensDoPedido(modelPedido.getId()));
    }

    private void carregarCategorias() 
    {
        categorias = new ArrayList<>();
        
        categorias = CategoriaProdutoDAO.obterTodasCategorias();
        
        for(int i = 0; i < categorias.size(); i++) 
        {
            String idCategoria = String.valueOf(categorias.get(i).getId());
            String descricao = categorias.get(i).getDescricao();
            String item = idCategoria + " - " + descricao;
            this.jComboBoxCategoriaProduto.addItem(item);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCardapio = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButtonNextPagina = new javax.swing.JButton();
        jButtonPrevPagina = new javax.swing.JButton();
        jButtonFiltrarProduto = new javax.swing.JButton();
        jTextFieldFiltrarProduto = new javax.swing.JTextField();
        jComboBoxCategoriaProduto = new javax.swing.JComboBox<>();
        jPanelPedido = new javax.swing.JPanel();
        jButtonConfirmarPedido = new javax.swing.JButton();
        jButtonCancelarPedido = new javax.swing.JButton();
        jLabelFormaPagamento = new javax.swing.JLabel();
        jTextFieldTotalPedido = new javax.swing.JTextField();
        jLabelUsuario = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableItensPedido = new javax.swing.JTable();
        jButtonRemover = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jLabelItensPedido = new javax.swing.JLabel();
        jLabelTotalPedido = new javax.swing.JLabel();
        jButtonIncluir = new javax.swing.JButton();
        jbDinheiro = new javax.swing.JButton();
        jbPix = new javax.swing.JButton();
        jbCartao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pedido - Cardápio");
        setMinimumSize(new java.awt.Dimension(1000, 680));

        jPanelCardapio.setMinimumSize(new java.awt.Dimension(600, 600));
        jPanelCardapio.setPreferredSize(new java.awt.Dimension(600, 600));
        jPanelCardapio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("jButton1");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setIconTextGap(10);
        jButton1.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jButton4.setText("jButton4");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setIconTextGap(10);
        jButton4.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, -1, -1));

        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setIconTextGap(10);
        jButton3.setLabel("jButton3");
        jButton3.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, -1, -1));

        jButton2.setText("jButton2");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setIconTextGap(10);
        jButton2.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, -1, -1));

        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setIconTextGap(10);
        jButton6.setLabel("jButton6");
        jButton6.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, -1, -1));

        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setIconTextGap(10);
        jButton7.setLabel("jButton7");
        jButton7.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, -1, -1));

        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setIconTextGap(10);
        jButton8.setLabel("jButton8");
        jButton8.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, -1, -1));

        jButton5.setText("jButton5");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setIconTextGap(10);
        jButton5.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setIconTextGap(10);
        jButton9.setLabel("jButton9");
        jButton9.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setIconTextGap(10);
        jButton10.setLabel("jButton10");
        jButton10.setMaximumSize(new java.awt.Dimension(75, 23));
        jButton10.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, -1, -1));

        jButton11.setText("jButton1");
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setIconTextGap(10);
        jButton11.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton11.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, -1, -1));

        jButton12.setText("jButton1");
        jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton12.setIconTextGap(10);
        jButton12.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton12.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 320, -1, -1));

        jButton15.setText("jButton1");
        jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton15.setIconTextGap(10);
        jButton15.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton15.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, -1, -1));

        jButton13.setText("jButton1");
        jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton13.setIconTextGap(10);
        jButton13.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        jButton14.setText("jButton1");
        jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton14.setIconTextGap(10);
        jButton14.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton14.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, -1, -1));

        jButton16.setText("jButton1");
        jButton16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton16.setIconTextGap(10);
        jButton16.setPreferredSize(new java.awt.Dimension(130, 120));
        jButton16.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton16.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelCardapio.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 450, -1, -1));

        jButtonNextPagina.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonNextPagina.setText(">");
        jButtonNextPagina.setEnabled(false);
        jButtonNextPagina.setMaximumSize(new java.awt.Dimension(45, 35));
        jButtonNextPagina.setMinimumSize(new java.awt.Dimension(45, 30));
        jButtonNextPagina.setPreferredSize(new java.awt.Dimension(45, 35));
        jButtonNextPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextPaginaActionPerformed(evt);
            }
        });
        jPanelCardapio.add(jButtonNextPagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 585, -1, -1));

        jButtonPrevPagina.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonPrevPagina.setEnabled(false);
        jButtonPrevPagina.setLabel("<");
        jButtonPrevPagina.setMaximumSize(new java.awt.Dimension(45, 35));
        jButtonPrevPagina.setMinimumSize(new java.awt.Dimension(45, 30));
        jButtonPrevPagina.setPreferredSize(new java.awt.Dimension(45, 35));
        jButtonPrevPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrevPaginaActionPerformed(evt);
            }
        });
        jPanelCardapio.add(jButtonPrevPagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 585, -1, -1));

        jButtonFiltrarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tchefood/src/icon/filtro-limpo.png"))); // NOI18N
        jButtonFiltrarProduto.setText("Limpar");
        jButtonFiltrarProduto.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonFiltrarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFiltrarProdutoActionPerformed(evt);
            }
        });
        jPanelCardapio.add(jButtonFiltrarProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 25, -1, -1));

        jTextFieldFiltrarProduto.setPreferredSize(new java.awt.Dimension(64, 25));
        jTextFieldFiltrarProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltrarProdutoKeyReleased(evt);
            }
        });
        jPanelCardapio.add(jTextFieldFiltrarProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 25, 240, -1));
        PromptSupport.setPrompt("Pesquisar por nome", jTextFieldFiltrarProduto);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, jTextFieldFiltrarProduto);

        jComboBoxCategoriaProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos Produtos" }));
        jComboBoxCategoriaProduto.setMinimumSize(new java.awt.Dimension(65, 22));
        jComboBoxCategoriaProduto.setPreferredSize(new java.awt.Dimension(65, 25));
        jComboBoxCategoriaProduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxCategoriaProdutoItemStateChanged(evt);
            }
        });
        jPanelCardapio.add(jComboBoxCategoriaProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 25, 190, -1));

        jPanelPedido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonConfirmarPedido.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonConfirmarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/check.png"))); // NOI18N
        jButtonConfirmarPedido.setText("Confirmar Pedido");
        jButtonConfirmarPedido.setPreferredSize(new java.awt.Dimension(160, 35));
        jButtonConfirmarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarPedidoActionPerformed(evt);
            }
        });
        jPanelPedido.add(jButtonConfirmarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 590, -1, -1));

        jButtonCancelarPedido.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonCancelarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clear.png"))); // NOI18N
        jButtonCancelarPedido.setText("Cancelar Pedido");
        jButtonCancelarPedido.setPreferredSize(new java.awt.Dimension(150, 35));
        jButtonCancelarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarPedidoActionPerformed(evt);
            }
        });
        jPanelPedido.add(jButtonCancelarPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 590, -1, -1));

        jLabelFormaPagamento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelFormaPagamento.setText("Selecione a forma de pagamento:");
        jLabelFormaPagamento.setPreferredSize(new java.awt.Dimension(44, 20));
        jPanelPedido.add(jLabelFormaPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 242, -1));

        jTextFieldTotalPedido.setEditable(false);
        jTextFieldTotalPedido.setPreferredSize(new java.awt.Dimension(64, 25));
        jPanelPedido.add(jTextFieldTotalPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 242, -1));

        jLabelUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelUsuario.setText("Usuário:");
        jLabelUsuario.setPreferredSize(new java.awt.Dimension(44, 20));
        jPanelPedido.add(jLabelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 242, -1));

        jTextFieldUsuario.setPreferredSize(new java.awt.Dimension(64, 25));
        jTextFieldUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldUsuarioFocusLost(evt);
            }
        });
        jPanelPedido.add(jTextFieldUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 242, -1));

        jTableItensPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Produto", "Opcional", "Quantidade", "Preço", "Sub Total"
            }
        ));
        jScrollPane1.setViewportView(jTableItensPedido);

        jPanelPedido.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 560, 300));

        jButtonRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clean.png"))); // NOI18N
        jButtonRemover.setText("Remover");
        jButtonRemover.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoverActionPerformed(evt);
            }
        });
        jPanelPedido.add(jButtonRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 248, -1, -1));

        jButtonAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/paper.png"))); // NOI18N
        jButtonAlterar.setText("Alterar");
        jButtonAlterar.setPreferredSize(new java.awt.Dimension(95, 25));
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });
        jPanelPedido.add(jButtonAlterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 248, -1, -1));

        jLabelItensPedido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelItensPedido.setText("Itens do Pedido:");
        jLabelItensPedido.setPreferredSize(new java.awt.Dimension(44, 20));
        jPanelPedido.add(jLabelItensPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 248, 242, -1));

        jLabelTotalPedido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTotalPedido.setText("Total do Pedido:");
        jLabelTotalPedido.setPreferredSize(new java.awt.Dimension(44, 20));
        jPanelPedido.add(jLabelTotalPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 242, -1));

        jButtonIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        jButtonIncluir.setText("Incluir");
        jButtonIncluir.setEnabled(false);
        jButtonIncluir.setPreferredSize(new java.awt.Dimension(100, 25));
        jButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirActionPerformed(evt);
            }
        });
        jPanelPedido.add(jButtonIncluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 248, -1, -1));

        jbDinheiro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbDinheiro.setText(" Dinheiro");
        jbDinheiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDinheiroActionPerformed(evt);
            }
        });
        jPanelPedido.add(jbDinheiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jbPix.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbPix.setText("PIX");
        jbPix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPixActionPerformed(evt);
            }
        });
        jPanelPedido.add(jbPix, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        jbCartao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbCartao.setText("Cartão");
        jbCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCartaoActionPerformed(evt);
            }
        });
        jPanelPedido.add(jbCartao, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelCardapio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCardapio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNextPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextPaginaActionPerformed
        pagina++;
        offset += 16;
        
        carregarCardapio();
        checarPaginacao();
    }//GEN-LAST:event_jButtonNextPaginaActionPerformed

    private void jButtonPrevPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrevPaginaActionPerformed
        pagina--;
        offset -= 16;
        
        carregarCardapio();
        checarPaginacao();
    }//GEN-LAST:event_jButtonPrevPaginaActionPerformed

    private void jButtonConfirmarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarPedidoActionPerformed
        if(jTableItensPedido.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(rootPane, "ERRO: Não há items no carrinho");
            return;
        }
        
        if(formaPagamento == 1)
        {
            modelPedido.setStatusPagamento(2); //pago
            modelPedido.setStatusPedido(2); //pedido concluido
            
            atualizarPedido();
            JOptionPane.showMessageDialog(rootPane, "Pedido Cadastrado com Sucesso");
            this.dispose();
        }
        else
        {
            modelPedido.setStatusPedido(1); //aguardando pagamento
            atualizarPedido();
            
            if(formaPagamento == 2)
            {
                jdCadastrarCartao a = new jdCadastrarCartao(null, true, this);
                a.setVisible(true);
            }
            else if(formaPagamento == 3)
            {
                jdCadastrarPix a = new jdCadastrarPix(null, true, this);
                a.setVisible(true);
            }
        }
    }//GEN-LAST:event_jButtonConfirmarPedidoActionPerformed

    private void atualizarPedido()
    {
        String strTotalPedido = jTextFieldTotalPedido.getText();
                
        strTotalPedido = strTotalPedido.replace("R$ ", "");
        strTotalPedido = strTotalPedido.replace(",", ".");
        //System.out.println(strTotalPedido);
       
        double totalPedido = Double.parseDouble(strTotalPedido);

        modelPedido.setTotal(totalPedido);
        
        ModelFormaPagamento formaPgto = FormaPagamentoDAO.obterFormaPagamento(this.formaPagamento);
        modelPedido.setFormaPagamentoId(formaPgto);
        
        PedidoDAO.atualizar(modelPedido);
    }
    
    private void jButtonCancelarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarPedidoActionPerformed
        if(modelPedido != null)
        {
            PedidoDAO.deletar(modelPedido);    
        }
        
        JOptionPane.showMessageDialog(rootPane, "Pedido Cancelado");
        this.dispose();
    }//GEN-LAST:event_jButtonCancelarPedidoActionPerformed

    private void jButtonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoverActionPerformed
        int linhaSelecionada = jTableItensPedido.getSelectedRow();
        
        if(linhaSelecionada == -1)
        {
            JOptionPane.showMessageDialog(this, "Nenhum produto Selecionado"); //depois verificar se tem selecionado e ativar o botao
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(this, "Confirma a exclusão do produto?", "Excluir", JOptionPane.YES_NO_OPTION);
        
        if(opcao == JOptionPane.YES_OPTION)
        {
            Object idObjeto = jTableItensPedido.getValueAt(linhaSelecionada, 0);
            int id = (int) idObjeto;
            
            ModelItensPedido itemPedido = ItensPedidoDAO.obterItem(id);
            
            ItensPedidoDAO.deletar(itemPedido);
            JOptionPane.showMessageDialog(this, "Item Excluído");    
            
            TableModelItensPedido tableModel = new TableModelItensPedido(ItensPedidoDAO.carregarItensDoPedido(modelPedido.getId()));
            jTableItensPedido.setModel(tableModel);
        }
    }//GEN-LAST:event_jButtonRemoverActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        int linhaSelecionada = jTableItensPedido.getSelectedRow();
        
        if(linhaSelecionada == -1)
        {
            JOptionPane.showMessageDialog(this, "Nenhum produto Selecionado");
            return;
        }
        
        Object idObjeto = jTableItensPedido.getValueAt(linhaSelecionada, 0);
        int id = (int) idObjeto;
        
        ModelItensPedido itemPedido = ItensPedidoDAO.obterItem(id);

        JDialogEditarItemPedido jDialogEditarItemPedido = new JDialogEditarItemPedido(null, true, itemPedido, modelPedido, jTableItensPedido);
        jDialogEditarItemPedido.setVisible(true);
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        JDCadastrarItensPedidos jDCadastrarItensPedidos = new JDCadastrarItensPedidos(null, true, jTableItensPedido, modelPedido);
        jDCadastrarItensPedidos.setVisible(true);
    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jButtonFiltrarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFiltrarProdutoActionPerformed
        jComboBoxCategoriaProduto.setSelectedIndex(0);
        jTextFieldFiltrarProduto.setText(null);
        filtroCategoria = "%";
        filtroNome = "%";
        
        pagina = 1;
        offset = 0;
        
        carregarCardapio();
        verificarQuantidadeRegistros();
        checarPaginacao();
        
        
        //Troquei, antes era o botão pesquisar
        /*if(jComboBoxCategoriaProduto.getSelectedIndex() > 0)
        {
            int idDaCategoria = categorias.get(jComboBoxCategoriaProduto.getSelectedIndex() -1 ).getId(); //pq começa em 0 o array

            filtroCategoria = String.valueOf(idDaCategoria);
        }
        else
        {
            filtroCategoria = "%";
        }
        if(!jTextFieldFiltrarProduto.getText().isBlank())
        {
            filtroNome = "%" + jTextFieldFiltrarProduto.getText() + "%";
        }
        else
        {
            filtroNome = "%";
        }
        
        pagina = 1;
        offset = 0;
        
        carregarCardapio();
        verificarQuantidadeRegistros();
        checarPaginacao();*/
    }//GEN-LAST:event_jButtonFiltrarProdutoActionPerformed

    private void jbDinheiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDinheiroActionPerformed
        jbDinheiro.setEnabled(false);
        jbPix.setEnabled(true);
        jbCartao.setEnabled(true);

        formaPagamento = 1;
         checarDadosCriarPedido();
    }//GEN-LAST:event_jbDinheiroActionPerformed
    
    private void criarPedido()
    {
        try 
        {
            modelPedido = new ModelPedido();
            
            ModelUsuario modelUsuario = DAO.DAOUsuario.obterUsuarioPorID(idUsuario);
            
            modelPedido.setUsuarioId(modelUsuario);
            
            ModelFormaPagamento modelFormaPagamento = FormaPagamentoDAO.obterFormaPagamento(formaPagamento);
            
            modelPedido.setFormaPagamentoId(modelFormaPagamento);
            
            PedidoDAO.salvar(modelPedido);
            
            modelPedido = PedidoDAO.carregarUltimoPedido();
            jPanelCardapio.setVisible(true);
            jButtonIncluir.setEnabled(true);
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jbPixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPixActionPerformed
        jbDinheiro.setEnabled(true);
        jbPix.setEnabled(false);
        jbCartao.setEnabled(true);
        
        formaPagamento = 3;
        checarDadosCriarPedido();
    }//GEN-LAST:event_jbPixActionPerformed

    private void jbCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCartaoActionPerformed
        jbDinheiro.setEnabled(true);
        jbPix.setEnabled(true);
        jbCartao.setEnabled(false);
        
        formaPagamento = 2;
        checarDadosCriarPedido();
    }//GEN-LAST:event_jbCartaoActionPerformed

    private void jTextFieldUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldUsuarioFocusLost
        try 
        {
            if(!jTextFieldUsuario.getText().isBlank())
            {
                idUsuario = Integer.parseInt(jTextFieldUsuario.getText());
            }
            
            if(DAO.DAOUsuario.isIdValido(idUsuario))
            {    
                checarDadosCriarPedido();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "ID do Usuário Não Encontrado");
                jTextFieldUsuario.requestFocus();
            }
        } 
        catch (SQLException | ClassNotFoundException ex) 
        {
            Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jTextFieldUsuarioFocusLost

    private void jTextFieldFiltrarProdutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltrarProdutoKeyReleased
        // TODO add your handling code here:
        if(jComboBoxCategoriaProduto.getSelectedIndex() > 0)
        {
            int idDaCategoria = categorias.get(jComboBoxCategoriaProduto.getSelectedIndex() -1 ).getId(); //pq começa em 0 o array

            filtroCategoria = String.valueOf(idDaCategoria);
        }
        else
        {
            filtroCategoria = "%";
        }
        if(!jTextFieldFiltrarProduto.getText().isBlank())
        {
            //filtroNome = "%" + jTextFieldFiltrarProduto.getText() + "%";
            filtroNome = jTextFieldFiltrarProduto.getText();
        }
        else
        {
            filtroNome = "%";
        }
        
        pagina = 1;
        offset = 0;
        
        carregarCardapio();
        verificarQuantidadeRegistros();
        checarPaginacao();
        
    }//GEN-LAST:event_jTextFieldFiltrarProdutoKeyReleased

    private void jComboBoxCategoriaProdutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxCategoriaProdutoItemStateChanged
        // TODO add your handling code here:
        if(jComboBoxCategoriaProduto.getSelectedIndex() > 0)
        {
            int idDaCategoria = categorias.get(jComboBoxCategoriaProduto.getSelectedIndex() -1 ).getId(); //pq começa em 0 o array

            filtroCategoria = String.valueOf(idDaCategoria);
        }
        else
        {
            filtroCategoria = "%";
        }
        if(!jTextFieldFiltrarProduto.getText().isBlank())
        {
            //filtroNome = "%" + jTextFieldFiltrarProduto.getText() + "%";
            filtroNome = jTextFieldFiltrarProduto.getText();
        }
        else
        {
            filtroNome = "%";
        }
        
        pagina = 1;
        offset = 0;
        
        carregarCardapio();
        verificarQuantidadeRegistros();
        checarPaginacao();
    }//GEN-LAST:event_jComboBoxCategoriaProdutoItemStateChanged

    private void checarDadosCriarPedido()
    {
        if(modelPedido == null)
        {
            try 
            {
                boolean usuarioValido = DAO.DAOUsuario.isIdValido(idUsuario);
                
                if(usuarioValido && formaPagamento != -1)
                {
                    criarPedido();    
                }
            } 
            catch (SQLException | ClassNotFoundException ex) 
            {
                Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void checarPaginacao()
    {
        if(quantProdutos - offset > 16)
        {
            jButtonNextPagina.setEnabled(true);
        }
        else
        {
            jButtonNextPagina.setEnabled(false);
        }
        if(pagina == 1)
        {
            jButtonPrevPagina.setEnabled(false);
        }
        else
        {
            jButtonPrevPagina.setEnabled(true);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogPedidoCardapio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                JDialogPedidoCardapio dialog = new JDialogPedidoCardapio(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() 
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) 
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonCancelarPedido;
    private javax.swing.JButton jButtonConfirmarPedido;
    private javax.swing.JButton jButtonFiltrarProduto;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonNextPagina;
    private javax.swing.JButton jButtonPrevPagina;
    private javax.swing.JButton jButtonRemover;
    private javax.swing.JComboBox<String> jComboBoxCategoriaProduto;
    private javax.swing.JLabel jLabelFormaPagamento;
    private javax.swing.JLabel jLabelItensPedido;
    private javax.swing.JLabel jLabelTotalPedido;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JPanel jPanelCardapio;
    private javax.swing.JPanel jPanelPedido;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableItensPedido;
    private javax.swing.JTextField jTextFieldFiltrarProduto;
    public static javax.swing.JTextField jTextFieldTotalPedido;
    private javax.swing.JTextField jTextFieldUsuario;
    private javax.swing.JButton jbCartao;
    private javax.swing.JButton jbDinheiro;
    private javax.swing.JButton jbPix;
    // End of variables declaration//GEN-END:variables
}
