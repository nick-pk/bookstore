package com.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IMediaService {
public List<String> fetchPosts(Long isbn) throws JsonProcessingException;
}
