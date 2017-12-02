package CLIENT;

import java.util.ArrayList;

public class Chat {
	private String name;
	private ArrayList<Pacchetto> chat = new ArrayList<Pacchetto>();
	private boolean blocked = false;
	
	public Chat(String name, ArrayList<Pacchetto> chat, boolean blocked) {
		this.name = name;
		this.chat = chat;
		this.blocked = blocked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Pacchetto> getChat() {
		return chat;
	}

	public void setChat(ArrayList<Pacchetto> chat) {
		this.chat = chat;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	
	
}
