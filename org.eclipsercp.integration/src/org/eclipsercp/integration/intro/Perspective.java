package org.eclipsercp.integration.intro;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipsercp.integration.view.ProjectTreeView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addStandaloneView(ProjectTreeView.ID, true, IPageLayout.LEFT,
				.25f, layout.getEditorArea());
		layout.getViewLayout(ProjectTreeView.ID).setCloseable(false);
		layout.setEditorAreaVisible(true);
		layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, 0.7f, layout.getEditorArea());
	}
}
