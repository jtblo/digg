package org.jbl;

import java.util.Collection;

public class UserDomain {
   public record User(
      String name,
      String email,
      String address,
      String telephone
   ) {}

   public record UserRequest(User user) {}

   public record UserResponse(Collection<User> users) {}
}
