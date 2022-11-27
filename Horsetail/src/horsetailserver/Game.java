package horsetailserver;

import Util.Protocol;

import java.util.ArrayList;

public class Game extends Thread{
    private ArrayList<RecvThread> _userList;
    private ArrayList<String> words;
    private String inputWord = "";
    private int turnUserIdx = -1;

    private Timer timer;
    private GameRoom gameRoom;


    private static final int timeout = 20;

    public Game(GameRoom gr){
        gameRoom = gr;
        _userList = gr.GetUserList();
        words = new ArrayList<String>();
        timer = new Timer();
    }

    //u 유저가 게임에 w란 단어를 전송했다.
    public void EnterWord(String w, RecvThread u){
        if(_userList.get(turnUserIdx).GetUser().getId().compareTo(
                u.GetUser().getId()) != 0)
        {
            //본인턴이 아님
            u.SendMessage(Protocol.SENDWORD_NO);
            return;
        }

        inputWord = w;
    }

    public void NextTurn(){
        turnUserIdx++;
        turnUserIdx %= _userList.size();

        gameRoom.BroadCast(Protocol.ANOTHERTURN + "//" + _userList.get(turnUserIdx).GetUser().getId());
        timer.Reset();
    }

    //게임 순서에따른 로직 구현 부분
    public void run(){
        turnUserIdx = 0;

        while(true){
            if(timer.GetTime() >= timeout){
                //현재유저 패배 +1
                //나머지 유저 승리 +1 레이팅은 알아서
                for(int i =0;i<_userList.size();i++){
                    User u = _userList.get(i).GetUser();
                    String q1;
                    if(i == turnUserIdx){
                        q1 = "update user set loss = " + u.getLoses() + 1 +
                                " where id = \"" + u.getId() + "\";";
                    }
                    else{
                        q1 = "update user set wins = " + u.getWins() + 1 +
                                " where id = \"" + u.getId() + "\";";
                    }
                }
                break;
            }

            //입력이 오지 않음
            if(inputWord.compareTo("") == 0)
                continue;

            //BroadCast한 이유는 특정 유저가 어떤 단어를 입력했는데 왜 틀렸는지 알려주기 위함
            if (isOneChar(inputWord)) {
               gameRoom.BroadCast(Protocol.WORDONLYONECHAR + "//" + inputWord);
               inputWord = "";
               continue;
            }
			else if (isNotChain(inputWord)) {
                gameRoom.BroadCast(Protocol.WORDNOTCHAIN + "//" + inputWord);
                inputWord = "";
                continue;
            }
			else if (isOverlap(inputWord)) {
                gameRoom.BroadCast(Protocol.WORDOVERLAP + "//" + inputWord);
                inputWord = "";
                continue;
            }
			else if (isNotExist(inputWord)) {
                gameRoom.BroadCast(Protocol.WORDNOTEXIST + "//" + inputWord);
                inputWord = "";
                continue;
            }

            //모든조건이 걸리지 않았다면 목록에 추가하고 다음턴으로 넘기자!
            addWord(inputWord);
            inputWord = "";

            NextTurn();
        }
    }
//
//			else {
//        gr.addWord(reqs[1]);
//        SendMessage(Protocol.SENDWORD_OK + "//" + user.getNick());
//
//        int idx = gr.GetUserList().indexOf((Object) user.getNick());
//        int i = 1;
//        while (gr.GetUserList().get((idx + i) % gr.GetUserList().size()).user.getAlive()) {
//            i++;
//        }
//        gr.GetUserList().get((idx + i) % gr.GetUserList().size()).user.setTurn(true);
//        this.user.setTurn(false);
//
//        SendMessage(Protocol.YOURTURN + "//" + gr.GetUserList().get((idx + i) % gr.GetUserList().size()).user.getNick());
//    }

//    		else if (reqs[0].compareTo(Protocol.SENDDEF) == 0) {
//        GameRoom gr = RoomManager.GetRoomList().get(nowRoomIndex);
//        String def = gr.getDefinition();
//        SendMessage(Protocol.SENDDEF_OK + "//" + def);
//    }
//
//		else if (reqs[0].compareTo(Protocol.TIMEOUT) == 0) {
//        user.setAlive(false);
//        SendMessage(Protocol.TIMEOUT_OK + "//" + user.getNick());
//    }
//
//
//		else if (reqs[0].compareTo(Protocol.GAMEOUT) == 0) { // 남은 기회가 0이 되면 request
//        GameRoom gr = RoomManager.GetRoomList().get(nowRoomIndex);
//        user.setAlive(false);
//        gr.addDeadUser(user.getNick());
//        SendMessage(Protocol.GAMEOUT_OK + "//" + user.getNick());
//
//        if (gr.GetUserList().size() == gr.getDeadUserNum() - 1) {
//            gr.BroadCast(Protocol.GAMEEND + "//게임이 종료되었습니다.//" + gr.getDeadUserNick());
//        }
//    }


    //한글자인지
    public boolean isOneChar(String w) {
        if(w.length() == 1) {
            return true;
        }

        return false;
    }
    
    //연결되는지
    public boolean isNotChain(String w) {
        if(words.size() == 0)
            return false;
        int lastIdx = words.size() - 1;
        String lastWord = words.get(lastIdx);

        char lastWordChar = lastWord.charAt(lastWord.length() - 1);
        char newWordChar = w.charAt(0);

        return lastWordChar != newWordChar;
    }

    //중복단어인지
    public boolean isOverlap(String w) {
        for(int i =0;i<words.size();i++){
            if(w.compareTo(words.get(i)) == 0)
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
        String def = "";

        // API와 연동해서 단어 뜻 가져오는 코드 추가

        return def;
    }

    public void resetGame()
    {
        words.clear();
    }

}
