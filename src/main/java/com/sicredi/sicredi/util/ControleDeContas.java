package com.sicredi.sicredi.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sicredi.sicredi.models.DadosContas;
import com.sicredi.sicredi.services.ReceitaService;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ControleDeContas {

    public static  void main(String[] args) throws IOException, InterruptedException {

        List<String[]> arquivo = importFile("/entradadecontas.csv");

        List<DadosContas> contas = validarContas(arquivo);

        contas.forEach(obj->{
            log.debug(obj.toString());
            exportFile(obj.toString(), "saidadecontas.csv");
        });
    }

    public static List<DadosContas> validarContas(List<String[]> arquivos) throws InterruptedException {
        List<DadosContas> contasList = new ArrayList<>();

        for(String[] arquivo : arquivos){
            DadosContas conta = new DadosContas();
            conta.setAgencia(arquivo[0]);
            conta.setConta(arquivo[1].replace("-", ""));
            conta.setSaldo(arquivo[2].replace(',', '.'));
            conta.setStatus(arquivo[3]);
            var statusValidacao = ReceitaService.atualizarConta(conta.getAgencia(), conta.getConta(), Double.parseDouble(conta.getSaldo()), conta.getStatus());
            conta.setValidaConta(statusValidacao);
            contasList.add(conta);

        }

        return contasList;
    }

    public static List<String[]> importFile(String file) throws IOException {
        final CSVParser parser  =
                new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();

        // Pegar o caminho onde esta o csv
        File fileUtil = new File("src/main/resources/inputcsv");
        String diretorio = fileUtil.getAbsolutePath();
        // Pega o arquivo csv
        Reader reader = Files.newBufferedReader(Paths.get(diretorio.concat(file)), StandardCharsets.ISO_8859_1);

        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();

        return csvReader.readAll();
    }

    public static void exportFile(String linhas, String nomeArquivo){
        File fileUtil = new File("src/main/resources/outputcsv");
        String diretorio = fileUtil.getAbsolutePath();
        File arquivo = new File(diretorio, nomeArquivo);

        try {
            FileWriter arq = new FileWriter(arquivo, true);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(linhas);
            gravarArq.close();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


}
