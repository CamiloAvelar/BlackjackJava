import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.*;
import java.util.HashMap;

public class Interface extends JFrame implements ActionListener{

    // Variáveis que auxiliam no tamanho dos objetos
    int width = 1280;
    int height = 800;

    int gridX = 50;
    int gridY = 50;
    int gridW = 900;
    int gridH = 400;

    // Map que guarda os botões, com a key sendo o nome vindo do ENUM Botoes
    private Map<String, JButton> botoes = new HashMap<String, JButton>();

    // Atributos que guardam informações para exibir na interface
    private static boolean showTotal = false;
    private static int myTotal;
    private static int dealerTotal;

    private static String playerName;
    private static double meuSaldo;
    private static double dealerSaldo;

    private static double aposta;

    private static ArrayList<Carta> myCards;
    private static ArrayList<Carta> dealerCards;
    private static boolean displayCards = false;
    private static boolean showFirstCard = false;

    private static String returnButton = "";
    private static String playAgain = "";
    private static boolean showPlayAgain = false;

    Board board = new Board();

    // Array que guarda os logs que serão exibidos na interface
    ArrayList<Message> Log = new ArrayList<Message>();

    // Variáveis de estilização (Cor e fontes)
    Color backgroundColor = new Color(39, 119,20);
    Color infoColor = new Color(29,150,20);
    Color buttonColor = new Color(204,204,0);
    Color cDealer = Color.red;
    Color cPlayer = new Color(78, 105,255);

    Font fontCard = new Font("Times New Roman", Font.PLAIN, 40);
    Font buttonFont = new Font("Times New Roman", Font.PLAIN, 30);
    Font fontLog = new Font("Times New Roman", Font.ITALIC, 30);
    Font titleFont = new Font("Times New Roman", Font.PLAIN,  30);
    Font infoFont = new Font("Times New Roman", Font.ITALIC, 25);
    Font namesFont = new Font("Times New Roman", Font.PLAIN, 26);

    int[] polyX = new int[4];
    int[] polyY = new int[4];

    //card spacing and dimensions
    int spacing = 10;
    int rounding = 10;
    int tCardW = (int) gridW/6;
    int tCardH = (int) gridH/2;
    int cardW = tCardW - spacing*2;
    int cardH = tCardH - spacing*2;

    // Construtor da interface, onde são setadas as configurações, criados os botões e suas funções
    public Interface() {
        this.setSize(width, height + 29);
        this.setTitle("Blackjack");
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(board);
        this.setLayout(null);

        /*  Cria os botões, adicionando ao Map, com a key o valor do ENUM Botoes e como value o JButton
            Para recuperar o botão basta dar um botoes.get(NOME), é assim que são setadas as suas configurações no loop
         */
        for (Botoes b: Botoes.values()){
            botoes.put(b.name(), new JButton());

            botoes.get(b.name()).setVisible(false);
            botoes.get(b.name()).setText(b.name());
            botoes.get(b.name()).setFont(buttonFont);
            botoes.get(b.name()).setBackground(buttonColor);
            botoes.get(b.name()).addActionListener(this);

            board.add(botoes.get(b.name()));
        }

        this.setButtonsSize();
    }

    // Função para setar as posições e tamanhos dos botões
    public void setButtonsSize() {
        this.botoes.get("Pegar").setBounds(970, 530,300,50);
        this.botoes.get("Ficar").setBounds(970, 600,300,50);
        this.botoes.get("Abandonar").setBounds(970, 670,300,50);
        this.botoes.get("Dobrar").setBounds(970, 740,300,50);
        this.botoes.get("Sim").setBounds(990, 560,120,60);
        this.botoes.get("Nao").setBounds(1140, 560,120,60);
    }

