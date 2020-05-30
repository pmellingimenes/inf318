package sistemaCaixaAutomatico;

public class CobrancaPacotePremium extends Cobranca implements CobrancaTaxaStrategy  {
	CobrancaPacotePremium(ContaCor contaCor) {
		super(contaCor, 20.0f, 0.0f);
	}

	@Override
	public void cobrarTaxaOperacao() throws Exception {
		this.contaCor.setOperacoesRegistradasNoMes(this.contaCor.getOperacoesRegistradasNoMes() + 1);
	}

	@Override
	public void aplicarMensalidade() throws Exception {
		this.contaCor.removerSaldo("Taxa de mensalidade", mensalidade);
	}
}
