import java.awt.Rectangle;

public class PowerUp implements Runnable{//Serve per usare "run()"   (Gemini)
    private int x;
    private int y;
    private int speed;
    private int maxY;
    private MyPanel panel;
    private boolean attivo;

    public PowerUp(int x, int y, int speed, int maxY, MyPanel panel){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.maxY = maxY;
        this.panel = panel;
        attivo = true;

        new Thread(this).start();//Creo un thread di questo oggetto-->lo Starto   (Gemini)
    }

    @Override
    public void run() {//Esegue il thread in parallelo al resto della classe   (Gemini)
        while(attivo == true && y < maxY){
            y += speed;

            try{
                Thread.sleep(100);//Tempo che aspetta prima di muoversi di nuovo
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        attivo = false;//Sono uscito dal while-->la bomba ha toccato terra-->fermo questo powerup
        panel.powerUpAttivi.remove(this);//Sono uscito dal while-->il powerup ha toccato terra-->tolgo questo powerup
    }

    public void distruggi(){
        attivo = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    //Crea la "Hitbox" del powerup   (Gemini)
    public Rectangle getBounds(){//Resituisce un Rettangolo-->poi vedo se si intersecano
        return new Rectangle(x, y, 40, 40);
    }
}