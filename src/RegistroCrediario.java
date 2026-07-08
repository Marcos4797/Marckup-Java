import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistroCrediario {
    private LocalDate data;
    private String descricao;
    private double valor;
    private String tipo;

    public RegistroCrediario(String descricao, double valor, String tipo) {
        this.data = LocalDate.now();
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
    }

    public void exibirLinha() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String sinal = tipo.equals("DEBITO") ? "(+)" : "(-)";
        System.out.printf("%s | %s %-30s | R$ %,.2f\n", data.format(formato), sinal, descricao, valor);
    }
}