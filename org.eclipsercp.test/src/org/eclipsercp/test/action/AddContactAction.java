package org.eclipsercp.test.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipsercp.test.IImageKeys;
import org.eclipsercp.test.dialog.AddContactDialog;
import org.eclipsercp.test.model.ContactsEntry;
import org.eclipsercp.test.model.ContactsGroup;

public class AddContactAction extends Action implements ISelectionListener, ActionFactory.IWorkbenchAction{
	private final IWorkbenchWindow window;
	public  final static String ID = "org.eclipsercp.test.action.addContact";
	private IStructuredSelection selection;
	
	public AddContactAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText("&Add contact...");
		setToolTipText("Add a contact to your contacts list.");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipsercp.test", IImageKeys.ADD_CONTACT));
		window.getSelectionService().addSelectionListener(this);
	}
	
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		//只有当窗口中一个项目被选中且选中contactsGroup时才能启用添加联系人动作
		//selection containing element
		if (incoming instanceof IStructuredSelection) {
			selection = (IStructuredSelection) incoming;
			setEnabled(selection.size() == 1 && selection.getFirstElement() instanceof ContactsGroup);
		}	else {
				//other selections (e.g., containing text or of other konds)
				setEnabled(false);
		}				
	}
	
	public void run() {
		AddContactDialog d = new AddContactDialog(window.getShell());
		int code = d.open();
		if (code == Window.OK) {
			Object item = selection.getFirstElement();
			ContactsGroup group = (ContactsGroup) item;
			ContactsEntry entry = new ContactsEntry(group, d.getName(), d.getNickname(), d.getServer());
			group.addEntry(entry);				
		}		
	}
	
	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
	}	

}
