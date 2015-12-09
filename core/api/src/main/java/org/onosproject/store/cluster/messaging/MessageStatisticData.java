package org.onosproject.store.cluster.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cr on 15-12-9.
 */
public class MessageStatisticData {

    private Endpoint ep;

    private MessageSubject messageSubject;

    private Map<MessageSubject, Long> countStatistic;

    private Map<MessageSubject, Long> lengthStatistic;

    public MessageStatisticData(Endpoint ep) {
        this.ep = ep;
        countStatistic = new HashMap<MessageSubject, Long>();
        lengthStatistic = new HashMap<MessageSubject, Long>();
    }

    public MessageStatisticData() {
        countStatistic = new HashMap<MessageSubject, Long>();
        lengthStatistic = new HashMap<MessageSubject, Long>();
    }

    public Endpoint getEp() {
        return ep;
    }

    public void setEp(Endpoint ep) {
        this.ep = ep;
    }

    public MessageSubject getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(MessageSubject messageSubject) {
        this.messageSubject = messageSubject;
    }

    public Map<MessageSubject, Long> getCountStatistic() {
        return countStatistic;
    }

    public void setCountStatistic(Map<MessageSubject, Long> countStatistic) {
        this.countStatistic = countStatistic;
    }

    public Map<MessageSubject, Long> getLengthStatistic() {
        return lengthStatistic;
    }

    public void setLengthStatistic(Map<MessageSubject, Long> lengthStatistic) {
        this.lengthStatistic = lengthStatistic;
    }

    public void addMssageCount(MessageSubject subject) {
        Long count = countStatistic.get(subject);
        if (null == count) {
            count = 0L;
        }
        count++;
        countStatistic.put(subject, count);
    }

    public void addMessageLength(MessageSubject subject, long length) {
        Long l = lengthStatistic.get(subject);
        if (null == l) {
            l = 0L;
        }
        l += length;
        lengthStatistic.put(subject, l);
    }
}
