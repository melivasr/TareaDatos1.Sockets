package Servidor;

import java.io.ObjectInputStream;
import java.net.Socket;

import Mensaje.Mensaje;

class ClienteConection implements Runnable {
	String nick, ip, mensaje;

    Socket socket;

	public ClienteConection(String nick, String ip, Socket socket){

    this.nick = nick;

    this.ip = ip;

    this.socket = socket;
		
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

                System.out.println("paquete recibido " + paqueteRecibido.getRemitente()+ " " + paqueteRecibido.getMensaje());
                
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}