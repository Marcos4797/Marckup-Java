import java.util.List;
public class BalancoFinanceiro {
    public void gerarRelatorioFinanceiro(List<produto> estoque, configuracao_MK config) {
        double receitaTotal = 0.0;
        double custoTotalVendido = 0.0;

        System.out.println("\n=========================================================================================");
        System.out.println("                          === DEMONSTRATIVO DE VENDAS POR PRODUTO ===                    ");
        System.out.println("=========================================================================================");
        // Cabeçalho da Tabela
        System.out.printf("%-20s | %-8s | %-14s | %-13s | %-13s | %-13s\n",
                "PRODUTO", "QTD VEND", "PREÇO UN (R$)", "RECEITA (R$)", "CUSTO TOT(R$)", "LUCRO BRU(R$)");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (produto p : estoque) {
            int qtdVendida = p.getSaldoInicial() + p.getTotalEntradas() - p.getSaldoFinal();

            if (qtdVendida > 0) {
                double precoVenda = p.calcularPrecoVenda(config);
                double receitaProduto = qtdVendida * precoVenda;
                double custoProduto = qtdVendida * p.getCustoCompra();
                double lucroBrutoProduto = receitaProduto - custoProduto;

                receitaTotal += receitaProduto;
                custoTotalVendido += custoProduto;

                // Linha do produto formatada e perfeitamente alinhada
                System.out.printf("%-20s | %-8d | %-14.2f | %-13.2f | %-13.2f | %-13.2f\n",
                        p.getNome(), qtdVendida, precoVenda, receitaProduto, custoProduto, lucroBrutoProduto);
            }
        }
        System.out.println("=========================================================================================");

        double lucroBrutoTotal = receitaTotal - custoTotalVendido;
        double totalDespesasFixas = receitaTotal * (config.getDespesasFixas() / 100.0);
        double totalDespesasVariaveis = receitaTotal * (config.getDespesasVariaveis() / 100.0);
        double lucroLiquidoFinal = lucroBrutoTotal - totalDespesasFixas - totalDespesasVariaveis;

        System.out.println("\n=============================================");
        System.out.println("      RESULTADO CONSOLIDADO DO MERCADO       ");
        System.out.println("=============================================");
        System.out.printf("(+) RECEITA BRUTA TOTAL (Vendas):  R$ %.2f\n", receitaTotal);
        System.out.printf("(-) CUSTO DAS MERCADORIAS (CMV):   R$ %.2f\n", custoTotalVendido);
        System.out.printf("(=) LUCRO BRUTO CONSOLIDADO:       R$ %.2f\n", lucroBrutoTotal);
        System.out.printf("(-) DESPESAS FIXAS (%s%%):           R$ %.2f\n", config.getDespesasFixas(), totalDespesasFixas);
        System.out.printf("(-) DESPESAS VARIÁVEIS (%s%%):       R$ %.2f\n", config.getDespesasVariaveis(), totalDespesasVariaveis);
        System.out.println("---------------------------------------------");
        System.out.printf("(=) LUCRO LÍQUIDO FINAL DO MERCADO: R$ %.2f\n", lucroLiquidoFinal);
        System.out.println("=============================================\n");
    }
}
