package Btree;
import java.util.Scanner;

public class Main {
	
	private static Scanner Teclado = new Scanner(System.in);

	public static void main(String[] args) {
		
		System.out.println("\nInforme o tamanho das páginas:");
		int pag = Teclado.nextInt();
		Teclado.nextLine();
		BTree bt = new BTree(pag);
		
		do {
			
			System.out.println("+-----------------------------------------------+\n|                                               |");
			System.out.println("|Digite a opção:                                |\n|                                               |");
			System.out.println("|1. Adicionar chave;                            |\n|                                               |");
			System.out.println("|2. Buscar chave;                               |\n|                                               |");
			System.out.println("|3. Remover chave;                              |\n|                                               |");
			System.out.println("|4. Imprimir arvore;                            |\n|                                               |");
			System.out.println("|0. Sair                                        |\n|                                               |");
			System.out.println("+-----------------------------------------------+\n");
			int opcao = Teclado.nextInt();
			Teclado.nextLine();
			
			if(opcao == 1) {
				System.out.println("\nChave que deseja inserir:\n");
				int id = Teclado.nextInt();
				bt.insert(id);
			}
			
			if(opcao == 2) {
				System.out.println("\nChave que deseja buscar:\n");
				int busca = Teclado.nextInt();
				bt.Search(busca);
			}
			
			if(opcao == 3) {
				System.out.println("\nChave que deseja deletar:\n");
				int id = Teclado.nextInt();
				bt.Remove(id);
			}
			
			if(opcao == 4) {
				bt.display();
				System.out.println("\n");
			}
			
			if (opcao == 0) {
				System.out.println("\n\nSaindo....\n");
				System.exit(0);
			}
			
		}while(true);
		
	}

}
