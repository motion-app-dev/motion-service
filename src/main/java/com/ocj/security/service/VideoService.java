package com.ocj.security.service;

import com.ocj.security.commom.ResponseResult;
import com.ocj.security.domain.dto.PublishVideoRequest;
import com.ocj.security.domain.dto.PageRequest;
import com.ocj.security.domain.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocj.security.domain.vo.PageVO;
import com.ocj.security.domain.vo.VideoDataVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author L
* @description 针对表【video】的数据库操作Service
* @createDate 2023-10-28 09:11:25
*/
public interface VideoService extends IService<Video> {

    //上传视频
    ResponseResult publishVideo(MultipartFile file);


    ResponseResult publishVideo(MultipartFile file, @RequestBody PublishVideoRequest publishVideoRequest) ;
//    获取视频列表(随机)
    List<VideoDataVO> getVideoList();

    PageVO getVideoByName(PageRequest pageRequest,String videoName);

    PageVO getVideoList(PageRequest pageRequest);

    ////获取视频详细信息(给前端的)（根据videoId，返回DataVO）
    VideoDataVO getVideoDataById(String videoId);

}
