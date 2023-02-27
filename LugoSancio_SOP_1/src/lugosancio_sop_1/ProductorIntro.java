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
import static lugosancio_sop_1.Interface.cantidadDeDiasEntreLanzamientos;
import lugosancio_sop_1.LugoSancio_SOP_1;

/**
 *
 * @author ernes
 */
public class ProductorIntro extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 5;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    JTextField textField;

    String nombre;

    public ProductorIntro(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    String introGenerica = "\noshiete oshiete watashi wa arigato gosaimasu\nATTACK ON TITAN TEMPORADA 4.5 PARTE 2 LADO A v1 (copy)\nDirigida por Satteo Mancio y Lugesto Erno\n";

    @Override
    public void run() {
        try {
            while (cantidadDeDiasEntreLanzamientos > 0) {
                //se está creando la intro
                sleep(duracionDiaEnSegundos * 1000 / numeroDeProductores);
                //se revisa si hay espacio en el buffer
                eIntro.acquire();
                //tiene que estar solito en el buffer
                sIntro.acquire();
                //SECCION CRITICA
                Interface.inIntro= LugoSancio_SOP_1.append(introGenerica, Interface.bIntro, Interface.driveIntro, Interface.inIntro);
                //ya salió de la sección crítica
                sIntro.release();
                //hay un item consumible más en N
                nIntro.release();
                textField.setText(Integer.toString(nIntro.availablePermits()));
                montoPorPagar = montoPorPagar + sueldo * 24;

            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorIntro.class.getName()).log(Level.SEVERE, null, ex);
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
