/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import Interface.Interface;
import java.util.concurrent.Semaphore;
import lugosancio_sop_1.ProductorIntro;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author ernes
 */
public class LugoSancio_SOP_1 {
    
    //drive
    public static int kIntro = 30;
    public static int kCreditos = 25;
    public static int kInicio = 50;
    public static int kCierre = 55;
    public static int kPlottwist = 40;
    
    //buffers
    public static String[] bIntro = new String[kIntro];
    public static String[] bCreditos = new String[kCreditos];
    public static String[] bInicio = new String[kInicio];
    public static String[] bCierre = new String[kCierre];
    public static String[] bPlottwist = new String[kPlottwist];
    
    //in/out
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
    public static Semaphore eIntro = new Semaphore(kIntro);
    public static Semaphore sCreditos = new Semaphore(1);
    public static Semaphore nCreditos = new Semaphore(0);
    public static Semaphore eCreditos = new Semaphore(kCreditos);
    public static Semaphore sInicio = new Semaphore(1);
    public static Semaphore nInicio = new Semaphore(0);
    public static Semaphore eInicio = new Semaphore(kInicio);
    public static Semaphore sCierre = new Semaphore(1);
    public static Semaphore nCierre = new Semaphore(0);
    public static Semaphore eCierre = new Semaphore(kCierre);
    public static Semaphore sPlottwist = new Semaphore(1);
    public static Semaphore nPlottwist = new Semaphore(0);
    public static Semaphore ePlottwist = new Semaphore(kPlottwist);
    
    public static void append(String v, String[] b, int k, int in) {
        b[in] = v;
        in = (in+1) % k;
    }
    
    public static String take(String[] b, int k, int out) {
        String w = b[out];
        out = (out+1) % k;
        return w;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(6);
        

        ProductorIntro tIntro = new ProductorIntro(sem, 1, "Intro", 1);
        ProductorCreditos tCreditos = new ProductorCreditos(sem, 1, "Creditos", 1);
        ProductorInicio tInicio = new ProductorInicio(sem, 1, "Inicio", 1);
        ProductorCierre tCierre = new ProductorCierre(sem, 1, "Cierre", 1);
        ProductorPlottwist tPlottwist = new ProductorPlottwist(sem, 1, "Plot Twist", 1);
        Ensamblador tEnsamblador = new Ensamblador(sem,1,"Ensamblador",1);

//        try {
            tIntro.start();
            tCreditos.start();
            tInicio.start();
            tCierre.start();
            tPlottwist.start();
            tEnsamblador.start();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(LugoSancio_SOP_1.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //tIntro.start();
        //Thread.sleep(1000);
//        tCreditos.start();
        //Thread.sleep(1000);   
//        tInicio.start();
        //Thread.sleep(1000);
//        tCierre.start();
        //Thread.sleep(1000);
//        tPlottwist.start();
        //Thread.sleep(1000);
//        tEnsamblador.start();
        


//        Interface interfaz = new Interface();
//        interfaz.setLocationRelativeTo(null);
//        interfaz.setVisible(true);
//        interfaz.Leer();
    }

}
