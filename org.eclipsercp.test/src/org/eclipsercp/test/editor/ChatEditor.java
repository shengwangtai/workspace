package org.eclipsercp.test.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class ChatEditor extends EditorPart {
	
	public static String ID = "org.eclipsercp.test.editors.chat";
	private Text transcript;
	private Text entry;

	//always need a no-arg constructor.
	public ChatEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// TODO Auto-generated method stub
		setSite(site);
		setInput(input);
		setPartName(getUser());		
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
        Composite top = new Composite(parent, SWT.NONE);
        GridLayout layout =  new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        top.setLayout(layout);
        
		transcript = new Text(top, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		transcript.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, true));
		transcript.setEditable(false);
		transcript.setBackground(transcript.getDisplay().getSystemColor(
				SWT.COLOR_INFO_BACKGROUND));
		transcript.setForeground(transcript.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));

		entry = new Text(top, SWT.BORDER | SWT.WRAP);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
				false);
		gridData.heightHint = entry.getLineHeight() * 2;
		entry.setLayoutData(gridData);
		entry.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.CR) {
					sendMessage();
					event.doit = false;
				}
			}
		});
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		if (entry != null && !entry.isDisposed()) {
			entry.setFocus();
		}
	}
	
	private String getUser() {
		return ((ChatEditorInput) getEditorInput()).getName();
	}
	
	private String renderMessage(String from, String body) {
		if (from == null)
			return body;
		int j = from.indexOf('@');
		if (j > 0)
			from = from.substring(0, j);
		return "<" + from + ">  " + body;
	}
	
	private void scrollToEnd() {
		int n = transcript.getCharCount();
		transcript.setSelection(n, n);
		transcript.showSelection();
	}

	/**
	 * Sends the text in the entry field to the contact(s). The sent text will
	 * appear in the transcript area.
	 */
	private void sendMessage() {
		String body = entry.getText();
		if (body.length() == 0)
			return;
		transcript.append(renderMessage(getUser(), body));
		transcript.append("\n");
		scrollToEnd();
		entry.setText("");
	}

	public void dispose() {
	}

}
