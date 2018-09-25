package com.ailk.aus.demo;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaProducerTest {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", args[0]);
		props.put("group.id", args[1]);
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("auto.offset.reset", "earliest");
		props.put("enable.auto.commit", "false");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList(args[2]));
		ConsumerRecords<String, String> records = consumer.poll(10000);
		for (ConsumerRecord<String, String> record : records) {
			System.out.println(record.value());
		}
		consumer.close();

	}

}
