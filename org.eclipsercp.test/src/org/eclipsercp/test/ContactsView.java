package org.eclipsercp.test;

import javax.print.attribute.standard.MediaSize.Other;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.handlers.WizardHandler.New;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;
import org.eclipsercp.test.model.Contact;
import org.eclipsercp.test.model.ContactsEntry;
import org.eclipsercp.test.model.ContactsGroup;
import org.eclipsercp.test.model.IContactsListener;
import org.eclipsercp.test.model.Session;

public class ContactsView extends ViewPart {
    public static final String ID = "org.eclipsercp.test.views.contacts";
    private TreeViewer treeViewer;
    private Session session;
    private IAdapterFactory adapterFactory = new TestAdapterFactory();
            
	public ContactsView() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		initializeSession();//临时调整建造一个假模式
       //,SWT.NULTI代表可以选择多行,SWT.BORDER边框，SWT.V_SCROLL ,SWT.H_SCROLL滚动条
		treeViewer = new TreeViewer(parent, SWT.BORDER|SWT.MULTI|SWT.V_SCROLL);
		
		Platform.getAdapterManager().registerAdapters(adapterFactory, Contact.class);
		
		getSite().setSelectionProvider(treeViewer);//将treeViewer视图注册为选择提供程序
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
		treeViewer.setInput(session.getRoot());
		session.getRoot().addContactsListener(new IContactsListener() {			
			@Override
			public void contactsChanged(ContactsGroup contacts, ContactsEntry entry) {
				// TODO Auto-generated method stub
				treeViewer.refresh();
			}
		});
	}
	
	public void  dispose() {
		Platform.getAdapterManager().unregisterAdapters(adapterFactory);
		super.dispose();		
	}
	
   private void initializeSession() {
	   // TODO Auto-generated method stub
      session = new Session();
      ContactsGroup root  = session.getRoot();
      ContactsGroup friendsGroup = new ContactsGroup(root, "Friends");
      root.addEntry(friendsGroup);
      friendsGroup.addEntry(new ContactsEntry(friendsGroup, "Alize", "alize", "localhost"));
      friendsGroup.addEntry(new ContactsEntry(friendsGroup, "Fgj", "Feng", "192.168.3.12"));
      ContactsGroup otherGroup = new ContactsGroup(root, "Other");
      root.addEntry(otherGroup);
      otherGroup.addEntry(new ContactsEntry(otherGroup, "Nadine", "nad","localhost"));
    }
   
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		treeViewer.getControl().setFocus();
	}

}
