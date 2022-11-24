/*
 * Name : Game.java
 * Author : 이준형
 * Description : 서버 레벨에서 게임을 관리하는 클래스
 */

package horsetail.server;

import java.util.*;

public class Game {
	
	private ArrayList<String> words;
	private ArrayList<String> deadUsers;
	
	public boolean isOneChar(String w) {
		if(w.length() == 1) {
			return true;
		}
		
		return false;
	}
	
	public boolean isNotChain(String w) {
		int lastIdx = words.size() - 1;
		String lastWord = words.get(lastIdx);
		
		char lastWordChar = lastWord.charAt(lastWord.length() - 1);
		char newWordChar = w.charAt(w.length() - 1);
		
		if(!(lastWordChar == newWordChar)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isOverlap(String w) {
		if(words.contains(w)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isNotExist(String w) {
		// API와 연동해서 단어 체크 코드 추가
		// 단어가 있으면 false, 없으면 true 리턴
		
		return false;
	}
	
	public void addWord(String w) {
		words.add(w);
	}
	
	public String getDefinition() {
		String def = null;
		
		// API와 연동해서 단어 뜻 가져오는 코드 추가
		
		return def;
	}
	
	public void addDeadUser(String nick) {
		deadUsers.add(nick);
	}
	
	public int getDeadUserNum() {
		return deadUsers.size();
	}
	
	public String getDeadUserNick() {
		String dead = "";
		for(int i = 0; i < deadUsers.size(); i++) {
			dead += deadUsers.get(i) + "%";
		}
		
		return dead;
	}
	
	public void resetGame()
	{
		words.clear();
		deadUsers.clear();
	}
}
