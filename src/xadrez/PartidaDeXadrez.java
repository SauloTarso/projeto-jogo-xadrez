package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroDoJogo.Peca;
import tabuleiroDoJogo.Posicao;
import tabuleiroDoJogo.Tabuleiro;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;

	private List<Peca> pecasDoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		configInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getXeque() {
		return xeque;
	}

	public boolean getXequeMate() {
		return xequeMate;
	}

	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}

	public boolean[][] possiveisMovimentos(PosicaoDoXadrez posicaoDeOrigem) {
		Posicao posicao = posicaoDeOrigem.posicionar();
		validarPosicaoDeOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}

	public PecaDeXadrez executarMovimentoDaPecaDeXadrez(PosicaoDoXadrez posicaoDeOrigem,
			PosicaoDoXadrez posicaoDeDestino) {
		Posicao origem = posicaoDeOrigem.posicionar();
		Posicao destino = posicaoDeDestino.posicionar();
		validarPosicaoDeOrigem(origem);
		validarPosicaoDeDestino(origem, destino);
		Peca capturarPeca = movimentarPeca(origem, destino);

		if (testeDeXeque(jogadorAtual)) {
			desfazerMovimentarPeca(origem, destino, capturarPeca);
			throw new XadrezException("Você não pode se colocar em xeque.");
		}

		xeque = (testeDeXeque(oponente(jogadorAtual))) ? true : false;

		if (testeDeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {

			proximoTurno();
		}
		return (PecaDeXadrez) capturarPeca;
	}

	private Peca movimentarPeca(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(origem);
		p.addContagemMovimento();
		Peca capturarPeca = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (capturarPeca != null) {
			pecasDoTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}

		return capturarPeca;
	}

	private void desfazerMovimentarPeca(Posicao origem, Posicao destino, Peca capturarPeca) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(destino);
		p.diminuirContagemMovimento();
		tabuleiro.colocarPeca(p, origem);

		if (capturarPeca != null) {
			tabuleiro.colocarPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasDoTabuleiro.add(capturarPeca);
		}
	}

	private void validarPosicaoDeOrigem(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem");
		}

		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua");
		}

		if (!tabuleiro.peca(posicao).exiteUmMovimentoPossivel()) {
			throw new XadrezException("Não exite movimentos possíveis para a peça escolhida");
		}
	}

	private void validarPosicaoDeDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para posição de destino");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasDoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());

		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}

		throw new IllegalStateException("Não existe Rei " + cor + " no tabuleiro");
	}

	private boolean testeDeXeque(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoDoXadrez().posicionar();
		List<Peca> pecasDoOponente = pecasDoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasDoOponente) {
			boolean[][] matriz = p.possiveisMovimentos();
			if (matriz[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeDeXequeMate(Cor cor) {
		if (!testeDeXeque(cor)) {
			return false;
		}

		List<Peca> lista = pecasDoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] matriz = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoDoXadrez().posicionar();
						Posicao destino = new Posicao(i, j);
						Peca capturarPeca = movimentarPeca(origem, destino);
						boolean testeDeXeque = testeDeXeque(cor);
						desfazerMovimentarPeca(origem, destino, capturarPeca);
						if (!testeDeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoDoXadrez(coluna, linha).posicionar());
		pecasDoTabuleiro.add(peca);
	}

	private void configInicial() {

		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));

	}
}
