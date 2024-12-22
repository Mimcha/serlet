package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong counter = new AtomicLong();

  // Константы
  private static final long POST_ID_NOT_SET = 0;
  private static final String POST_NOT_FOUND_MESSAGE = "Пост с id %d не найден.";

  public List<Post> all() {
    return List.copyOf(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == POST_ID_NOT_SET) {
      // Создание нового поста
      long newId = counter.incrementAndGet(); // Увеличиваем счетчик
      post.setId(newId); // Присваиваем новый id
      posts.put(newId, post); // Сохраняем пост в репозитории
    } else {
      // Обновление существующего поста
      if (posts.containsKey(post.getId())) {
        posts.put(post.getId(), post); // Обновляем пост
      } else {
        // Если поста с таким id не существует, выбрасываем исключение
        throw new NotFoundException(String.format(POST_NOT_FOUND_MESSAGE, post.getId()));
      }
    }
    return post; // Возвращаем сохраненный или обновленный пост
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}
