package drpc;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;
import backtype.storm.utils.DRPCClient;

/**
 * DRPC example
 *  
 * @author Storm-Book
 *
 */
public class DRPCTopologyMain {

	public static void main(String[] args) {
		//Create the local drpc client/server
		LocalDRPC drpc = new LocalDRPC();

		//Create the drpc topology
		LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("add");
		builder.addBolt(new AdderBolt(),2);
		
		Config conf = new Config();
		conf.setDebug(true);
		
		//Create cluster and submit the topology
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("drpc-adder-topology", conf, builder.createLocalTopology(drpc));
		
		//Test the topology
		String result = drpc.execute("add", "1+-1");
		checkResult(result,0);
		result = drpc.execute("add", "1+1+5+10");
		
		//Finish and shutdown
		checkResult(result,17);
		cluster.shutdown();
		drpc.shutdown();
	}

	private static boolean checkResult(String result, int expected) {
		if(result != null && !result.equals("NULL")){
			if(Integer.parseInt(result) == expected){
				System.out.println("Add valid [result: "+result+"]");
				return true;
			}else{
				System.err.print("Invalid result ["+result+"]");
			}
		}else{
			System.err.println("There was an error running the drpc call");
		}
		return false;
	}
}
