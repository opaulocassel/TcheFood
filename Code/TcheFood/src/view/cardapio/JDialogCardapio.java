package view.cardapio;

import DAO.ProdutoDAO;
import Model.ProdutoModel;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import view.pedidos.JDialogPedidoCardapio;


public class JDialogCardapio extends javax.swing.JDialog 
{
    private ArrayList<ProdutoModel> produtos;
    
    
    public JDialogCardapio(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        carregarCardapio();
        criarBotao();
    }

    private void carregarCardapio()
    {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtos = produtoDAO.obterTodoCardapio();
    }
    
    private void criarBotao()
    {
        Dimension dimension = new Dimension(800, 260 * produtos.size());
        
        jPanelBase.setSize(dimension);
        jPanelBase.setPreferredSize(dimension);
        
        int y = 10;
        
        for(int i = 0; i < produtos.size(); i++)
        {
            JPanel jPanel1 = new JPanel();
            jPanel1.setBackground(new java.awt.Color(230, 230, 230));
            jPanel1.setBounds(10, y, 780, 250);
            jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            JLabel jLabelFoto = new JLabel();
            
            String nomeImagem = produtos.get(i).getImagem();
            
            try 
            {
                Image img;

                String caminhoImagem = System.getProperty("user.dir") + "\\src\\images/"; //pega até a pasta source

                String caminhoCompleto = caminhoImagem + nomeImagem;

                File checarImagemPresenteNoDiretorio = new File(caminhoCompleto);


                if (!checarImagemPresenteNoDiretorio.exists()) 
                {
                    img = ImageIO.read(getClass().getResource("null.png"));
                }
                else
                {
                    String imagem = "/images/" + nomeImagem;
                    img = ImageIO.read(getClass().getResource(imagem));
                }

                ImageIcon icon = new javax.swing.ImageIcon(img.getScaledInstance(250, 200, Image.SCALE_SMOOTH));
                jLabelFoto.setIcon(icon);
            }
            catch (IOException ex) 
            {
                Logger.getLogger(JDialogCardapio.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //jLabelFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tchefood/src/images/null.png"))); // NOI18N
            jLabelFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
            jPanel1.add(jLabelFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 25, 250, 200));

            JLabel jLabelNome = new JLabel();
            jLabelNome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            jLabelNome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabelNome.setText(produtos.get(i).getNome());
            jPanel1.add(jLabelNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 400, -1));

            JScrollPane jScrollPane1 = new JScrollPane();
            jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
            jScrollPane1.setBorder(null);
            jScrollPane1.setEnabled(false);
            jScrollPane1.setFocusable(false);
            jScrollPane1.setVerifyInputWhenFocusTarget(false);

            JTextArea jTextArea = new JTextArea();
            jTextArea.setBackground(new java.awt.Color(230, 230, 230));
            jTextArea.setColumns(20);
            jTextArea.setRows(5);
            jTextArea.setLineWrap(true);
            jTextArea.setWrapStyleWord(true);
            jTextArea.setFocusable(false);
            jScrollPane1.setViewportView(jTextArea);
            jTextArea.setText(produtos.get(i).getDescricao());

            jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 460, 140));

            JLabel jLabelPreço = new JLabel();
            jLabelPreço.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
            jLabelPreço.setForeground(new java.awt.Color(0, 153, 51));
            jLabelPreço.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            jLabelPreço.setText("Preço: R$ " + String.format("%.2f", produtos.get(i).getPreco()));
            jLabelPreço.setVisible(true);
            jPanel1.add(jLabelPreço, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 200, -1));

            jPanel1.setVisible(true);
            jPanelBase.add(jPanel1);
            y += 260;
        }
        
        jPanelBase.updateUI(); 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneBase = new javax.swing.JScrollPane();
        jPanelBase = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nosso Cardápio");

        jScrollPaneBase.setMaximumSize(new java.awt.Dimension(825, 600));
        jScrollPaneBase.setPreferredSize(new java.awt.Dimension(810, 600));

        jPanelBase.setPreferredSize(new java.awt.Dimension(398, 1000));

        javax.swing.GroupLayout jPanelBaseLayout = new javax.swing.GroupLayout(jPanelBase);
        jPanelBase.setLayout(jPanelBaseLayout);
        jPanelBaseLayout.setHorizontalGroup(
            jPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 813, Short.MAX_VALUE)
        );
        jPanelBaseLayout.setVerticalGroup(
            jPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );

        jScrollPaneBase.setViewportView(jPanelBase);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneBase, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneBase, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(JDialogCardapio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCardapio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCardapio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCardapio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogCardapio dialog = new JDialogCardapio(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelBase;
    private javax.swing.JScrollPane jScrollPaneBase;
    // End of variables declaration//GEN-END:variables
}
