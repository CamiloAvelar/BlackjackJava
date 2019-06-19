import java.util.ArrayList;

public class Jogador {

    private String nome;

    private ArrayList<Carta> mao;

    private double creditos;
    private double aposta;

    public double getAposta() {
        return aposta;
    }

    public ArrayList<Carta> getMao() {
        return mao;
    }

    public void setAposta(double aposta) {
        this.aposta = aposta;
    }

    public String getNome() {
        return nome;
    }

    public void dobraAposta() {
        this.aposta *= 2;
    }

    public void abandonarPartida(Jogador dealer) {
        double aposta = this.aposta/2;
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

    public Jogador(String nome, double creditos) {
        this.nome = nome;
        this.creditos = creditos;
        this.mao = new ArrayList<>();

        this.esvaziaMao();
    }

    public void esvaziaMao() {
        if (!mao.isEmpty()) {
            mao.clear();
        }
    }

    public boolean addCarta(Carta carta) {
        this.mao.add(carta);

        return (this.somaMao(true) <= 21);
    }

    public int somaMao(boolean somaPrimeiraCarta) {
        int somaMao = 0;
        int numeroCarta;
        int numeroAs = 0;

        int cont = 0;
        for(Carta c : mao) {
            numeroCarta = c.getNumero();

            if(!somaPrimeiraCarta && cont == 0) {
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

            cont++;
        }

        while (somaMao > 21 && numeroAs > 0) {
            somaMao -= 10;
            numeroAs--;
        }


        return somaMao;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }
}
