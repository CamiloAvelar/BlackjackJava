
import java.util.ArrayList;
import java.util.Collections;

public class Baralho {
    private ArrayList<Carta> cartas;

    public Baralho() {
        this.cartas = new ArrayList<>();
    }

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

    public void embaralha() {
        Collections.shuffle(cartas);
        SoundsPlayer.SHUFFLE.play();
    }

    public Carta entregaCarta() {
        Carta topo = this.cartas.get(0);

        this.cartas.remove(0);
        SoundsPlayer.DEAL.play();
        return topo;
    }

    public void printBaralho() {
        System.out.println(this.cartas);
    }
}
