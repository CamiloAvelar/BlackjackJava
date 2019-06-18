public class Jogador {

    private String nome;

    private Carta[] mao = new Carta[10];

    private int numeroCartas;

    //TODO: Transformar Creditos e Aposta para double
    private int creditos;
    private int aposta;

    public int getAposta() {
        return aposta;
    }

    public int getNumeroCartas() {
        return numeroCartas;
    }

    public Carta[] getMao() {
        return mao;
    }


    public void setAposta(int aposta) {
        this.aposta = aposta;
    }

    public String getNome() {
        return nome;
    }

    public void dobraAposta() {
        this.aposta *= 2;
    }

    public void abandonarPartida(Jogador dealer) {
        int aposta = this.aposta/2;
        this.creditos -= aposta;
        dealer.setCreditos(dealer.getCreditos() + aposta);
    }

    public void ganharAposta(Jogador perdedor) {
        this.creditos += this.aposta;
        perdedor.setCreditos(perdedor.getCreditos() - this.aposta);
    }

    public void perderAposta(Jogador ganhador) {
        this.creditos -= this.aposta;
        ganhador.setCreditos(ganhador.getCreditos() + this.aposta);
    }

    public Jogador(String nome, int creditos) {
        this.nome = nome;
        this.creditos = creditos;

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

        return (this.somaMao(true) <= 21);
    }

    public int somaMao(boolean somaPrimeiraCarta) {
        int somaMao = 0;
        int numeroCarta;
        int numeroAs = 0;

        for(int c = 0; c < this.numeroCartas; c++) {
            numeroCarta = this.mao[c].getNumero();

            if(!somaPrimeiraCarta && c == 0) {
                numeroCarta = 0;
            }

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

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
}
