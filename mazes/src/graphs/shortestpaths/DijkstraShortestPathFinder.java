package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        HashMap<V, E> edgeTo = new HashMap<>();
        HashMap<V, Double> distTo = new HashMap<>();
        if (Objects.equals(start, end)) {
            return edgeTo;
        }
        ExtrinsicMinPQ<V> verticesToVisit = createMinPQ();
        distTo.put(start, 0.0);
        verticesToVisit.add(start, 0);
        while (!verticesToVisit.isEmpty()) {
            V fromV = verticesToVisit.removeMin();
            if (Objects.equals(fromV, end)) {
                // Shortest paths tree construction completed
                return edgeTo;
            }
            for (E edge : graph.outgoingEdgesFrom(fromV)) {
                V toV = edge.to();
                double oldDist = distTo.getOrDefault(toV, Double.POSITIVE_INFINITY);
                double newDist = distTo.get(fromV) + edge.weight();
                if (newDist < oldDist) {
                    distTo.put(toV, newDist);
                    edgeTo.put(toV, edge);
                    if (verticesToVisit.contains(toV)) {
                        verticesToVisit.changePriority(toV, newDist);
                    } else {
                        verticesToVisit.add(toV, newDist);
                    }
                }
            }
        }
        return edgeTo;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }
        E edge = spt.get(end);
        if (edge == null) {
            return new ShortestPath.Failure<>();
        }
        List<E> shortestPathEdges = new ArrayList<>();
        shortestPathEdges.add(edge);
        while (!Objects.equals(edge.from(), start)) {
            edge = spt.get(edge.from());
            if (edge == null) {
                return new ShortestPath.Failure<>();
            }
            shortestPathEdges.add(edge);
        }
        Collections.reverse(shortestPathEdges);
        return new ShortestPath.Success<>(shortestPathEdges);
    }

}
