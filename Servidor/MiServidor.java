package Servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

import Common.ClienteConnection;

/**
 * Clase de servidor que se encarga de recibir los mensajes y reenviarlos
 */
public class MiServidor implements Runnable {
	private	JTextArea areatexto;
	public MiServidor(){
		
		}

    /**
     * Inicia el servidor y crea una conexion propia
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("holi");

        try {
            ServerSocket servidor= new ServerSocket(9998);

            String nick, ip, mensaje;

            ArrayList <String> listaIp= new ArrayList<String>();

            while(true){
//guardar variable mi usuario en recepcion,
                System.out.println("holi");

                Socket misocket = servidor.accept();

                ClienteConnection miusuario=new ClienteConnection("nick", "ip", misocket);

                Thread mihilo= new Thread(miusuario);

                mihilo.start();

            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}