    /*  Configura as ações dos botões
            Como a lógica principal fica no ExecutaJogo,
            os botões tem como função apenas retornar o tipo do botão apertado,
            para o ExecutaJogo executar o bloco correto de código
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == botoes.get("Pegar")) {
            Interface.returnButton = "pegar";
        }

        if(e.getSource() == botoes.get("Ficar")) {
            SoundsPlayer.CLICK.play();
            Interface.returnButton = "ficar";
        }

        if(e.getSource() == botoes.get("Abandonar")) {
            SoundsPlayer.CLICK.play();
            Interface.returnButton = "abandonar";
        }

        if(e.getSource() == botoes.get("Dobrar")) {
            Interface.returnButton = "dobrar";
        }

        if(e.getSource() == botoes.get("Sim")) {
            SoundsPlayer.CLICK.play();
            Log.clear();
            Interface.playAgain = "S";
            repaint();
        }

        if(e.getSource() == botoes.get("Nao")) {
            SoundsPlayer.CLICK.play();
            Interface.playAgain = "N";
        }
    }

    /*  Função para abrir a caixa de digitação,
        recebe o tipo de caixa que deseja  e retorna o valor digitado na caixa,
        faz também a verificação para o formato correto digitado
    */
    public String openModal(String modalType) {
        String text = "";
        switch (modalType) {
            case "nome":
                String name = StringUtils.capitalize(JOptionPane.showInputDialog(null,"Digite seu nome:", "Nome", JOptionPane.PLAIN_MESSAGE));
                if(name == null) {
                    System.exit(0);
                }

                Interface.playerName = name;
                text = name;
                break;
            case "fichas":
                String fichas = JOptionPane.showInputDialog(null,"Digite a quantidade de R$ que deseja comprar:", "Fichas", JOptionPane.PLAIN_MESSAGE);
                if(fichas == null) {
                    System.exit(0);
                }

                fichas = fichas.replace(',', '.');

                try {
                    Interface.meuSaldo = Double.parseDouble(fichas);
                } catch (NumberFormatException e) {
                    JOptionPane.showConfirmDialog(null,"Formato inválido!", "Erro!", JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
                    fichas = "0.0";
                }

                text = fichas;
                break;
            case "aposta":
                JPanel apostaPanel = new JPanel(new GridLayout(3, 1, 2, 1));
                apostaPanel.setPreferredSize(new Dimension(100, 50));

                JLabel lblSaldo = new JLabel("Saldo: R$" + String.format("%.2f", Interface.meuSaldo));
                JLabel lblBet = new JLabel("Digite a sua aposta: ");

                apostaPanel.add(lblSaldo);
                apostaPanel.add(lblBet);
                String aposta = JOptionPane.showInputDialog(null,apostaPanel, "Aposta", JOptionPane.PLAIN_MESSAGE);
                if(aposta == null) {
                    System.exit(0);
                }

                aposta = aposta.replace(',', '.');

                try {
                    if(Double.parseDouble(aposta) < 1.0) {
                        JOptionPane.showConfirmDialog(null,"Aposta deve ser maior que R$1.0", "Erro!", JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
                    }

                    if(Double.parseDouble(aposta) > Interface.meuSaldo) {
                        JOptionPane.showConfirmDialog(null,"Saldo insuficiente!", "Erro!", JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showConfirmDialog(null,"Formato inválido!", "Erro!", JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
                    aposta = "0.0";
                }

                text = aposta;
                break;
        }

        return text;
    }

    // Retorna o valor do botão apertado, é a String que retorna para o ExecutaJogo
    public String getReturnButton () {
        return Interface.returnButton;
    }

    // Retorna para o ExecutaJogo se o usuáro irá jogar novamente
    public String playAgain () {
        return Interface.playAgain;
    }

    // Reseta o valor da variável de retorno
    public void resetReturn() {
        Interface.returnButton = "none";
    }

    // Reseta o valor da variável de retorno do playAgain
    public void resetPlayAgain() {
        Interface.playAgain = "none";
    }

    // Esconde todos os botões
    public void hideAllButtons() {
        for(Botoes b: Botoes.values()) {
            botoes.get(b.name()).setVisible(false);
        }
    }

    // Esconde apenas os botões de ação (pegar, ficar, ..);
    public void hideActionButtons() {
        for(Botoes b: Botoes.values()) {
            if(b.getAction()) {
                botoes.get(b.name()).setVisible(false);
            }
        }
    }

    // Esconde apenas o botões de JogarNovamente
    public void hidePlayAgainButtons() {
        for(Botoes b: Botoes.values()) {
            if(!b.getAction()) {
                botoes.get(b.name()).setVisible(false);
            }
        }
        Interface.showPlayAgain = false;
    }

    // Desativa os botões de ação
    public void disableActionButtons() {
        for(Botoes b: Botoes.values()) {
            if(b.getAction()) {
                botoes.get(b.name()).setEnabled(false);
            }
        }
    }

    // Ativa os botões de jogar novamente
    public void enablePlayAgainButtons() {
        for(Botoes b: Botoes.values()) {
            if(!b.getAction()) {
                botoes.get(b.name()).setVisible(true);
            }
        }
        Interface.showPlayAgain = true;
    }

    // Ativa os botões de ação (pegar, ficar, ...)
    public void enableActionButtons() {
        for(Botoes b: Botoes.values()) {
            if(b.getAction()) {
                botoes.get(b.name()).setEnabled(true);
            }
        }
    }

    // Mostra os botões de ação
    public void showActionButtons() {
        for(Botoes b: Botoes.values()) {
            if(b.getAction()) {
                botoes.get(b.name()).setVisible(true);
            }
        }
    }

    // Adiciona uma mensagem ao Log, para mostrar na interface
    public void log(String message, String who) {
        if(Log.size() > 7){
            Log.remove(0);
        }

        Log.add(new Message(message, who));
        repaint();
    }

    // Recebe os parâmetros do ExecutaJogo para mostrar na interface
    public void showTotal(int myTotal, int dealerTotal, double meuSaldo, double dealerSaldo, double aposta) {
        Interface.showTotal = true;
        Interface.myTotal = myTotal;
        Interface.dealerTotal = dealerTotal;

        Interface.meuSaldo = meuSaldo;
        Interface.dealerSaldo = dealerSaldo;

        Interface.aposta = aposta;

        repaint();
    }

    // Esconde os valores na interface
    public void hideTotal() {
        Interface.showTotal = false;
    }

    // Atualiza a interface mostrando as cartas
    public void displayCards(Jogador eu, Jogador dealer, boolean showFirstCard) {
        Interface.myCards = eu.getMao();
        Interface.dealerCards = dealer.getMao();

        Interface.displayCards = true;
        Interface.showFirstCard = showFirstCard;

        repaint();
    }

    // Atualiza a interface escondendo todas as cartas
    public void hideCards() {
        Interface.displayCards = false;
    }

    // Classe que desenha no Board
    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);

            g.setColor(Color.black);
            g.fillRect(gridX, gridY+gridH+50, gridW, 500);

            g.setColor(infoColor);
            g.fillRect(980,10,285,480);

            // Faz um loop los Logs e mostram na interface
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

            // Mostra a opção de jogar novamente se a variável estiver true
            if(Interface.showPlayAgain) {
                g.setColor(Color.black);
                g.drawString("Jogar novamente?", 990,550);
            }

            // Mostra as informações na interface se a variável estiver true
            if(Interface.showTotal) {
                g.setColor(Color.black);

                g.setFont(titleFont);
                g.drawString("Pontos:", gridX+gridW+130, gridY+340);
                g.setFont(namesFont);
                g.drawString(Interface.playerName, gridX+gridW+50, gridY+370);
                g.drawString("Dealer", gridX+gridW+210, gridY+370);
                g.setFont(infoFont);
                g.drawString(Integer.toString(Interface.myTotal), gridX+gridW+50, gridY+400);
                g.drawString(Integer.toString(Interface.dealerTotal), gridX+gridW+210, gridY+400);

                g.setFont(titleFont);
                g.drawString("Saldo:", gridX+gridW+130, gridY+110);
                g.setFont(namesFont);
                g.drawString(Interface.playerName, gridX+gridW+50, gridY+140);
                g.drawString("Dealer", gridX+gridW+50, gridY+205);
                g.setColor(Color.green);
                g.setFont(infoFont);
                g.drawString("R$",gridX+gridW+50,gridY+170);
                g.drawString(String.format("%.2f", Interface.meuSaldo), gridX+gridW+100, gridY+170);
                if(Interface.dealerSaldo < 0) {
                    g.setColor(Color.red);
                }
                g.drawString("R$", gridX+gridW+50, gridY+240);
                g.drawString(String.format("%.2f", Interface.dealerSaldo), gridX+gridW+100, gridY+240);

                g.setColor(Color.black);
                g.setFont(titleFont);
                g.drawString("Aposta:", gridX+gridW+130, gridY);
                g.setFont(infoFont);
                g.setColor(Color.red);
                g.drawString("R$", gridX+gridW+50, gridY+30);
                g.drawString(String.format("%.2f", Interface.aposta), gridX+gridW+100, gridY+30);
            }

            // Mostra as cartas de a variável estiver true
            if(Interface.displayCards) {
                // Mostra as cartas do jogador
                int i = 0;
                for (Carta c : myCards) {
                    // Função que desenha as cartas
                    drawCards(g, c, i, false);
                    i++;
                }
                 // Mostra as cartas do dealer
                int iDealer = 0;
                for (Carta c : dealerCards) {
                    // Função que desenha as cartas
                    drawCards(g, c, iDealer, true);

                    iDealer++;
                }
            }
        }

        // Função que contém informações para desenhar as cartas
        private void drawCards(Graphics g, Carta c, int i, boolean isDealer) {
            int dealerSpacing;
            if (isDealer) {
                dealerSpacing = 200;
            } else {
                dealerSpacing = 0;
            }

            if(isDealer && i == 0 && !Interface.showFirstCard) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.white);
            }
            g.fillRect(gridX+spacing+tCardW*i+rounding, gridY+spacing+dealerSpacing, cardW-rounding*2, cardH);
            g.fillRect(gridX+spacing+tCardW*i, gridY+spacing+rounding+dealerSpacing, cardW, cardH-rounding*2);
            g.fillOval(gridX+spacing+tCardW*i, gridY+spacing+dealerSpacing, rounding*2, rounding*2);
            g.fillOval(gridX+spacing+tCardW*i, gridY+spacing+cardH-rounding*2+dealerSpacing, rounding*2, rounding*2);
            g.fillOval(gridX+spacing+tCardW*i+cardW-rounding*2, gridY+spacing+dealerSpacing, rounding*2, rounding*2);
            g.fillOval(gridX+spacing+tCardW*i+cardW-rounding*2, gridY+spacing+cardH-rounding*2+dealerSpacing, rounding*2, rounding*2);
            if(!(isDealer && i == 0) || Interface.showFirstCard) {
                g.setFont(fontCard);
                if (c.getNaipe().toString().equalsIgnoreCase("Copas") || c.getNaipe().toString().equalsIgnoreCase("Ouros")) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }

                g.drawString(c.getName(), gridX+spacing+tCardW*i+rounding, gridY+spacing+cardH-rounding+dealerSpacing);

                if (c.getNaipe().toString().equalsIgnoreCase("Copas")) {
                    g.fillOval(gridX+tCardW*i+42, gridY+70+dealerSpacing, 35, 35);
                    g.fillOval(gridX+tCardW*i+73, gridY+70+dealerSpacing, 35, 35);
                    g.fillArc(gridX+tCardW*i+30, gridY+90+dealerSpacing, 90, 90, 51, 78);
                } else if (c.getNaipe().toString().equalsIgnoreCase("Ouros")) {
                    polyX[0] = gridX+tCardW*i+75;
                    polyX[1] = gridX+tCardW*i+50;
                    polyX[2] = gridX+tCardW*i+75;
                    polyX[3] = gridX+tCardW*i+100;
                    polyY[0] = gridY+60+dealerSpacing;
                    polyY[1] = gridY+100+dealerSpacing;
                    polyY[2] = gridY+140+dealerSpacing;
                    polyY[3] = gridY+100+dealerSpacing;
                    g.fillPolygon(polyX, polyY, 4);
                } else if (c.getNaipe().toString().equalsIgnoreCase("Espadas")) {
                    g.fillOval(gridX+tCardW*i+42, gridY+90+dealerSpacing, 35, 35);
                    g.fillOval(gridX+tCardW*i+73, gridY+90+dealerSpacing, 35, 35);
                    g.fillArc(gridX+tCardW*i+30, gridY+15+dealerSpacing, 90, 90, 51+180, 78);
                    g.fillRect(gridX+tCardW*i+70, gridY+100+dealerSpacing, 10, 40);
                } else {
                    g.fillOval(gridX+tCardW*i+40, gridY+90+dealerSpacing, 35, 35);
                    g.fillOval(gridX+tCardW*i+75, gridY+90+dealerSpacing, 35, 35);
                    g.fillOval(gridX+tCardW*i+58, gridY+62+dealerSpacing, 35, 35);
                    g.fillRect(gridX+tCardW*i+70, gridY+75+dealerSpacing, 10, 70);
                }
            }
        }
    }
}
