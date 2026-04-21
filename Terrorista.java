import java.util.ArrayList;
import java.util.List;

public class Terrorista{
    int spawnRate;
    boolean gameOver;
    Thread osama;
    MyPanel panel;

    public List<Bomba> bombeAttive = new ArrayList<>();

    public Terrorista(int spawnRate, boolean gameOver, MyPanel panel){
        this.spawnRate = spawnRate;
        this.gameOver = gameOver;
        this.panel = panel;
        bombarda(spawnRate);
    }

    private synchronized void bombarda(int spawnRate){
        osama = new Thread(()->{
            while(!gameOver){
                int spawnX = (int)(Math.random()*10);
                int bombSpeed = (int)(Math.random()*10);
                int startingY = 3;
                
                spawnBomba(spawnX, startingY, bombSpeed);

                panel.repaint();

                try {
                    Thread.sleep(spawnRate);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        osama.start();
    }

    private synchronized void spawnBomba(int x, int startingY, int speed){
        Bomba bomba = new Bomba(x, startingY, speed);
        bombeAttive.add(bomba);
    }

    public synchronized void modalità(){
        if(gameOver){
            gameOver = false;
        }else{
            gameOver = true;
        }
    }
}