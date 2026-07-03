public class produto {
    private String nome;
    private double custoCompra;
    private int saldoInicial;
    private int totalEntradas;
    private int totalSaidas;
    public produto(String nome, double custoCompra, int saldoInicial) {
        this.nome = nome;
        this.custoCompra = custoCompra;
        this.saldoInicial = saldoInicial;
        this.totalEntradas = 0;
        this.totalSaidas = 0;
    }
    public double calcularPrecoVenda(configuracao_MK config) {
        double indice = config.calcularIndiceMarkup();
        return this.custoCompra * indice;
}
    public void registrarEntrada(int quantidade) {
        if (quantidade > 0) {
            this.totalEntradas += quantidade;
        }
}

public void registrarSaida(int quantidade) {
        if (quantidade > 0 && (getSaldoFinal() - quantidade >= 0)) {
            this.totalSaidas += quantidade;
        } else {
            System.out.println("Erro: Estoque insuficiente para a saída de " + nome);
        }
}
    public int getSaldoFinal() {
        return this.saldoInicial + this.totalEntradas - this.totalSaidas;
}
    public void exibirPrecoInicial(configuracao_MK config) {
        System.out.printf("--- Produto: %s ---\n", nome);
        System.out.printf("Custo de Compra: R$ %.2f\n", custoCompra);
        System.out.printf("Preço de Venda Sugerido: R$ %.2f\n", calcularPrecoVenda(config));
        System.out.println("---------------------------\n");
    }
    public void exibirRelatorio(configuracao_MK config) {
        System.out.printf("--- Produto: %s ---\n", nome);
        System.out.printf("Custo de Compra: R$ %.2f\n", custoCompra);
        System.out.printf("Preço de Venda Sugerido: R$ %.2f\n", calcularPrecoVenda(config));
        System.out.println("Movimentação de Estoque:");
        System.out.println("  [+] Saldo Inicial: " + saldoInicial);
        System.out.println("  [+] Total Entradas: " + totalEntradas);
        System.out.println("  [-] Total Saídas: " + totalSaidas);
        System.out.println("  [=] Saldo Atual: " + getSaldoFinal());
        System.out.println("---------------------------\n");
    }

    public double getCustoCompra() { return custoCompra; }
    public int getSaldoInicial() { return saldoInicial; }
    public int getTotalEntradas() { return totalEntradas; }

    public String getNome() { return nome; }
}
