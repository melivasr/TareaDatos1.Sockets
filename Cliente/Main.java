
package Cliente;

import javax.swing.JFrame;

public class Main {

	/**
	 * Crea una instancia del cliente(chat)
	 * @param args No se usan pero son necesarios
	 */
	public static void main(String[] args) {

        MiCliente miusuario=new MiCliente();
		
		miusuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}


