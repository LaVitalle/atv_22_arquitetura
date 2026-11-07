# Atividade 022 - Padrão Iterator

Refatoração do sistema de playlist musical utilizando o padrão **Iterator**, incluindo o desafio adicional do percurso em ordem inversa.

## Estrutura

| Classe | Papel |
|---|---|
| `SongCollection` | Agregado: abstração com `createIterator()` |
| `Playlist` | Agregado concreto: guarda as músicas, rejeita músicas nulas e cria os iteradores |
| `SongIterator` | Iterador: `hasNext()` e `next()` |
| `PlaylistIterator` | Iterador concreto (classe interna privada da `Playlist`): percorre na ordem de inclusão |
| `ReversePlaylistIterator` | Iterador concreto do desafio adicional: percorre da última para a primeira música |
| `Main` | Cliente: percorre as músicas usando só `hasNext()` e `next()` |

O método `getSongs()` foi removido, então o cliente não tem mais acesso à lista interna.

## Como executar

```bash
javac -d out src/*.java
java -cp out Main
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Iterator?**

A playlist entregava a própria lista para o cliente, que controlava os índices para percorrer as músicas. Com isso, o cliente dependia de como a playlist guardava os dados e podia até alterar a lista por fora. Agora o cliente só pede um iterador e chama `hasNext()` e `next()`, sem ver a lista nem lidar com índices.

**b) Quais abstrações e classes representam o agregado, o agregado concreto, o iterador e o iterador concreto na sua implementação?**

- Agregado: `SongCollection`
- Agregado concreto: `Playlist`
- Iterador: `SongIterator`
- Iteradores concretos: `PlaylistIterator` e `ReversePlaylistIterator`

**c) Por que a posição deve pertencer a cada iterador, em vez de ser armazenada na própria playlist?**

Para permitir vários percursos ao mesmo tempo, cada um no seu ponto. Se a posição ficasse na playlist, todos os percursos compartilhariam o mesmo índice, e avançar um afetaria os outros. Na demonstração, o primeiro iterador avança duas vezes e o segundo ainda começa pela primeira música.

**d) Por que `hasNext()` não deve avançar o percurso? Qual é a importância de definir o comportamento de `next()` após o último elemento?**

`hasNext()` é só uma pergunta: pode ser chamado várias vezes sem pular músicas. Se ele avançasse, o cliente perderia elementos sem perceber. Definir que `next()` lança `NoSuchElementException` depois do fim deixa o comportamento previsível e mostra o erro na hora, em vez de devolver `null` ou um valor errado que causaria problemas mais adiante.

**e) Que decisões seriam necessárias para permitir alterações na playlist durante um percurso? Como percorrer uma cópia dos elementos difere de detectar modificações e interromper a iteração?**

Seria preciso decidir o que o percurso deve ver quando a lista muda: as músicas novas ou só as que existiam no início. Uma opção é o iterador percorrer uma cópia da lista feita na sua criação. Assim ele nunca quebra, mas não enxerga as alterações e gasta memória com a cópia. A outra é detectar modificações (por exemplo, com um contador de alterações, como no `ArrayList`) e lançar uma exceção se a lista mudar durante o percurso. Essa opção não copia nada, mas interrompe a iteração, e o cliente precisa tratar o erro.
