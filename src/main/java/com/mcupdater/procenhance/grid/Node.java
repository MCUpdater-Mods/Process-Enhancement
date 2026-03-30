package com.mcupdater.procenhance.grid;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Node {

	private final UUID nodeId;
	private UUID gridId;
	private Set<Node> neighborSet;

	public Node(){
		this.nodeId = UUID.randomUUID();
		this.neighborSet = new HashSet<>();
	}


}
