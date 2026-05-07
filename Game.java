// Game.java
import java.awt.*;
import javax.swing.*;

public class Game {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainContainer;
    private static Menu menuPanel;
    private static MyPanel myPanel;
    private static Terrorista terrorista;

    public static void main(String[] args) {
        frame = new JFrame("Falling Bombs");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);//Centra la finestra sullo schermo

        cardLayout = new CardLayout();//"CardLayout"-->mette più schermate una sopra l'altra e ti permette di "sfogliarle"
        mainContainer = new JPanel(cardLayout);//il "JPanel" deve usare il "CardLayout"

        ///Creo il menu passandogli l'azione da eseguire alla pressione di "GIOCA" (li passo il "startNewGame" [per capire megli leggi i commenti in "Menu"])
        menuPanel = new Menu(e -> startNewGame());
        mainContainer.add(menuPanel, "MENU");//Aggiungo "menuPanel" a "mainContainer", ("MENU" è l'"Etichetta" del "menuPanel")

        frame.add(mainContainer);//Aggiungo il mio "JPanel" al "JFframe"
        cardLayout.show(mainContainer, "MENU");//Dentro al "cardLayout-->Di a "mainContainer"-->Mostra il coso con il nome "MENU"

        frame.setVisible(true);//faccio "setVisible" al "JFrame"
    }

    private static void startNewGame() {
        //Creo un "myPanel"
        myPanel = new MyPanel() {
            private boolean gameOverHandled = false;//Per gestire la possibilità di più "gameOver" simultanei

            // Sovrascriviamo il metodo gameOver() per intercettare la fine del gioco
            @Override
            public void gameOver() {
                super.gameOver();//Eseguo tutte le cose standard di "gameOver()"-->IN PIù-->Faccio anche il resto di questa roba
                
                //Per gestire più "gameOver" simultanei
                synchronized (this) {
                    if (gameOverHandled){//Se "gameOver" è già stato "Gestito"-->returna e non fare altro
                        return;
                    }
                    gameOverHandled = true;//sennò-->metti che il "GameOver" è stato "Gestito"
                }

                //Se il Terrorista sta ancora andando-->lo fermo
                if (terrorista != null) {
                    terrorista.modalità();//Metto il gameOver dentro a Terrorista
                }

                //Aspetta 3 secondi sulla schermata di Game Over per dare tempo di leggere il punteggio-->poi torna al Menu   (Gemini)
                SwingUtilities.invokeLater(() -> {
                    Timer timer = new Timer(3000, ev -> {//Dopo i "3000" milliseondi-->esegue il codice della "Lamba Expression" ["ev"]
                        returnToMenu();//Ritorna al menu iniziale
                    });
                    timer.setRepeats(false);//Il timer NON deve ripetersi più di Una volta
                    timer.start();//Fa partire il Timer
                });
            }
        };

        //Creo thread Terrorista
        terrorista = new Terrorista(1000, false, myPanel);

        mainContainer.add(myPanel, "GAME");//dentro al "JPanel"-->aggiungo il myPanel, ci metto l'"Etichetta" ["GAME"]
        cardLayout.show(mainContainer, "GAME");//Dentro al "cardLayout-->Di a "mainContainer"-->Mostra il coso con il nome "GAME"

        myPanel.requestFocusInWindow();//Ora gli input da tastiera vanno nella classe "myPanel" (il vecchio sistema di farlo direttamente in "MyPanel" non va più perché lo chiama qundo "MyPanel" viene costruito  non è anchora in uso)   (Gemini)
    }

    private static void returnToMenu() {
        //Torno alla "card" con l'etichetta "MENU"
        cardLayout.show(mainContainer, "MENU");

        //Cancello il vecchio "myPanel" (così da togliere thread rimasti che fanno robe per i cavoli loro)
        if (myPanel != null) {
            mainContainer.remove(myPanel);
            myPanel = null;
        }
        terrorista = null;
    }
}