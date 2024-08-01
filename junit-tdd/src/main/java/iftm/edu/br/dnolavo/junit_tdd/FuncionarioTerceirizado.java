package iftm.edu.br.dnolavo.junit_tdd;

public class FuncionarioTerceirizado extends Funcionario {
    private double despesasAdicionais;

    public FuncionarioTerceirizado(String nome, int horasTrabalhadas, double valorHora, double despesasAdicionais) {
        super(nome, horasTrabalhadas, valorHora);
        this.despesasAdicionais = validarDespesasAdicionais(despesasAdicionais);
    }

    public double getDespesasAdicionais() {
        return despesasAdicionais;
    }

    public void setDespesasAdicionais(double despesasAdicionais) {
        this.despesasAdicionais = validarDespesasAdicionais(despesasAdicionais);
    }

    @Override
    public double calcularPagamento() {
        return super.calcularPagamento() + 1.1 * despesasAdicionais;
    }

    private double validarDespesasAdicionais(double despesasAdicionais) {
        if (despesasAdicionais > 1000.00) {
            throw new IllegalArgumentException("O valor das despesas adicionais não pode ultrapassar R$ 1000.00.");
        }
        return despesasAdicionais;
    }
}
