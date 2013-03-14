/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

/**
 *
 * @author Karl
 */
public class MainFrame extends javax.swing.JFrame {
    
    private void action0(){
        if(this.button0.isSelected()){
            this.buttonLabel.setText("Bouton 0 sélectionné.");
        }
        else{
            this.buttonLabel.setText("Bouton 0 désélectionné.");
        }
    }
    private void action1(){
        if(this.button1.isSelected()){
            this.buttonLabel.setText("Bouton 1 sélectionné.");
        }
        else{
            this.buttonLabel.setText("Bouton 1 désélectionné.");
        }
    }
    private void action2(){
        if(this.button2.isSelected()){
            this.buttonLabel.setText("Bouton 2 sélectionné.");
        }
        else{
            this.buttonLabel.setText("Bouton 2 désélectionné.");
        }
    }
    
    /**
    /**
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        mainPanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        utilsButton0 = new javax.swing.JToggleButton();
        utilsButton1 = new javax.swing.JToggleButton();
        utilsButton2 = new javax.swing.JToggleButton();
        splitPane = new javax.swing.JSplitPane();
        leftButtonsPanel = new javax.swing.JPanel();
        button0 = new javax.swing.JToggleButton();
        button1 = new javax.swing.JToggleButton();
        button2 = new javax.swing.JToggleButton();
        rightPanel = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jDraw = new aihm.JDrawPanel();
        infoPanel = new javax.swing.JPanel();
        buttonLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mainMenu = new javax.swing.JMenu();
        menuFileQuit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        utilsButton0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Zero.png"))); // NOI18N
        utilsButton0.setFocusable(false);
        utilsButton0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        utilsButton0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/ZeroSelected.png"))); // NOI18N
        utilsButton0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, button0, org.jdesktop.beansbinding.ELProperty.create("${selected}"), utilsButton0, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        utilsButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utilsButton0ActionPerformed(evt);
            }
        });
        jToolBar1.add(utilsButton0);

        utilsButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/One.png"))); // NOI18N
        utilsButton1.setFocusable(false);
        utilsButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        utilsButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/OneSelected.png"))); // NOI18N
        utilsButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, button1, org.jdesktop.beansbinding.ELProperty.create("${selected}"), utilsButton1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        utilsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utilsButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(utilsButton1);

        utilsButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Two.png"))); // NOI18N
        utilsButton2.setFocusable(false);
        utilsButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        utilsButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/TwoSelected.png"))); // NOI18N
        utilsButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, button2, org.jdesktop.beansbinding.ELProperty.create("${selected}"), utilsButton2, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        utilsButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utilsButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(utilsButton2);

        mainPanel.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        splitPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        leftButtonsPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        leftButtonsPanel.setLayout(new java.awt.GridBagLayout());

        button0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Zero.png"))); // NOI18N
        button0.setToolTipText("");
        button0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/ZeroSelected.png"))); // NOI18N
        button0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button0ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        leftButtonsPanel.add(button0, gridBagConstraints);

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/One.png"))); // NOI18N
        button1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/OneSelected.png"))); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        leftButtonsPanel.add(button1, gridBagConstraints);

        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Two.png"))); // NOI18N
        button2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/TwoSelected.png"))); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        leftButtonsPanel.add(button2, gridBagConstraints);

        splitPane.setLeftComponent(leftButtonsPanel);

        rightPanel.setBackground(new java.awt.Color(255, 255, 255));

        jSlider1.setBackground(new java.awt.Color(255, 255, 255));
        jSlider1.setOrientation(javax.swing.JSlider.VERTICAL);
        jSlider1.setValue(0);
        jSlider1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jSlider1MouseWheelMoved(evt);
            }
        });
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jDraw.setBackground(new java.awt.Color(255, 255, 255));
        jDraw.setLayout(null);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDraw, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap())
        );

        splitPane.setRightComponent(rightPanel);

        mainPanel.add(splitPane, java.awt.BorderLayout.CENTER);

        infoPanel.setBackground(new java.awt.Color(204, 204, 204));

        buttonLabel.setText(" ");
        buttonLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonLabel.setDoubleBuffered(true);
        buttonLabel.setName(""); // NOI18N

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(infoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(buttonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
            .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buttonLabel)))
        );

        mainPanel.add(infoPanel, java.awt.BorderLayout.PAGE_END);

        mainMenu.setText("Fichier");

        menuFileQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menuFileQuit.setText("Quitter");
        menuFileQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileQuitActionPerformed(evt);
            }
        });
        mainMenu.add(menuFileQuit);

        jMenuBar1.add(mainMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileQuitActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_menuFileQuitActionPerformed

    private void button0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button0ActionPerformed
        this.action0();
    }//GEN-LAST:event_button0ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        this.action1();
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        this.action2();
    }//GEN-LAST:event_button2ActionPerformed

    private void utilsButton0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utilsButton0ActionPerformed
        this.action0();
    }//GEN-LAST:event_utilsButton0ActionPerformed

    private void utilsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utilsButton1ActionPerformed
        this.action1();
    }//GEN-LAST:event_utilsButton1ActionPerformed

    private void utilsButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utilsButton2ActionPerformed
        this.action2();
    }//GEN-LAST:event_utilsButton2ActionPerformed

    private void jSlider1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jSlider1MouseWheelMoved
        this.jSlider1.setValue(this.jSlider1.getValue() - evt.getUnitsToScroll());
    }//GEN-LAST:event_jSlider1MouseWheelMoved

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        this.jDraw.setPosX(this.jSlider1.getValue());
        this.jDraw.repaint();
    }//GEN-LAST:event_jSlider1StateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton button0;
    private javax.swing.JToggleButton button1;
    private javax.swing.JToggleButton button2;
    private javax.swing.JLabel buttonLabel;
    private javax.swing.JPanel infoPanel;
    private aihm.JDrawPanel jDraw;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel leftButtonsPanel;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuItem menuFileQuit;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JToggleButton utilsButton0;
    private javax.swing.JToggleButton utilsButton1;
    private javax.swing.JToggleButton utilsButton2;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
