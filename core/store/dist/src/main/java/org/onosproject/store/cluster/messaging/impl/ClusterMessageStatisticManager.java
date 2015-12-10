package org.onosproject.store.cluster.messaging.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.onlab.netty.InternalMessage;
import org.onosproject.store.cluster.messaging.ClusterMessageStatisticService;
import org.onosproject.store.cluster.messaging.Endpoint;
import org.onosproject.store.cluster.messaging.MessageStatisticData;
import org.onosproject.store.cluster.messaging.MessageSubject;
import org.onlab.packet.IpAddress;
import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cr on 15-12-9.
 */
@Component(immediate = true, enabled = true)
@Service
public class ClusterMessageStatisticManager implements ClusterMessageStatisticService {

    private boolean statisticFlag = false;

    private Map<Endpoint, MessageStatisticData> msdMap = new HashMap<Endpoint, MessageStatisticData>();

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Activate
    public void activate() {
        start();
    }

    @Deactivate
    public void deactivate() {
        log.info("Stopped");
    }

    @Override
    public void start() {
        statisticFlag = true;
        log.info("start cluster message statistic");
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
    public Map<Endpoint, MessageStatisticData>  getMessageStatisticResult() {
        return msdMap;
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
