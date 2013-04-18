package org.eclipsercp.integration.intro;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipsercp.integration.action.NewProjectAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction introAction;
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private NewProjectAction newProjectAction;
    
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		introAction = ActionFactory.INTRO.create(window);
		register(introAction);		
    	exitAction = ActionFactory.QUIT.create(window);
    	register(exitAction);
    	aboutAction = ActionFactory.ABOUT.create(window);
    	register(aboutAction);
    	newProjectAction = new NewProjectAction();
    	register(newProjectAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", "File");
		MenuManager newFile = new MenuManager("&New", "newFile");
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
		fileMenu.add(newFile);
		newFile.add(newProjectAction);
		menuBar.add(fileMenu);		
		menuBar.add(helpMenu);
		// Help
		helpMenu.add(introAction);
	}
	
    protected void fillTrayItem(IMenuManager trayItem) {
    	trayItem.add(exitAction);
    	trayItem.add(aboutAction);		
	}

}
