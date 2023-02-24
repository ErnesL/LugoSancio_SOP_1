/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lugosancio_sop_1.Ensamblador;
import lugosancio_sop_1.ProductorCierre;
import lugosancio_sop_1.ProductorCreditos;
import lugosancio_sop_1.ProductorInicio;
import lugosancio_sop_1.ProductorIntro;
import lugosancio_sop_1.ProductorPlottwist;

/**
 *
 * @author ernes
 */
public class Interface extends javax.swing.JFrame {

    //Config
    int duracionDiaEnSegundos = 5;
    int cantidadDeDiasEntreLanzamientos = 2;
    static int dias = 0;

    //Drive
    public static int driveIntro = 10;
    public static int driveCreditos = 5;
    public static int driveInicio = 5;
    public static int driveCierre = 5;
    public static int drivePlottwist = 5;

    //Buffers
    public static String[] bIntro;
    public static String[] bCreditos;
    public static String[] bInicio;
    public static String[] bCierre;
    public static String[] bPlottwist;

    //In/out
    public static int inIntro = 0;
    public static int outIntro = 0;
    public static int inCreditos = 0;
    public static int outCreditos = 0;
    public static int inInicio = 0;
    public static int outInicio = 0;
    public static int inCierre = 0;
    public static int outCierre = 0;
    public static int inPlottwist = 0;
    public static int outPlottwist = 0;

    /*
    * s = mutual exclusion in buffer
    * n = consumable items in buffer
    * e = empty spaces in buffer
     */
    public static Semaphore sIntro = new Semaphore(1);
    public static Semaphore nIntro = new Semaphore(0);
    public static Semaphore eIntro;
    public static Semaphore sCreditos = new Semaphore(1);
    public static Semaphore nCreditos = new Semaphore(0);
    public static Semaphore eCreditos;
    public static Semaphore sInicio = new Semaphore(1);
    public static Semaphore nInicio = new Semaphore(0);
    public static Semaphore eInicio;
    public static Semaphore sCierre = new Semaphore(1);
    public static Semaphore nCierre = new Semaphore(0);
    public static Semaphore eCierre;
    public static Semaphore sPlottwist = new Semaphore(1);
    public static Semaphore nPlottwist = new Semaphore(0);
    public static Semaphore ePlottwist;

    //Inicializando Threads con valores bases
    ProductorIntro tIntro = new ProductorIntro(1, "Intro", 1);
    ProductorCreditos tCreditos = new ProductorCreditos(1, "Creditos", 1);
    ProductorInicio tInicio = new ProductorInicio(1, "Inicio", 1);

    ProductorCierre tCierre = new ProductorCierre(1, "Cierre", 1);
    ProductorPlottwist tPlottwist = new ProductorPlottwist(1, "Plot Twist", 1);
    Ensamblador tEnsamblador = new Ensamblador(1, "Ensamblador", 1);

    public Interface() {
        initComponents();
    }

