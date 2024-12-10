package com.example.admin_demo.util;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.*;
import com.example.admin_demo.config.*;
import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.List;

public class AiUtil {
    /**
     * 业务ID模版
     */
    private static final String REQUEST_ID_TEMPLATE = "teaAI12345678";
    /**
     * AI调用客户端
     */
    private static final ClientV4 CLIENT = new ClientV4.Builder(AiConfig.API_SECRET_KEY).build();
    /**
     * 调用AI接口(同步)
     *
     * @param codeContent 代码
     * @return AI返回的答案
     */
    public static String aiCaller(String codeContent){
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage1 = new ChatMessage(ChatMessageRole.SYSTEM.value(), "\"你是一位智能编程助手。" +
                "你会为用户回答关于编程、代码、计算机方面的任何问题，并提供格式规范、可以执行、准确安全的代码。" +
                "任务：请检测以下代码中有没有威胁评测系统的不安全的代码，例如系统操作命令。如果有请告诉我字符串“True”，如果没有请告诉我“False”，只需要告诉我这一个字符串即可，无需其他信息！");
        ChatMessage chatMessage2 = new ChatMessage(ChatMessageRole.USER.value(), "需要检验的代码为\n"+codeContent);
        messages.add(chatMessage1);
        messages.add(chatMessage2);
        String requestId = String.format(REQUEST_ID_TEMPLATE, codeContent);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(AiConfig.MODEL_NAME)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .requestId(requestId)
                .build();
        ModelApiResponse invokeModelApiResp = CLIENT.invokeModelApi(chatCompletionRequest);
        return invokeModelApiResp.getData().getChoices().get(0).getMessage().getContent().toString();
    }

    /**
     * 调用AI接口(SSE)
     */
    public static Flowable<ModelData> aiCallerFlow(String codeContent) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage1 = new ChatMessage(ChatMessageRole.SYSTEM.value(), "\"你是一位智能编程助手，你叫CodeGeeX。" +
                "你会为用户回答关于编程、代码、计算机方面的任何问题，并提供格式规范、可以执行、准确安全的代码。" +
                "任务：请对输入代码中威胁系统安全的代码进行格式规范的注释，包含多行注释和单行注释，请只返回注释后的代码。");
        ChatMessage chatMessage2 = new ChatMessage(ChatMessageRole.USER.value(), "需要检验的代码为\n"+codeContent);
        messages.add(chatMessage1);
        messages.add(chatMessage2);
        String requestId = String.format(REQUEST_ID_TEMPLATE, System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(AiConfig.MODEL_NAME).
                stream(Boolean.TRUE).
                invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .requestId(requestId)
                .build();
        ModelApiResponse invokeModelApiResp = CLIENT.invokeModelApi(chatCompletionRequest);
        return invokeModelApiResp.getFlowable();
    }
}
