/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

import aihm.model.Lift;
import aihm.model.LiftException;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Karl
 */
public class LiftFrame extends javax.swing.JFrame {
    private static final float MUSIC_BASE_VOL = .05f;
    
    private Lift model;
    private AudioPlayer audioMusic;
    private AudioPlayer audioDing;
    
    /**
     * Creates new form LiftFrame
     */
    public LiftFrame(final Lift model) {
        this.model = model;
        
        this.audioMusic = new AudioPlayer("/aihm/res/music.au");
        this.audioMusic.loop(true);
        this.audioMusic.volume(MUSIC_BASE_VOL);
        this.audioMusic.play();
        
        this.audioDing = new AudioPlayer("/aihm/res/ding.au");
        
        initComponents();
        
        final int openWaitDef = 50;
        
        Timer timer = new Timer(20, new ActionListener(){
            private int openWait = openWaitDef;
            @Override
            public void actionPerformed(ActionEvent evt){
                try {
                    switch(model.getState()){
                        case OPENED :
                            if(this.openWait > 0){
                                this.openWait--;
                            }
                            else{
                                model.requestDoorsClosing();
                            }
                            break;
                        case CLOSED :
                            //Current floor announce
                            int floorPX = (100 / (model.getNbFloors() - 1));
                            int liftPosX = lift.getPosX();
                            if(liftPosX % floorPX == 0){ //A floor is reached
                                int floor = liftPosX / floorPX;
                                
                                //Is current floor in requests ?
                                if(model.isFloorInRequest(floor)){
                                    model.setCurrentFloor(floor);
                                    setFloorButtonUnselected(floor);
                                    if(optionsSoundDing.isSelected()){
                                        audioDing.play();
                                    }
                                    model.requestDoorsOpening();
                                }
                                else{
                                    model.setCurrentFloor(floor);
                                }
                            }
                            
                            //Move if doors are closed
                            if(model.getState() == Lift.States.CLOSED){
                                switch(model.getRequestedMove()){
                                    case STANDBY :
                                        break;
                                    case UP :
                                        lift.incrPosX();
                                        break;
                                    case DOWN :
                                        lift.decrPosX();
                                        break;
                                }
                            }
                                
                            //Floor announce in cab
                            cabState.setText(String.valueOf(model.getCurrentFloor()));
                                
                            break;
                        case OPENING :
                            lift.incrDoorsOverture();
                            if(optionsSoundMusic.isSelected()){
                                audioMusic.volume(MUSIC_BASE_VOL + ((float)lift.getDoorsOverture() / (float)LiftPanel.MAX_DOORS_OPENING / 4));
                            }
                            if(lift.getDoorsOverture() == LiftPanel.MAX_DOORS_OPENING){
                                model.setDoorsOpened();
                                this.openWait = openWaitDef;
                            }
                            break;
                        case CLOSING :
                            lift.decrDoorsOverture();
                            if(optionsSoundMusic.isSelected()){
                                audioMusic.volume(MUSIC_BASE_VOL + ((float)lift.getDoorsOverture() / (float)LiftPanel.MAX_DOORS_OPENING / 4));
                            }
                            if(lift.getDoorsOverture() == 0){
                                model.setDoorsClosed();
                            }
                            break;
                    }
                    
                    lift.repaint();
                } catch (LiftException ex) {
                    Logger.getLogger(LiftFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        timer.start();
    }
    
    public void requestFloor(int index){
        try {
            this.model.requestFloor(index);
        } catch (LiftException ex) {
            Logger.getLogger(LiftFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setSourceButtonSelected(java.awt.event.ActionEvent evt){
        ((javax.swing.JButton) evt.getSource()).setSelected(true);
    }
    
    private void setFloorButtonUnselected(int index){
        /*
        switch(index){
            case 0 :
                this.mainButton0.setSelected(false);
                break;
            case 1 :
                this.mainButton1.setSelected(false);
                break;
            case 2 : 
                this.mainButton2.setSelected(false);
                break;
        }
        */
        
        //Better (in case of adding new floors and new buttons)
        //Uses reflexives calls on mainButtonX where X is the floor number.
        try {
            Field f = this.getClass().getDeclaredField("mainButton" + index);
            f.setAccessible(true);
            f.get(this).getClass().getMethod("setSelected", new Class[]{boolean.class}).invoke(f.get(this), false);
        } catch (NoSuchFieldException | NoSuchMethodException| SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(LiftFrame.class.getName()).log(Level.SEVERE, null, ex);
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
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        aboutDialog = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        content = new javax.swing.JPanel();
        mainBar = new javax.swing.JToolBar();
        mainButton0 = new javax.swing.JButton();
        mainButton1 = new javax.swing.JButton();
        mainButton2 = new javax.swing.JButton();
        mainButton3 = new javax.swing.JButton();
        split = new javax.swing.JSplitPane();
        splitLift = new javax.swing.JPanel();
        labelLift = new javax.swing.JLabel();
        contentLift = new javax.swing.JPanel();
        lift = new aihm.LiftPanel();
        panelLiftButtons = new javax.swing.JPanel();
        floor3 = new javax.swing.JPanel();
        liftButton3 = new javax.swing.JButton();
        floor2 = new javax.swing.JPanel();
        liftButton2 = new javax.swing.JButton();
        floor1 = new javax.swing.JPanel();
        liftButton1 = new javax.swing.JButton();
        floor0 = new javax.swing.JPanel();
        liftButton0 = new javax.swing.JButton();
        splitCab = new javax.swing.JPanel();
        labelCab = new javax.swing.JLabel();
        panelCabButtons = new javax.swing.JPanel();
        cabButton0 = new javax.swing.JButton();
        cabButton1 = new javax.swing.JButton();
        cabButton2 = new javax.swing.JButton();
        cabState = new javax.swing.JLabel();
        cabButton3 = new javax.swing.JButton();
        cabBell = new javax.swing.JButton();
        menu = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        MenuFileQuit = new javax.swing.JMenuItem();
        menuOptions = new javax.swing.JMenu();
        optionsSoundMusic = new javax.swing.JCheckBoxMenuItem();
        optionsSoundDing = new javax.swing.JCheckBoxMenuItem();

        aboutDialog.setMinimumSize(new java.awt.Dimension(300, 160));
        aboutDialog.setResizable(false);
        aboutDialog.setType(java.awt.Window.Type.UTILITY);

        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0};
        jPanel1Layout.rowHeights = new int[] {0, 5, 0, 5, 0};
        jPanel1.setLayout(jPanel1Layout);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("225kg Max.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText("En cas de panne, contactez Karl Woditsch (2A IR)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel3.setText("REF : TP-2AIR-AIHM-2012-2013");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanel1.add(jLabel3, gridBagConstraints);

        javax.swing.GroupLayout aboutDialogLayout = new javax.swing.GroupLayout(aboutDialog.getContentPane());
        aboutDialog.getContentPane().setLayout(aboutDialogLayout);
        aboutDialogLayout.setHorizontalGroup(
            aboutDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
        );
        aboutDialogLayout.setVerticalGroup(
            aboutDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulation d'ascenseur");
        setPreferredSize(new java.awt.Dimension(400, 630));

        content.setLayout(new java.awt.BorderLayout());

        mainBar.setFloatable(false);
        mainBar.setRollover(true);

        mainButton0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Zero.png"))); // NOI18N
        mainButton0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton0.setFocusable(false);
        mainButton0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/ZeroSelected.png"))); // NOI18N
        mainButton0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainButton0ActionPerformed(evt);
            }
        });
        mainBar.add(mainButton0);

        mainButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/One.png"))); // NOI18N
        mainButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton1.setFocusable(false);
        mainButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/OneSelected.png"))); // NOI18N
        mainButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainButton1ActionPerformed(evt);
            }
        });
        mainBar.add(mainButton1);

        mainButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Two.png"))); // NOI18N
        mainButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton2.setFocusable(false);
        mainButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/TwoSelected.png"))); // NOI18N
        mainButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainButton2ActionPerformed(evt);
            }
        });
        mainBar.add(mainButton2);

        mainButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Three.png"))); // NOI18N
        mainButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton3.setFocusable(false);
        mainButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/ThreeSelected.png"))); // NOI18N
        mainButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainButton3ActionPerformed(evt);
            }
        });
        mainBar.add(mainButton3);

        content.add(mainBar, java.awt.BorderLayout.PAGE_START);

        split.setBorder(null);
        split.setDividerLocation(250);
        split.setDividerSize(1);

        splitLift.setBackground(new java.awt.Color(255, 255, 255));
        splitLift.setLayout(new java.awt.BorderLayout());

        labelLift.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelLift.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLift.setText("Ascenseur");
        splitLift.add(labelLift, java.awt.BorderLayout.PAGE_START);

        contentLift.setBackground(new java.awt.Color(255, 255, 255));

        lift.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout liftLayout = new javax.swing.GroupLayout(lift);
        lift.setLayout(liftLayout);
        liftLayout.setHorizontalGroup(
            liftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
        );
        liftLayout.setVerticalGroup(
            liftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );

        panelLiftButtons.setBackground(new java.awt.Color(255, 255, 255));
        panelLiftButtons.setLayout(new java.awt.GridLayout(4, 1));

        floor3.setBackground(new java.awt.Color(255, 255, 255));
        floor3.setLayout(new javax.swing.BoxLayout(floor3, javax.swing.BoxLayout.LINE_AXIS));

        liftButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Call.png"))); // NOI18N
        liftButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton3.setFocusable(false);
        liftButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/CallSelected.png"))); // NOI18N
        liftButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton3, org.jdesktop.beansbinding.ELProperty.create("${selected}"), liftButton3, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        liftButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liftButton3ActionPerformed(evt);
            }
        });
        floor3.add(liftButton3);

        panelLiftButtons.add(floor3);

        floor2.setBackground(new java.awt.Color(255, 255, 255));
        floor2.setLayout(new javax.swing.BoxLayout(floor2, javax.swing.BoxLayout.LINE_AXIS));

        liftButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Call.png"))); // NOI18N
        liftButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton2.setFocusable(false);
        liftButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/CallSelected.png"))); // NOI18N
        liftButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton2, org.jdesktop.beansbinding.ELProperty.create("${selected}"), liftButton2, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        liftButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liftButton2ActionPerformed(evt);
            }
        });
        floor2.add(liftButton2);

        panelLiftButtons.add(floor2);

        floor1.setBackground(new java.awt.Color(255, 255, 255));
        floor1.setLayout(new javax.swing.BoxLayout(floor1, javax.swing.BoxLayout.LINE_AXIS));

        liftButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Call.png"))); // NOI18N
        liftButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton1.setFocusable(false);
        liftButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        liftButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/CallSelected.png"))); // NOI18N
        liftButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton1, org.jdesktop.beansbinding.ELProperty.create("${selected}"), liftButton1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        liftButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liftButton1ActionPerformed(evt);
            }
        });
        floor1.add(liftButton1);

        panelLiftButtons.add(floor1);

        floor0.setBackground(new java.awt.Color(255, 255, 255));
        floor0.setLayout(new javax.swing.BoxLayout(floor0, javax.swing.BoxLayout.LINE_AXIS));

        liftButton0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Call.png"))); // NOI18N
        liftButton0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton0.setFocusable(false);
        liftButton0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        liftButton0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/CallSelected.png"))); // NOI18N
        liftButton0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton0, org.jdesktop.beansbinding.ELProperty.create("${selected}"), liftButton0, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        liftButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liftButton0ActionPerformed(evt);
            }
        });
        floor0.add(liftButton0);

        panelLiftButtons.add(floor0);

        javax.swing.GroupLayout contentLiftLayout = new javax.swing.GroupLayout(contentLift);
        contentLift.setLayout(contentLiftLayout);
        contentLiftLayout.setHorizontalGroup(
            contentLiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLiftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLiftButtons, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );
        contentLiftLayout.setVerticalGroup(
            contentLiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLiftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentLiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lift, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelLiftButtons, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE))
                .addContainerGap())
        );

        splitLift.add(contentLift, java.awt.BorderLayout.CENTER);

        split.setLeftComponent(splitLift);

        splitCab.setBackground(new java.awt.Color(255, 255, 255));
        splitCab.setLayout(new java.awt.BorderLayout());

        labelCab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelCab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCab.setText("Cabine");
        splitCab.add(labelCab, java.awt.BorderLayout.PAGE_START);

        panelCabButtons.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout panelCabButtonsLayout = new java.awt.GridBagLayout();
        panelCabButtonsLayout.columnWidths = new int[] {0};
        panelCabButtonsLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        panelCabButtons.setLayout(panelCabButtonsLayout);

        cabButton0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Zero.png"))); // NOI18N
        cabButton0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton0.setFocusable(false);
        cabButton0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cabButton0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/ZeroSelected.png"))); // NOI18N
        cabButton0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton0, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton0, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        cabButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cabButton0ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        panelCabButtons.add(cabButton0, gridBagConstraints);

        cabButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/One.png"))); // NOI18N
        cabButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton1.setFocusable(false);
        cabButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cabButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/OneSelected.png"))); // NOI18N
        cabButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton1, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        cabButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cabButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        panelCabButtons.add(cabButton1, gridBagConstraints);

        cabButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Two.png"))); // NOI18N
        cabButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton2.setFocusable(false);
        cabButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cabButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/TwoSelected.png"))); // NOI18N
        cabButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton2, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton2, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        cabButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cabButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        panelCabButtons.add(cabButton2, gridBagConstraints);

        cabState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cabState.setText("0");
        cabState.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.ipady = 6;
        panelCabButtons.add(cabState, gridBagConstraints);

        cabButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Three.png"))); // NOI18N
        cabButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton3.setFocusable(false);
        cabButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cabButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/ThreeSelected.png"))); // NOI18N
        cabButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton3, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton3, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        cabButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cabButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        panelCabButtons.add(cabButton3, gridBagConstraints);

        cabBell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Bell.png"))); // NOI18N
        cabBell.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabBell.setFocusable(false);
        cabBell.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/BellSelected.png"))); // NOI18N
        cabBell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cabBellActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelCabButtons.add(cabBell, gridBagConstraints);

        splitCab.add(panelCabButtons, java.awt.BorderLayout.CENTER);

        split.setRightComponent(splitCab);

        content.add(split, java.awt.BorderLayout.CENTER);

        menuFile.setText("Fichier");

        MenuFileQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        MenuFileQuit.setText("Quitter");
        MenuFileQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFileQuitActionPerformed(evt);
            }
        });
        menuFile.add(MenuFileQuit);

        menu.add(menuFile);

        menuOptions.setText("Options");

        optionsSoundMusic.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        optionsSoundMusic.setSelected(true);
        optionsSoundMusic.setText("Musique");
        optionsSoundMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsSoundMusicActionPerformed(evt);
            }
        });
        menuOptions.add(optionsSoundMusic);

        optionsSoundDing.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        optionsSoundDing.setSelected(true);
        optionsSoundDing.setText("Bruitages");
        optionsSoundDing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsSoundDingActionPerformed(evt);
            }
        });
        menuOptions.add(optionsSoundDing);

        menu.add(menuOptions);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuFileQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFileQuitActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_MenuFileQuitActionPerformed

    private void mainButton0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainButton0ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(0);
    }//GEN-LAST:event_mainButton0ActionPerformed

    private void mainButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainButton1ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(1);
    }//GEN-LAST:event_mainButton1ActionPerformed

    private void mainButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainButton2ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(2);
    }//GEN-LAST:event_mainButton2ActionPerformed

    private void liftButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liftButton2ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(2);
    }//GEN-LAST:event_liftButton2ActionPerformed

    private void liftButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liftButton1ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(1);
    }//GEN-LAST:event_liftButton1ActionPerformed

    private void liftButton0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liftButton0ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(0);
    }//GEN-LAST:event_liftButton0ActionPerformed

    private void cabButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cabButton2ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(2);
    }//GEN-LAST:event_cabButton2ActionPerformed

    private void cabButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cabButton1ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(1);
    }//GEN-LAST:event_cabButton1ActionPerformed

    private void cabButton0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cabButton0ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(0);
    }//GEN-LAST:event_cabButton0ActionPerformed

    private void mainButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainButton3ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(3);
    }//GEN-LAST:event_mainButton3ActionPerformed

    private void cabButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cabButton3ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(3);
    }//GEN-LAST:event_cabButton3ActionPerformed

    private void liftButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liftButton3ActionPerformed
        this.setSourceButtonSelected(evt);
        this.requestFloor(3);
    }//GEN-LAST:event_liftButton3ActionPerformed

    private void optionsSoundMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsSoundMusicActionPerformed
        if(optionsSoundMusic.isSelected()){
            this.audioMusic.volume(MUSIC_BASE_VOL);
        }
        else{
            this.audioMusic.volume(AudioPlayer.MIN_VOLUME);
        }
    }//GEN-LAST:event_optionsSoundMusicActionPerformed

    private void optionsSoundDingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsSoundDingActionPerformed
        if(optionsSoundDing.isSelected()){
            this.audioDing.volume(AudioPlayer.RESET_VOLUME);
        }
        else{
            this.audioDing.volume(AudioPlayer.MIN_VOLUME);
        }
    }//GEN-LAST:event_optionsSoundDingActionPerformed

    private void cabBellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cabBellActionPerformed
        if(!this.aboutDialog.isVisible()){
            GraphicsConfiguration gc = this.aboutDialog.getGraphicsConfiguration();  
            Rectangle bounds = gc.getBounds();
            this.aboutDialog.setLocation((int) ((bounds.width / 2) - (this.aboutDialog.getSize().width / 2)),  
                                         (int) ((bounds.height / 2) - (this.aboutDialog.getSize().height / 2)));
        }
        this.aboutDialog.setVisible(true);
    }//GEN-LAST:event_cabBellActionPerformed

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
            java.util.logging.Logger.getLogger(LiftFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiftFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiftFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiftFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        final Lift liftModel = new Lift(LiftPanel.NB_FLOORS);
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LiftFrame frame = new LiftFrame(liftModel);
                GraphicsConfiguration gc = frame.getGraphicsConfiguration();  
                Rectangle bounds = gc.getBounds();
                frame.setLocation((int) ((bounds.width / 2) - (frame.getSize().width / 2)),  
                                  (int) ((bounds.height / 2) - (frame.getSize().height / 2)));
                frame.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuFileQuit;
    private javax.swing.JDialog aboutDialog;
    private javax.swing.JButton cabBell;
    private javax.swing.JButton cabButton0;
    private javax.swing.JButton cabButton1;
    private javax.swing.JButton cabButton2;
    private javax.swing.JButton cabButton3;
    private javax.swing.JLabel cabState;
    private javax.swing.JPanel content;
    private javax.swing.JPanel contentLift;
    private javax.swing.JPanel floor0;
    private javax.swing.JPanel floor1;
    private javax.swing.JPanel floor2;
    private javax.swing.JPanel floor3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelCab;
    private javax.swing.JLabel labelLift;
    private aihm.LiftPanel lift;
    private javax.swing.JButton liftButton0;
    private javax.swing.JButton liftButton1;
    private javax.swing.JButton liftButton2;
    private javax.swing.JButton liftButton3;
    private javax.swing.JToolBar mainBar;
    private javax.swing.JButton mainButton0;
    private javax.swing.JButton mainButton1;
    private javax.swing.JButton mainButton2;
    private javax.swing.JButton mainButton3;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuOptions;
    private javax.swing.JCheckBoxMenuItem optionsSoundDing;
    private javax.swing.JCheckBoxMenuItem optionsSoundMusic;
    private javax.swing.JPanel panelCabButtons;
    private javax.swing.JPanel panelLiftButtons;
    private javax.swing.JSplitPane split;
    private javax.swing.JPanel splitCab;
    private javax.swing.JPanel splitLift;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
