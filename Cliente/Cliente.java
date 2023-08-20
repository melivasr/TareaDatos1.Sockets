package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import Mensaje.Mensaje;

public class Cliente implements Runnable{
    String nick;
    ConcurrentLinkedQueue<Mensaje> mensajes_recibidos;
    Socket socket;
    
    public Cliente(Socket socket){
        this.socket = socket;
        this.mensajes_recibidos= new ConcurrentLinkedQueue<>();

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



    @Override
    public void run() {
        try{

            Mensaje paqueteRecibido;

            while (true){

                ObjectInputStream entradaDatos = new ObjectInputStream(this.socket.getInputStream());

                paqueteRecibido=(Mensaje)entradaDatos.readObject();
                
                this.mensajes_recibidos.add(paqueteRecibido);
                
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}