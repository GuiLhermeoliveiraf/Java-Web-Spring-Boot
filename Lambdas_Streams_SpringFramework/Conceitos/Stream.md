# O que é Stream em Java?

## 🔍 Introdução

A interface `Stream<T>` foi introduzida no **Java 8** e representa uma **sequência de dados** (como uma lista), sobre a qual você pode aplicar **operações funcionais**, como:

- `filter` → filtrar elementos  
- `map` → transformar dados  
- `sorted` → ordenar  
- `limit` → limitar o resultado  
- `collect` → reunir os dados em uma lista, mapa, etc.

🟢 É como uma esteira de produção: você coloca dados de um lado, aplica transformações e, no final, coleta o resultado.

---

## 🧪 Exemplo usando o meu Principal.java

```java
List<DadosEpisodio> dadosEpisodios = temporadas.stream()
        .flatMap(t -> t.episodios().stream())
        .collect(Collectors.toList());
```

### O que acontece aqui?

1️⃣ `temporadas.stream()`  
- Transforma a lista de temporadas em um `Stream<DadosTemporada>`

2️⃣ `.flatMap(t -> t.episodios().stream())`  
- Para cada temporada `t`, pega os episódios e transforma em `Stream<DadosEpisodio>`  
- O `flatMap` **junta tudo em uma lista única** de episódios

3️⃣ `.collect(Collectors.toList())`  
- Coleta o resultado como `List<DadosEpisodio>`

🧠 Transformei uma **lista de listas** (temporadas → episódios) em uma única **lista linear** com todos os episódios.

---

## 🎬 Outro exemplo com Stream

```java
dadosEpisodios.stream()
    .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
    .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
    .limit(10)
    .map(e -> e.titulo().toUpperCase())
    .forEach(System.out::println);
```

### Etapas do Stream

| Operação     | Função                                      |
|--------------|---------------------------------------------|
| `stream()`   | Cria o fluxo de dados da lista              |
| `filter()`   | Remove episódios com avaliação "N/A"        |
| `sorted()`   | Ordena pela avaliação (descendente)         |
| `limit()`    | Pega os 10 primeiros                        |
| `map()`      | Transforma o título em maiúsculo            |
| `forEach()`  | Imprime cada título                         |

📌 Tudo isso **sem loops manuais**, apenas declarando o que deve ser feito.  
É **programação declarativa**.

---

## 🧠 Resumo dos principais métodos de Stream

| Método       | Descrição                                                       |
|--------------|------------------------------------------------------------------|
| `filter()`   | Filtra elementos com base em uma condição                       |
| `map()`      | Transforma cada item (ex: `String` → `Integer`)                 |
| `flatMap()`  | Junta várias listas em uma só                                   |
| `sorted()`   | Ordena os dados                                                 |
| `limit()`    | Limita o número de elementos                                    |
| `collect()`  | Recolhe os dados no final (em `List`, `Set`, `Map`, etc.)       |
| `forEach()`  | Executa algo para cada item (como imprimir)                     |
| `peek()`     | Inspeciona o item no meio do fluxo (usado para debug)           |

---

## 🚀 Por que usar Stream?

- Código mais **declarativo** e **legível**
- Evita `for` aninhados e `if` espalhados
- Facilita **operações encadeadas**
- Ideal para **filtrar, transformar e agrupar dados**
