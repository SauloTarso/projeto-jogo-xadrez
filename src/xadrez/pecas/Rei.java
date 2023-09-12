package xadrez.pecas;

import tabuleiroDoJogo.Posicao;
import tabuleiroDoJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	private boolean testeTorreRoque(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContagemMovimento() == 0;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// cima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existeAPosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existeAPosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existeAPosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existeAPosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existeAPosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existeAPosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().existeAPosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().existeAPosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// movimento especial Roque
		if (getContagemMovimento() == 0 && !partidaDeXadrez.getXeque()) {
			// Roque pequeno
			Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testeTorreRoque(posicaoTorre1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}

			}
			// Roque grande
			Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testeTorreRoque(posicaoTorre2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);

				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}

			}
		}

		return matriz;

	}
}
