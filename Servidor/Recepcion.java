package Servidor;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import Common.ClienteConnection;
import Common.Mensaje;



/**
 * Clase encargada de recibir mensajes leer para quien es y enviarlo
 */
public class Recepcion implements Runnable {

    ConcurrentHashMap<String, ClienteConnection> conexiones;

    /**
     * Inicia el hashmap de conexiones de los clientes
     */
    public Recepcion(){

        this.conexiones = new ConcurrentHashMap<>();

    }

    /**
     * Añade una conexion el hashmap
     * @param conexion La clase especificada para conectar cada cliente
     */
    public void AgregarConexion(ClienteConnection conexion){
        this.conexiones.put(conexion.getNick(), conexion);

    }

    /**
     * Se encarga de leer para quien es el mensaje y enviarlo
     * @param mensaje La clase para los mensajes
     */
    public void EnviarMensaje(Mensaje mensaje){
        if(this.conexiones.containsKey(mensaje.getDestinatario())){
            this.conexiones.get(mensaje.getDestinatario()).Enviar_mensaje(mensaje);// obtiene el destinatario y envia el mensaje
        }

    }

    /**
     * @return El nick de cada chat
     */
    public String ObtenerNombreClientes(){
        String result = "";
        Enumeration<String> llaves = this.conexiones.keys();
        while(llaves.hasMoreElements()){
            String llave = llaves.nextElement();
            if(this.conexiones.containsKey(llave)){
                result += this.conexiones.get(llave).getNick()+";";
            }
        }
        return result;

    }

    /**
     * Recorre el hashmap y envia el mensaje dado
     * @param mensaje La clase para los mensajes
     */
    public void EnviarMensajeTodos(Mensaje mensaje){
        Enumeration<String> llaves = this.conexiones.keys();
        while(llaves.hasMoreElements()){
            String llave = llaves.nextElement();
            if(this.conexiones.containsKey(llave)){
                this.conexiones.get(llave).Enviar_mensaje(mensaje);// obtiene el destinatario y envia el mensaje
            }//Falta mensaje comando que envie que alguien nuevo se conecto
        }
    }

    /**
     * Revisa la bandeja de cada conexion para ver si tienen mensajes pendientes y los envia
     */
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
                        this.EnviarMensaje(mensaje);
                    }
                }
            }
        }
    
    }
}
