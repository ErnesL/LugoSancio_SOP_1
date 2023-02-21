/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import Interface.Interface;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import lugosancio_sop_1.LugoSancio_SOP_1;
import static lugosancio_sop_1.LugoSancio_SOP_1.sInicio;
import static lugosancio_sop_1.LugoSancio_SOP_1.nInicio;
import static lugosancio_sop_1.LugoSancio_SOP_1.eInicio;

/**
 *
 * @author ernes
 */
public class ProductorInicio extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 7;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    Semaphore sem;
    String nombre;

    public ProductorInicio(Semaphore sem, int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.sem = sem;
        this.nombre = nombre;

        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    String inicioGenerico = "Miguel Mouse se levanta entusiasmado para ir a la escuela.\n";
    
    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la inicio
                System.out.println("creando inicio...");
                sleep(1000/rendimiento);
                //se revisa si hay espacio en el buffer
                eInicio.acquire();
                System.out.println("hay espacio en el buffer");
                //tiene que estar solito en el buffer
                sInicio.acquire();
                System.out.println("sc enter");
                //SECCION CRITICA
                LugoSancio_SOP_1.append(inicioGenerico,LugoSancio_SOP_1.bInicio,LugoSancio_SOP_1.kInicio,LugoSancio_SOP_1.inInicio);
                System.out.println("sc exit");
                //ya salió de la sección crítica
                sInicio.release();
                //hay un item consumible más en N
                nInicio.release();
                System.out.println("hay esta cantidad de inicios: " + nInicio.availablePermits());
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicio.class.getName()).log(Level.SEVERE, null, ex);
        }

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
