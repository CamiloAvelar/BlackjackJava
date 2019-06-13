public class Jogador {

    private String nome;

    private Carta[] mao = new Carta[10];

    private int numeroCartas;

    public Jogador(String nome) {
        this.nome = nome;

        this.esvaziaMao();
    }

    public void esvaziaMao() {
        for(int c = 0; c < 10; c++) {
            this.mao[c] = null;
        }
        this.numeroCartas = 0;
    }

    public boolean addCarta(Carta carta) {
        if(this.numeroCartas == 10) {
            System.err.printf("A mão de %s já tem 10 cartas, não pode adicionar outra!\n", this.nome);
            System.exit(1);
        }

        this.mao[this.numeroCartas] = carta;
        this.numeroCartas++;

        return (this.somaMao() <= 21);
    }

    public int somaMao() {
        int somaMao = 0;
        int numeroCarta;
        int numeroAs = 0;

        for(int c = 0; c < this.numeroCartas; c++) {
            numeroCarta = this.mao[c].getNumero();

            if(numeroCarta == 1) {
                numeroAs++;
                somaMao += 11;
            } else if (numeroCarta > 10) {
                somaMao += 10;
            } else {
                somaMao += numeroCarta;
            }
        }

        while (somaMao > 21 && numeroAs > 0) {
            somaMao -= 10;
            numeroAs--;
        }


        return somaMao;
    }

    public void imprimeMao(boolean mostraPrimeiraCarta) {
        System.out.printf("Cartas de %s\n", this.nome);
        for (int c = 0; c < this.numeroCartas; c++) {
            if(c == 0 && !mostraPrimeiraCarta) {
                System.out.println(" [escondida]");
            } else {
                System.out.printf(" %s\n", this.mao[c].toString());
            }
        }
    }
}
