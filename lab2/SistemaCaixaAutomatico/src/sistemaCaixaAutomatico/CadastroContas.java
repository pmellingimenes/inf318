/**
 * Autores: C.M.F. Rubira, P.A. Guerra e L.P. Tizzei
 * 
 * Introdução à Programação Orientada a Objetos usando Java
 * 
 * Estudo de caso: Sistema de Caixa Automático
 * 
 * última modificação: março de 2014
 */

package sistemaCaixaAutomatico;


public class CadastroContas {
	//Atributos
	  private ContaCor c[];  // vetor de contas
	  
	//Operacoes
	  
	  public CadastroContas () {  // método construtor 
	    c=new ContaCor[4];// o índice zero não é utilizado
	    
	    try {
			c[1]=new ContaCor("Juliana",10000,1,1, ContaCor.PACOTE_PREMIUM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("Conta de Juliana criada com id 1 senha 1 e 500,00");
	    
	    try {
			c[2]=new ContaCor("Maria",10000,2,2, ContaCor.PACOTE_BASICO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("Conta de Maria criada com id 2 senha 2 e 500,00");
	    
	    try {
			c[3]=new ContaCor("Nestor",10000,3,3, ContaCor.PACOTE_COBRANCA_OPERACAO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("Conta de Nestor criada com id 3 senha 3 e 500,00");
	    
	  }
	  
	  public ContaCor buscarConta (int num) {
		System.out.println("PESQUISANDO POR ..." + num);
	    if (num < 1 || num > 3)  // apenas os número 1, 2 e 3 são aceitos
	      return(null);
	    else
	      return(c[num]);
	  }

}