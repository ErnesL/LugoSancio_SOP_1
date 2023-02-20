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

/**
 *
 * @author ernes
 */
public class ProductorPlottwist extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 10;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    Semaphore sem;
    String nombre;

    public ProductorPlottwist(Semaphore sem, int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.sem = sem;
        this.nombre = nombre;

        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    @Override
    public void run() {
        try {
            sem.acquire();
            while (Interface.inventarioPlottwist < Interface.drivePlottwist) {
                this.montoPorPagar = this.montoPorPagar + this.sueldo * this.numeroDeProductores;
                Interface.inventarioPlottwist++;
                System.out.println(this.nombre + "--->" + Interface.inventarioPlottwist);
                sleep(duracionDiaEnSegundos * 1000);

            }
            System.out.println(this.nombre + "ya se lleno");
            System.out.println(this.nombre + "El monto a pagar es: " + this.montoPorPagar);
            sem.release();

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorPlottwist.class.getName()).log(Level.SEVERE, null, ex);
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
