import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# Load data from files
data_files = [
    "Data_QUICKSORT_Select.txt",
    "Data_QUICKSORT_WorstCase.txt"
]

algorithms = [
    "QuickSort with Select",
    "Worst Case QuickSort"
]

dfs = []
for file, algorithm in zip(data_files, algorithms):
    df = pd.read_csv(file, sep=" ", header=None, names=["n", "time", "comparisons", "swaps"])
    df["algorithm"] = algorithm
    dfs.append(df)

# Concatenate dataframes
df = pd.concat(dfs)

# Calculate average comparisons and time for each algorithm and n
avg_df = df.groupby(["algorithm", "n"]).mean().reset_index()

# Plotting
plt.figure(figsize=(12, 6))

# Average comparisons
plt.subplot(1, 2, 1)
for algorithm in algorithms:
    temp_df = avg_df[avg_df["algorithm"] == algorithm]
    plt.plot(temp_df["n"], temp_df["comparisons"], label=algorithm)
plt.title("Average Comparisons")
plt.xlabel("Data Size (n)")
plt.ylabel("Average Comparisons")
plt.legend()

# Average time
plt.subplot(1, 2, 2)
for algorithm in algorithms:
    temp_df = avg_df[avg_df["algorithm"] == algorithm]
    plt.plot(temp_df["n"], temp_df["time"], label=algorithm)
plt.title("Average Time")
plt.xlabel("Data Size (n)")
plt.ylabel("Average Time (ms)")
plt.legend()

plt.tight_layout()
plt.show()
