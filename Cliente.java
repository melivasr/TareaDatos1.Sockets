import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MiCliente miusuario=new MiCliente();
		
		miusuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MiCliente extends JFrame{
	
	public MiCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMiCliente micanvas=new LaminaMiCliente();
		
		add(micanvas);
		
		setVisible(true);
		}
	
}

class LaminaMiCliente extends JPanel{
	
	public LaminaMiCliente(){
	
		JLabel texto=new JLabel("CLIENTE");
		
		add(texto);
	
		campo1=new JTextField(20);
	
		add(campo1);
	
		miboton=new JButton("Enviar");

        EnviarTexto mievento= new EnviarTexto();

        miboton.addActionListener(mievento);
		
		add(miboton);
		
	}

    private class EnviarTexto implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            //System.out.println(campo1.getText());
            try {
                Socket misocket= new Socket("127.0.0.1", 9999);

                DataOutputStream salida_datos = new DataOutputStream(misocket.getOutputStream());

                salida_datos.writeUTF(campo1.getText());

                salida_datos.close();

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
	
	private JButton miboton;
	
}