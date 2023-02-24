/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import static lugosancio_sop_1.Interface.eIntro;
import static lugosancio_sop_1.Interface.nIntro;
import static lugosancio_sop_1.Interface.sIntro;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import static lugosancio_sop_1.Interface.eIntroRM;
import static lugosancio_sop_1.Interface.nIntroRM;
import static lugosancio_sop_1.Interface.sIntroRM;
import lugosancio_sop_1.LugoSancio_SOP_1;

/**
 *
 * @author ernes
 */
public class ProductorIntroRM extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 5;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    JTextField textField;

    String nombre;

    public ProductorIntroRM(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    String introGenerica = "\noshiete oshiete watashi wa arigato gosaimasu\nATTACK ON TITAN TEMPORADA 4.5 PARTE 2 LADO A v1 (copy)\nDirigida por Satteo Mancio y Lugesto Erno\n";

    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la intro
                sleep(duracionDiaEnSegundos * 1000 / numeroDeProductores*2);
                //se revisa si hay espacio en el buffer
                eIntroRM.acquire();
                //tiene que estar solito en el buffer
                sIntroRM.acquire();
                //SECCION CRITICA
                Interface.inIntroRM = LugoSancio_SOP_1.append(introGenerica, Interface.bIntroRM, Interface.driveIntro, Interface.inIntroRM);
                //ya salió de la sección crítica
                sIntroRM.release();
                //hay un item consumible más en N
                nIntroRM.release();
                textField.setText(Integer.toString(nIntroRM.availablePermits()));
                montoPorPagar = montoPorPagar + sueldo * 24;

            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorIntroRM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public void setNumeroDeProductores(int numeroDeProductores) {
        this.numeroDeProductores = numeroDeProductores;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMontoPorPagar() {
        return montoPorPagar;
    }

    public void setMontoPorPagar(int montoPorPagar) {
        this.montoPorPagar = montoPorPagar;
    }

}