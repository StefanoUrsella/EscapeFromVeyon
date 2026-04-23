import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public  class MyPanel extends JPanel{

    //private Image imgBomba; NON USO PIù L'IMMAGINE

    public List<Bomba> bombeAttive = new java.util.concurrent.CopyOnWriteArrayList<>();//Invece che cambiare dierettamente il valore della "ArrayList<>()"-->Fa una copia, Modifica la copia, Sostituisce l'originale con la Copia-->Sennò con i Tanti thread si incastrerebbe   (Gemini)
    public List<Proiettile> proiettiliAttivi = new java.util.concurrent.CopyOnWriteArrayList<>();
    public List<PowerUp> powerUpAttivi = new java.util.concurrent.CopyOnWriteArrayList<>();

    public Giocatore giocatore = new Giocatore(0, 500, 5, this);

    private int repaintRate = 50;


    //Variabili proiettili
    private int bulletSpeed = 10;
    private long istanteUltimoSparo = 0;
    private int standardFirerate = 500;
    private int CIWSfirerate = 10;
    private int cooldownSparare = standardFirerate;
    private long istantePresoPowerup = 0;
    private int durataPowerup = 4000;

    public MyPanel(){
        /*Toolkit tk = Toolkit.getDefaultToolkit();
        imgBomba = tk.getImage("bomba.png");
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(imgBomba, WIDTH);
        try {
            mt.waitForAll();
        } catch (InterruptedException e) {

        }*/// NON USO PIù L'IMMAGINE
        

        //Thread che fà repaint ogni tot tempo-->Se lo metto dentro alle bombe avrei TROPPI thread che fanno repaint-->Si incarterebbe tutto   (Gemini)
        Thread austrianRepainter = new Thread(()->{
            while(true){

                controllareCollisioni();

                repaint();

                try {
                    Thread.sleep(repaintRate);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        austrianRepainter.start();

        setFocusable(true);//Ora il JPanel può anche dare input   (Gemini)
        requestFocusInWindow();//Ora gli input da tastiera vanno a questa classe   (Gemini)

        //Per leggere i tasti premuti, per controllare il player
        addKeyListener(new KeyAdapter() {//Non devo più fare l'"@Override" di TUTTI i metodi di "addKeyListener()", sennò dovrei anche farlo di "keyTyped()" (che è inutile)   (Gemini)
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    giocatore.setSinistra(true);
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    giocatore.setDestra(true);
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    long tempoAttuale = System.currentTimeMillis();//Momento di adesso   (Gemini)

                    //Per impostare il Firerate
                    if(tempoAttuale - istantePresoPowerup <= durataPowerup){//Se ho preso un powerup meno di "durataPowerup" fa-->uso "CIWSfirerate"-->sennò uso "standardFirerate"
                        cooldownSparare = CIWSfirerate;
                    }else{
                        cooldownSparare = standardFirerate;
                    }

                    if(tempoAttuale - istanteUltimoSparo >= cooldownSparare){//Se il "tempoAttuale" - l'orario in cui ho sparato l'ultima volta-->sono minori del Firerate-->posso sparare   (Gemini)
                        Proiettile proiettile = new Proiettile(giocatore.getX(), giocatore.getY(), bulletSpeed, 0, MyPanel.this);//Se metto semplicemente "this"-->capisce "addKeyListener" invece che "MyPanel"
                        proiettiliAttivi.add(proiettile);

                        istanteUltimoSparo = tempoAttuale;//Imposto il nuove "istanteUltimoSparo" all'ultimo ostante in cui ho sparato
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    giocatore.setSinistra(false);
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    giocatore.setDestra(false);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Disegnare Bombe
        for (Bomba b : bombeAttive){
            //g.drawImage(imgBomba, b.getX(), b.getY(), null); NON USO PIù L'IMMAGINE

            g.setColor(Color.RED);
            g.fillOval(b.getX(), b.getY(), 40, 40);
        }

        //Disegare powerup
        for (PowerUp pw : powerUpAttivi){
            g.setColor(Color.BLUE);
            g.fillOval(pw.getX(), pw.getY(), 40, 40);
        }

        //Disegnare Player
        /*g.setColor(Color.BLACK);
        g.fillRect(giocatore.getX(), giocatore.getY(), 40, 40);*/
        buildingIronDome(g, giocatore.getX(), giocatore.getY());

        //Disegnare Proiettili
        for (Proiettile p : proiettiliAttivi){
            g.setColor(Color.YELLOW);
            g.fillRect(p.getX(), p.getY()-40, 10, 30);
        }
    }

    public void buildingIronDome(Graphics g, int x, int y) {
        int size = 40;//Grandezza del CIWS
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x - size / 2, y, size, size);//"x" è pari a "x-size/2" perché così-->è centrato

        int silosWidth = size / 2;
        int silosHeight = size / 2;

        g.setColor(Color.BLACK);

        //Disegnare Autocannon
        g.fillRect((x - silosWidth / 2) + 8, (y - silosHeight / 2) - 30 , silosWidth/4, silosHeight*2);
        
        g.setColor(Color.LIGHT_GRAY);

        //Disegnare Silos
        g.fillRect(x - silosWidth / 2, y - silosHeight / 2, silosWidth, silosHeight);

        //Disegnare la Cupola
        g.fillArc(x - silosWidth / 2, y - silosHeight, silosWidth, silosHeight, 0, 180);//Width e Height servono per delimitare la grandezza dell'Arco
    }

    //Controllo se i "rettangoli" delle Hitbox di proiettili e bombe si intersecano-->se si-->cancello sia bomba che proiettile
    private void controllareCollisioni(){
        for (Proiettile p : proiettiliAttivi){
            for(Bomba b : bombeAttive){
                if(p.getBounds().intersects(b.getBounds())){
                    p.distruggi();
                    b.distruggi();
                    proiettiliAttivi.remove(p);
                    bombeAttive.remove(b);
                    break;
                }
            }

            for(PowerUp pw : powerUpAttivi){
                if(p.getBounds().intersects(pw.getBounds())){
                    p.distruggi();
                    pw.distruggi();
                    proiettiliAttivi.remove(p);
                    powerUpAttivi.remove(pw);

                    istantePresoPowerup = System.currentTimeMillis();

                    break;
                }
            }
        }
    }
}