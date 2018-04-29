package servidor;
//import lists.*;

//import java.io.IOException;
import java.io.*;
import java.net.*;

// pensar em como derrubar o servidor, caso desejado,
// exigindo confirmaÇÕES
public class Servidor {
    public static void main (String[] args)
    {
        Salas        salas  = new Salas (new String[] {"Pokemon", "Games", "Estudos"});
        ServerSocket pedido;
        
		try {
			pedido = new ServerSocket (12345);
		} catch (IOException e) {
			System.out.println("Erro na inicialização do servidor:");
			System.out.println(e.getMessage());
			return;
		}

        System.out.println ("Servidor operante...");
        System.out.println ("De o comando 'shutdown' para derrubar o servidor");
        
        //Thread para receber o input do usuario
        new Thread(new Runnable() {
			public void run() {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				for (;;) {
					try {
						String input = br.readLine();
						
						switch (input.toLowerCase()) {
						case "shutdown":
							System.out.println("Servidor sendo desativado");
							System.exit(0);
						}
					} catch (IOException e) {
						System.out.println("Erro na leitura de input:");
						System.out.println(e.getMessage());
					}
				}
			}
        }).start();
        
        for(;;)
        {
            Socket conexao;
			try {
				conexao = pedido.accept();
				System.out.println("SERVER: Cliente conectou");
				new TratadorDeConexao (salas, conexao).start();
			} catch (Exception e) {
				System.out.println("Erro na inicialização de uma conexão:");
				e.printStackTrace();
			}
        }
    }
}