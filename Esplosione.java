import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

public class Esplosione{
    int x;
    int y;
    MyPanel panel;
    private long istanteEsplosione = 0;
    private int durataEsplosione = 20;
    Thread binLaden;
    boolean attiva = true;

    public Esplosione(int x, int y, MyPanel panel){
        this.x = x;
        this.y = y;
        this.panel = panel;

        istanteEsplosione = System.currentTimeMillis();

        binLaden = new Thread(()->{
            while(attiva){
                long momentoAttuale = System.currentTimeMillis();

                if(momentoAttuale - istanteEsplosione >= durataEsplosione){
                    panel.esplosoniAttive.remove(this);
                    attiva = false;
                }
            }
        });

        binLaden.start();
    }

    public synchronized Rectangle getBounds(){
        return new Rectangle(x-100, y-60, 300, 300);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}