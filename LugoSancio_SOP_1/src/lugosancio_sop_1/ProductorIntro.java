/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ernes
 */
public class ProductorIntro extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 5;
    int almacenamiento = 0;
    int almacenamientoMax = 30;
    int montoPorPagar = 0;
    Semaphore sem;
    String nombre;

    public ProductorIntro(Semaphore sem, int numeroProductores, String nombre, int almacenamientoMax) {
        this.numeroDeProductores = numeroProductores;
        this.sem = sem;
        this.nombre = nombre;
        this.almacenamientoMax = almacenamientoMax;
    }

    @Override
    public void run() {
        try {
            sem.acquire();
            System.out.println("consegui el permiso");
            while (almacenamiento < almacenamientoMax) {
                this.montoPorPagar = this.montoPorPagar + this.sueldo * this.numeroDeProductores;
                almacenamiento++;
                System.out.println("Aumente el almacenamiento en 1");
                System.out.println("El almacenamiento va por: " + this.almacenamiento);

            }
            System.out.println("ya se lleno");
            System.out.println("El monto a pagar es: " + this.montoPorPagar);
            sem.release();

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorIntro.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setAlmacenamientoMax(int almacenamientoMax) {
        this.almacenamientoMax = almacenamientoMax;
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

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
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
