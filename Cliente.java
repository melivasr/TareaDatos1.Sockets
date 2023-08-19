import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
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



public class Cliente {

	public static void main(String[] args) {

        MiCliente miusuario=new MiCliente();
		
		miusuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class MiCliente extends JFrame{


	
	public MiCliente(){
		
		setBounds(650,200,280,350);

		InterfazCliente micanvas=new InterfazCliente();
		
		add(micanvas);
		
		setVisible(true);
		}
}

class InterfazCliente extends JPanel implements Runnable {
	public InterfazCliente() {

        String usuario = JOptionPane.showInputDialog("Nick: ");

        JLabel n_nick= new JLabel("Nick: ");
        add(n_nick);
    
        nick = new JLabel();
        nick.setText(usuario);
        add (nick);
	
		JLabel texto=new JLabel("Online: ");
		
		add(texto);
        
        ip = new JComboBox();

        ip.addItem("Usuario1");

        ip.addItem("Usuario2");

        ip.addItem("Usuario3");


        add(ip);

        espaciochat = new JTextArea(12,20); //coordenadas ventana

        add (espaciochat);
	
		campo1=new JTextField(20);
	
		add(campo1);
	
		miboton=new JButton("Enviar");

        Enviar mievento= new Enviar();

        miboton.addActionListener(mievento);
		
		add(miboton);

        Thread mihilo=new Thread(this);

        mihilo.start();
		
	}

    @Override
    public void run() {
        try{
            ServerSocket servidorCliente = new ServerSocket(9089);

            Socket cliente;

            Envios paqueteRecibido;

            while (true){
                cliente=servidorCliente.accept();

                ObjectInputStream entradaDatos = new ObjectInputStream(cliente.getInputStream());

                paqueteRecibido=(Envios)entradaDatos.readObject();

                espaciochat.append("\n"+paqueteRecibido.getNick()+": "+paqueteRecibido.getMensaje());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private class Enviar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            //System.out.println(campo1.getText());

            espaciochat.append("\n" + campo1.getText());

            try {
                Socket misocket= new Socket("127.0.0.1", 9999);

                Envios datos = new Envios();

                datos.setNick(nick.getText());

                datos.setIp(ip.getSelectedItem().toString());

                datos.setMensaje(campo1.getText());

                ObjectOutputStream envio_datos= new ObjectOutputStream(misocket.getOutputStream());

                envio_datos.writeObject(datos);

                misocket.close();

            } catch (UnknownHostException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                System.out.println(e1.getMessage());
            }

        }

    }

		
	private JTextField campo1;

    private JComboBox ip;

    private JLabel nick;

    private JTextArea espaciochat;
	
	private JButton miboton;
	
}

class Envios implements Serializable{
    private String nick, ip, mensaje;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


}