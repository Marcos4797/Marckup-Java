import java.util.List;
import java.util.Locale;

public class BalancoFinanceiro {

    public void gerarRelatorioFinanceiro(List<produto> estoque, configuracao_MK config, List<Cliente> clientes) {
        double receitaTotal = 0.0;
        double custoTotalVendido = 0.0;

        Locale localeBR = Locale.of("pt", "BR");

        System.out.println("\n=========================================================================================");
        System.out.println("                          === DEMONSTRATIVO DE VENDAS POR PRODUTO ===                    ");
        System.out.println("=========================================================================================");
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

        String totReceita = String.format(localeBR, "%,.2f", receitaTotal);
        String totCusto = String.format(localeBR, "%,.2f", custoTotalVendido);
        String totLucro = String.format(localeBR, "%,.2f", lucroBrutoTotal);

        System.out.printf("%-20s | %-8s | %-14s | %-13s | %-13s | %-13s\n",
                "TOTAL", "", "", totReceita, totCusto, totLucro);

        System.out.println("=========================================================================================");


        double contasAReceber = 0.0;
        for (Cliente c : clientes) {
            contasAReceber += c.getSaldoDevedor();
        }

        double totalDespesasFixas = receitaTotal * (config.getDespesasFixas() / 100.0);
        double totalDespesasVariaveis = receitaTotal * (config.getDespesasVariaveis() / 100.0);
        double dinheiroRealNoCaixa = receitaTotal - totalDespesasFixas - totalDespesasVariaveis - contasAReceber;

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
        System.out.printf(localeBR, "(=) LUCRO LÍQUIDO FINAL:           R$ %,.2f\n", lucroLiquidoFinal);
        System.out.println("============================================= ");

        System.out.println("\n=============================================");
        System.out.println("          2. FLUXO DE CAIXA REAL             ");
        System.out.println("=============================================");
        System.out.printf(localeBR, "(+) DINHEIRO QUE DEVERIA ENTRAR:   R$ %,.2f\n", receitaTotal);
        System.out.printf(localeBR, "(-) VENDAS NO FIADO (A Receber):   R$ %,.2f\n", contasAReceber);
        System.out.printf(localeBR, "(-) DESPESAS PAGAS DO MERCADO:     R$ %,.2f\n", (totalDespesasFixas + totalDespesasVariaveis));
        System.out.println("---------------------------------------------");
        System.out.printf(localeBR, "(=) DINHEIRO REAL FÍSICO NO CAIXA:  R$ %,.2f\n", dinheiroRealNoCaixa);
        System.out.println("=============================================\n");
    }
}