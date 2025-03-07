import matplotlib.pyplot as plt
import pandas as pd


def read_data(file_path, has_splitnum=True):
    if has_splitnum:
        data = pd.read_csv(file_path, sep=' ', header=None)
        data.columns = ['n', 'k', 'splitNum', 'comparisons', 'swaps']
    else:
        data = pd.read_csv(file_path, sep=' ', header=None)
        data.columns = ['n', 'k', 'comparisons', 'swaps']
    return data


def calculate_avg_data(data):
    avg_data = data.groupby('n').mean().reset_index()
    return avg_data


def plot_graph(data_select, data_randomizedselect, avg_data_select, avg_data_randomizedselect):
    fig, axs = plt.subplots(2, 2, figsize=(12, 8))

    axs[0, 0].plot(data_select['n'], data_select['comparisons'], label='Select', color='orange', alpha=0.5)
    axs[0, 0].plot(avg_data_select['n'], avg_data_select['comparisons'], label='Avg Select', color='orange',
                   linestyle='dashed')
    axs[0, 0].plot(data_randomizedselect['n'], data_randomizedselect['comparisons'], label='RandomizedSelect',
                   color='green', alpha=0.5)
    axs[0, 0].plot(avg_data_randomizedselect['n'], avg_data_randomizedselect['comparisons'],
                   label='Avg RandomizedSelect', color='green', linestyle='dashed')
    axs[0, 0].set_title('Comparisons')
    axs[0, 0].legend()

    axs[0, 1].plot(data_select['n'], data_select['swaps'], label='Select', color='orange', alpha=0.5)
    axs[0, 1].plot(avg_data_select['n'], avg_data_select['swaps'], label='Avg Select', color='orange',
                   linestyle='dashed')
    axs[0, 1].plot(data_randomizedselect['n'], data_randomizedselect['swaps'], label='RandomizedSelect', color='green',
                   alpha=0.5)
    axs[0, 1].plot(avg_data_randomizedselect['n'], avg_data_randomizedselect['swaps'], label='Avg RandomizedSelect',
                   color='green', linestyle='dashed')
    axs[0, 1].set_title('Swaps')
    axs[0, 1].legend()

    axs[1, 0].plot(data_select['n'], data_select['swaps'] / data_select['n'], label='Select', color='orange', alpha=0.5)
    axs[1, 0].plot(avg_data_select['n'], avg_data_select['swaps'] / avg_data_select['n'], label='Avg Select',
                   color='orange', linestyle='dashed')
    axs[1, 0].plot(data_randomizedselect['n'], data_randomizedselect['swaps'] / data_randomizedselect['n'],
                   label='RandomizedSelect', color='green', alpha=0.5)
    axs[1, 0].plot(avg_data_randomizedselect['n'], avg_data_randomizedselect['swaps'] / avg_data_randomizedselect['n'],
                   label='Avg RandomizedSelect', color='green', linestyle='dashed')
    axs[1, 0].set_title('Swaps/n')
    axs[1, 0].legend()

    axs[1, 1].plot(data_select['n'], data_select['comparisons'] / data_select['n'], label='Select', color='orange',
                   alpha=0.5)
    axs[1, 1].plot(avg_data_select['n'], avg_data_select['comparisons'] / avg_data_select['n'], label='Avg Select',
                   color='orange', linestyle='dashed')
    axs[1, 1].plot(data_randomizedselect['n'], data_randomizedselect['comparisons'] / data_randomizedselect['n'],
                   label='RandomizedSelect', color='green', alpha=0.5)
    axs[1, 1].plot(avg_data_randomizedselect['n'],
                   avg_data_randomizedselect['comparisons'] / avg_data_randomizedselect['n'],
                   label='Avg RandomizedSelect', color='green', linestyle='dashed')
    axs[1, 1].set_title('Comparisons/n')
    axs[1, 1].legend()

    for ax in axs.flat:
        ax.set(xlabel='n', ylabel='Value')
        ax.label_outer()

    plt.suptitle("Comparisons and Swaps: Select vs RandomizedSelect (with Avg)")
    plt.tight_layout()
    plt.show()


# Read data from files
data_select = read_data("Data_SELECT.txt")
data_randomizedselect = read_data("Data_RANDOMIZEDSELECT.txt", has_splitnum=False)

# Calculate average data
avg_data_select = calculate_avg_data(data_select)
avg_data_randomizedselect = calculate_avg_data(data_randomizedselect)

# Plot graphs
plot_graph(data_select, data_randomizedselect, avg_data_select, avg_data_randomizedselect)



























































