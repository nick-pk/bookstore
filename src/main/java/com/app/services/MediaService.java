package com.app.services;

import com.app.exceptions.MediaException;
import com.app.models.catchers.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaService implements IMediaService {
    @Value("${service.endpoint}")
    private String baseURL;
    public List<String> fetchPosts(Long isbn,String title) {
        RestTemplate template= new RestTemplate();
        ObjectMapper mapper=new ObjectMapper();
        List<Post> postList= null;
        try {
            String jsonArray=template.getForObject(baseURL, String.class);
            postList = mapper.readValue(jsonArray, new TypeReference<List<Post>>(){});
        } catch (Exception e) {
            throw new MediaException("Failed to retrieve Posts for Book " + isbn);
        }
        List<String> result=new ArrayList<>();
        for(Post post:postList){
            if(post.getTitle().contains(title) || post.getBody().contains(title)){
                result.add(post.getTitle());
            }
        }
        return result;
    }
}
