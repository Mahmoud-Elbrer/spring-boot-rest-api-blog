package com.spring.boot.blog.service.impl;

import com.spring.boot.blog.entity.Comment;
import com.spring.boot.blog.entity.Post;
import com.spring.boot.blog.excpetion.BlogApiException;
import com.spring.boot.blog.excpetion.ResourceNotFoundException;
import com.spring.boot.blog.payload.CommentDto;
import com.spring.boot.blog.repository.CommentRepository;
import com.spring.boot.blog.repository.PostRepository;
import com.spring.boot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	// @Autowired // if your write autowired no need to make postRepository as constructor
	private final PostRepository postRepository;

	private final ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);

		// retrieve the post entity
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		/// set post to comment entity
		comment.setPost(post);

		// comment entity to DB
		Comment newComment = commentRepository.save(comment);

		return mapToDTO(newComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long id) {

		// retrieve comments by post id from DB
		List<Comment> comments = commentRepository.findByPostId(id);

		// convert list id entities to list of comments DTO
		return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {

		// retrieve the post entity
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comments by post id from DB
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// check if comment belong to post
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doss not belong to post");
		}

		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		// retrieve the post entity
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comments by post id from DB
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// check if comment belong to post
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doss not belong to post");
		}

		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setContent(commentDto.getContent());

		commentRepository.save(comment);

		return mapToDTO(comment);
	}

	@Override
	public void deleteCommentById(long postId, long commentId) {

		boolean IsExist = IsExistPostOrComment(postId, commentId);

		// is true
		if (!IsExist) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doss not belong to post");
		}

		commentRepository.deleteById(commentId);

	}

	private CommentDto mapToDTO(Comment comment) {
		return mapper.map(comment, CommentDto.class);
		// CommentDto commentDto = new CommentDto();
		// commentDto.setId(comment.getId());
		// commentDto.setName(comment.getName());
		// commentDto.setEmail(comment.getEmail());
		// commentDto.setContent(comment.getContent());
		// return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		return mapper.map(commentDto, Comment.class);
		// Comment comment = new Comment();
		// comment.setId(commentDto.getId());
		// comment.setName(commentDto.getName());
		// comment.setEmail(commentDto.getEmail());
		// comment.setContent(commentDto.getContent());
		// return comment;
	}

	private boolean IsExistPostOrComment(long postId, long commentId) {
		// retrieve the post entity
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve comments by post id from DB
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// check if comment belong to post // true == available comment and false == not
		// available comment
		return comment.getPost().getId().equals(post.getId());
	}

}
