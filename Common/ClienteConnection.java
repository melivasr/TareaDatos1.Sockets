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
    /**
     * Representa la informacion de la ventana(nombre de usuario e ip)
     */
	String nick, ip;
    /**
     * Representa el texto a enviar
     */
    public String mensaje;
    /**
     * Representa una linea de salida de datos
     */
    private ObjectOutputStream envioDatos;
    /**
     * Representa una linea de entrada de datos
     */
    private ObjectInputStream entradaDatos;
    /**
     * Representa el socket del cliente
     */
    Socket socket;
    /**
     * Representa los mensajes recibidos
     */
    public ConcurrentLinkedQueue<Mensaje> mensajes_recibidos;

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
     * Recibe los datos de la linea de entrada de datos
     */
    public ObjectInputStream getEntradaDatos() {
        if(this.entradaDatos == null){
            try {
                this.entradaDatos = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return entradaDatos;
    }
    /**
     * Envia los datos de la linea de salida de datos
     */
    public ObjectOutputStream getEnvioDatos() {
        if(this.envioDatos == null){
            try {
                this.envioDatos = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return envioDatos;
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

                this.getEnvioDatos().flush();

                this.getEnvioDatos().writeObject(mensaje);

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
     * Función para revisar el cliente recibe mensajes
     * @return Un booleano que dice si la lista de mensajes recibidos tiene algun elemento
     */
    public Boolean Revisar_bandeja(){
        return this.mensajes_recibidos != null && !this.mensajes_recibidos.isEmpty();


    }

    /**
     * @param nick El nuevo nombre de usuario
     */
    public void setNick(String nick){
        this.nick= nick;
    }
    /**
     * @return Devuelve el nombre de usuario
     */
    public String getNick() {
        return nick;
    }

    /**
     * @return Devuelve los datos leidos de la linea de entrada de datos
     */
    public Mensaje LeerMensajeMetaData(){

        ObjectInputStream entradaDatos = this.getEntradaDatos();
        try {
            Mensaje mensaje = (Mensaje)entradaDatos.readObject();
            return mensaje;
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    /**
     * Se encarga de recibir constantemente mensajes y los añade a la lista de mensajes
     */

    }
    @Override
    public void run() {
        System.out.println("Se inicio el run");

        try {

            Mensaje paqueteRecibido;
            ObjectInputStream entradaDatos = this.getEntradaDatos();

            while(true){

                paqueteRecibido=(Mensaje)entradaDatos.readObject();

                if(this.mensajes_recibidos==null){
                    this.mensajes_recibidos= new ConcurrentLinkedQueue<>();
                }

                this.mensajes_recibidos.offer(paqueteRecibido);
                System.out.println(this.mensajes_recibidos.toString());

            
                
            }

        } catch (Exception e) {
            System.out.println("Salio");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}