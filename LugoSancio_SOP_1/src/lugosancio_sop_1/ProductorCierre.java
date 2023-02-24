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

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
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
    JTextField textField;

    public ProductorCierre(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
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
                sleep(duracionDiaEnSegundos * 1000 / numeroDeProductores);
                //se revisa si hay espacio en el buffer
                eCierre.acquire();
                //tiene que estar solito en el buffer
                sCierre.acquire();
                //SECCION CRITICA
                LugoSancio_SOP_1.inCierre = LugoSancio_SOP_1.append(cierreGenerico,LugoSancio_SOP_1.bCierre,LugoSancio_SOP_1.kCierre,LugoSancio_SOP_1.inCierre);
                //ya salió de la sección crítica
                sCierre.release();
                //hay un item consumible más en N
                nCierre.release();
                System.out.println("hay esta cantidad de cierres: " + nCierre.availablePermits());
                textField.setText(Integer.toString(nCierre.availablePermits()));
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
