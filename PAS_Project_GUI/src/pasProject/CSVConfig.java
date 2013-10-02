package pasProject;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;

public class CSVConfig {

	protected Shell shlExportFileConfiguration;
	private Text debitText;
	private Text creditText;
	private Text delimiterText;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CSVConfig window = new CSVConfig();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlExportFileConfiguration.open();
		shlExportFileConfiguration.layout();
		while (!shlExportFileConfiguration.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlExportFileConfiguration = new Shell();
		shlExportFileConfiguration.setSize(500, 500);
		shlExportFileConfiguration.setText("Export File Configuration");
		shlExportFileConfiguration.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shlExportFileConfiguration, SWT.NONE);
		
		Group grpSettings = new Group(composite, SWT.NONE);
		grpSettings.setText("Settings");
		grpSettings.setBounds(10, 10, 464, 176);
		
		debitText = new Text(grpSettings, SWT.BORDER);
		debitText.setBounds(107, 29, 30, 21);
		debitText.setTextLimit(1);
		
		creditText = new Text(grpSettings, SWT.BORDER);
		creditText.setBounds(107, 64, 30, 21);
		creditText.setTextLimit(1);
		
		Label lblDebitOperator = new Label(grpSettings, SWT.RIGHT);
		lblDebitOperator.setBounds(19, 32, 82, 15);
		lblDebitOperator.setText("Debit Operator");
		
		Label lblCreditOperator = new Label(grpSettings, SWT.RIGHT);
		lblCreditOperator.setBounds(19, 67, 82, 15);
		lblCreditOperator.setText("Credit Operator");
		
		delimiterText = new Text(grpSettings, SWT.BORDER);
		delimiterText.setBounds(107, 98, 30, 21);
		delimiterText.setTextLimit(1);
		
		Label lblDelimiter = new Label(grpSettings, SWT.RIGHT);
		lblDelimiter.setBounds(46, 101, 55, 15);
		lblDelimiter.setText("Delimiter");
		
		Button colHdrCheckButton = new Button(grpSettings, SWT.CHECK);
		colHdrCheckButton.setText("Include Column Headers");
		colHdrCheckButton.setToolTipText("");
		colHdrCheckButton.setBounds(107, 139, 152, 16);
		
		Button saveButton = new Button(grpSettings, SWT.NONE);
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		saveButton.setBounds(386, 141, 68, 25);
		saveButton.setText("Save");
		
		Combo configFilesCombo = new Combo(grpSettings, SWT.NONE);
		configFilesCombo.setBounds(217, 32, 237, 23);
		
		Label lblSettings = new Label(grpSettings, SWT.NONE);
		lblSettings.setBounds(217, 11, 122, 15);
		lblSettings.setText("Saved Configurations ");
		
		Group grpSelectFields = new Group(composite, SWT.NONE);
		grpSelectFields.setText("Fields to Export");
		grpSelectFields.setBounds(10, 257, 464, 197);
		
		List dbMasterList = new List(grpSelectFields, SWT.BORDER | SWT.MULTI);
		dbMasterList.setBounds(23, 51, 125, 136);
		
		List dbExportList = new List(grpSelectFields, SWT.BORDER);
		dbExportList.setBounds(237, 51, 125, 136);
		
		Button addAllButton = new Button(grpSelectFields, SWT.NONE);
		addAllButton.setBounds(179, 57, 28, 25);
		addAllButton.setText(">>");
		
		Button addSelButton = new Button(grpSelectFields, SWT.NONE);
		addSelButton.setBounds(179, 90, 28, 25);
		addSelButton.setText(">");
		
		Button removeSelButton = new Button(grpSelectFields, SWT.NONE);
		removeSelButton.setBounds(179, 122, 28, 25);
		removeSelButton.setText("<");
		
		Button removeAllButton = new Button(grpSelectFields, SWT.NONE);
		removeAllButton.setBounds(179, 155, 28, 25);
		removeAllButton.setText("<<");
		
		Button moveUpButton = new Button(grpSelectFields, SWT.NONE);
		moveUpButton.setBounds(385, 90, 28, 25);
		moveUpButton.setText("\u02C4");
		
		Button moveDownButton = new Button(grpSelectFields, SWT.NONE);
		moveDownButton.setBounds(385, 122, 28, 25);
		moveDownButton.setText("\u02C5");
		
		Label lblMasterList = new Label(grpSelectFields, SWT.CENTER);
		lblMasterList.setBounds(50, 30, 63, 15);
		lblMasterList.setText("Master List");
		
		Label lblExportList = new Label(grpSelectFields, SWT.CENTER);
		lblExportList.setBounds(265, 30, 63, 15);
		lblExportList.setText("Export List");
		
		Label lblChangeOrder = new Label(grpSelectFields, SWT.NONE);
		lblChangeOrder.setBounds(385, 67, 36, 15);
		lblChangeOrder.setText("Order");

	}
}
