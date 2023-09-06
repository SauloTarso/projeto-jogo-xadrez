package xadrez;

import tabuleiroDoJogo.Peca;
import tabuleiroDoJogo.Posicao;
import tabuleiroDoJogo.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {

	private Cor cor;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public PosicaoDoXadrez getPosicaoDoXadrez() {
		return PosicaoDoXadrez.naPosicao(posicao);
	}
	
	protected boolean existePecaDoOponente(Posicao posicao) {
		PecaDeXadrez peca = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return peca != null && peca.getCor() != cor;
		
	}

}
