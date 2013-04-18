package org.eclipsercp.test.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipsercp.test.editor.ChatEditor;
import org.eclipsercp.test.editor.ChatEditorInput;
import org.eclipsercp.test.model.ContactsEntry;
import org.eclipsercp.test.Application;
import org.eclipsercp.test.IImageKeys;
import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;

public class ChatAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "org.eclipsercp.test.chat";
	private IStructuredSelection selection;
	
	public ChatAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText("&Chat");
		setToolTipText("Chat with the selected contact.");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, IImageKeys.CHAT));
		window.getSelectionService().addSelectionListener(this);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		window.getSelectionService().removeSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		// TODO Auto-generated method stub
		if (incoming instanceof IStructuredSelection) {
			selection = (IStructuredSelection) incoming;
			setEnabled(selection.size() == 1
					&& selection.getFirstElement() instanceof ContactsEntry);
		} else {
			// Other selections, for example containing text or of other kinds.
			setEnabled(false);
		}
	}
	
	public void run() {
		Object item = selection.getFirstElement();
		ContactsEntry entry = (ContactsEntry) item;
		IWorkbenchPage page = window.getActivePage();
		ChatEditorInput input = new ChatEditorInput(entry.getName());
		try {
//			page.openEditor(input, ChatEditor.ID);
			page.openEditor(input, BPMN2Editor.EDITOR_ID );
		} catch (PartInitException e) {
			// handle error
		}
	}

}
