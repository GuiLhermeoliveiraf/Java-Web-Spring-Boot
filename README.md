# Java-Web-Spring-Boot

<br>



# O que é uma Lambda em Java?

## 🧠 Definição simples

**Lambda** é uma forma curta e expressiva de escrever funções anônimas (sem nome).  
Ela é muito usada quando queremos passar comportamento como argumento, principalmente ao trabalhar com **coleções** e **streams**.

---

## 🧾 Sintaxe básica

```java
(parâmetros) -> { corpo da função }
```

---

### ✅ Exemplo simples

```java
lista.forEach(item -> System.out.println(item));
```

Aqui:

- `item` é o **parâmetro**  
- `System.out.println(item)` é o que acontece com ele

**Não precisa criar uma classe nova nem implementar uma interface!**

---

## 🔁 Sem lambda vs Com lambda

### 🚫 Sem lambda (estilo imperativo)

```java
for (int i = 0; i < dados.totalTemporadas(); i++) {
    List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
    for (int j = 0; j < episodiosTemporada.size(); j++) {
        System.out.println(episodiosTemporada.get(j).titulo());
    }
}
```

- Percorre todas as temporadas, uma por uma, usando um índice `i`  
- Para cada temporada, pega a lista de episódios  
- Percorre todos os episódios dessa temporada com o índice `j`  
- Imprime o título do episódio atual

**Resumo:** estamos acessando duas listas: uma de temporadas, e dentro de cada uma, outra de episódios.

---

### ✅ Com lambda (estilo funcional)

```java
temporadas.forEach(t -> 
    t.episodios().forEach(e -> 
        System.out.println(e.titulo())
    )
);
```

- Para cada temporada `t`, execute a função dentro do `->`  
- Para cada episódio `e` dessa temporada, execute a função interna  
- Imprime o título do episódio atual

**Resumo:** esse código faz exatamente a mesma coisa que o primeiro, mas de forma muito mais limpa e sem índices manuais.

---

✅ Esse exemplo mostra perfeitamente como migrar de um estilo **imperativo** (com `for`) para um estilo **funcional e moderno** com lambdas.  
O resultado é menos código, mais clareza e melhor adesão às práticas modernas.

---

<br>


# O que é forEach em Java?

## 🔄 Definição

**forEach** é um método usado para iterar (ou percorrer) elementos de uma coleção — como listas ou arrays — de forma mais **moderna e funcional**.

Ele faz parte das interfaces:

- `Iterable` (desde o Java 8)  
- `Stream` (usado com `stream().forEach()`)

---

## 🧾 Como funciona?

### 🧬 Sintaxe

```java
colecao.forEach(elemento -> {
    // código que será executado para cada elemento
});
```

Você passa uma **função lambda** que será chamada para cada item da lista.  
Essa função recebe o item como parâmetro (ex: `elemento` ou `e`).

---

### ✅ Exemplo simples

```java
List<String> nomes = List.of("Ana", "Bruno", "Carlos");

nomes.forEach(nome -> System.out.println(nome));
```

#### Resultado:

```
Ana  
Bruno  
Carlos
```

---

### 💡 Exemplo prático com lambdas

```java
temporadas.forEach(t -> 
    t.episodios().forEach(e -> 
        System.out.println(e.titulo())
    )
);
```

- `temporadas.forEach(...)` percorre cada temporada  
- `t.episodios().forEach(...)` percorre cada episódio dessa temporada  
- `System.out.println(e.titulo())` imprime o título de cada episódio

---

## 🎯 Por que usar forEach?

| Vantagem                   | Descrição                                                |
|---------------------------|----------------------------------------------------------|
| ✅ Mais limpo             | Evita laços `for` longos e índices manuais              |
| ✅ Mais expressivo        | Fala a intenção do código: “para cada item, faça isso”  |
| ✅ Combinável com stream()| Pode ser encadeado com filtros, mapeamentos, etc.       |

---

<br>


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
