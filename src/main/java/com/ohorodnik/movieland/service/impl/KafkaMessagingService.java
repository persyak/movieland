package com.ohorodnik.movieland.service.impl;

import com.ohorodnik.movieland.dto.events.MovieEventDto;
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

    private final KafkaTemplate<Integer, MovieEventDto> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Override
    public CompletableFuture<SendResult<Integer, MovieEventDto>> sendMessage(MovieEventDto movieEventDto) {
        Integer key = movieEventDto.getMovieEventId();

        ProducerRecord<Integer, MovieEventDto> producerRecord = buildProducerRecord(key, movieEventDto);

        CompletableFuture<SendResult<Integer, MovieEventDto>> send
                = kafkaTemplate.send(producerRecord);

        return send.whenComplete((result, ex) -> {
            if (ex != null) {
                handleError(producerRecord, ex);
            } else {
                handleSuccess(key, movieEventDto, result);
            }
        });
    }

    private ProducerRecord<Integer, MovieEventDto> buildProducerRecord(Integer key, MovieEventDto movieEventDto) {
        List<Header>  recordHeaders = List.of(new RecordHeader("event-source", "movieland".getBytes()));

        return new ProducerRecord<>(topic, null, key, movieEventDto, recordHeaders);
    }

    private void handleError(ProducerRecord<Integer, MovieEventDto> producerRecord, Throwable ex) {
        log.error("Error sending the message with key: {}, value: {}, the exception is {} ",
                producerRecord.key(), producerRecord.value(), ex.getMessage(), ex);
    }

    private void handleSuccess(Integer key, MovieEventDto movieEventDto, SendResult<Integer, MovieEventDto> result) {
        log.info("Message sent successfully with key: {}, value: {}, partition: {}, offset: {}",
                key,
                movieEventDto,
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
    }
}
