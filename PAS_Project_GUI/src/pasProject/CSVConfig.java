package pasProject;

import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class CSVConfig {

	protected Shell shlExportFileConfiguration;
	private Text debitText;
	private Text creditText;
	private Text delimiterText;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		try {
			MySQLAccess dao = new MySQLAccess();
			//TODO define query string to get list of column names
			String query = new String("");
			dao.readDataBase(query);
			//TODO develop code to put column names into a linked list to be displayed in dbMasterList
			//
			//
			//
			/////////////////////////////////////////////////////////////
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
		shlExportFileConfiguration.setLocation(100, 100);
		
		Composite composite = new Composite(shlExportFileConfiguration, SWT.NONE);
		
		Group grpSettings = new Group(composite, SWT.NONE);
		grpSettings.setText("Settings");
		grpSettings.setBounds(10, 10, 464, 176);
		
		debitText = new Text(grpSettings, SWT.BORDER);
		debitText.setToolTipText("");
		debitText.setBounds(107, 29, 30, 21);
		debitText.setTextLimit(1);
		//set default character
		debitText.setText("-");
		
		creditText = new Text(grpSettings, SWT.BORDER);
		creditText.setToolTipText("");
		creditText.setBounds(107, 64, 30, 21);
		creditText.setTextLimit(1);
		//set default character
		creditText.setText("+");
		
		Label lblDebitOperator = new Label(grpSettings, SWT.RIGHT);
		lblDebitOperator.setToolTipText("Enter symbol that represents debits");
		lblDebitOperator.setBounds(19, 32, 82, 15);
		lblDebitOperator.setText("Debit Operator");
		
		Label lblCreditOperator = new Label(grpSettings, SWT.RIGHT);
		lblCreditOperator.setToolTipText("Enter symbol that represents credits");
		lblCreditOperator.setBounds(19, 67, 82, 15);
		lblCreditOperator.setText("Credit Operator");
		
		delimiterText = new Text(grpSettings, SWT.BORDER);
		delimiterText.setToolTipText("");
		delimiterText.setBounds(107, 98, 30, 21);
		delimiterText.setTextLimit(1);
		//set default character
		delimiterText.setText(",");
		
		Label lblDelimiter = new Label(grpSettings, SWT.RIGHT);
		lblDelimiter.setToolTipText("Enter desired delimiter character");
		lblDelimiter.setBounds(46, 101, 55, 15);
		lblDelimiter.setText("Delimiter");
		
		//Color red = new Color(SWT.COLOR_RED);
		
		final Button colHdrCheckButton = new Button(grpSettings, SWT.CHECK);
		colHdrCheckButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		colHdrCheckButton.setText("Include Column Headers");
		colHdrCheckButton.setSelection(true);
		colHdrCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		//colHdrCheckButton.setText("Include Column Headers");
		colHdrCheckButton.setToolTipText("Include column headers in export file");
		colHdrCheckButton.setBounds(107, 139, 152, 16);
		
		Button saveButton = new Button(grpSettings, SWT.NONE);
		saveButton.setToolTipText("Save Configuration Settings");
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell shlSaveDialog = new Shell();
				//shlSaveDialog.setLocation(shlExportFileConfiguration.getLocation());
				SaveDialog saveDialog = new SaveDialog(shlSaveDialog, SWT.APPLICATION_MODAL, shlExportFileConfiguration.getLocation());
				//SaveDialog saveDialog = new SaveDialog(shlSaveDialog);
				saveDialog.open();
			}
		});
		saveButton.setBounds(386, 141, 68, 25);
		saveButton.setText("Save");
		
		Combo configFilesCombo = new Combo(grpSettings, SWT.NONE);
		configFilesCombo.setBounds(217, 32, 237, 23);
		
		Label lblSettings = new Label(grpSettings, SWT.NONE);
		lblSettings.setBounds(217, 11, 122, 15);
		lblSettings.setText("Saved Configurations ");
		
		Button btnRestoreDefaults = new Button(grpSettings, SWT.NONE);
		btnRestoreDefaults.setToolTipText("Restore Default Settings");
		btnRestoreDefaults.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//restore default characters
				debitText.setText("-");
				creditText.setText("+");
				delimiterText.setText(",");
				colHdrCheckButton.setSelection(true);
			}
		});
		btnRestoreDefaults.setBounds(283, 141, 97, 25);
		btnRestoreDefaults.setText("Restore Defaults");
		
		Group grpSelectFields = new Group(composite, SWT.NONE);
		grpSelectFields.setText("Fields to Export");
		grpSelectFields.setBounds(10, 222, 464, 197);
		
		final List dbMasterList = new List(grpSelectFields, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		dbMasterList.setBounds(23, 51, 125, 136);
		
		// TODO implement code to pull column names from linked list and place them into dbMasterList
		// the next 3 lines are for demo purposes only REPLACE WITH FINAL IMPLEMENTATION
		for(int i=0;i<30;i++){
			dbMasterList.add("Column" + i);
		}
		
		final List dbExportList = new List(grpSelectFields, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		dbExportList.setBounds(237, 51, 125, 136);
		
		Button addAllButton = new Button(grpSelectFields, SWT.NONE);
		addAllButton.setToolTipText("Add All");
		addAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbExportList.removeAll();
				dbExportList.setItems(dbMasterList.getItems());
			}
		});
		addAllButton.setBounds(179, 57, 28, 25);
		addAllButton.setText(">>");
		
		Button addSelButton = new Button(grpSelectFields, SWT.NONE);
		addSelButton.setToolTipText("Add Selection");
		addSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(dbMasterList.getSelection().length != 0){
					for(int i=0;i<dbMasterList.getSelection().length;i++){
						dbExportList.add(dbMasterList.getSelection()[i]);
					}
				}
			}
		});
		addSelButton.setBounds(179, 90, 28, 25);
		addSelButton.setText(">");
		
		Button removeSelButton = new Button(grpSelectFields, SWT.NONE);
		removeSelButton.setToolTipText("Remove Selection");
		removeSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(dbExportList.getSelection().length != 0){
					String[] array = dbExportList.getSelection();
					for(int i=0;i<array.length;i++){
						dbExportList.remove(array[i]);
					}
				}
				
			}
		});
		removeSelButton.setBounds(179, 122, 28, 25);
		removeSelButton.setText("<");
		
		Button removeAllButton = new Button(grpSelectFields, SWT.NONE);
		removeAllButton.setToolTipText("Remove All");
		removeAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbExportList.removeAll();
			}
		});
		removeAllButton.setBounds(179, 155, 28, 25);
		removeAllButton.setText("<<");
		
		Button moveUpButton = new Button(grpSelectFields, SWT.NONE);
		moveUpButton.setToolTipText("Move Selection Up");
		moveUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
		moveUpButton.setBounds(385, 90, 28, 25);
		moveUpButton.setText("\u02C4");
		
		Button moveDownButton = new Button(grpSelectFields, SWT.NONE);
		moveDownButton.setToolTipText("Move Selection Down");
		moveDownButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
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
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlExportFileConfiguration.close();
			}
		});
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
		btnCancel.setBounds(399, 429, 75, 25);
		btnCancel.setText("Cancel");
		
		Button btnExport = new Button(composite, SWT.NONE);
		btnExport.setToolTipText("Export CSV File");
		btnExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell exportShell = new Shell();
				// File Save dialog
			    FileDialog fileDialog = new FileDialog(exportShell, SWT.SAVE);
				// tie the location of this shell to the application window
			    exportShell.setLocation(shlExportFileConfiguration.getLocation());
			    // prompt user if they attempt to overwrite an existing file
			    fileDialog.setOverwrite(true);
			    // Set the text
			    fileDialog.setText("Export File");
			    // Set the filter path to user's home directory
			    fileDialog.setFilterPath(System.getProperty("user.home"));
			    // Set filter on .csv files
			    fileDialog.setFilterExtensions(new String[] { "*.csv" });
			    // Put in a name for the filter
			    fileDialog.setFilterNames(new String[] { "CSV Files(*.csv)" });
			    // Open Dialog and save result of selection
			    String selected = fileDialog.open();
			    System.out.println(selected);
			}
		});
		btnExport.setBounds(318, 429, 75, 25);
		btnExport.setText("Export");

	}
	
	private void exportFile(Shell shell) {
	    // File Save dialog
	    FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
	    // Set the text
	    fileDialog.setText("Export File");
	    // Set filter on .txt files
	    fileDialog.setFilterExtensions(new String[] { "*.csv" });
	    // Put in a readable name for the filter
	    fileDialog.setFilterNames(new String[] { "Textfiles(*.txt)" });
	    // Open Dialog and save result of selection
	    String selected = fileDialog.open();
	    System.out.println(selected);
	    
	   
	    // Directly standard selection
	    DirectoryDialog dirDialog = new DirectoryDialog(shell);
	    dirDialog.setText("Select your home directory");
	    String selectedDir = dirDialog.open();
	    System.out.println(selectedDir);

	    // Select Font
	    FontDialog fontDialog = new FontDialog(shell);
	    fontDialog.setText("Select your favorite font");
	    FontData selectedFont = fontDialog.open();
	    System.out.println(selectedFont);

	    // Select Color
	    ColorDialog colorDialog = new ColorDialog(shell);
	    colorDialog.setText("Select your favorite color");
	    RGB selectedColor = colorDialog.open();
	    System.out.println(selectedColor);

	    // Message
	    MessageBox messageDialog = new MessageBox(shell, SWT.ERROR);
	    messageDialog.setText("Evil Error has happend");
	    messageDialog.setMessage("This is fatal!!!");
	    int returnCode = messageDialog.open();
	    System.out.println(returnCode);

	    // Message with ok and cancel button and info icon
	    messageDialog = new MessageBox(shell, 
	        SWT.ICON_QUESTION | 
	        SWT.OK
	        | SWT.CANCEL);
	    messageDialog.setText("My info");
	    messageDialog.setMessage("Do you really want to do this.");
	    returnCode = messageDialog.open();
	    System.out.println(returnCode);
	  }

	

}
