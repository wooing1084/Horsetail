/*
 * Name : User.java
 * Author : 이준형
 * Description : 유저의 개별 정보를 저장하는 클래스
 */

package horsetailserver;

public class User {
	
	private String nick;
	private String id;
	private String pw;
	private int rating;
	private int wins;
	private int loses;
	private boolean alive;
	private boolean turn;

	public User() {
		this("", "", 0, 0, 0);
		this.alive = true;
		this.turn = false;
	}
	
	public User(String nickname, String password, int ratingNum, int winsNum, int losesNum) {
		super();
		this.nick = nickname;
		this.pw = password;
		this.rating = ratingNum;
		this.wins = winsNum;
		this.loses = losesNum;
		this.alive = true;
		this.turn = false;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String n) {
		this.nick = n;
	}
	public String getId(){	return id;	}
	public void setId(String _id){	id = _id;	}
	
	public String getPw() {
		return pw;
	}
	
	public void setPw(String p) {
		this.pw = p;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int r) {
		this.rating = r;
	}
	
	public boolean getAlive() {
		return this.alive;
	}
	
	public void setAlive(boolean a) {
		this.alive = a;
	}
	
	public boolean getTurn() {
		return this.turn;
	}
	
	public void setTurn(boolean t) {
		this.turn = t;
	}
	
	public int getWins() {
		return this.wins;
	}
	
	public void setWins(int w) {
		this.wins = w;
	}
	
	public int getLoses() {
		return this.loses;
	}
	
	public void setLoses(int l) {
		this.loses = l;
	}
	
	public void initialize() {
		this.id = "";
		this.nick = "";
		this.pw = "";
		this.rating = 0;
		this.alive = true;
		this.turn = false;
		this.wins = 0;
		this.loses = 0;
	}
}
