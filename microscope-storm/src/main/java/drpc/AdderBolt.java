package drpc;

import java.security.InvalidParameterException;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class AdderBolt<String> extends BaseBasicBolt{

	private static final Object NULL = "NULL";
	private OutputCollector collector;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		//Parse the add expression
		String[] numbers = (String[]) input.getString(1).split("\\+");
		Integer added = 0;
		try{
			if(numbers.length<2){
				throw new InvalidParameterException("Should be at least 2 numbers");
			}
			for(String num : numbers){
				//Add each member
				added += Integer.parseInt((java.lang.String) num);
			}
		}catch(Exception e){
			//On error emit null
			collector.emit(new Values(input.getValue(0),NULL));
		}
		//Emit the result
		collector.emit(new Values(input.getValue(0),added));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("id","result"));
	}
}
