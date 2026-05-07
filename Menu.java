// Menu.java
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu extends JPanel {

    public Menu(ActionListener onStartGame) {//Riceve l' "onStartGame"-->"onStartGame" corrisponde a far partire "startNewGame" su "Game"
        //"GridBagLayout"-->coso per indicare la posizione degli elementi
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();//"gbc"-->per a osizione degli elemnti nel "GridBagLayout"
        gbc.gridx = 0;//"x" del "GridBagLayout"
        gbc.gridy = 0;//"y" del "GridBagLayout"
        gbc.insets = new Insets(20, 20, 20, 20);//Spazio tra i componenti

        //Titolo del Gioco
        JLabel title = new JLabel("FALLING BOMBS");
        title.setFont(new Font("TimesRoman", Font.BOLD, 50));
        title.setForeground(Color.RED);
        add(title, gbc);//aggiungo la cosa con i valori del "gbc"

        //Pulsante Gioca
        gbc.gridy = 1;//metto la "y" del "gbc" a-->1
        JButton playButton = new JButton("GIOCA");
        playButton.setFont(new Font("TimesRoman", Font.BOLD, 24));
        playButton.setBackground(Color.DARK_GRAY);
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.addActionListener(onStartGame);//Ascolta le cose-->se riceve-->fa partire "startNewGame" su "Game"
        add(playButton, gbc);//aggiungo la cosa con i valori del "gbc"

        //Pulsante Esci
        gbc.gridy = 2;//metto la "y" del "gbc" a-->2
        JButton exitButton = new JButton("ESCI");
        exitButton.setFont(new Font("TimesRoman", Font.BOLD, 24));
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));//Chiude il programma
        add(exitButton, gbc);//aggiungo la cosa con i valori del "gbc"
    }
}