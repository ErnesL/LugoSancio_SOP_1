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
public class Ensamblador extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 8;
    int capitulosListos = 0;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    Semaphore sem;
    String nombre;

    public Ensamblador(Semaphore sem, int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
        this.sem = sem;
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    @Override
    public void run() {
        try {
            sem.acquire();
            while (true) {
                if (Interface.inventarioIntro > 0 && Interface.inventarioCreditos > 0 && Interface.inventarioInicio > 0 && Interface.inventarioCierre > 0 && Interface.inventarioPlottwist > 0) {

                    //TODO: agregar vaina para que cada 5 caps saque uno con plot twist
                    capitulosListos++;
                    Interface.driveIntro--;
                    Interface.driveCreditos--;
                    Interface.driveInicio--;
                    Interface.driveCierre--;
                    Interface.drivePlottwist--;

                    System.out.println("Capitulo creado exitosamente");
                    System.out.println("Capitulos listos: " + this.capitulosListos);

                    Thread.sleep(duracionDiaEnSegundos * 1000);

                } else {
                    //Cuando no hay caps para crear
                    System.out.println("No hay caps para crear");
                    Thread.sleep(duracionDiaEnSegundos * 1000);
                }
                sem.release();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Ensamblador.class.getName()).log(Level.SEVERE, null, ex);
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
