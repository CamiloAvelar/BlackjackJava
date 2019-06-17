import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.*;

public class Interface extends JFrame {

    int width = 1280;
    int height = 800;

    int gridX = 50;
    int gridY = 50;
    int gridW = 900;
    int gridH = 400;

    private static String playerName;

    private static boolean showTotal = false;
    private static int myTotal;
    private static int dealerTotal;

    private static int meuSaldo;
    private static int dealerSaldo;

    private static int aposta;

    private static Carta[] myCards;
    private static Carta[] dealerCards;
    private static int myCardsQtd;
    private static int dealerCardsQtd;
    private static boolean displayCards = false;
    private static boolean showFirstCard = false;

    private static String returnButton = "none";
    private static String playAgain = "none";
    private static boolean showPlayAgain = false;

    Board board = new Board();

    ArrayList<Message> Log = new ArrayList<Message>();

    Color backgroundColor = new Color(39, 119,20);
    Color buttonColor = new Color(204,204,0);
    Color cDealer = Color.red;
    Color cPlayer = new Color(25,55,255);

    Font fontCard = new Font("Times New Roman", Font.PLAIN, 40);
    Font buttonFont = new Font("Times New Roman", Font.PLAIN, 30);
    Font fontLog = new Font("Times New Roman", Font.ITALIC, 30);

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

    int[] polyX = new int[4];
    int[] polyY = new int[4];

    //card spacing and dimensions
    int spacing = 10;
    int rounding = 10;
    int tCardW = (int) gridW/6;
    int tCardH = (int) gridH/2;
    int cardW = tCardW - spacing*2;
    int cardH = tCardH - spacing*2;


    public Interface() {
        this.setSize(width, height + 29);
        this.setTitle("Blackjack");
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(board);
        this.setLayout(null);

        pegar.addActionListener(pegarAct);
        pegar.setBounds(970, 530,300,50);
        pegar.setFont(buttonFont);
        pegar.setBackground(buttonColor);
        pegar.setText("PEGAR");
        board.add(pegar);

        ficar.addActionListener(ficarAct);
        ficar.setBounds(970, 600,300,50);
        ficar.setFont(buttonFont);
        ficar.setBackground(buttonColor);
        ficar.setText("FICAR");
        board.add(ficar);

        abandonar.addActionListener(abandonarAct);
        abandonar.setBounds(970, 670,300,50);
        abandonar.setFont(buttonFont);
        abandonar.setBackground(buttonColor);
        abandonar.setText("ABANDONAR");
        board.add(abandonar);

        dobrar.addActionListener(dobrarAct);
        dobrar.setBounds(970, 740,300,50);
        dobrar.setFont(buttonFont);
        dobrar.setBackground(buttonColor);
        dobrar.setText("DOBRAR");
        board.add(dobrar);

        sim.addActionListener(simAct);
        sim.setBounds(1000, 510,120,60);
        sim.setFont(buttonFont);
        sim.setBackground(buttonColor);
        sim.setText("SIM");
        board.add(sim);

        nao.addActionListener(naoAct);
        nao.setBounds(1150, 510,120,60);
        nao.setFont(buttonFont);
        nao.setBackground(buttonColor);
        nao.setText("NAO");
        board.add(nao);

        this.hideAllButtons();
    }

