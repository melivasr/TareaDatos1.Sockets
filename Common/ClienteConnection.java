package Common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClienteConnection implements Runnable {
	String nick, ip;

    public String mensaje;

    Socket socket;

    ConcurrentLinkedQueue<Mensaje> mensajes_recibidos;

	public ClienteConnection(String nick, String ip, Socket socket){

    this.nick = nick;

    this.ip = ip;

    this.socket = socket;

    this.mensajes_recibidos= new ConcurrentLinkedQueue<>();

		
		}


    public ClienteConnection(Socket misocket) {
        this.socket= misocket;
    }

    public void Enviar_mensaje(Mensaje mensaje){
        try {

                ObjectOutputStream envio_datos= new ObjectOutputStream(this.socket.getOutputStream());

                envio_datos.writeObject(mensaje);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                System.out.println(e1.getMessage());
            }
        
    }

    
    public Mensaje Obtener_mensaje(){

        return this.mensajes_recibidos.poll();
    }

    public Boolean Revisar_bandeja(){
        return !this.mensajes_recibidos.isEmpty();
    }

    public void setNick(String nick){
        this.nick= nick;
    }
    public String getNick(){
        return nick;
    }
        

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