import java.awt.Rectangle;

public class Esplosivo implements Runnable{
    private int x;
    private int y;
    private int speed;
    private int maxY;
    private MyPanel panel;
    private boolean attivo;

    public Esplosivo(int x, int y, int speed, int maxY, MyPanel panel){
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
            //panel.repaint();//Aggiorno il "MyPanel"

            try{
                Thread.sleep(100);//Tempo che aspetta prima di muoversi di nuovo
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }

        }

        if(y > maxY){
            EsplosioneNaturale esplosioneNaturale = new EsplosioneNaturale(x, y, panel);
            panel.esplosoniNaturali.add(esplosioneNaturale);
            panel.gameOver();
        }

        attivo = false;//Sono uscito dal while-->la bomba ha toccato terra-->fermo questa bomba
        panel.esplosiviAttivi.remove(this);//Sono uscito dal while-->l'esplosivo ha toccato terra-->tolgo questo esplosivo
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

    //Crea la "Hitbox" dell'Esplosivo   (Gemini)
    public Rectangle getBounds(){//Resituisce un Rettangolo-->poi vedo se si intersecano
        return new Rectangle(x, y+20, 40, 40);
    }
}