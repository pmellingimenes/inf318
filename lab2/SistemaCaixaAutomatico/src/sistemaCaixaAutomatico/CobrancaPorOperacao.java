package sistemaCaixaAutomatico;

public class CobrancaPorOperacao extends Cobranca implements CobrancaTaxaStrategy {
	CobrancaPorOperacao(ContaCor contaCor) {
		super(contaCor, 0, 20);
	}

	@Override
	public void cobrarTaxaOperacao() throws Exception {
		this.contaCor.removerSaldo("Taxa de Operacao", taxaOperacao);
		this.contaCor.setOperacoesRegistradasNoMes(this.contaCor.getOperacoesRegistradasNoMes() + 1);
	}

	@Override
	public void aplicarMensalidade() throws Exception {
	}

}
