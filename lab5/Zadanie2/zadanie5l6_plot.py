import matplotlib.pyplot as plt
import numpy as np
import networkx as nx
from collections import defaultdict, deque
from gen import gen

def visualize_tree(tree, title="Random Tree"):
    """ Visualizes the tree using NetworkX. """
    G = nx.Graph()
    for parent in tree:
        for child in tree[parent]:
            G.add_edge(parent, child)
    pos = nx.spring_layout(G)
    plt.figure(figsize=(8, 8))
    nx.draw(G, pos, with_labels=True, node_color='skyblue', node_size=500, edge_color='k', font_size=15, font_color='black')
    plt.title(title)
    plt.show()

def generate_random_tree(n):
    """Generates a random tree with n nodes."""
    if n == 1:
        return {0: []}
    tree = defaultdict(list)
    for i in range(1, n):
        parent = np.random.randint(0, i)
        tree[parent].append(i)
    return tree


def dfs(v, tree, dp, arrival_time, current_time, depths):
    children = tree.get(v, [])
    if not children:
        dp[v] = 0
        arrival_time[v] = current_time
        return 0

    # Sort children by the depth of their subtrees, deepest first
    children.sort(key=lambda x: depths[x], reverse=True)

    child_times = []
    for child in children:
        child_time = dfs(child, tree, dp, arrival_time, current_time + 1 + len(child_times), depths)
        child_times.append(child_time + 1)

    max_time = max(child_times[i] + i for i in range(len(child_times)))
    dp[v] = max_time
    arrival_time[v] = current_time
    return max_time


def calculate_depths(v, tree, depths):
    if v not in tree:
        depths[v] = 0
        return 0

    # Inicjalizacja kolejki do BFS
    queue = [(v, 0)]  # każdy element to krotka (węzeł, aktualna głębokość)

    while queue:
        current_node, current_depth = queue.pop(0)

        # Ustawienie głębokości dla bieżącego węzła
        depths[current_node] = current_depth

        # Jeśli bieżący węzeł ma dzieci, dodajemy je do kolejki z głębokością o jeden większą
        if current_node in tree:
            for child in tree[current_node]:
                queue.append((child, current_depth + 1))

    # Funkcja zwraca maksymalną głębokość w drzewie
    return max(depths.values())

def perform_experiment(tree, n):
    depths = {}
    calculate_depths(0, tree, depths)

    dp = [0] * n
    arrival_time = [-1] * n
    dfs(0, tree, dp, arrival_time, 0, depths)

    return max(arrival_time)


def analyze_trees(trees):
    results = defaultdict(list)

    for n, edges in trees:
        tree = defaultdict(list)
        for u, v, _ in edges:
            tree[u].append(v)
        rounds = perform_experiment(tree, n)
        results[n].append(rounds)

    averages = {n: np.mean(results[n]) for n in results}
    maxima = {n: np.max(results[n]) for n in results}
    minima = {n: np.min(results[n]) for n in results}

    return averages, maxima, minima


# Assuming gen() is defined and returns the tree data
trees = gen()
averages, maxima, minima = analyze_trees(trees)

# Plotting the results
tree_sizes = sorted(averages.keys())

plt.figure(figsize=(12, 8))
plt.plot(tree_sizes, [averages[n] for n in tree_sizes], label='Średnia liczba rund', marker='o')
plt.plot(tree_sizes, [maxima[n] for n in tree_sizes], label='Maksymalna liczba rund', marker='o')
plt.plot(tree_sizes, [minima[n] for n in tree_sizes], label='Minimalna liczba rund', marker='o')
plt.xlabel('Liczba wierzchołków drzewa')
plt.ylabel('Liczba rund')
plt.title('Analiza liczby rund potrzebnych do rozesłania wiadomości')
plt.legend()
plt.grid(True)
plt.show()
