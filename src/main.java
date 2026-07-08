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

        System.out.println("\n=== DEFINIR MARGEM DE LUCRO POR CATEGORIA ===");
        for (Categoria cat : Categoria.values()) {
            System.out.print("Digite o % de Lucro para [" + cat.getDescricao() + "]: ");
            double lucroCat = teclado.nextDouble();
            cat.setMargemLucro(lucroCat);
        }
        // LISTA FIXA DE PRODUTOS
        configuracao_MK markupMercado = new configuracao_MK(df, dv, 0);

        List<produto> estoque = new ArrayList<>();
        estoque.add(new produto("Arroz 5kg", 18.50, 30, Categoria.BASICOS));       // Índice 0
        estoque.add(new produto("Feijão Preto 1kg", 7.20, 20, Categoria.BASICOS)); // Índice 1
        estoque.add(new produto("Refrigerante 2L", 6.00, 20, Categoria.BEBIDAS));  // Índice 2
        estoque.add(new produto("Sabão em Pó", 12.00, 25, Categoria.LIMPEZA));     // Índice 3
        estoque.add(new produto("Creme Dental", 9.00, 25, Categoria.HIGIENE));     // Índice 4
        estoque.add(new produto("Salame", 22.00, 10, Categoria.FRIOS));            // Índice 5
        estoque.add(new produto("Banana", 12.00, 5, Categoria.HORTIFRUTI));        // Índice 6
        estoque.add(new produto("Pão Francês", 29.00, 5, Categoria.PADARIA));      // Índice 7
        estoque.add(new produto("Vaso de Cristal", 80.00, 10, Categoria.CONVENIENCIA)); // Índice 8


        // PRIMEIRO FLUXO - MOVIMENTAÇÃO DE ESTOQUE (PREÇOS ORIGINAIS)
        produto arroz = estoque.get(0);

        produto feijao = estoque.get(1);
        feijao.registrarEntrada(30);
        feijao.registrarSaida(15);

        produto refrigerante = estoque.get(2);
        refrigerante.registrarEntrada(15);
        refrigerante.registrarSaida(10);

        produto sabao = estoque.get(3);
        sabao.registrarEntrada(10);
        sabao.registrarSaida(5);

        produto cremedental = estoque.get(4);
        cremedental.registrarEntrada(20);
        cremedental.registrarSaida(10);

        produto salame = estoque.get(5);
        salame.registrarEntrada(20);
        salame.registrarSaida(10);

        produto banana = estoque.get(6);
        banana.registrarEntrada(25);
        banana.registrarSaida(10);

        produto pao = estoque.get(7);
        pao.registrarEntrada(15);
        pao.registrarSaida(10);

        produto vaso = estoque.get(8);
        vaso.registrarEntrada(20);
        vaso.registrarSaida(10);


        // SEGUNDO FLUXO - NOVAS ENTRADAS COM PREÇOS MANUAIS E NOVAS VENDAS

        System.out.println("\n--- PROCESSANDO NOVOS LOTES DE ENTRADA (CUSTOS VARIADOS) ---");


        arroz.registrarEntrada(15, 24.00);
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

        System.out.println("------------------------------------------------------------\n");

        // CADASTRO DE CLIENTE
        System.out.println("\n=== CADASTRO DE CLIENTES (CREDIÁRIO) ===");
        Cliente cliente01 = new Cliente("Dona Maria (Vizinha)", 200.00);
        System.out.printf("Cliente cadastrado: %s | Limite: R$ %.2f\n", cliente01.getNome(), cliente01.getLimiteCredito());

        // INCLUSÃO DO NOVO CLIENTE
        Cliente cliente02 = new Cliente("Seu José (Entregador)", 200.00);
        System.out.printf("Cliente cadastrado: %s | Limite: R$ %.2f\n", cliente02.getNome(), cliente02.getLimiteCredito());

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente01);
        clientes.add(cliente02);

        // SIMULAR UMA VENDA NO CARRINHO
        System.out.println("\n=== SIMULANDO COMPRA NO CARRINHO ===");

        // COMPRA DA DONA MARIA (Arroz)
        double precoVendaArroz = arroz.calcularPrecoVenda(markupMercado);
        int qtdDesejada = 2;
        double totalCarrinho = qtdDesejada * precoVendaArroz;

        System.out.printf("Dona Maria está levando %d un de Arroz. Total Carrinho: R$ %.2f\n", qtdDesejada, totalCarrinho);

        if (cliente01.comprarNoCrediario(totalCarrinho, qtdDesejada + "x Arroz 5kg")) {
            arroz.registrarSaida(qtdDesejada);
            System.out.println(" VENDA AUTORIZADA! Valor " + cliente01.getNome() + " pendurado com sucesso.");

        } else {
            System.out.println(" VENDA RECUSADA! Limite de crédito insuficiente.");
        }
        cliente01.imprimirExtratoCliente();

        // NOVA COMPRA: SEU JOSÉ LEVANDO 2 ITENS DE FRIOS (Salame)
        salame = estoque.get(5); // Índice 5 é o Salame (Categoria.FRIOS)
        double precoVendaSalame = salame.calcularPrecoVenda(markupMercado);
        int qtdSalame = 2;
        double totalJose = qtdSalame * precoVendaSalame;

        System.out.printf("\nSeu José está levando %d un de Salame. Total Carrinho: R$ %.2f\n", qtdSalame, totalJose);

        if (cliente02.comprarNoCrediario(totalJose, qtdSalame + "x Salame")) {
            salame.registrarSaida(qtdSalame); // Retira as 2 unidades do estoque físico
            System.out.println(" VENDA AUTORIZADA! Valor " + cliente02.getNome() + " pendurado com sucesso.");
        } else {
            System.out.println(" VENDA RECUSADA! Limite de crédito insuficiente.");
        }
        cliente02.imprimirExtratoCliente();


        // RELATORIO FINAL
        System.out.println("=== RELATÓRIO DE SALDO FINAL ===");
        for (produto p : estoque) {
            p.exibirRelatorio(markupMercado);
        }

        // DEMONSTRATIVO FINANCEIRO
        BalancoFinanceiro balanco = new BalancoFinanceiro();
        balanco.gerarRelatorioFinanceiro(estoque, markupMercado, clientes);

        teclado.close();
    }
}