package com.sicredi.sicredi;

import com.sicredi.sicredi.models.DadosContas;
import com.sicredi.sicredi.util.ControleDeContas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

import static com.sicredi.sicredi.util.ControleDeContas.*;

@SpringBootApplication
public class SincronizacaoReceitaApplication {

	public static  void main(String[] args) throws IOException, InterruptedException {

		List<String[]> arquivo = importFile("/entradadecontas.csv");

		List<DadosContas> contas = validarContas(arquivo);

		contas.forEach(obj->{
			exportFile(obj.toString(), "saidadecontas.csv");
		});
	}

}
