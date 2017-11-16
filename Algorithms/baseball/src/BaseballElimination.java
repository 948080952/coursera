/**
 * Created by daipei on 2017/11/13.
 */

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

    private String[]    teams;
    private int[]       win;
    private int[]       loss;
    private int[]       left;
    private int[][]     schedule;
    private final int   teamNumber;
    private ST<String, Integer> teamMap;
    private boolean[]   isEliminated;
    private SET<String>[] eliminations;

    public BaseballElimination(String filename) {
        if (filename == null || filename.equals("")) {
            throw new IllegalArgumentException();
        }
        In in = new In(filename);
        int n = in.readInt();
        teamNumber = n;
        teamMap = new ST<>();
        teams = new String[n];
        win = new int[n];
        loss = new int[n];
        left = new int[n];
        schedule = new int[n][n];
        isEliminated = new boolean[n];
        eliminations = (SET<String>[]) new SET[n];

        for (int i = 0; i < n; i++) {
            String teamName = in.readString();
            teamMap.put(teamName, i);
            teams[i] = teamName;
            win[i] = in.readInt();
            loss[i] = in.readInt();
            left[i] = in.readInt();
            for (int j = 0; j < n; j++) {
                schedule[i][j] = in.readInt();
            }
        }

        trivialElimination();
        nontrivialElimination();
    }

    private void trivialElimination() {
        for (int i = 0; i < teamNumber; i++) {
            for (int j = 0; j < teamNumber; j++) {
                if (i == j) {
                    assert schedule[i][j] == 0;
                    continue;
                }
                if (win[i] + left[i] < win[j]) {
                    isEliminated[i] = true;
                    assert eliminations[i] == null;
                    eliminations[i] = new SET<>();
                    eliminations[i].add(teams[j]);
                }
            }
        }
    }

    private void nontrivialElimination() {

        int scheduleVerticeNumber = (teamNumber - 1) * (teamNumber - 2) / 2;
        int verticeNumber = 1 + scheduleVerticeNumber + (teamNumber - 1) + 1;

        for (int team = 0; team < teamNumber; team++) {
            if (isEliminated[team]) {
                continue;
            }

            FlowNetwork network = new FlowNetwork(verticeNumber + 1);

            int k = teamNumber + 1;

            // add schedule vertices to network
            for (int i = 0; i < teamNumber; i++) {
                if (i == team) {
                    continue;
                }
                for (int j = i + 1; j < teamNumber; j++) {
                    if (j == team || j == i) {
                        continue;
                    }
                    FlowEdge e = new FlowEdge(0, k, schedule[i][j]);
                    network.addEdge(e);

                    e = new FlowEdge(k, i + 1, Double.POSITIVE_INFINITY);
                    network.addEdge(e);

                    e = new FlowEdge(k, j + 1, Double.POSITIVE_INFINITY);
                    network.addEdge(e);
                    k++;
                }
            }
            assert k == verticeNumber;

            for (int i = 0; i < teamNumber; i++) {
                if (i == team) {
                    continue;
                }
                FlowEdge e = new FlowEdge(i + 1, verticeNumber, win[team] + left[team] - win[i]);
                network.addEdge(e);
            }

            FordFulkerson maxflow = new FordFulkerson(network, 0, verticeNumber);

            for (FlowEdge e : network.adj(0)) {
                if (e.flow() != e.capacity()) {
                    isEliminated[team] = true;
                    eliminations[team] = new SET<>();
                    for (int i = 0; i < teamNumber; i++) {
                        if (i == team) {
                            continue;
                        }
                        if (maxflow.inCut(i + 1)) {
                            eliminations[team].add(teams[i]);
                        }
                    }
                    break;
                }
            }
        }
    }

    public int numberOfTeams() {
        return teamMap.size();
    }

    public Iterable<String> teams() {
        return teamMap.keys();
    }

    public int wins(String team) {
        int index = teamMap.get(team);
        return win[index];
    }

    public int losses(String team) {
        int index = teamMap.get(team);
        return loss[index];
    }

    public int remaining(String team) {
        int index = teamMap.get(team);
        return left[index];
    }

    public int against(String team1, String team2) {
        int index1 = teamMap.get(team1);
        int index2 = teamMap.get(team2);
        return schedule[index1][index2];
    }

    public boolean isEliminated(String team) {
        return isEliminated[teamMap.get(team)];
    }

    public Iterable<String> certificateOfElimination(String team) {
        return eliminations[teamMap.get(team)];
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
