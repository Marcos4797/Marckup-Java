import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class produto {
    private String nome;
    private double custoCompra;
    private int saldoInicial;
    private int totalEntradas;
    private int totalSaidas;
    private int saldoFinal;
    private Categoria categoria;
    private double totalGastoCompras;
    private List<Lote> lotes;

    public produto(String nome, double custoCompra, int saldoInicial, Categoria categoria) {
        this.nome = nome;
        this.custoCompra = custoCompra;
        this.saldoInicial = saldoInicial;
        this.totalEntradas = 0;
        this.totalSaidas = 0;
        this.saldoFinal = saldoInicial;
        this.categoria = categoria;
        this.totalGastoCompras = 0.0;
        this.lotes = new ArrayList<>();
    }
    public void registrarEntrada(int quantidade, double novoCusto) {
        LocalDate hoje = LocalDate.now();
        LocalDate validadePadrao = hoje.plusDays(30);

        this.registrarEntrada(quantidade, novoCusto, hoje, validadePadrao);
    }

        public void registrarEntrada(int quantidade, double novoCusto, LocalDate dataEntrada, LocalDate dataValidade) {
        this.totalGastoCompras += (quantidade * novoCusto);

        this.custoCompra = ((this.saldoFinal * this.custoCompra) + (quantidade * novoCusto)) / (this.saldoFinal + quantidade);

        this.totalEntradas += quantidade;
        this.saldoFinal += quantidade;
        this.lotes.add(new Lote(quantidade, novoCusto, dataEntrada, dataValidade));
    }

    public void registrarSaida(int quantidade) {
        this.totalSaidas += quantidade;
        this.saldoFinal -= quantidade;

        int restanteParaConsumir = quantidade;

        while (restanteParaConsumir > 0) {
            Lote loteMaisProximo = encontrarLoteMaisProximoDoVencimento();
            if (loteMaisProximo == null) {
                break;
            }
            int consumido = loteMaisProximo.consumir(restanteParaConsumir);
            restanteParaConsumir -= consumido;

            if (loteMaisProximo.getQuantidadeAtual() == 0) {
                lotes.remove(loteMaisProximo); // Remove o lote caso zere
            }
        }
    }

    private Lote encontrarLoteMaisProximoDoVencimento() {
        Lote maisProximo = null;
        for (Lote l : lotes) {
            if (l.getQuantidadeAtual() > 0) {
                if (maisProximo == null || l.getDataValidade().isBefore(maisProximo.getDataValidade())) {
                    maisProximo = l;
                }
            }
        }
        return maisProximo;
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

    public double calcularPrecoVenda(configuracao_MK config, LocalDate dataReferencia) {
        double precoBase = calcularPrecoVenda(config);
        Lote lotePerigo = encontrarLoteMaisProximoDoVencimento();

        if (lotePerigo != null) {
            long diasParaVencer = lotePerigo.getDiasParaVencer(dataReferencia);

            if (diasParaVencer <= 0) {
                return 0.0;
            } else if (diasParaVencer <= 5) {
                return precoBase * 0.70;
            } else if (diasParaVencer <= 15) {
                return precoBase * 0.85;
            }
        }
        return precoBase;
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
    public double getTotalGastoCompras() { return totalGastoCompras; }
    public List<Lote> getLotes() { return lotes; }

    public void exibirAnaliseLotes() {
        LocalDate hoje = LocalDate.now();
        System.out.println("\n>>> Detalhes de Lotes ativos para: " + this.nome);
        if (lotes.isEmpty()) {
            System.out.println("    Sem lotes ativos em estoque.");
            return;
        }
        for (int i = 0; i < lotes.size(); i++) {
            Lote lote = lotes.get(i);
            long diasEstoque = lote.getDiasEmEstoque(hoje);
            long diasVencer = lote.getDiasParaVencer(hoje);

            System.out.printf("    [Lote %d] Qtd Atual: %-3d | Custo Lote: R$ %-6.2f | No Estoque há: %d dias | Vence em: %d dias %s\n",
                    (i + 1),
                    lote.getQuantidadeAtual(),
                    lote.getCustoCompra(),
                    diasEstoque,
                    diasVencer,
                    (diasVencer <= 5 ? "⚠️ (PROMOÇÃO ATIVA - 30% DESCONTO!)" : (diasVencer <= 15 ? "⚠️ (PROMOÇÃO ATIVA - 15% DESCONTO!)" : ""))
            );
        }
    }

}