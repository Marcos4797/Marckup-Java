import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Lote {
    private int quantidadeInicial;
    private int quantidadeAtual;
    private double custoCompra;
    private LocalDate dataEntrada;
    private LocalDate dataValidade;

    public Lote(int quantidade, double custoCompra, LocalDate dataEntrada, LocalDate dataValidade) {
        this.quantidadeInicial = quantidade;
        this.quantidadeAtual = quantidade;
        this.custoCompra = custoCompra;
        this.dataEntrada = dataEntrada;
        this.dataValidade = dataValidade;
    }
    public long getDiasEmEstoque(LocalDate dataReferencia) {
        return ChronoUnit.DAYS.between(dataEntrada, dataReferencia);
    }
    public long getDiasParaVencer(LocalDate dataReferencia) {
        return ChronoUnit.DAYS.between(dataReferencia, dataValidade);
    }
    public int consumir(int qtdDesejada) {
        if (qtdDesejada >= quantidadeAtual) {
            int consumido = quantidadeAtual;
            this.quantidadeAtual = 0;
            return consumido;
        } else {
            this.quantidadeAtual -= qtdDesejada;
            return qtdDesejada;
        }
    }
        public int getQuantidadeAtual() { return quantidadeAtual; }
        public double getCustoCompra() { return custoCompra; }
        public LocalDate getDataValidade() { return dataValidade; }
        public LocalDate getDataEntrada() { return dataEntrada; }
    }
