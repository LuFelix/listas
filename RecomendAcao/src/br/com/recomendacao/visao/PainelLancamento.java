package br.com.recomendacao.visao;

import br.com.recomendacao.beans.Lancamento;

public class PainelLancamento extends PainelObjetos {

	public PainelLancamento(Lancamento lanc) {
		super(lanc);
		getLblTituloTela().setText("Lancamento");

	}

	@Override
	public Object lerCampos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void carregarCampos(Object objeto) {
		// TODO Auto-generated method stub

	}

}