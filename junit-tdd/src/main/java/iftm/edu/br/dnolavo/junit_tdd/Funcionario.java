package iftm.edu.br.dnolavo.junit_tdd;

public class Funcionario {
    private String nome;
    private int horasTrabalhadas;
    private double valorHora;

    public Funcionario(String nome, int horasTrabalhadas, double valorHora) {
        this.nome = nome;
        this.horasTrabalhadas = validarHorasTrabalhadas(horasTrabalhadas);
        this.valorHora = validarValorHora(valorHora);
        validarPagamento();
    }

    public String getNome() {
        return nome;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = validarHorasTrabalhadas(horasTrabalhadas);
        validarPagamento();
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = validarValorHora(valorHora);
        validarPagamento();
    }

    public double calcularPagamento() {
        return horasTrabalhadas * valorHora;
    }

    private int validarHorasTrabalhadas(int horasTrabalhadas) {
        if (horasTrabalhadas > 40) {
            throw new IllegalArgumentException("O número de horas trabalhadas por funcionários deve ser menor ou igual a 40.");
        }
        return horasTrabalhadas;
    }

    private double validarValorHora(double valorHora) {
        double salarioMinimo = 1320.00;
        if (valorHora < 0.04 * salarioMinimo || valorHora > 0.10 * salarioMinimo) {
            throw new IllegalArgumentException("O valor por hora deve ser entre 4% e 10% do salário mínimo.");
        }
        return valorHora;
    }

    private void validarPagamento() {
        if (calcularPagamento() < 1320.00) {
            throw new IllegalArgumentException("O pagamento dos funcionários deve ser maior ou igual ao valor atual do salário mínimo.");
        }
    }
}
