package Servidor;

public class MainServidor {

	/**
	 * Crea una instancia de servidor
	 * @param args No se usan pero son necesarios
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MiServidor miusuario=new MiServidor();

        Thread mihilo= new Thread(miusuario);

        mihilo.start();
			
	}
}