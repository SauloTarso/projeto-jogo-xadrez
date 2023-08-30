package aplicacao;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoDoXadrez;

public class Program {

	public static void main(String[] args) {

		Scanner ler = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

		while (true) {
			UI.imprimirTabuleiro(partidaDeXadrez.getPecas());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoDoXadrez origem = UI.lerPosicaoDoXadrez(ler);

			System.out.println();
			System.out.print("Destino: ");
			PosicaoDoXadrez destino = UI.lerPosicaoDoXadrez(ler);

			PecaDeXadrez capturarPeca = partidaDeXadrez.executarMovimentoDaPecaDeXadrez(origem, destino);

		}

	}
}
