/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cd2;

//N20DCCN065 NGUYỄN TRẦN TRỌNG TÍN
//N20DCCN066 NGUYỄN TRỌNG TÍN
//N20DCCN067 ĐẶNG KHẮC TOẢN
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Edge {

    char start;
    char end;
    int weight;

    public Edge(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}

class Node {

    char vertex;
    int cost;

    public Node(char vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }
}

class Vertex {

    private String name;
    private List<String> adj;
    private boolean visited;

    public Vertex(String n) {
        visited = false;
        name = n;
        adj = new ArrayList<String>();
    }

    public void addAdjacencyVertex(String n) {
        adj.add(n);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        visited = true;
    }

    public void resetVisited() {
        visited = false;
    }

    public boolean isExist(String v) {
        if (v.equals(name)) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getAdjacencyVertex() {
        return adj.toString();
    }

    public List<String> getAdj() {
        return adj;
    }
}

public class N20DCCN065_N20DCCN066_N20DCCN067 {
    
    static String inputpath="C:\\Users\\toand\\Desktop\\backup\\HKI-2022-2023\\AI\\Group14\\GitHub\\Group14-TTNT\\cd2\\src\\cd2\\input.txt";
    static String outputPath="C:\\Users\\toand\\Desktop\\backup\\HKI-2022-2023\\AI\\Group14\\GitHub\\Group14-TTNT\\cd2\\src\\cd2\\output.txt";
    
    
    // BFS
    static ArrayList<Edge> edges = new ArrayList<>();
    static ArrayList<Node> nodes = new ArrayList<>();
    static boolean[] visited;
    static String path = "";
 static char[] pathBFS = new char[8];
    public static void bfs(char start, char goal) {
        Queue<Character> queue = new LinkedList<>(); //Tạo một hàng đợi để lưu trữ các đỉnh cần duyệt.

        visited[(int) start - 65] = true; //Đánh dấu đỉnh ban đầu (v) đã được duyệt và thêm nó vào hàng đợi.
        queue.offer(start);
        while (!queue.isEmpty()) {
            char u = queue.poll();  //Lấy một đỉnh từ đầu hàng đợi .
            System.out.print(u + " ");
            path += u + "-->";

            if (u == goal) {
//                  printPath(start, goal);
                return;
            }

            for (Edge edge : edges) {
                if (edge.start == u && !visited[(int) edge.end - 65]) //Duyệt qua danh sách các cạnh (edges) và tìm các đỉnh kề với u chưa được duyệt.
                {
                    visited[(int) edge.end - 65] = true;
                    queue.offer(edge.end);
                     //Nếu tìm thấy một đỉnh kề chưa được duyệt, đánh dấu nó đã được duyệt và thêm nó vào cuối hàng đợi
                     pathBFS[(int) edge.end - 65] = u;//luu lai duong di

                   
                }
            }
            // lặp lại cho đến khi hàng đợi trống
        }
    }
public static void printPath(char start, char goal) {
    System.out.println("\n==>Duong di tu " + start + " toi " + goal + ": ");
    String pathStr = "";
    char vertex = goal;
    while (vertex != start) {
        pathStr = " --> " + vertex + pathStr; //Thêm đỉnh vào đầu chuỗi đường đi.
        vertex = pathBFS[(int) vertex - 65]; //Lấy đỉnh trước đó của đỉnh hiện tại.
    }
    System.out.print(start + pathStr); //In ra đường đi từ đỉnh start đến đỉnh goal.
}
    
    // ------------------------------------------------------------------------------------------------------------------------------------------------
    // uniformCostSearch
    public static void readFile(List<Edge> edge) throws FileNotFoundException {
        File file = new File(inputpath);
        Scanner sc = new Scanner(file);
        String s;
        String[] part;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            part = s.split(" ");
            edge.add(new Edge(Character.toUpperCase(part[0].charAt(0)), Character.toUpperCase(part[1].charAt(0)),
                    Integer.parseInt(part[2])));
            edge.add(new Edge(Character.toUpperCase(part[1].charAt(0)),Character.toUpperCase(part[0].charAt(0)),
                    Integer.parseInt(part[2])));
        }
        sc.close();
    }

    public static void writeFile(List<Character> path) throws IOException {
        FileWriter fw = new FileWriter(outputPath);
        for (int i = 0; i < path.size(); i++) {
            fw.write(path.get(i));
            if (i != path.size() - 1) {
                fw.write(" --> ");
            }
        }
        fw.close();
    }

    public static List<Character> uniformCostSearch(char start, char end, List<Edge> edges) {
        int n = edges.size();
        int[] dist = new int[n]; //Khởi tạo mảng dist để lưu trữ khoảng cách 
        boolean[] visited = new boolean[n]; // mảng visited để lưu trữ thông tin các đỉnh đã được duyệt hay chưa
        char[] parent = new char[n]; //mảng parent để lưu trữ đỉnh cha của mỗi đỉnh 
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, '0');
        dist[(int) start - 65] = 0;

        //Khởi tạo một hàng đợi ưu tiên (priority queue) để lưu trữ các đỉnh cần duyệt, sắp xếp các đỉnh theo chi phí thấp nhất đến cao nhất.
        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
            public int compare(Node a, Node b) {
                return a.cost - b.cost;
            }
        });

        //Thêm đỉnh start vào hàng đợi ưu tiên với chi phí là 0.
        queue.offer(new Node(start, 0));

        //thực hiện vòng lặp cho tới khi hàng đợi trống
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            char u = node.vertex; //Lấy ra đỉnh u với chi phí thấp nhất từ hàng đợi ưu tiên.
            if (visited[(int) u - 65]) {
                continue;
            }

            visited[(int) u - 65] = true;// Đánh dấu u đã được duyệt

            if (u == end) {
                break;
            }
            for (int i = 0; i < n; i++) // Duyệt qua danh sách các cạnh trong đồ thị, nếu có cạnh kết nối từ u tới đỉnh v
            {
                Edge edge = edges.get(i);
                if (edge.start == u) {
                    char v = edge.end;
                    int w = edge.weight;
                    if (dist[(int) v - 65] > dist[(int) u - 65] + w) {
                        dist[(int) v - 65] = dist[(int) u - 65] + w;
                        //Nếu khoảng cách dist[v] từ start đến v lớn hơn khoảng cách hiện tại dist[u] từ start đến u cộng với chi phí w, thay đổi giá trị dist[v] và parent[v].
                        parent[(int) v - 65] = u;
                        queue.offer(new Node(v, dist[(int) v - 65]));// Thêm đỉnh v vào hàng đợi ưu tiên với chi phí là dist[v].

                    }
                }
            }
        }

        List<Character> path = new ArrayList<>();
        char u = end;
        while (u != '0') {
            path.add(u);
            u = parent[(int) u - 65];
        }
        Collections.reverse(path);
        return path;
    }
    // ---------------------------------------------------------------------------------------------//

    // DFS
    private static int V;
    private static String s = "";
    private static LinkedList<Integer> adj[];

    public static void readFile(LinkedList<Integer> adj[]) throws FileNotFoundException {
        File file = new File(inputpath);
        Scanner sc = new Scanner(file);
        String s;
        String[] part;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            part = s.split(" ");
            char v = part[0].charAt(0);
            char w = part[1].charAt(0);
            adj[(int) v - 65].add((int) w - 65);
            adj[(int) w - 65].add((int) v - 65);
        }
        sc.close();
    }

    public static void writeFile() throws IOException {
        FileWriter fw = new FileWriter(outputPath);
        fw.write(s.substring(0, s.length() - 3));
        fw.close();
    }

    public static void DFS(char start, char goal) {

        boolean visited[] = new boolean[V];

        boolean[] check = new boolean[1];
        DFSUtil(start, goal, visited, check);
        
        
    }

    public static void DFSUtil(char start, char goal, boolean visited[], boolean[] check) {
        visited[(int) start - 65] = true;
        System.out.print(start + " ");
        s += start + "-->";
        if (start == goal) {
            check[0] = true;
        }

        Iterator<Integer> i = adj[(int) start - 65].listIterator();
        //tạo ra một iterator (i) để duyệt qua danh sách kề của đỉnh hiện tại (v) bằng cách lấy danh sách kề tương ứng với v từ danh sách adj.
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n]) //nếu đỉnh n chưa được duyệt, gọi đệ quy hàm DFSUtil với đỉnh n làm đỉnh bắt đầu để tiếp tục duyệt tìm kiếm các đỉnh kề.
            {
                if (check[0]) {
                    return;
                }
                DFSUtil((char) (n + 65), goal, visited, check);

            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------
    // Depth Limited Search && Iterative Deepening Search
    private static List<Vertex> vertexList;

    public static void readFile1(List<Vertex> v) {
        try {
            FileReader fr = new FileReader(inputpath);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] component = line.split(" ");
                String v1 = component[0];
                boolean v1IsExist = false;
                boolean v2IsExist = false;

                String v2 = component[1];
                for (Vertex ver : v) {
                    if (ver.isExist(v1)) {
                        v1IsExist = true;
                        ver.addAdjacencyVertex(v2);
                        continue;
                    }
                    if (ver.isExist(v2)) {
                        v2IsExist = true;
                        ver.addAdjacencyVertex(v1);
                        continue;
                    }
                }
                if (!v1IsExist) {
                    Vertex temp = new Vertex(v1);
                    temp.addAdjacencyVertex(v2);
                    v.add(temp);
                }
                if (!v2IsExist) {
                    Vertex temp = new Vertex(v2);
                    temp.addAdjacencyVertex(v1);
                    v.add(temp);
                }

            }
            br.close();
            fr.close();
        } catch (Exception e) {

        }
    }

    public static void writeFile1(List<String> solution) {
        try {
            FileWriter fw = new FileWriter(outputPath);
            BufferedWriter bw = new BufferedWriter(fw);
            if (solution.isEmpty()) {
                bw.write("Not find path.");
            } else {
                int i = 0;
                for (String a : solution) {
                    if (i == 0) {
                        bw.write(a);
                    } else {
                        bw.write(" -> " + a);
                    }
                    i++;
                }

            }
            bw.close();
            fw.close();
        } catch (Exception e) {

        }
    }

    public static void showSolution(List<String> solution) {
        if (solution.size() == 0) {
            System.out.println("Not find path.");
        } else {
            int stt = 0;
            for (String a : solution) {
                if (stt == 0) {
                    System.out.print(a);
                } else {
                    System.out.print(" -> " + a);
                }
                stt++;
            }
        }
        writeFile1(solution);
    }

   public static List<String> depthLimitedSearch(String nodeStart, String nodeEnd, int limited) {
        Stack<Vertex> stackVertex = new Stack<Vertex>();// lưu đường đi hiện tại
        List<String> solution = new ArrayList<String>();
        boolean cutoff = true;
        int l = 0;
        for (Vertex x : vertexList) {// reset các đỉnh về trạng thái chưa thăm
            x.resetVisited();
        }
        for (Vertex x : vertexList) {// find vertex start
            if (x.getName().equals(nodeStart)) {// 
                x.setVisited();// set trạng thái của đỉnh bắt đầu là đã thăm
                stackVertex.push(x);// thêm đỉnh bắt đầu vào stack

                while (!stackVertex.isEmpty()) {
                    Vertex temp = stackVertex.peek();// lay gia tri de kiem tra dinh ke
                    if (temp.getName().equals(nodeEnd)) {// nếu đỉnh tạm thời trùng với đỉnh kết thúc thì:
                        cutoff = false;// Đánh dấu ngắt
                        break;// thoát khỏi vòng lặp, không duyệt stack nữa
                    }
                    // khi đỉnh tạm thời không trùng với đỉnh kết thúc
                    for (String a : temp.getAdj()) {// duyet cac dinh ke xem da duoc tham chua
                        boolean haveAVertexNotVisited = false;// tạo biến kiểm tra xem liệu rằng đỉnh tạm thời có còn đỉnh kề nào chưa 
                                                            // được thăm hay không
                        for (Vertex b : vertexList) {// tìm đỉnh có tên là a
                            // nếu tìm thấy đỉnh có tên là a mà chưa được thăm đồng thời chiều sâu l < giới hạn
                            if (b.getName().equals(a) && !b.isVisited() && l < limited) {
                                l++;// tăng chiều sâu hiện tại lên 1
                                b.setVisited();// đánh dấu đỉnh có tên là a đã được thăm
                                stackVertex.push(b);// đẩy đỉnh có tên là a vào trong stack
                                haveAVertexNotVisited = true;// đánh dấu là đỉnh hiện tại (temp) có 1 đỉnh chưa thăm
                                break;// ngừng việc kiểm tra xem còn đỉnh nào chưa thăm không
                            }
                        }
                        if (haveAVertexNotVisited) {// Nếu đỉnh hiện tại còn ít nhất 1 đỉnh chưa thăm thì 
                                                    // thoát khỏi vòng lặp (dừng việc tìm kiếm đỉnh kề của đỉnh hiện tại
                                                    // và tiếp tục duyệt lấy đỉnh trên cùng của stack ra để kiểm tra các
                                                    // đỉnh kề. Do ở bước trên khi tìm thấy đỉnh kề chưa được thăm ta đã
                                                    //thêm vào stack rồi.
                                                    // 
                            break;
                        } else {// khi đỉnh kề đang xét của đỉnh tạm thời (đỉnh temp) mà đã được thăm. Ta kiểm tra liệu 
                                // trong danh sách đỉnh kề của đỉnh temp có còn đỉnh kề nào chưa thăm không
                                // nếu có thì tiếp tục duyệt danh dách các đỉnh kề. Ngược lại thì lấy đỉnh tạm thời ra khỏi stack
                                // giảm chiều sâu đi 1 và tiếp tục duyệt 
                            haveAVertexNotVisited = false;
                            for (String c : temp.getAdj()) {// duyệt các đỉnh kề của đỉnh hiện tại (đỉnh temp)
                                for (Vertex k : vertexList) {
                                    if (k.getName().equals(c) && !k.isVisited() && l < limited) {
                                        haveAVertexNotVisited = true;
                                        break;
                                    }
                                }
                                if (haveAVertexNotVisited) {
                                    break;
                                }
                            }
                            if (haveAVertexNotVisited) {
                                continue;
                            } else {
                                stackVertex.pop();
                                l--;
                                break;
                            }
                        }
                    }
                }
                if (!cutoff) {// nếu tìm thấy đường đi thì lưu đường đi vào solution
                    for (Vertex c : stackVertex) {
                        solution.add(c.getName());
                    }

                } else {
                    // System.out.println("CUTOFF");
                }
                break;
            }
        }
        return solution;
    }

    public static List<String> iterativeDeepeningSearch(String nodeStart, String nodeEnd) {
        List<String> solution = new ArrayList<String>();
        int i = 0;
        while (i < vertexList.size()) {
            solution = depthLimitedSearch(nodeStart, nodeEnd, i);
            if (!solution.isEmpty()) {
                break;
            }
            i++;
        }
        return solution;
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        int luaChon = -1;
        while (luaChon != 0) {
            System.out.println("1.BFS\n2.UCS\n3.DFS\n4.DLS\n5.IDS\nLUACHON:");
            luaChon = sc.nextInt();
            switch (luaChon) {
                case 1: {
                    System.out.println("Nhap dinh bat dau: ");
                    char Start = sc.next().charAt(0);
                    System.out.println("Nhap dinh dich: ");
                    char Goal = sc.next().charAt(0);

                    BufferedReader reader = new BufferedReader(new FileReader(inputpath));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" ");
                        char start = parts[0].charAt(0);
                        char end = parts[1].charAt(0);
                        int weight = Integer.parseInt(parts[2]);
                        edges.add(new Edge(start, end, weight));
                         edges.add(new Edge(end, start, weight));
                        nodes.add(new Node(start, weight));
                        nodes.add(new Node(end, weight));
                    }
                    reader.close();
                    visited = new boolean[nodes.size()];
                    bfs(Start, Goal);
                    FileWriter writer = new FileWriter(outputPath);
                    writer.write(path.substring(0, path.length() - 3));
                    writer.close();
                }
                break;
                case 2: {
                    System.out.println("Nhap dinh bat dau: ");
                    char Start = sc.next().charAt(0);
                    System.out.println("Nhap dinh dich: ");
                    char Goal = sc.next().charAt(0);

                    List<Edge> edges = new ArrayList<>();
                    readFile(edges);

                    List<Character> path = uniformCostSearch(Start, Goal, edges);
                    for (int i = 0; i < path.size(); i++) {
                        System.out.print(path.get(i));
                        if (i != path.size() - 1) {
                            System.out.print(" --> ");
                        }
                    }
                    writeFile(path);
                }
                break;

                case 3: {

                    System.out.println("Nhap dinh bat dau: ");
                    char Start = sc.next().charAt(0);
                    System.out.println("Nhap dinh dich: ");
                    char Goal = sc.next().charAt(0);

                    int v = 50;// số đỉnh
                    V = v;
                    adj = new LinkedList[v];
                    for (int i = 0; i < v; ++i) {
                        adj[i] = new LinkedList();
                    }

                    readFile(adj);
                    DFS(Start, Goal);// đỉnh bắt đầu , đỉnh đích
                    writeFile();
                }
                break;
                case 4: {
                    String temp = sc.nextLine();

                    System.out.println("Nhap dinh bat dau: ");
                    String Start = sc.nextLine();
                    System.out.println("Nhap dinh dich: ");
                    String Goal = sc.nextLine();
                    System.out.println("Nhap gioi han: ");
                    int gioiHan = sc.nextInt();

                    vertexList = new ArrayList<Vertex>();
                    readFile1(vertexList);
                    List<String> solution = depthLimitedSearch(Start, Goal, gioiHan);// start, end,
                    showSolution(solution);
                }
                break;
                case 5: {

                    String temp = sc.nextLine();

                    System.out.println("Nhap dinh bat dau: ");
                    String Start = sc.nextLine();
                    System.out.println("Nhap dinh dich: ");
                    String Goal = sc.nextLine();

                    vertexList = new ArrayList<Vertex>();
                    readFile1(vertexList);
                    List<String> solution = iterativeDeepeningSearch(Start, Goal);// start, end
                    showSolution(solution);

                }
                break;

            }
            
              System.out.println("\n========================================\n");
        }

    }
}
