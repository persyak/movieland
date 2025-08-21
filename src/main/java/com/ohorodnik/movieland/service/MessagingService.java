package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.events.MovieEventDto;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public interface MessagingService {

    CompletableFuture<SendResult<Integer, MovieEventDto>> sendMessage(MovieEventDto movieEventDto);
}
