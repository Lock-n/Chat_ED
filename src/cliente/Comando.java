package cliente;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class Comando implements Serializable {
	private String cmd;
	private Serializable[] complementos;
	
	public Comando (String cmd, Serializable[] complementos) {
		this.cmd = cmd;
		this.complementos = complementos;
	}

	public String getCmd() {
		return cmd;
	}

	public Object[] getComplementos() {
		return complementos;
	}

	@Override
	public int hashCode() {
		final int prime = 7;
		int result = 1;
		result = prime * result + ((cmd == null) ? 0 : cmd.hashCode());
		result = prime * result + Arrays.hashCode(complementos);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comando other = (Comando) obj;
		if (cmd == null) {
			if (other.cmd != null)
				return false;
		} else if (!cmd.equals(other.cmd))
			return false;
		if (!Arrays.equals(complementos, other.complementos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comando [cmd=" + cmd + ", complementos=" + Arrays.toString(complementos) + "]";
	}
	
}
