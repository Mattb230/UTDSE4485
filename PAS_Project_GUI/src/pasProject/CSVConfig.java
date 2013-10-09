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
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TouchEvent;

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
		
		/////////////////////////////////Setup UI Components////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////
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
		
		final Button colHdrCheckButton = new Button(grpSettings, SWT.CHECK);
		colHdrCheckButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		colHdrCheckButton.setText("Include Column Headers");
		colHdrCheckButton.setSelection(true);
		colHdrCheckButton.setToolTipText("Include column headers in export file");
		colHdrCheckButton.setBounds(107, 139, 152, 16);
		
		final Button saveButton = new Button(grpSettings, SWT.NONE);
		saveButton.setToolTipText("Save Configuration Settings");
		saveButton.setBounds(386, 141, 68, 25);
		saveButton.setText("Save");
		
		Combo configFilesCombo = new Combo(grpSettings, SWT.NONE);
		configFilesCombo.setBounds(217, 32, 237, 23);
		
		Label lblSettings = new Label(grpSettings, SWT.NONE);
		lblSettings.setBounds(217, 11, 122, 15);
		lblSettings.setText("Saved Configurations ");
		
		Group grpSelectFields = new Group(composite, SWT.NONE);
		grpSelectFields.setText("Fields to Export");
		grpSelectFields.setBounds(10, 222, 464, 197);
		
		final List dbMasterList = new List(grpSelectFields, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		dbMasterList.setBounds(23, 51, 125, 136);
		
		// TODO implement code to pull column names from linked list and place them into dbMasterList
		// the lines within the DEMO brackets are for demo purposes only. REPLACE WITH FINAL IMPLEMENTATION
		////////////////////////////////DEMO/////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////
		final LinkedList<String> inputList = new LinkedList<String>(new ArrayList<String>());
		for(int i=0;i<30;i++){
			inputList.add("Column" + i);
		}
		ListIterator<String> iterator = inputList.listIterator();
		while(iterator.hasNext()){
			dbMasterList.add(iterator.next().toString());
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		final List dbExportList = new List(grpSelectFields, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		dbExportList.setBounds(237, 51, 125, 136);
		
		final Button addAllButton = new Button(grpSelectFields, SWT.NONE);
		addAllButton.setToolTipText("Add All");
		addAllButton.setBounds(179, 57, 28, 25);
		addAllButton.setText(">>");
		
		final Button addSelButton = new Button(grpSelectFields, SWT.NONE);
		addSelButton.setToolTipText("Add Selection");
		addSelButton.setBounds(179, 90, 28, 25);
		addSelButton.setText(">");
		addSelButton.setEnabled(false);
		
		final Button removeSelButton = new Button(grpSelectFields, SWT.NONE);
		removeSelButton.setToolTipText("Remove Selection");
		removeSelButton.setBounds(179, 122, 28, 25);
		removeSelButton.setText("<");
		removeSelButton.setEnabled(false);
		
		final Button removeAllButton = new Button(grpSelectFields, SWT.NONE);
		removeAllButton.setToolTipText("Remove All");
		removeAllButton.setBounds(179, 155, 28, 25);
		removeAllButton.setText("<<");
		removeAllButton.setEnabled(false);
		
		final Button moveUpButton = new Button(grpSelectFields, SWT.NONE);
		moveUpButton.setToolTipText("Move Selection Up");
		moveUpButton.setEnabled(false);
				
		moveUpButton.setBounds(385, 90, 28, 25);
		moveUpButton.setText("\u02C4");
		
		final Button moveDownButton = new Button(grpSelectFields, SWT.NONE);
		moveDownButton.setToolTipText("Move Selection Down");
		moveDownButton.setEnabled(false);
		
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
		
		final Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setBounds(399, 429, 75, 25);
		btnCancel.setText("Cancel");
		
		final Button btnExport = new Button(composite, SWT.NONE);
		btnExport.setToolTipText("Export CSV File");
		btnExport.setBounds(318, 429, 75, 25);
		btnExport.setText("Export");
		
		final Button btnRestoreDefaults = new Button(grpSettings, SWT.NONE);
		btnRestoreDefaults.setToolTipText("Restore Default Settings");
		btnRestoreDefaults.setBounds(283, 141, 97, 25);
		btnRestoreDefaults.setText("Restore Defaults");
		///////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////Listeners / Event Handlers/////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////
		addAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbExportList.removeAll();
				dbMasterList.removeAll();
				ListIterator<String> iterator = inputList.listIterator();
				while(iterator.hasNext()){
					dbExportList.add(iterator.next().toString());
				}
				removeAllButton.setEnabled(true);
				addAllButton.setEnabled(false);
				addSelButton.setEnabled(false);
			}
		});
		
		addSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] array = dbMasterList.getSelection();
				if(array.length != 0){
					for(int i=0;i<array.length;i++){
						dbExportList.add(array[i]);
						dbMasterList.remove(array[i]);
					}
				}
				if(dbExportList.getItemCount() > 0){
					removeAllButton.setEnabled(true);
				}
				addSelButton.setEnabled(false);
			}
		});
		
		removeSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] array = dbExportList.getSelection();
				if(array.length != 0){
					for(int i=0;i<array.length;i++){
						dbExportList.remove(array[i]);
						int linkIndex = inputList.indexOf(array[i]);
						if(dbMasterList.getItemCount() == 0){
							dbMasterList.add(array[i]);
						}
						else if(linkIndex+1 > inputList.indexOf(dbMasterList.getItem(dbMasterList.getItemCount()-1))){
							dbMasterList.add(array[i]);
						}
						else{
							
							for(int k=0;k<dbMasterList.getItemCount();k++){
								int masterIndex = inputList.indexOf(dbMasterList.getItem(k));
								if(linkIndex < masterIndex){
									dbMasterList.add(array[i], k);
									break;
								}
							}
						}
					}
				}
				addAllButton.setEnabled(true);
				removeSelButton.setEnabled(false);
				moveUpButton.setEnabled(false);
				moveDownButton.setEnabled(false);
			}
		});
		
		removeAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbExportList.removeAll();
				dbMasterList.removeAll();
				ListIterator<String> iterator = inputList.listIterator();
				while(iterator.hasNext()){
					dbMasterList.add(iterator.next().toString());
				}
				removeAllButton.setEnabled(false);
				addAllButton.setEnabled(true);
				moveUpButton.setEnabled(false);
				moveDownButton.setEnabled(false);
			}
		});
		
		moveUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(dbExportList.getSelectionIndex() > 0){
					int index = dbExportList.getSelectionIndex();
					String item = dbExportList.getSelection()[0];
					dbExportList.remove(item);
					dbExportList.add(item, index-1);
					dbExportList.select(index-1);
					moveDownButton.setEnabled(true);
				}
				if(dbExportList.getSelectionIndex() == 0){
					moveUpButton.setEnabled(false);
				}
			}
		});
		
		moveDownButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(dbExportList.getSelectionIndex() < dbExportList.getItemCount()-2){
					int index = dbExportList.getSelectionIndex();
					String item = dbExportList.getSelection()[0];
					dbExportList.remove(item);
					dbExportList.add(item, index+1);
					dbExportList.select(index+1);
					moveUpButton.setEnabled(true);
				}
				else if(dbExportList.getSelectionIndex() == dbExportList.getItemCount()-2){
					String item = dbExportList.getSelection()[0];
					dbExportList.remove(item);
					dbExportList.add(item);
					dbExportList.select(dbExportList.getItemCount()-1);
				}
				if(dbExportList.getSelectionIndex() == dbExportList.getItemCount()-1){
					moveDownButton.setEnabled(false);	
				}
			}
		});
		
		dbMasterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(dbMasterList.getSelectionCount() > 0){
					addSelButton.setEnabled(true);
				}
			}
		});
				
		dbExportList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				switch (dbExportList.getSelection().length) {
				case 1:
					if(dbExportList.getSelectionIndex() > 0){
						moveUpButton.setEnabled(true);
					}
					else{
						moveUpButton.setEnabled(false);
					}
					if(dbExportList.getSelectionIndex() < dbExportList.getItemCount()-1){
						moveDownButton.setEnabled(true);
					}
					else{
						moveDownButton.setEnabled(false);
					}
					
					removeSelButton.setEnabled(true);
					break;
				default:
					removeSelButton.setEnabled(true);
					moveUpButton.setEnabled(false);
					moveDownButton.setEnabled(false);
				}
				
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shlExportFileConfiguration.close();
			}
		});
		
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
			    dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
				
			}
		});
		
		btnRestoreDefaults.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//restore default characters
				debitText.setText("-");
				creditText.setText("+");
				delimiterText.setText(",");
				colHdrCheckButton.setSelection(true);
				dbExportList.removeAll();
				dbMasterList.removeAll();
				ListIterator<String> iterator = inputList.listIterator();
				while(iterator.hasNext()){
					dbMasterList.add(iterator.next().toString());
				}
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
				removeAllButton.setEnabled(false);
				addAllButton.setEnabled(true);
			}
		});
		
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell shlSaveDialog = new Shell();
				SaveDialog saveDialog = new SaveDialog(shlSaveDialog, SWT.APPLICATION_MODAL, shlExportFileConfiguration.getLocation());
				saveDialog.open();
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		shlExportFileConfiguration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		composite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		grpSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		grpSelectFields.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		colHdrCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		delimiterText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		lblDelimiter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		creditText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		lblCreditOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		debitText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		lblDebitOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		configFilesCombo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		lblSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		lblMasterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		lblExportList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		lblChangeOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				dbMasterList.deselectAll();
				dbExportList.deselectAll();
				addSelButton.setEnabled(false);
				removeSelButton.setEnabled(false);
			}
		});
		
		
	}

}
