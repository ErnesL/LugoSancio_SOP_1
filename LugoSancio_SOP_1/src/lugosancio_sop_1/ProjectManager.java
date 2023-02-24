/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

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
    
    public ProjectManager(int diasRestantes, int numDeCedula) {
        this.diasRestantes = diasRestantes;
        this.numDeCedula = numDeCedula;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                estaViendoRM = false;
                sCountdown.acquire();
                LugoSancio_SOP_1.reduce();
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
