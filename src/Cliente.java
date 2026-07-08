import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private double limiteCredito;
    private double saldoDevedor;
    private List<RegistroCrediario> extratoCrediario;

    public Cliente(String nome, double limiteCredito) {
        this.nome = nome;
        this.limiteCredito = limiteCredito;
        this.saldoDevedor = 0.0;
        this.extratoCrediario = new ArrayList<>();
    }

    public boolean comprarNoCrediario(double valorCompra, String detalhesProdutos) {
        if ((saldoDevedor + valorCompra) <= limiteCredito) {
            saldoDevedor += valorCompra;
            // Grava a compra no histórico do cliente
            extratoCrediario.add(new RegistroCrediario("Compra: " + detalhesProdutos, valorCompra, "DEBITO"));
            return true;
        }
        return false;
    }

    public void pagarDivida(double valorPago) {
        if (valorPago > 0) {
            saldoDevedor -= valorPago;

            extratoCrediario.add(new RegistroCrediario("Pagamento em Dinheiro", valorPago, "CREDITO"));
            System.out.printf("Pagamento de R$ %,.2f registrado para %s.\n", valorPago, this.nome);
        }
    }
    public void imprimirExtratoCliente() {
        System.out.println("\n=======================================================");
        System.out.println("   EXTRATO DE CREDIÁRIO - " + nome.toUpperCase());
        System.out.println("=======================================================");
        if (extratoCrediario.isEmpty()) {
            System.out.println("Nenhuma movimentação no fiado.");
        } else {
            for (RegistroCrediario reg : extratoCrediario) {
                reg.exibirLinha();
            }
        }
        System.out.println("-------------------------------------------------------");
        System.out.printf("LIMITE TOTAL: R$ %,.2f | SALDO DEVEDOR ATUAL: R$ %,.2f\n", limiteCredito, saldoDevedor);
        System.out.println("=======================================================");
    }


    public String getNome() { return nome; }
    public double getLimiteCredito() { return limiteCredito; }
    public double getSaldoDevedor() { return saldoDevedor; }
}
