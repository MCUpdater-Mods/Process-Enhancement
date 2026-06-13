package com.mcupdater.procenhance.grid;

public class GridSizeComparator implements java.util.Comparator<Grid> {
	@Override
	public int compare(Grid o1, Grid o2) {
		return Long.compare(o1.getSize(), o2.getSize());
	}
}
