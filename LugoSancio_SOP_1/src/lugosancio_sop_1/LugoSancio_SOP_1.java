/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import Interface.Interface;
import java.util.concurrent.Semaphore;

/**
 *
 * @author ernes
 */
public class LugoSancio_SOP_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
//        Semaphore sem = new Semaphore(1);
//        ProductorIntro pIntro = new ProductorIntro(sem, 1, "Intro");

//        pIntro.start();
        Interface interfaz = new Interface();
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
        interfaz.Leer();

    }

}
