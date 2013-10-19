package pasProject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class CSVConfig {

	protected static Shell configurationShell;
	private Text userDebitText;
	private Text userCreditText;
	private Text userDelimiterText;
	private Text adminDebitText;
	private Text adminCreditText;
	private Text adminDelimiterText;
	private static String reportName;
	private static boolean admin;
	private static boolean loginValid = false;
	private static boolean loginCancel = false;
	private static boolean[] loginState = new boolean[2];
	private static ListIterator<String> iterator;
	private Text adminReportNameText;
	private static String username1 = "admin";
	private static String username2 = "client";
	private static String password1 = "admin";
	private static String password2 = "client";
	private Table adminExportTable;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		try {
			//run the login dialog
			Shell shlLoginDialog = new Shell();
			LoginDialog loginDialog = new LoginDialog(shlLoginDialog, SWT.APPLICATION_MODAL);
			loginState = loginDialog.open();
			//if the login credentials are not valid, end program
			if(!loginState[1]){
				System.out.println("exiting program");
				return;
			}
			//is the user an admin or client? true = admin
			admin = loginState[0];
			
			//make connection with database
			MySQLAccess dao = new MySQLAccess();
			//TODO define query string to get list of column names
			String query = new String("");
			dao.readDataBase(query);
			reportName = "PAS Report";
			//admin = true;
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
		createUserContents();
		configurationShell.open();
		configurationShell.layout();
		while (!configurationShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createUserContents() {
		
		/////////////////////////////////Setup UI Components////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////
		configurationShell = new Shell();
		configurationShell.setSize(973, 587);
		configurationShell.setText("PAS Export File Configuration");
		configurationShell.setLayout(new FillLayout(SWT.HORIZONTAL));
		configurationShell.setLocation(100, 100);
		
		CTabFolder tabFolder = new CTabFolder(configurationShell, SWT.BORDER);
		tabFolder.setBounds(0, 0, 484, 392);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		if(admin){
			adminContents(tabFolder);
		}
		
		//////////////////////////////////////////Admin Tab Components////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*CTabItem tbtmAdmin = new CTabItem(tabFolder, SWT.NONE);
		tbtmAdmin.setText("Administration");
		Composite adminComposite = new Composite(tabFolder, SWT.NONE);
		tbtmAdmin.setControl(adminComposite);
		
		Label adminLblReportName = new Label(adminComposite, SWT.NONE);
		adminLblReportName.setToolTipText("Report name to display for user");
		adminLblReportName.setAlignment(SWT.RIGHT);
		adminLblReportName.setBounds(10, 21, 92, 15);
		adminLblReportName.setText("Report Name");
		
		adminReportNameText = new Text(adminComposite, SWT.BORDER);
		adminReportNameText.setBounds(108, 18, 131, 21);
				
		adminDebitText = new Text(adminComposite, SWT.BORDER);
		adminDebitText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		adminDebitText.setBounds(108, 52, 30, 21);
		adminDebitText.setTextLimit(1);
		//set default character
		adminDebitText.setText("-");
		
		adminCreditText = new Text(adminComposite, SWT.BORDER);
		adminCreditText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		adminCreditText.setBounds(108, 86, 30, 21);
		adminCreditText.setTextLimit(1);
		//set default character
		adminCreditText.setText("+");
		
		Label adminLblDebitOperator = new Label(adminComposite, SWT.RIGHT);
		adminLblDebitOperator.setToolTipText("Enter symbol that represents debits");
		adminLblDebitOperator.setBounds(20, 55, 82, 15);
		adminLblDebitOperator.setText("Debit Operator");
		
		Label adminLblCreditOperator = new Label(adminComposite, SWT.RIGHT);
		adminLblCreditOperator.setToolTipText("Enter symbol that represents credits");
		adminLblCreditOperator.setBounds(20, 89, 82, 15);
		adminLblCreditOperator.setText("Credit Operator");
		
		adminDelimiterText = new Text(adminComposite, SWT.BORDER);
		adminDelimiterText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		adminDelimiterText.setBounds(108, 120, 30, 21);
		adminDelimiterText.setTextLimit(1);
		//set default character
		adminDelimiterText.setText(",");
		
		Label adminLblDelimiter = new Label(adminComposite, SWT.RIGHT);
		adminLblDelimiter.setToolTipText("Enter desired delimiter character");
		adminLblDelimiter.setBounds(47, 123, 55, 15);
		adminLblDelimiter.setText("Delimiter");
		
		final Button adminColHdrCheckButton = new Button(adminComposite, SWT.CHECK);
		adminColHdrCheckButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		adminColHdrCheckButton.setText("Include Column Headers");
		adminColHdrCheckButton.setSelection(true);
		adminColHdrCheckButton.setToolTipText("Include column headers in export file");
		adminColHdrCheckButton.setBounds(227, 96, 152, 16);
		
		final Button adminBtnRestoreDefaults = new Button(adminComposite, SWT.NONE);
		adminBtnRestoreDefaults.setToolTipText("Restore Default Report Settings");
		adminBtnRestoreDefaults.setBounds(293, 118, 97, 25);
		adminBtnRestoreDefaults.setText("Restore Defaults");
		
		final Button adminSaveButton = new Button(adminComposite, SWT.NONE);
		adminSaveButton.setToolTipText("Save Report Settings");
		adminSaveButton.setBounds(784, 485, 68, 25);
		adminSaveButton.setText("Save");
		
		Combo adminConfigFilesCombo = new Combo(adminComposite, SWT.NONE);
		adminConfigFilesCombo.setBounds(227, 67, 237, 23);
		
		Label adminLblSavedConfig = new Label(adminComposite, SWT.NONE);
		adminLblSavedConfig.setBounds(227, 46, 45, 15);
		adminLblSavedConfig.setText("Reports");
		
		final Label adminWarning1a = new Label(adminComposite, SWT.NONE);
		adminWarning1a.setVisible(false);
		adminWarning1a.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		adminWarning1a.setBounds(144, 58, 55, 15);
		adminWarning1a.setText("Debit");
		
		final Label adminWarning1b = new Label(adminComposite, SWT.NONE);
		adminWarning1b.setVisible(false);
		adminWarning1b.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		adminWarning1b.setBounds(144, 79, 55, 15);
		adminWarning1b.setText("Credit");
		
		final Label adminWarning1c = new Label(adminComposite, SWT.NONE);
		adminWarning1c.setVisible(false);
		adminWarning1c.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		adminWarning1c.setBounds(144, 100, 55, 15);
		adminWarning1c.setText("Delimiter");
		
		final Label adminWarning1d = new Label(adminComposite, SWT.NONE);
		adminWarning1d.setVisible(false);
		adminWarning1d.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		adminWarning1d.setBounds(144, 121, 90, 15);
		adminWarning1d.setText("must be unique");
		
		final List adminDBMasterList = new List(adminComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		adminDBMasterList.setBounds(154, 119, 125, 46);
		
		// TODO implement code to pull column names from linked list and place them into adminDBMasterList
		// the lines within the DEMO brackets are for demo purposes only. REPLACE WITH FINAL IMPLEMENTATION
		////////////////////////////////DEMO/////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////
		final LinkedList<String> adminInputList = new LinkedList<String>(new ArrayList<String>());
		for(int i=0;i<30;i++){
			adminInputList.add("Column" + i);
		}
		iterator = adminInputList.listIterator();
		while(iterator.hasNext()){
			adminDBMasterList.add(iterator.next().toString());
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		final List adminDBExportList = new List(adminComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		adminDBExportList.setBounds(491, 17, 131, 79);
		
		final Button adminAddAllButton = new Button(adminComposite, SWT.NONE);
		adminAddAllButton.setToolTipText("Add All");
		adminAddAllButton.setBounds(293, 202, 28, 25);
		adminAddAllButton.setText(">>");
		
		final Button adminAddSelButton = new Button(adminComposite, SWT.NONE);
		adminAddSelButton.setToolTipText("Add Selection");
		adminAddSelButton.setBounds(293, 235, 28, 25);
		adminAddSelButton.setText(">");
		adminAddSelButton.setEnabled(false);
		
		final Button adminRemoveSelButton = new Button(adminComposite, SWT.NONE);
		adminRemoveSelButton.setToolTipText("Remove Selection");
		adminRemoveSelButton.setBounds(293, 267, 28, 25);
		adminRemoveSelButton.setText("<");
		adminRemoveSelButton.setEnabled(false);
		
		final Button adminRemoveAllButton = new Button(adminComposite, SWT.NONE);
		adminRemoveAllButton.setToolTipText("Remove All");
		adminRemoveAllButton.setBounds(293, 300, 28, 25);
		adminRemoveAllButton.setText("<<");
		adminRemoveAllButton.setEnabled(false);
		
		final Button adminMoveUpButton = new Button(adminComposite, SWT.NONE);
		adminMoveUpButton.setToolTipText("Move Selection Up");
		adminMoveUpButton.setEnabled(false);
		adminMoveUpButton.setBounds(905, 225, 28, 25);
		adminMoveUpButton.setText("\u02C4");
		
		final Button adminMoveDownButton = new Button(adminComposite, SWT.NONE);
		adminMoveDownButton.setToolTipText("Move Selection Down");
		adminMoveDownButton.setEnabled(false);
		adminMoveDownButton.setBounds(905, 257, 28, 25);
		adminMoveDownButton.setText("\u02C5");
		
		Label adminLblMasterList = new Label(adminComposite, SWT.CENTER);
		adminLblMasterList.setBounds(51, 181, 192, 15);
		adminLblMasterList.setText("Imported Database Tables/Fields");
		
		Label adminLblExportList = new Label(adminComposite, SWT.CENTER);
		adminLblExportList.setBounds(512, 181, 63, 15);
		adminLblExportList.setText("Export List");
		
		Label adminLblChangeOrder = new Label(adminComposite, SWT.NONE);
		adminLblChangeOrder.setBounds(905, 202, 36, 15);
		adminLblChangeOrder.setText("Order");
		
		final Button adminBtnCancel = new Button(adminComposite, SWT.NONE);
		adminBtnCancel.setBounds(858, 485, 75, 25);
		adminBtnCancel.setText("Cancel");
		
		
		adminExportTable = new Table(adminComposite, SWT.BORDER);
		adminExportTable.setBounds(331, 202, 560, 269);
		adminExportTable.setHeaderVisible(true);
		adminExportTable.setLinesVisible(true);
		
		final TableColumn tblclmnTable = new TableColumn(adminExportTable, SWT.NONE);
		tblclmnTable.setWidth(140);
		tblclmnTable.setText("Field");
		
		final TableColumn tblclmnField = new TableColumn(adminExportTable, SWT.NONE);
		tblclmnField.setWidth(140);
		tblclmnField.setText("Alias");
		
		final TableColumn tblclmnAlias = new TableColumn(adminExportTable, SWT.NONE);
		tblclmnAlias.setWidth(140);
		tblclmnAlias.setText("Table");
		
		final TableColumn tblclmnDescription = new TableColumn(adminExportTable, SWT.NONE);
		tblclmnDescription.setWidth(140);
		tblclmnDescription.setText("Description");
		
		Button btnCopyFieldName = new Button(adminComposite, SWT.NONE);
		btnCopyFieldName.setBounds(630, 171, 103, 25);
		btnCopyFieldName.setText("Copy Field Name");
		
		final Tree adminImportTree = new Tree(adminComposite, SWT.BORDER | SWT.CHECK | SWT.MULTI);
		adminImportTree.setBounds(10, 202, 274, 269);
		
		////////Test Code for Import Tree List and Export Table - NOT FOR PRODUCTION//////////////
		for (int i=0;i<4;i++){
			TreeItem iItem = new TreeItem(adminImportTree, 0);
			iItem.setText("Table"+i);
			for (int j=0;j<4;j++){
				TreeItem jItem = new TreeItem (iItem, 0);
				jItem.setText("FieldNameLongXXXXXXXXXXXXXX"+j);
				TableItem item = new TableItem (adminExportTable, SWT.NONE);
			}
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		
		
		
		/////////////////////////////Tree List Listeners / Event Handlers//////////////////////
		adminImportTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				//TreeItem[] items = tree.getSelection();
				for(int i=0;i<adminImportTree.getItems().length;i++){
					if(adminImportTree.getItems()[i].equals(adminImportTree.getItem(new Point(e.x, e.y)))){
						System.out.println("treelist sees mouse click");
						System.out.println(adminImportTree.getSelectionCount()+" items selected");
						if(adminImportTree.getItems()[i].getChecked()){
							System.out.println("table was checked");
							adminImportTree.getItems()[i].setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
							for(int j=0;j<adminImportTree.getItems()[i].getItems().length;j++){
								adminImportTree.getItems()[i].getItems()[j].setChecked(true);
								adminImportTree.getItems()[i].getItems()[j].setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
								adminImportTree.getItems()[i].getItems()[j].setGrayed(true);
							}
						}
						else{
							System.out.println("table wasn't checked");
							adminImportTree.getItems()[i].setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
							for(int j=0;j<adminImportTree.getItems()[i].getItems().length;j++){
								adminImportTree.getItems()[i].getItems()[j].setChecked(false);
								adminImportTree.getItems()[i].getItems()[j].setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
								adminImportTree.getItems()[i].getItems()[j].setGrayed(false); 
							}
							
						}
					}
					for(int k=0;k<adminImportTree.getItems()[i].getItems().length;k++){
						if(adminImportTree.getItems()[i].getItems()[k].getGrayed()){
							adminImportTree.getItems()[i].getItems()[k].setChecked(true);
						}
					}
				}
			}
		});
		////////////////////////////////////////////////////////////////////////////////////////
		
		/////////////////////////////Export Table Listeners / Event Handlers//////////////////////
		
		adminExportTable.addListener(SWT.MouseDown, new Listener(){
			public void handleEvent(Event event) {
		        Rectangle clientArea = adminExportTable.getClientArea();
		        Point pt = new Point(event.x, event.y);
		        int index = adminExportTable.getTopIndex();
		        while (index < adminExportTable.getItemCount()) {
		          boolean visible = false;
		          TableItem item = adminExportTable.getItem(index);
		          for (int i = 0; i < 4; i++) {
		            Rectangle rect = item.getBounds(i);
		            if (rect.contains(pt)) {
		              System.out.println("Item " + index + "-" + i);
		              if(i == 3){
		            	  //item.
		              }
		            }
		            if (!visible && rect.intersects(clientArea)) {
		              visible = true;
		            }
		          }
		          if (!visible)
		            return;
		          index++;
		        }
		      }
	    });
		
		////////////////////////////////////////////////////////////////////////////////////////

		////////////////////////////////Listeners / Event Handlers/////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////
		adminAddAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBExportList.removeAll();
				adminDBMasterList.removeAll();
				iterator = adminInputList.listIterator();
				while(iterator.hasNext()){
					adminDBExportList.add(iterator.next().toString());
				}
				adminRemoveAllButton.setEnabled(true);
				adminAddAllButton.setEnabled(false);
				adminAddSelButton.setEnabled(false);
			}
		});
		
		adminAddSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] array = adminDBMasterList.getSelection();
				if(array.length != 0){
					for(int i=0;i<array.length;i++){
						adminDBExportList.add(array[i]);
						adminDBMasterList.remove(array[i]);
					}
				}
				if(adminDBExportList.getItemCount() > 0){
					adminRemoveAllButton.setEnabled(true);
				}
				adminAddSelButton.setEnabled(false);
			}
		});
		
		adminRemoveSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] array = adminDBExportList.getSelection();
				if(array.length != 0){
					for(int i=0;i<array.length;i++){
						adminDBExportList.remove(array[i]);
						int linkIndex = adminInputList.indexOf(array[i]);
						if(adminDBMasterList.getItemCount() == 0){
							adminDBMasterList.add(array[i]);
						}
						else if(linkIndex+1 > adminInputList.indexOf(adminDBMasterList.getItem(adminDBMasterList.getItemCount()-1))){
							adminDBMasterList.add(array[i]);
						}
						else{
							
							for(int k=0;k<adminDBMasterList.getItemCount();k++){
								int masterIndex = adminInputList.indexOf(adminDBMasterList.getItem(k));
								if(linkIndex < masterIndex){
									adminDBMasterList.add(array[i], k);
									break;
								}
							}
						}
					}
				}
				adminAddAllButton.setEnabled(true);
				adminRemoveSelButton.setEnabled(false);
				adminMoveUpButton.setEnabled(false);
				adminMoveDownButton.setEnabled(false);
			}
		});
		
		adminRemoveAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBExportList.removeAll();
				adminDBMasterList.removeAll();
				iterator = adminInputList.listIterator();
				while(iterator.hasNext()){
					adminDBMasterList.add(iterator.next().toString());
				}
				adminRemoveAllButton.setEnabled(false);
				adminAddAllButton.setEnabled(true);
				adminMoveUpButton.setEnabled(false);
				adminMoveDownButton.setEnabled(false);
			}
		});
		
		adminMoveUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(adminDBExportList.getSelectionIndex() > 0){
					int index = adminDBExportList.getSelectionIndex();
					String item = adminDBExportList.getSelection()[0];
					adminDBExportList.remove(item);
					adminDBExportList.add(item, index-1);
					adminDBExportList.select(index-1);
					adminMoveDownButton.setEnabled(true);
				}
				if(adminDBExportList.getSelectionIndex() == 0){
					adminMoveUpButton.setEnabled(false);
				}
			}
		});
		
		adminMoveDownButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(adminDBExportList.getSelectionIndex() < adminDBExportList.getItemCount()-2){
					int index = adminDBExportList.getSelectionIndex();
					String item = adminDBExportList.getSelection()[0];
					adminDBExportList.remove(item);
					adminDBExportList.add(item, index+1);
					adminDBExportList.select(index+1);
					adminMoveUpButton.setEnabled(true);
				}
				else if(adminDBExportList.getSelectionIndex() == adminDBExportList.getItemCount()-2){
					String item = adminDBExportList.getSelection()[0];
					adminDBExportList.remove(item);
					adminDBExportList.add(item);
					adminDBExportList.select(adminDBExportList.getItemCount()-1);
				}
				if(adminDBExportList.getSelectionIndex() == adminDBExportList.getItemCount()-1){
					adminMoveDownButton.setEnabled(false);	
				}
			}
		});
		
		adminDBMasterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(adminDBMasterList.getSelectionCount() > 0){
					adminAddSelButton.setEnabled(true);
				}
			}
		});
				
		adminDBExportList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				switch (adminDBExportList.getSelection().length) {
				case 1:
					if(adminDBExportList.getSelectionIndex() > 0){
						adminMoveUpButton.setEnabled(true);
					}
					else{
						adminMoveUpButton.setEnabled(false);
					}
					if(adminDBExportList.getSelectionIndex() < adminDBExportList.getItemCount()-1){
						adminMoveDownButton.setEnabled(true);
					}
					else{
						adminMoveDownButton.setEnabled(false);
					}
					
					adminRemoveSelButton.setEnabled(true);
					break;
				default:
					adminRemoveSelButton.setEnabled(true);
					adminMoveUpButton.setEnabled(false);
					adminMoveDownButton.setEnabled(false);
				}
				
			}
		});
		
		adminBtnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				configurationShell.close();
			}
		});
		
		adminBtnRestoreDefaults.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//restore default characters
				adminWarning1a.setVisible(false);
				adminWarning1b.setVisible(false);
				adminWarning1c.setVisible(false);
				adminWarning1d.setVisible(false);
				adminSaveButton.setEnabled(true);
				adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				adminDebitText.setText("-");
				adminCreditText.setText("+");
				adminDelimiterText.setText(",");
				adminColHdrCheckButton.setSelection(true);
				adminDBExportList.removeAll();
				adminDBMasterList.removeAll();
				iterator = adminInputList.listIterator();
				while(iterator.hasNext()){
					adminDBMasterList.add(iterator.next().toString());
				}
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
				adminRemoveAllButton.setEnabled(false);
				adminAddAllButton.setEnabled(true);
			}
		});
		
		adminSaveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell shlSaveDialog = new Shell();
				SaveDialog saveDialog = new SaveDialog(shlSaveDialog, SWT.APPLICATION_MODAL, configurationShell.getLocation());
				saveDialog.open();
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		configurationShell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminComposite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminColHdrCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminDelimiterText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminDelimiterText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(adminDelimiterText.getText().compareTo(adminCreditText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else if(adminDelimiterText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else if(adminCreditText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else{
					adminWarning1a.setVisible(false);
					adminWarning1b.setVisible(false);
					adminWarning1c.setVisible(false);
					adminWarning1d.setVisible(false);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminSaveButton.setEnabled(true);
				}
			}
		});
		
		adminLblDelimiter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminCreditText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminCreditText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(adminDelimiterText.getText().compareTo(adminCreditText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else if(adminDelimiterText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else if(adminCreditText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else{
					adminWarning1a.setVisible(false);
					adminWarning1b.setVisible(false);
					adminWarning1c.setVisible(false);
					adminWarning1d.setVisible(false);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminSaveButton.setEnabled(true);
				}
			}
		});
		
		adminLblCreditOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminDebitText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminDebitText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(adminDelimiterText.getText().compareTo(adminCreditText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else if(adminDelimiterText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else if(adminCreditText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
				}
				else{
					adminWarning1a.setVisible(false);
					adminWarning1b.setVisible(false);
					adminWarning1c.setVisible(false);
					adminWarning1d.setVisible(false);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminSaveButton.setEnabled(true);
				}
			}
		});
			
		adminLblDebitOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminConfigFilesCombo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminLblSavedConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminLblMasterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminLblExportList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminLblChangeOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});*/
		
		
		
		
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//////////////////////////////////////////User Tab Components////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		CTabItem tbtmUser = new CTabItem(tabFolder, SWT.NONE);
		tbtmUser.setText(reportName);
				
		Composite userComposite = new Composite(tabFolder, SWT.NONE);
		//userComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tbtmUser.setControl(userComposite);
		
		Group userGrpSettings = new Group(userComposite, SWT.NONE);
		userGrpSettings.setText("Settings");
		userGrpSettings.setBounds(10, 62, 464, 135);
		
		userDebitText = new Text(userGrpSettings, SWT.BORDER);
		userDebitText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		userDebitText.setBounds(98, 31, 30, 21);
		userDebitText.setTextLimit(1);
		//set default character
		userDebitText.setText("-");
		
		userCreditText = new Text(userGrpSettings, SWT.BORDER);
		userCreditText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		userCreditText.setBounds(98, 66, 30, 21);
		userCreditText.setTextLimit(1);
		//set default character
		userCreditText.setText("+");
		
		Label userLblDebitOperator = new Label(userGrpSettings, SWT.RIGHT);
		userLblDebitOperator.setToolTipText("Enter symbol that represents debits");
		userLblDebitOperator.setBounds(10, 34, 82, 15);
		userLblDebitOperator.setText("Debit Operator");
		
		Label userLblCreditOperator = new Label(userGrpSettings, SWT.RIGHT);
		userLblCreditOperator.setToolTipText("Enter symbol that represents credits");
		userLblCreditOperator.setBounds(10, 69, 82, 15);
		userLblCreditOperator.setText("Credit Operator");
		
		userDelimiterText = new Text(userGrpSettings, SWT.BORDER);
		userDelimiterText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		userDelimiterText.setBounds(98, 100, 30, 21);
		userDelimiterText.setTextLimit(1);
		//set default character
		userDelimiterText.setText(",");
		
		Label userLblDelimiter = new Label(userGrpSettings, SWT.RIGHT);
		userLblDelimiter.setToolTipText("Enter desired delimiter character");
		userLblDelimiter.setBounds(37, 103, 55, 15);
		userLblDelimiter.setText("Delimiter");
		
		final Button userColHdrCheckButton = new Button(userGrpSettings, SWT.CHECK);
		userColHdrCheckButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		userColHdrCheckButton.setText("Include Column Headers");
		userColHdrCheckButton.setSelection(true);
		userColHdrCheckButton.setToolTipText("Include column headers in export file");
		userColHdrCheckButton.setBounds(217, 66, 152, 16);
		
		final Button userBtnRestoreDefaults = new Button(userGrpSettings, SWT.NONE);
		userBtnRestoreDefaults.setToolTipText("Restore Default Report Settings");
		userBtnRestoreDefaults.setBounds(283, 96, 97, 25);
		userBtnRestoreDefaults.setText("Restore Defaults");
		
		final Button userSaveButton = new Button(userGrpSettings, SWT.NONE);
		userSaveButton.setToolTipText("Save Report Settings");
		userSaveButton.setBounds(386, 96, 68, 25);
		userSaveButton.setText("Save");
		
		Combo userConfigFilesCombo = new Combo(userGrpSettings, SWT.NONE);
		userConfigFilesCombo.setBounds(217, 32, 237, 23);
		
		Label userLblSavedConfig = new Label(userGrpSettings, SWT.NONE);
		userLblSavedConfig.setBounds(217, 11, 163, 15);
		userLblSavedConfig.setText("Saved Report Configurations");
		
		final Label userWarning1a = new Label(userGrpSettings, SWT.NONE);
		userWarning1a.setVisible(false);
		userWarning1a.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		userWarning1a.setBounds(134, 37, 55, 15);
		userWarning1a.setText("Debit");
		
		final Label userWarning1b = new Label(userGrpSettings, SWT.NONE);
		userWarning1b.setVisible(false);
		userWarning1b.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		userWarning1b.setBounds(134, 58, 55, 15);
		userWarning1b.setText("Credit");
		
		final Label userWarning1c = new Label(userGrpSettings, SWT.NONE);
		userWarning1c.setVisible(false);
		userWarning1c.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		userWarning1c.setBounds(134, 79, 55, 15);
		userWarning1c.setText("Delimiter");
		
		final Label userWarning1d = new Label(userGrpSettings, SWT.NONE);
		userWarning1d.setVisible(false);
		userWarning1d.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		userWarning1d.setBounds(134, 100, 90, 15);
		userWarning1d.setText("must be unique");
		
		Group userGrpSelectFields = new Group(userComposite, SWT.NONE);
		userGrpSelectFields.setText("Fields to Export");
		userGrpSelectFields.setBounds(10, 203, 464, 197);
		
		final List userDBMasterList = new List(userGrpSelectFields, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		userDBMasterList.setBounds(23, 51, 125, 136);
		
		// TODO implement code to pull column names from linked list and place them into dbMasterList
		// the lines within the DEMO brackets are for demo purposes only. REPLACE WITH FINAL IMPLEMENTATION
		////////////////////////////////DEMO/////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////
		final LinkedList<String> userInputList = new LinkedList<String>(new ArrayList<String>());
		for(int i=0;i<30;i++){
			userInputList.add("Column" + i);
		}
		iterator = userInputList.listIterator();
		while(iterator.hasNext()){
			userDBMasterList.add(iterator.next().toString());
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		final List userDBExportList = new List(userGrpSelectFields, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		userDBExportList.setBounds(237, 51, 125, 136);
		
		final Button userAddAllButton = new Button(userGrpSelectFields, SWT.NONE);
		userAddAllButton.setToolTipText("Add All");
		userAddAllButton.setBounds(179, 57, 28, 25);
		userAddAllButton.setText(">>");
		
		final Button userAddSelButton = new Button(userGrpSelectFields, SWT.NONE);
		userAddSelButton.setToolTipText("Add Selection");
		userAddSelButton.setBounds(179, 90, 28, 25);
		userAddSelButton.setText(">");
		userAddSelButton.setEnabled(false);
		
		final Button userRemoveSelButton = new Button(userGrpSelectFields, SWT.NONE);
		userRemoveSelButton.setToolTipText("Remove Selection");
		userRemoveSelButton.setBounds(179, 122, 28, 25);
		userRemoveSelButton.setText("<");
		userRemoveSelButton.setEnabled(false);
		
		final Button userRemoveAllButton = new Button(userGrpSelectFields, SWT.NONE);
		userRemoveAllButton.setToolTipText("Remove All");
		userRemoveAllButton.setBounds(179, 155, 28, 25);
		userRemoveAllButton.setText("<<");
		userRemoveAllButton.setEnabled(false);
		
		final Button userMoveUpButton = new Button(userGrpSelectFields, SWT.NONE);
		userMoveUpButton.setToolTipText("Move Selection Up");
		userMoveUpButton.setEnabled(false);
				
		userMoveUpButton.setBounds(385, 90, 28, 25);
		userMoveUpButton.setText("\u02C4");
		
		final Button userMoveDownButton = new Button(userGrpSelectFields, SWT.NONE);
		userMoveDownButton.setToolTipText("Move Selection Down");
		userMoveDownButton.setEnabled(false);
		
		userMoveDownButton.setBounds(385, 122, 28, 25);
		userMoveDownButton.setText("\u02C5");
		
		Label userLblMasterList = new Label(userGrpSelectFields, SWT.CENTER);
		userLblMasterList.setBounds(50, 30, 63, 15);
		userLblMasterList.setText("Master List");
		
		Label userLblExportList = new Label(userGrpSelectFields, SWT.CENTER);
		userLblExportList.setBounds(265, 30, 63, 15);
		userLblExportList.setText("Export List");
		
		Label userLblChangeOrder = new Label(userGrpSelectFields, SWT.NONE);
		userLblChangeOrder.setBounds(385, 67, 36, 15);
		userLblChangeOrder.setText("Order");
		
		final Button userBtnCancel = new Button(userComposite, SWT.NONE);
		userBtnCancel.setBounds(399, 406, 75, 25);
		userBtnCancel.setText("Cancel");
		
		final Button userBtnExport = new Button(userComposite, SWT.NONE);
		userBtnExport.setToolTipText("Export CSV File");
		userBtnExport.setBounds(318, 406, 75, 25);
		userBtnExport.setText("Export");
		
		Combo userSelReportCombo = new Combo(userComposite, SWT.NONE);
		userSelReportCombo.setBounds(20, 33, 237, 23);
		
		Label userSelReportLabel = new Label(userComposite, SWT.NONE);
		userSelReportLabel.setBounds(20, 12, 75, 15);
		userSelReportLabel.setText("Select Report");
		
		///////////////////////////////////////////////////////////////////////////////////////
		
		////////////////////////////////Listeners / Event Handlers/////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////
		userAddAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBExportList.removeAll();
				userDBMasterList.removeAll();
				iterator = userInputList.listIterator();
				while(iterator.hasNext()){
					userDBExportList.add(iterator.next().toString());
				}
				userRemoveAllButton.setEnabled(true);
				userAddAllButton.setEnabled(false);
				userAddSelButton.setEnabled(false);
			}
		});
		
		userAddSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] array = userDBMasterList.getSelection();
				if(array.length != 0){
					for(int i=0;i<array.length;i++){
						userDBExportList.add(array[i]);
						userDBMasterList.remove(array[i]);
					}
				}
				if(userDBExportList.getItemCount() > 0){
					userRemoveAllButton.setEnabled(true);
				}
				userAddSelButton.setEnabled(false);
			}
		});
		
		userRemoveSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] array = userDBExportList.getSelection();
				if(array.length != 0){
					for(int i=0;i<array.length;i++){
						userDBExportList.remove(array[i]);
						int linkIndex = userInputList.indexOf(array[i]);
						if(userDBMasterList.getItemCount() == 0){
							userDBMasterList.add(array[i]);
						}
						else if(linkIndex+1 > userInputList.indexOf(userDBMasterList.getItem(userDBMasterList.getItemCount()-1))){
							userDBMasterList.add(array[i]);
						}
						else{
							
							for(int k=0;k<userDBMasterList.getItemCount();k++){
								int masterIndex = userInputList.indexOf(userDBMasterList.getItem(k));
								if(linkIndex < masterIndex){
									userDBMasterList.add(array[i], k);
									break;
								}
							}
						}
					}
				}
				userAddAllButton.setEnabled(true);
				userRemoveSelButton.setEnabled(false);
				userMoveUpButton.setEnabled(false);
				userMoveDownButton.setEnabled(false);
			}
		});
		
		userRemoveAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBExportList.removeAll();
				userDBMasterList.removeAll();
				iterator = userInputList.listIterator();
				while(iterator.hasNext()){
					userDBMasterList.add(iterator.next().toString());
				}
				userRemoveAllButton.setEnabled(false);
				userAddAllButton.setEnabled(true);
				userMoveUpButton.setEnabled(false);
				userMoveDownButton.setEnabled(false);
			}
		});
		
		userMoveUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(userDBExportList.getSelectionIndex() > 0){
					int index = userDBExportList.getSelectionIndex();
					String item = userDBExportList.getSelection()[0];
					userDBExportList.remove(item);
					userDBExportList.add(item, index-1);
					userDBExportList.select(index-1);
					userMoveDownButton.setEnabled(true);
				}
				if(userDBExportList.getSelectionIndex() == 0){
					userMoveUpButton.setEnabled(false);
				}
			}
		});
		
		userMoveDownButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(userDBExportList.getSelectionIndex() < userDBExportList.getItemCount()-2){
					int index = userDBExportList.getSelectionIndex();
					String item = userDBExportList.getSelection()[0];
					userDBExportList.remove(item);
					userDBExportList.add(item, index+1);
					userDBExportList.select(index+1);
					userMoveUpButton.setEnabled(true);
				}
				else if(userDBExportList.getSelectionIndex() == userDBExportList.getItemCount()-2){
					String item = userDBExportList.getSelection()[0];
					userDBExportList.remove(item);
					userDBExportList.add(item);
					userDBExportList.select(userDBExportList.getItemCount()-1);
				}
				if(userDBExportList.getSelectionIndex() == userDBExportList.getItemCount()-1){
					userMoveDownButton.setEnabled(false);	
				}
			}
		});
		
		userDBMasterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(userDBMasterList.getSelectionCount() > 0){
					userAddSelButton.setEnabled(true);
				}
			}
		});
				
		userDBExportList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				switch (userDBExportList.getSelection().length) {
				case 1:
					if(userDBExportList.getSelectionIndex() > 0){
						userMoveUpButton.setEnabled(true);
					}
					else{
						userMoveUpButton.setEnabled(false);
					}
					if(userDBExportList.getSelectionIndex() < userDBExportList.getItemCount()-1){
						userMoveDownButton.setEnabled(true);
					}
					else{
						userMoveDownButton.setEnabled(false);
					}
					
					userRemoveSelButton.setEnabled(true);
					break;
				default:
					userRemoveSelButton.setEnabled(true);
					userMoveUpButton.setEnabled(false);
					userMoveDownButton.setEnabled(false);
				}
				
			}
		});
		
		userBtnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				configurationShell.close();
			}
		});
		
		userBtnExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell exportShell = new Shell();
				// File Save dialog
			    FileDialog fileDialog = new FileDialog(exportShell, SWT.SAVE);
				// tie the location of this shell to the application window
			    exportShell.setLocation(configurationShell.getLocation());
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
			    userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
				
			}
		});
		
		userBtnRestoreDefaults.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//restore default characters
				userWarning1a.setVisible(false);
				userWarning1b.setVisible(false);
				userWarning1c.setVisible(false);
				userWarning1d.setVisible(false);
				userSaveButton.setEnabled(true);
				userBtnExport.setEnabled(true);
				userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				userDebitText.setText("-");
				userCreditText.setText("+");
				userDelimiterText.setText(",");
				userColHdrCheckButton.setSelection(true);
				userDBExportList.removeAll();
				userDBMasterList.removeAll();
				iterator = userInputList.listIterator();
				while(iterator.hasNext()){
					userDBMasterList.add(iterator.next().toString());
				}
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
				userRemoveAllButton.setEnabled(false);
				userAddAllButton.setEnabled(true);
			}
		});
		
		userSaveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell shlSaveDialog = new Shell();
				SaveDialog saveDialog = new SaveDialog(shlSaveDialog, SWT.APPLICATION_MODAL, configurationShell.getLocation());
				saveDialog.open();
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		configurationShell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userComposite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userGrpSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userGrpSelectFields.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userColHdrCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userDelimiterText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userDelimiterText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(userDelimiterText.getText().compareTo(userCreditText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else if(userDelimiterText.getText().compareTo(userDebitText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else if(userCreditText.getText().compareTo(userDebitText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else{
					userWarning1a.setVisible(false);
					userWarning1b.setVisible(false);
					userWarning1c.setVisible(false);
					userWarning1d.setVisible(false);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userSaveButton.setEnabled(true);
					userBtnExport.setEnabled(true);
				}
			}
		});
				
		userLblDelimiter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userCreditText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userCreditText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(userDelimiterText.getText().compareTo(userCreditText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else if(userDelimiterText.getText().compareTo(userDebitText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else if(userCreditText.getText().compareTo(userDebitText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else{
					userWarning1a.setVisible(false);
					userWarning1b.setVisible(false);
					userWarning1c.setVisible(false);
					userWarning1d.setVisible(false);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userSaveButton.setEnabled(true);
					userBtnExport.setEnabled(true);
				}
			}
		});
		
		userLblCreditOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userDebitText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userDebitText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(userDelimiterText.getText().compareTo(userCreditText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else if(userDelimiterText.getText().compareTo(userDebitText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else if(userCreditText.getText().compareTo(userDebitText.getText())==0){
					userWarning1a.setVisible(true);
					userWarning1b.setVisible(true);
					userWarning1c.setVisible(true);
					userWarning1d.setVisible(true);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					userSaveButton.setEnabled(false);
					userBtnExport.setEnabled(false);
				}
				else{
					userWarning1a.setVisible(false);
					userWarning1b.setVisible(false);
					userWarning1c.setVisible(false);
					userWarning1d.setVisible(false);
					userDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					userSaveButton.setEnabled(true);
					userBtnExport.setEnabled(true);
				}
			}
		});
		
		userLblDebitOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userConfigFilesCombo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userLblSavedConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userLblMasterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userLblExportList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		userLblChangeOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				userDBMasterList.deselectAll();
				userDBExportList.deselectAll();
				userAddSelButton.setEnabled(false);
				userRemoveSelButton.setEnabled(false);
			}
		});
		
		
	}
	
	protected static void loginDialog(){
		System.out.println("running login dialog");
		final Shell loginShell = new Shell();
		loginShell.setSize(220, 168);
		loginShell.setText("Login - PAS Export File Configuration");
		loginShell.setLocation(100, 100);
				
		Composite composite = new Composite(loginShell, SWT.NONE);
		composite.setBounds(0, 0, 214, 142);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(20, 34, 55, 15);
		lblNewLabel.setText("username:");
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBounds(22, 73, 55, 15);
		lblNewLabel_1.setText("password:");
		
		final Text usernameText = new Text(composite, SWT.BORDER);
		usernameText.setBounds(88, 31, 101, 21);
		
		final Text passwordText = new Text(composite, SWT.BORDER);
		passwordText.setBounds(88, 70, 101, 21);
		
		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.setBounds(20, 105, 75, 25);
		btnOk.setText("OK");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setBounds(114, 105, 75, 25);
		btnCancel.setText("Cancel");
		
		final Label lblInvalidUsername = new Label(composite, SWT.NONE);
		lblInvalidUsername.setVisible(false);
		lblInvalidUsername.setAlignment(SWT.CENTER);
		lblInvalidUsername.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblInvalidUsername.setBounds(20, 10, 167, 15);
		lblInvalidUsername.setText("Invalid username or password");
		
		////////////////////////////////////////////////listeners - event handlers//////////////////////////////////////////////////
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				loginValid = false;
				switch (usernameText.getText()) {
					case "admin":
						switch (passwordText.getText()){
							case "admin":
								admin = true;
								loginValid = true;
								break;
							default:
								lblInvalidUsername.setVisible(true);
						}
						break;
					case "client":
						switch (passwordText.getText()){
							case "client":
								admin = false;
								loginValid = true;
								break;
							default:
								lblInvalidUsername.setVisible(true);
						} 
						break;
					default:
						lblInvalidUsername.setVisible(true);
						break;
				}
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				loginCancel = true;
			}
		});
		
		if(loginValid){
			loginShell.close();
		}
		if(loginCancel){
			loginValid = false;
			loginShell.close();
		}
	}
	
	protected void adminContents(CTabFolder tabFolder){
		CTabItem tbtmAdmin = new CTabItem(tabFolder, SWT.NONE);
		tbtmAdmin.setText("Administration");
		Composite adminComposite = new Composite(tabFolder, SWT.NONE);
		tbtmAdmin.setControl(adminComposite);
		
		Label adminLblReportName = new Label(adminComposite, SWT.NONE);
		adminLblReportName.setToolTipText("Report name to display for user");
		adminLblReportName.setAlignment(SWT.RIGHT);
		adminLblReportName.setBounds(10, 21, 92, 15);
		adminLblReportName.setText("Report Name");
		
		adminReportNameText = new Text(adminComposite, SWT.BORDER);
		adminReportNameText.setBounds(108, 18, 131, 21);
				
		adminDebitText = new Text(adminComposite, SWT.BORDER);
		adminDebitText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		adminDebitText.setBounds(108, 52, 30, 21);
		adminDebitText.setTextLimit(1);
		//set default character
		adminDebitText.setText("-");
		
		adminCreditText = new Text(adminComposite, SWT.BORDER);
		adminCreditText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		adminCreditText.setBounds(108, 86, 30, 21);
		adminCreditText.setTextLimit(1);
		//set default character
		adminCreditText.setText("+");
		
		Label adminLblDebitOperator = new Label(adminComposite, SWT.RIGHT);
		adminLblDebitOperator.setToolTipText("Enter symbol that represents debits");
		adminLblDebitOperator.setBounds(20, 55, 82, 15);
		adminLblDebitOperator.setText("Debit Operator");
		
		Label adminLblCreditOperator = new Label(adminComposite, SWT.RIGHT);
		adminLblCreditOperator.setToolTipText("Enter symbol that represents credits");
		adminLblCreditOperator.setBounds(20, 89, 82, 15);
		adminLblCreditOperator.setText("Credit Operator");
		
		adminDelimiterText = new Text(adminComposite, SWT.BORDER);
		adminDelimiterText.setToolTipText("Debit, credit, and delimiter must be mutually exclusive");
		adminDelimiterText.setBounds(108, 120, 30, 21);
		adminDelimiterText.setTextLimit(1);
		//set default character
		adminDelimiterText.setText(",");
		
		Label adminLblDelimiter = new Label(adminComposite, SWT.RIGHT);
		adminLblDelimiter.setToolTipText("Enter desired delimiter character");
		adminLblDelimiter.setBounds(47, 123, 55, 15);
		adminLblDelimiter.setText("Delimiter");
		
		final Button adminColHdrCheckButton = new Button(adminComposite, SWT.CHECK);
		adminColHdrCheckButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		adminColHdrCheckButton.setText("Include Column Headers");
		adminColHdrCheckButton.setSelection(true);
		adminColHdrCheckButton.setToolTipText("Include column headers in export file");
		adminColHdrCheckButton.setBounds(227, 96, 152, 16);
		
		final Button adminBtnRestoreDefaults = new Button(adminComposite, SWT.NONE);
		adminBtnRestoreDefaults.setToolTipText("Restore Default Report Settings");
		adminBtnRestoreDefaults.setBounds(293, 118, 97, 25);
		adminBtnRestoreDefaults.setText("Restore Defaults");
		
		final Button adminSaveButton = new Button(adminComposite, SWT.NONE);
		adminSaveButton.setToolTipText("Save Report Settings");
		adminSaveButton.setBounds(396, 118, 68, 25);
		adminSaveButton.setText("Save");
		
		Combo adminConfigFilesCombo = new Combo(adminComposite, SWT.NONE);
		adminConfigFilesCombo.setBounds(227, 67, 237, 23);
		
		Label adminLblSavedConfig = new Label(adminComposite, SWT.NONE);
		adminLblSavedConfig.setBounds(227, 46, 163, 15);
		adminLblSavedConfig.setText("Saved Report Configurations");
		
		final Label adminWarning1a = new Label(adminComposite, SWT.NONE);
		adminWarning1a.setVisible(false);
		adminWarning1a.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		adminWarning1a.setBounds(144, 58, 55, 15);
		adminWarning1a.setText("Debit");
		
		final Label adminWarning1b = new Label(adminComposite, SWT.NONE);
		adminWarning1b.setVisible(false);
		adminWarning1b.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		adminWarning1b.setBounds(144, 79, 55, 15);
		adminWarning1b.setText("Credit");
		
		final Label adminWarning1c = new Label(adminComposite, SWT.NONE);
		adminWarning1c.setVisible(false);
		adminWarning1c.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		adminWarning1c.setBounds(144, 100, 55, 15);
		adminWarning1c.setText("Delimiter");
		
		final Label adminWarning1d = new Label(adminComposite, SWT.NONE);
		adminWarning1d.setVisible(false);
		adminWarning1d.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		adminWarning1d.setBounds(144, 121, 90, 15);
		adminWarning1d.setText("must be unique");
		
		final List adminDBMasterList = new List(adminComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		adminDBMasterList.setBounds(33, 202, 125, 136);
		
		// TODO implement code to pull column names from linked list and place them into adminDBMasterList
		// the lines within the DEMO brackets are for demo purposes only. REPLACE WITH FINAL IMPLEMENTATION
		////////////////////////////////DEMO/////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////
		final LinkedList<String> adminInputList = new LinkedList<String>(new ArrayList<String>());
		for(int i=0;i<30;i++){
			adminInputList.add("Column" + i);
		}
		iterator = adminInputList.listIterator();
		while(iterator.hasNext()){
			adminDBMasterList.add(iterator.next().toString());
		}
		////////////////////////////////////////////////////////////////////////////////////////
		
		final List adminDBExportList = new List(adminComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		adminDBExportList.setBounds(247, 202, 125, 136);
		
		final Button adminAddAllButton = new Button(adminComposite, SWT.NONE);
		adminAddAllButton.setToolTipText("Add All");
		adminAddAllButton.setBounds(189, 208, 28, 25);
		adminAddAllButton.setText(">>");
		
		final Button adminAddSelButton = new Button(adminComposite, SWT.NONE);
		adminAddSelButton.setToolTipText("Add Selection");
		adminAddSelButton.setBounds(189, 241, 28, 25);
		adminAddSelButton.setText(">");
		adminAddSelButton.setEnabled(false);
		
		final Button adminRemoveSelButton = new Button(adminComposite, SWT.NONE);
		adminRemoveSelButton.setToolTipText("Remove Selection");
		adminRemoveSelButton.setBounds(189, 273, 28, 25);
		adminRemoveSelButton.setText("<");
		adminRemoveSelButton.setEnabled(false);
		
		final Button adminRemoveAllButton = new Button(adminComposite, SWT.NONE);
		adminRemoveAllButton.setToolTipText("Remove All");
		adminRemoveAllButton.setBounds(189, 306, 28, 25);
		adminRemoveAllButton.setText("<<");
		adminRemoveAllButton.setEnabled(false);
		
		final Button adminMoveUpButton = new Button(adminComposite, SWT.NONE);
		adminMoveUpButton.setToolTipText("Move Selection Up");
		adminMoveUpButton.setEnabled(false);
		adminMoveUpButton.setBounds(395, 241, 28, 25);
		adminMoveUpButton.setText("\u02C4");
		
		final Button adminMoveDownButton = new Button(adminComposite, SWT.NONE);
		adminMoveDownButton.setToolTipText("Move Selection Down");
		adminMoveDownButton.setEnabled(false);
		adminMoveDownButton.setBounds(395, 273, 28, 25);
		adminMoveDownButton.setText("\u02C5");
		
		Label adminLblMasterList = new Label(adminComposite, SWT.CENTER);
		adminLblMasterList.setBounds(60, 181, 63, 15);
		adminLblMasterList.setText("Master List");
		
		Label adminLblExportList = new Label(adminComposite, SWT.CENTER);
		adminLblExportList.setBounds(275, 181, 63, 15);
		adminLblExportList.setText("Export List");
		
		Label adminLblChangeOrder = new Label(adminComposite, SWT.NONE);
		adminLblChangeOrder.setBounds(395, 218, 36, 15);
		adminLblChangeOrder.setText("Order");
		
		final Button adminBtnCancel = new Button(adminComposite, SWT.NONE);
		adminBtnCancel.setBounds(399, 357, 75, 25);
		adminBtnCancel.setText("Cancel");
		
		final Button adminBtnExport = new Button(adminComposite, SWT.NONE);
		adminBtnExport.setToolTipText("Export CSV File");
		adminBtnExport.setBounds(318, 357, 75, 25);
		adminBtnExport.setText("Export");
		
		////////////////////////////////Listeners / Event Handlers/////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////
		adminAddAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBExportList.removeAll();
				adminDBMasterList.removeAll();
				iterator = adminInputList.listIterator();
				while(iterator.hasNext()){
					adminDBExportList.add(iterator.next().toString());
				}
				adminRemoveAllButton.setEnabled(true);
				adminAddAllButton.setEnabled(false);
				adminAddSelButton.setEnabled(false);
			}
		});
		
		adminAddSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] array = adminDBMasterList.getSelection();
				if(array.length != 0){
					for(int i=0;i<array.length;i++){
						adminDBExportList.add(array[i]);
						adminDBMasterList.remove(array[i]);
					}
				}
				if(adminDBExportList.getItemCount() > 0){
					adminRemoveAllButton.setEnabled(true);
				}
				adminAddSelButton.setEnabled(false);
			}
		});
		
		adminRemoveSelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				String[] array = adminDBExportList.getSelection();
				if(array.length != 0){
					for(int i=0;i<array.length;i++){
						adminDBExportList.remove(array[i]);
						int linkIndex = adminInputList.indexOf(array[i]);
						if(adminDBMasterList.getItemCount() == 0){
							adminDBMasterList.add(array[i]);
						}
						else if(linkIndex+1 > adminInputList.indexOf(adminDBMasterList.getItem(adminDBMasterList.getItemCount()-1))){
							adminDBMasterList.add(array[i]);
						}
						else{
							
							for(int k=0;k<adminDBMasterList.getItemCount();k++){
								int masterIndex = adminInputList.indexOf(adminDBMasterList.getItem(k));
								if(linkIndex < masterIndex){
									adminDBMasterList.add(array[i], k);
									break;
								}
							}
						}
					}
				}
				adminAddAllButton.setEnabled(true);
				adminRemoveSelButton.setEnabled(false);
				adminMoveUpButton.setEnabled(false);
				adminMoveDownButton.setEnabled(false);
			}
		});
		
		adminRemoveAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBExportList.removeAll();
				adminDBMasterList.removeAll();
				iterator = adminInputList.listIterator();
				while(iterator.hasNext()){
					adminDBMasterList.add(iterator.next().toString());
				}
				adminRemoveAllButton.setEnabled(false);
				adminAddAllButton.setEnabled(true);
				adminMoveUpButton.setEnabled(false);
				adminMoveDownButton.setEnabled(false);
			}
		});
		
		adminMoveUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(adminDBExportList.getSelectionIndex() > 0){
					int index = adminDBExportList.getSelectionIndex();
					String item = adminDBExportList.getSelection()[0];
					adminDBExportList.remove(item);
					adminDBExportList.add(item, index-1);
					adminDBExportList.select(index-1);
					adminMoveDownButton.setEnabled(true);
				}
				if(adminDBExportList.getSelectionIndex() == 0){
					adminMoveUpButton.setEnabled(false);
				}
			}
		});
		
		adminMoveDownButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(adminDBExportList.getSelectionIndex() < adminDBExportList.getItemCount()-2){
					int index = adminDBExportList.getSelectionIndex();
					String item = adminDBExportList.getSelection()[0];
					adminDBExportList.remove(item);
					adminDBExportList.add(item, index+1);
					adminDBExportList.select(index+1);
					adminMoveUpButton.setEnabled(true);
				}
				else if(adminDBExportList.getSelectionIndex() == adminDBExportList.getItemCount()-2){
					String item = adminDBExportList.getSelection()[0];
					adminDBExportList.remove(item);
					adminDBExportList.add(item);
					adminDBExportList.select(adminDBExportList.getItemCount()-1);
				}
				if(adminDBExportList.getSelectionIndex() == adminDBExportList.getItemCount()-1){
					adminMoveDownButton.setEnabled(false);	
				}
			}
		});
		
		adminDBMasterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(adminDBMasterList.getSelectionCount() > 0){
					adminAddSelButton.setEnabled(true);
				}
			}
		});
				
		adminDBExportList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				switch (adminDBExportList.getSelection().length) {
				case 1:
					if(adminDBExportList.getSelectionIndex() > 0){
						adminMoveUpButton.setEnabled(true);
					}
					else{
						adminMoveUpButton.setEnabled(false);
					}
					if(adminDBExportList.getSelectionIndex() < adminDBExportList.getItemCount()-1){
						adminMoveDownButton.setEnabled(true);
					}
					else{
						adminMoveDownButton.setEnabled(false);
					}
					
					adminRemoveSelButton.setEnabled(true);
					break;
				default:
					adminRemoveSelButton.setEnabled(true);
					adminMoveUpButton.setEnabled(false);
					adminMoveDownButton.setEnabled(false);
				}
				
			}
		});
		
		adminBtnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				configurationShell.close();
			}
		});
		
		adminBtnExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell exportShell = new Shell();
				// File Save dialog
			    FileDialog fileDialog = new FileDialog(exportShell, SWT.SAVE);
				// tie the location of this shell to the application window
			    exportShell.setLocation(configurationShell.getLocation());
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
			    adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
				
			}
		});
		
		adminBtnRestoreDefaults.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//restore default characters
				adminWarning1a.setVisible(false);
				adminWarning1b.setVisible(false);
				adminWarning1c.setVisible(false);
				adminWarning1d.setVisible(false);
				adminSaveButton.setEnabled(true);
				adminBtnExport.setEnabled(true);
				adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				adminDebitText.setText("-");
				adminCreditText.setText("+");
				adminDelimiterText.setText(",");
				adminColHdrCheckButton.setSelection(true);
				adminDBExportList.removeAll();
				adminDBMasterList.removeAll();
				iterator = adminInputList.listIterator();
				while(iterator.hasNext()){
					adminDBMasterList.add(iterator.next().toString());
				}
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
				adminRemoveAllButton.setEnabled(false);
				adminAddAllButton.setEnabled(true);
			}
		});
		
		adminSaveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Shell shlSaveDialog = new Shell();
				SaveDialog saveDialog = new SaveDialog(shlSaveDialog, SWT.APPLICATION_MODAL, configurationShell.getLocation());
				saveDialog.open();
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		configurationShell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminComposite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminColHdrCheckButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminDelimiterText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminDelimiterText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(adminDelimiterText.getText().compareTo(adminCreditText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else if(adminDelimiterText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else if(adminCreditText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else{
					adminWarning1a.setVisible(false);
					adminWarning1b.setVisible(false);
					adminWarning1c.setVisible(false);
					adminWarning1d.setVisible(false);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminSaveButton.setEnabled(true);
					adminBtnExport.setEnabled(true);
				}
			}
		});
		
		adminLblDelimiter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminCreditText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminCreditText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(adminDelimiterText.getText().compareTo(adminCreditText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else if(adminDelimiterText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else if(adminCreditText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else{
					adminWarning1a.setVisible(false);
					adminWarning1b.setVisible(false);
					adminWarning1c.setVisible(false);
					adminWarning1d.setVisible(false);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminSaveButton.setEnabled(true);
					adminBtnExport.setEnabled(true);
				}
			}
		});
		
		adminLblCreditOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminDebitText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminDebitText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(adminDelimiterText.getText().compareTo(adminCreditText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else if(adminDelimiterText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else if(adminCreditText.getText().compareTo(adminDebitText.getText())==0){
					adminWarning1a.setVisible(true);
					adminWarning1b.setVisible(true);
					adminWarning1c.setVisible(true);
					adminWarning1d.setVisible(true);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
					adminSaveButton.setEnabled(false);
					adminBtnExport.setEnabled(false);
				}
				else{
					adminWarning1a.setVisible(false);
					adminWarning1b.setVisible(false);
					adminWarning1c.setVisible(false);
					adminWarning1d.setVisible(false);
					adminDelimiterText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminCreditText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminDebitText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					adminSaveButton.setEnabled(true);
					adminBtnExport.setEnabled(true);
				}
			}
		});
			
		adminLblDebitOperator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminConfigFilesCombo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminLblSavedConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminLblMasterList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminLblExportList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
		
		adminLblChangeOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				adminDBMasterList.deselectAll();
				adminDBExportList.deselectAll();
				adminAddSelButton.setEnabled(false);
				adminRemoveSelButton.setEnabled(false);
			}
		});
	}
}
