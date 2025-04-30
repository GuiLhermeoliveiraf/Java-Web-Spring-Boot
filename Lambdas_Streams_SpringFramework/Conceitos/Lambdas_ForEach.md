# O que é uma Lambda em Java?

## 🧠 Definição simples

**Lambda** é uma forma curta e expressiva de escrever funções anônimas (sem nome).  
Ela é muito usada quando queremos passar comportamento como argumento, principalmente ao trabalhar com **coleções** e **streams**.

## 🧾 Sintaxe básica

```java
(parâmetros) -> { corpo da função }
```

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
- Para cada temporada, pega a lista de episódios.
- Percorre todos os episódios dessa temporada com o índice `j`
- Imprime o título do episódio atual.

**Resumo:** estamos acessando duas listas: uma de temporadas, e dentro de cada uma, outra de episódios.

### ✅ Com lambda (estilo funcional)

```java
temporadas.forEach(t -> 
    t.episodios().forEach(e -> 
        System.out.println(e.titulo())
    )
);
```

- Para cada temporada `t`, execute a função dentro do `->`.
- Para cada episódio `e` dessa temporada, execute a função interna.
- Imprime o título do episódio atual.

**Resumo:** esse código faz exatamente a mesma coisa que o primeiro, mas de forma muito mais limpa e sem índices manuais.

---

✅ Esse exemplo mostra perfeitamente como migrar de um estilo **imperativo** (com `for`) para um estilo **funcional e moderno** com lambdas.  
O resultado é menos código, mais clareza e melhor adesão às práticas modernas.

---

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
}
);
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

| Vantagem                  | Descrição                                                |
|---------------------------|----------------------------------------------------------|
| ✅ Mais limpo             | Evita laços `for` longos e índices manuais              |
| ✅ Mais expressivo        | Fala a intenção do código: “para cada item, faça isso”  |
| ✅ Combinável com stream()| Pode ser encadeado com filtros, mapeamentos, etc.       |

---

## ⚠️ Dica importante

Use `forEach` somente para **ações finais**, como:

- imprimir
- salvar no banco
- logar algo

Evite usar `forEach` para **alterar dados** ou **retornar novos valores**.  
Para isso, prefira métodos como `map`, `filter`, `collect`, etc.
