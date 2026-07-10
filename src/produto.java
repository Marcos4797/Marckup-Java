public class produto {
    private String nome;
    private double custoCompra;
    private int saldoInicial;
    private int totalEntradas;
    private int totalSaidas;
    private int saldoFinal;
    private Categoria categoria;
    private double totalGastoCompras;

    public produto(String nome, double custoCompra, int saldoInicial, Categoria categoria) {
        this.nome = nome;
        this.custoCompra = custoCompra;
        this.saldoInicial = saldoInicial;
        this.totalEntradas = 0;
        this.totalSaidas = 0;
        this.saldoFinal = saldoInicial;
        this.categoria = categoria;
        this.totalGastoCompras = 0.0; // Inicializa o acumulador financeiro zerado
    }

    public void registrarEntrada(int quantidade, double novoCusto) {

        this.totalGastoCompras += (quantidade * novoCusto);
        this.custoCompra = ((this.saldoFinal * this.custoCompra) + (quantidade * novoCusto)) / (this.saldoFinal + quantidade);
        this.totalEntradas += quantidade;
        this.saldoFinal += quantidade;
    }
    public void registrarSaida(int quantidade) {
        this.totalSaidas += quantidade;
        this.saldoFinal -= quantidade;
    }

    public double calcularPrecoVenda(configuracao_MK config) {
        double margemLucro = this.categoria.getMargemLucro() / 100.0;
        double despesasFixas = config.getDespesasFixas() / 100.0;
        double despesasVariaveis = config.getDespesasVariaveis() / 100.0;

        double divisorMarkup = 1 - (despesasFixas + despesasVariaveis + margemLucro);

        if (divisorMarkup <= 0) {
            return this.custoCompra * 2;
        }

        return this.custoCompra / divisorMarkup;
    }

    public void exibirRelatorio(configuracao_MK config) {
        System.out.printf("Produto: %-20s | Custo Médio: R$ %-6.2f | Estoque Atual: %-3d | Preço Sugerido: R$ %-6.2f\n",
                this.nome, this.custoCompra, this.saldoFinal, calcularPrecoVenda(config));
    }

    public String getNome() { return nome; }
    public double getCustoCompra() { return custoCompra; }
    public int getSaldoInicial() { return saldoInicial; }
    public int getTotalEntradas() { return totalEntradas; }
    public int getTotalSaidas() { return totalSaidas; }
    public int getSaldoFinal() { return saldoFinal; }
    public Categoria getCategoria() { return categoria; }
    public double getTotalGastoCompras() {
        return totalGastoCompras;
    }
}