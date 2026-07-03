import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // ENTRADA DOS DAS TAXAS
        System.out.println("=== CONFIGURAÇÃO DE TAXAS DO MERCADO ===");
        System.out.print("Digite o % de Despesas Fixas (DF): ");
        double df = teclado.nextDouble();

        System.out.print("Digite o % de Despesas Variáveis (DV): ");
        double dv = teclado.nextDouble();

        System.out.print("Digite o % de Lucro Desejado (L): ");
        double lucro = teclado.nextDouble();

        // LISTA FIXA DE PRODUTOS - VAI ENTRAR PRODUTOS SCANNER
        configuracao_MK markupMercado = new configuracao_MK(df, dv, lucro);

        List<produto> estoque = new ArrayList<>();
        estoque.add(new produto("Arroz 5kg", 18.50, 50));
        estoque.add(new produto("Feijão Preto 1kg", 7.20, 30));
        estoque.add(new produto("Óleo de Soja", 5.10, 100));

        // EXIBIR A PRECIFICACAO
        System.out.println("\n=== ESTOQUE INICIAL E PRECIFICAÇÃO ===\n");
        for (produto p : estoque) {
            p.exibirPrecoInicial(markupMercado);
        }

        // MOVIMENTAÇÃO DE ESTOQUE
        System.out.println("=== SIMULANDO MOVIMENTAÇÕES ===\n");
        produto arroz = estoque.get(0);
        arroz.registrarEntrada(20);
        arroz.registrarSaida(10);

        produto feijao = estoque.get(1);
        feijao.registrarEntrada(30);
        feijao.registrarSaida(15);

        produto oleo = estoque.get(2);
        oleo.registrarEntrada(50);
        oleo.registrarSaida(20);

        // RELATORIO FINAL
        System.out.println("=== RELATÓRIO DE SALDO FINAL ===");
        for (produto p : estoque) {
            p.exibirRelatorio(markupMercado);
        }

        //  DEMONSTRATIVO FINANCEIRO- FORMATO DE TABELA
        BalancoFinanceiro balanco = new BalancoFinanceiro();
        balanco.gerarRelatorioFinanceiro(estoque, markupMercado);

        teclado.close();
    }
}