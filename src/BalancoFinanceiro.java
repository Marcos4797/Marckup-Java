import java.util.List;
import java.util.Locale;

public class BalancoFinanceiro {

    public void gerarRelatorioFinanceiro(List<produto> estoque, configuracao_MK config) {
        double receitaTotal = 0.0;
        double custoTotalVendido = 0.0;
        double totalInvestidoEstoque = 0.0;

        Locale localeBR = Locale.of("pt", "BR");

        // =========================================================================================
        // 1. DEMONSTRATIVO DE VENDAS POR PRODUTO
        // =========================================================================================
        System.out.println("\n=========================================================================================");
        System.out.println("                          === DEMONSTRATIVO DE VENDAS POR PRODUTO ===                    ");
        System.out.println("=========================================================================================");
        System.out.printf("%-20s | %-8s | %-14s | %-13s | %-13s | %-13s\n",
                "PRODUTO", "QTD VEND", "PREÇO UN (R$)", "RECEITA (R$)", "CUSTO TOT(R$)", "LUCRO BRU(R$)");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (produto p : estoque) {
            // Puxa o dinheiro exato e real que saiu do caixa para as NFs deste produto
            totalInvestidoEstoque += p.getTotalGastoCompras();

            int qtdVendida = p.getSaldoInicial() + p.getTotalEntradas() - p.getSaldoFinal();

            if (qtdVendida > 0) {
                double precoVenda = p.calcularPrecoVenda(config);
                double receitaProduto = qtdVendida * precoVenda;
                double custoProduto = qtdVendida * p.getCustoCompra();
                double lucroBrutoProduto = receitaProduto - custoProduto;

                receitaTotal += receitaProduto;
                custoTotalVendido += custoProduto;

                String txtPreco = String.format(localeBR, "%,.2f", precoVenda);
                String txtReceita = String.format(localeBR, "%,.2f", receitaProduto);
                String txtCusto = String.format(localeBR, "%,.2f", custoProduto);
                String txtLucro = String.format(localeBR, "%,.2f", lucroBrutoProduto);

                System.out.printf("%-20s | %-8d | %-14s | %-13s | %-13s | %-13s\n",
                        p.getNome(), qtdVendida, txtPreco, txtReceita, txtCusto, txtLucro);
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------");

        double lucroBrutoTotal = receitaTotal - custoTotalVendido;

        System.out.printf("%-20s | %-8s | %-14s | %-13s | %-13s | %-13s\n",
                "TOTAL", "", "",
                String.format(localeBR, "%,.2f", receitaTotal),
                String.format(localeBR, "%,.2f", custoTotalVendido),
                String.format(localeBR, "%,.2f", lucroBrutoTotal));
        System.out.println("=========================================================================================");

        // =========================================================================================
        // 2. RESULTADO CONSOLIDADO E FLUXO DE CAIXA REAL
        // =========================================================================================
        double totalDespesasFixas = receitaTotal * (config.getDespesasFixas() / 100.0);
        double totalDespesasVariaveis = receitaTotal * (config.getDespesasVariaveis() / 100.0);

        // AJUSTADO PARA R$ 5.000,00 CONFORME SOLICITADO
        double saldoInicialCaixa = 5000.00;
        double dinheiroRealNoCaixa = saldoInicialCaixa + receitaTotal - totalDespesasFixas - totalDespesasVariaveis - totalInvestidoEstoque;

        System.out.println("\n=============================================");
        System.out.println("      RESULTADO CONSOLIDADO DO MERCADO       ");
        System.out.println("=============================================");
        System.out.printf(localeBR, "(+) RECEITA BRUTA TOTAL (Vendas):      R$ %,.2f\n", receitaTotal);
        System.out.printf(localeBR, "(-) CUSTO DAS MERCADORIAS (CMV):       R$ %,.2f\n", custoTotalVendido);
        System.out.printf(localeBR, "(=) LUCRO BRUTO CONSOLIDADO:           R$ %,.2f\n", lucroBrutoTotal);
        System.out.printf(localeBR, "(-) DESPESAS FIXAS (%.1f%%):           R$ %,.2f\n", config.getDespesasFixas(), totalDespesasFixas);
        System.out.printf(localeBR, "(-) DESPESAS VARIÁVEIS (%.1f%%):       R$ %,.2f\n", config.getDespesasVariaveis(), totalDespesasVariaveis);
        System.out.println("---------------------------------------------");
        double lucroLiquidoFinal = lucroBrutoTotal - totalDespesasFixas - totalDespesasVariaveis;
        System.out.printf(localeBR, "(=) LUCRO LÍQUIDO FINAL:               R$ %,.2f\n", lucroLiquidoFinal);
        System.out.println("============================================= ");

        System.out.println("\n=============================================");
        System.out.println("          2. FLUXO DE CAIXA REAL             ");
        System.out.println("=============================================");
        System.out.printf(localeBR, "(+) SALDO ANTERIOR / APORTE INICIAL:   R$ %,.2f\n", saldoInicialCaixa);
        System.out.printf(localeBR, "(+) DINHEIRO ENTRADO (VENDAS À VISTA): R$ %,.2f\n", receitaTotal);
        System.out.printf(localeBR, "(-) PAGAMENTO DE FORNECEDORES (COMPRAS):R$ %,.2f\n", totalInvestidoEstoque);
        System.out.printf(localeBR, "(-) DESPESAS PAGAS DO MERCADO:         R$ %,.2f\n", (totalDespesasFixas + totalDespesasVariaveis));
        System.out.println("---------------------------------------------");
        System.out.printf(localeBR, "(=) DINHEIRO REAL FÍSICO NO CAIXA:     R$ %,.2f\n", dinheiroRealNoCaixa);
        System.out.println("=============================================\n");

        // =========================================================================================
        // 3. VALORAÇÃO E LUCRO POTENCIAL DO ESTOQUE ATUAL
        // =========================================================================================
        System.out.println("=========================================================================================");
        System.out.println("                    === 3. VALORAÇÃO E LUCRO POTENCIAL DO ESTOQUE ATUAL ===              ");
        System.out.println("=========================================================================================");
        System.out.printf("%-20s | %-11s | %-18s | %-18s | %-15s\n",
                "PRODUTO", "SALDO ATUAL", "VALOR CUSTO (R$)", "VALOR VENDA (R$)", "LUCRO POT. (R$)");
        System.out.println("-----------------------------------------------------------------------------------------");

        double totalCustoEstoque = 0.0;
        double totalVendaEstoque = 0.0;

        for (produto p : estoque) {
            int saldoAtual = p.getSaldoFinal();

            if (saldoAtual > 0) {
                double custoEstoque = saldoAtual * p.getCustoCompra();
                double vendaEstoque = saldoAtual * p.calcularPrecoVenda(config);
                double lucroPotencial = vendaEstoque - custoEstoque;

                totalCustoEstoque += custoEstoque;
                totalVendaEstoque += vendaEstoque;

                System.out.printf("%-20s | %-11d | %-18s | %-18s | %-15s\n",
                        p.getNome(),
                        saldoAtual,
                        String.format(localeBR, "%,.2f", custoEstoque),
                        String.format(localeBR, "%,.2f", vendaEstoque),
                        String.format(localeBR, "%,.2f", lucroPotencial));
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        double totalLucroPotencial = totalVendaEstoque - totalCustoEstoque;
        System.out.printf("%-20s | %-11s | %-18s | %-18s | %-15s\n",
                "TOTAL EM ESTOQUE", "",
                String.format(localeBR, "%,.2f", totalCustoEstoque),
                String.format(localeBR, "%,.2f", totalVendaEstoque),
                String.format(localeBR, "%,.2f", totalLucroPotencial));
        System.out.println("=========================================================================================\n");
    }
}