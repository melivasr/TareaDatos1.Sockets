package Servidor;

public class MainServidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MiServidor miusuario=new MiServidor();

        Thread mihilo= new Thread(miusuario);

        mihilo.start();
			
	}
}