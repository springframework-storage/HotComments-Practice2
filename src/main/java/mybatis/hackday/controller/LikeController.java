package mybatis.hackday.controller;

import mybatis.hackday.dto.Comment;
import mybatis.hackday.dto.Post;
import mybatis.hackday.model.DefaultResponse;
import mybatis.hackday.model.StatusEnum;
import mybatis.hackday.service.CommentService;
import mybatis.hackday.service.LikeService;
import mybatis.hackday.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("board")
public class LikeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private PostService postService;

    @GetMapping("{categoryId}/{postNo}/{commentId}")
    public ResponseEntity<DefaultResponse> likeComment(@PathVariable int categoryId, @PathVariable int postNo, @PathVariable int commentId) {
        DefaultResponse res = new DefaultResponse();
        Post post = postService.findByCategoryIdAndNo(categoryId, postNo);
        Comment comment = commentService.findByCategoryIdAndPostNoAndId(categoryId, post.getNo(), commentId);
        likeService.insert(categoryId, post.getNo(), comment.getId());

        res.setData(comment);
        res.setMsg("좋아요");
        res.setStatusEnum(StatusEnum.SUCCESS);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
