
import java.util.Scanner;

public class ExecutaJogo {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Baralho baralho = new Baralho(1, true);

        Jogador eu = new Jogador("Camilo");
        Jogador dealer = new Jogador("Dealer");

        eu.addCarta(baralho.entregaCarta());
        dealer.addCarta(baralho.entregaCarta());
        eu.addCarta(baralho.entregaCarta());
        dealer.addCarta(baralho.entregaCarta());

        System.out.println("Cartas distribuidas!");
        eu.imprimeMao(true);
        dealer.imprimeMao(false);
        System.out.println("\n");

        boolean euAcabei = false;
        boolean dealerAcabou = false;
        String resp;

        while (!euAcabei || !dealerAcabou) {
            if(!euAcabei) {
                System.out.print("Pegar ou Ficar? (Digite P ou F): ");
                resp = sc.next();
                System.out.println();

                if(resp.compareToIgnoreCase("P") == 0) {
                    euAcabei = !eu.addCarta(baralho.entregaCarta());
                    eu.imprimeMao(true);
                } else {
                    euAcabei = true;
                }
            }

            if(!dealerAcabou) {
                if(dealer.somaMao() < 17) {
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

        sc.close();

        eu.imprimeMao(true);
        dealer.imprimeMao(true);

        int minhaSoma = eu.somaMao();
        int somaDealer = dealer.somaMao();

        if (minhaSoma > somaDealer && minhaSoma <= 21 || somaDealer > 21) {
            System.out.println("Você ganhou!");
        } else {
            System.out.println("O dealer ganhou!");
        }

    }
}
