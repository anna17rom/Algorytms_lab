import random
import networkx as nx
import matplotlib.pyplot as plt
import time

def generate_complete_graph(n):
    # Tworzenie grafu z losowymi wagami dla każdej krawędzi z prawdopodobieństwem p
    G = nx.Graph()
    p = 1
    for i in range(n):
        for j in range(i + 1, n):
            if random.random() < p:  # Losowanie, czy dodać krawędź
                weight = random.uniform(0, 1)
                G.add_edge(i, j, weight=weight)
    return G

def print_mst(edges,mst_cost):
    print(f"Koszt MST: {mst_cost}")
    for edge in edges:
        print(f"Krawędź {edge[0]} - {edge[1]} z wagą {edge[2]:.2f}")

def draw_graph(G):
    # Rysuje graf z etykietami wag krawędzi
    pos = nx.spring_layout(G)  # Pozycje wierzchołków w układzie 2D
    nx.draw(G, pos, with_labels=True, node_color='skyblue', node_size=500, edge_color='k', linewidths=1, font_size=15)

    # Etykiety dla krawędzi
    edge_labels = nx.get_edge_attributes(G, 'weight')
    nx.draw_networkx_edge_labels(G, pos, edge_labels={e: f"{w:.2f}" for e, w in edge_labels.items()})

    plt.show()

def prim_algorithm(G,mst_collection,n):
    # Wybieramy startowy wierzchołek
    start_node = list(G.nodes())[0]
    visited = set([start_node])

    # Lista krawędzi, które można dodać do MST
    edges = [(weight['weight'], start_node, to) for to, weight in G[start_node].items()]
    mst_edges = []
    mst_cost = 0

    # Dodajemy krawędzie do MST, póki są dostępne
    while edges:
        # Szukamy krawędzi z minimalnym ciężarem nieodwiedzonego jeszcze wierzchołka
        min_edge = None
        for edge in edges:
            if edge[2] not in visited and (min_edge is None or edge[0] < min_edge[0]):
                min_edge = edge

        if min_edge is None:
            break

        # Dodajemy wybraną krawędź do MST
        weight, frm, to = min_edge
        visited.add(to)
        mst_edges.append((frm, to, weight))
        mst_cost += weight
        edges.remove(min_edge)

        # Dodajemy nowe krawędzie z nowo odwiedzonego wierzchołka
        for next_to, attr in G[to].items():
            if next_to not in visited:
                edges.append((attr['weight'], to, next_to))
    mst_collection.append((n, mst_edges))

    return mst_cost, mst_edges



def find(parent, i):
    while parent[i] != i:
        i = parent[i]
    return i


def union(parent, rank, x, y):
    xroot = find(parent, x)
    yroot = find(parent, y)

    if rank[xroot] < rank[yroot]:
        parent[xroot] = yroot
    elif rank[xroot] > rank[yroot]:
        parent[yroot] = xroot
    else:
        parent[yroot] = xroot
        rank[xroot] += 1

def partition(arr, low, high):
    i = low - 1
    pivot = arr[high][2]['weight']

    for j in range(low, high):
        if arr[j][2]['weight'] <= pivot:
            i = i + 1
            arr[i], arr[j] = arr[j], arr[i]

    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1

def quicksort(arr, low, high):
    if low < high:
        pi = partition(arr, low, high)
        quicksort(arr, low, pi - 1)
        quicksort(arr, pi + 1, high)

def kruskal_algorithm(G, mst_collection):
    edges = list(G.edges(data=True))
    quicksort(edges, 0, len(edges) - 1)
    nodes = list(G.nodes)

    parent = {node: node for node in nodes}
    rank = {node: 0 for node in nodes}

    result = []
    mst_cost = 0

    i = 0  # Index used to sort edges
    e = 0  # Number of edges in MST must be equal to (n-1)

    while e < len(nodes) - 1:
        if i >= len(edges):
            break

        u, v, data = edges[i]
        weight = data['weight']
        i += 1
        x = find(parent, u)
        y = find(parent, v)

        # If including this edge does not cause a cycle
        if x != y:
            e += 1
            result.append((u, v, weight))
            mst_cost += weight
            union(parent, rank, x, y)

    mst_collection.append((mst_cost, result))


    return mst_cost, result


nMin, nMax, step, rep = 2,20,2,20
data = {"Prim": [], "Kruskal": []}
prim_msts = []
kruskal_msts = []
for n in range(nMin, nMax + 1, step):
        prim_times = []
        kruskal_times = []
        for _ in range(rep):

            G = generate_complete_graph(n)

            # Mierzenie czasu dla algorytmu Prima
            start_time = time.time()
            prim_algorithm(G,prim_msts,n)
            prim_times.append(time.time() - start_time)
            # Mierzenie czasu dla algorytmu Kruskala
            start_time = time.time()
            kruskal_algorithm(G,kruskal_msts)
            kruskal_times.append(time.time() - start_time)



        data["Prim"].append(sum(prim_times) / rep)
        data["Kruskal"].append(sum(kruskal_times) / rep)

    # Tworzenie wykresu
fig, ax = plt.subplots()
x = list(range(nMin, nMax + 1, step))
ax.plot(x, data["Prim"], label='Prim\'s Algorithm', marker='o')
ax.plot(x, data["Kruskal"], label='Kruskal\'s Algorithm', marker='o')
ax.set_xlabel('Number of Nodes')
ax.set_ylabel('Average Running Time (seconds)')
ax.set_title('Comparison of MST Algorithms')
ax.legend()
plt.grid(True)
plt.show()












































