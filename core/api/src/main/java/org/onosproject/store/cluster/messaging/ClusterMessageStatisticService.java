package org.onosproject.store.cluster.messaging;

import java.util.List;

/**
 * Created by cr on 15-12-9.
 */
public interface ClusterMessageStatisticService {

    /**
     *  start message statistics.
     */
    public void start();

    /**
     * stop message statistics.
     */
    public void stop();

    /**
     * restart message statistics.
     */
    public void restart();

    /**
     * handle message and count.
     * @param mesasge
     */
    public void handleMessage(Endpoint receiverEp, Object mesasge);

    /**
     * the result of message statistics.
     * @return
     */
    public List<MessageStatisticData> getMessageStatisticResult();
}
