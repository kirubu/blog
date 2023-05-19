package com.gorl.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gorl.demo.dto.PostDTO;
import com.gorl.demo.dto.PostPagingResponse;
import com.gorl.demo.entity.Post;
import com.gorl.demo.exception.ResourcenotFoundException;
import com.gorl.demo.repo.PostRepo;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Override
	public PostDTO createpost(PostDTO postDto) {

		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDesc(postDto.getDesc());
		post.setContent(postDto.getContent());

		Post postResponse = postRepo.save(post);

		postDto.setId(postResponse.getId());

		return postDto;
	}

	/*
	 @Override 
	 public List<PostDTO> getAllPost() {
	  
		  List<Post> list = postRepo.findAll();
		  
		  List<PostDTO> dtoList = new ArrayList<>();
		  
		  for(Post post: list) { PostDTO dto = new PostDTO(); dto.setId(post.getId());
		  dto.setTitle(post.getTitle()); dto.setDesc(post.getContent());
		  dto.setContent(post.getContent());
		  
		  dtoList.add(dto); } return dtoList; 
	  }
	 */

	@Override
	public PostPagingResponse getAllPost(int pageNo, int pageSize, String sort) {
		
		//sorting in asc or desc, create sort object
		String[] arr = sort.split("_");//title_asc -> arr[0] = title, arr[1] = asc or desc
		Sort sortOrder = arr[1].equalsIgnoreCase("ASC")?Sort.by(arr[0]).ascending() :
												   Sort.by(arr[0]).descending();
		
		Pageable pageSupport = PageRequest.of(pageNo, pageSize, sortOrder);
		
		//Pageable pageSupport = PageRequest.of(pageNo, pageSize, Sort.by(sort));
		Page<Post> listPost = postRepo.findAll(pageSupport);
		
		List<Post> list = listPost.getContent();
		
		List<PostDTO> dtoList = new ArrayList<>();

		for (Post post : list) {
			PostDTO dto = new PostDTO();
			dto.setId(post.getId());
			dto.setTitle(post.getTitle());
			dto.setDesc(post.getContent());
			dto.setContent(post.getContent());

			dtoList.add(dto);
		}
		
		PostPagingResponse postPageRes = new PostPagingResponse();
		postPageRes.setPost(dtoList);
		postPageRes.setPageNo(listPost.getNumber());
		postPageRes.setPageSize(listPost.getSize());
		postPageRes.setTotalElements(listPost.getTotalElements());
		postPageRes.setTotalPages(listPost.getTotalPages());
		postPageRes.setLast(listPost.isLast());
		
		return postPageRes;
	}

	@Override
	public PostDTO getByid(long id) {

		Post post = postRepo.findById(id).orElseThrow(() -> new ResourcenotFoundException("Post", "id", "" + id));

		PostDTO dto = new PostDTO();
		dto.setId(post.getId());
		dto.setTitle(post.getTitle());
		dto.setDesc(post.getContent());
		dto.setContent(post.getContent());

		return dto;
	}

	@Override
	public PostDTO updatePost(long id, PostDTO dto) {

		Post post = postRepo.findById(id).orElseThrow(() -> new ResourcenotFoundException("Post", "id", "" + id));

		post.setTitle(dto.getTitle());
		post.setDesc(dto.getDesc());
		post.setContent(dto.getContent());

		post = postRepo.save(post);
		
		PostDTO dt = new PostDTO();
		dt.setId(post.getId());
		dt.setTitle(post.getTitle());
		dt.setDesc(post.getContent());
		dt.setContent(post.getContent());

		return dt;
	}

	@Override
	public void deletePostById(long id) {

		Post post = postRepo.findById(id).orElseThrow(() -> new ResourcenotFoundException("Post", "id", "" + id));
		postRepo.delete(post);
	}

}
