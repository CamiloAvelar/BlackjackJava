//TODO: Adicionar validação nos campos

public class ExecutaJogo implements Runnable {

    Interface UI = new Interface();

    public static void main(String[] args) {
        new Thread( new ExecutaJogo() ).start();
    }

    @Override
    public void run(){

        Baralho baralho = new Baralho(true);
        String nome;
        double creditos, aposta;

        UI.hideAllButtons();
        nome = UI.openModal("nome");
        do {
            creditos = Double.parseDouble(UI.openModal("fichas"));
        } while (creditos < 1);

        Jogador eu = new Jogador(nome, creditos);
        Jogador dealer = new Jogador("Dealer", 0);

        boolean abandonGame = false;

        while(true) {

            UI.hideCards();
            eu.esvaziaMao();
            dealer.esvaziaMao();

            eu.addCarta(baralho.entregaCarta());
            dealer.addCarta(baralho.entregaCarta());
            eu.addCarta(baralho.entregaCarta());
            dealer.addCarta(baralho.entregaCarta());

            creditos = eu.getCreditos();
            do{
                aposta = Double.parseDouble(UI.openModal("aposta"));
            } while((aposta > creditos) || aposta < 1);

            eu.setAposta(aposta);

            UI.showActionButtons();

            UI.log("Cartas distribuidas!", dealer.getNome());
            UI.displayCards(eu, dealer, false);

            boolean euAcabei = false;
            boolean dealerAcabou = false;
            String resp;

            while ((!euAcabei || !dealerAcabou)) {
                UI.showTotal(eu.somaMao(true), dealer.somaMao(false), eu.getCreditos(), dealer.getCreditos(), eu.getAposta());
                if(!euAcabei) {
                    UI.enableActionButtons();
                    switch (UI.getReturnButton()) {
                        case "pegar":
                            euAcabei = !eu.addCarta(baralho.entregaCarta());
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
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

            if(!abandonGame) {
                UI.displayCards(eu, dealer, true);

                int minhaSoma = eu.somaMao(true);
                int somaDealer = dealer.somaMao(true);

                if (minhaSoma > somaDealer && minhaSoma <= 21 || somaDealer > 21) {
                    eu.ganharAposta(dealer);
                    UI.log("Você ganhou!", eu.getNome());
                } else if (minhaSoma == somaDealer) {
                    eu.setAposta(0);
                    UI.log("Empatou", "");
                } else {
                    eu.perderAposta(dealer);
                    UI.log("Dealer ganhou!", dealer.getNome());
                }

                UI.showTotal(minhaSoma, somaDealer, eu.getCreditos(), dealer.getCreditos(), eu.getAposta());
            }

            boolean exitLoop = false;
            UI.enablePlayAgainButtons();
            UI.hideActionButtons();
            do {
                switch(UI.playAgain()) {
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
