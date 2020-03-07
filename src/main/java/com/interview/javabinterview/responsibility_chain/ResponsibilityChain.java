package com.interview.javabinterview.responsibility_chain;

/**
 * 责任链设计
 *
 * @author Ju Baoquan
 * Created at  2020/3/6
 */
public class ResponsibilityChain {

    private static IRequestProcessor iRequestProcessor;

    public  void start() {
        PrintProcessor printProcessor = new PrintProcessor();
        printProcessor.start();
        SaveProcessor saveProcessor = new SaveProcessor(printProcessor);
        saveProcessor.start();
        iRequestProcessor = new PreProcessor(saveProcessor);
        ((PreProcessor) iRequestProcessor).start();
    }

    public static void main(String[] args) {
        ResponsibilityChain responsibilityChain = new ResponsibilityChain();
        responsibilityChain.start();
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setName("jubaoquan");
        iRequestProcessor.process(requestDTO);
    }
}
