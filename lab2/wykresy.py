import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.backends.backend_pdf import PdfPages

def load_and_prepare_data(file_path):
    df = pd.read_csv(file_path, sep=":", header=None, names=['Algorithm', 'Data'])
    data_split = df['Data'].str.split(' ', expand=True)
    df['n'] = pd.to_numeric(data_split[0])
    df['Comparisons'] = pd.to_numeric(data_split[1])
    df['Swaps'] = pd.to_numeric(data_split[2], errors='coerce').fillna(0)
    return df

import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from matplotlib.backends.backend_pdf import PdfPages

def generate_plots(df, k_value, algorithms=None, suffix=''):
    metrics = ['Comparisons', 'Swaps', 'c/n', 's/n', 'C value']
    df_agg = df.groupby(['Algorithm', 'n']).agg({'Comparisons': 'mean', 'Swaps': 'mean'}).reset_index()
    df_agg['c/n'] = df_agg['Comparisons'] / df_agg['n']
    df_agg['s/n'] = df_agg['Swaps'] / df_agg['n']
    df_agg['C value'] = df_agg.apply(lambda row: row['Comparisons'] / (row['n'] * np.log(row['n'])) if row['n'] > 1 else np.nan, axis=1)

    n_ranges = {"small": (10, 50), "large": (1000, 50000)}

    for range_label, (n_start, n_end) in n_ranges.items():
        with PdfPages(f'sorting_k_{k_value}_{range_label}{suffix}.pdf') as pdf:
            for metric in metrics:
                plt.figure(figsize=(10, 6))
                for algorithm in df_agg['Algorithm'].unique():
                    if algorithms and algorithm not in algorithms:
                        continue
                    if algorithm == 'DualPivotQuickSort' or metric != 'C value':
                        subset = df_agg[(df_agg['Algorithm'] == algorithm) & (df_agg['n'] >= n_start) & (df_agg['n'] <= n_end)]
                        if not subset.empty:
                            plt.plot(subset['n'], subset[metric], label=algorithm + (' C value' if metric == 'C value' else ''), marker='o')
                if not (algorithm == 'DualPivotQuickSort' and metric == 'C value'):
                    plt.title(f'{metric} (k={k_value}, range: {n_start}-{n_end})')
                    plt.xlabel('n')
                    plt.ylabel(metric)
                    plt.legend()
                    plt.grid(True)
                    pdf.savefig()
                    plt.close()

file_paths = ['Data_1.txt', 'Data_10.txt', 'Data_100.txt']
k_values = [1, 10, 100]

# Standardowe wykresy dla wszystkich algorytmów
for file_path, k in zip(file_paths, k_values):
    df = load_and_prepare_data(file_path)
    generate_plots(df, k)

# Wykresy tylko dla QuickSort i DualPivotQuickSort
qs_and_dpqs_algorithms = ['QuickSort', 'DualPivotQuickSort']
for file_path, k in zip(file_paths, k_values):
    df = load_and_prepare_data(file_path)
    generate_plots(df, k, algorithms=qs_and_dpqs_algorithms, suffix='_QS_and_DPQS')

# Wykresy tylko dla CustomSort i MergeSort
cs_and_ms_algorithms = ['CustomSort', 'MergeSort']
for file_path, k in zip(file_paths, k_values):
    df = load_and_prepare_data(file_path)
    generate_plots(df, k, algorithms=cs_and_ms_algorithms, suffix='_CS_and_MS')

