
import java.util.ArrayList;
import java.util.Collections;

public class Baralho {
    private ArrayList<Carta> cartas;

    // Construtor do baralho, inicia o array de cartas
    public Baralho() {
        this.cartas = new ArrayList<>();
    }

    /* Cria o baralho, recebe embaralhar como parâmetro, que já cria o baralho embaralhado
            Tem um for dentro de um for, criando todas as cartas de um mesmo naipe, depois de outro,
            utilizando o ENUM de 'Naipes(Naipes.values()[naipe]'.
     */
    public void montaBaralho(boolean embaralhar) {
        if(!this.cartas.isEmpty()) {
            cartas.clear();
        }

        for(int naipe = 0; naipe < 4; naipe++){

            for(int numero = 1; numero <= 13; numero++) {
                this.cartas.add(new Carta(Naipes.values()[naipe], numero));
            }
        }

        if (embaralhar) {
            this.embaralha();
        }
    }

    // Embaralha o ArrayList e executa o som de embaralhamento.
    public void embaralha() {
        Collections.shuffle(cartas);
        SoundsPlayer.SHUFFLE.play();
    }

    /*  Retorna a primeira carta do baralho depois
        deleta a primeira carta do baralho,
        executando o som de entrega de carta;
     */
    public Carta entregaCarta() {
        Carta topo = this.cartas.get(0);

        this.cartas.remove(0);
        SoundsPlayer.DEAL.play();
        return topo;
    }

    // Printa o baralho, para debug.
    public void printBaralho() {
        System.out.println(this.cartas);
    }
}
