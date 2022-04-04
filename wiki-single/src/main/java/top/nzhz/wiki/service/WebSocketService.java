package top.nzhz.wiki.service;

import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.nzhz.wiki.websocket.WebSocketServer;

import javax.annotation.Resource;

@Service
public class WebSocketService {

    @Resource
    public WebSocketServer webSocketServer;

    @Async
    public void sendInfo(String info, String logID){
        MDC.put("LOG_ID",logID);
        webSocketServer.sentInfo(info);
    }
}