    public void Leer() {
        BufferedReader br;
        try {
            //Lectura
            br = new BufferedReader(new FileReader("config.txt"));
            String data = br.readLine();
            String[] dataSplit = data.split("//");
            String[] productoresSplit = dataSplit[3].split(",");
            String[] almacenamientoSplit = dataSplit[2].split(",");

            //Dias
            duracionDiaEnSegundos = Integer.parseInt(dataSplit[0]);
            cantidadDeDiasEntreLanzamientos = Integer.parseInt(dataSplit[1]);

            //Productores
            velmaIntro.setText(productoresSplit[0]);
            velmaCreditos.setText(productoresSplit[1]);
            velmaInicio.setText(productoresSplit[2]);
            velmaCierre.setText(productoresSplit[3]);
            velmaPlottwist.setText(productoresSplit[4]);

            tIntro.setNumeroDeProductores(Integer.parseInt(velmaIntro.getText()));
            tCreditos.setNumeroDeProductores(Integer.parseInt(velmaCreditos.getText()));
            tInicio.setNumeroDeProductores(Integer.parseInt(velmaInicio.getText()));
            tCierre.setNumeroDeProductores(Integer.parseInt(velmaCierre.getText()));
            tPlottwist.setNumeroDeProductores(Integer.parseInt(velmaPlottwist.getText()));

            //Ensamblador
            velmaEnsambladores.setText(dataSplit[4]);
            tEnsamblador.setNumeroDeProductores(Integer.parseInt(velmaEnsambladores.getText()));

            //Drive
            driveIntro = Integer.parseInt(almacenamientoSplit[0]);
            driveCreditos = Integer.parseInt(almacenamientoSplit[1]);
            driveInicio = Integer.parseInt(almacenamientoSplit[2]);
            driveCierre = Integer.parseInt(almacenamientoSplit[3]);
            drivePlottwist = Integer.parseInt(almacenamientoSplit[4]);

            bIntro = new String[driveIntro];
            bCreditos = new String[driveCreditos];
            bInicio = new String[driveInicio];
            bCierre = new String[driveCierre];
            bPlottwist = new String[drivePlottwist];

            eIntro = new Semaphore(driveIntro);
            eCreditos = new Semaphore(driveCreditos);
            eInicio = new Semaphore(driveInicio);
            eCierre = new Semaphore(driveCierre);
            ePlottwist = new Semaphore(drivePlottwist);

            velmaInventarioIntroMaximo.setText(Integer.toString(driveIntro));
            velmaInventarioCreditosMaximo.setText(Integer.toString(driveCreditos));
            velmaInventarioInicioMaximo.setText(Integer.toString(driveInicio));
            velmaInventarioCierreMaximo.setText(Integer.toString(driveCierre));
            velmaInventarioPlottwistMaximo.setText(Integer.toString(drivePlottwist));

            //InventarioDisponible
            velmaInventarioIntroDisponible.setText(Integer.toString(nIntro.availablePermits()));
            velmaInventarioCreditosDisponible.setText(Integer.toString(nCreditos.availablePermits()));
            velmaInventarioInicioDisponible.setText(Integer.toString(nInicio.availablePermits()));
            velmaInventarioCierreDisponible.setText(Integer.toString(nCierre.availablePermits()));
            velmaInventarioPlottwistDisponible.setText(Integer.toString(nPlottwist.availablePermits()));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        velmaProductoresLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        velmaPlottwist = new javax.swing.JTextField();
        velmaIntro = new javax.swing.JTextField();
        velmaCreditos = new javax.swing.JTextField();
        velmaInicio = new javax.swing.JTextField();
        velmaCierre = new javax.swing.JTextField();
        rmProductoresLabel = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        rmPlottwist = new javax.swing.JTextField();
        rmIntro = new javax.swing.JTextField();
        rmCreditos = new javax.swing.JTextField();
        rmInicio = new javax.swing.JTextField();
        rmCierre = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        velmaEnsambladores = new javax.swing.JTextField();
        velmaInventarioIntroDisponible = new javax.swing.JTextField();
        velmaInventarioCreditosDisponible = new javax.swing.JTextField();
        velmaInventarioInicioDisponible = new javax.swing.JTextField();
        velmaInventarioCierreDisponible = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        rmInventarioPlottwist = new javax.swing.JTextField();
        rmInventarioIntro = new javax.swing.JTextField();
        rmInventarioCreditos = new javax.swing.JTextField();
        rmInventarioInicio = new javax.swing.JTextField();
        rmInventarioCierre = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        velmaInventarioPlottwistDisponible = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        velmoCapsAcabadosEnUltimoLote = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        rmEnsambladores = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        rmCaps = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        velmaPMFaltas = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        velmaCaps = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        velmaPMActividad = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        velmaPMSalario = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        velmaGastoMensual = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        velmoGananciasTotales = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        velmaDirectorActividad = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        velmaDeadline = new javax.swing.JTextField();
        pmCapsAcabadosEnUltimoLote = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        rmPMFaltas = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        rmPMActividad = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        rmPMSalario = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        rmGastoMensual = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        rmGananciasTotales = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        rmDirectorActividad = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        rmDeadline = new javax.swing.JTextField();
        velmaEnsambladoresUp = new javax.swing.JButton();
        velmaIntroUp = new javax.swing.JButton();
        velmaCreditosUp = new javax.swing.JButton();
        velmaInicioUp = new javax.swing.JButton();
        velmaCierreUp = new javax.swing.JButton();
        velmaPlottwistUp = new javax.swing.JButton();
        rmIntroUp = new javax.swing.JButton();
        rmCreditosUp = new javax.swing.JButton();
        rmInicioUp = new javax.swing.JButton();
        rmCierreUp = new javax.swing.JButton();
        rmPlottwistUp = new javax.swing.JButton();
        rmEnsambladoresUp = new javax.swing.JButton();
        velmaPlottwistDown = new javax.swing.JButton();
        velmaEnsambladoresDown = new javax.swing.JButton();
        velmaCreditosDown = new javax.swing.JButton();
        velmaInicioDown = new javax.swing.JButton();
        velmaCierreDown = new javax.swing.JButton();
        velmaIntroDown = new javax.swing.JButton();
        rmIntroDown = new javax.swing.JButton();
        rmCreditosDown = new javax.swing.JButton();
        rmInicioDown = new javax.swing.JButton();
        rmCierreDown = new javax.swing.JButton();
        rmPlottwistDown = new javax.swing.JButton();
        rmEnsambladoresDown = new javax.swing.JButton();
        start = new javax.swing.JToggleButton();
        config = new javax.swing.JToggleButton();
        velmaInventarioIntroMaximo = new javax.swing.JTextField();
        velmaInventarioCreditosMaximo = new javax.swing.JTextField();
        velmaInventarioInicioMaximo = new javax.swing.JTextField();
        velmaInventarioCierreMaximo = new javax.swing.JTextField();
        velmaInventarioPlottwistMaximo = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("VELMA");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, -1, -1));

