import pandas as pd
import matplotlib.pyplot as plt

# Caminho do CSV
path = "hash_experiments_results.csv"  # ou "bin/hash_experiments_results.csv"

# Lê o conteúdo do arquivo manualmente
with open(path, encoding="utf-8") as f:
    lines = f.readlines()

# Lê o cabeçalho original (18 colunas corretas)
headers = [
    "TableType", "TableSize", "HashFunction", "DataSize",
    "InsertionTime(ns)", "InsertionCollisions",
    "Search1Time(ns)", "Search1Comparisons",
    "Search2Time(ns)", "Search2Comparisons",
    "Search3Time(ns)", "Search3Comparisons",
    "Search4Time(ns)", "Search4Comparisons",
    "Search5Time(ns)", "Search5Comparisons",
    "AvgSearchTime(ns)", "AvgSearchComparisons"
]

rows = []
for line in lines[1:]:
    parts = line.strip().split(",")

    # Corrige os campos com vírgula decimal (ex: 105760,00)
    if len(parts) == 20:
        # Junta avg time e avg comparisons quebrados
        avg_time = parts[-4] + "." + parts[-3]
        avg_comp = parts[-2] + "." + parts[-1]
        fixed = parts[:16] + [avg_time, avg_comp]
        rows.append(fixed)
    else:
        # Se estiver ok (18 colunas), só adiciona
        rows.append(parts)

# Cria o DataFrame
df = pd.DataFrame(rows, columns=headers)

# Converte colunas numéricas
numeric_cols = headers[3:]  # ignora TableType, TableSize, HashFunction
for col in numeric_cols:
    df[col] = pd.to_numeric(df[col], errors="coerce")

# Converte TableSize para int também
df["TableSize"] = pd.to_numeric(df["TableSize"], errors="coerce")

# Filtro para 20M
df20 = df[df["DataSize"] == 20000000]
print("Total de linhas para DataSize=20000000:", len(df20))

# Gráfico 1 – Colisões na Inserção
plt.figure()
for table in df20["TableType"].unique():
    for hf in df20["HashFunction"].unique():
        sub = df20[(df20["TableType"] == table) & (df20["HashFunction"] == hf)]
        if not sub.empty:
            sub = sub.sort_values("TableSize")
            plt.plot(sub["TableSize"], sub["InsertionCollisions"], marker='o', label=f"{table}-{hf}")
plt.title("Colisões na Inserção vs Tamanho da Tabela (20M)")
plt.xlabel("Tamanho da Tabela")
plt.ylabel("Colisões")
plt.grid(True)
plt.legend()
plt.tight_layout()
plt.show()

# Gráfico 2 – Tempo Médio de Busca
plt.figure()
for table in df20["TableType"].unique():
    for hf in df20["HashFunction"].unique():
        sub = df20[(df20["TableType"] == table) & (df20["HashFunction"] == hf)]
        if not sub.empty:
            sub = sub.sort_values("TableSize")
            plt.plot(sub["TableSize"], sub["AvgSearchTime(ns)"], marker='o', label=f"{table}-{hf}")
plt.title("Tempo Médio de Busca vs Tamanho da Tabela (20M)")
plt.xlabel("Tamanho da Tabela")
plt.ylabel("Tempo (ns)")
plt.grid(True)
plt.legend()
plt.tight_layout()
plt.show()

# Gráfico 3 – Comparações Médias de Busca
plt.figure()
for table in df20["TableType"].unique():
    for hf in df20["HashFunction"].unique():
        sub = df20[(df20["TableType"] == table) & (df20["HashFunction"] == hf)]
        if not sub.empty:
            sub = sub.sort_values("TableSize")
            plt.plot(sub["TableSize"], sub["AvgSearchComparisons"], marker='o', label=f"{table}-{hf}")
plt.title("Comparações Médias de Busca vs Tamanho da Tabela (20M)")
plt.xlabel("Tamanho da Tabela")
plt.ylabel("Comparações")
plt.grid(True)
plt.legend()
plt.tight_layout()
plt.show()
