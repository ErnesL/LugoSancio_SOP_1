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

/**
 *
 * @author matteosancio
 */
public class Director extends Thread {
    
    int diasRestantes;
    boolean seAcaboElDia;
    int duracionDiaEnSegundos;
    boolean isSupervisingOver;
    
    Semaphore sem = new Semaphore(1);
    Semaphore sem2 = new Semaphore(0);
    Semaphore isDayOver = new Semaphore(0);
    
    public class HiloD extends Thread {
        
        @Override
        public void run() {
            try {
                while (true) {
                    sem.acquire();
                    Thread.sleep(duracionDiaEnSegundos*1000);
                    seAcaboElDia = true;
                    isDayOver.release();
                    System.out.println("LISTO UN DIA SEGUN D");
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
                    System.out.println("empiexa supervision");
                    Thread.sleep(x);
                    isSupervisingOver = true;
                    System.out.println("LISTO SUPERVISION SEGUN D");
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
            while (true) {
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
                System.out.println("uno x dia DIRECTOR");
                sem.release();
                }
            } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
