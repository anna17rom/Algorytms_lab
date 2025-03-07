import matplotlib.pyplot as plt
import numpy as np


def parse_data(filename, label):
    data = {}
    with open(filename, 'r') as file:
        lines = file.readlines()

    current_n = None
    experiment_type = None
    for line in lines:
        if line.startswith('N ='):
            parts = line.split('(')
            current_n = int(parts[0].split('=')[1].strip())
            experiment_type = parts[1].split(')')[0].strip()
            if current_n not in data:
                data[current_n] = {}
            if experiment_type not in data[current_n]:
                data[current_n][experiment_type] = {
                    'Total key comparisons': [],
                    'Total pointer changes': [],
                    'Average key comparisons per operation': [],
                    'Max key comparisons per operation': [],
                    'Average pointer changes and reads per operation': [],
                    'Max pointer changes and reads per operation': []
                }
        elif 'Total key comparisons:' in line:
            value = int(line.split(':')[1].strip())
            data[current_n][experiment_type]['Total key comparisons'].append((value, label))
        elif 'Total pointer changes:' in line:
            value = int(line.split(':')[1].strip())
            data[current_n][experiment_type]['Total pointer changes'].append((value, label))
        elif 'Average key comparisons per operation:' in line:
            value = float(line.split(':')[1].strip())
            data[current_n][experiment_type]['Average key comparisons per operation'].append((value, label))
        elif 'Max key comparisons per operation:' in line:
            value = float(line.split(':')[1].strip())
            data[current_n][experiment_type]['Max key comparisons per operation'].append((value, label))
        elif 'Average pointer changes and reads per operation:' in line:
            value = float(line.split(':')[1].strip())
            data[current_n][experiment_type]['Average pointer changes and reads per operation'].append((value, label))
        elif 'Max pointer changes and reads per operation:' in line:
            value = float(line.split(':')[1].strip())
            data[current_n][experiment_type]['Max pointer changes and reads per operation'].append((value, label))
    return data


def combine_data(filenames, labels):
    combined_data = {}
    for filename, label in zip(filenames, labels):
        data = parse_data(filename, label)
        for n, experiments in data.items():
            if n not in combined_data:
                combined_data[n] = {}
            for experiment_type, metrics in experiments.items():
                if experiment_type not in combined_data[n]:
                    combined_data[n][experiment_type] = {
                        'Total key comparisons': [],
                        'Total pointer changes': [],
                        'Average key comparisons per operation': [],
                        'Max key comparisons per operation': [],
                        'Average pointer changes and reads per operation': [],
                        'Max pointer changes and reads per operation': []
                    }
                for metric, values in metrics.items():
                    combined_data[n][experiment_type][metric].extend(values)
    return combined_data


def plot_data(data, experiment_type, metric, labels_colors):
    ns = sorted(data.keys())

    plt.figure(figsize=(10, 6))
    for label, color in labels_colors.items():
        all_values = []
        n_labels = []

        for n in ns:
            values = [val for val, lbl in data[n][experiment_type][metric] if lbl == label]
            all_values.extend(values)
            n_labels.extend([n] * len(values))

        mean_values = [np.mean([val for val, lbl in data[n][experiment_type][metric] if lbl == label]) for n in ns]

        plt.scatter(n_labels, all_values, alpha=0.4, label=label, color=color)
        plt.plot(ns, mean_values, marker='o', linestyle='-', color=color)

    plt.title(f'{metric} for {experiment_type}')
    plt.xlabel('N')
    plt.ylabel(metric)
    plt.legend()
    plt.grid(True)
    plt.show()


# Use the functions
filenames = ["full_zadanie2.txt", "zadanie4 (1).txt", "zadanie6.txt"]
labels = ["BST Tree", "RB Tree", "Splay Tree"]
labels_colors = {"BST Tree": "blue", "RB Tree": "green", "Splay Tree": "yellow"}

combined_data = combine_data(filenames, labels)
metrics = ['Total key comparisons', 'Total pointer changes', 'Average key comparisons per operation',
           'Max key comparisons per operation', 'Average pointer changes and reads per operation',
           'Max pointer changes and reads per operation']
experiment_types = ['Rosnący ciąg -> Losowe usuwanie', 'Losowy ciąg -> Losowe usuwanie']

for experiment_type in experiment_types:
    for metric in metrics:
        plot_data(combined_data, experiment_type, metric, labels_colors)
