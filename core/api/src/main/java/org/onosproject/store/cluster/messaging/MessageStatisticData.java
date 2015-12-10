package org.onosproject.store.cluster.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cr on 15-12-9.
 */
public class MessageStatisticData {

    private Endpoint ep;

    private MessageSubject messageSubject;

    private Map<MessageSubject, Map<String, Long>> statisticdData;

    private Map<MessageSubject, Long> lengthStatistic;

    public static final String COUNT = "count";

    public static final String LENGTH = "length";

    public MessageStatisticData(Endpoint ep) {
        this.ep = ep;
        statisticdData = new HashMap<MessageSubject, Map<String, Long>>();
        Map<String, Long> data = new HashMap<String, Long>();
        data.put(COUNT, 0L);
        data.put(LENGTH, 0L);
    }

    public MessageStatisticData() {
        statisticdData = new HashMap<MessageSubject, Map<String, Long>>();
        Map<String, Long> data = new HashMap<String, Long>();
        data.put(COUNT, 0L);
        data.put(LENGTH, 0L);
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

    public Map<MessageSubject, Long> getLengthStatistic() {
        return lengthStatistic;
    }

    public void setLengthStatistic(Map<MessageSubject, Long> lengthStatistic) {
        this.lengthStatistic = lengthStatistic;
    }

    public void addMssageCount(MessageSubject subject) {
        setCount(subject, getCount(subject) + 1);
    }

    public void addMessageLength(MessageSubject subject, long length) {
        Long l = getLength(subject);
        if (null == l) {
            l = 0L;
        }
        l += length;
        setLength(subject, l);
    }

    public long getCount(MessageSubject subject) {
        long count = 0L;
        Map<String, Long> dataMap = statisticdData.get(subject);
        if (null == dataMap) {
            count = 0L;
            dataMap = new HashMap<String, Long>();
            dataMap.put(COUNT, count);
            dataMap.put(LENGTH, count);
            statisticdData.put(subject, dataMap);
        }
        count = dataMap.get(COUNT);
        return count;
    }
    public long getLength(MessageSubject subject) {
        long length = 0L;
        Map<String, Long> dataMap = statisticdData.get(subject);
        if (null == dataMap) {
            length = 0L;
            dataMap = new HashMap<String, Long>();
            dataMap.put(COUNT, length);
            dataMap.put(LENGTH, length);
            statisticdData.put(subject, dataMap);
        }
        length = dataMap.get(LENGTH);
        return length;
    }

    private void setCount(MessageSubject subject, Long count) {
        Map<String, Long> dataMap = statisticdData.get(subject);
        if (null == dataMap) {
            dataMap = new HashMap<String, Long>();
            dataMap.put(COUNT, 0L);
            dataMap.put(LENGTH, 0L);
            statisticdData.put(subject, dataMap);
        }
        dataMap.put(COUNT, count);
    }

    private void setLength(MessageSubject subject, Long length) {
        Map<String, Long> dataMap = statisticdData.get(subject);
        if (null == dataMap) {
            dataMap = new HashMap<String, Long>();
            dataMap.put(COUNT, 0L);
            dataMap.put(LENGTH, 0L);
            statisticdData.put(subject, dataMap);
        }
        dataMap.put(LENGTH, length);
    }

    public Map<MessageSubject, Map<String, Long>> getStatisticdData() {
        return statisticdData;
    }


    public List<MessageSubject> getMessageSubjectList() {
        List<MessageSubject> list = new ArrayList<MessageSubject>();
        for (MessageSubject subject : getStatisticdData().keySet()) {
            list.add(subject);
        }
        return list;
    }
}
