/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import Interface.Interface;
import java.util.concurrent.Semaphore;
import lugosancio_sop_1.ProductorIntro;

/**
 *
 * @author ernes
 */
public class LugoSancio_SOP_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Semaphore sem = new Semaphore(6);

        ProductorIntro tIntro = new ProductorIntro(sem, 1, "Intro", 1);
        ProductorCreditos tCreditos = new ProductorCreditos(sem, 1, "Creditos", 1);
        ProductorInicio tInicio = new ProductorInicio(sem, 1, "Inicio", 1);
        ProductorCierre tCierre = new ProductorCierre(sem, 1, "Cierre", 1);
        ProductorPlottwist tPlottwist = new ProductorPlottwist(sem, 1, "Plottwist", 1);
        Ensamblador tEnsamblador = new Ensamblador(sem,1,"Ensamblador",1);

        tIntro.start();
        tCreditos.start();
        tInicio.start();
        tCierre.start();
        tPlottwist.start();
        tEnsamblador.start();
        
        


//        Interface interfaz = new Interface();
//        interfaz.setLocationRelativeTo(null);
//        interfaz.setVisible(true);
//        interfaz.Leer();
    }

}
