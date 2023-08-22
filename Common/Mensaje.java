package Common;

import java.io.Serializable;

/**
 * Clase para los mensajes y la información que contienen, ademas de añadir su getter y setter a cada dato
 */
public class Mensaje implements Serializable{

    //tipo posibles opciones: comando, mensaje
    /**
     * Representa la información contenida en el mensaje
     */
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

    /**
     * @return Devuelve el tipo de mensaje
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * @param tipo El nuevo tipo de mensaje a cambiar
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /**
     * @return Devuelve de quien es el mensaje
     */
    public String getRemitente() {
        return this.remitente;
    }

    /**
     * @param nick Nuevo nombre de usuario de quien envia el mensaje
     */
    public void setRemitente(String nick) {
        this.remitente = nick;
    }
    /**
     * @return Devuelve para quien es el mensaje
     */
    public String getDestinatario() {
        return this.destinatario;
    }
    /**
     * @param nick Nuevo nombre de usuario de quien recibe el mensaje
     */
    public void setDestinatario(String nick) {
        this.destinatario = nick;
    }
    /**
     * @return Devuelve el texto del mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje El nuevo texto del mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    
}
