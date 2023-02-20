/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interface.Interface;

/**
 *
 * @author ernes
 */
public class ProductorCierre extends Thread {

    int numeroDeProductores = 1;
    double sueldo = 7.5;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    Semaphore sem;
    String nombre;

    public ProductorCierre(Semaphore sem, int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.sem = sem;
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    @Override
    public void run() {
        try {
            sem.acquire();
            while (Interface.inventarioCierre < Interface.driveCierre) {
                Thread.sleep(duracionDiaEnSegundos*1000);
                this.montoPorPagar = (int) (this.montoPorPagar + this.sueldo * this.numeroDeProductores);
                Interface.inventarioCierre++;
                System.out.println("Hay " + Interface.inventarioCierre +" "+ this.nombre + " creadas");
            }
            System.out.println(this.nombre + "ya se lleno");
            System.out.println(this.nombre +"El monto a pagar es: " + this.montoPorPagar);
            sem.release();

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorCierre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    public void setNumeroDeProductores(int numeroDeProductores) {
        this.numeroDeProductores = numeroDeProductores;
    }

    public int getSueldo() {
        return (int) sueldo;
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
