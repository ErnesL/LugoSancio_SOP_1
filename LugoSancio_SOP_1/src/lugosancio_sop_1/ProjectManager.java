/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JTextField;
import static lugosancio_sop_1.Interface.cantidadDeDiasEntreLanzamientos;
import static lugosancio_sop_1.Interface.sCountdown;

/**
 *
 * @author matteosancio
 */
public class ProjectManager extends Thread {

    boolean estaViendoRM = false;
    int diasRestantes;
    int numDeCedula;
    boolean seAcaboElDia;
    int duracionDiaEnSegundos;
    int sueldo = 7;
    int montoPorPagar = 0;
    JTextField velmaDeadline;
    JTextField rmDeadline;
    JTextField actividad;
    JTextField salario;
    

    Semaphore sem = new Semaphore(0);

    public class Hilo extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    sem.acquire();
                    Thread.sleep(duracionDiaEnSegundos * 1000);
                    seAcaboElDia = true;

                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ProductorIntro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ProjectManager(int diasRestantes, int numDeCedula, int duracionDiaEnSegundos) {
        this.diasRestantes = diasRestantes;
        this.numDeCedula = numDeCedula;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    @Override
    public void run() {
        try {
            Hilo tHilo = new Hilo();
            tHilo.start();
            while (cantidadDeDiasEntreLanzamientos > 0) {
                sem.release();
                estaViendoRM = false;
                actividad.setText("Trabajando");
                seAcaboElDia = false;
                sCountdown.acquire();
                diasRestantes--;
                cantidadDeDiasEntreLanzamientos = diasRestantes;
                velmaDeadline.setText(Integer.toString(diasRestantes));
                rmDeadline.setText(Integer.toString(diasRestantes));
                sCountdown.release();
                while (!seAcaboElDia) {
                    estaViendoRM = !estaViendoRM;
                    if (estaViendoRM) {
                        actividad.setText("Viendo Rick and Morty");
                    } else {

                        actividad.setText("Trabajando");
                    }
                    Thread.sleep((15 + numDeCedula) * 1000 / 1440);

                }

                montoPorPagar = montoPorPagar + sueldo * 24;
                salario.setText(Integer.toString(montoPorPagar));
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setVelmaDeadline(JTextField velmaDeadline) {
        this.velmaDeadline = velmaDeadline;
    }

    public void setRmDeadline(JTextField rmDeadline) {
        this.rmDeadline = rmDeadline;
    }

    public void setActividad(JTextField actividad) {
        this.actividad = actividad;
    }

    public void setSalario(JTextField salario) {
        this.salario = salario;
    }

    public int getMontoPorPagar() {
        return montoPorPagar;
    }
    
    

}
