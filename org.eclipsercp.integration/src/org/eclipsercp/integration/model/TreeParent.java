package org.eclipsercp.integration.model;

import java.util.ArrayList;

public class TreeParent extends TreeObject {

	/**
	 * ÏîÄ¿TreeParent
	 * 
	 * @author Administrator
	 * 
	 */
		private ArrayList children;

		public TreeParent(String name) {
			super(name);
			children = new ArrayList();
		}

		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}

		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}

		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}
}
