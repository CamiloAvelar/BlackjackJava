
import java.util.Scanner;

public class ExecutaJogo implements Runnable {

    Interface UI = new Interface();

    public static void main(String[] args) {
        new Thread( new ExecutaJogo() ).start();
    }

    @Override
    public void run(){

        Scanner sc = new Scanner(System.in);
        Baralho baralho = new Baralho(true);
        String nome;
        int creditos;

        System.out.print("Digite o seu nome: ");
        nome = sc.next();
        System.out.print("Digite a quantidade de fichas irá comprar: ");
        creditos = sc.nextInt();

        Jogador eu = new Jogador(nome, creditos);
        Jogador dealer = new Jogador("Dealer", 0);

        boolean endGame = false;

        while(!endGame) {

            eu.esvaziaMao();
            dealer.esvaziaMao();

            eu.addCarta(baralho.entregaCarta());
            dealer.addCarta(baralho.entregaCarta());
            eu.addCarta(baralho.entregaCarta());
            dealer.addCarta(baralho.entregaCarta());

            System.out.printf("Qual será sua aposta? (Créditos: %d) : ", eu.getCreditos());
            eu.setAposta(sc.nextInt());

            System.out.println("Cartas distribuidas!");
            eu.imprimeMao(true);
            dealer.imprimeMao(false);
            System.out.println("\n");

            boolean euAcabei = false;
            boolean dealerAcabou = false;
            String resp;

            while ((!euAcabei || !dealerAcabou) && !endGame) {
                if(!euAcabei) {

                    switch (UI.getReturnButton()) {
                        case "pegar":
                            euAcabei = !eu.addCarta(baralho.entregaCarta());
                            eu.imprimeMao(true);
                            break;
                        case "ficar":
                            euAcabei = true;
                            break;
                        case "abandonar":
                            eu.abandonarPartida(dealer);
                            endGame = true;
                            break;
                        case "desistir":
                            eu.dobraAposta();
                            euAcabei = !eu.addCarta(baralho.entregaCarta());
                            eu.imprimeMao(true);
                            break;
                        default:
                            continue;
                    }

                    UI.resetReturn();
                }

                if(!dealerAcabou) {
                    if(dealer.somaMao(true) < 17) {
                        System.out.println("O Dealer pegou\n");
                        dealerAcabou = !dealer.addCarta(baralho.entregaCarta());
                        dealer.imprimeMao(false);
                    } else {
                        System.out.println("O Dealer fica\n");
                        dealerAcabou = true;
                    }
                }

                System.out.println();
            }

            if(!endGame) {
                eu.imprimeMao(true);
                dealer.imprimeMao(true);

                int minhaSoma = eu.somaMao(true);
                int somaDealer = dealer.somaMao(true);

                if (minhaSoma > somaDealer && minhaSoma <= 21 || somaDealer > 21) {
                    eu.ganharAposta(dealer);
                    System.out.println("Você ganhou!");
                } else if (minhaSoma == somaDealer) {
                    eu.setAposta(0);
                    System.out.println("Empate!");
                } else {
                    eu.perderAposta(dealer);
                    System.out.println("O dealer ganhou!");
                }
            }

            System.out.printf("Saldo %s: %d", eu.getNome(), eu.getCreditos());
            System.out.println();
            System.out.printf("Saldo %s: %d", dealer.getNome(), dealer.getCreditos());
            System.out.println();
            System.out.print("Deseja jogar novamente? (S ou N)\n");
            boolean exitLoop = false;
            do {
                switch(UI.playAgain()) {
                    case "S":
                        endGame = false;
                        exitLoop = true;
                        UI.resetPlayAgain();
                        break;
                    case "N":
                        endGame = true;
                        exitLoop = true;
                        UI.resetPlayAgain();
                        break;
                    default:
                        break;
                }
            } while (!exitLoop);
        }

    }
}
