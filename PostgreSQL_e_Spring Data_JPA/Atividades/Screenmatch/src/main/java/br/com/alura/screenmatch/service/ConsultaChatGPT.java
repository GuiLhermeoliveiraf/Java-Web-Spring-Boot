package br.com.alura.screenmatch.service;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;

import java.util.List;

public class ConsultaChatGPT {

    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService(
                "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");

        ChatMessage mensagemUsuario = new ChatMessage("user", "Traduza para o português: " + texto);

        ChatCompletionRequest requisicao = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(mensagemUsuario))
                .temperature(0.7)
                .maxTokens(1000)
                .build();

        return service.createChatCompletion(requisicao)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent()
                .trim();
    }
}
