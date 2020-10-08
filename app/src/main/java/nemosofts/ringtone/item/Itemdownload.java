package nemosofts.ringtone.item;

import java.io.Serializable;

/**
 * Created by thivakaran
 */
public class Itemdownload implements Serializable{

	private String id, mp3, title, duration;


	public Itemdownload(String id, String mp3, String title, String duration) {
		this.id = id;
		this.mp3 = mp3;
		this.title = title;
		this.duration = duration;

	}

	public String getId() {
		return id;
	}

	public String getMp3() {
		return mp3;
	}

	public String getTitle() {
		return title;
	}

	public String getDuration(){
		return duration;
	}
}