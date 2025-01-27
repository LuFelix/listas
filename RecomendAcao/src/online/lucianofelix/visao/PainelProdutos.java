package br.com.recomendacao.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import br.com.recomendacao.beans.Produto;
import br.com.recomendacao.controle.ControlaListaProdutos;
import br.com.recomendacao.controle.ControlaProduto;
import br.com.recomendacao.controle.ControlaTabelaPreco;
import br.com.recomendacao.dao.DAOProdutoPrepSTM;
import br.com.recomendacao.dao.DAOTabelaPreco;

public class PainelProdutos extends JPanel {

	// JFrame telaProduto;

	private JSplitPane jspPrincipal;
	private JSplitPane sppSuperior;
	private JSplitPane sppImagem;
	private JPanel painelGrid;
	private JPanel painelMovimento;
	private JLabel lblTituloTela;
	// Labels e text fields
	
	private static JLabel lblImagem;
	private JLabel lbl01;
	private JLabel lbl02;
	private JLabel lbl03;
	private JLabel lbl04;
	private JLabel lbl05;
	private JLabel lbl06;
	private JLabel lbl07;
	private JLabel lbl08;
	private JLabel lbl09;
	private JLabel lbl10;
	
	private static JTextField txtF01;
	private static JTextField txtF02;
	private static JTextField txtF03;
	private static JTextField txtF04;
	private static JTextField txtF05;
	private static JTextField txtF06;
	private static JTextField txtF07;
	private static JTextField txtF08;
	private static JTextField txtF09;
	private static JTextField txtF10;
	
	private static JCheckBox chkBListaPrecos;

	private static JComboBox<String> cmbTabPreco;

	private JButton btnProximo;
	private JButton btnAnterior;
	private static JButton btnEditarPreco;
	private static JButton btnNovo;
	private static JButton btnEditar;
	private static JButton btnCancelar;

	// Tabela de pre�os do produto

	private JTabbedPane tabVisualiza;
	private static JTable tabelaPrecos;
	private static JTable tabelaMovEstoque;
	private static DefaultTableModel modeloTabela;
	private static JScrollPane scrPrecos;
	private static JScrollPane scrEstoque;
	private static JScrollPane scrImagem;

	// objetos de controle

	private static ControlaListaProdutos controledaLista;
	private static ControlaProduto contProd;
	private ControlaTabelaPreco contTabPreco;
	private static Produto prod;
	private DAOProdutoPrepSTM daoP;
	private List<Produto> listProd;
	int tam;
	private DAOTabelaPreco daoTabPreco;

