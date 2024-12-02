//package org.techwork.verycool.services.impl;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.techwork.verycool.models.entities.CommentEntity;
//import org.techwork.verycool.models.entities.IdeaEntity;
//import org.techwork.verycool.repositories.CommentRepository;
//import org.techwork.verycool.utils.MapperUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class CommentServiceImplTest {
//
//    @Mock
//    private CommentRepository commentRepository;
//
//    @InjectMocks
//    private CommentServiceImpl commentService;
//
//    private List<CommentEntity> commentList = new ArrayList<>();
//    private CommentEntity comment1;
//    private CommentEntity comment2;
//
//    @BeforeEach
//    void setUp() throws IOException {
//        // Créer des objets de test
//        commentList =  MapperUtils.objectMapper().readValue(
//                new File("src/test/resources/data/comments.json"),
//                new TypeReference<>() {});
//
//        comment1 = commentList.get(0);
//        comment2 = commentList.get(1);
//    }
//
//    @Test
//    void shouldReturnAllComments_whenFindAllIsCalled() {
//        // Given
//        when(commentRepository.findAll()).thenReturn(commentList);
//
//        // When
//        List<CommentEntity> result = commentService.findAll();
//
//        // Then
//        assertEquals(2, result.size());
//        assertEquals("First comment", result.get(0).getContent());
//        verify(commentRepository, times(1)).findAll();
//    }
//
//    @Test
//    void shouldReturnEmptyList_whenNoCommentsExist() {
//        // Given
//        when(commentRepository.findAll()).thenReturn(List.of());
//
//        // When
//        List<CommentEntity> result = commentService.findAll();
//
//        // Then
//        assertTrue(result.isEmpty());
//        verify(commentRepository, times(1)).findAll();
//    }
//
//    @Test
//    void shouldReturnComment_whenValidIdIsGiven() {
//        // Given
//        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment1));
//
//        // When
//        CommentEntity result = commentService.findOne(1L);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("First comment", result.getContent());
//        verify(commentRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void shouldReturnNull_whenInvalidIdIsGiven() {
//        // Given
//        when(commentRepository.findById(999L)).thenReturn(Optional.empty());
//
//        // When
//        CommentEntity result = commentService.findOne(999L);
//
//        // Then
//        assertNull(result);
//        verify(commentRepository, times(1)).findById(999L);
//    }
//
//    @Test
//    void shouldReturnUserComments_whenValidUserIdIsGiven() {
//        // Given
//        Long userId = 1L;
//        when(commentRepository.findAllByUserId(userId)).thenReturn(List.of(comment1));
//
//        // When
//        List<CommentEntity> result = commentService.findUserComment(userId);
//
//        // Then
//        assertEquals(1, result.size());
//        assertEquals("First comment", result.get(0).getContent());
//        verify(commentRepository, times(1)).findAllByUserId(userId);
//    }
//
//    @Test
//    void shouldReturnEmptyList_whenUserHasNoComments() {
//        // Given
//        Long userId = 999L;
//        when(commentRepository.findAllByUserId(userId)).thenReturn(List.of());
//
//        // When
//        List<CommentEntity> result = commentService.findUserComment(userId);
//
//        // Then
//        assertTrue(result.isEmpty());
//        verify(commentRepository, times(1)).findAllByUserId(userId);
//    }
//
//    @Test
//    void shouldSaveAndReturnComment_whenValidDataIsGiven() {
//        // Given
//        CommentEntity newComment = new CommentEntity(null, new IdeaEntity(), null, "New comment", null);
//        CommentEntity savedComment = new CommentEntity(3L, new IdeaEntity(), null, "New comment", null);
//        when(commentRepository.save(newComment)).thenReturn(savedComment);
//
//        // When
//        CommentEntity result = commentService.saveComment(newComment);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(3L, result.getId());
//        assertEquals("New comment", result.getContent());
//        verify(commentRepository, times(1)).save(newComment);
//    }
//
//    @Test
//    void shouldDeleteComment_whenValidCommentIsGiven() {
//        // Given
//        doNothing().when(commentRepository).delete(comment1);
//
//        // When
//        commentService.deleteComment(comment1);
//
//        // Then
//        verify(commentRepository, times(1)).delete(comment1);
//    }
//
//    @Test
//    void shouldThrowException_whenDeletingNullComment() {
//        // Given
//        doThrow(new IllegalArgumentException("Comment cannot be null")).when(commentRepository).delete(null);
//
//        // When/Then
//        assertThrows(IllegalArgumentException.class, () -> commentService.deleteComment(null));
//        verify(commentRepository, times(1)).delete(null);
//    }
//}
