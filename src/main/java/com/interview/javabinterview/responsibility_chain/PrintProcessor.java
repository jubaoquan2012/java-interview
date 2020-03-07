package com.interview.javabinterview.responsibility_chain;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/3/6
 */
public class PrintProcessor extends Thread implements IRequestProcessor {

    private LinkedBlockingDeque<RequestDTO> requests = new LinkedBlockingDeque<>();

    private IRequestProcessor nextProcessor;

    private volatile boolean isFinish = false;

    public PrintProcessor() {

    }

    @Override
    public void run() {
        while (!isFinish) {
            try {
                RequestDTO request = requests.take();
                System.out.println("printProcessor:" + request.getName());
                if (nextProcessor != null) {
                    nextProcessor.process(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(RequestDTO request) {
        requests.add(request);
    }
}