	// TODO Construtor
	public PainelProdutos(String nome) {

		UIManager.put("TextField.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font", new Font("Times New Roman", Font.BOLD, 12));
		// Controle

		contProd = new ControlaProduto();
		contTabPreco = new ControlaTabelaPreco();
		// Dados
		daoP = new DAOProdutoPrepSTM();
		daoTabPreco = new DAOTabelaPreco();
		// telaProduto.setContentPane(painelPrincipal);

		// TODO Configura��o dos Labels e text fields

		lblTituloTela = new JLabel("   Produto");
		lblTituloTela.setBounds(10, 0, 150, 40);
		lblTituloTela.setFont(new Font("Times New Roman", Font.BOLD, 32));

		lbl02 = new JLabel("Sequencia:");
		txtF02 = new JTextField();
		lbl03 = new JLabel("C�digo Interno:");
		txtF03 = new JTextField(0);
		lbl04 = new JLabel("C�digo F�brica:");
		txtF04 = new JTextField(0);
		lbl05 = new JLabel("Produto: ");
		txtF05 = new JTextField();
		lbl06 = new JLabel("Al�quota ICMS: ");
		txtF06 = new JTextField();
		lbl07 = new JLabel("Descri��o: ");
		txtF07 = new JTextField();
		lbl08 = new JLabel("Estoque: ");
		txtF08 = new JTextField();
		lbl09 = new JLabel("Pre�o Atual:");
		txtF09 = new JTextField();

		btnEditarPreco = new JButton("Alterar Pre�o");

		cmbTabPreco = new JComboBox<String>();
		cmbTabPreco.addItem("Tabela de Pre�os");
		for (int i = 0; i < daoTabPreco.pesquisaString("").size(); i++) {
			cmbTabPreco.addItem(daoTabPreco.pesquisaString("").get(i).getDescTabela());
		}
		chkBListaPrecos = new JCheckBox("Exibir");
		chkBListaPrecos.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (chkBListaPrecos.isSelected()) {
					habilitaTabelaPrecos();
				} else {
					desabilitaTabela();
				}
			}
		});

		// Tabela de Visualiza

		tabelaPrecos = new JTable();
		tabelaMovEstoque = new JTable();

		scrPrecos = new JScrollPane();
		scrPrecos.setViewportView(tabelaPrecos);
		tabelaPrecos.getParent().setBackground(Color.WHITE);

		lblImagem = new JLabel("Image not Found");

		scrImagem = new JScrollPane(lblImagem);
		scrImagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrImagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabVisualiza = new JTabbedPane();
		tabVisualiza.addTab("Hist�rico de Pre�os", scrPrecos);
		tabVisualiza.add("Estoque", scrEstoque);

		// A��es
		btnEditarPreco.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alteraPreco();
			}
		});

		// TODO Painel principal
		painelGrid = new JPanel();
		painelGrid.setBorder(BorderFactory.createEtchedBorder());
		painelGrid.setLayout(new GridLayout(9, 2));
		painelGrid.setBackground(Color.WHITE);
		painelGrid.add(lbl02);
		painelGrid.add(txtF02);
		painelGrid.add(lbl03);
		painelGrid.add(txtF03);
		painelGrid.add(lbl04);
		painelGrid.add(txtF04);
		painelGrid.add(lbl05);
		painelGrid.add(txtF05);
		painelGrid.add(lbl07);
		painelGrid.add(txtF07);
		painelGrid.add(lbl06);
		painelGrid.add(txtF06);
		painelGrid.add(lbl08);
		painelGrid.add(txtF08);
		painelGrid.add(lbl09);
		painelGrid.add(txtF09);
		painelGrid.add(cmbTabPreco);
		painelGrid.add(btnEditarPreco);

		sppImagem = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppImagem.add(lblTituloTela);
		sppImagem.add(scrImagem);
		sppImagem.setDividerLocation(50);
		sppImagem.setEnabled(false);
		sppImagem.setBackground(Color.WHITE);
		sppImagem.setForeground(Color.WHITE);
		sppImagem.setDividerSize(3);

		sppSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppSuperior.setDividerLocation(200);
		sppSuperior.setDividerSize(3);
		sppSuperior.setEnabled(false);
		sppSuperior.add(sppImagem);
		sppSuperior.add(painelGrid);

		painelMovimento = new JPanel();
		painelMovimento.setBorder(BorderFactory.createEtchedBorder());
		painelMovimento.setLayout(new GridLayout());
		painelMovimento.setBackground(Color.WHITE);
		painelMovimento.add(tabVisualiza);

		desHabilitaEdicao();
		listProd = contProd.pesqNomeArray(nome);
		tam = listProd.size();
		tam--;
		if (tam < 0) {
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.getScrVisualiza().setViewportView(FrameInicial.getPainelVisualiza());
		} else {
			controledaLista = new ControlaListaProdutos(listProd);
			prod = controledaLista.first();
			prod.setEstoqueAtual(contProd.consultaEstoque(prod));
			carregarCampos(prod);
		}
		jspPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		jspPrincipal.setDividerSize(3);
		jspPrincipal.setDividerLocation(250);
		jspPrincipal.setEnabled(false);
		jspPrincipal.setBackground(Color.WHITE);
		jspPrincipal.add(sppSuperior);
		jspPrincipal.add(painelMovimento);
		setLayout(new GridLayout());
		setBackground(Color.WHITE);
		add(jspPrincipal);
	}

	// TODO Fim contrutor inicio Habilita/Desab./Carrega/Le/Limpa Campos

	public static void irParaPoicao(int posicao) {
		controledaLista.setCurrentPosition(posicao);
		prod = controledaLista.getAt(posicao);
		prod.setEstoqueAtual(contProd.consultaEstoque(prod));
		carregarCampos(prod);
	}

	private void alteraPreco() {
		prod = lerCampos();
		String data = String.valueOf(new Timestamp(System.currentTimeMillis())).substring(0, 10);
		float valor = Float.parseFloat(JOptionPane.showInputDialog("Informe o novo valor:"));
		try {
			contProd.novoPreco(cmbTabPreco.getItemAt(0), Date.valueOf(data), prod.getCodi_prod_1(), valor);
			txtF09.setText(String.valueOf(valor));
			habilitaTabelaPrecos();
			contProd.funcaoSobrescrever();
			FrameInicial.getBtnSalvar().doClick();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problemas: ", " Erro ao Cadastrar: " + e1.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void desabilitaTabela() {
		setTabelaPrecos(null);
		getScrPrecos().setViewportView(tabelaPrecos);

	}

	// TODO Habilitar hist�rico de pre�os
	public static JTable habilitaTabelaPrecos() {
		tabelaPrecos = new JTable();
		modeloTabela = new DefaultTableModel();
		modeloTabela = (DefaultTableModel) tabelaPrecos.getModel();
		contProd.carregarCotacoes(prod);
		Object colunas[] = { "Nome", "Pre�o Unit�rio", "Data" };
		modeloTabela.setColumnIdentifiers(colunas);
		tabelaPrecos.setShowGrid(true);

		tabelaPrecos.setModel(modeloTabela);
		for (int i = 0; i < prod.getListCotacaoProduto().size(); i++) {
			Object linha[] = { prod.getNome_prod(), prod.getListCotacaoProduto().get(i).getValor(),
					prod.getListCotacaoProduto().get(i).getDataHoraMarcacao() };
			modeloTabela.addRow(linha);
		}

		scrPrecos.setViewportView(tabelaPrecos);
		return tabelaPrecos;
	}

	// TODO Ler Campos.

	public static Produto lerCampos() {
		prod = new Produto();
		if (txtF03.getText().equals("") || txtF03.getText().equals(null)) {
			prod.setCodi_prod_1(contProd.criaCodiProd());
		} else {
			prod.setCodi_prod_1(txtF03.getText());
		}
		if (!txtF05.getText().equals(null) & !txtF05.getText().equals("")) {
			prod.setNome_prod(txtF05.getText());
		} else {
			JOptionPane.showMessageDialog(null, "Problemas: Verifique as informa��es preenchidas",
					"Erro ao Salvar. Campos com * s�o necess�rios", JOptionPane.ERROR_MESSAGE);
		}
		prod.setDesc_prod(txtF07.getText());
		if (!txtF06.getText().equals(null) & !txtF06.getText().equals("")) {
			prod.setAliq_prod(txtF06.getText());
		} else {
			JOptionPane.showMessageDialog(null, "Problemas: Verifique as informa��es preenchidas",
					"Erro ao Salvar. Campos com * s�o necess�rios", JOptionPane.ERROR_MESSAGE);
		}
		prod.setAliq_prod(txtF06.getText());
		if (txtF09.getText().equals("")) {
			prod.setPrec_prod_1(0);
		} else {
			prod.setPrec_prod_1(Float.parseFloat(txtF09.getText()));
		}
		return prod;
	}

	public static void carregarImagem(String codiProd) {
		lblImagem = new JLabel(new ImageIcon("C:\\SIMPRO\\Salutar\\prods\\" + codiProd + ".jpg "));
		scrImagem.setViewportView(lblImagem);
	}

	// TODO Carregar campos
	public static void carregarCampos(Produto prod) {
		if (!prod.equals(null)) {
			chkBListaPrecos.setSelected(false);
			txtF02.setText(String.valueOf(prod.getSeq_produto()));
			txtF03.setText(String.valueOf(prod.getCodi_prod_1()));
			txtF05.setText(prod.getNome_prod());
			txtF07.setText(prod.getDesc_prod());
			txtF06.setText(prod.getAliq_prod());
			txtF09.setText(String.valueOf(prod.getPrec_prod_1()));
			txtF08.setText(String.valueOf(prod.getEstoqueAtual()));
			habilitaTabelaPrecos();
			carregarImagem(prod.getCodi_prod_1());

		}

	}

	// TODO Habilitar Edi��o
	public static void habilitaEdicao() {
		txtF03.setEditable(false);
		txtF05.setEditable(true);
		txtF07.setEditable(true);
		txtF08.setEditable(true);
		txtF06.setEditable(true);
		txtF09.setEditable(true);

		btnEditarPreco.setEnabled(true);
		cmbTabPreco.setEnabled(true);
	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();
		txtF02.setEditable(false);
		txtF03.setEditable(false);
		txtF03.setText(contProd.criaCodiProd());
		txtF04.setEditable(true);
		txtF04.grabFocus();
		txtF05.setEditable(true);
		txtF07.setEditable(true);
		txtF08.setEditable(true);
		txtF06.setEditable(true);
		// txtFPrecoAtual.setEditable(true);

		btnEditarPreco.setEnabled(true);
		cmbTabPreco.setEnabled(true);
	}

	// TODO Desabilita edi��o
	public static void desHabilitaEdicao() {
		txtF02.setEditable(false);
		txtF03.setEditable(false);
		txtF05.setEditable(false);
		txtF07.setEditable(false);
		txtF08.setEditable(false);
		txtF06.setEditable(false);

		btnEditarPreco.setEnabled(false);
		cmbTabPreco.setEnabled(false);
		chkBListaPrecos.setSelected(false);

	}

	// TODO Limpar campos
	public static void limparCampos() {
		txtF02.setText(null);
		txtF03.setText(null);
		txtF05.setText(null);
		txtF07.setText(null);
		txtF08.setText(null);
		txtF06.setText(null);
		txtF09.setText(null);
		chkBListaPrecos.setSelected(false);

	}

	public static JButton getBtnNovo() {
		return btnNovo;
	}

	public static void setBtnNovo(JButton btnNovo) {
		PainelProdutos.btnNovo = btnNovo;
	}

	public static JButton getBtnEditar() {
		return btnEditar;
	}

	public static void setBtnEditar(JButton btnEditar) {
		PainelProdutos.btnEditar = btnEditar;
	}

	public static JTextField getTxtFNomeProd() {
		return txtF05;
	}

	public static void setTxtFNomeProd(JTextField txtFNomeProd) {
		PainelProdutos.txtF05 = txtFNomeProd;
	}

	public static JButton getBtnCancelar() {
		return btnCancelar;
	}

	public static void setBtnCancelar(JButton btnCancelar) {
		PainelProdutos.btnCancelar = btnCancelar;
	}

	public static JComboBox<String> getCmbTabPreco() {
		return cmbTabPreco;
	}

	public static void setCmbTabPreco(JComboBox<String> cmbTabPreco) {
		PainelProdutos.cmbTabPreco = cmbTabPreco;
	}

	public JTable getTabelaPrecos() {
		return tabelaPrecos;
	}

	public void setTabelaPrecos(JTable tabelaPrecos) {
		this.tabelaPrecos = tabelaPrecos;
	}

	public JScrollPane getScrPrecos() {
		return scrPrecos;
	}

	public void setScrPrecos(JScrollPane scrPrecos) {
		this.scrPrecos = scrPrecos;
	}

}
