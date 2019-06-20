/* ENUM para auxiliar na criação dos botoes
permite que os botoes sejam criados em um loop,
evitando a criação de código repetido.
 */

public enum Botoes {
    Pegar(true),
    Ficar(true),
    Abandonar(true),
    Dobrar(true),
    Sim(false),
    Nao(false);

    // Funções para auxiliar na distinção entre botões de ação e botões de confirmação
    private final boolean isAction;
    Botoes(final boolean isAction) { this.isAction = isAction; }
    public boolean getAction() {
        return this.isAction;
    }
}
