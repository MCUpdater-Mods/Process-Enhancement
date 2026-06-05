package com.mcupdater.procenhance.grid;

public interface INodeHolder {
	Node getNode(); // Perform node lookup
	void validateOnNextTick(); // Schedule validation tick
	void onRemove(); // Remove and clean up node
}
