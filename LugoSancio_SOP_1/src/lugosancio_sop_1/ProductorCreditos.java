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
import static lugosancio_sop_1.LugoSancio_SOP_1.sCreditos;
import static lugosancio_sop_1.LugoSancio_SOP_1.nCreditos;
import static lugosancio_sop_1.LugoSancio_SOP_1.eCreditos;

/**
 *
 * @author ernes
 */
public class ProductorCreditos extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 3;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    Semaphore sem;
    String nombre;

    public ProductorCreditos(Semaphore sem, int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.sem = sem;
        this.nombre = nombre;

        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    String creditosGenerico = "\nDIRECTED BY:\nSatteo Mancio\nLugesto Erno\n\nACKNOWLEDGEMENTS:\nGebrayel Inatti\nStackOverflow\nw3schools\nla chica leyendo esto <3\n\n";
    
    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la creditos
                sleep(1000/numeroDeProductores);
                //se revisa si hay espacio en el buffer
                eCreditos.acquire();
                //tiene que estar solito en el buffer
                sCreditos.acquire();
                //SECCION CRITICA
                LugoSancio_SOP_1.append(creditosGenerico,LugoSancio_SOP_1.bCreditos,LugoSancio_SOP_1.kCreditos,LugoSancio_SOP_1.inCreditos);
                //ya salió de la sección crítica
                sCreditos.release();
                //hay un item consumible más en N
                nCreditos.release();
                System.out.println("hay esta cantidad de creditos: " + nCreditos.availablePermits());
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorCreditos.class.getName()).log(Level.SEVERE, null, ex);
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
