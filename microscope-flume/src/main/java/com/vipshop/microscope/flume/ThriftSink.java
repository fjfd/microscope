package com.vipshop.microscope.flume;

import com.vipshop.microscope.common.cons.Constants;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.ThriftCategory;
import com.vipshop.microscope.thrift.ThriftClient;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

import java.util.Arrays;

public class ThriftSink extends AbstractSink implements Configurable {

    /**
     * use thrift client send {@code LogEntry}
     */
    private final ThriftClient client = new ThriftClient("10.101.2.98",
            9410,
            3000,
            ThriftCategory.THREAD_SELECTOR);

    private String myProp;

    @Override
    public void configure(Context context) {
        String myProp = context.getString("myProp", "defaultValue");

        // Process the myProp value (e.g. validation)

        // Store myProp for later retrieval by process() method
        this.myProp = myProp;
    }

    @Override
    public void start() {
        // Initialize the connection to the external repository (e.g. HDFS) that
        // this Sink will forward Events to ..
    }

    @Override
    public void stop() {
        // Disconnect from the external respository and do any
        // additional cleanup (e.g. releasing resources or nulling-out
        // field values) ..
    }

    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;

        // Start transaction
        Channel ch = getChannel();
        Transaction txn = ch.getTransaction();
        txn.begin();
        try {
            // This try clause includes whatever Channel operations you want to do

            Event event = ch.take();

            LogEntry logEntry = new LogEntry(Constants.LOG, event.getHeaders().toString() + event.getBody().toString());

            client.send(Arrays.asList(logEntry));

            // Send the Event to the external repository.
            // storeSomeData(e);
            txn.commit();
            status = Status.READY;
        } catch (Throwable t) {
            txn.rollback();

            // Log exception, handle individual exceptions as needed

            status = Status.BACKOFF;

            // re-throw all Errors
            if (t instanceof Error) {
                throw (Error) t;
            }
        } finally {
            txn.close();
        }
        return status;
    }
}