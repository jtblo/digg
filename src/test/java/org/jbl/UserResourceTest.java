package org.jbl;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.jbl.UserDomain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
class UserResourceTest {
   private final List<User> usersList = List.of(
      new User("John", "john@doe.com", "Testvägen 1, 12345 Farsta", "+4655555"),
      new User("John", "john@doe.com", "Testvägen 2, 12346 Farsta", "+4655556"),
      new User("John", "john@doe.com", "Testvägen 3, 12347 Farsta", "+4655557"),
      new User("John", "john@doe.com", "Testvägen 4, 12348 Farsta", "+4655558"),
      new User("John", "john@doe.com", "Testvägen 5, 12349 Farsta", "+4655559"),
      new User("John", "john@doe.com", "Testvägen 6, 12350 Farsta", "+4655560")
   );

   @InjectMock
   UserService userServiceMock;

   @Inject
   UserResource userResource;

   @BeforeEach
   void setUp() {
      Mockito.when(userServiceMock.getAllUsers()).thenReturn(usersList);
      Mockito.when(userServiceMock.getUsersPaged(1, 2)).thenReturn(usersList.subList(0,2));
      Mockito.when(userServiceMock.getUsersPaged(2, 2)).thenReturn(usersList.subList(2,4));
   }

   @Test
   void getAllUsers() {
      var users = userResource.getAllUsers().users();
      assertThat(users, hasSize(6));
      usersList.forEach(u -> assertThat(users, hasItem(u)));
   }

   @Test
   void getFirstPagedUsers() {
      var users = userResource.getUsersPaged(1, 2).users();
      assertThat(users, hasSize(2));
      usersList.stream()
         .limit(2)
         .forEach(u -> assertThat(users, hasItem(u)));
   }

   @Test
   void getSecondPagedUsers() {
      var users = userResource.getUsersPaged(2, 2).users();
      assertThat(users, hasSize(2));
      usersList.stream()
         .skip(2)
         .limit(2)
         .forEach(u -> assertThat(users, hasItem(u)));
   }

}