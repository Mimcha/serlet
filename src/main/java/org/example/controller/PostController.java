package org.example.controller;

import com.google.gson.Gson;
import org.example.model.Post;
import org.example.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  private static final String APPLICATION_JSON = "application/json";
  private final PostService service;
  private final Gson gson = new Gson();

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    response.getWriter().print(gson.toJson(service.all()));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    Post post = service.getById(id);
    response.getWriter().print(gson.toJson(post));
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    Post post = gson.fromJson(body, Post.class);
    Post savedPost = service.save(post);
    response.getWriter().print(gson.toJson(savedPost));
  }

  public void removeById(long id, HttpServletResponse response) throws IOException {
    service.removeById(id);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }
}
