package org.jbl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAllowedException;
import org.jbl.UserDomain.User;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

@ApplicationScoped
public class UserService {

   private final ConcurrentLinkedQueue<User> queue = new ConcurrentLinkedQueue<>();

   void addUser(User user) {
      if(user == null) {
         throw new NotAllowedException("User cannot be null");
      }
      queue.add(user);
   }

   Collection<User> getAllUsers() {
      return Collections.unmodifiableCollection(queue);
   }

   Collection<User> getUsersPaged(int page, int pageSize) {
      return queue.stream()
         .skip((page - 1L) * pageSize)
         .limit(pageSize)
         .toList();
   }

}