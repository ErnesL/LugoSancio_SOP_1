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
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JTextField;

/**
 *
 * @author matteosancio
 */
public class Director extends Thread {
    
    int diasRestantes;
    boolean seAcaboElDia;
    int duracionDiaEnSegundos;
    boolean isSupervisingOver;
    int montoPorPagar = 0;
    int sueldo = 100;
    Semaphore sem = new Semaphore(1);
    Semaphore sem2 = new Semaphore(0);
    Semaphore isDayOver = new Semaphore(0);
    
    JTextField sueldoDirector;
    JTextField faltasPM;
    JTextField actividadDR;
    
    public class HiloD extends Thread {
        
        @Override
        public void run() {
            try {
                while (true) {
                    sem.acquire();
                    Thread.sleep(duracionDiaEnSegundos*1000);
                    seAcaboElDia = true;
                    isDayOver.release();
                   
                }
            } catch (InterruptedException ex) {
            Logger.getLogger(ProductorIntro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public class Hilo2 extends Thread {
        
        @Override
        public void run() {
            try {
                int x;
                while(true) {
                    x = ThreadLocalRandom.current().nextInt(12, 19)*1000/24;
                    sem2.acquire();
                   
                    actividadDR.setText("Supervisando al PM");
                    Thread.sleep(x);
                    isSupervisingOver = true;
                    
                    actividadDR.setText("Idle");
                    Thread.sleep((duracionDiaEnSegundos*1000)-x);
                }
            } catch (InterruptedException ex) {
            Logger.getLogger(ProductorIntro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Director(int diasRestantes, int duracionDiaEnSegundos) {
        this.diasRestantes = diasRestantes;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }
    
    public int randomSupervisingPeriod() {
        int n = ThreadLocalRandom.current().nextInt(12, 19);
        return n;
    }
    
    @Override
    public void run() {
        try {
            HiloD tHilo = new HiloD();
            Hilo2 tHilo2 = new Hilo2();
            tHilo.start();
            tHilo2.start();
            while (diasRestantes > 0) {
                seAcaboElDia = false;
                sCountdown.acquire();
                // TODO: leer valor para mostrar en interfaz
                sCountdown.release();
                isSupervisingOver = false;
                sem2.release();
                while (!isSupervisingOver) {
                    int randomNumber = ThreadLocalRandom.current().nextInt(30, 91);
                    Thread.sleep(randomNumber*1000/1440);
                    //TODO: LugoSancio_SOP_1.checkOnPM();
                    }
                isDayOver.acquire();
                
                sem.release();
                
                montoPorPagar = montoPorPagar + sueldo;
                sueldoDirector.setText(Integer.toString(montoPorPagar));
                
                }
            
            } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getMontoPorPagar() {
        return montoPorPagar;
    }

    public void setMontoPorPagar(int montoPorPagar) {
        this.montoPorPagar = montoPorPagar;
    }

    public JTextField getFaltasPM() {
        return faltasPM;
    }

    public void setFaltasPM(JTextField faltasPM) {
        this.faltasPM = faltasPM;
    }

    public JTextField getSueldoDirector() {
        return sueldoDirector;
    }

    public void setSueldoDirector(JTextField sueldoDirector) {
        this.sueldoDirector = sueldoDirector;
    }
    
    

    public void setActividadDR(JTextField actividadDR) {
        this.actividadDR = actividadDR;
    }
    
    
    
}
