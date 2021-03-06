package fr.xebia.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JavaConsumer {

    public static void main(String[] args) {
        //configuration d'un consumer
        KafkaConsumer<String, String> consumer = createKafkaConsumer();

        // TODO 2_7 : shutdownhook
        final Thread mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Starting exit...");
                    // Note that shutdownhook runs in a separate thread, so the only thing we can safely do to a consumer is wake it up
                    consumer.wakeup();
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // TODO 2_2

    }

    private static KafkaConsumer<String, String> createKafkaConsumer() {
        // TODO 2_1
        Map<String, Object> props = new HashMap<>();


        // TODO 2_3




        // The amount of time a consumer can be out of contact with the brokers while still considered alive
        props.put("session.timeout.ms", "30000");

        // The expected time between heartbeats to the consumer coordinator
        //  must be set lower than session.timeout.ms
        props.put("heartbeat.interval.ms", "3000");

        // specify the minimum amount of data that it wants to receive from the broker when fetching records
        // If a Broker receives a request for records from a Consumer but the new records amount to fewer bytes than min.fetch.bytes,
        // the broker will wait until more messages are available before send‐ ing the records back to the consumer.
        props.put("fetch.min.bytes", "1");

        // By setting fetch.min.bytes you tell Kafka to wait until it has enough data to send before responding to the consumer
        // If you set fetch.max.wait.ms to 100ms and fetch.min.bytes to 1MB, Kafka will recieve a fetch request from the consumer
        // and will respond with data either when it has 1MB of data to return or after 100ms, whichever happens first.
        props.put("fetch.max.wait.ms", "500");

        // This property controls the maximum number of bytes the server will return per partition
        props.put("max.partition.fetch.bytes", "1048576");

        // This property controls the behavior of the consumer when it starts reading a partition
        // for which it doesn’t have a committed offset or if the committed offset it has is invalid
        // earliest : automatically reset the offset to the earliest offset
        // latest : automatically reset the offset to the latest offset
        // none : throw exception to the consumer if no previous offset is found for the consumer's group
        props.put("auto.offset.reset", "latest");

        // Will be used by the brokers to identify messages sent from the client. It is used in logging, metrics and for quotas.
        props.put("client.id", "");

        return new KafkaConsumer<>(props);
    }


    private static void display(ConsumerRecord<String, String> record) {
        System.out.println(String.format("topic = %s, partition: %d, offset: %d: %s", record.topic(), record.partition(),
                record.offset(), record.value()));
    }

    private static void manualCommit(KafkaConsumer<String, String> consumer) {
        // TODO 2_4
        try {

        } catch (CommitFailedException e) {
            e.printStackTrace();
        }
    }

    private static void manualAsynchronousCommit(KafkaConsumer<String, String> consumer) {
        // TODO 2_5

    }

    // TODO 2_6
    private static class HandleRebalance implements ConsumerRebalanceListener {
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

        }

        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

        }
    }


}
