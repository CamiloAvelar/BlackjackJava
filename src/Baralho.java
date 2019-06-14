
import java.util.Random;

public class Baralho {
    private Carta[] cartas;
    private int numeroCartas;

    public Baralho(boolean embaralhar) {
        this.numeroCartas = 52;
        this.cartas = new Carta[this.numeroCartas];

        int indexCarta = 0;

        for(int naipe = 0; naipe < 4; naipe++){

            for(int numero = 1; numero <= 13; numero++) {

                this.cartas[indexCarta] = new Carta(Naipes.values()[naipe], numero);
                indexCarta++;

            }
        }

        if (embaralhar) {
            this.embaralha();
        }
    }

    public void embaralha() {

        Random random = new Random();

        Carta temp;

        int j;
        for(int i = 0 ; i < this.numeroCartas; i++) {
            j = random.nextInt(this.numeroCartas);

            temp = this.cartas[i];
            this.cartas[i] = this.cartas[j];
            this.cartas[j] = temp;
        }
    }

    public Carta entregaCarta() {

        Carta topo = this.cartas[0];

        // move todas as cartas para a esquerda
        for (int c = 1; c < this.numeroCartas; c++) {
            this.cartas[c-1] = this.cartas[c];
        }

        this.cartas[this.numeroCartas-1] = null;

        this.numeroCartas--;

        return topo;
    }

    public void printBaralho(int numeroImpressao) {
        for (int c = 0 ; c < numeroImpressao; c++) {
            System.out.printf("% 3d/%d %s\n", c + 1, this.numeroCartas, this.cartas[c].toString());
        }
        System.out.printf("\t\t[%d outro]\n", this.numeroCartas-numeroImpressao);
    }
}
