/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscabinaria;

import java.io.DataInput;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class BuscaBinaria {

    public static int c = 0;

    public static void main(String[] args) throws Exception {

        /*  
        * Instanciando objeto "RandomAccessFile" 
        * 
        * Para instanciar um objeto desta classe deve-se passar 2 parametros, 
        * o primeiro uma referencia a um objeto da classe "File" que será o  
        * arquivo a ser manipulado, e o segundo é uma string que varia entre 
        * "r" e "rw", usa-se o "r" (read) para abrir o arquivo somente como 
        * leitura, não sendo possível sua alteração, ou então o "rw" (read/write) 
        * para abrir o arquivo com permissão para modificações. 
        * Neste caso, deve-se instanciar o objeto como "rw" para poder escrever 
        * o conteudo desejado nele 
        * 
         */
        try {
            RandomAccessFile f = new RandomAccessFile("CepOrdenadoMenor.dat", "r");
            Endereco e = new Endereco();
            Scanner ler = new Scanner(System.in);

            System.out.println("Digite um cep existente entre 88301040 até 99099000, que são ceps do Rio Grande do Sul:");
            String cep = ler.nextLine();

            long primeiro = 0;
            long ultimo = (f.length() / 300) - 1;

            long resultado = buscaBinariaRecursiva(f, primeiro, ultimo, cep);
            f.seek(resultado * 300);

            while (f.getFilePointer() < f.length()) // para Detectar EOF
            {
                e.leEndereco(f);
                System.out.println(e.getLogradouro());
                System.out.println(e.getBairro());
                System.out.println(e.getCidade());
                System.out.println(e.getEstado());
                System.out.println(e.getSigla());
                System.out.println("Foram feitas " + c + " buscas!");
                break;
            }

            f.close();

        } catch (Exception e) {
            System.out.println("Cep invalido, tente de novo!");
        }
    }

    private static long buscaBinariaRecursiva(RandomAccessFile dados, long esq, long dir, String cep) throws Exception {
        c++;

        long meio = (esq + dir) / 2;

        Endereco teste = new Endereco();

        dados.seek((meio) * 300);

        teste.leEndereco(dados);

        String cepLinhaAtual = teste.getCep();

        if (esq > dir) {
            return -1;
        } else if (cepLinhaAtual.equals(cep)) {
            return meio;
        } else if ((Long.parseLong(cepLinhaAtual)) < (Long.parseLong(cep))) {
            return buscaBinariaRecursiva(dados, meio + 1, dir, cep);
        } else {
            return buscaBinariaRecursiva(dados, esq, meio - 1, cep);
        }
    }

}
