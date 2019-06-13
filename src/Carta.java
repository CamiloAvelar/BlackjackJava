public class Carta {


    private Naipes naipe;
    private int numero;

    public Carta(Naipes naipe, int numero) {
        this.naipe = naipe;

        if(numero >= 1 && numero <= 13) {
            this.numero = numero;
        } else {
            System.err.println(numero + " não é uma Carta válida!");
            System.exit(1);
        }
    }

    public Naipes getNaipe() {
        return naipe;
    }

    public void setNaipe(Naipes naipe) {
        this.naipe = naipe;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String toString() {
        String numStr = "Err";

        switch(this.numero) {
            case 2:
                numStr = "Dois";
                break;
            case 3:
                numStr = "Três";
                break;
            case 4:
                numStr = "Quatro";
                break;
            case 5:
                numStr = "Cinco";
                break;
            case 6:
                numStr = "Seis";
                break;
            case 7:
                numStr = "Sete";
                break;
            case 8:
                numStr = "Oito";
                break;
            case 9:
                numStr = "Nove";
                break;
            case 10:
                numStr = "Dez";
                break;
            case 11:
                numStr = "Valete";
                break;
            case 12:
                numStr = "Dama";
                break;
            case 13:
                numStr = "Rei";
                break;
            case 1:
                numStr = "As";
                break;
        }

        return numStr + " de " + naipe.toString();
    }


}

