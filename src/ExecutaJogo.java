/*  Classe principal do programa, responsável
    pelo fluxo correto das ações */
public class ExecutaJogo implements Runnable {

    // Inicia a interface gráfica
    Interface UI = new Interface();

    public static void main(String[] args) {
        new Thread( new ExecutaJogo() ).start();
    }

    @Override
    public void run(){

        /*  Instancia os objetos (Baralho, Jogadores)
            pega o input do usuário (nome, créditos),
            instancia as variáveis.
         */

        Baralho baralho = new Baralho();
        String nome;
        double creditos, aposta;

        UI.hideAllButtons();
        nome = UI.openModal("nome");
        do {
            creditos = Double.parseDouble(UI.openModal("fichas"));
        } while (creditos < 1);

        Jogador eu = new Jogador(nome, creditos);
        Jogador dealer = new Jogador("Dealer", 0);

        SoundsPlayer.init();

        boolean abandonGame = false;

        // Loop principal do programa
        while(true) {

            /* Esconde as cartas, e esvazia as mãos
                para iniciar um novo jogo
             */
            UI.hideCards();
            eu.esvaziaMao();
            dealer.esvaziaMao();

            /*  Loop que espera pelo input correto do usuário para a aposta
                - Aposta deve ser maior que 1 e menor que o saldo atual
             */
            creditos = eu.getCreditos();
            do{
                aposta = Double.parseDouble(UI.openModal("aposta"));
            } while((aposta > creditos) || aposta < 1);

            eu.setAposta(aposta);

            // Monta o baralho embaralhado, o Thread.sleep serve para esperar o som de embaralhamento tocar.
            baralho.montaBaralho(true);
            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Exibe as opções do usuário a Interface
            UI.showActionButtons();

            /*  Entrega as cartas e mostra na interface
                As Thread.sleep's servem para mostrar a ação de entrega
                além de esperar o som das cartas tocar;
             */
            eu.addCarta(baralho.entregaCarta());
            dealer.addCarta(baralho.entregaCarta());
            try {
                UI.displayCards(eu, dealer, false);
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eu.addCarta(baralho.entregaCarta());
            dealer.addCarta(baralho.entregaCarta());
            try {
                UI.displayCards(eu, dealer, false);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Mostra mensagem na interface.
            UI.log("Cartas distribuidas!", dealer.getNome());

            boolean euAcabei = false;
            boolean dealerAcabou = false;
            String resp;

            // Loop que lê ações do usuário
            while ((!euAcabei || !dealerAcabou)) {
                // Mostra as informações na interface;
                UI.showTotal(eu.somaMao(true), dealer.somaMao(false), eu.getCreditos(), dealer.getCreditos(), eu.getAposta());
                // Atualiza as cartas da interface
                UI.displayCards(eu, dealer, false);
                // Espera pela ação do usuário caso ele ainda tenha menos de 21 pontos;
                if(!euAcabei) {
                    UI.enableActionButtons();
                    // UI.getReturnButton espera o usuário interagir com algum botão
                    switch (UI.getReturnButton()) {
                        case "pegar":
                            /* Jogador.addCarta retorna um boolean
                                true: se a soma das cartas for maior que 21
                                false: se a soma for menor que 21
                             */
                            euAcabei = !eu.addCarta(baralho.entregaCarta());
                            // Loga na interface
                            UI.log("Você pegou uma carta!", eu.getNome());
                            break;
                        case "ficar":
                            euAcabei = true;
                            UI.log("Você decidiu ficar!", eu.getNome());
                            break;
                        case "abandonar":
                            eu.abandonarPartida(dealer);
                            UI.log("Você desistiu!", eu.getNome());
                            abandonGame = true;
                            break;
                        case "dobrar":
                            eu.dobraAposta();
                            euAcabei = !eu.addCarta(baralho.entregaCarta());
                            UI.log("Você dobrou a aposta e pegou mais uma carta!", eu.getNome());
                            break;
                        default:
                            // Thread.sleep, evita o consumo excessivo do CPU na espera do input do usuário
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                    }
                    if(abandonGame) {
                        UI.showTotal(eu.somaMao(true), dealer.somaMao(false), eu.getCreditos(), dealer.getCreditos(), eu.getAposta());
                        UI.hideCards();
                        UI.resetReturn();
                        break;
                    }
                    UI.resetReturn();
                }

                UI.disableActionButtons();
                UI.displayCards(eu, dealer, false);
                UI.showTotal(eu.somaMao(true), dealer.somaMao(false), eu.getCreditos(), dealer.getCreditos(), eu.getAposta());

                if(!dealerAcabou) {
                    // Thread.sleep dá mais interação pro jogo, espera 1s antes da decisão do Dealer
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Caso a soma do dealer for menor que 17 ele pega uma carta, caso for maior ele fica
                    if(dealer.somaMao(true) < 17) {
                        UI.log("O Dealer pegou uma carta!", dealer.getNome());
                        dealerAcabou = !dealer.addCarta(baralho.entregaCarta());
                    } else {
                        UI.log("O Dealer decidiu ficar!", dealer.getNome());
                        dealerAcabou = true;
                    }
                }

                UI.displayCards(eu, dealer, false);
            }

            // Caso o jogador não tenha abandonado o game, inicia a verificação do vencedor;
            if(!abandonGame) {
                UI.displayCards(eu, dealer, true);

                // Pega a informação da soma das mãoes
                int minhaSoma = eu.somaMao(true);
                int somaDealer = dealer.somaMao(true);

                /*  Lógica para verificar o ganhador
                    Caso minha soma for maior que a do dealer e menor que 21 eu ganho,
                    caso a soma do dealer for maior que 21 eu ganho,
                    caso as somas forem iguais, empate,
                    qualquer outro caso o dealer ganha.
                 */
                if (minhaSoma > somaDealer && minhaSoma <= 21 || somaDealer > 21) {
                    eu.ganharAposta(dealer);
                    UI.log("Você ganhou!", eu.getNome());
                    SoundsPlayer.WIN.play();
                } else if (minhaSoma == somaDealer) {
                    eu.setAposta(0);
                    UI.log("Empatou", "");
                } else {
                    eu.perderAposta(dealer);
                    UI.log("Dealer ganhou!", dealer.getNome());
                    SoundsPlayer.LOSE.play();
                }

                UI.showTotal(minhaSoma, somaDealer, eu.getCreditos(), dealer.getCreditos(), eu.getAposta());
            }

            boolean exitLoop = false;
            // Esconde os botões de ação e espera a decisão do usuário de jogar novamente
            UI.enablePlayAgainButtons();
            UI.hideActionButtons();
            do {
                switch(UI.playAgain()) {
                    // Caso selecione SIM, inicia o processo de iniciar o jogo novamente
                    case "S":
                        abandonGame = false;
                        exitLoop = true;
                        UI.resetPlayAgain();
                        UI.hidePlayAgainButtons();
                        UI.hideTotal();
                        break;
                    case "N":
                        System.exit(0);
                        break;
                    default:
                        // Evita consumo excessivo da CPU
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } while (!exitLoop);
        }

    }
}
