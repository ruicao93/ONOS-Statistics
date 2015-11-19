package org.onosproject.cli.net;

import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.store.cluster.messaging.ClusterCommunicationService;
import org.onosproject.store.cluster.messaging.Endpoint;

import java.util.Map;

/**
 * Created by paradise on 2015/11/19.
 */
@Command(scope = "onos",name="cclist",
    description = "List for this cluster node communication message statistics")
public class ClusterCommunicationCommand extends AbstractShellCommand{

    private ClusterCommunicationService clusterCommunicationService;
    @Override
    protected void execute() {
        clusterCommunicationService = get(ClusterCommunicationService.class);
        if(null != clusterCommunicationService){
            print("获取ClusterCommunicationService成功，class的类型为：" + clusterCommunicationService.getClass());
            Map<Endpoint,Long> countMap = clusterCommunicationService.getReceivedMessageCount();
            if(null != countMap){
                for(Endpoint ep : countMap.keySet()){
                    print(ep.toString() + "  " + countMap.get(ep));
                }
            }else{
                print("countMap是空的，呜呜~~~");
            }

        }else{
            print("获取服务失败！");
        }
    }
}
