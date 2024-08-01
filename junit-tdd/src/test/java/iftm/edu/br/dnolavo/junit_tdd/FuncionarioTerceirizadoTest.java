package iftm.edu.br.dnolavo.junit_tdd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FuncionarioTerceirizadoTest {

    @Test
    public void testarConstrutorEntradasValidas() {
        FuncionarioTerceirizado ft = new FuncionarioTerceirizado("Ana", 30, 100.00, 500.00);
        assertEquals("Ana", ft.getNome());
        assertEquals(30, ft.getHorasTrabalhadas());
        assertEquals(100.00, ft.getValorHora());
        assertEquals(500.00, ft.getDespesasAdicionais());
    }

    @Test
    public void testarConstrutorEntradaDespesasInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FuncionarioTerceirizado("Ana", 30, 100.00, 1500.00);
        });
    }

    @Test
    public void testarModificarDespesasEntradaValida() {
        FuncionarioTerceirizado ft = new FuncionarioTerceirizado("Ana", 30, 100.00, 500.00);
        ft.setDespesasAdicionais(700.00);
        assertEquals(700.00, ft.getDespesasAdicionais());
    }

    @Test
    public void testarModificarDespesasEntradaInvalida() {
        FuncionarioTerceirizado ft = new FuncionarioTerceirizado("Ana", 30, 100.00, 500.00);
        assertThrows(IllegalArgumentException.class, () -> {
            ft.setDespesasAdicionais(1500.00);
        });
    }

    @Test
    public void testarCalcularPagamento() {
        FuncionarioTerceirizado ft = new FuncionarioTerceirizado("Ana", 30, 100.00, 500.00);
        assertEquals(3850.00, ft.calcularPagamento());
    }
}
