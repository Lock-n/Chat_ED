package cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import servidor.Comando;

public class Conexao {
	static Socket conexao;
	public static ObjectOutputStream transmissor;
	public static ObjectInputStream receptor;
	public static Comando cmd;
}
