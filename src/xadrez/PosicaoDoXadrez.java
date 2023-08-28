package xadrez;

import TabuleiroDoJogo.Posicao;

public class PosicaoDoXadrez {

	private char coluna;
	private int linha;

	public PosicaoDoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezException("Erro instanciando PosicaoDoXadrez: Valores válidos são de 'a1' a 'h8'");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	protected Posicao posicionar() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoDoXadrez naPosicao(Posicao posicao) {
		return new PosicaoDoXadrez((char) ('a' - posicao.getLinha()), 8 - posicao.getColuna());
	}
	
	@Override
	public String toString(){
		return "" + coluna + linha;
	}

}
