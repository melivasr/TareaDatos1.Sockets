import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MiServidor miusuario=new MiServidor();
		
		miusuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}
}

class MiServidor extends JFrame implements Runnable {
	
	public MiServidor(){
		
		setBounds(800,250,280,350);
			
		JPanel micanvas= new JPanel();
		
		micanvas.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		micanvas.add(areatexto,BorderLayout.CENTER);
		
		add(micanvas);
		
		setVisible(true);

        Thread mihilo= new Thread(this);

        mihilo.start();
		
		}
	
	

    @Override
    public void run() {
        // TODO Auto-generated method stub
        //System.out.println("holi");

        try {
            ServerSocket servidor= new ServerSocket(9999);

            String nick, ip, mensaje;

            Envios datos_recibidos;

            while(true){

            Socket misocket = servidor.accept();

            ObjectInputStream envio_datos = new ObjectInputStream(misocket.getInputStream());

            datos_recibidos = (Envios) envio_datos.readObject();

            
           /*  DataInputStream entrada_datos = new DataInputStream(misocket.getInputStream());

            String mensaje_texto = entrada_datos.readUTF();

            areatexto.append("\n" + mensaje_texto);*/

            misocket.close();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private	JTextArea areatexto;


}