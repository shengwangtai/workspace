package org.eclipsercp.test;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.operations.UndoRedoActionGroup;
import org.eclipsercp.test.action.AddContactAction;
import org.eclipsercp.test.action.ChatAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	private IWorkbenchAction newAction;
	private IWorkbenchAction openAction;
	private IWorkbenchAction undoAction;
	private IWorkbenchAction redoAction;
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction addContactAction;
	private IWorkbenchAction chatAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	//添加自定义动作
    	addContactAction = new AddContactAction(window);
    	register(addContactAction);
    	chatAction = new ChatAction(window);
    	register(chatAction);
    	newAction = ActionFactory.NEW.create(window);
    	register(newAction);
    	openAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
    	register(openAction);
    	undoAction = ActionFactory.UNDO.create(window);
    	register(undoAction);
    	redoAction = ActionFactory.REDO.create(window);
    	register(redoAction);
    	exitAction = ActionFactory.QUIT.create(window);
    	register(exitAction);
    	aboutAction = ActionFactory.ABOUT.create(window);
    	register(aboutAction);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	MenuManager fileMenu =  new MenuManager("&file", "file");
    	fileMenu.add(addContactAction);
    	fileMenu.add(chatAction);
    	fileMenu.add(newAction);
    	fileMenu.add(openAction);
    	//可添加其它插件填写的菜单
    	fileMenu.add(new GroupMarker("other-actions"));
    	fileMenu.appendToGroup("other-actions", exitAction);
    	
    	MenuManager editMenu = new MenuManager("&edit", "edit");
    	editMenu.add(undoAction);
    	editMenu.add(redoAction);
    	
    	MenuManager helpMenu = new MenuManager("&help", "help");
    	helpMenu.add(aboutAction);
    	menuBar.add(fileMenu);
    	menuBar.add(editMenu);
//    	fileMenu.add(helpMenu);//级联菜单
    	menuBar.add(helpMenu);  	    	
    }
    
    public void populateCoolBar(IActionBarConfigurer configurer) {
    	//将动作放在同一工具栏组中，同时又相互独立
    	ICoolBarManager mgr = configurer.getCoolBarManager();
    	IToolBarManager toolbar = new ToolBarManager(mgr.getStyle());
    	mgr.add(toolbar);
    	toolbar.add(addContactAction);
    	toolbar.add(new Separator());
    	toolbar.add(addContactAction);		
	}
    
    protected void fillCoolBar(ICoolBarManager coolBar) {
    	//IToolBarManager是一组工具栏，ICoolBarManager是窗口的工具栏，可有多组工具栏构成
    	IToolBarManager  toolbar = new ToolBarManager(coolBar.getStyle());
//    	IToolBarManager  toolbar2 = new ToolBarManager(coolBar.getStyle());    	
    	coolBar.add(toolbar);
//    	coolBar.add(toolbar2);    	
    	toolbar.add(addContactAction);	
    	toolbar.add(chatAction);
//    	toolbar2.add(addContactAction);	
	}    
    
    protected void fillTrayItem(IMenuManager trayItem) {
    	trayItem.add(exitAction);
    	trayItem.add(aboutAction);		
	}
    
}
