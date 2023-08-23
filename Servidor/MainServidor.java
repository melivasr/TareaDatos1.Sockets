package Servidor;

public class MainServidor {

	/**
	 * Crea una instancia de servidor
	 * @param args No se usan pero son necesarios
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Recepcion recepcion = new Recepcion();
		MiServidor miusuario=new MiServidor(recepcion);

        Thread mihilo2= new Thread(recepcion);

        mihilo2.start();

        Thread mihilo= new Thread(miusuario);

        mihilo.start();
			
	}
}