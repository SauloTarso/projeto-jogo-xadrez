package aplicacao;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoDoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) throws InterruptedException, IOException {

		Scanner ler = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

		while (true) {
			try {	
				UI.limparTela();
				UI.imprimirTabuleiro(partidaDeXadrez.getPecas());
				System.out.println();
				System.out.print("Origem: ");
				PosicaoDoXadrez origem = UI.lerPosicaoDoXadrez(ler);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoDoXadrez destino = UI.lerPosicaoDoXadrez(ler);

				PecaDeXadrez capturarPeca = partidaDeXadrez.executarMovimentoDaPecaDeXadrez(origem, destino);
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				ler.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				ler.nextLine();
			}
		}

	}
}
