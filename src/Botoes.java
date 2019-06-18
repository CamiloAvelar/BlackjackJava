public enum Botoes {
    Pegar(true),
    Ficar(true),
    Abandonar(true),
    Dobrar(true),
    Sim(false),
    Nao(false);

    private final boolean isAction;
    Botoes(final boolean isAction) { this.isAction = isAction; }
    public boolean getAction() {
        return this.isAction;
    }
}
