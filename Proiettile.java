import java.awt.Rectangle;

public class Proiettile implements Runnable{//Serve per usare "run()"   (Gemini)
    private int x;
    private int y;
    private int speed;
    private int minY;
    private MyPanel panel;
    private boolean attivo;

    public Proiettile(int x, int y, int speed, int minY, MyPanel panel){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.minY = minY;
        this.panel = panel;
        attivo = true;

        new Thread(this).start();//Creo un thread di questo oggetto-->lo Starto   (Gemini)
    }

    @Override
    public void run() {//Esegue il thread in parallelo al resto della classe   (Gemini)
        while(attivo == true && y > minY){
            y -= speed;

            try{
                Thread.sleep(100);//Tempo che aspetta prima di muoversi di nuovo
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        attivo = false;//Sono uscito dal while-->il proiettile è uscito dalla mappa-->fermo questo proiettile
        panel.proiettiliAttivi.remove(this);//Sono uscito dal while-->il proiettile è uscito dalla mappa-->tolgo questo proiettile
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

    //Crea la "Hitbox" del Proiettile   (Gemini)
    public Rectangle getBounds(){//Resituisce un Rettangolo-->poi vedo se si intersecano
        return new Rectangle(x, y, 40, 40);
    }
}