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
import static lugosancio_sop_1.LugoSancio_SOP_1.sCierre;
import static lugosancio_sop_1.LugoSancio_SOP_1.nCierre;
import static lugosancio_sop_1.LugoSancio_SOP_1.eCierre;

/**
 *
 * @author ernes
 */
public class ProductorCierre extends Thread {

    int numeroDeProductores = 1;
    double sueldo = 7.5;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    Semaphore sem;
    String nombre;

    public ProductorCierre(Semaphore sem, int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.sem = sem;
        this.nombre = nombre;

        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

//    @Override
//    public void run() {
//        try {
//            sem.acquire();
//            while (Interface.inventarioCierre < Interface.driveCierre) {
//                Thread.sleep(duracionDiaEnSegundos*1000);
//                this.montoPorPagar = this.montoPorPagar + this.sueldo * this.numeroDeProductores;
//                Interface.inventarioCierre++;
//                System.out.println("Hay " + Interface.inventarioCierre +" "+ this.nombre + " creadas");
//            }
//            System.out.println(this.nombre + "ya se lleno");
//            System.out.println(this.nombre +"El monto a pagar es: " + this.montoPorPagar);
//            sem.release();
//            
//            
//
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ProductorCierre.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    String cierreGenerico = "y a la final todos se murieron y nunca se encontró la gema del poder, FIN.\n";
    
    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la cierre
                System.out.println("creando cierre...");
                sleep(1000/rendimiento);
                //se revisa si hay espacio en el buffer
                eCierre.acquire();
                System.out.println("hay espacio en el buffer");
                //tiene que estar solito en el buffer
                sCierre.acquire();
                System.out.println("sc enter");
                //SECCION CRITICA
                LugoSancio_SOP_1.append(cierreGenerico,LugoSancio_SOP_1.bCierre,LugoSancio_SOP_1.kCierre,LugoSancio_SOP_1.inCierre);
                System.out.println("sc exit");
                //ya salió de la sección crítica
                sCierre.release();
                //hay un item consumible más en N
                nCierre.release();
                System.out.println("hay esta cantidad de cierres: " + nCierre.availablePermits());
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorCierre.class.getName()).log(Level.SEVERE, null, ex);
        }

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
