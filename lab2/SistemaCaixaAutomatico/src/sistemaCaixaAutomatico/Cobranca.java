package sistemaCaixaAutomatico;

public abstract class Cobranca {
	protected ContaCor contaCor;
	protected float mensalidade;
	protected float taxaOperacao;
	
	Cobranca (ContaCor contaCor, float mensalidade, float taxaOperacao) {
		this.contaCor = contaCor;
		this.mensalidade = mensalidade;
		this.taxaOperacao = taxaOperacao;
	}
}
