package iftm.edu.br.dnolavo.junit_tdd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FuncionarioTest {
    
    @Test
    public void testarConstrutorEntradasValidas() {
        Funcionario f = new Funcionario("Carlos", 30, 100.00);
        assertEquals("Carlos", f.getNome());
        assertEquals(30, f.getHorasTrabalhadas());
        assertEquals(100.00, f.getValorHora());
    }

    @Test
    public void testarConstrutorEntradaHorasInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Funcionario("Carlos", 50, 100.00);
        });
    }

    @Test
    public void testarConstrutorEntradaValorHoraInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Funcionario("Carlos", 30, 500.00);
        });
    }

    @Test
    public void testarConstrutorPagamentoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Funcionario("Carlos", 10, 50.00);
        });
    }

    @Test
    public void testarModificarHorasEntradaValida() {
        Funcionario f = new Funcionario("Carlos", 30, 100.00);
        f.setHorasTrabalhadas(35);
        assertEquals(35, f.getHorasTrabalhadas());
    }

    @Test
    public void testarModificarHorasEntradaInvalida() {
        Funcionario f = new Funcionario("Carlos", 30, 100.00);
        assertThrows(IllegalArgumentException.class, () -> {
            f.setHorasTrabalhadas(50);
        });
    }

    @Test
    public void testarModificarValorEntradaValida() {
        Funcionario f = new Funcionario("Carlos", 30, 100.00);
        f.setValorHora(120.00);
        assertEquals(120.00, f.getValorHora());
    }

    @Test
    public void testarModificarValorEntradaInvalida() {
        Funcionario f = new Funcionario("Carlos", 30, 100.00);
        assertThrows(IllegalArgumentException.class, () -> {
            f.setValorHora(500.00);
        });
    }

    @Test
    public void testarCalcularPagamento() {
        Funcionario f = new Funcionario("Carlos", 30, 100.00);
        assertEquals(3000.00, f.calcularPagamento());
    }
}
