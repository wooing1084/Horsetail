package horsetailserver;

import Util.Protocol;

import java.util.TimerTask;

public class GameTimer implements Runnable {
    private int time = 0;
    Game game;
    public static final int timeout = 20;

    public GameTimer(Game gr){
        game = gr;
    }

    public synchronized int GetTime()
    {
        return time;
    }
    public void SetTime(int t){
        time = t;
    }

    public void Reset(){
        time = 0;
    }

    @Override
    public void run() {
        time++;

        game.GetGameRoom().BroadCast(Protocol.NOWTIME + "//" +time);

    }
}
