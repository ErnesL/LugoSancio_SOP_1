/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

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
    
    public Director(int diasRestantes, boolean seAcaboElDia) {
        this.diasRestantes = diasRestantes;
        this.seAcaboElDia = seAcaboElDia;
    }
    
    public int randomSupervisingPeriod() {
        int n = ThreadLocalRandom.current().nextInt(12, 19);
        return n;
    }
    
    @Override
    public void run() {
        try {
            sCountdown.acquire();
            // TODO: leer valor para mostrar en interfaz
            sCountdown.release();
            boolean isSupervisingOver = false;
            while (diasRestantes!=0 && !isSupervisingOver) {
                int randomNumber = ThreadLocalRandom.current().nextInt(30, 91);
                Thread.sleep(randomNumber*1000/1440);
                LugoSancio_SOP_1.checkOnPM();
                }
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
