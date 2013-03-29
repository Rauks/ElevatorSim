/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

import aihm.ui.lift.LiftPanel;
import aihm.model.Lift;
import aihm.model.LiftException;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
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
        final int openWaitDoorDef = 4;
        
        Timer timer = new Timer(1, new ActionListener(){
            private int openWait = openWaitDef;
            private int openWaitDoor = openWaitDoorDef;
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
                                this.openWait = openWaitDef;
                            }
                            break;
                        case CLOSED :
                            //Current floor announce
                            int floorPX = (LiftPanel.MAX_POS_X / LiftPanel.NB_FLOORS);
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
                            if(this.openWaitDoor > 0){
                                this.openWaitDoor--;
                            }
                            else{
                                lift.incrDoorsOverture();
                                this.openWaitDoor = openWaitDoorDef;
                                if(optionsSoundMusic.isSelected()){
                                    audioMusic.volume(MUSIC_BASE_VOL + ((float)lift.getDoorsOverture() / (float)LiftPanel.MAX_DOORS_OPENING / 4));
                                }
                                if(lift.getDoorsOverture() == LiftPanel.MAX_DOORS_OPENING){
                                    model.setDoorsOpened();
                                }
                            }
                            
                            break;
                        case CLOSING :
                            if(this.openWaitDoor > 0){
                                this.openWaitDoor--;
                            }
                            else{
                                lift.decrDoorsOverture();
                                this.openWaitDoor = openWaitDoorDef;
                                if(optionsSoundMusic.isSelected()){
                                    audioMusic.volume(MUSIC_BASE_VOL + ((float)lift.getDoorsOverture() / (float)LiftPanel.MAX_DOORS_OPENING / 4));
                                }
                                if(lift.getDoorsOverture() == 0){
                                    model.setDoorsClosed();
                                }
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
            f.get(this).getClass().getMethod("setForeground", new Class[]{Color.class}).invoke(f.get(this), Color.BLACK);
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
        mainButton4 = new javax.swing.JButton();
        mainButton5 = new javax.swing.JButton();
        mainButton6 = new javax.swing.JButton();
        mainButton7 = new javax.swing.JButton();
        mainButton8 = new javax.swing.JButton();
        mainButton9 = new javax.swing.JButton();
        mainButton10 = new javax.swing.JButton();
        mainButton11 = new javax.swing.JButton();
        mainButton12 = new javax.swing.JButton();
        mainButton13 = new javax.swing.JButton();
        mainButton14 = new javax.swing.JButton();
        splitCab = new javax.swing.JPanel();
        labelCab = new javax.swing.JLabel();
        panelCabButtons = new javax.swing.JPanel();
        cabState = new javax.swing.JLabel();
        cabBell = new javax.swing.JButton();
        cabButton0 = new javax.swing.JButton();
        cabButton1 = new javax.swing.JButton();
        cabButton2 = new javax.swing.JButton();
        cabButton3 = new javax.swing.JButton();
        cabButton4 = new javax.swing.JButton();
        cabButton5 = new javax.swing.JButton();
        cabButton6 = new javax.swing.JButton();
        cabButton7 = new javax.swing.JButton();
        cabButton8 = new javax.swing.JButton();
        cabButton9 = new javax.swing.JButton();
        cabButton10 = new javax.swing.JButton();
        cabButton11 = new javax.swing.JButton();
        cabButton12 = new javax.swing.JButton();
        cabButton13 = new javax.swing.JButton();
        cabButton14 = new javax.swing.JButton();
        splitLift = new javax.swing.JPanel();
        lift = new aihm.ui.lift.LiftPanel();
        liftButtons = new javax.swing.JPanel();
        liftButton1 = new javax.swing.JButton();
        liftButton2 = new javax.swing.JButton();
        liftButton3 = new javax.swing.JButton();
        liftButton4 = new javax.swing.JButton();
        liftButton5 = new javax.swing.JButton();
        liftButton6 = new javax.swing.JButton();
        liftButton7 = new javax.swing.JButton();
        liftButton8 = new javax.swing.JButton();
        liftButton9 = new javax.swing.JButton();
        liftButton10 = new javax.swing.JButton();
        liftButton11 = new javax.swing.JButton();
        liftButton12 = new javax.swing.JButton();
        liftButton13 = new javax.swing.JButton();
        liftButton14 = new javax.swing.JButton();
        liftButton0 = new javax.swing.JButton();
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        aboutDialogLayout.setVerticalGroup(
            aboutDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulation d'ascenseur");
        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/aihm/res/Elevator.png")));

        content.setBackground(new java.awt.Color(204, 204, 204));
        content.setLayout(new java.awt.BorderLayout(1, 0));

        mainBar.setFloatable(false);
        mainBar.setRollover(true);

        mainButton0.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton0.setText("0");
        mainButton0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton0.setFocusable(false);
        mainButton0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton0.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton0.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton0.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton0.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton0);

        mainButton1.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton1.setText("1");
        mainButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton1.setFocusable(false);
        mainButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton1.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton1.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton1.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton1.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton1);

        mainButton2.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton2.setText("2");
        mainButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton2.setFocusable(false);
        mainButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton2.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton2.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton2.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton2.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton2);

        mainButton3.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton3.setText("3");
        mainButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton3.setFocusable(false);
        mainButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton3.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton3.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton3.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton3.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton3);

        mainButton4.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton4.setText("4");
        mainButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton4.setFocusable(false);
        mainButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton4.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton4.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton4.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton4.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton4);

        mainButton5.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton5.setText("5");
        mainButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton5.setFocusable(false);
        mainButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton5.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton5.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton5.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton5.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton5);

        mainButton6.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton6.setText("6");
        mainButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton6.setFocusable(false);
        mainButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton6.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton6.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton6.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton6.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton6);

        mainButton7.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton7.setText("7");
        mainButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton7.setFocusable(false);
        mainButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton7.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton7.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton7.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton7.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton7);

        mainButton8.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton8.setText("8");
        mainButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton8.setFocusable(false);
        mainButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton8.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton8.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton8.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton8.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton8);

        mainButton9.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton9.setText("9");
        mainButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton9.setFocusable(false);
        mainButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton9.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton9.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton9.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton9.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton9);

        mainButton10.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton10.setText("10");
        mainButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton10.setFocusable(false);
        mainButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton10.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton10.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton10.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton10.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton10);

        mainButton11.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton11.setText("11");
        mainButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton11.setFocusable(false);
        mainButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton11.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton11.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton11.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton11.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton11);

        mainButton12.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton12.setText("12");
        mainButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton12.setFocusable(false);
        mainButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton12.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton12.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton12.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton12.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton12);

        mainButton13.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton13.setText("13");
        mainButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton13.setFocusable(false);
        mainButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton13.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton13.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton13.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton13.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton13);

        mainButton14.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        mainButton14.setText("14");
        mainButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainButton14.setFocusable(false);
        mainButton14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mainButton14.setMargin(new java.awt.Insets(6, 1, 1, 1));
        mainButton14.setMaximumSize(new java.awt.Dimension(40, 31));
        mainButton14.setMinimumSize(new java.awt.Dimension(40, 31));
        mainButton14.setPreferredSize(new java.awt.Dimension(40, 31));
        mainButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        mainBar.add(mainButton14);

        content.add(mainBar, java.awt.BorderLayout.PAGE_START);

        splitCab.setBackground(new java.awt.Color(255, 255, 255));
        splitCab.setLayout(new java.awt.BorderLayout());

        labelCab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelCab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCab.setText("Cabine");
        splitCab.add(labelCab, java.awt.BorderLayout.PAGE_START);

        panelCabButtons.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout panelCabButtonsLayout = new java.awt.GridBagLayout();
        panelCabButtonsLayout.columnWidths = new int[] {0, 5, 0, 5, 0};
        panelCabButtonsLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        panelCabButtons.setLayout(panelCabButtonsLayout);

        cabState.setFont(new java.awt.Font("kroeger 05_55", 1, 30)); // NOI18N
        cabState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cabState.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.ipady = 6;
        panelCabButtons.add(cabState, gridBagConstraints);

        cabBell.setBackground(new java.awt.Color(255, 255, 255));
        cabBell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/Bell.png"))); // NOI18N
        cabBell.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabBell.setFocusable(false);
        cabBell.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabBell.setMaximumSize(new java.awt.Dimension(55, 35));
        cabBell.setMinimumSize(new java.awt.Dimension(55, 35));
        cabBell.setPreferredSize(new java.awt.Dimension(55, 35));
        cabBell.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/aihm/res/BellSelected.png"))); // NOI18N
        cabBell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cabBellActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        panelCabButtons.add(cabBell, gridBagConstraints);

        cabButton0.setBackground(new java.awt.Color(255, 255, 255));
        cabButton0.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton0.setText("0");
        cabButton0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton0.setFocusable(false);
        cabButton0.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton0.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton0.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton0.setPreferredSize(new java.awt.Dimension(55, 35));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton0, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton0, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton0, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton0, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        panelCabButtons.add(cabButton0, gridBagConstraints);

        cabButton1.setBackground(new java.awt.Color(255, 255, 255));
        cabButton1.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton1.setText("1");
        cabButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton1.setFocusable(false);
        cabButton1.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton1.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton1.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton1.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton1, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton1, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton1, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        panelCabButtons.add(cabButton1, gridBagConstraints);

        cabButton2.setBackground(new java.awt.Color(255, 255, 255));
        cabButton2.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton2.setText("2");
        cabButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton2.setFocusable(false);
        cabButton2.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton2.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton2.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton2.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton2, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton2, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton2, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton2, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        panelCabButtons.add(cabButton2, gridBagConstraints);

        cabButton3.setBackground(new java.awt.Color(255, 255, 255));
        cabButton3.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton3.setText("3");
        cabButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton3.setFocusable(false);
        cabButton3.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton3.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton3.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton3.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton3, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton3, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton3, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton3, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        panelCabButtons.add(cabButton3, gridBagConstraints);

        cabButton4.setBackground(new java.awt.Color(255, 255, 255));
        cabButton4.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton4.setText("4");
        cabButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton4.setFocusable(false);
        cabButton4.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton4.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton4.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton4.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton4, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton4, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton4, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton4, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelCabButtons.add(cabButton4, gridBagConstraints);

        cabButton5.setBackground(new java.awt.Color(255, 255, 255));
        cabButton5.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton5.setText("5");
        cabButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton5.setFocusable(false);
        cabButton5.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton5.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton5.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton5.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cabButton5, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton5, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton5, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton5, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        panelCabButtons.add(cabButton5, gridBagConstraints);

        cabButton6.setBackground(new java.awt.Color(255, 255, 255));
        cabButton6.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton6.setText("6");
        cabButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton6.setFocusable(false);
        cabButton6.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton6.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton6.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton6.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton6, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton6, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton6, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton6, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        panelCabButtons.add(cabButton6, gridBagConstraints);

        cabButton7.setBackground(new java.awt.Color(255, 255, 255));
        cabButton7.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton7.setText("7");
        cabButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton7.setFocusable(false);
        cabButton7.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton7.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton7.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton7.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton7, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton7, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton7, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton7, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        panelCabButtons.add(cabButton7, gridBagConstraints);

        cabButton8.setBackground(new java.awt.Color(255, 255, 255));
        cabButton8.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton8.setText("8");
        cabButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton8.setFocusable(false);
        cabButton8.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton8.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton8.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton8.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton8, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton8, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton8, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton8, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        panelCabButtons.add(cabButton8, gridBagConstraints);

        cabButton9.setBackground(new java.awt.Color(255, 255, 255));
        cabButton9.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton9.setText("9");
        cabButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton9.setFocusable(false);
        cabButton9.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton9.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton9.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton9.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton9, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton9, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton9, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton9, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        panelCabButtons.add(cabButton9, gridBagConstraints);

        cabButton10.setBackground(new java.awt.Color(255, 255, 255));
        cabButton10.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton10.setText("10");
        cabButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton10.setFocusable(false);
        cabButton10.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton10.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton10.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton10.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton10, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton10, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton10, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton10, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        panelCabButtons.add(cabButton10, gridBagConstraints);

        cabButton11.setBackground(new java.awt.Color(255, 255, 255));
        cabButton11.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton11.setText("11");
        cabButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton11.setFocusable(false);
        cabButton11.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton11.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton11.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton11.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton11, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton11, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton11, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton11, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        panelCabButtons.add(cabButton11, gridBagConstraints);

        cabButton12.setBackground(new java.awt.Color(255, 255, 255));
        cabButton12.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton12.setText("12");
        cabButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton12.setFocusable(false);
        cabButton12.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton12.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton12.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton12.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton12, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton12, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton12, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton12, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        panelCabButtons.add(cabButton12, gridBagConstraints);

        cabButton13.setBackground(new java.awt.Color(255, 255, 255));
        cabButton13.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton13.setText("13");
        cabButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton13.setFocusable(false);
        cabButton13.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton13.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton13.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton13.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton13, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton13, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton13, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton13, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        panelCabButtons.add(cabButton13, gridBagConstraints);

        cabButton14.setBackground(new java.awt.Color(255, 255, 255));
        cabButton14.setFont(new java.awt.Font("kroeger 05_55", 1, 22)); // NOI18N
        cabButton14.setText("14");
        cabButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cabButton14.setFocusable(false);
        cabButton14.setMargin(new java.awt.Insets(6, 1, 1, 1));
        cabButton14.setMaximumSize(new java.awt.Dimension(55, 35));
        cabButton14.setMinimumSize(new java.awt.Dimension(55, 35));
        cabButton14.setPreferredSize(new java.awt.Dimension(55, 35));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton14, org.jdesktop.beansbinding.ELProperty.create("${selected}"), cabButton14, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mainButton14, org.jdesktop.beansbinding.ELProperty.create("${foreground}"), cabButton14, org.jdesktop.beansbinding.BeanProperty.create("foreground"));
        bindingGroup.addBinding(binding);

        cabButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionButtonRequestFloorHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        panelCabButtons.add(cabButton14, gridBagConstraints);

        splitCab.add(panelCabButtons, java.awt.BorderLayout.CENTER);

        content.add(splitCab, java.awt.BorderLayout.CENTER);

        splitLift.setBackground(new java.awt.Color(255, 255, 255));
        splitLift.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lift.setBackground(new java.awt.Color(255, 255, 255));
        lift.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        liftButtons.setOpaque(false);

        liftButton1.setBackground(new java.awt.Color(255, 255, 255));
        liftButton1.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton1.setText("1");
        liftButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton1.setFocusable(false);
        liftButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton1.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton1.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton1.setOpaque(false);
        liftButton1.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton2.setBackground(new java.awt.Color(255, 255, 255));
        liftButton2.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton2.setText("2");
        liftButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton2.setFocusable(false);
        liftButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton2.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton2.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton2.setOpaque(false);
        liftButton2.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton3.setBackground(new java.awt.Color(255, 255, 255));
        liftButton3.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton3.setText("3");
        liftButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton3.setFocusable(false);
        liftButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton3.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton3.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton3.setOpaque(false);
        liftButton3.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton4.setBackground(new java.awt.Color(255, 255, 255));
        liftButton4.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton4.setText("4");
        liftButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton4.setFocusable(false);
        liftButton4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton4.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton4.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton4.setOpaque(false);
        liftButton4.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton5.setBackground(new java.awt.Color(255, 255, 255));
        liftButton5.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton5.setText("5");
        liftButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton5.setFocusable(false);
        liftButton5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton5.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton5.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton5.setOpaque(false);
        liftButton5.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton6.setBackground(new java.awt.Color(255, 255, 255));
        liftButton6.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton6.setText("6");
        liftButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton6.setFocusable(false);
        liftButton6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton6.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton6.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton6.setOpaque(false);
        liftButton6.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton7.setBackground(new java.awt.Color(255, 255, 255));
        liftButton7.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton7.setText("7");
        liftButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton7.setFocusable(false);
        liftButton7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton7.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton7.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton7.setOpaque(false);
        liftButton7.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton8.setBackground(new java.awt.Color(255, 255, 255));
        liftButton8.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton8.setText("8");
        liftButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton8.setFocusable(false);
        liftButton8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton8.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton8.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton8.setOpaque(false);
        liftButton8.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton9.setBackground(new java.awt.Color(255, 255, 255));
        liftButton9.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton9.setText("9");
        liftButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton9.setFocusable(false);
        liftButton9.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton9.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton9.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton9.setOpaque(false);
        liftButton9.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton10.setBackground(new java.awt.Color(255, 255, 255));
        liftButton10.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton10.setText("10");
        liftButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton10.setFocusable(false);
        liftButton10.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton10.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton10.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton10.setOpaque(false);
        liftButton10.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton11.setBackground(new java.awt.Color(255, 255, 255));
        liftButton11.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton11.setText("11");
        liftButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton11.setFocusable(false);
        liftButton11.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton11.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton11.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton11.setOpaque(false);
        liftButton11.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton12.setBackground(new java.awt.Color(255, 255, 255));
        liftButton12.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton12.setText("12");
        liftButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton12.setFocusable(false);
        liftButton12.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton12.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton12.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton12.setOpaque(false);
        liftButton12.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton13.setBackground(new java.awt.Color(255, 255, 255));
        liftButton13.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton13.setText("13");
        liftButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton13.setFocusable(false);
        liftButton13.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton13.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton13.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton13.setOpaque(false);
        liftButton13.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton14.setBackground(new java.awt.Color(255, 255, 255));
        liftButton14.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton14.setText("14");
        liftButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton14.setFocusable(false);
        liftButton14.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton14.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton14.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton14.setOpaque(false);
        liftButton14.setPreferredSize(new java.awt.Dimension(40, 23));

        liftButton0.setBackground(new java.awt.Color(255, 255, 255));
        liftButton0.setFont(new java.awt.Font("kroeger 05_55", 1, 10)); // NOI18N
        liftButton0.setText("0");
        liftButton0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        liftButton0.setFocusable(false);
        liftButton0.setMargin(new java.awt.Insets(0, 0, 0, 0));
        liftButton0.setMaximumSize(new java.awt.Dimension(40, 23));
        liftButton0.setMinimumSize(new java.awt.Dimension(40, 23));
        liftButton0.setOpaque(false);
        liftButton0.setPreferredSize(new java.awt.Dimension(40, 23));

        javax.swing.GroupLayout liftButtonsLayout = new javax.swing.GroupLayout(liftButtons);
        liftButtons.setLayout(liftButtonsLayout);
        liftButtonsLayout.setHorizontalGroup(
            liftButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liftButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(liftButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(liftButton0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(liftButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        liftButtonsLayout.setVerticalGroup(
            liftButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liftButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(liftButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(liftButton0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout liftLayout = new javax.swing.GroupLayout(lift);
        lift.setLayout(liftLayout);
        liftLayout.setHorizontalGroup(
            liftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liftLayout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(liftButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(388, Short.MAX_VALUE))
        );
        liftLayout.setVerticalGroup(
            liftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, liftLayout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addComponent(liftButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        splitLift.add(lift, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        content.add(splitLift, java.awt.BorderLayout.LINE_START);

        menuFile.setMnemonic('F');
        menuFile.setText("Fichier");

        MenuFileQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        MenuFileQuit.setMnemonic('Q');
        MenuFileQuit.setText("Quitter");
        MenuFileQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFileQuitActionPerformed(evt);
            }
        });
        menuFile.add(MenuFileQuit);

        menu.add(menuFile);

        menuOptions.setMnemonic('O');
        menuOptions.setText("Options");

        optionsSoundMusic.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        optionsSoundMusic.setMnemonic('M');
        optionsSoundMusic.setSelected(true);
        optionsSoundMusic.setText("Musique");
        optionsSoundMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsSoundMusicActionPerformed(evt);
            }
        });
        menuOptions.add(optionsSoundMusic);

        optionsSoundDing.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        optionsSoundDing.setMnemonic('B');
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
            .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 989, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuFileQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFileQuitActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_MenuFileQuitActionPerformed

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

    private void actionButtonRequestFloorHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionButtonRequestFloorHandler
        JButton source = ((JButton) evt.getSource());
        int floorIndex = Integer.parseInt(source.getText());

        try {
            Field f = this.getClass().getDeclaredField("mainButton" + floorIndex);
            f.setAccessible(true);
            f.get(this).getClass().getMethod("setSelected", new Class[]{boolean.class}).invoke(f.get(this), true);
            f.get(this).getClass().getMethod("setForeground", new Class[]{Color.class}).invoke(f.get(this), Color.getHSBColor(0f, 1f, .8f));
        } catch (NoSuchFieldException | NoSuchMethodException| SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(LiftFrame.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        this.requestFloor(floorIndex);
    }//GEN-LAST:event_actionButtonRequestFloorHandler

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
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
    private javax.swing.JButton cabButton10;
    private javax.swing.JButton cabButton11;
    private javax.swing.JButton cabButton12;
    private javax.swing.JButton cabButton13;
    private javax.swing.JButton cabButton14;
    private javax.swing.JButton cabButton2;
    private javax.swing.JButton cabButton3;
    private javax.swing.JButton cabButton4;
    private javax.swing.JButton cabButton5;
    private javax.swing.JButton cabButton6;
    private javax.swing.JButton cabButton7;
    private javax.swing.JButton cabButton8;
    private javax.swing.JButton cabButton9;
    private javax.swing.JLabel cabState;
    private javax.swing.JPanel content;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelCab;
    private aihm.ui.lift.LiftPanel lift;
    private javax.swing.JButton liftButton0;
    private javax.swing.JButton liftButton1;
    private javax.swing.JButton liftButton10;
    private javax.swing.JButton liftButton11;
    private javax.swing.JButton liftButton12;
    private javax.swing.JButton liftButton13;
    private javax.swing.JButton liftButton14;
    private javax.swing.JButton liftButton2;
    private javax.swing.JButton liftButton3;
    private javax.swing.JButton liftButton4;
    private javax.swing.JButton liftButton5;
    private javax.swing.JButton liftButton6;
    private javax.swing.JButton liftButton7;
    private javax.swing.JButton liftButton8;
    private javax.swing.JButton liftButton9;
    private javax.swing.JPanel liftButtons;
    private javax.swing.JToolBar mainBar;
    private javax.swing.JButton mainButton0;
    private javax.swing.JButton mainButton1;
    private javax.swing.JButton mainButton10;
    private javax.swing.JButton mainButton11;
    private javax.swing.JButton mainButton12;
    private javax.swing.JButton mainButton13;
    private javax.swing.JButton mainButton14;
    private javax.swing.JButton mainButton2;
    private javax.swing.JButton mainButton3;
    private javax.swing.JButton mainButton4;
    private javax.swing.JButton mainButton5;
    private javax.swing.JButton mainButton6;
    private javax.swing.JButton mainButton7;
    private javax.swing.JButton mainButton8;
    private javax.swing.JButton mainButton9;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuOptions;
    private javax.swing.JCheckBoxMenuItem optionsSoundDing;
    private javax.swing.JCheckBoxMenuItem optionsSoundMusic;
    private javax.swing.JPanel panelCabButtons;
    private javax.swing.JPanel splitCab;
    private javax.swing.JPanel splitLift;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
