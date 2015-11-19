package org.onosproject.store.cluster.messaging.impl;

import org.onosproject.cluster.NodeId;
import org.onosproject.store.cluster.messaging.ClusterMessage;
import org.onosproject.store.cluster.messaging.Endpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paradise on 2015/11/19.
 */
public class ClusterMessageStatistician {

        private Map<Endpoint,Long> receiveCount;
        private Map<Endpoint,Long> sendCount;
        private Map<Endpoint,Long> receiveLength;
        private Map<Endpoint,Long> sendLength;
        private NodeId  localNodeId;

        private ClusterCommunicationManager clusterCommunicationManager;

        public ClusterMessageStatistician(ClusterCommunicationManager clusterCommunicationManager){
            receiveCount = new HashMap<Endpoint,Long>();
            sendCount = new HashMap<Endpoint,Long>();
            receiveLength = new HashMap<Endpoint,Long>();
            sendLength = new HashMap<Endpoint,Long>();
            this.clusterCommunicationManager = clusterCommunicationManager;
            this.localNodeId = clusterCommunicationManager.getLocalNodeId();
        }

        public void handleSendedMessage(Endpoint ep, String type, byte[] payload){
            //sendCount++
            Long sc = sendCount.get(ep);
            if(null == sc){
                sendCount.put(ep,1L);
            }else{
                sendCount.put(ep,++sc);
            }
            //sendLength++
//            Long sl = sendLength.get(receivedNodeId);
//            Long messageLength = Long.valueOf(message.getBytes().length);
//            if(null == sl){
//                sendLength.put(receivedNodeId,messageLength);
//            }else{
//                sendLength.put(receivedNodeId,sl + messageLength);
//            }
        }

        public void handleReceivedMessage(ClusterMessage message){
            NodeId sendNodeId = message.sender();
            Endpoint ep = clusterCommunicationManager.getEndPointByNodeId(sendNodeId);
            //receiveCount++
            Long rc = receiveCount.get(ep);
            if(null == rc){
                receiveCount.put(ep,1L);
            }else{
                receiveCount.put(ep,++rc);
            }
            //receiveLength++
//            Long rl = receiveLength.get(sendNodeId);
//            Long messageLength = Long.valueOf(message.getBytes().length);
//            if(null == rl){
//                receiveLength.put(sendNodeId,messageLength);
//            }else{
//                receiveLength.put(sendNodeId,rl + messageLength);
//            }
        }

        public Map<Endpoint,Long> getReceivedMessageCount(){
            return receiveCount;
        }

        public Map<Endpoint,Long> getSendedMessageCount(){
            return sendCount;
        }
}
