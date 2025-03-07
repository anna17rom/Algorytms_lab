import matplotlib.pyplot as plt


def read_data_from_file(filename):
    data = {'Increasing': {'heights': [], 'N_values': []},
            'Random': {'heights': [], 'N_values': []}}
    current_experiment = None
    current_N = None

    with open(filename, 'r') as file:
        for line in file:
            if line.startswith('N ='):
                parts = line.split()
                current_N = int(parts[2])  # Zakładamy, że N jest zawsze trzecim elementem w tej linii
                if 'Rosnący ciąg' in line:
                    current_experiment = 'Increasing'
                elif 'Losowy ciąg' in line:
                    current_experiment = 'Random'
            elif line.startswith('[') and line.endswith(']\n'):
                heights = line[1:-2].split(', ')
                heights = list(map(int, heights))
                if current_experiment is not None:
                    data[current_experiment]['heights'].extend(heights)
                    data[current_experiment]['N_values'].extend([current_N] * len(heights))

    return data


def downsample(heights, n_values, factor=10):
    # Próbkowanie co `factor` elementów
    return heights[::factor], n_values[::factor]


def plot_data(data):
    plt.figure(figsize=(14, 7))

    for i, key in enumerate(['Increasing', 'Random']):
        # Poprawne wywołanie downsample z odpowiednimi listami
        sampled_heights, sampled_n_values = downsample(data[key]['heights'], data[key]['N_values'])
        plt.subplot(1, 2, i + 1)
        plt.plot(sampled_n_values, sampled_heights, label=f'{key} ciąg -> Losowe usuwanie')
        plt.title(f'Zmiana wysokości BST ({key} ciąg)')
        plt.xlabel('Wartość N (ilość elementów)')
        plt.ylabel('Wysokość drzewa')
        plt.legend()

    plt.tight_layout()
    plt.show()


filename = 'full_zadanie2_height.txt'
data = read_data_from_file(filename)
plot_data(data)