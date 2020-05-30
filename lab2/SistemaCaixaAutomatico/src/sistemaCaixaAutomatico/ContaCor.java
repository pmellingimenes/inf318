/**
 * Autores: C.M.F. Rubira, P.A. Guerra, L.P. Tizzei, L.Montecchi
 *
 * Introdução à Programação Orientada a Objetos usando Java
 *
 * Estudo de caso Sistema de Caixa Automático
 *
 * Última modificação: Junho de 2018
 */

package sistemaCaixaAutomatico;

public class ContaCor implements CobrancaTaxaStrategy {

	public static int ATIVA = 1;
	public static int ENCERRADA = 2;
	public static int QTDMAXLANC = 1000000;
	public static int PACOTE_BASICO = 0;
	public static int PACOTE_PREMIUM = 1;
	public static int PACOTE_COBRANCA_OPERACAO = 2;
	

	private int estado;  		  // Ativa ou Encerrada
	private int pacote;			  // Codigo do pacote
	private String titular; 	  // nome do titular
	private int numConta;		  // número da conta
	private int senha;			  // senha
	private float saldoAnterior;  // saldo anterior
	private String historico[];   // históricos e
	private float valorLanc[];	  /* valores dos últimos lançamentos > 0 p/ créditos; < 0 p/ débitos.
	Nessa versão do código, a opção de crédito não foi implementada */
	private int ultLanc;		  // topo dos vetores acima
	private float saldoAtual;     // saldo atual da conta
	private int operacoesRegistradasNoMes = 0;

	public ContaCor(String titular, float saldoAtual, int numConta, int senha, int pacote) throws Exception {
		this.estado = ContaCor.ATIVA; 		// A conta se torna ativa, ppodendo receber lançamentos.
		this.titular = titular;
		this.saldoAtual = saldoAtual;
		this.numConta = numConta;
		this.senha = senha;
		this.ultLanc = 0; 					// A conta sem nenhum lançamento.
		this.setOperacoesRegistradasNoMes(0);
		this.historico = new String[ContaCor.QTDMAXLANC]; 		// cria vetores ...
		this.valorLanc = new float[ContaCor.QTDMAXLANC];		// ... com QTDMAXLANC elementos
		this.pacote = pacote;
		this.aplicarMensalidade();
	}

	private boolean estaAtiva() {
		return estado == ContaCor.ATIVA;
	}

	public float obterSaldo(int senhaCliente) {
		//A conta deve estar ativa
		if (!this.estaAtiva()){
			System.out.println("Conta inativa");
			return (-1);
		}
		//A senha de entrada deve ser igual à senha da conta
		if (senha!=senhaCliente){
			System.out.println("Senha incorreta");
			return (-1);
		}
		return (saldoAtual);		// retorna o saldo atual
	}

	/**
	 * Este método verifica se a conta está ativa, se a senha é correta e se o valor do débito é adequado, antes de efetuar o débito na conta corrente do cliente.
	 * Caso o débito seja efetuado, isso é registrado em um histórico da conta do cliente.
	 * @param hist
	 * @param val o valor do saque deve ser: (i) maior que zero; (ii) menor ou igual a R$200,00; (iii) múltiplo de 10; (iv) menor ou igual que o saldo do cliente.
	 * @param senhaCliente
	 * @return true se o débito for bem sucedido e false caso contrário
	 * @throws Exception 
	 */
	public boolean debitarValor(String hist, float val, int senhaCliente) throws Exception {



		//A senha de entrada deve ser igual à senha da conta
		if (senha != senhaCliente){
			System.out.println("Senha incorreta");
			return false;
		}
		
		return this.removerSaldo(hist, val);
	}
	
	public boolean removerSaldo (String hist, float val) throws Exception {
		//A conta deve estar ativa
		if (!this.estaAtiva()){
			System.out.println("Conta Inativa");
			return false;
		}
		
		if (val<0 || (val%10)!=0 || val>200 || val> this.obterSaldo(senha) ){
			System.out.println("Valor de saque incorreto:"+val);
			return false;
		}

		if (ultLanc == (ContaCor.QTDMAXLANC - 1) ) 	// Se está no limite de lançamentos da lista
			for(int i = 0; i < (ContaCor.QTDMAXLANC - 1) ; i++) {
				// remove o primeiro da lista
				historico[i] = historico[i+1];
				valorLanc[i] = valorLanc[i+1];
			}
		else
			ultLanc++;

		historico[ultLanc] = hist;	// guarda histórico ...
		valorLanc[ultLanc] = -val;	// ... e valor do lançamento (com sinal negativo)
		saldoAnterior = this.saldoAtual;
		this.saldoAtual -= val; 			// debita valor do saldo atual

		if ( saldoAtual == 0 ){			// se zerou o saldo ...
			estado = ContaCor.ENCERRADA;		// ... torna a conta inativa
			System.out.println("Conta de "+this.titular+", número "+this.numConta+" foi encerrada.");
		}
		this.cobrarTaxaOperacao();
		return true;
	}

	@Override
	public void cobrarTaxaOperacao() throws Exception {
		if (this.pacote == ContaCor.PACOTE_BASICO) {
			CobrancaPacoteBasico cobrancaPacoteBasico = new CobrancaPacoteBasico(this);
			cobrancaPacoteBasico.cobrarTaxaOperacao();
		} else if (this.pacote == ContaCor.PACOTE_PREMIUM) {
			CobrancaPacotePremium cobrancaPacotePremium = new CobrancaPacotePremium(this);
			cobrancaPacotePremium.cobrarTaxaOperacao();
		} else if (this.pacote == ContaCor.PACOTE_COBRANCA_OPERACAO) {
			CobrancaPorOperacao cobrancaPorOperacao = new CobrancaPorOperacao(this);
			cobrancaPorOperacao.cobrarTaxaOperacao();
		} else {
			throw new Exception("Conta Sem Pacote");
		}
	}

	@Override
	public void aplicarMensalidade() throws Exception {
		if (this.pacote == ContaCor.PACOTE_BASICO) {
			CobrancaPacoteBasico cobrancaPacoteBasico = new CobrancaPacoteBasico(this);
			cobrancaPacoteBasico.aplicarMensalidade();
			System.out.println("APLICANDO PACOTE BASICO NO: " + this.titular);
		} else if (this.pacote == ContaCor.PACOTE_PREMIUM) {
			CobrancaPacotePremium cobrancaPacotePremium = new CobrancaPacotePremium(this);
			cobrancaPacotePremium.aplicarMensalidade();
			System.out.println("APLICANDO PACOTE PREMIUM NO: " + this.titular);
		} else if (this.pacote == ContaCor.PACOTE_COBRANCA_OPERACAO) {
			System.out.println("APLICANDO PACOTE OPERACAO NO: " + this.titular);
			CobrancaPorOperacao cobrancaPorOperacao = new CobrancaPorOperacao(this);
			cobrancaPorOperacao.aplicarMensalidade();
		} else {
			throw new Exception("Conta Sem Pacote");
		}
	}

	public int getOperacoesRegistradasNoMes() {
		return operacoesRegistradasNoMes;
	}

	public void setOperacoesRegistradasNoMes(int operacoesRegistradasNoMes) {
		this.operacoesRegistradasNoMes = operacoesRegistradasNoMes;
	}

}