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
import static lugosancio_sop_1.LugoSancio_SOP_1.eCierre;
import static lugosancio_sop_1.LugoSancio_SOP_1.eCreditos;
import static lugosancio_sop_1.LugoSancio_SOP_1.eInicio;
import static lugosancio_sop_1.LugoSancio_SOP_1.eIntro;
import static lugosancio_sop_1.LugoSancio_SOP_1.ePlottwist;
import static lugosancio_sop_1.LugoSancio_SOP_1.nCierre;
import static lugosancio_sop_1.LugoSancio_SOP_1.nCreditos;
import static lugosancio_sop_1.LugoSancio_SOP_1.nInicio;
import static lugosancio_sop_1.LugoSancio_SOP_1.nIntro;
import static lugosancio_sop_1.LugoSancio_SOP_1.nPlottwist;
import static lugosancio_sop_1.LugoSancio_SOP_1.sCierre;
import static lugosancio_sop_1.LugoSancio_SOP_1.sCreditos;
import static lugosancio_sop_1.LugoSancio_SOP_1.sInicio;
import static lugosancio_sop_1.LugoSancio_SOP_1.sIntro;
import static lugosancio_sop_1.LugoSancio_SOP_1.sPlottwist;

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
            while (true) {
                
                String nuevoCapitulo = "";
                String w = "";
                
                //INTRO
                nIntro.acquire();
                sIntro.acquire();
                w = LugoSancio_SOP_1.take(LugoSancio_SOP_1.bIntro,LugoSancio_SOP_1.kIntro,LugoSancio_SOP_1.outIntro);
                LugoSancio_SOP_1.outIntro = (LugoSancio_SOP_1.outIntro+1)%LugoSancio_SOP_1.kIntro;
                sIntro.release();
                eIntro.release();
                nuevoCapitulo = nuevoCapitulo.concat(w);
                
                //INICIO
                nInicio.acquire();
                sInicio.acquire();
                w = LugoSancio_SOP_1.take(LugoSancio_SOP_1.bInicio,LugoSancio_SOP_1.kInicio,LugoSancio_SOP_1.outInicio);
                LugoSancio_SOP_1.outInicio = (LugoSancio_SOP_1.outInicio+1)%LugoSancio_SOP_1.kInicio;
                sInicio.release();
                eInicio.release();
                nuevoCapitulo = nuevoCapitulo.concat(w);
                
                //PLOT TWIST
                if (capitulosListos % 5 == 4) {
                    nPlottwist.acquire();
                    sPlottwist.acquire();
                    w = LugoSancio_SOP_1.take(LugoSancio_SOP_1.bPlottwist,LugoSancio_SOP_1.kPlottwist,LugoSancio_SOP_1.outPlottwist);
                    LugoSancio_SOP_1.outPlottwist = (LugoSancio_SOP_1.outPlottwist+1)%LugoSancio_SOP_1.kPlottwist;
                    sPlottwist.release();
                    ePlottwist.release();
                    nuevoCapitulo = nuevoCapitulo.concat(w);
                }
                
                //CIERRE
                nCierre.acquire();
                sCierre.acquire();
                w = LugoSancio_SOP_1.take(LugoSancio_SOP_1.bCierre,LugoSancio_SOP_1.kCierre,LugoSancio_SOP_1.outCierre);
                LugoSancio_SOP_1.outCierre = (LugoSancio_SOP_1.outCierre+1)%LugoSancio_SOP_1.kCierre;
                sCierre.release();
                eCierre.release();
                nuevoCapitulo = nuevoCapitulo.concat(w);
                
                //CREDITOS
                nCreditos.acquire();
                sCreditos.acquire();
                w = LugoSancio_SOP_1.take(LugoSancio_SOP_1.bCreditos,LugoSancio_SOP_1.kCreditos,LugoSancio_SOP_1.outCreditos);
                LugoSancio_SOP_1.outCreditos = (LugoSancio_SOP_1.outCreditos+1)%LugoSancio_SOP_1.kCreditos;
                sCreditos.release();
                eCreditos.release();
                nuevoCapitulo = nuevoCapitulo.concat(w);
                
                //dormir ensamblador dos días para que cree el cap
                Thread.sleep(duracionDiaEnSegundos * 2000);
                capitulosListos++;
                
                System.out.println(nuevoCapitulo);
                System.out.println("Capitulos listos: " + this.capitulosListos);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public void run() {
//        try {
//            sem.acquire();
//            while (true) {
//                if (Interface.inventarioIntro > 0 && Interface.inventarioCreditos > 0 && Interface.inventarioInicio > 0 && Interface.inventarioCierre > 0 && Interface.inventarioPlottwist > 0) {
//
//                    //TODO: agregar vaina para que cada 5 caps saque uno con plot twist
//                    Interface.inventarioIntro--;
//                    Interface.inventarioCreditos--;
//                    Interface.inventarioInicio--;
//                    Interface.inventarioCierre--;
//                    //agrega plot twist al cap si ya es el quinto cap
//                    if (capitulosListos % 5 == 0) {
//                        Interface.inventarioPlottwist--;
//                    }
//                    System.out.println("El ensamblador ha empezado a crear un capítulo");
//                    //dormir ensamblador dos días para que cree el cap
//                    Thread.sleep(duracionDiaEnSegundos * 2000);
//                    capitulosListos++;
//                    
//                    System.out.println("Capitulo creado exitosamente");
//                    System.out.println("Capitulos listos: " + this.capitulosListos);
//                    
//                } else {
//                    //Cuando no hay caps para crear
//                    System.out.println("No hay caps para crear");
//                    Thread.sleep(duracionDiaEnSegundos * 1000);
//                }
//                sem.release();
//            }
//
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Ensamblador.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

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
