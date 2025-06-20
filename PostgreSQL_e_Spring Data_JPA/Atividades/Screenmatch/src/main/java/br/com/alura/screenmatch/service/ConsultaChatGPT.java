package br.com.alura.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService("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");


        CompletionRequest requisicao = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}