package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.MovieDetailsDto;
import com.ohorodnik.movieland.service.MessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessagingService implements MessagingService {

    private final KafkaTemplate<Integer, MovieDetailsDto> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Override
    public CompletableFuture<SendResult<Integer, MovieDetailsDto>> sendMessage(MovieDetailsDto movieDetailsDto) {
        Integer key = movieDetailsDto.getId();

        ProducerRecord<Integer, MovieDetailsDto> producerRecord = buildProducerRecord(key, movieDetailsDto);

        CompletableFuture<SendResult<Integer, MovieDetailsDto>> send
                = kafkaTemplate.send(producerRecord);

        return send.whenComplete((result, ex) -> {
            if (ex != null) {
                handleError(producerRecord, ex);
            } else {
                handleSuccess(key, movieDetailsDto, result);
            }
        });
    }

    private ProducerRecord<Integer, MovieDetailsDto> buildProducerRecord(Integer key, MovieDetailsDto movieDetailsDto) {
        List<Header>  recordHeaders = List.of(new RecordHeader("event-source", "movieland".getBytes()));

        return new ProducerRecord<>(topic, null, key, movieDetailsDto, recordHeaders);
    }

    private void handleError(ProducerRecord<Integer, MovieDetailsDto> producerRecord, Throwable ex) {
        log.error("Error sending the message with key: {}, value: {}, the exception is {} ",
                producerRecord.key(), producerRecord.value(), ex.getMessage(), ex);
    }

    private void handleSuccess(Integer key, MovieDetailsDto movieDetailsDto, SendResult<Integer, MovieDetailsDto> result) {
        log.info("Message sent successfully with key: {}, value: {}, partition: {}, offset: {}",
                key,
                movieDetailsDto,
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
    }
}
