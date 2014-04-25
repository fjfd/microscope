package countword.bolts;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordNormalizer extends BaseRichBolt {

	private OutputCollector collector;
	int numCounterTasks=0;
	public void cleanup() {}

	/**
	 * The bolt will receive the line from the
	 * words file and process it to Normalize this line
	 * 
	 * The normalize will be put the words in lower case
	 * and split the line to get all words in this 
	 */
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] words = sentence.split(" ");
        for(String word : words){
            word = word.trim();
            if(!word.isEmpty()){
                word = word.toLowerCase();
                collector.emit(new Values(word));
            }
        }
        // Acknowledge the tuple
        collector.ack(input);
    }
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.numCounterTasks = context.getComponentTasks("word-counter").size();
	}

	/**
	 * The bolt will only emit the field "word" 
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
