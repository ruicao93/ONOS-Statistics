package org.onosproject.cli.net;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.store.cluster.messaging.ClusterMessageStatisticService;
import org.onosproject.store.cluster.messaging.Endpoint;
import org.onosproject.store.cluster.messaging.MessageStatisticData;
import org.onosproject.store.cluster.messaging.MessageSubject;

import java.util.Map;

/**
 * Created by cr on 15-12-9.
 */
@Command(scope = "onos", name = "cclist",
        description = "Cluster message statistics")
public class ClusterMessageStatisticCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "operation", description = "operation",
            required = false, multiValued = false)
    String operation = null;

    protected ClusterMessageStatisticService clusterMessageStatisticService;

    @Override
    protected void execute() {
        if (null == clusterMessageStatisticService) {
            clusterMessageStatisticService = getService(ClusterMessageStatisticService.class);
        }
        if (null == operation || operation.trim().isEmpty()) {
            resultList();
            return;
        }
        switch (operation) {
            case "start":
                clusterMessageStatisticService.start();
                print("start message statistic");
                break;
            case "stop":
                clusterMessageStatisticService.stop();
                print("stop message statistic");
                break;
            case "restart":
                clusterMessageStatisticService.restart();
                print("restart message statistic");
                break;
            default:
                print("argument not found,arg:%s", operation);

        }
        return;
    }

    private void resultList() {
        Map<Endpoint, MessageStatisticData> msd = clusterMessageStatisticService.getMessageStatisticResult();
        if (null == msd) {
            print("Data is empty");
            return;
        }
        print("Send message statistiacs:");
        for (Endpoint ep : msd.keySet()) {
            MessageStatisticData data = msd.get(ep);
            for (MessageSubject subject : data.getMessageSubjectList()) {
                print("%s %s %d %d", ep.toString(), subject.toString(), data.getCount(subject), data.getLength(subject));
            }
        }
    }
}
