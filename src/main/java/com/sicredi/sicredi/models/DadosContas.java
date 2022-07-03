package com.sicredi.sicredi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DadosContas {

    private String agencia;
    private String conta;
    private String saldo;
    private String status;
    private boolean validaConta;

    @Override
    public String toString() {
        return "agencia;conta;saldo;status;validaConta\n"
                +agencia + ";" + conta + ";" + saldo + ";" + status + ";" + validaConta+"\n";
    }
}
