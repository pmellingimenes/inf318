package sistemaCaixaAutomatico;

public interface CobrancaTaxaStrategy {
	void cobrarTaxaOperacao() throws Exception;
	void aplicarMensalidade() throws Exception;
}
