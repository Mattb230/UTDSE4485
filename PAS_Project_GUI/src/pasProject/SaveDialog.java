package pasProject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SaveDialog extends Dialog {

	protected Object result;
	protected Shell shlSaveFile;
	private Text fileNameField;
	private Point shellLocation;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SaveDialog(Shell parent, int style, Point location) {
		super(parent, style);
		setText("SWT Dialog");
		shellLocation = location;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlSaveFile.open();
		shlSaveFile.layout();
		Display display = getParent().getDisplay();
		while (!shlSaveFile.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		//shlSaveFile = new Shell(getParent(), getStyle());
		shlSaveFile = new Shell(getParent());
		shlSaveFile.setSize(242, 300);
		shlSaveFile.setText("Save File");
		shlSaveFile.setLocation(shellLocation.x+12, shellLocation.y+32);
		
		Composite composite = new Composite(shlSaveFile, SWT.NONE);
		composite.setBounds(0, 0, 236, 274);
		
		Button saveButton = new Button(composite, SWT.NONE);
		saveButton.setBounds(10, 239, 75, 25);
		saveButton.setText("Save");
		
		Button cancelButton = new Button(composite, SWT.NONE);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlSaveFile.close();
			}
		});
		cancelButton.setBounds(151, 239, 75, 25);
		cancelButton.setText("Cancel");
		
		fileNameField = new Text(composite, SWT.BORDER);
		fileNameField.setBounds(10, 208, 216, 21);
		
		List fileNameList = new List(composite, SWT.BORDER);
		fileNameList.setBounds(10, 10, 216, 184);

	}
}
