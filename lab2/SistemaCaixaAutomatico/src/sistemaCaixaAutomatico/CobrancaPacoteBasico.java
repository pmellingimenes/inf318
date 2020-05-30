package sistemaCaixaAutomatico;

public class CobrancaPacoteBasico extends Cobranca implements CobrancaTaxaStrategy {
	private static int limitOperacao = 2;
	
	CobrancaPacoteBasico(ContaCor contaCor) {
		super(contaCor, 10, 10);
	}
	@Override
	public void cobrarTaxaOperacao() throws Exception {
		if (this.contaCor.getOperacoesRegistradasNoMes() > limitOperacao) {
			this.contaCor.removerSaldo("Taxa de Operacao", taxaOperacao);
		}
		this.contaCor.setOperacoesRegistradasNoMes(this.contaCor.getOperacoesRegistradasNoMes() + 1);
	}

	@Override
	public void aplicarMensalidade() throws Exception {
		this.contaCor.removerSaldo("Taxa de mensalidade", mensalidade);
	}
}
