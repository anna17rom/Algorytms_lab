import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression

def load_data(filepath):
    # Load the data into a pandas DataFrame, assuming the data format 'n time comparisons swaps'
    columns = ['n', 'time', 'comparisons', 'swaps']
    # Use `sep='\s+'` to handle whitespace delimited files
    data = pd.read_csv(filepath, sep='\s+', names=columns)
    # Calculate the logarithm of 'n' and 'comparisons' for regression
    data['log_n'] = np.log(data['n'])
    data['log_comparisons'] = np.log(data['comparisons'])
    return data


def perform_regression(data):
    # Prepare data for regression
    X = data[['log_n']]  # Features in 2D array
    y = data['log_comparisons']  # Target
    # Create and fit the regression model
    model = LinearRegression()
    model.fit(X, y)
    # Calculate the coefficient of determination (R^2)
    r_squared = model.score(X, y)
    return model.coef_[0], model.intercept_, r_squared

# Paths to the data files
file_paths = {
    "qs": "Data_QUICKSORT.txt",
    "qs_classic": "Data_QUICKSORT_Classic.txt",
    "qs_worst": "Data_QUICKSORT_WorstCase.txt",
    "dpqs_classic": "Data_DUALPIVOTQUICKSORT_Classic.txt",
    "dpqs": "Data_DUALPIVOTQUICKSORT.txt"
}

# Load and process data from each file
results = {}
for key, path in file_paths.items():
    data = load_data(path)
    coeff, intercept, r2 = perform_regression(data)
    results[key] = {
        "Coefficient": coeff,
        "Intercept": intercept,
        "R-squared": r2
    }

# Print the results
for algorithm, result in results.items():
    print(f"{algorithm} - Coefficient: {result['Coefficient']}, Intercept: {result['Intercept']}, R-squared: {result['R-squared']}")




































