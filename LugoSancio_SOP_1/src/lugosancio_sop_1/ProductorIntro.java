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
import static lugosancio_sop_1.LugoSancio_SOP_1.sIntro;
import static lugosancio_sop_1.LugoSancio_SOP_1.nIntro;
import static lugosancio_sop_1.LugoSancio_SOP_1.eIntro;

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
    Semaphore sem;
    String nombre;

    public ProductorIntro(Semaphore sem, int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.sem = sem;
        this.nombre = nombre;

        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

//    @Override
//    public void run() {
//        try {
//            sem.acquire();
//            while (Interface.inventarioIntro < Interface.driveIntro) {
//                Thread.sleep(duracionDiaEnSegundos*1000);
//                this.montoPorPagar = this.montoPorPagar + this.sueldo * this.numeroDeProductores;
//                Interface.inventarioIntro++;
//                System.out.println("Hay " + Interface.inventarioIntro +" "+ this.nombre + " creadas");
//            }
//            System.out.println(this.nombre + "ya se lleno");
//            System.out.println(this.nombre +"El monto a pagar es: " + this.montoPorPagar);
//            sem.release();
//            
//            
//
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ProductorIntro.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    String introGenerica = "\noshiete oshiete watashi wa arigato gosaimasu\nATTACK ON TITAN TEMPORADA 4.5 PARTE 2 LADO A v1 (copy)\nDirigida por Satteo Mancio y Lugesto Erno\n";
    
    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la intro
                sleep(1000/rendimiento);
                //se revisa si hay espacio en el buffer
                eIntro.acquire();
                //tiene que estar solito en el buffer
                sIntro.acquire();
                //SECCION CRITICA
                LugoSancio_SOP_1.append(introGenerica,LugoSancio_SOP_1.bIntro,LugoSancio_SOP_1.kIntro,LugoSancio_SOP_1.inIntro);
                //ya salió de la sección crítica
                sIntro.release();
                //hay un item consumible más en N
                nIntro.release();
                System.out.println("hay esta cantidad de intros: " + nIntro.availablePermits());

            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorIntro.class.getName()).log(Level.SEVERE, null, ex);
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
