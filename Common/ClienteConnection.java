package Common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Clase encargada de crear conexiones entre clientes y el servidor
 */
public class ClienteConnection implements Runnable {
	String nick, ip;

    public String mensaje;

    Socket socket;

    ConcurrentLinkedQueue<Mensaje> mensajes_recibidos;

    /**
     * Crea un paquete con la informacion de cada mensaje
     * @param nick Quien lo envia
     * @param ip Ip de quien lo envia
     * @param socket socket al que pertenece
     */
	public ClienteConnection(String nick, String ip, Socket socket){

    this.nick = nick;

    this.ip = ip;

    this.socket = socket;

    this.mensajes_recibidos= new ConcurrentLinkedQueue<>();

		
		}


    /**
     * Conecta al cliente con el socket dado
     * @param misocket Usado para concetar al cliente
     */
    public ClienteConnection(Socket misocket) {
        this.socket= misocket;
    }

    /**
     * Crea una linea de envio y escribe un mensaje con los datos
     * @param mensaje La clase para la informacion de los mensajes
     */
    public void Enviar_mensaje(Mensaje mensaje){
        try {

                ObjectOutputStream envio_datos= new ObjectOutputStream(this.socket.getOutputStream());

                envio_datos.writeObject(mensaje);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                System.out.println(e1.getMessage());
            }
        
    }


    /**
     * Devuelve el primer elemento de la lista de mensajes
     * @return El primer elemento de la lista de mensajes
     */
    public Mensaje Obtener_mensaje(){

        return this.mensajes_recibidos.poll();
    }

    /**
     * Función para revisar el cliente resive mensajes
     * @return Un booleano que dice si la lista de mensajes recibidos tiene algun elemento
     */
    public Boolean Revisar_bandeja(){
        return !this.mensajes_recibidos.isEmpty();
    }

    public void setNick(String nick){
        this.nick= nick;
    }
    public String getNick(){
        return nick;
    }


    /**
     * Se encarga de recibir constantemente mensajes y los añade a la lista de mensajes
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("nuevo cliente conectado");

        try {

            Mensaje paqueteRecibido;

            while(true){
                ObjectInputStream entradaDatos = new ObjectInputStream(this.socket.getInputStream());

                paqueteRecibido=(Mensaje)entradaDatos.readObject();

                this.mensajes_recibidos.add(paqueteRecibido);
                
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}