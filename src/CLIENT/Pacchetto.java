package CLIENT;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.paint.Color;


public class Pacchetto implements Serializable{
	private Color colore;
	private String type;
	private String destinatario;
	private String mittente;
	private String msg;
	private boolean flag;
	private String ora;
	private ArrayList<String> data;
	public Pacchetto(String type, String destinatario, String mittente, String msg, String ora, boolean flag) {
		this.destinatario = destinatario;
		this.type = type;
		this.msg = msg;
		this.mittente = mittente;
		this.flag = flag;
		this.ora = ora;
	}
	
	public Color getColore() {
		return colore;
	}

	public void setColore(Color colore) {
		this.colore = colore;
	}

	public Pacchetto(String type, String destinatario, String mittente, String msg, String ora, boolean flag, Color colore_here) {
		this.destinatario = destinatario;
		this.type = type;
		this.msg = msg;
		this.mittente = mittente;
		this.flag = flag;
		this.ora = ora;
		this.colore = colore_here;
	}
	
	public Pacchetto(String type, ArrayList<String> data, String mittente, String msg, boolean flag) {
		this.type = type;
		this.msg = msg;
		this.mittente = mittente;
		this.flag = flag;
		this.data = data;
	}
	
	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public ArrayList<String> getData() {
		return data;
	}

	public void setData(ArrayList<String> data) {
		this.data = data;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "Pacchetto [type=" + type + ", destinatario=" + destinatario + ", mittente=" + mittente + ", msg=" + msg
				+ ", flag=" + flag + ", data=" + data + "]";
	}
	
	
}
