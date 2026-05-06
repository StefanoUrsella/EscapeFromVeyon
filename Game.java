// Game.java
import java.awt.*;
import javax.swing.*;

public class Game {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainContainer;
    private static Menu menuPanel;
    private static MyPanel gamePanel;
    private static Terrorista terrorista;

    public static void main(String[] args) {
        frame = new JFrame("Falling Bombs");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centra la finestra sullo schermo

        // CardLayout ci permette di alternare tra Schermata Menu e Schermata Gioco
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Creiamo il menu passandogli l'azione da eseguire alla pressione di "GIOCA"
        menuPanel = new Menu(e -> startNewGame());
        mainContainer.add(menuPanel, "MENU");

        frame.add(mainContainer);
        cardLayout.show(mainContainer, "MENU");

        // Nota: setVisible(true) è l'equivalente moderno e non deprecato di frame.show()
        frame.setVisible(true);
    }

    private static void startNewGame() {
        // Creiamo un nuovo pannello di gioco per azzerare tutto (punteggio, player, liste)
        gamePanel = new MyPanel() {
            private boolean gameOverHandled = false;

            // Sovrascriviamo il metodo gameOver() per intercettare la fine del gioco
            @Override
            public void gameOver() {
                super.gameOver(); // Esegue la logica originale di MyPanel (ferma il player, mostra scritte)
                
                // Impedisce chiamate multiple simultanee in caso di collisioni concorrenti
                synchronized (this) {
                    if (gameOverHandled) return;
                    gameOverHandled = true;
                }

                // Fermiamo il thread del terrorista (Osama) per evitare leak di thread in background
                if (terrorista != null) {
                    terrorista.modalità(); // Imposta gameOver = true all'interno del Terrorista
                }

                // Aspettiamo 3 secondi sulla schermata di Game Over (per leggere lo score) prima di tornare al menu
                SwingUtilities.invokeLater(() -> {
                    Timer timer = new Timer(3000, ev -> {
                        returnToMenu();
                        ((Timer) ev.getSource()).stop(); // Ferma questo timer usa-e-getta
                    });
                    timer.setRepeats(false);
                    timer.start();
                });
            }
        };

        // Creiamo il terrorista associato al nuovo pannello di gioco
        terrorista = new Terrorista(1000, false, gamePanel);

        // Aggiungiamo il gioco al contenitore e lo mostriamo
        mainContainer.add(gamePanel, "GAME");
        cardLayout.show(mainContainer, "GAME");

        // IMPORTANTE: Richiediamo il focus della tastiera sul pannello di gioco per far muovere il cannone
        gamePanel.requestFocusInWindow();
    }

    private static void returnToMenu() {
        // Torna alla schermata del Menu principale
        cardLayout.show(mainContainer, "MENU");

        // Rimuoviamo il vecchio pannello di gioco per distruggere le risorse e i thread vecchi
        if (gamePanel != null) {
            mainContainer.remove(gamePanel);
            gamePanel = null;
        }
        terrorista = null;
    }
}