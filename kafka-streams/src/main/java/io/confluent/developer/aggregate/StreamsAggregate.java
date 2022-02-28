package io.confluent.developer.aggregate;

import io.confluent.developer.StreamsUtils;
import io.confluent.developer.avro.ElectronicOrder;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class StreamsAggregate {

    public static void main(String[] args) throws IOException {

        final Properties streamsProps = StreamsUtils.loadProperties();
        streamsProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "aggregate-streams");

        StreamsBuilder builder = new StreamsBuilder();
        final String inputTopic = streamsProps.getProperty("aggregate.input.topic");
        final String outputTopic = streamsProps.getProperty("aggregate.output.topic");
        final Map<String, Object> configMap = StreamsUtils.propertiesToMap(streamsProps);

        final SpecificAvroSerde<ElectronicOrder> electronicSerde =
                StreamsUtils.getSpecificAvroSerde(configMap);

        final KStream<String, ElectronicOrder> electronicStream = null;
              // Using the StreamsBuilder from above, create a KStream with an input-topic
              // and a Consumed instance with the correct
              // Serdes for the key, Serdes.String() and the value electronicSerde created above

              // To view the key-value records coming into the application consider
              //  adding this statement to the KStream .peek((key, value) -> System.out.println("Incoming record - key " +key +" value " + value));

              // Now take the electronicStream object, group by key and perform an aggregation
              // Don't forget to convert the KTable returned by the aggregate call back to a KStream using the toStream method
              electronicStream.groupByKey().aggregate(null, null);

              // To view the results of the aggregation consider
              // right after the toStream() method .peek((key, value) -> System.out.println("Outgoing record - key " +key +" value " + value))

              // Finally write the results to an output topic
              // using a KStream.to method with a Produced config object
              // and the appropriate Serdes for the key Serdes.String()  and the value Serdes.Long()

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsProps);
        TopicLoader.runProducer();
        kafkaStreams.start();
    }


}
