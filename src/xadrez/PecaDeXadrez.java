package xadrez;

import tabuleiroDoJogo.Peca;
import tabuleiroDoJogo.Posicao;
import tabuleiroDoJogo.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {

	private Cor cor;
	private int contagemMovimento;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContagemMovimento() {
		return contagemMovimento;
	}
	
	public void addContagemMovimento() {
		contagemMovimento++;
	}
	
	public void diminuirContagemMovimento() {
		contagemMovimento--;
	}
	
	public PosicaoDoXadrez getPosicaoDoXadrez() {
		return PosicaoDoXadrez.naPosicao(posicao);
	}
	
	protected boolean existePecaDoOponente(Posicao posicao) {
		PecaDeXadrez peca = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return peca != null && peca.getCor() != cor;
		
	}

}
