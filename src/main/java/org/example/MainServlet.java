package org.example;

import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    PostRepository repository = new PostRepository();
    PostService service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      switch (method) {
        case "GET":
          if (path.equals("/api/posts")) {
            controller.all(resp);
            resp.getWriter().println("fsdfsdfsdf");
          } else if (path.matches("/api/posts/\\d+")) {
            long id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
            controller.getById(id, resp);
          }
          break;
        case "POST":
          if (path.equals("/api/posts")) {
            controller.save(req.getReader(), resp);
          }
          break;
        case "DELETE":
          if (path.matches("/api/posts/\\d+")) {
            long id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
            controller.removeById(id, resp);
          }
          break;
        default:
          resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
