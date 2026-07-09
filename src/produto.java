public class produto {
    // ATRIBUTOS EXISTENTES
    private String nome;
    private double custoCompra;
    private int saldoInicial;
    private int totalEntradas;
    private int totalSaidas;
    private int saldoFinal;
    private Categoria categoria;

    // NOVO ATRIBUTO: Guarda o dinheiro real que saiu do caixa em cada NF de compra
    private double totalGastoCompras;

    // CONSTRUTOR ATUALIZADO
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

    // MÉTODO DE ENTRADA ATUALIZADO (Soma o estoque e calcula o custo médio)
    public void registrarEntrada(int quantidade, double novoCusto) {
        // 1. Registra o dinheiro real que saiu do caixa nesta compra específica
        this.totalGastoCompras += (quantidade * novoCusto);

        // 2. Lógica do Custo Médio Ponderado
        // (Soma o valor do que sobrou com o valor do que está entrando e divide pelo total de itens)
        this.custoCompra = ((this.saldoFinal * this.custoCompra) + (quantidade * novoCusto)) / (this.saldoFinal + quantidade);

        // 3. Atualiza os contadores físicos de estoque
        this.totalEntradas += quantidade;
        this.saldoFinal += quantidade;
    }

    // MÉTODO DE SAÍDA (Vendas)
    public void registrarSaida(int quantidade) {
        this.totalSaidas += quantidade;
        this.saldoFinal -= quantidade;
    }

    // CALCULA O PREÇO DE VENDA BASEADO NO MARKUP E NA CATEGORIA
    public double calcularPrecoVenda(configuracao_MK config) {
        // Exemplo de cálculo usando o Markup da sua configuração e a margem da categoria
        double margemLucro = this.categoria.getMargemLucro() / 100.0;
        double despesasFixas = config.getDespesasFixas() / 100.0;
        double despesasVariaveis = config.getDespesasVariaveis() / 100.0;

        double divisorMarkup = 1 - (despesasFixas + despesasVariaveis + margemLucro);

        if (divisorMarkup <= 0) {
            return this.custoCompra * 2; // Margem de segurança caso o markup estoure
        }

        return this.custoCompra / divisorMarkup;
    }

    // EXIBE O RELATÓRIO INDIVIDUAL DO PRODUTO (Usado no loop da Main)
    public void exibirRelatorio(configuracao_MK config) {
        System.out.printf("Produto: %-20s | Custo Médio: R$ %-6.2f | Estoque Atual: %-3d | Preço Sugerido: R$ %-6.2f\n",
                this.nome, this.custoCompra, this.saldoFinal, calcularPrecoVenda(config));
    }

    // GETTERS E SETTERS
    public String getNome() { return nome; }
    public double getCustoCompra() { return custoCompra; }
    public int getSaldoInicial() { return saldoInicial; }
    public int getTotalEntradas() { return totalEntradas; }
    public int getTotalSaidas() { return totalSaidas; }
    public int getSaldoFinal() { return saldoFinal; }
    public Categoria getCategoria() { return categoria; }

    // NOVO GETTER: Permite que a classe BalancoFinanceiro puxe o valor exato gasto em compras
    public double getTotalGastoCompras() {
        return totalGastoCompras;
    }
}