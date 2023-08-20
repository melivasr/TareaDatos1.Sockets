package Servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class MiServidor implements Runnable {
	private	JTextArea areatexto;
	public MiServidor(){
		
		}
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

                ClienteConection miusuario=new ClienteConection("nick", "ip", misocket);

                Thread mihilo= new Thread(miusuario);

                mihilo.start();

            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}


