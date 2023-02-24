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
import static lugosancio_sop_1.Interface.eCreditosRM;
import static lugosancio_sop_1.Interface.sCreditosRM;
import lugosancio_sop_1.LugoSancio_SOP_1;


/**
 *
 * @author ernes
 */
public class ProductorCreditosRM extends Thread {

    int numeroDeProductores = 1;
    int sueldo = 3;
    int montoPorPagar = 0;
    int duracionDiaEnSegundos;
    int rendimiento = 1;
    String nombre;
    JTextField textField;

    public ProductorCreditosRM( int numeroProductores, String nombre, int duracionDiaEnSegundos) {
        this.numeroDeProductores = numeroProductores;
  
        this.nombre = nombre;
        this.duracionDiaEnSegundos = duracionDiaEnSegundos;
    }

    String creditosGenerico = "\nDIRECTED BY:\nSatteo Mancio\nLugesto Erno\n\nACKNOWLEDGEMENTS:\nGebrayel Inatti\nStackOverflow\nw3schools\nla chica leyendo esto <3\n\n";
    
    @Override
    public void run() {
        try {
            while (true) {
                //se está creando la creditos
                sleep(duracionDiaEnSegundos*1000/numeroDeProductores*2);
                //se revisa si hay espacio en el buffer
                eCreditosRM.acquire();
                //tiene que estar solito en el buffer
                sCreditosRM.acquire();
                //SECCION CRITICA
                Interface.inCreditosRM = LugoSancio_SOP_1.append(creditosGenerico,Interface.bCreditosRM,Interface.driveCreditos,Interface.inCreditos);
                //ya salió de la sección crítica
                sCreditos.release();
                //hay un item consumible más en N
                nCreditos.release();
                textField.setText(Integer.toString(nCreditos.availablePermits()));
                montoPorPagar = montoPorPagar + sueldo*24;
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorCreditosRM.class.getName()).log(Level.SEVERE, null, ex);
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
