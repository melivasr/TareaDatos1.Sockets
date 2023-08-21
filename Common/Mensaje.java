package Common;

import java.io.Serializable;

/**
 * Clase para los mensajes y la información que contienen, ademas de añadir su getter y setter a cada dato
 */
public class Mensaje implements Serializable{

    //tipo posibles opciones: comando, mensaje
    private String remitente, destinatario, mensaje, tipo;

    /**
     * Contructor del mensaje
     * @param remitente Quien lo envia
     * @param destinatario Para quien
     * @param mensaje Texto del mensaje
     * @param tipo Comando o texto
     */
    public Mensaje(String remitente, String destinatario, String mensaje, String tipo){
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRemitente() {
        return this.remitente;
    }

    public void setRemitente(String nick) {
        this.remitente = nick;
    }

    public String getDestinatario() {
        return this.destinatario;
    }

    public void setDestinatario(String nick) {
        this.destinatario = nick;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    
}