    public String openModal(String modalType) {
        JPanel p=new JPanel(new GridLayout(1, 2, 10, 10));
        p.setPreferredSize(new Dimension(100, 20));
        JTextField t = new JTextField();

        p.add(t);

        int option;
        String text = "";

        switch (modalType) {
            case "nome":
                option = JOptionPane.showConfirmDialog(null,p,"Nome:",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                if(option==0){
                    text = t.getText();
                    Interface.playerName = text;
                }else{
                    System.exit(0);
                }
                break;
            case "fichas":
                option = JOptionPane.showConfirmDialog(null,p,"Fichas:",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                if(option==0){
                    text = t.getText();
                }else{
                    System.exit(0);
                }
                break;
            case "aposta":
                option = JOptionPane.showConfirmDialog(null,p,"Qual será sua aposta:",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                if(option==0){
                    text = t.getText();
                }else{
                    System.exit(0);
                }
                break;
        }

        return text;
    }

    public void disablePegar() {
        pegar.setEnabled(false);
    }

    public String getReturnButton () {
        this.enableActionButtons();
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

    public void hideAllButtons() {
        pegar.setVisible(false);
        ficar.setVisible(false);
        dobrar.setVisible(false);
        abandonar.setVisible(false);
        sim.setVisible(false);
        nao.setVisible(false);
    }

    public void hideActionButtons() {
        pegar.setVisible(false);
        ficar.setVisible(false);
        dobrar.setVisible(false);
        abandonar.setVisible(false);
    }

    public void disableActionButtons() {
        pegar.setEnabled(false);
        ficar.setEnabled(false);
        dobrar.setEnabled(false);
        abandonar.setEnabled(false);
    }

    public void enableActionButtons() {
        pegar.setEnabled(true);
        ficar.setEnabled(true);
        dobrar.setEnabled(true);
        abandonar.setEnabled(true);
    }

    public void showActionButtons() {
        pegar.setVisible(true);
        ficar.setVisible(true);
        dobrar.setVisible(true);
        abandonar.setVisible(true);
    }

    public void hidePlayAgainButtons() {
        sim.setVisible(false);
        nao.setVisible(false);
        Interface.showPlayAgain = false;
    }

    public void enablePlayAgainButtons() {
        sim.setVisible(true);
        nao.setVisible(true);
        Interface.showPlayAgain = true;

        repaint();
    }

    public void log(String message, String who) {
        Log.add(new Message(message, who));

        repaint();
    }

    public void showTotal(int myTotal, int dealerTotal, int meuSaldo, int dealerSaldo, int aposta) {
        Interface.showTotal = true;
        Interface.myTotal = myTotal;
        Interface.dealerTotal = dealerTotal;

        Interface.meuSaldo = meuSaldo;
        Interface.dealerSaldo = dealerSaldo;

        Interface.aposta = aposta;
        repaint();
    }

    public void displayCards(Jogador eu, Jogador dealer, boolean showFirstCard) {
        Interface.myCards = eu.getMao();
        Interface.dealerCards = dealer.getMao();

        Interface.myCardsQtd = eu.getNumeroCartas();
        Interface.dealerCardsQtd = dealer.getNumeroCartas();

        Interface.displayCards = true;
        Interface.showFirstCard = showFirstCard;
        repaint();
    }

    public void hideCards() {
        Interface.displayCards = false;
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);

            g.setColor(Color.black);
            g.fillRect(gridX, gridY+gridH+50, gridW, 500);

            // WRITE LOGS
            g.setFont(fontLog);
            int logIndex = 0;
            for (Message L : Log) {
                if (L.getWho().equalsIgnoreCase("Dealer")) {
                    g.setColor(cDealer);
                } else {
                    g.setColor(cPlayer);
                }
                g.drawString(L.getMessage(), gridX+20, gridY+480+logIndex*35);
                logIndex++;
            }

            if(Interface.showPlayAgain) {
                g.setColor(Color.black);
                g.drawString("Jogar novamente?", 1000,500);
            }

            if(Interface.showTotal) {
                g.setColor(Color.black);

                g.drawString("Pontos:", gridX+gridW+50, gridY+290);
                g.drawString(Interface.playerName, gridX+gridW+50, gridY+320);
                g.drawString("Dealer", gridX+gridW+210, gridY+320);
                g.drawString(Integer.toString(Interface.myTotal), gridX+gridW+50, gridY+350);
                g.drawString(Integer.toString(Interface.dealerTotal), gridX+gridW+210, gridY+350);

                g.drawString("Saldo:", gridX+gridW+100, gridY+70);
                g.drawString(Interface.playerName, gridX+gridW+50, gridY+100);
                g.drawString("Dealer", gridX+gridW+50, gridY+165);
                g.setColor(Color.green);
                g.drawString("R$",gridX+gridW+50,gridY+130);
                g.drawString(Integer.toString(Interface.meuSaldo), gridX+gridW+100, gridY+130);
                g.drawString("R$", gridX+gridW+50, gridY+200);
                g.drawString(Integer.toString(Interface.dealerSaldo), gridX+gridW+100, gridY+200);

                g.setColor(Color.black);
                g.drawString("Aposta:", gridX+gridW+50, gridY);
                g.setColor(Color.red);
                g.drawString("R$", gridX+gridW+165, gridY);
                g.drawString(Integer.toString(Interface.aposta), gridX+gridW+205, gridY);
            }

            if(Interface.displayCards) {
                //player cards
                for (int i = 0; i < Interface.myCardsQtd; i++) {
                    g.setColor(Color.white);
                    g.fillRect(gridX+spacing+tCardW*i+rounding, gridY+spacing, cardW-rounding*2, cardH);
                    g.fillRect(gridX+spacing+tCardW*i, gridY+spacing+rounding, cardW, cardH-rounding*2);
                    g.fillOval(gridX+spacing+tCardW*i, gridY+spacing, rounding*2, rounding*2);
                    g.fillOval(gridX+spacing+tCardW*i, gridY+spacing+cardH-rounding*2, rounding*2, rounding*2);
                    g.fillOval(gridX+spacing+tCardW*i+cardW-rounding*2, gridY+spacing, rounding*2, rounding*2);
                    g.fillOval(gridX+spacing+tCardW*i+cardW-rounding*2, gridY+spacing+cardH-rounding*2, rounding*2, rounding*2);

                    g.setFont(fontCard);
                    if (Interface.myCards[i].getNaipe().toString().equalsIgnoreCase("Copas") || Interface.myCards[i].getNaipe().toString().equalsIgnoreCase("Ouros")) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(Color.black);
                    }

                    g.drawString(Interface.myCards[i].getName(), gridX+spacing+tCardW*i+rounding, gridY+spacing+cardH-rounding);

                    if (Interface.myCards[i].getNaipe().toString().equalsIgnoreCase("Copas")) {
                        g.fillOval(gridX+tCardW*i+42, gridY+70, 35, 35);
                        g.fillOval(gridX+tCardW*i+73, gridY+70, 35, 35);
                        g.fillArc(gridX+tCardW*i+30, gridY+90, 90, 90, 51, 78);
                    } else if (Interface.myCards[i].getNaipe().toString().equalsIgnoreCase("Ouros")) {
                        polyX[0] = gridX+tCardW*i+75;
                        polyX[1] = gridX+tCardW*i+50;
                        polyX[2] = gridX+tCardW*i+75;
                        polyX[3] = gridX+tCardW*i+100;
                        polyY[0] = gridY+60;
                        polyY[1] = gridY+100;
                        polyY[2] = gridY+140;
                        polyY[3] = gridY+100;
                        g.fillPolygon(polyX, polyY, 4);
                    } else if (Interface.myCards[i].getNaipe().toString().equalsIgnoreCase("Espadas")) {
                        g.fillOval(gridX+tCardW*i+42, gridY+90, 35, 35);
                        g.fillOval(gridX+tCardW*i+73, gridY+90, 35, 35);
                        g.fillArc(gridX+tCardW*i+30, gridY+15, 90, 90, 51+180, 78);
                        g.fillRect(gridX+tCardW*i+70, gridY+100, 10, 40);
                    } else {
                        g.fillOval(gridX+tCardW*i+40, gridY+90, 35, 35);
                        g.fillOval(gridX+tCardW*i+75, gridY+90, 35, 35);
                        g.fillOval(gridX+tCardW*i+58, gridY+62, 35, 35);
                        g.fillRect(gridX+tCardW*i+70, gridY+75, 10, 70);
                    }
                }
                 //dealer cards
                for (int i = 0; i < Interface.dealerCardsQtd; i++) {
                    if(i == 0 && !Interface.showFirstCard) {
                        g.setColor(Color.black);
                        g.fillRect(gridX+spacing+tCardW*i+rounding, gridY+spacing+200, cardW-rounding*2, cardH);
                        g.fillRect(gridX+spacing+tCardW*i, gridY+spacing+rounding+200, cardW, cardH-rounding*2);
                        g.fillOval(gridX+spacing+tCardW*i, gridY+spacing+200, rounding*2, rounding*2);
                        g.fillOval(gridX+spacing+tCardW*i, gridY+spacing+cardH-rounding*2+200, rounding*2, rounding*2);
                        g.fillOval(gridX+spacing+tCardW*i+cardW-rounding*2, gridY+spacing+200, rounding*2, rounding*2);
                        g.fillOval(gridX+spacing+tCardW*i+cardW-rounding*2, gridY+spacing+cardH-rounding*2+200, rounding*2, rounding*2);
                    } else {
                        g.setColor(Color.white);
                        g.fillRect(gridX+spacing+tCardW*i+rounding, gridY+spacing+200, cardW-rounding*2, cardH);
                        g.fillRect(gridX+spacing+tCardW*i, gridY+spacing+rounding+200, cardW, cardH-rounding*2);
                        g.fillOval(gridX+spacing+tCardW*i, gridY+spacing+200, rounding*2, rounding*2);
                        g.fillOval(gridX+spacing+tCardW*i, gridY+spacing+cardH-rounding*2+200, rounding*2, rounding*2);
                        g.fillOval(gridX+spacing+tCardW*i+cardW-rounding*2, gridY+spacing+200, rounding*2, rounding*2);
                        g.fillOval(gridX+spacing+tCardW*i+cardW-rounding*2, gridY+spacing+cardH-rounding*2+200, rounding*2, rounding*2);

                        g.setFont(fontCard);
                        if (Interface.dealerCards[i].getNaipe().toString().equalsIgnoreCase("Copas") || Interface.dealerCards[i].getNaipe().toString().equalsIgnoreCase("Ouros")) {
                            g.setColor(Color.red);
                        } else {
                            g.setColor(Color.black);
                        }

                        g.drawString(Interface.dealerCards[i].getName(), gridX+spacing+tCardW*i+rounding, gridY+spacing+cardH-rounding+200);

                        if (Interface.dealerCards[i].getNaipe().toString().equalsIgnoreCase("Copas")) {
                            g.fillOval(gridX+tCardW*i+42, gridY+70+200, 35, 35);
                            g.fillOval(gridX+tCardW*i+73, gridY+70+200, 35, 35);
                            g.fillArc(gridX+tCardW*i+30, gridY+90+200, 90, 90, 51, 78);
                        } else if (Interface.dealerCards[i].getNaipe().toString().equalsIgnoreCase("Ouros")) {
                            polyX[0] = gridX+tCardW*i+75;
                            polyX[1] = gridX+tCardW*i+50;
                            polyX[2] = gridX+tCardW*i+75;
                            polyX[3] = gridX+tCardW*i+100;
                            polyY[0] = gridY+60+200;
                            polyY[1] = gridY+100+200;
                            polyY[2] = gridY+140+200;
                            polyY[3] = gridY+100+200;
                            g.fillPolygon(polyX, polyY, 4);
                        } else if (Interface.dealerCards[i].getNaipe().toString().equalsIgnoreCase("Espadas")) {
                            g.fillOval(gridX+tCardW*i+42, gridY+90+200, 35, 35);
                            g.fillOval(gridX+tCardW*i+73, gridY+90+200, 35, 35);
                            g.fillArc(gridX+tCardW*i+30, gridY+15+200, 90, 90, 51+180, 78);
                            g.fillRect(gridX+tCardW*i+70, gridY+100+200, 10, 40);
                        } else {
                            g.fillOval(gridX+tCardW*i+40, gridY+90+200, 35, 35);
                            g.fillOval(gridX+tCardW*i+75, gridY+90+200, 35, 35);
                            g.fillOval(gridX+tCardW*i+58, gridY+62+200, 35, 35);
                            g.fillRect(gridX+tCardW*i+70, gridY+75+200, 10, 70);
                        }
                    }
                }
            }
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
            Log.clear();
            Interface.playAgain = "S";
            repaint();
        }
    }

    public class naoAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Interface.playAgain = "N";
        }
    }

}
