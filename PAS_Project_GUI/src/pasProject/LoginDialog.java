package pasProject;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class LoginDialog extends Dialog {

	protected Object result;
	protected Shell loginShell;
	private static boolean loginCancel = false;
	private static boolean[] loginState = new boolean[2];
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public LoginDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
		loginState[0] = false;
		loginState[1] = false;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	//public Object open() {
	public boolean[] open() {
		createContents();
		loginShell.open();
		loginShell.layout();
		Display display = getParent().getDisplay();
		while (!loginShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return loginState;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		loginShell = new Shell(getParent());
		loginShell.setSize(220, 168);
		loginShell.setText("Login - PAS Export File Config");
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
		usernameText.setFocus();
		
		final Text passwordText = new Text(composite, SWT.BORDER);
		passwordText.setBounds(88, 70, 101, 21);
		
		final Button btnOk = new Button(composite, SWT.NONE);
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
		
		loginShell.setDefaultButton(btnOk);
		
		////////////////////////////////////////////////listeners - event handlers//////////////////////////////////////////////////
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				loginState[1] = false;
				switch (usernameText.getText()) {
					case "admin":
						switch (passwordText.getText()){
							case "admin":
								loginState[0] = true;
								loginState[1] = true;
								break;
							default:
								lblInvalidUsername.setVisible(true);
						}
						break;
					case "client":
						switch (passwordText.getText()){
							case "client":
								loginState[0] = false;
								loginState[1] = true;
								break;
							default:
								lblInvalidUsername.setVisible(true);
						} 
						break;
					default:
						lblInvalidUsername.setVisible(true);
						break;
				}
				if(loginState[1]){
					loginShell.close();
				}
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				loginState[1] = false;
				loginShell.close();
			}
		});
		
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loginState[1] = false;
				switch (usernameText.getText()) {
					case "admin":
						switch (passwordText.getText()){
							case "admin":
								loginState[0] = true;
								loginState[1] = true;
								break;
							default:
								lblInvalidUsername.setVisible(true);
						}
						break;
					case "client":
						switch (passwordText.getText()){
							case "client":
								loginState[0] = false;
								loginState[1] = true;
								break;
							default:
								lblInvalidUsername.setVisible(true);
						} 
						break;
					default:
						lblInvalidUsername.setVisible(true);
						break;
				}
				if(loginState[1]){
					loginShell.close();
				}
				
			}
		});
	}
}
