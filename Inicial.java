
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Inicial implements ActionListener {
    private JFrame frame=new JFrame();
    private JButton crearCliente= new JButton("Crear chat");

    private JButton verUsuarios= new JButton("Ver usuarios conectados");

    private JLabel nombreChat= new JLabel("Tell'em Chat",JLabel.CENTER);

    private JTextField nickname = new JTextField(5);

    ArrayList<String> usuarios = new ArrayList<String>();
    public Inicial(){
        nickname.setBounds(65,50,150,40);
        nickname.setText("Nombre de usuario");
        frame.add(nickname);

        nombreChat.setBounds(65,10,150,40);
        nombreChat.setForeground(Color.green);
        nombreChat.setFont(new Font("Verdana", Font.PLAIN, 15));
        frame.add(nombreChat);

        crearCliente.setBounds(65,110,150,40);
        crearCliente.setFocusable(false);
        crearCliente.addActionListener(this);

        crearCliente.setBounds(65,210,150,40);
        crearCliente.setFocusable(false);
        crearCliente.addActionListener(this);

        frame.add(crearCliente);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(650,200,300,300);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==crearCliente){
            MiCliente miusuario=new MiCliente(nickname.getText());
            miusuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            usuarios.add(nickname.getText());
        }else if(e.getSource()==verUsuarios){
            System.out.println(usuarios);
        }
    }

}
