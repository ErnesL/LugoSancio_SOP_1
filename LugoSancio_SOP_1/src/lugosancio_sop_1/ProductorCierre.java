/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import static lugosancio_sop_1.Interface.driveCierre;
import static lugosancio_sop_1.Interface.eCierre;
import static lugosancio_sop_1.Interface.nCierre;
import static lugosancio_sop_1.Interface.sCierre;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import static lugosancio_sop_1.Interface.cantidadDeDiasEntreLanzamientos;
import lugosancio_sop_1.LugoSancio_SOP_1;

/**
 *
 * @author ernes
 */
public class ProductorCierre extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 8;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    String nombre;
    JTextField textField;

    public ProductorCierre(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;

    }

    String cierreGenerico = "y a la final todos se murieron y nunca se encontró la gema del poder, FIN.\n";

    @Override
    public void run() {
        try {
            while (cantidadDeDiasEntreLanzamientos > 0) {
                //se está creando la cierre
                sleep(duracionDiaEnSegundos * 4000 / numeroDeProductores);
                //se revisa si hay espacio en el buffer
                eCierre.acquire();
                //tiene que estar solito en el buffer
                sCierre.acquire();
                //SECCION CRITICA
                Interface.inCierre = LugoSancio_SOP_1.append(cierreGenerico, Interface.bCierre, driveCierre, Interface.inCierre);
                //ya salió de la sección crítica
                sCierre.release();
                //hay un item consumible más en N
                nCierre.release();
                textField.setText(Integer.toString(nCierre.availablePermits()));
                montoPorPagar = montoPorPagar + sueldo*24;
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorCierre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public void setNumeroDeProductores(int numeroDeProductores) {
        this.numeroDeProductores = numeroDeProductores;
    }

    public double getSueldo() {
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
