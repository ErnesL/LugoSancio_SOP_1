/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import static lugosancio_sop_1.Interface.eInicio;
import static lugosancio_sop_1.Interface.nInicio;
import static lugosancio_sop_1.Interface.sInicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import static lugosancio_sop_1.Interface.eInicioRM;
import static lugosancio_sop_1.Interface.nInicioRM;
import static lugosancio_sop_1.Interface.sInicioRM;
import lugosancio_sop_1.LugoSancio_SOP_1;

/**
 *
 * @author ernes
 */
public class ProductorInicioRM extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 7;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    String nombre;
    JTextField textField;

    public ProductorInicioRM(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;

    }

    String inicioGenerico = "Miguel Mouse se levanta entusiasmado para ir a la escuela.\n";

    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la inicio
                sleep(duracionDiaEnSegundos * 3000 / numeroDeProductores);
                //se revisa si hay espacio en el buffer
                eInicioRM.acquire();
                //tiene que estar solito en el buffer
                sInicioRM.acquire();
                //SECCION CRITICA
                Interface.inInicioRM = LugoSancio_SOP_1.append(inicioGenerico, Interface.bInicioRM, Interface.driveInicio, Interface.inInicioRM);
                //ya salió de la sección crítica
                sInicioRM.release();
                //hay un item consumible más en N
                nInicioRM.release();
                textField.setText(Integer.toString(nInicioRM.availablePermits()));
                montoPorPagar = montoPorPagar + sueldo * 24;

            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicioRM.class.getName()).log(Level.SEVERE, null, ex);
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
