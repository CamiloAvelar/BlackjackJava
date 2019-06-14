import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.*;

public class Interface extends JFrame {

    int width = 1280;
    int height = 800;

    private static String returnButton = "none";
    private static String playAgain = "none";

    Board board = new Board();

    Color backgroundColor = new Color(39, 119,20);
    Color buttonColor = new Color(204,204,0);

    Font buttonFont = new Font("Times New Roman", Font.PLAIN, 30);

    JButton pegar = new JButton();
    pegarAction pegarAct = new pegarAction();

    JButton ficar = new JButton();
    ficarAction ficarAct = new ficarAction();

    JButton abandonar = new JButton();
    abandonarAction abandonarAct = new abandonarAction();

    JButton dobrar = new JButton();
    dobrarAction dobrarAct = new dobrarAction();

    JButton sim = new JButton();
    simAction simAct = new simAction();

    JButton nao = new JButton();
    naoAction naoAct = new naoAction();


    public Interface() {
        this.setSize(width, height + 29);
        this.setTitle("Blackjack");
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(board);
        this.setLayout(null);

        pegar.addActionListener(pegarAct);
        pegar.setBounds(400, 400,150,80);
        pegar.setFont(buttonFont);
        pegar.setBackground(buttonColor);
        pegar.setText("PEGAR");
        board.add(pegar);

        ficar.addActionListener(ficarAct);
        ficar.setBounds(550, 400,150,80);
        ficar.setFont(buttonFont);
        ficar.setBackground(buttonColor);
        ficar.setText("FICAR");
        board.add(ficar);

        abandonar.addActionListener(abandonarAct);
        abandonar.setBounds(700, 400,150,80);
        abandonar.setFont(buttonFont);
        abandonar.setBackground(buttonColor);
        abandonar.setText("ABANDONAR");
        board.add(abandonar);

        dobrar.addActionListener(dobrarAct);
        dobrar.setBounds(850, 400,150,80);
        dobrar.setFont(buttonFont);
        dobrar.setBackground(buttonColor);
        dobrar.setText("DOBRAR");
        board.add(dobrar);

        sim.addActionListener(simAct);
        sim.setBounds(1000, 400,150,80);
        sim.setFont(buttonFont);
        sim.setBackground(buttonColor);
        sim.setText("SIM");
        board.add(sim);

        nao.addActionListener(naoAct);
        nao.setBounds(1150, 400,150,80);
        nao.setFont(buttonFont);
        nao.setBackground(buttonColor);
        nao.setText("NAO");
        board.add(nao);
    }

    public void disablePegar() {
        pegar.setEnabled(false);
    }

    public String getReturnButton () {
        return Interface.returnButton;
    }

    public void resetReturn() {
        Interface.returnButton = "none";
    }

    public void resetPlayAgain() {
        Interface.playAgain = "none";
    }

    public String playAgain () {
        return Interface.playAgain;
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);
        }
    }

    public class pegarAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Interface.returnButton = "pegar";
        }
    }

    public class ficarAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Interface.returnButton = "ficar";
        }
    }

    public class abandonarAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Interface.returnButton = "abandonar";
        }
    }

    public class dobrarAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Interface.returnButton = "dobrar";
        }
    }

    public class simAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Interface.playAgain = "S";
        }
    }

    public class naoAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Interface.playAgain = "N";
        }
    }

}
