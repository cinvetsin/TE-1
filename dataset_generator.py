import csv
import random

# Define sizes
sizes = {
    "small": 2**9,
    "medium": 2**13,
    "large": 2**16
}

# Define functions to generate sorted, random, and reversed datasets
def generate_sorted(size):
    base_data = random.sample(range(2**22), size)
    return sorted(base_data)

def generate_random(size):
    return random.sample(range(2**22), size)

def generate_restructured_reversed(size):
    base_data = random.sample(range(2**22), size)
    return sorted(base_data, reverse=True)

# Export datasets to CSV files
csv_files_restructured = []

if __name__ == "__main__":
    for size_key, size_value in sizes.items():
        # Sorted dataset
        with open(f'sorted_{size_key}.csv', 'w', newline='') as csvfile:
            writer = csv.writer(csvfile, delimiter='\n')
            writer.writerow(generate_sorted(size_value))
            csv_files_restructured.append(f'sorted_{size_key}.csv')
        
        # Random dataset
        with open(f'random_{size_key}.csv', 'w', newline='') as csvfile:
            writer = csv.writer(csvfile, delimiter='\n')
            writer.writerow(generate_random(size_value))
            csv_files_restructured.append(f'random_{size_key}.csv')
        
        # Reversed dataset
        with open(f'reversed_{size_key}.csv', 'w', newline='') as csvfile:
            writer = csv.writer(csvfile, delimiter='\n')
            writer.writerow(generate_restructured_reversed(size_value))
            csv_files_restructured.append(f'reversed_{size_key}.csv')