/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author ACER 53F4
 */
public class MensajeEntrada extends Thread {
    
    private Notificable notificable;
    private DataInputStream entrada;
    
    public MensajeEntrada(Notificable notificable, DataInputStream entrada){
        this.notificable = notificable;
        this.entrada = entrada;
    }    
    
    public String procesarMensaje(String mensaje){
        String[] keys = mensaje.split(",");
        
        String[] nameKey = keys[0].split(":"); 
        String[] codeKey = keys[1].split(":");
        String[] actionKey = keys[2].split(":");
        String[] valueKey = keys[3].split(":");
        
        switch(actionKey[1]){
            case "name":
                this.notificable.login(nameKey[1] + ": " + valueKey[1]);
                break;
            case "switch":
                String[] reactorKey = keys[4].split(":");
                this.notificable.switchReactor(nameKey[1] + ": " + valueKey[1], valueKey[1], nameKey[1], reactorKey[1]);
                break;
        }
        
        
        return "";
    }

    @Override
    public void run() {
        while (true) {
            try {
                // read the message sent to this client
                String msg = entrada.readUTF();
                System.out.println(msg);
                procesarMensaje(msg);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

}