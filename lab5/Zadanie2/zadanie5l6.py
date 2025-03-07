import matplotlib.pyplot as plt
import numpy as np
import networkx as nx
from collections import defaultdict

def visualize_tree(tree, title, filename):
  
    G = nx.Graph()
    for parent in tree:
        for child in tree[parent]:
            G.add_edge(parent, child)
    pos = nx.spring_layout(G)
    plt.figure(figsize=(8, 8))
    nx.draw(G, pos, with_labels=True, node_color='skyblue', node_size=500, edge_color='k', font_size=15, font_color='black')
    plt.title(title)
    plt.savefig(filename)
   

def generate_random_tree(n):

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
    if v not in tree or not tree[v]:
        depths[v] = 0
        return 0
    max_depth = 0
    for child in tree[v]:
        child_depth = calculate_depths(child, tree, depths)
        max_depth = max(max_depth, child_depth + 1)
    depths[v] = max_depth
    return max_depth


n = 8  # Liczba węzłów
tree = {0: [1, 2], 1: [5, 6], 2: [3], 3: [4], 5: [7]}
visualize_tree(tree,"Tree", "tree.png")
depths = {}
calculate_depths(0, tree, depths)

dp = [0] * n
arrival_time = [-1] * n
dfs(0, tree, dp, arrival_time, 0, depths)

# Wydrukuj czas przybycia dla każdego węzła
print("Czas przybycia sygnału do każdego węzła:")
for i in range(n):
    print(f"Węzeł {i}: czas przybycia = {arrival_time[i]}")
