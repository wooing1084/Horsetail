package horsetailserver;

import Util.Protocol;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game extends Thread{
    private ArrayList<RecvThread> _userList;
    private ArrayList<String> words;
    private String inputWord = "";
    private int turnUserIdx = -1;

    private GameTimer timer;
    private GameRoom gameRoom;



    public Game(GameRoom gr){
        gameRoom = gr;
        _userList = gr.GetUserList();
        words = new ArrayList<String>();
        timer = new GameTimer(this);
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

        ScheduledExecutorService timerService = Executors.newSingleThreadScheduledExecutor();
        timerService.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);

        System.out.println(gameRoom.GetRoomID() + ": GameStart");

        while(true){
            if(timer.GetTime() >= timer.timeout){
                //현재유저 패배 +1
                //나머지 유저 승리 +1 레이팅은 알아서
                for(int i =0;i<_userList.size();i++){
                    User u = _userList.get(i).GetUser();
                    String q1;
                    if(i == turnUserIdx){
                        q1 = "update user set loss = " + (u.getLoses() + 1) +
                                " where id = \"" + u.getId() + "\";";
                    }
                    else{
                        q1 = "update user set wins = " + (u.getWins() + 1) +
                                " where id = \"" + u.getId() + "\";";
                    }

                    SQLMethods.ExecuteUpdate(q1);
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

            gameRoom.BroadCast(Protocol.SENDWORD_OK + "//" + inputWord);
            //모든조건이 걸리지 않았다면 목록에 추가하고 다음턴으로 넘기자!
            addWord(inputWord);
            inputWord = "";

            NextTurn();
        }

        System.out.println(gameRoom.GetRoomID() + ": GameEnd");
        gameRoom.BroadCast(Protocol.GAMEEND);
        timerService.shutdown();
        gameRoom.EndGame();
    }

    public GameRoom GetGameRoom(){
        return gameRoom;
    }

    public void SetGameRoom(GameRoom gr) {
        gameRoom = gr;
    }

    //한 글자인지
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
