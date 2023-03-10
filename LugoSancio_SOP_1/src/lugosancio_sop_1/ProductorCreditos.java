/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugosancio_sop_1;

import static lugosancio_sop_1.Interface.eCreditos;
import static lugosancio_sop_1.Interface.nCreditos;
import static lugosancio_sop_1.Interface.sCreditos;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import static lugosancio_sop_1.Interface.cantidadDeDiasEntreLanzamientos;
import static lugosancio_sop_1.Interface.nCreditosRM;
import static lugosancio_sop_1.Interface.sCreditosRM;
import lugosancio_sop_1.LugoSancio_SOP_1;


/**
 *
 * @author ernes
 */
public class ProductorCreditos extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 3;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    String nombre;
    JTextField textField;

    public ProductorCreditos( int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
  
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    String creditosGenerico = "\nDIRECTED BY:\nSatteo Mancio\nLugesto Erno\n\nACKNOWLEDGEMENTS:\nGebrayel Inatti\nStackOverflow\nw3schools\nla chica leyendo esto <3\n\n";
    
    @Override
    public void run() {
        try {
            while (cantidadDeDiasEntreLanzamientos > 0) {
                //se está creando la creditos
                sleep(duracionDiaEnSegundos*1000/numeroDeProductores*4);
                //se revisa si hay espacio en el buffer
                eCreditos.acquire();
                //tiene que estar solito en el buffer
                sCreditos.acquire();
                //SECCION CRITICA
                Interface.inCreditos = LugoSancio_SOP_1.append(creditosGenerico,Interface.bCreditos,Interface.driveCreditos,Interface.inCreditosRM);
                //ya salió de la sección crítica
                sCreditosRM.release();
                //hay un item consumible más en N
                nCreditosRM.release();
                textField.setText(Integer.toString(nCreditosRM.availablePermits()));
                montoPorPagar = montoPorPagar + sueldo*24;
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorCreditos.class.getName()).log(Level.SEVERE, null, ex);
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
