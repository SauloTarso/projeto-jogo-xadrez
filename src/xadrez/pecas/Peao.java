package xadrez.pecas;

import tabuleiroDoJogo.Posicao;
import tabuleiroDoJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existeAPosicao(p) && !getTabuleiro().temUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existeAPosicao(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().existeAPosicao(p2)
					&& !getTabuleiro().temUmaPeca(p2) && getContagemMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existeAPosicao(p) && existePecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existeAPosicao(p) && existePecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// movimento especial en passant peca BRANCA
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existeAPosicao(esquerda) && existePecaDoOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.getEnPassantVulneravel()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existeAPosicao(direita) && existePecaDoOponente(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.getEnPassantVulneravel()) {
					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		} else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existeAPosicao(p) && !getTabuleiro().temUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existeAPosicao(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().existeAPosicao(p2)
					&& !getTabuleiro().temUmaPeca(p2) && getContagemMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existeAPosicao(p) && existePecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existeAPosicao(p) && existePecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// movimento especial en passant peca PRETA
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existeAPosicao(esquerda) && existePecaDoOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.getEnPassantVulneravel()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existeAPosicao(direita) && existePecaDoOponente(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.getEnPassantVulneravel()) {
					matriz[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}

		}
		return matriz;
	}

	@Override
	public String toString() {
		return "P";
	}
}
