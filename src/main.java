import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("=== CONFIGURAÇÃO DE TAXAS DO MERCADO ===");
        System.out.print("Digite o % de Despesas Fixas (DF): ");
        double df = teclado.nextDouble();
        System.out.print("Digite o % de Despesas Variáveis (DV): ");
        double dv = teclado.nextDouble();

        System.out.println("\n=== DEFINIR MARGEM DE LUCRO POR CATEGORIA ===");
        for (Categoria cat : Categoria.values()) {
            System.out.print("Digite o % de Lucro para [" + cat.getDescricao() + "]: ");
            double lucroCat = teclado.nextDouble();
            cat.setMargemLucro(lucroCat);
        }
        configuracao_MK markupMercado = new configuracao_MK(df, dv, 0);

        // PRODUTOS SALDO INICIAL = 0
        List<produto> estoque = new ArrayList<>();
        estoque.add(new produto("Arroz 5kg", 18.50, 0, Categoria.BASICOS));
        estoque.add(new produto("Feijão Preto 1kg", 7.20, 0, Categoria.BASICOS));
        estoque.add(new produto("Refrigerante 2L", 6.00, 0, Categoria.BEBIDAS));
        estoque.add(new produto("Sabão em Pó", 12.00, 0, Categoria.LIMPEZA));
        estoque.add(new produto("Creme Dental", 9.00, 0, Categoria.HIGIENE));
        estoque.add(new produto("Salame", 22.00, 0, Categoria.FRIOS));
        estoque.add(new produto("Banana", 12.00, 0, Categoria.HORTIFRUTI));
        estoque.add(new produto("Pão Francês", 29.00, 0, Categoria.PADARIA));
        estoque.add(new produto("Vaso de Cristal", 80.00, 0, Categoria.CONVENIENCIA));

        produto arroz = estoque.get(0);
        produto feijao = estoque.get(1);
        produto refrigerante = estoque.get(2);
        produto sabao = estoque.get(3);
        produto cremedental = estoque.get(4);
        produto salame = estoque.get(5);
        produto banana = estoque.get(6);
        produto pao = estoque.get(7);
        produto vaso = estoque.get(8);
        LocalDate hoje = LocalDate.now();

       // 1º COMPRAS e VENDAS (Ajustadas com controle de datas em alguns produtos)
       // ARROZ: Compra antiga (há 40 dias) com validade longa de 6 meses (180 dias)
        arroz.registrarEntrada(30, 18.50, hoje.minusDays(40), hoje.plusDays(180));
        arroz.registrarSaida(12);

        feijao.registrarEntrada(20, 7.20);
        feijao.registrarSaida(15);

        refrigerante.registrarEntrada(20, 6.00);
        refrigerante.registrarSaida(10);

        // SABÃO EM PÓ: Compra antiga (há 10 dias) com validade crítica (vence em 4 dias!)
        sabao.registrarEntrada(25, 12.00, hoje.minusDays(10), hoje.plusDays(4));
        sabao.registrarSaida(5);

        cremedental.registrarEntrada(25, 9.00);
        cremedental.registrarSaida(10);

        salame.registrarEntrada(10, 22.00);
        salame.registrarSaida(6);

        banana.registrarEntrada(5, 12.00);
        banana.registrarSaida(3);

        pao.registrarEntrada(5, 29.00);
        pao.registrarSaida(2);

        vaso.registrarEntrada(10, 80.00);
        vaso.registrarSaida(4);

        // 2º COMPRAS e VENDAS
        // ARROZ: Nova compra realizada hoje com validade longa de 6 meses
        arroz.registrarEntrada(15, 24.00, hoje, hoje.plusDays(180));
        arroz.registrarSaida(8);

        feijao.registrarEntrada(20, 9.50);
        feijao.registrarSaida(12);

        refrigerante.registrarEntrada(30, 6.80);
        refrigerante.registrarSaida(15);

        sabao.registrarEntrada(10, 10.50);
        sabao.registrarSaida(7);

        cremedental.registrarEntrada(15, 13.00);
        cremedental.registrarSaida(5);

        salame.registrarEntrada(10, 26.00);
        salame.registrarSaida(8);

        banana.registrarEntrada(15, 15.00);
        banana.registrarSaida(12);

        pao.registrarEntrada(50, 32.00);
        pao.registrarSaida(40);

        vaso.registrarEntrada(5, 110.00);
        vaso.registrarSaida(2);

        // EXIBIÇÃO RELATÓRIOS
         System.out.println("\n=== RELATÓRIO DE SALDO FINAL DE ESTOQUE ===");
        for (produto p : estoque) {
            p.exibirRelatorio(markupMercado);
        }

        // Painel para Auditar Lotes, Idade de Estoque e Alertas de Validade
        System.out.println("\n=========================================================================================");
        System.out.println("                 === AUDITORIA DE LOTES E ANÁLISE DE PROMOÇÕES POR VALIDADE ===          ");
        System.out.println("=========================================================================================");
        arroz.exibirAnaliseLotes();
        sabao.exibirAnaliseLotes();
        System.out.println("=========================================================================================\n");

        BalancoFinanceiro balanco = new BalancoFinanceiro();
        balanco.gerarRelatorioFinanceiro(estoque, markupMercado);

        teclado.close();
    }
}