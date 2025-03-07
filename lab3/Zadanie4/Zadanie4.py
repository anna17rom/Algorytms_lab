import numpy as np
import matplotlib.pyplot as plt

def read_data(file_name):
    with open(file_name, 'r') as file:
        lines = file.readlines()
        data = [(int(line.split(',')[0].split(':')[1]), int(line.split(',')[2].split(':')[1]), int(line.split(',')[3].split(':')[1].replace('ms', '')), int(line.split(',')[4].split(':')[1])) for line in lines]
    return data

def plot_comparison(data, color, label):
    n_values = np.unique([entry[0] for entry in data])
    comparison_values = [np.mean([entry[1] for entry in data if entry[0] == n]) for n in n_values]
    plt.plot(n_values, comparison_values, color=color, label=label)

def plot_time(data, color, label):
    n_values = np.unique([entry[0] for entry in data])
    time_values = [np.mean([entry[2] for entry in data if entry[0] == n]) for n in n_values]
    plt.plot(n_values, time_values, color=color, label=label)

# Define file names and corresponding colors
file_color_mapping = [
    ("BINARYSEARCH_STARTVALUE.txt", 'red'),
    ("BINARYSEARCH_MIDDLEVALUE.txt", 'green'),
    ("BINARYSEARCH_ENDVALUE.txt", 'yellow'),
    ("BINARYSEARCH_RANDOMVALUE.txt", 'violet'),
    ("BINARYSEARCH_NONEXISTVALUE.txt", 'black')
]

# Plot comparison graph
plt.figure(figsize=(10, 6))
for file_name, color in file_color_mapping:
    data = read_data(file_name)
    plot_comparison(data, color, file_name.split("_")[1].split(".")[0])
plt.xlabel('Array Size (n)')
plt.ylabel('Average Comparisons')
plt.title('Average Comparison Count vs Array Size')
plt.legend()
plt.grid(True)
plt.show()

# Plot time graph
plt.figure(figsize=(10, 6))
for file_name, color in file_color_mapping:
    data = read_data(file_name)
    plot_time(data, color, file_name.split("_")[1].split(".")[0])
plt.xlabel('Array Size (n)')
plt.ylabel('Average Time (ms)')
plt.title('Average Execution Time vs Array Size')
plt.legend()
plt.grid(True)
plt.show()

