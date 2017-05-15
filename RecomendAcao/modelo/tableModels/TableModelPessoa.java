package tableModels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import br.com.simprovendas.beans.Pessoa;
import br.com.simprovendas.dao.DAOPessoa;

public class TableModelPessoa extends AbstractTableModel {

	private List<Pessoa> linhas;
	private String[] colunas = new String[] { "Pessoas" };
	private static final int Nome = 0;
	DAOPessoa daoPessoa;

	public TableModelPessoa() {
		daoPessoa = new DAOPessoa();
		linhas = new ArrayList<Pessoa>();
	}

	public TableModelPessoa(List<Pessoa> listPessoa) {
		daoPessoa = new DAOPessoa();
		linhas = new ArrayList<Pessoa>(listPessoa);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pessoa p = linhas.get(rowIndex);
		switch (columnIndex) {
		case Nome:
			return p.getNome();
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	public boolean contemNomeLista(Pessoa pessoa) {
		for (Pessoa p : linhas) {
			if (p.equals(pessoa)) {
				return true;
			}
		}
		return false;
	}

	public Pessoa getPessoa(int linha) {
		return linhas.get(linha);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case Nome:
			return String.class;
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Pessoa p = linhas.get(rowIndex);
		p = ((Pessoa) aValue);
		 fireTableCellUpdated(rowIndex, columnIndex);
		JOptionPane.showMessageDialog(null, p.getNome()+p.getCodiPessoa());
		 // daoPessoa.editaLista(checkList); usar para alterar direto da tabela
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	};

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return linhas.size();
	}

}