package org.onosproject.cli.net;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.store.cluster.messaging.ClusterCommunicationService;
import org.onosproject.store.cluster.messaging.Endpoint;
import org.onosproject.store.cluster.messaging.MessageSubject;

import java.util.Map;

/**
 * Created by paradise on 2015/11/19.
 */
@Command(scope = "onos",name="cclist",
    description = "List for this cluster node communication message statistics")
public class ClusterCommunicationCommand extends AbstractShellCommand{

    @Argument(index = 0, name = "command ", description = "start/stop/restart",
            required = false, multiValued = false)
    String command = null;

    private ClusterCommunicationService clusterCommunicationService;
    @Override
    protected void execute() {
        clusterCommunicationService = get(ClusterCommunicationService.class);
        if(null == clusterCommunicationService){
            print("获取服务失败！");
            return;

        }
        if(null != command){
            if(command.trim().equals("start")){
                clusterCommunicationService.startMessageStatistics();
                print("开始统计");
            }else if(command.trim().equals("stop")){
                clusterCommunicationService.stopMessageStatistics();
                print("停止统计");
            }else if(command.trim().equals("restart")){
                clusterCommunicationService.restartMessageStatistics();
                print("重新开始统计");
            }else{
                print("命令错误");
            }
            return;
        }
        print("获取ClusterCommunicationService成功，class的类型为：" + clusterCommunicationService.getClass());
        Map<Endpoint,Map<MessageSubject,Long>> receivedCountMap = clusterCommunicationService.getReceivedMessageCount();
        Map<Endpoint,Map<MessageSubject,Long>> receivedLengthMap = clusterCommunicationService.getReceivedMessageLength();
        Map<Endpoint,Map<MessageSubject,Long>> sendedCountMap = clusterCommunicationService.getSendedMessageCount();
        Map<Endpoint,Map<MessageSubject,Long>> sendedLengthMap = clusterCommunicationService.getSendedMessageLength();
        if(null != receivedCountMap){
            print("接收消息统计");
            for(Endpoint ep : receivedCountMap.keySet()){
                Map<MessageSubject,Long> messageCountMap = receivedCountMap.get(ep);
                Map<MessageSubject,Long> messageLengthMap = receivedLengthMap.get(ep);
                for(MessageSubject messageSubject : messageCountMap.keySet()){
                    Long messageLength = null;
                    if(null != messageLengthMap){
                        messageLength = messageLengthMap.get(messageSubject);
                    }
                    print(ep.toString() + "  " +  messageSubject + "  "
                            + messageCountMap.get(messageSubject) + "  " + messageLength) ;
                }
            }
        }else{
            print("接收消息统计为空");
        }
        if(null != sendedCountMap){
            print("发送消息统计");
            for(Endpoint ep : sendedCountMap.keySet()){
                Map<MessageSubject,Long> messageCountMap = sendedCountMap.get(ep);
                Map<MessageSubject,Long> messageLengthMap = sendedLengthMap.get(ep);
                for(MessageSubject messageSubject : messageCountMap.keySet()){
                    Long messageLength = null;
                    if(null != messageLengthMap){
                        messageLength = messageLengthMap.get(messageSubject);
                    }
                    print(ep.toString() + "  " + messageSubject + "  "
                            +  messageCountMap.get(messageSubject) + "  " + messageLength) ;
                }
            }
        }else{
            print("接收消息统计为空");
        }

    }
}
