package Servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import Common.ClienteConnection;
import Common.Mensaje;

/**
 * Clase de servidor que se encarga de recibir los mensajes y reenviarlos
 */
public class MiServidor implements Runnable {
    /**
     * Representa el lugar para donde se muestran los mensajes
     */
	private	JTextArea areatexto;
    /**
     * Representa una instancia de la clase encargada de recibir los mensajes
     */
    public Recepcion recepcion;

    /**
     * Crea una instancia de servidor usando una recepcion
     * @param recepcion
     */
	public MiServidor(Recepcion recepcion){

        this.recepcion = recepcion;
		
		}

    /**
     * Inicia el servidor y crea una conexion propia
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub

        try {
            ServerSocket servidor= new ServerSocket(0);

            System.out.println("Usando el puerto: " + servidor.getLocalPort());

            while(true){
//guardar variable mi usuario en recepcion,
                Socket misocket = servidor.accept();

                ClienteConnection miusuario=new ClienteConnection("nick", "ip", misocket);

                Mensaje mensajeMetadata = miusuario.LeerMensajeMetaData();

                if (mensajeMetadata != null && mensajeMetadata.getRemitente() != ""){
                    this.recepcion.EnviarMensajeTodos(mensajeMetadata);
                    Mensaje mensajeClientesConectados = new Mensaje("server", mensajeMetadata.getRemitente(), this.recepcion.ObtenerNombreClientes(), "conexiones" );
                    System.out.println("Usuario " +mensajeMetadata.getRemitente()+ " se ha conectado");
                    miusuario.setNick(mensajeMetadata.getRemitente());
                    this.recepcion.AgregarConexion(miusuario);
                    this.recepcion.EnviarMensaje(mensajeClientesConectados);

                    Thread mihilo= new Thread(miusuario);

                    mihilo.start();
                }


            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}


