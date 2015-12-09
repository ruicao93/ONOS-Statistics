package org.onosproject.cli.net;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.store.cluster.messaging.ClusterMessageStatisticService;

/**
 * Created by cr on 15-12-9.
 */
@Command(scope = "onos", name = "cclist",
        description = "Cluster message statistics")
public class ClusterMessageStatisticCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "operation", description = "operation",
            required = false, multiValued = false)
    String operation = null;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected ClusterMessageStatisticService clusterMessageStatisticService;

    @Override
    protected void execute() {
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
                resultList();

        }
        return;
    }

    private void resultList() {
        print("cclist start working,arg:%s", operation);
    }
}
