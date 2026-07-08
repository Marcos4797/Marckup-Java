public class produto {
    private String nome;
    private int estoque;
    private Categoria categoria;
    private double custoCompra;
    private int saldoInicial;
    private int totalEntradas;
    private int totalSaidas;

    public produto(String nome, double custoCompra, int saldoInicial, Categoria categoria) {
        this.nome = nome;
        this.custoCompra = custoCompra;
        this.saldoInicial = saldoInicial;
        this.estoque = saldoInicial;
        this.totalEntradas = 0;
        this.totalSaidas = 0;
        this.categoria = categoria;
    }

    //  Custo Médio
    public void registrarEntrada(int quantidade, double novoCustoCompra) {
        if (quantidade > 0) {
            int saldoAntes = getSaldoFinal();
            double custoTotalAntes = saldoAntes * this.custoCompra;
            double custoTotalNovaEntrada = quantidade * novoCustoCompra;

            this.totalEntradas += quantidade;
            this.estoque += quantidade;

            this.custoCompra = (custoTotalAntes + custoTotalNovaEntrada) / (saldoAntes + quantidade);

            System.out.printf("[Entrada] %s: %d un a R$ %.2f | Novo Custo Médio: R$ %.2f\n",
                    nome, quantidade, novoCustoCompra, this.custoCompra);
        }
    }

     public void registrarEntrada(int quantidade) {
        registrarEntrada(quantidade, this.custoCompra);
    }

    public void registrarSaida(int quantidade) {
        if (quantidade > 0 && (getSaldoFinal() - quantidade >= 0)) {
            this.totalSaidas += quantidade;
            this.estoque -= quantidade; // Baixa no estoque físico
        } else {
            System.out.println("Erro: Estoque insuficiente para a saída de " + nome);
        }
    }

    public int getSaldoFinal() {
        return this.saldoInicial + this.totalEntradas - this.totalSaidas;
    }

    public double getCustoCompra() { return custoCompra; }
    public int getSaldoInicial() { return saldoInicial; }
    public int getTotalEntradas() { return totalEntradas; }
    public String getNome() { return nome; }

    public double calcularPrecoVenda(configuracao_MK config) {
        double margemLucroCategoria = this.categoria.getMargemLucro();
        double indice = config.calcularIndiceMarkup(margemLucroCategoria);
        return this.custoCompra * indice; // O preço de venda flutuará com o custo médio!
    }

    public void exibirRelatorio(configuracao_MK config) {
        System.out.printf("--- Produto: %s ---\n", nome);
        System.out.printf("Custo Médio Atual: R$ %.2f\n", custoCompra);
        System.out.printf("Preço de Venda Sugerido (Baseado no Custo Médio): R$ %.2f\n", calcularPrecoVenda(config));
        System.out.println("Movimentação de Estoque:");
        System.out.println("  [+] Saldo Inicial: " + saldoInicial);
        System.out.println("  [+] Total Entradas: " + totalEntradas);
        System.out.println("  [-] Total Saídas: " + totalSaidas);
        System.out.println("  [=] Saldo Atual: " + getSaldoFinal());
        System.out.println("---------------------------\n");
    }
}
