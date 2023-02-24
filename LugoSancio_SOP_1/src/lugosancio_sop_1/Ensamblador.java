/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import static lugosancio_sop_1.Interface.eCierre;
import static lugosancio_sop_1.Interface.eCreditos;
import static lugosancio_sop_1.Interface.eInicio;
import static lugosancio_sop_1.Interface.eIntro;
import static lugosancio_sop_1.Interface.ePlottwist;
import static lugosancio_sop_1.Interface.nCierre;
import static lugosancio_sop_1.Interface.nCreditos;
import static lugosancio_sop_1.Interface.nInicio;
import static lugosancio_sop_1.Interface.nIntro;
import static lugosancio_sop_1.Interface.nPlottwist;
import static lugosancio_sop_1.Interface.sCierre;
import static lugosancio_sop_1.Interface.sCreditos;
import static lugosancio_sop_1.Interface.sInicio;
import static lugosancio_sop_1.Interface.sIntro;
import static lugosancio_sop_1.Interface.sPlottwist;

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
    int cantidadDeDiasEntreLanzamientos;
    JTextField textField;

    String nombre;

    public Ensamblador(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
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
                w = LugoSancio_SOP_1.take(Interface.bIntro, Interface.driveIntro, Interface.outIntro);
                Interface.outIntro = (Interface.outIntro+1)%Interface.driveIntro;
                sIntro.release();
                eIntro.release();
                nuevoCapitulo = nuevoCapitulo.concat(w);

                //INICIO
                nInicio.acquire();
                sInicio.acquire();
                w = LugoSancio_SOP_1.take(Interface.bInicio, Interface.driveInicio, Interface.outInicio);
                Interface.outInicio = (Interface.outInicio+1)%Interface.driveInicio;
                sInicio.release();
                eInicio.release();
                nuevoCapitulo = nuevoCapitulo.concat(w);

                //PLOT TWIST
                if (capitulosListos % 5 == 4) {
                    nPlottwist.acquire();
                    sPlottwist.acquire();
                    w = LugoSancio_SOP_1.take(Interface.bPlottwist, Interface.drivePlottwist, Interface.outPlottwist);
                    Interface.outPlottwist = (Interface.outPlottwist+1)%Interface.drivePlottwist;
                    sPlottwist.release();
                    ePlottwist.release();
                    nuevoCapitulo = nuevoCapitulo.concat(w);
                }

                //CIERRE
                nCierre.acquire();
                sCierre.acquire();
                w = LugoSancio_SOP_1.take(Interface.bCierre, Interface.driveCierre, Interface.outCierre);
                Interface.outCierre = (Interface.outCierre+1)%Interface.driveCierre;
                sCierre.release();
                eCierre.release();
                nuevoCapitulo = nuevoCapitulo.concat(w);

                //CREDITOS
                nCreditos.acquire();
                sCreditos.acquire();
                w = LugoSancio_SOP_1.take(Interface.bCreditos, Interface.driveCreditos, Interface.outCreditos);
                Interface.outCreditos = (Interface.outCreditos+1)%Interface.driveCreditos;
                sCreditos.release();
                eCreditos.release();
                nuevoCapitulo = nuevoCapitulo.concat(w);

                //dormir ensamblador dos días para que cree el cap
                Thread.sleep(duracionDiaEnSegundos * 2000);
                capitulosListos++;
                textField.setText(Integer.toString(capitulosListos));
                montoPorPagar = montoPorPagar + sueldo*numeroDeProductores;

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
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
