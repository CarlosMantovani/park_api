package com.mantovani.park_api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {

    private static final double PRIMEIROS_15_MINUTOS = 5.00;
    private static final double PRIMEIROS_60_MINUTOS = 9.25;
    private static final double ADICIONAL_15_MINUTOS = 1.75;
    private static final double DESCONTO_PERCENTUAL = 0.30;

    public static String gerarRecibo(){
        LocalDateTime date = LocalDateTime.now();
        String recibo = date.toString().substring(0,19);
        return recibo.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }
    public static BigDecimal calcularCusto(LocalDateTime entrada, LocalDateTime saida) {
        long minutosTotais = entrada.until(saida, ChronoUnit.MINUTES);
        double total;

        if (minutosTotais <= 15) {
            total = PRIMEIROS_15_MINUTOS;
        } else if (minutosTotais <= 60) {
            total = PRIMEIROS_60_MINUTOS;
        } else {
            long minutosAdicionais = minutosTotais - 60;
            int blocosDe15Minutos = (int) Math.ceil(minutosAdicionais / 15.0);
            total = PRIMEIROS_60_MINUTOS + ADICIONAL_15_MINUTOS * blocosDe15Minutos;
        }
        return BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calcularDesconto(BigDecimal custo, long numeroDeVezes) {
        BigDecimal desconto = ((numeroDeVezes > 0) && (numeroDeVezes % 10 == 0))
                ? custo.multiply(new BigDecimal(DESCONTO_PERCENTUAL))
                : new BigDecimal(0);
        return desconto.setScale(2, RoundingMode.HALF_EVEN);
    }
}
