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

class MiCliente extends JFrame{
	
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

class InterfazCliente extends JPanel implements Runnable {

    private JTextField campo1;

    private ClienteConnection cliente;

    private JComboBox ip;

    private JLabel nick;

    private JTextArea espaciochat;
	
	private JButton miboton;

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

    private class Enviar implements ActionListener{

        private ClienteConnection cliente;

        public Enviar(ClienteConnection cliente){
        
            this.cliente = cliente;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            espaciochat.append("\n" + campo1.getText());

            Mensaje datos = new Mensaje(nick.getText(), "destinatario", campo1.getText(), " mensaje");
            this.cliente.Enviar_mensaje(datos);

            System.out.println("Mensaje Enviao");

        }

    }

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

