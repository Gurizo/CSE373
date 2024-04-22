package mazes.logic.carvers;

import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
//import java.util.stream.Collectors;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) { //return MST will make the maze solvable
        // Hint: you'll probably need to include something like the following:
        // this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges));
        // walls.stream().collect(Collectors.toList())
        List<EdgeWithData<Room, Wall>> edgeWithDataList = new ArrayList<>(walls.size());
        for (Wall wall : walls) { //where we find EdgeWithData
            Room room1 = wall.getRoom1();
            Room room2 = wall.getRoom2();
            double randomWeight = rand.nextDouble();
            EdgeWithData<Room, Wall> edgeWithData = new EdgeWithData<>(room1, room2, randomWeight, wall);
            edgeWithDataList.add(edgeWithData);
        }
        MazeGraph graph = new MazeGraph(edgeWithDataList);
        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> mst =
            this.minimumSpanningTreeFinder.findMinimumSpanningTree(graph);
        Collection<EdgeWithData<Room, Wall>> minEdge = mst.edges();
        Set<Wall> wallsToRemove = new HashSet<>();
        for (EdgeWithData<Room, Wall> edgeWithData : minEdge) {
            wallsToRemove.add(edgeWithData.data());
        }
        return wallsToRemove;
    }
}
