package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
        private Scanner leitura = new Scanner(System.in);
        private final String ENDERECO = "https://www.omdbapi.com/?t=";
        private final String API_KEY = "&apikey=3651c9ec";
        private ConsumoApi consumo = new ConsumoApi();
        private ConverteDados conversor = new ConverteDados();

        public void exibiMenu() {
                System.out.println("Digite o nome da serie para buscar: ");
                var nomeSerie = leitura.nextLine();

                var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

                DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

                System.out.println(dados);

                System.out.println("\n");

                List<DadosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= dados.totalTemporadas(); i++) {
                        json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
                        DadosTemporada dadosTem = conversor.obterDados(json, DadosTemporada.class);
                        temporadas.add(dadosTem);
                }
                temporadas.forEach(System.out::println);

                System.out.println("\n");

                // for(int i = 0; i < dados.totalTemporadas(); i++){
                // List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
                // for(int j = 0; j < episodiosTemporada.size(); j++){
                // System.out.println(episodiosTemporada.get(j).titulo());
                // }
                // }
                System.out.println("Nome de todos os Episodios");
                temporadas.forEach(t -> t.episodios()
                                .forEach(e -> System.out.println(e.titulo())));

                System.out.println("\nTop 10 episodios");

                List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                                .flatMap(t -> t.episodios().stream())
                                .collect(Collectors.toList());

                dadosEpisodios.stream()
                                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                                .limit(10)
                                .map(e -> e.titulo().toUpperCase() + " Nota: " + e.avaliacao())
                                .forEach(System.out::println);

                System.out.println("\n");

                List<Episodio> episodios = temporadas.stream()
                                .flatMap(t -> t.episodios().stream()
                                                .map(d -> new Episodio(t.temporada(), d)))
                                .collect(Collectors.toList());

                episodios.forEach(System.out::println);

                System.out.println("Qual nome gostaria de buscar");
                var trechoTitulo = leitura.nextLine();

                Optional<Episodio> episodiosBuscado = episodios.stream()
                                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                                .findFirst();

                if (episodiosBuscado.isPresent()) {
                        System.out.println("Episodio encontrado!! ");
                        System.out.println("Temporada: " + episodiosBuscado.get().getTemporada());
                } else {
                        System.out.println("Episodio não encontrado");
                }

                System.out.println("Apartir de que ano voce quer ver?: ");
                var ano = leitura.nextInt();
                leitura.nextLine();

                LocalDate databusca = LocalDate.of(ano, 1, 1);

                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                episodios.stream()
                                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(databusca))
                                .forEach(e -> System.out.println(
                                                "Temporada: " + e.getTemporada() +
                                                                " Episodio " + e.getNumeroEpisodio() +
                                                                " Data Laçamento: "
                                                                + e.getDataLancamento().format(formatador)));

                Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                                .filter(e -> e.getAvaliacao() > 0.0)
                                .collect(Collectors.groupingBy(Episodio::getTemporada,
                                                Collectors.averagingDouble(Episodio::getAvaliacao)));
                System.out.println(avaliacoesPorTemporada);

                DoubleSummaryStatistics est = episodios.stream()
                                .filter(e -> e.getAvaliacao() > 0.0)
                                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
                System.out.println("Media: " + est.getAverage());
                System.out.println("Melhor Episodio: " + est.getMax());
                System.out.println("Pior Episodio: " + est.getMin());
                System.out.println("Total de Ep Avaliados: " + est.getCount());
        }
};