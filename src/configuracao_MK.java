public class configuracao_MK {
    private double despesasFixas;
    private double despesasVariaveis;
    private double margemLucro;

    public configuracao_MK (double despesasFixas, double despesasVariaveis, double margemLucro) {
        this.despesasFixas = despesasFixas;
        this.despesasVariaveis = despesasVariaveis;
        this.margemLucro = margemLucro;
    }
    public double calcularIndiceMarkup() {
        double somaPercentuais = despesasFixas + despesasVariaveis + margemLucro;
        if (somaPercentuais >= 100) {
            throw new IllegalArgumentException("A soma das taxas não pode ser maior ou igual a 100%");
        }
        return 100.0 / (100.0 - somaPercentuais);
    }

    public double calcularIndiceMarkup(double margemLucroCategoria) {
        double somaPercentuais = despesasFixas + despesasVariaveis + margemLucroCategoria;
        if (somaPercentuais >= 100) {
            throw new IllegalArgumentException("A soma das taxas (DF + DV + Lucro Categoria) não pode ser maior ou igual a 100%");
        }
        return 100.0 / (100.0 - somaPercentuais);
    }
    public double getDespesasFixas() {
        return despesasFixas;
    }
    public double getDespesasVariaveis() {
        return despesasVariaveis;
    }
    public double getMargemLucro() {
        return margemLucro;
    }
}