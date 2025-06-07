# 🔍 Projeto de Estrutura de Dados – Tabela Hash em Java

Este projeto foi desenvolvido para análise comparativa de desempenho entre diferentes **técnicas de hashing** e estratégias de **tratamento de colisões** em Java, utilizando conjuntos de dados grandes, simulando cenários reais de carga.

O trabalho foi realizado conforme a disciplina de **Resolução de Problemas Estruturados em Computação**, no curso de Engenharia de Software da PUCPR.

---

## 📋 Objetivo do Projeto

O objetivo foi aplicar os conceitos de **tabela hash** usando Java, testando múltiplas funções hash e técnicas de inserção, medindo o desempenho com grandes volumes de dados. O foco foi:

- Comparar **Open Addressing (sondagem linear)** com **Chaining (encadeamento externo)**.
- Avaliar três funções hash distintas:
  - **Divisão (k % m)**
  - **Multiplicação (Knuth)**
  - **Dobramento (folding de blocos de 3 dígitos)**
- Medir:
  - Tempo de inserção
  - Número de colisões
  - Tempo de busca
  - Número de comparações

---

## ⚙️ Implementação

O projeto está estruturado com as seguintes classes:
```
 📁 br.edu.hashing
├── domain.Registro.java // Armazena chaves de 9 dígitos
├── data.DataGenerator.java // Gera dados com seed fixa
├── hashing.functions.* // Três funções hash
├── hashing.table.* // HashTable, OpenAddressing, Chaining
├── hashing.metrics.Metrics.java // Contadores de colisões e comparações
└── experiments.ExperimentRunner.java // Executa os testes e exporta CSV
```

- Cada teste insere e busca dados para todas as combinações (tipo × função × tamanho).
- Os dados são exportados no arquivo `hash_experiments_results.csv` para análise gráfica.

---

## 📊 Conjuntos de Dados Utilizados

| Conjunto | Quantidade de Registros | Seed  |
|---------|--------------------------|-------|
| Pequeno | 1.000.000                | 12345 |
| Médio   | 5.000.000                | 54321 |
| Grande  | 20.000.000               | 99999 |

Cada registro possui um código único de **9 dígitos**, formatado como string com zeros à esquerda.

---

## 🧪 Tamanhos das Tabelas Testadas

| Tamanho da Tabela | Observação                           |
|-------------------|--------------------------------------|
| 1.000             | Pequena – Alta carga (α muito alto)  |
| 10.000            | Média                                |
| 100.000           | Grande                               |

A carga α = n/m varia entre ~1000 até 200 com o conjunto grande.

---

## 🧠 Funções Hash Utilizadas

- `DivisionHash`: k % m  
- `MultiplicationHash`: floor(m * frac(k * A)), A ≈ 0.618033  
- `FoldingHash`: soma de blocos de 3 dígitos (ex: 123 + 456 + 789)

---

## 📈 Resultados (Dataset: 20.000.000)

### 🔹 Gráfico 1 – Colisões na Inserção
- `OpenAddressing-Folding` foi a pior, com bilhões de colisões.
- `Chaining` manteve colisões controladas mesmo com carga elevada.
- `Chaining + Multiplication` teve a menor colisão entre todos.

### 🔹 Gráfico 2 – Tempo Médio de Busca (ns)
- `Chaining-Multiplication` e `Chaining-Division` foram os mais rápidos.
- `OpenAddressing` cresceu drasticamente conforme α aumentou.
- Folding teve comportamento inconsistente.

### 🔹 Gráfico 3 – Comparações Médias na Busca
- `Chaining` manteve o número de comparações próximo de 2000, mesmo com 20M de registros.
- `OpenAddressing-Folding` atingiu mais de 200.000 comparações.

---

## 🏆 Conclusão

### ✅ Melhor combinação:
- **Chaining + MultiplicationHash + Tabela com 100.000**
  - Baixa colisão
  - Busca rápida
  - Poucas comparações
  - Ótima distribuição das chaves

### ❌ Pior combinação:
- **OpenAddressing + FoldingHash**
  - Muitas colisões
  - Altíssimo tempo de busca
  - Crescimento exponencial do número de comparações

### 🎯 Aprendizados:
- Funções hash bem projetadas (como multiplicação) impactam fortemente o desempenho.
- Chaining é muito mais robusto em cenários de carga alta (α > 1).
- Sondagem linear degrada rapidamente com poucas colisões acumuladas.

---

## 💻 Tecnologias Utilizadas

- Java 17
- Execução via terminal (javac/java)
- pandas + matplotlib (para gráficos em `plot_hash_results.py`)
- IDE: VS Code

---

## 📁 Como Executar

```bash
javac -d bin br/edu/hashing/**/*.java
java -Xmx4g -cp bin br.edu.hashing.experiments.ExperimentRunner
Depois, use o script Python plot_hash_results.py para gerar os gráficos.
```
---

## 👨‍💼 Créditos
- Projeto desenvolvido por Misael Vicente, Otávio Augusto e Emmanuel Victorio.
- Disciplina: Resolução de Problemas Estruturados em Computação
- Engenharia de Software – PUCPR
---
---
## 🖼 Exemplos de Gráficos
- 📊 Gráfico 1 – Colisões na Inserção
![grafico1_colisoes_insercao](https://github.com/user-attachments/assets/c68fe19a-7880-4f3d-b8e9-311eb6aead55)
- ⏱️ Gráfico 2 – Tempo Médio de Busca
![grafico2_tempo_busca](https://github.com/user-attachments/assets/ca64e8da-6fff-4f2c-a661-9ac98fd241ee)
- 🔍 Gráfico 3 – Comparações Médias de Busca
![grafico3_comparacoes_busca](https://github.com/user-attachments/assets/d5818b1a-8102-4335-8a0d-400e9469f24e)

