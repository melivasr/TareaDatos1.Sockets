package Servidor;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import Common.ClienteConnection;
import Common.Mensaje;

// recibir mensajes para enviarlos al destinatario

public class Recepcion implements Runnable {

    ConcurrentHashMap<String, ClienteConnection> conexiones;

    public Recepcion(){

        this.conexiones = new ConcurrentHashMap<>();

    }

    public void AgregarConexion(ClienteConnection conexion){
        this.conexiones.put(conexion.getNick(), conexion);

    }

    public void EnviarMensaje(Mensaje mensaje){
        if(this.conexiones.containsKey(mensaje.getDestinatario())){
            this.conexiones.get(mensaje.getDestinatario()).Enviar_mensaje(mensaje);// obtiene el destinatario y envia el mensaje
        }

    }

    public void EnviarMensajeTodos(Mensaje mensaje){
        Enumeration<String> llaves = this.conexiones.keys();
        while(llaves.hasMoreElements()){
            String llave = llaves.nextElement();
            if(this.conexiones.containsKey(llave)){
                this.conexiones.get(llave).Enviar_mensaje(mensaje);// obtiene el destinatario y envia el mensaje
            }//Mensaje comando que envie que alguien nuevo se conecto
        }

    }

    @Override
    public void run() {
        while (true) {
            Enumeration<String> llaves = this.conexiones.keys();
            while (llaves.hasMoreElements()) {
                String llave = llaves.nextElement();
                ClienteConnection conexion = this.conexiones.get(llave);
                if(conexion.Revisar_bandeja()){
                    Mensaje mensaje = conexion.Obtener_mensaje();
                    if (mensaje != null) {
                        EnviarMensaje(mensaje);
                    }
                }
            }
        }
    
    }
}
