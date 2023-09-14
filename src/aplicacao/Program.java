package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoDoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) {

		Scanner ler = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

		List<PecaDeXadrez> capturadas = new ArrayList<>();

		while (!partidaDeXadrez.getXequeMate()) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partidaDeXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoDoXadrez origem = UI.lerPosicaoDoXadrez(ler);

				boolean[][] possiveisMovimentos = partidaDeXadrez.possiveisMovimentos(origem);
				UI.limparTela();
				UI.imprimirTabuleiro(partidaDeXadrez.getPecas(), possiveisMovimentos);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoDoXadrez destino = UI.lerPosicaoDoXadrez(ler);

				PecaDeXadrez capturarPeca = partidaDeXadrez.executarMovimentoDaPecaDeXadrez(origem, destino);

				if (capturarPeca != null) {
					capturadas.add(capturarPeca);
				}
				
				if(partidaDeXadrez.getPromocao() != null) {
					System.out.print("Digite a peça de promoção (B/C/T/Q): ");
					String tipo = ler.nextLine();
					partidaDeXadrez.trocarPecaPromovida(tipo);
				}
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				ler.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				ler.nextLine();
			}
		}
		UI.limparTela();
		UI.imprimirPartida(partidaDeXadrez, capturadas);
	}
}
