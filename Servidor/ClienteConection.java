package Servidor;

import java.net.Socket;

class ClienteConection implements Runnable {
	String nick, ip, mensaje;

    Socket socket;

	public ClienteConection(String nick, String ip, Socket socket);

    this.nick = nick;

    this.ip = ip;

    this.socket = socket;

		
		}
	

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("nuevo cliente conectado");

        try {

            while(true){

            
            }

            //

            }
            }

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
