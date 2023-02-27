/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import static lugosancio_sop_1.Interface.ePlottwist;
import static lugosancio_sop_1.Interface.nPlottwist;
import static lugosancio_sop_1.Interface.sPlottwist;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import static lugosancio_sop_1.Interface.cantidadDeDiasEntreLanzamientos;
import static lugosancio_sop_1.Interface.ePlottwistRM;
import static lugosancio_sop_1.Interface.nPlottwistRM;
import static lugosancio_sop_1.Interface.sPlottwistRM;
import lugosancio_sop_1.LugoSancio_SOP_1;

/**
 *
 * @author ernes
 */
public class ProductorPlottwistRM extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 7;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    String nombre;
    JTextField textField;

    public ProductorPlottwistRM(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    String plottwistGenerico = "pero resulta que.... el malo todo este tiempo ha sido Miguel Mouse!\n";

    @Override
    public void run() {
        try {
            while (cantidadDeDiasEntreLanzamientos > 0) {
                //se está creando la plottwist
                sleep(duracionDiaEnSegundos * 2000 / numeroDeProductores);
                //se revisa si hay espacio en el buffer
                ePlottwistRM.acquire();
                //tiene que estar solito en el buffer
                sPlottwistRM.acquire();
                //SECCION CRITICA
                Interface.inPlottwistRM = LugoSancio_SOP_1.append(plottwistGenerico, Interface.bPlottwistRM, Interface.drivePlottwistRM, Interface.inPlottwistRM);
                //ya salió de la sección crítica
                sPlottwistRM.release();
                //hay un item consumible más en N
                nPlottwistRM.release();
                textField.setText(Integer.toString(nPlottwistRM.availablePermits()));
                montoPorPagar = montoPorPagar + sueldo * 24;
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorPlottwistRM.class.getName()).log(Level.SEVERE, null, ex);
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
