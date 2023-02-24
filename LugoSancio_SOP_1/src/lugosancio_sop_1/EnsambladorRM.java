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
import static lugosancio_sop_1.Interface.eCierreRM;
import static lugosancio_sop_1.Interface.eCreditos;
import static lugosancio_sop_1.Interface.eCreditosRM;
import static lugosancio_sop_1.Interface.eInicio;
import static lugosancio_sop_1.Interface.eInicioRM;
import static lugosancio_sop_1.Interface.eIntro;
import static lugosancio_sop_1.Interface.eIntroRM;
import static lugosancio_sop_1.Interface.ePlottwist;
import static lugosancio_sop_1.Interface.ePlottwistRM;
import static lugosancio_sop_1.Interface.nCierre;
import static lugosancio_sop_1.Interface.nCierreRM;
import static lugosancio_sop_1.Interface.nCreditos;
import static lugosancio_sop_1.Interface.nCreditosRM;
import static lugosancio_sop_1.Interface.nInicio;
import static lugosancio_sop_1.Interface.nInicioRM;
import static lugosancio_sop_1.Interface.nIntro;
import static lugosancio_sop_1.Interface.nIntroRM;
import static lugosancio_sop_1.Interface.nPlottwist;
import static lugosancio_sop_1.Interface.nPlottwistRM;
import static lugosancio_sop_1.Interface.sCierre;
import static lugosancio_sop_1.Interface.sCierreRM;
import static lugosancio_sop_1.Interface.sCreditos;
import static lugosancio_sop_1.Interface.sCreditosRM;
import static lugosancio_sop_1.Interface.sInicio;
import static lugosancio_sop_1.Interface.sInicioRM;
import static lugosancio_sop_1.Interface.sIntro;
import static lugosancio_sop_1.Interface.sIntroRM;
import static lugosancio_sop_1.Interface.sPlottwist;
import static lugosancio_sop_1.Interface.sPlottwistRM;

/**
 *
 * @author ernes
 */
public class EnsambladorRM extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 8;
    int capitulosListos = 0;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int cantidadDeDiasEntreLanzamientos;
    JTextField textField;

    String nombre;

    public EnsambladorRM(int numeroProductores, String nombre, int duracionDiaEnSegundos) {
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
                nIntroRM.acquire();
                sIntroRM.acquire();
                w = LugoSancio_SOP_1.take(Interface.bIntroRM, Interface.driveIntroRM, Interface.outIntroRM);
                Interface.outIntroRM = (Interface.outIntroRM+1)%Interface.driveIntroRM;
                sIntroRM.release();
                eIntroRM.release();
//                nuevoCapitulo = nuevoCapitulo.concat(w);

                //INICIO
                nInicioRM.acquire();
                sInicioRM.acquire();
                w = LugoSancio_SOP_1.take(Interface.bInicioRM, Interface.driveInicioRM, Interface.outInicioRM);
                Interface.outInicioRM = (Interface.outInicioRM+1)%Interface.driveInicioRM;
                sInicioRM.release();
                eInicioRM.release();
//                nuevoCapitulo = nuevoCapitulo.concat(w);

                //PLOT TWIST
                if (capitulosListos % 5 == 4) {
                    nPlottwistRM.acquire();
                    sPlottwistRM.acquire();
                    w = LugoSancio_SOP_1.take(Interface.bPlottwistRM, Interface.drivePlottwistRM, Interface.outPlottwistRM);
                    Interface.outPlottwistRM = (Interface.outPlottwistRM+1)%Interface.drivePlottwistRM;
                    sPlottwistRM.release();
                    ePlottwistRM.release();
//                    nuevoCapitulo = nuevoCapitulo.concat(w);
                }

                //CIERRE
                nCierreRM.acquire();
                sCierreRM.acquire();
                w = LugoSancio_SOP_1.take(Interface.bCierreRM, Interface.driveCierreRM, Interface.outCierreRM);
                Interface.outCierreRM = (Interface.outCierreRM+1)%Interface.driveCierreRM;
                sCierreRM.release();
                eCierreRM.release();
//                nuevoCapitulo = nuevoCapitulo.concat(w);

                //CREDITOS
                nCreditosRM.acquire();
                sCreditosRM.acquire();
                w = LugoSancio_SOP_1.take(Interface.bCreditosRM, Interface.driveCreditosRM, Interface.outCreditosRM);
                Interface.outCreditosRM = (Interface.outCreditosRM+1)%Interface.driveCreditosRM;
                sCreditosRM.release();
                eCreditosRM.release();
//                nuevoCapitulo = nuevoCapitulo.concat(w);

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

    public int getCapitulosListos() {
        return capitulosListos;
    }
    

    public void setMontoPorPagar(int montoPorPagar) {
        this.montoPorPagar = montoPorPagar;
    }

}
