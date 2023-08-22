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

        Socket misocket;
        try {
            misocket = new Socket("192.168.100.8", 9998);

            ClienteConnection cliente = new ClienteConnection(misocket);

            InterfazCliente micanvas=new InterfazCliente(cliente);
		
            new Thread(cliente).start();

            new Thread(micanvas).start();

            add(micanvas);
		

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        
        }

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
	public InterfazCliente(ClienteConnection cliente) {

        this.cliente = cliente;

        String usuario = JOptionPane.showInputDialog("Nick: ");

        this.cliente.setNick(usuario);

        this.cliente.Enviar_mensaje(new Mensaje(usuario, "", "conection", "comando"));

        JLabel n_nick= new JLabel("Nick: ");
        add(n_nick);
    
        nick = new JLabel();
        nick.setText(usuario);
        add (nick);



		JLabel texto=new JLabel("Online: ");
		
		add(texto);
        
        ip = new JComboBox();

        ip.addItem(usuario);

        add(ip);

        espaciochat = new JTextArea(12,20); //coordenadas ventana

        add (espaciochat);
	
		campo1=new JTextField(20);
	
		add(campo1);
	
		miboton=new JButton("Enviar");

        Enviar mievento= new Enviar(this.cliente);

        miboton.addActionListener(mievento);
		
		add(miboton);

		
	}
    /**
     * Clase encargada de enviar mensajes al servidor
     */
    private class Enviar implements ActionListener{
        /**
         * Representa la conexión con el cliente
         */
        private ClienteConnection cliente;

        /**
         * Modifica la conexion del cliente
         * @param cliente La nueva conexion
         */
        public Enviar(ClienteConnection cliente){
        
            this.cliente = cliente;
        }
        /**
         * Detecta los eventos
         * @param e El evento que esperamos que pase en este
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            espaciochat.append("\n" + campo1.getText());

            Mensaje datos = new Mensaje(nick.getText(), "destinatario", campo1.getText(), " mensaje");
            this.cliente.Enviar_mensaje(datos);

            System.out.println("Mensaje Enviado");

        }

    }
    /**
     * Función encargada de revisar constantemente si se reciben mensajes y mostrarlos
     */
    @Override
    public void run() {
        while(true){
            if (this.cliente.Revisar_bandeja()){
                Mensaje paqueteRecibido = this.cliente.Obtener_mensaje();
                espaciochat.append("\n"+paqueteRecibido.getRemitente()+": "+paqueteRecibido.getMensaje());

            }
            
        }
    }
	
}

