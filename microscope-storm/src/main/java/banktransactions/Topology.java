package banktransactions;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

/**
 * To run this topology you should execute this main as: 
 * java -cp theGeneratedJar.jar banktransactions.Topology
 *
 * @author StormBook
 *
 */
public class Topology {

	public static void main(String[] args) throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("transactions-spout", new TransactionsSpouts());
		builder.setBolt("random-failure-bolt", new RandomFailureBolt()).
			shuffleGrouping("transactions-spout");
		
		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();
		conf.setDebug(true);
		cluster.submitTopology("transactions-test", conf, builder.createTopology());
		while(true){
			//Will wait for a fail
			Thread.sleep(1000);
		}
	}
}
