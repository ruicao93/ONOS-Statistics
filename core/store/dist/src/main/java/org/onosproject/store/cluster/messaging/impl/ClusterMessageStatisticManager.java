package org.onosproject.store.cluster.messaging.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.onlab.netty.InternalMessage;
import org.onosproject.store.cluster.messaging.ClusterMessageStatisticService;
import org.onosproject.store.cluster.messaging.Endpoint;
import org.onosproject.store.cluster.messaging.MessageStatisticData;
import org.onosproject.store.cluster.messaging.MessageSubject;
import org.onlab.packet.IpAddress;
import com.google.common.base.Charsets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cr on 15-12-9.
 */
@Component(immediate = true, enabled = true)
@Service
public class ClusterMessageStatisticManager implements ClusterMessageStatisticService {

    private boolean statisticFlag = false;

    private Map<Endpoint, MessageStatisticData> msdMap = new HashMap<Endpoint, MessageStatisticData>();


    @Activate
    public void activate() {
        start();
    }

    @Override
    public void start() {
        statisticFlag = true;
    }

    @Override
    public void stop() {
        statisticFlag = false;
    }

    @Override
    public void restart() {
        clearData();
        start();
    }

    @Override
    public void handleMessage(Endpoint receiverEp, Object message) {
        if (!statisticFlag) {
            return;
        }
        InternalMessage m = (InternalMessage) message;

        MessageStatisticData msd = msdMap.get(receiverEp);
        if (null == msd) {
            msd = new MessageStatisticData(receiverEp);
            msdMap.put(receiverEp, msd);
        }
        MessageSubject messageSubject = new MessageSubject(m.type());
        // message count ++
        msd.addMssageCount(messageSubject);
        //message length ++
        long length = calculateMessageLength(m);
        msd.addMessageLength(messageSubject, length);
    }

    @Override
    public List<MessageStatisticData> getMessageStatisticResult() {
        return null;
    }

    private void clearData() {
        msdMap = new HashMap<Endpoint, MessageStatisticData>();
    }

    private long calculateMessageLength(InternalMessage message) {
        long length = 0;
        if (null == message) {
            return length;
        }
        //messageId length
        length += Long.BYTES;
        Endpoint sender = message.sender();
        //IpAddress length
        IpAddress senderIp = sender.host();
        length += 1;
        length += senderIp.toOctets().length;

        //sender port length
        length += Integer.BYTES;

        byte[] messageTypeBytes = message.type().getBytes(Charsets.UTF_8);

        //length of message type
        length += messageTypeBytes.length;

        //message type bytes
        length += messageTypeBytes.length;

        byte[] payload = message.payload();

        //payload length
        length += Integer.BYTES;
        length += payload.length;
        return length;
    }
}
