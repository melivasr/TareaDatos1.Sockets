package Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Common.ClienteConnection;
import Common.Mensaje;
/**
 * Clase para crear una ventana de chat
 */
class MiCliente extends JFrame{
    /**
     * Constructor de la ventana de chat
     */
	public MiCliente(){
		
		setBounds(650,200,280,350);
        
    
        InterfazCliente micanvas=new InterfazCliente();

        new Thread(micanvas).start();

        add(micanvas);
		
        

		setVisible(true);

		}
}
/**
 * Clase encargada de los elementos de la interfaz del chat
 */
class InterfazCliente extends JPanel implements Runnable {
    /**
     * Representa el lugar para escribir el mensaje
     */
    private JTextField campo1;
    /**
     * Representa la conexión con el cliente
     */
    private ClienteConnection cliente;
    /**
     * Representa una lista desplejable de ips
     */
    private JComboBox ip;
    /**
     * Representa el texto del nombre de usuario
     */
    private JLabel nick;
    /**
     * Representa el lugar para donde se muestran los mensajes
     */
    private JTextArea espaciochat;
    /**
     * Representa el boton de enviar
     */
	private JButton miboton;
    /**
     * Clase encargada iniciar los diferentes elementos de la interfaz del chat
     * @param cliente Conexion con el cliente
     */
	public InterfazCliente() {

   

        JTextField username = new JTextField();
        JTextField port = new JTextField();
        Object[] message = {
            "Nick:", username,
            "Puerto:", port
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.DEFAULT_OPTION);

        Socket misocket;

        try {
            misocket = new Socket("192.168.100.8", Integer.parseInt(port.getText()));

            this.cliente = new ClienteConnection(misocket);

            this.cliente.setNick(username.getText());

            this.cliente.Enviar_mensaje(new Mensaje(username.getText(), "", "", "conexion"));
            
            Mensaje mensaje = this.cliente.LeerMensajeMetaData();

            

            new Thread(cliente).start();

            JLabel n_nick= new JLabel("Nick: ");
            add(n_nick);
        
            nick = new JLabel();
            nick.setText(username.getText());
            add (nick);



            JLabel texto=new JLabel("Online: ");
            
            add(texto);
            
            ip = new JComboBox();

            add(ip);

            if(mensaje.getTipo().equals("conexiones")){
                String[] parts = mensaje.getMensaje().split(";");
                for (String part : parts) {
                    if(part != ""){
                        ip.addItem(part);
                    }

                }
            }

            espaciochat = new JTextArea(12,20); //coordenadas ventana

            add (espaciochat);
        
            campo1=new JTextField(20);
        
            add(campo1);
        
            miboton=new JButton("Enviar");

            Enviar mievento= new Enviar(this.cliente, this.ip);

            miboton.addActionListener(mievento);
            
            add(miboton);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        
        }

        

		
	}
    /**
     * Clase encargada de enviar mensajes al servidor
     */
    private class Enviar implements ActionListener{
        /**
         * Representa la conexión con el cliente
         */
        private ClienteConnection cliente;
        private JComboBox ip;

        /**
         * Modifica la conexion del cliente
         * @param cliente La nueva conexion
         */
        public Enviar(ClienteConnection cliente, JComboBox ip){
        
            this.cliente = cliente;
            this.ip = ip;
        }
        /**
         * Detecta los eventos
         * @param e El evento que esperamos que pase en este
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            espaciochat.append("\n" + campo1.getText());

            Mensaje datos = new Mensaje(nick.getText(), String.valueOf(this.ip.getSelectedItem()), campo1.getText(), "mensaje");
            this.cliente.Enviar_mensaje(datos);
            campo1.setText("");
            System.out.println("Mensaje Enviado");

        }

    }
    /**
     * Función encargada de revisar constantemente si se reciben mensajes y mostrarlos
     */
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(4000);
                System.out.println(this.cliente.mensajes_recibidos);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (this.cliente.Revisar_bandeja()){
                Mensaje paqueteRecibido = this.cliente.Obtener_mensaje();
                System.out.println(paqueteRecibido.ToString());
                if(paqueteRecibido.getTipo().equals("conexion")){
                    ip.addItem(paqueteRecibido.getRemitente());
                }else if(paqueteRecibido.getTipo().equals("mensaje")){
                    espaciochat.append("\n"+paqueteRecibido.getRemitente()+": "+paqueteRecibido.getMensaje());
                }

            }
            
        }
    }
	
}

