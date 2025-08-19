package com.ohorodnik.movieland.service;

import com.ohorodnik.movieland.dto.MovieDetailsDto;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public interface MessagingService {

    CompletableFuture<SendResult<Integer, MovieDetailsDto>> sendMessage(MovieDetailsDto movieDetailsDto);
}
