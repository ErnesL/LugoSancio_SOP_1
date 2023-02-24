/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import static lugosancio_sop_1.Interface.ePlottwist;
import static lugosancio_sop_1.Interface.nPlottwist;
import static lugosancio_sop_1.Interface.sPlottwist;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import lugosancio_sop_1.LugoSancio_SOP_1;

/**
 *
 * @author ernes
 */
public class ProductorPlottwist extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 7;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    String nombre;
    JTextField textField;

    public ProductorPlottwist(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;

        this.nombre = nombre;

        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    String plottwistGenerico = "pero resulta que.... el malo todo este tiempo ha sido Miguel Mouse!\n";

    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la plottwist
                sleep(duracionDiaEnSegundos * 1000 / numeroDeProductores);
                //se revisa si hay espacio en el buffer
                ePlottwist.acquire();
                //tiene que estar solito en el buffer
                sPlottwist.acquire();
                //SECCION CRITICA
                Interface.inPlottwist = LugoSancio_SOP_1.append(plottwistGenerico, Interface.bPlottwist, Interface.drivePlottwist, Interface.inPlottwist);
                //ya salió de la sección crítica
                sPlottwist.release();
                //hay un item consumible más en N
                nPlottwist.release();
                System.out.println("hay esta cantidad de plottwists: " + nPlottwist.availablePermits());
                textField.setText(Integer.toString(nPlottwist.availablePermits()));
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorPlottwist.class.getName()).log(Level.SEVERE, null, ex);
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
