package com.mcupdater.procenhance.grid;

import java.util.Set;
import java.util.UUID;

public record Node(UUID id, UUID parentGrid, Set<Node> neighbors){
}
