/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import java.util.concurrent.Semaphore;
import lugosancio_sop_1.ProductorIntro;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author ernes
 */
public class LugoSancio_SOP_1 {  

    public static void append(String v, String[] b, int k, int in) {
        b[in] = v;
        in = (in + 1) % k;
    }

    public static String take(String[] b, int k, int out) {
        String w = b[out];
        out = (out + 1) % k;
        return w;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        
        Interface interfaz = new Interface();
        interfaz.setVisible(true);
        interfaz.Leer();

    }

}
