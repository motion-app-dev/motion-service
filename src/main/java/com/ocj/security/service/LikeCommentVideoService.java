package com.ocj.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocj.security.domain.entity.LikeCommentVideo;


/**
 * (LikeCommentVideo)表服务接口
 *
 * @author makejava
 * @since 2023-10-31 18:47:44
 */
public interface LikeCommentVideoService extends IService<LikeCommentVideo> {

    void saveLike(LikeCommentVideo likeCommentVideo);
}
