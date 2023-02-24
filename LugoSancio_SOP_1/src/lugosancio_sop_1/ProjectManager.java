/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;
import java.util.logging.Level;
import static lugosancio_sop_1.Interface.sCountdown;

/**
 *
 * @author matteosancio
 */
public class ProjectManager extends Thread {
    
    boolean estaViendoRM = false;
    int diasRestantes;
    int numDeCedula;
    boolean seAcaboElDia;
    int duracionDiaEnSegundos;
    
    public static Semaphore sem = new Semaphore(0);
    
    public class Hilo extends Thread {
        
        @Override
        public void run() {
            try {
                while (true) {
                    sem.acquire();
                    Thread.sleep(duracionDiaEnSegundos*1000);
                    seAcaboElDia = true;
                    System.out.println("LISTO UN DIA SEGUN PM");
                }
            } catch (InterruptedException ex) {
            Logger.getLogger(ProductorIntro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ProjectManager(int diasRestantes, int numDeCedula, int duracionDiaEnSegundos) {
        this.diasRestantes = diasRestantes;
        this.numDeCedula = numDeCedula;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }
    
    @Override
    public void run() {
        try {
            Hilo tHilo = new Hilo();
            tHilo.start();
            while (true) {
                sem.release();
                estaViendoRM = false;
                seAcaboElDia = false;
                sCountdown.acquire();
                diasRestantes--;
                sCountdown.release();
                while (!seAcaboElDia) {
                    estaViendoRM = !estaViendoRM;
                    Thread.sleep((15+numDeCedula)*1000/1440);
                    }
                }
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
