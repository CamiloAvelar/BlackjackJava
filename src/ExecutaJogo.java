
import java.util.Scanner;

public class ExecutaJogo {
    public static void main(String[] args) {

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

            while (!euAcabei || !dealerAcabou) {
                if(!euAcabei) {
                    System.out.print("Pegar Carta (P), Ficar (F), Abandonar (A) ou Dobrar Aposta(D)? ");
                    resp = sc.next();
                    System.out.println();

                    if(resp.compareToIgnoreCase("P") == 0) {
                        euAcabei = !eu.addCarta(baralho.entregaCarta());
                        eu.imprimeMao(true);
                    } else if(resp.compareToIgnoreCase("F") == 0){
                        euAcabei = true;
                    } else if (resp.compareToIgnoreCase("A") == 0){
                        eu.abandonarPartida(dealer);
                        endGame = true;
                        break;
                    } else if (resp.compareToIgnoreCase("D") == 0){
                        euAcabei = !eu.addCarta(baralho.entregaCarta());
                        eu.dobraAposta();
                        eu.imprimeMao(true);
                    }
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

            String playAgain;

            System.out.printf("Saldo %s: %d", eu.getNome(), eu.getCreditos());
            System.out.println();
            System.out.printf("Saldo %s: %d", dealer.getNome(), dealer.getCreditos());
            System.out.println();
            System.out.print("Deseja jogar novamente? (S ou N) ");
            playAgain = sc.next();
            switch (playAgain) {
                case "S":
                    endGame = false;
                    break;
                case "N":
                    endGame = true;
                    break;
            }
        }

    }
}