        jLabel3.setText("RICK & MORTY");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, -1, -1));

        velmaProductoresLabel.setText("CANTIDAD DE PRODUCTORES TRABAJANDO");
        jPanel1.add(velmaProductoresLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, -1, -1));

        jLabel5.setText("1. INTRO");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, -1));

        jLabel6.setText("2. CREDITOS");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, -1, -1));

        jLabel7.setText("3. INICIO");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, -1, -1));

        jLabel8.setText("4. CIERRE");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, -1, -1));

        jLabel9.setText("5. PLOT TWIST");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, -1, -1));

        velmaPlottwist.setEditable(false);
        velmaPlottwist.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaPlottwist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaPlottwistActionPerformed(evt);
            }
        });
        jPanel1.add(velmaPlottwist, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 40, -1));

        velmaIntro.setEditable(false);
        velmaIntro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaIntro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaIntroActionPerformed(evt);
            }
        });
        jPanel1.add(velmaIntro, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 40, -1));

        velmaCreditos.setEditable(false);
        velmaCreditos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaCreditos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaCreditosActionPerformed(evt);
            }
        });
        jPanel1.add(velmaCreditos, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 40, -1));

        velmaInicio.setEditable(false);
        velmaInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInicioActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 40, -1));

        velmaCierre.setEditable(false);
        velmaCierre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaCierreActionPerformed(evt);
            }
        });
        jPanel1.add(velmaCierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 40, -1));

        rmProductoresLabel.setText("CANTIDAD DE PRODUCTORES TRABAJANDO");
        jPanel1.add(rmProductoresLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, -1, -1));

        jLabel11.setText("1. INTRO");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, -1, -1));

        jLabel12.setText("2. CREDITOS");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, -1, -1));

        jLabel13.setText("3. INICIO");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, -1, -1));

        jLabel14.setText("4. CIERRE");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, -1, -1));

        jLabel15.setText("5. PLOT TWIST");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, -1, -1));

        rmPlottwist.setEditable(false);
        rmPlottwist.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmPlottwist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmPlottwistActionPerformed(evt);
            }
        });
        jPanel1.add(rmPlottwist, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 200, 40, -1));

        rmIntro.setEditable(false);
        rmIntro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmIntro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmIntroActionPerformed(evt);
            }
        });
        jPanel1.add(rmIntro, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 40, -1));

        rmCreditos.setEditable(false);
        rmCreditos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmCreditos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmCreditosActionPerformed(evt);
            }
        });
        jPanel1.add(rmCreditos, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 140, 40, -1));

        rmInicio.setEditable(false);
        rmInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmInicioActionPerformed(evt);
            }
        });
        jPanel1.add(rmInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 160, 40, -1));

        rmCierre.setEditable(false);
        rmCierre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmCierreActionPerformed(evt);
            }
        });
        jPanel1.add(rmCierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 180, 40, -1));

        jLabel16.setText("CANTIDAD DE ENSAMBLADORES");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, -1, -1));

        jLabel17.setText("1. INTRO");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, -1, -1));

        jLabel18.setText("2. CREDITOS");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, -1, -1));

        jLabel19.setText("3. INICIO");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, -1, -1));

        jLabel20.setText("4. CIERRE");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, -1, -1));

        jLabel21.setText("5. PLOT TWIST");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, -1, -1));

        velmaEnsambladores.setEditable(false);
        velmaEnsambladores.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaEnsambladores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaEnsambladoresActionPerformed(evt);
            }
        });
        jPanel1.add(velmaEnsambladores, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 370, 40, -1));

        velmaInventarioIntroDisponible.setEditable(false);
        velmaInventarioIntroDisponible.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioIntroDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioIntroDisponibleActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioIntroDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 40, -1));

        velmaInventarioCreditosDisponible.setEditable(false);
        velmaInventarioCreditosDisponible.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioCreditosDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioCreditosDisponibleActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioCreditosDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 40, -1));

        velmaInventarioInicioDisponible.setEditable(false);
        velmaInventarioInicioDisponible.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioInicioDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioInicioDisponibleActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioInicioDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, 40, -1));

        velmaInventarioCierreDisponible.setEditable(false);
        velmaInventarioCierreDisponible.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioCierreDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioCierreDisponibleActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioCierreDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, 40, -1));

        jLabel22.setText("INVENTARIO");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, -1, -1));

        jLabel23.setText("1. INTRO");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 260, -1, -1));

        jLabel24.setText("2. CREDITOS");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 280, -1, -1));

        jLabel25.setText("3. INICIO");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 300, -1, -1));

        jLabel26.setText("4. CIERRE");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 320, -1, -1));

        jLabel27.setText("5. PLOT TWIST");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 340, -1, -1));

        rmInventarioPlottwist.setEditable(false);
        rmInventarioPlottwist.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        rmInventarioPlottwist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmInventarioPlottwistActionPerformed(evt);
            }
        });
        jPanel1.add(rmInventarioPlottwist, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 340, 40, -1));

        rmInventarioIntro.setEditable(false);
        rmInventarioIntro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmInventarioIntro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmInventarioIntroActionPerformed(evt);
            }
        });
        jPanel1.add(rmInventarioIntro, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 260, 40, -1));

        rmInventarioCreditos.setEditable(false);
        rmInventarioCreditos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmInventarioCreditos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmInventarioCreditosActionPerformed(evt);
            }
        });
        jPanel1.add(rmInventarioCreditos, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 280, 40, -1));

        rmInventarioInicio.setEditable(false);
        rmInventarioInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmInventarioInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmInventarioInicioActionPerformed(evt);
            }
        });
        jPanel1.add(rmInventarioInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 300, 40, -1));

        rmInventarioCierre.setEditable(false);
        rmInventarioCierre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmInventarioCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmInventarioCierreActionPerformed(evt);
            }
        });
        jPanel1.add(rmInventarioCierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 40, -1));

        jLabel28.setText("ACTUAL");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, -1, -1));

        velmaInventarioPlottwistDisponible.setEditable(false);
        velmaInventarioPlottwistDisponible.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioPlottwistDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioPlottwistDisponibleActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioPlottwistDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 40, -1));

        jLabel29.setText("CANTIDAD DE CAPS TERMINADOS");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, -1, -1));

        velmoCapsAcabadosEnUltimoLote.setEditable(false);
        velmoCapsAcabadosEnUltimoLote.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmoCapsAcabadosEnUltimoLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmoCapsAcabadosEnUltimoLoteActionPerformed(evt);
            }
        });
        jPanel1.add(velmoCapsAcabadosEnUltimoLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 610, 40, -1));

        jLabel30.setText("CANTIDAD DE ENSAMBLADORES");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, -1, -1));

        rmEnsambladores.setEditable(false);
        rmEnsambladores.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmEnsambladores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmEnsambladoresActionPerformed(evt);
            }
        });
        jPanel1.add(rmEnsambladores, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 40, -1));

        jLabel31.setText("CANTIDAD DE CAPS TERMINADOS");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, -1, -1));

        rmCaps.setEditable(false);
        rmCaps.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmCaps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmCapsActionPerformed(evt);
            }
        });
        jPanel1.add(rmCaps, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 400, 40, -1));

        jLabel32.setText("PROJECT MANAGER");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 460, -1, -1));

        velmaPMFaltas.setEditable(false);
        velmaPMFaltas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaPMFaltas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaPMFaltasActionPerformed(evt);
            }
        });
        jPanel1.add(velmaPMFaltas, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 520, 40, -1));

        jLabel33.setText("CAPITULOS TERMINADOS (ULTIMO LOTE)");
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 610, -1, -1));

        jLabel34.setText("ACTIVIDAD");
        jPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 480, -1, -1));

        velmaCaps.setEditable(false);
        velmaCaps.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaCaps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaCapsActionPerformed(evt);
            }
        });
        jPanel1.add(velmaCaps, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 400, 40, -1));

        jLabel35.setText("SALARIO");
        jPanel1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 500, -1, -1));

        velmaPMActividad.setEditable(false);
        velmaPMActividad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaPMActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaPMActividadActionPerformed(evt);
            }
        });
        jPanel1.add(velmaPMActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 480, 40, -1));

        jLabel36.setText("FALTAS");
        jPanel1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 520, -1, -1));

        velmaPMSalario.setEditable(false);
        velmaPMSalario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaPMSalario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaPMSalarioActionPerformed(evt);
            }
        });
        jPanel1.add(velmaPMSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 500, 40, -1));

        jLabel37.setText("DIRECTOR");
        jPanel1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 550, -1, -1));

        velmaGastoMensual.setEditable(false);
        velmaGastoMensual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaGastoMensual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaGastoMensualActionPerformed(evt);
            }
        });
        jPanel1.add(velmaGastoMensual, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 590, 40, -1));

        jLabel38.setText("GASTO DE LA PLANTA (MENSUAL)");
        jPanel1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 590, -1, -1));

        velmoGananciasTotales.setEditable(false);
        velmoGananciasTotales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmoGananciasTotales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmoGananciasTotalesActionPerformed(evt);
            }
        });
        jPanel1.add(velmoGananciasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 630, 40, -1));

        jLabel39.setText("GANANCIAS TOTALES");
        jPanel1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 630, -1, -1));

        velmaDirectorActividad.setEditable(false);
        velmaDirectorActividad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaDirectorActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaDirectorActividadActionPerformed(evt);
            }
        });
        jPanel1.add(velmaDirectorActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 570, 40, -1));

        jLabel40.setText("ACTIVIDAD");
        jPanel1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 570, -1, -1));

        jLabel41.setText("DEADLINE (DIAS)");
        jPanel1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, -1, -1));

        velmaDeadline.setEditable(false);
        velmaDeadline.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaDeadline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaDeadlineActionPerformed(evt);
            }
        });
        jPanel1.add(velmaDeadline, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, 40, -1));

        pmCapsAcabadosEnUltimoLote.setEditable(false);
        pmCapsAcabadosEnUltimoLote.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pmCapsAcabadosEnUltimoLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmCapsAcabadosEnUltimoLoteActionPerformed(evt);
            }
        });
        jPanel1.add(pmCapsAcabadosEnUltimoLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 610, 40, -1));

        jLabel42.setText("PROJECT MANAGER");
        jPanel1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 460, -1, -1));

        rmPMFaltas.setEditable(false);
        rmPMFaltas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmPMFaltas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmPMFaltasActionPerformed(evt);
            }
        });
        jPanel1.add(rmPMFaltas, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 520, 40, -1));

        jLabel43.setText("CAPITULOS TERMINADOS (ULTIMO LOTE)");
        jPanel1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 610, -1, -1));

        jLabel44.setText("ACTIVIDAD");
        jPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 480, -1, -1));

        jLabel45.setText("SALARIO");
        jPanel1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, -1, -1));

        rmPMActividad.setEditable(false);
        rmPMActividad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmPMActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmPMActividadActionPerformed(evt);
            }
        });
        jPanel1.add(rmPMActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 480, 40, -1));

        jLabel46.setText("FALTAS");
        jPanel1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 520, -1, -1));

        rmPMSalario.setEditable(false);
        rmPMSalario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmPMSalario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmPMSalarioActionPerformed(evt);
            }
        });
        jPanel1.add(rmPMSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 500, 40, -1));

        jLabel47.setText("DIRECTOR");
        jPanel1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 550, -1, -1));

        rmGastoMensual.setEditable(false);
        rmGastoMensual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmGastoMensual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmGastoMensualActionPerformed(evt);
            }
        });
        jPanel1.add(rmGastoMensual, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 590, 40, -1));

        jLabel48.setText("GASTO DE LA PLANTA (MENSUAL)");
        jPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 590, -1, -1));

        rmGananciasTotales.setEditable(false);
        rmGananciasTotales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmGananciasTotales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmGananciasTotalesActionPerformed(evt);
            }
        });
        jPanel1.add(rmGananciasTotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 630, 40, -1));

        jLabel49.setText("GANANCIAS TOTALES");
        jPanel1.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 630, -1, -1));

        rmDirectorActividad.setEditable(false);
        rmDirectorActividad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmDirectorActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmDirectorActividadActionPerformed(evt);
            }
        });
        jPanel1.add(rmDirectorActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 570, 40, -1));

        jLabel50.setText("ACTIVIDAD");
        jPanel1.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 570, -1, -1));

        jLabel51.setText("DEADLINE (DIAS)");
        jPanel1.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 430, -1, -1));

        rmDeadline.setEditable(false);
        rmDeadline.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rmDeadline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmDeadlineActionPerformed(evt);
            }
        });
        jPanel1.add(rmDeadline, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, 40, -1));

        velmaEnsambladoresUp.setText("+");
        velmaEnsambladoresUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaEnsambladoresUpActionPerformed(evt);
            }
        });
        jPanel1.add(velmaEnsambladoresUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 370, -1, -1));

        velmaIntroUp.setText("+");
        velmaIntroUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaIntroUpActionPerformed(evt);
            }
        });
        jPanel1.add(velmaIntroUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, -1, -1));

        velmaCreditosUp.setText("+");
        velmaCreditosUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaCreditosUpActionPerformed(evt);
            }
        });
        jPanel1.add(velmaCreditosUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, -1, -1));

        velmaInicioUp.setText("+");
        velmaInicioUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInicioUpActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInicioUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, -1, -1));

        velmaCierreUp.setText("+");
        velmaCierreUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaCierreUpActionPerformed(evt);
            }
        });
        jPanel1.add(velmaCierreUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, -1, -1));

        velmaPlottwistUp.setText("+");
        velmaPlottwistUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaPlottwistUpActionPerformed(evt);
            }
        });
        jPanel1.add(velmaPlottwistUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, -1, -1));

        rmIntroUp.setText("+");
        jPanel1.add(rmIntroUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 120, -1, -1));

        rmCreditosUp.setText("+");
        jPanel1.add(rmCreditosUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 140, -1, -1));

        rmInicioUp.setText("+");
        jPanel1.add(rmInicioUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 160, -1, -1));

        rmCierreUp.setText("+");
        jPanel1.add(rmCierreUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 180, -1, -1));

        rmPlottwistUp.setText("+");
        jPanel1.add(rmPlottwistUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 200, -1, -1));

        rmEnsambladoresUp.setText("+");
        jPanel1.add(rmEnsambladoresUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, -1, -1));

        velmaPlottwistDown.setText("-");
        velmaPlottwistDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaPlottwistDownActionPerformed(evt);
            }
        });
        jPanel1.add(velmaPlottwistDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, -1, -1));

        velmaEnsambladoresDown.setText("-");
        velmaEnsambladoresDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaEnsambladoresDownActionPerformed(evt);
            }
        });
        jPanel1.add(velmaEnsambladoresDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, -1, -1));

        velmaCreditosDown.setText("-");
        velmaCreditosDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaCreditosDownActionPerformed(evt);
            }
        });
        jPanel1.add(velmaCreditosDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, -1, -1));

        velmaInicioDown.setText("-");
        velmaInicioDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInicioDownActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInicioDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, -1, -1));

        velmaCierreDown.setText("-");
        velmaCierreDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaCierreDownActionPerformed(evt);
            }
        });
        jPanel1.add(velmaCierreDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, -1, -1));

        velmaIntroDown.setText("-");
        velmaIntroDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaIntroDownActionPerformed(evt);
            }
        });
        jPanel1.add(velmaIntroDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, -1, -1));

        rmIntroDown.setText("-");
        jPanel1.add(rmIntroDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, -1, -1));

        rmCreditosDown.setText("-");
        jPanel1.add(rmCreditosDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, -1, -1));

        rmInicioDown.setText("-");
        jPanel1.add(rmInicioDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, -1, -1));

        rmCierreDown.setText("-");
        jPanel1.add(rmCierreDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, -1, -1));

        rmPlottwistDown.setText("-");
        jPanel1.add(rmPlottwistDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, -1, -1));

        rmEnsambladoresDown.setText("-");
        jPanel1.add(rmEnsambladoresDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 370, -1, -1));

        start.setText("Iniciar la simulacion");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });
        jPanel1.add(start, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, -1));

        config.setText("Cambiar valores");
        config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configActionPerformed(evt);
            }
        });
        jPanel1.add(config, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        velmaInventarioIntroMaximo.setEditable(false);
        velmaInventarioIntroMaximo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioIntroMaximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioIntroMaximoActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioIntroMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 260, 40, -1));

        velmaInventarioCreditosMaximo.setEditable(false);
        velmaInventarioCreditosMaximo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioCreditosMaximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioCreditosMaximoActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioCreditosMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 280, 40, -1));

        velmaInventarioInicioMaximo.setEditable(false);
        velmaInventarioInicioMaximo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioInicioMaximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioInicioMaximoActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioInicioMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, 40, -1));

        velmaInventarioCierreMaximo.setEditable(false);
        velmaInventarioCierreMaximo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioCierreMaximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioCierreMaximoActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioCierreMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 320, 40, -1));

        velmaInventarioPlottwistMaximo.setEditable(false);
        velmaInventarioPlottwistMaximo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        velmaInventarioPlottwistMaximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velmaInventarioPlottwistMaximoActionPerformed(evt);
            }
        });
        jPanel1.add(velmaInventarioPlottwistMaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340, 40, -1));

        jLabel52.setText("INVENTARIO");
        jPanel1.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, -1, -1));

        jLabel53.setText("MAXIMO");
        jPanel1.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void velmaPlottwistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaPlottwistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaPlottwistActionPerformed

    private void velmaIntroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaIntroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaIntroActionPerformed

    private void velmaCreditosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaCreditosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaCreditosActionPerformed

    private void velmaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInicioActionPerformed

    private void velmaCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaCierreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaCierreActionPerformed

    private void rmPlottwistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmPlottwistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmPlottwistActionPerformed

    private void rmIntroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmIntroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmIntroActionPerformed

    private void rmCreditosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmCreditosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmCreditosActionPerformed

    private void rmInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmInicioActionPerformed

    private void rmCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmCierreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmCierreActionPerformed

    private void velmaEnsambladoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaEnsambladoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaEnsambladoresActionPerformed

    private void velmaInventarioIntroDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioIntroDisponibleActionPerformed
    }//GEN-LAST:event_velmaInventarioIntroDisponibleActionPerformed

    private void velmaInventarioCreditosDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioCreditosDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioCreditosDisponibleActionPerformed

    private void velmaInventarioInicioDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioInicioDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioInicioDisponibleActionPerformed

    private void velmaInventarioCierreDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioCierreDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioCierreDisponibleActionPerformed

    private void rmInventarioPlottwistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmInventarioPlottwistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmInventarioPlottwistActionPerformed

    private void rmInventarioIntroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmInventarioIntroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmInventarioIntroActionPerformed

    private void rmInventarioCreditosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmInventarioCreditosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmInventarioCreditosActionPerformed

    private void rmInventarioInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmInventarioInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmInventarioInicioActionPerformed

    private void rmInventarioCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmInventarioCierreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmInventarioCierreActionPerformed

    private void velmaInventarioPlottwistDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioPlottwistDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioPlottwistDisponibleActionPerformed

    private void velmoCapsAcabadosEnUltimoLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmoCapsAcabadosEnUltimoLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmoCapsAcabadosEnUltimoLoteActionPerformed

    private void rmEnsambladoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmEnsambladoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmEnsambladoresActionPerformed

    private void rmCapsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmCapsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmCapsActionPerformed

    private void velmaPMFaltasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaPMFaltasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaPMFaltasActionPerformed

    private void velmaCapsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaCapsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaCapsActionPerformed

    private void velmaPMActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaPMActividadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaPMActividadActionPerformed

    private void velmaPMSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaPMSalarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaPMSalarioActionPerformed

    private void velmaGastoMensualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaGastoMensualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaGastoMensualActionPerformed

    private void velmoGananciasTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmoGananciasTotalesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmoGananciasTotalesActionPerformed

    private void velmaDirectorActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaDirectorActividadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaDirectorActividadActionPerformed

    private void velmaDeadlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaDeadlineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaDeadlineActionPerformed

    private void pmCapsAcabadosEnUltimoLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmCapsAcabadosEnUltimoLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pmCapsAcabadosEnUltimoLoteActionPerformed

    private void rmPMFaltasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmPMFaltasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmPMFaltasActionPerformed

    private void rmPMActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmPMActividadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmPMActividadActionPerformed

    private void rmPMSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmPMSalarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmPMSalarioActionPerformed

    private void rmGastoMensualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmGastoMensualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmGastoMensualActionPerformed

    private void rmGananciasTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmGananciasTotalesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmGananciasTotalesActionPerformed

    private void rmDirectorActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmDirectorActividadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmDirectorActividadActionPerformed

    private void rmDeadlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmDeadlineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rmDeadlineActionPerformed

    private void configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configActionPerformed

        String inputDias = JOptionPane.showInputDialog(null, "Tiempo (en segundos) que dura un dia: ");
        String inputDiasEntreLanzamiento = JOptionPane.showInputDialog(null, "Dias entre lanzamientos: ");
        String inputIntro = JOptionPane.showInputDialog(null, "Cantidad de productores en Intro: ");
        String inputCreditos = JOptionPane.showInputDialog(null, "Cantidad de productores en Creditos: ");
        String inputInicio = JOptionPane.showInputDialog(null, "Cantidad de productores en Inicio: ");
        String inputCierre = JOptionPane.showInputDialog(null, "Cantidad de productores en Cierre: ");
        String inputPlottwist = JOptionPane.showInputDialog(null, "Cantidad de productores en Plottwist: ");
        String inputEnsambladores = JOptionPane.showInputDialog(null, "Cantidad de ensambladores: ");

        int input1 = Integer.parseInt(inputIntro);
        int input2 = Integer.parseInt(inputCreditos);
        int input3 = Integer.parseInt(inputInicio);
        int input4 = Integer.parseInt(inputCierre);
        int input5 = Integer.parseInt(inputPlottwist);
        int input6 = Integer.parseInt(inputEnsambladores);

        if (input1 + input2 + input3 + input4 + input5 + input6 != 11) {
            JOptionPane.showMessageDialog(null, "Error, la cantidad de productores y ensambladores debe de sumar 11");
            this.dispose();
        }

        //Capacidades maximas en el Drive
        String inputIntroAlmacenamiento = JOptionPane.showInputDialog(null, "Almacenamiento maximo de Intro: ");
        String inputCreditosAlmacenamiento = JOptionPane.showInputDialog(null, "Almacenamiento maximo de Creditos: ");
        String inputInicioAlmacenamiento = JOptionPane.showInputDialog(null, "Almacenamiento maximo de Inicio: ");
        String inputCierreAlmacenamiento = JOptionPane.showInputDialog(null, "Almacenamiento maximo de Cierre: ");
        String inputPlottwistAlmacenamiento = JOptionPane.showInputDialog(null, "Almacenamiento maximo de PlotTwists: ");

        String escritura = inputDias + "//" + inputDiasEntreLanzamiento + "//" + inputIntroAlmacenamiento + "," + inputCreditosAlmacenamiento + "," + inputInicioAlmacenamiento + "," + inputCierreAlmacenamiento + "," + inputPlottwistAlmacenamiento + "//" + inputIntro + "," + inputCreditos + "," + inputInicio + "," + inputCierre + "," + inputPlottwist + "//" + inputEnsambladores;

        BufferedReader br;

        try {

            PrintStream out = new PrintStream(new FileOutputStream("config.txt"));
            out.print(escritura);

            //Lectura
            br = new BufferedReader(new FileReader("config.txt"));
            String data = br.readLine();
            String[] dataSplit = data.split("//");
            String[] productoresSplit = dataSplit[3].split(",");
            String[] almacenamientoSplit = dataSplit[2].split(",");

            //Dias
            duracionDiaEnSegundos = Integer.parseInt(dataSplit[0]);
            cantidadDeDiasEntreLanzamientos = Integer.parseInt(dataSplit[1]);

            //Productores
            velmaIntro.setText(productoresSplit[0]);
            velmaCreditos.setText(productoresSplit[1]);
            velmaInicio.setText(productoresSplit[2]);
            velmaCierre.setText(productoresSplit[3]);
            velmaPlottwist.setText(productoresSplit[4]);

            //Drive
            driveIntro = Integer.parseInt(almacenamientoSplit[0]);
            driveCreditos = Integer.parseInt(almacenamientoSplit[1]);
            driveInicio = Integer.parseInt(almacenamientoSplit[2]);
            driveCierre = Integer.parseInt(almacenamientoSplit[3]);
            drivePlottwist = Integer.parseInt(almacenamientoSplit[4]);

            bIntro = new String[driveIntro];
            bCreditos = new String[driveCreditos];
            bInicio = new String[driveInicio];
            bCierre = new String[driveCierre];
            bPlottwist = new String[drivePlottwist];

            eIntro = new Semaphore(driveIntro);
            eCreditos = new Semaphore(driveCreditos);
            eInicio = new Semaphore(driveInicio);
            eCierre = new Semaphore(driveCierre);
            ePlottwist = new Semaphore(drivePlottwist);

            //Ensamblador
            velmaEnsambladores.setText(dataSplit[4]);
            tEnsamblador.setNumeroDeProductores(Integer.parseInt(velmaEnsambladores.getText()));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_configActionPerformed

    private void velmaIntroUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaIntroUpActionPerformed
        if (Integer.parseInt(velmaIntro.getText()) + Integer.parseInt(velmaCreditos.getText()) + Integer.parseInt(velmaInicio.getText()) + Integer.parseInt(velmaCierre.getText()) + Integer.parseInt(velmaPlottwist.getText()) + Integer.parseInt(velmaEnsambladores.getText()) == 11) {
            JOptionPane.showMessageDialog(null, "La cantidad maxima de productores no puede ser mas de 11");

        } else {

            int holderInt = Integer.parseInt(velmaIntro.getText());
            holderInt++;
            velmaIntro.setText(Integer.toString(holderInt));
            tIntro.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaIntroUpActionPerformed

    private void velmaCreditosUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaCreditosUpActionPerformed
        if (Integer.parseInt(velmaIntro.getText()) + Integer.parseInt(velmaCreditos.getText()) + Integer.parseInt(velmaInicio.getText()) + Integer.parseInt(velmaCierre.getText()) + Integer.parseInt(velmaPlottwist.getText()) + Integer.parseInt(velmaEnsambladores.getText()) == 11) {
            JOptionPane.showMessageDialog(null, "La cantidad maxima de productores no puede ser mas de 11");

        } else {
            int holderInt = Integer.parseInt(velmaCreditos.getText());
            holderInt++;
            velmaCreditos.setText(Integer.toString(holderInt));
            tCreditos.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaCreditosUpActionPerformed

    private void velmaInicioUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInicioUpActionPerformed
        if (Integer.parseInt(velmaIntro.getText()) + Integer.parseInt(velmaCreditos.getText()) + Integer.parseInt(velmaInicio.getText()) + Integer.parseInt(velmaCierre.getText()) + Integer.parseInt(velmaPlottwist.getText()) + Integer.parseInt(velmaEnsambladores.getText()) == 11) {
            JOptionPane.showMessageDialog(null, "La cantidad maxima de productores no puede ser mas de 11");

        } else {
            int holderInt = Integer.parseInt(velmaInicio.getText());
            holderInt++;
            velmaInicio.setText(Integer.toString(holderInt));
            tInicio.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaInicioUpActionPerformed

    private void velmaCierreUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaCierreUpActionPerformed
        if (Integer.parseInt(velmaIntro.getText()) + Integer.parseInt(velmaCreditos.getText()) + Integer.parseInt(velmaInicio.getText()) + Integer.parseInt(velmaCierre.getText()) + Integer.parseInt(velmaPlottwist.getText()) + Integer.parseInt(velmaEnsambladores.getText()) == 11) {
            JOptionPane.showMessageDialog(null, "La cantidad maxima de productores no puede ser mas de 11");

        } else {
            int holderInt = Integer.parseInt(velmaCierre.getText());
            holderInt++;
            velmaCierre.setText(Integer.toString(holderInt));
            tCierre.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaCierreUpActionPerformed

    private void velmaPlottwistUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaPlottwistUpActionPerformed
        if (Integer.parseInt(velmaIntro.getText()) + Integer.parseInt(velmaCreditos.getText()) + Integer.parseInt(velmaInicio.getText()) + Integer.parseInt(velmaCierre.getText()) + Integer.parseInt(velmaPlottwist.getText()) + Integer.parseInt(velmaEnsambladores.getText()) == 11) {
            JOptionPane.showMessageDialog(null, "La cantidad maxima de productores no puede ser mas de 11");

        } else {
            int holderInt = Integer.parseInt(velmaPlottwist.getText());
            holderInt++;
            velmaPlottwist.setText(Integer.toString(holderInt));
            tPlottwist.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaPlottwistUpActionPerformed

    private void velmaIntroDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaIntroDownActionPerformed
        int holderInt = Integer.parseInt(velmaIntro.getText());
        if (holderInt == 1) {
            JOptionPane.showMessageDialog(null, "Los productores no pueden bajar de 1 dado que despiden al Project Manager");
        } else {
            holderInt--;
            velmaIntro.setText(Integer.toString(holderInt));
            tIntro.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaIntroDownActionPerformed

    private void velmaCreditosDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaCreditosDownActionPerformed
        int holderInt = Integer.parseInt(velmaCreditos.getText());
        if (holderInt == 1) {
            JOptionPane.showMessageDialog(null, "Los productores no pueden bajar de 1 dado que despiden al Project Manager");
        } else {
            holderInt--;
            velmaCreditos.setText(Integer.toString(holderInt));
            tCreditos.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaCreditosDownActionPerformed

    private void velmaInicioDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInicioDownActionPerformed
        int holderInt = Integer.parseInt(velmaInicio.getText());
        if (holderInt == 1) {
            JOptionPane.showMessageDialog(null, "Los productores no pueden bajar de 1 dado que despiden al Project Manager");
        } else {
            holderInt--;
            velmaInicio.setText(Integer.toString(holderInt));
            tInicio.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaInicioDownActionPerformed

    private void velmaCierreDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaCierreDownActionPerformed
        int holderInt = Integer.parseInt(velmaCierre.getText());
        if (holderInt == 1) {
            JOptionPane.showMessageDialog(null, "Los productores no pueden bajar de 1 dado que despiden al Project Manager");
        } else {
            holderInt--;
            velmaCierre.setText(Integer.toString(holderInt));
            tCierre.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaCierreDownActionPerformed

    private void velmaPlottwistDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaPlottwistDownActionPerformed
        int holderInt = Integer.parseInt(velmaPlottwist.getText());
        if (holderInt == 1) {
            JOptionPane.showMessageDialog(null, "Los productores no pueden bajar de 1 dado que despiden al Project Manager");
        } else {
            holderInt--;
            velmaPlottwist.setText(Integer.toString(holderInt));
            tPlottwist.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaPlottwistDownActionPerformed

    private void velmaEnsambladoresDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaEnsambladoresDownActionPerformed
        int holderInt = Integer.parseInt(velmaEnsambladores.getText());
        if (holderInt == 1) {
            JOptionPane.showMessageDialog(null, "Los productores no pueden bajar de 1 dado que despiden al Project Manager");
        } else {
            holderInt--;
            velmaEnsambladores.setText(Integer.toString(holderInt));
            tEnsamblador.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaEnsambladoresDownActionPerformed

    private void velmaEnsambladoresUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaEnsambladoresUpActionPerformed
        if (Integer.parseInt(velmaIntro.getText()) + Integer.parseInt(velmaCreditos.getText()) + Integer.parseInt(velmaInicio.getText()) + Integer.parseInt(velmaCierre.getText()) + Integer.parseInt(velmaPlottwist.getText()) + Integer.parseInt(velmaEnsambladores.getText()) == 11) {
            JOptionPane.showMessageDialog(null, "La cantidad maxima de productores no puede ser mas de 11");

        } else {
            int holderInt = Integer.parseInt(velmaEnsambladores.getText());
            holderInt++;
            velmaEnsambladores.setText(Integer.toString(holderInt));
            tEnsamblador.setNumeroDeProductores(holderInt);
        }
    }//GEN-LAST:event_velmaEnsambladoresUpActionPerformed

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed

        tIntro.setTextField(velmaInventarioIntroDisponible);
        tCreditos.setTextField(velmaInventarioCreditosDisponible);
        tInicio.setTextField(velmaInventarioInicioDisponible);
        tCierre.setTextField(velmaInventarioCierreDisponible);
        tPlottwist.setTextField(velmaInventarioPlottwistDisponible);

        tIntro.start();
        tCreditos.start();
        tInicio.start();
        tCierre.start();
        tPlottwist.start();
        tEnsamblador.start();


    }//GEN-LAST:event_startActionPerformed

    private void velmaInventarioIntroMaximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioIntroMaximoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioIntroMaximoActionPerformed

    private void velmaInventarioCreditosMaximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioCreditosMaximoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioCreditosMaximoActionPerformed

    private void velmaInventarioInicioMaximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioInicioMaximoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioInicioMaximoActionPerformed

    private void velmaInventarioCierreMaximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioCierreMaximoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioCierreMaximoActionPerformed

    private void velmaInventarioPlottwistMaximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velmaInventarioPlottwistMaximoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_velmaInventarioPlottwistMaximoActionPerformed

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
            java.util.logging.Logger.getLogger(Interface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton config;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField pmCapsAcabadosEnUltimoLote;
    private javax.swing.JTextField rmCaps;
    private javax.swing.JTextField rmCierre;
    private javax.swing.JButton rmCierreDown;
    private javax.swing.JButton rmCierreUp;
    private javax.swing.JTextField rmCreditos;
    private javax.swing.JButton rmCreditosDown;
    private javax.swing.JButton rmCreditosUp;
    private javax.swing.JTextField rmDeadline;
    private javax.swing.JTextField rmDirectorActividad;
    private javax.swing.JTextField rmEnsambladores;
    private javax.swing.JButton rmEnsambladoresDown;
    private javax.swing.JButton rmEnsambladoresUp;
    private javax.swing.JTextField rmGananciasTotales;
    private javax.swing.JTextField rmGastoMensual;
    private javax.swing.JTextField rmInicio;
    private javax.swing.JButton rmInicioDown;
    private javax.swing.JButton rmInicioUp;
    private javax.swing.JTextField rmIntro;
    private javax.swing.JButton rmIntroDown;
    private javax.swing.JButton rmIntroUp;
    private javax.swing.JTextField rmInventarioCierre;
    private javax.swing.JTextField rmInventarioCreditos;
    private javax.swing.JTextField rmInventarioInicio;
    private javax.swing.JTextField rmInventarioIntro;
    private javax.swing.JTextField rmInventarioPlottwist;
    private javax.swing.JTextField rmPMActividad;
    private javax.swing.JTextField rmPMFaltas;
    private javax.swing.JTextField rmPMSalario;
    private javax.swing.JTextField rmPlottwist;
    private javax.swing.JButton rmPlottwistDown;
    private javax.swing.JButton rmPlottwistUp;
    private javax.swing.JLabel rmProductoresLabel;
    private javax.swing.JToggleButton start;
    private javax.swing.JTextField velmaCaps;
    private javax.swing.JTextField velmaCierre;
    private javax.swing.JButton velmaCierreDown;
    private javax.swing.JButton velmaCierreUp;
    private javax.swing.JTextField velmaCreditos;
    private javax.swing.JButton velmaCreditosDown;
    private javax.swing.JButton velmaCreditosUp;
    private javax.swing.JTextField velmaDeadline;
    private javax.swing.JTextField velmaDirectorActividad;
    private javax.swing.JTextField velmaEnsambladores;
    private javax.swing.JButton velmaEnsambladoresDown;
    private javax.swing.JButton velmaEnsambladoresUp;
    private javax.swing.JTextField velmaGastoMensual;
    private javax.swing.JTextField velmaInicio;
    private javax.swing.JButton velmaInicioDown;
    private javax.swing.JButton velmaInicioUp;
    private javax.swing.JTextField velmaIntro;
    private javax.swing.JButton velmaIntroDown;
    private javax.swing.JButton velmaIntroUp;
    private javax.swing.JTextField velmaInventarioCierreDisponible;
    private javax.swing.JTextField velmaInventarioCierreMaximo;
    private javax.swing.JTextField velmaInventarioCreditosDisponible;
    private javax.swing.JTextField velmaInventarioCreditosMaximo;
    private javax.swing.JTextField velmaInventarioInicioDisponible;
    private javax.swing.JTextField velmaInventarioInicioMaximo;
    public static javax.swing.JTextField velmaInventarioIntroDisponible;
    private javax.swing.JTextField velmaInventarioIntroMaximo;
    private javax.swing.JTextField velmaInventarioPlottwistDisponible;
    private javax.swing.JTextField velmaInventarioPlottwistMaximo;
    private javax.swing.JTextField velmaPMActividad;
    private javax.swing.JTextField velmaPMFaltas;
    private javax.swing.JTextField velmaPMSalario;
    private javax.swing.JTextField velmaPlottwist;
    private javax.swing.JButton velmaPlottwistDown;
    private javax.swing.JButton velmaPlottwistUp;
    private javax.swing.JLabel velmaProductoresLabel;
    private javax.swing.JTextField velmoCapsAcabadosEnUltimoLote;
    private javax.swing.JTextField velmoGananciasTotales;
    // End of variables declaration//GEN-END:variables
}
