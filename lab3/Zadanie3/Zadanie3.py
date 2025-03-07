import pandas as pd
import matplotlib.pyplot as plt

# Read the data from the text file
data = pd.read_csv("Data_SELECT_zadanie3.txt", sep=" ", names=["n", "k", "splitNum", "comparisons", "swaps", "time"])

# Calculate averages for each splitNum
avg_split_num_3 = data[data["splitNum"] == 3].groupby("n").mean().reset_index()
avg_split_num_5 = data[data["splitNum"] == 5].groupby("n").mean().reset_index()
avg_split_num_7 = data[data["splitNum"] == 7].groupby("n").mean().reset_index()
avg_split_num_9 = data[data["splitNum"] == 9].groupby("n").mean().reset_index()

# Plot comparison graphs
plt.figure(figsize=(12, 8))

plt.subplot(2, 2, 1)
plt.plot(avg_split_num_3["n"], avg_split_num_3["comparisons"], color="green", label="SplitNum 3")
plt.plot(avg_split_num_5["n"], avg_split_num_5["comparisons"], color="blue", label="SplitNum 5")
plt.plot(avg_split_num_7["n"], avg_split_num_7["comparisons"], color="red", label="SplitNum 7")
plt.plot(avg_split_num_9["n"], avg_split_num_9["comparisons"], color="yellow", label="SplitNum 9")
plt.xlabel("n")
plt.ylabel("Average Comparisons")
plt.legend()

plt.subplot(2, 2, 2)
plt.plot(avg_split_num_3["n"], avg_split_num_3["swaps"], color="green", label="SplitNum 3")
plt.plot(avg_split_num_5["n"], avg_split_num_5["swaps"], color="blue", label="SplitNum 5")
plt.plot(avg_split_num_7["n"], avg_split_num_7["swaps"], color="red", label="SplitNum 7")
plt.plot(avg_split_num_9["n"], avg_split_num_9["swaps"], color="yellow", label="SplitNum 9")
plt.xlabel("n")
plt.ylabel("Average Swaps")
plt.legend()

plt.subplot(2, 2, 3)
plt.plot(avg_split_num_3["n"], avg_split_num_3["comparisons"]/avg_split_num_3["n"], color="green", label="SplitNum 3")
plt.plot(avg_split_num_5["n"], avg_split_num_5["comparisons"]/avg_split_num_5["n"], color="blue", label="SplitNum 5")
plt.plot(avg_split_num_7["n"], avg_split_num_7["comparisons"]/avg_split_num_7["n"], color="red", label="SplitNum 7")
plt.plot(avg_split_num_9["n"], avg_split_num_9["comparisons"]/avg_split_num_9["n"], color="yellow", label="SplitNum 9")
plt.xlabel("n")
plt.ylabel("Average Comparisons per element")
plt.legend()

plt.subplot(2, 2, 4)
plt.plot(avg_split_num_3["n"], avg_split_num_3["swaps"]/avg_split_num_3["n"], color="green", label="SplitNum 3")
plt.plot(avg_split_num_5["n"], avg_split_num_5["swaps"]/avg_split_num_5["n"], color="blue", label="SplitNum 5")
plt.plot(avg_split_num_7["n"], avg_split_num_7["swaps"]/avg_split_num_7["n"], color="red", label="SplitNum 7")
plt.plot(avg_split_num_9["n"], avg_split_num_9["swaps"]/avg_split_num_9["n"], color="yellow", label="SplitNum 9")
plt.xlabel("n")
plt.ylabel("Average Swaps per element")
plt.legend()

plt.tight_layout()
plt.show()

# Plot time graph
plt.figure(figsize=(8, 6))

plt.plot(avg_split_num_3["n"], avg_split_num_3["time"], color="green", label="SplitNum 3")
plt.plot(avg_split_num_5["n"], avg_split_num_5["time"], color="blue", label="SplitNum 5")
plt.plot(avg_split_num_7["n"], avg_split_num_7["time"], color="red", label="SplitNum 7")
plt.plot(avg_split_num_9["n"], avg_split_num_9["time"], color="yellow", label="SplitNum 9")
plt.xlabel("n")
plt.ylabel("Average Time (s)")
plt.legend()

plt.tight_layout()
plt.show()
