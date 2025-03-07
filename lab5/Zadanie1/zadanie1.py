import random
import networkx as nx
import matplotlib.pyplot as plt

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

def draw_graph(G,filename):
    # Rysuje graf z etykietami wag krawędzi
    pos = nx.spring_layout(G)  # Pozycje wierzchołków w układzie 2D
    nx.draw(G, pos, with_labels=True, node_color='skyblue', node_size=500, edge_color='k', linewidths=1, font_size=15)

    # Etykiety dla krawędzi
    edge_labels = nx.get_edge_attributes(G, 'weight')
    nx.draw_networkx_edge_labels(G, pos, edge_labels={e: f"{w:.2f}" for e, w in edge_labels.items()})
    plt.savefig(filename)



def prim_algorithm(G):
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


def kruskal_algorithm(G):
    edges = [(data['weight'], u, v) for u, v, data in G.edges(data=True)]
    n = len(G.nodes())
    result = []  # lista krawędzi w MST
    mst_cost = 0  # całkowity koszt MST

    # Sortowanie wszystkich krawędzi w grafie w porządku rosnącym według wag
    edges = sorted(edges, key=lambda item: item[0])

    parent = []
    rank = []

    # Tworzenie zbiorów dla każdego wierzchołka
    for node in range(n):
        parent.append(node)
        rank.append(0)

    # Index używany do sortowanych krawędzi
    i = 0

    # Liczba krawędzi w MST musi być równa (n-1)
    e = 0
    while e < n - 1:
        if i >= len(edges):
            break
        weight, u, v = edges[i]
        i += 1
        x = find(parent, u)
        y = find(parent, v)

        # Jeśli dodanie tej krawędzi nie powoduje cyklu
        if x != y:
            e += 1
            result.append((u, v, weight))
            mst_cost += weight
            union(parent, rank, x, y)

    return mst_cost, result

# Przykład użycia:
n = 5  # Liczba wierzchołków
G = generate_complete_graph(n)
draw_graph(G, "graph.png")

# Prim's algorithm
prim_mst_cost, prim_mst_edges = prim_algorithm(G)
print(f"Koszt MST (Prim's): {prim_mst_cost}")
for edge in prim_mst_edges:
    print(f"Krawędź {edge[0]} - {edge[1]} z wagą {edge[2]:.2f}")

# Kruskal's algorithm
kruskal_mst_cost, kruskal_mst_edges = kruskal_algorithm(G)
print(f"Koszt MST (Kruskal's): {kruskal_mst_cost}")
for edge in kruskal_mst_edges:
    print(f"Krawędź {edge[0]} - {edge[1]} z wagą {edge[2]:.2f}")
