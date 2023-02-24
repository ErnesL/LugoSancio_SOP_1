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
import static lugosancio_sop_1.Interface.eCierreRM;
import static lugosancio_sop_1.Interface.nCierreRM;
import static lugosancio_sop_1.Interface.sCierreRM;
import lugosancio_sop_1.LugoSancio_SOP_1;

/**
 *
 * @author ernes
 */
public class ProductorCierreRM extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 8;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 2;
    String nombre;
    JTextField textField;

    public ProductorCierreRM(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;

    }

    String cierreGenerico = "y a la final todos se murieron y nunca se encontró la gema del poder, FIN.\n";

    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la cierre
                sleep(duracionDiaEnSegundos * 2000 / numeroDeProductores);
                //se revisa si hay espacio en el buffer
                eCierreRM.acquire();
                //tiene que estar solito en el buffer
                sCierreRM.acquire();
                //SECCION CRITICA
                Interface.inCierreRM = LugoSancio_SOP_1.append(cierreGenerico, Interface.bCierreRM, driveCierre, Interface.inCierreRM);
                //ya salió de la sección crítica
                sCierreRM.release();
                //hay un item consumible más en N
                nCierreRM.release();
                textField.setText(Integer.toString(nCierreRM.availablePermits()));
                montoPorPagar = montoPorPagar + sueldo*24;
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorCierreRM.class.getName()).log(Level.SEVERE, null, ex);
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
