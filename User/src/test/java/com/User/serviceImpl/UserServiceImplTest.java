package com.User.serviceImpl;

import com.User.entity.User;
import com.User.repository.UserRepository;
import com.User.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    public UserServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        this.userServiceImpl =new UserServiceImpl(this.userRepository);
    }

    @Test
    void getAllUsers() {
        userServiceImpl.getAllUsers();
        verify(userRepository).findAll();
    }

    @Test
    void getUserByClientId() {
        userServiceImpl.getUserByClientId("IS103");
        verify(userRepository).findByClientId("IS103");
    }

    @Test
    void testUpdateUser() {
        // Arrange
        LocalDate date = LocalDate.of(2024, 8, 31);
        User existingUser = new User("IS104", "Old User", "olduser@gmail.com", "321", "1234567890",
                "Old City", date, new ArrayList<>());

        User updatedUser = new User("IS104", "Updated User", "updated@gmail.com", "456", "0987654321",
                "New City", date, new ArrayList<>());

        when(userRepository.findByClientId("IS104")).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Act
        User result = userServiceImpl.updateUser("IS104", updatedUser);

        // Assert
        assertThat(result.getName()).isEqualTo(updatedUser.getName());
        assertThat(result.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(result.getPhone()).isEqualTo(updatedUser.getPhone());
        assertThat(result.getCity()).isEqualTo(updatedUser.getCity());
        assertThat(result.getLastTradeDate()).isEqualTo(updatedUser.getLastTradeDate());

        verify(userRepository, times(1)).findByClientId("IS104");  // Verify findByClientId was called once
        verify(userRepository, times(1)).save(existingUser);  // Verify save was called once
    }
}