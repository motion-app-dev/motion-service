package com.ocj.security;

import com.ocj.security.config.QinuConfig;
import com.ocj.security.domain.entity.Video;
import com.ocj.security.domain.entity.VideoCover;
import com.ocj.security.domain.vo.CoverVO;
import com.ocj.security.domain.vo.VideoDataVO;
import com.ocj.security.mapper.VideoCoverMapper;
import com.ocj.security.mapper.VideoMapper;
import com.ocj.security.service.FileService;
import com.ocj.security.service.QiniuApiService;
import com.ocj.security.service.VideoService;
import com.ocj.security.utils.FileToMultipartFileConverter;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import com.qiniu.util.UrlSafeBase64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class MySecurityApplicationTests {

    @Resource
    VideoService videoService;

//    @Test
//    void contextLoads() {
//
//        String folderPath = "D:\\抖音视频\\日常"; // 指定文件夹路径
//
//        File folder = new File(folderPath);
//
//        if (folder.isDirectory()) {
//            File[] files = folder.listFiles();
//
//            if (files != null) {
//                List<MultipartFile> multipartFiles = new ArrayList<>();
//
//                for (File file : files) {
//                    try {
//                        MultipartFile multipartFile = FileToMultipartFileConverter.convertFileToMultipartFile(file);
//                        multipartFiles.add(multipartFile);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                // 现在您可以使用multipartFiles进行后续操作
//                for (int i = 0; i < multipartFiles.size(); i++) {
//                    videoService.addVideo(multipartFiles.get(i));
//                    System.out.printf(String.valueOf(i));
//                }
//            } else {
//                System.err.println("No files found in the specified folder.");
//            }
//        } else {
//            System.err.println("The specified path is not a folder.");
//        }
//
//    }
//
//    @Test
//    void cu(){
//        List<VideoDataVO> videoList = videoService.getVideoList();
//
//        System.out.println(videoList);
//    }

    @Resource
    QinuConfig qinuConfig;
    @Resource
    FileService fileService;

    @Test
    void showConfig() {
//        String accessKey = qinuConfig.getAccessKey();
//        String bucket = qinuConfig.getBucket();
//        System.out.printf(accessKey+bucket);

//        fileService.processFile("video/video:00e1030e235a4964.mp4","vframe/jpg/offset/1","success.jpg");


    }

    @Resource
    VideoMapper videoMapper;

    @Test
    void mapperUpdate() {
//        List<Video> videos = videoMapper.selectList(null);
//        for (Video video:videos){
//
//            String url = video.getUrl();
//            String substring = url.substring(29);
////            System.out.println(substring);
////            video.setAddress(substring);
////            videoMapper.updateById(video);
//        }
    }

    @Test
    void cu() {

//        String accessKey = "MAh90OZvgbaAY6m5g8DhtNC5s7TtFomeGHu2pCrT";
//        String secretKey = "Zm2fQUO3zMIH7R8psNby8oCqywT8QshxcXjNc54A";
//        String bucket = "motion1024";
//
//        Auth auth = Auth.create(accessKey, secretKey);
//
//        System.out.println(auth.toString());
//        String upToken = auth.uploadToken(bucket);
//
//        System.out.println(upToken);

//
        String accessKey = "MAh90OZvgbaAY6m5g8DhtNC5s7TtFomeGHu2pCrT";
        String secretKey = "Zm2fQUO3zMIH7R8psNby8oCqywT8QshxcXjNc54A";
        String bucket = "motion1024";
//待处理文件名
//        String key = "video/video:0375101c743d404a.mp4";
        String key = "video/video:00e1030e235a4964.mp4";

        Auth auth = Auth.create(accessKey, secretKey);

//数据处理指令，支持多个指令
        String saveMp4Entry = String.format("%s:avthumb_test_target.mp4", bucket);
        String saveJpgEntry = String.format("%s:vframe_test_target.jpg", bucket);
        String avthumbMp4Fop = String.format("avthumb/mp4|saveas/%s", UrlSafeBase64.encodeToString(saveMp4Entry));
        String vframeJpgFop = String.format("vframe/jpg/offset/1|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));
//将多个数据处理指令拼接起来
        String persistentOpfs = StringUtils.join(new String[]{
                avthumbMp4Fop, vframeJpgFop
        }, ";");

//数据处理队列名称，必须
        String persistentPipeline = "testQu";
//数据处理完成结果通知地址
        String persistentNotifyUrl = "http://xiaoligongzuoshi.top/notify";

//构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释

//构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);


        String fops = "vframe/jpg/offset/7";

        System.out.println(fops);
        try {

            String persistentId = operationManager.pfop(bucket, key, fops, null, null, true);
            //可以根据该 persistentId 查询任务处理进度
            System.out.println(persistentId);

            OperationStatus operationStatus = operationManager.prefop(persistentId);
            //解析 operationStatus 的结果

            System.out.printf(operationStatus.inputBucket + operationStatus.inputKey);

            System.out.println(operationStatus.toString());
        } catch (QiniuException e) {

            System.out.printf("异常拿了");
            System.err.println(e.response.toString());
        }

    }

    @Test
    void pu() {
        //设置账号的AK,SK
        String ACCESS_KEY = "MAh90OZvgbaAY6m5g8DhtNC5s7TtFomeGHu2pCrT";
        String SECRET_KEY = "Zm2fQUO3zMIH7R8psNby8oCqywT8QshxcXjNc54A";
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //新建一个OperationManager对象
        OperationManager operater = new OperationManager(auth, cfg);
        //设置要转码的空间和key，并且这个key在你空间中存在
        String bucket = "motion1024";
        String key = "video/video:00e1030e235a4964.mp4";
        //设置转码操作参数
        String fops = "vframe/jpg/offset/7";
        //设置转码的队列
        String pipeline = "testQu";
        //可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
        String urlbase64 = UrlSafeBase64.encodeToString("motion1024:test123.png");
        String pfops = fops + "|saveas/" + urlbase64;
        //设置pipeline参数
        StringMap params = new StringMap().putWhen("force", 1, true).putNotEmpty("pipeline", null);
        try {
            String persistid = operater.pfop(bucket, key, pfops, params);


            //打印返回的persistid
            System.out.println(persistid);


        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            // 请求失败时简单状态信息
            System.out.println(r.toString());
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }

    @Resource
    VideoCoverMapper videoCoverMapper;

    @Test
    void updateVideoCover() {

//        List<Video> videos = videoMapper.selectList(null);
//        for (Video video : videos) {
//
//            fileService.processFile(video.getAddress(),"vframe/jpg/offset/1","/videoCover/"+video.getVideoId()+".jpg");
//            VideoCover videoCover = new VideoCover();
//            videoCover.setVideoId(video.getVideoId());
//            videoCover.setVideoCoverUrl(qinuConfig.getDomain()+"/videoCover/"+video.getVideoId()+".jpg");
//            videoCover.setCoverAddress("videoCover/"+video.getVideoId()+".jpg");
//            videoCoverMapper.insert(videoCover);
//        }

//        List<VideoCover> videoCovers = videoCoverMapper.selectList(null);
//
//        for (VideoCover videoCover:videoCovers){
//            String videoCoverUrl = videoCover.getVideoCoverUrl();
//            System.out.println(videoCoverUrl);
//
//            try {
//                URL url = new URL("http://"+videoCoverUrl);
//                BufferedImage image = ImageIO.read(url);
//
//                if (image != null) {
//                    int width = image.getWidth();
//                    int height = image.getHeight();
//
//                    System.out.println("Width: " + width);
//                    System.out.println("Height: " + height);
//                    videoCover.setLength(height);
//                    videoCover.setWidth(width);
//
//
//                } else {
//                    System.out.println("无法读取图像");
//
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            videoCoverMapper.updateById(videoCover);
//        }
    }

    @Test
    void testPhoto() {
//        List<Video> videos = videoMapper.selectList(null);
//
//        for (Video video:videos){
//
//            String url = video.getUrl();
//            video.setUrl("http://"+url);
//
//            videoMapper.updateById(video);
//        }

        List<VideoCover> videoCovers = videoCoverMapper.selectList(null);
        for (VideoCover videoCover : videoCovers) {
            String videoCoverUrl = videoCover.getVideoCoverUrl();

            videoCover.setVideoCoverUrl("http://" + videoCoverUrl);

            videoCoverMapper.updateById(videoCover);
        }
    }

    @Resource
    Auth auth;
    @Resource
    QiniuApiService qiniuApiService;

    @Test
    void pt() throws QiniuException {
//
//        System.out.println(auth);
//        String s = qiniuApiService.ImageCensor("http://s36fh9xu3.hn-bkt.clouddn.com//videoCover/video:00e1030e235a4964.jpg");
//
//        System.out.println(s);
        // String videoCensorResultByJobID = qiniuApiService.getVideoCensorResultByJobID("6541a661000187045bc68636153ee893");
//        System.out.println(s);
        //System.out.println(videoCensorResultByJobID);
//        String s = qiniuApiService.VideoCensor("http://s36fh9xu3.hn-bkt.clouddn.com/video/video%3A03c591d441514b61.mp4");
//
//        System.out.println(s);
//  System.out.println(qiniuApiService.getVideoCensorResultByJobID("6541aa06a2cfbcaec1db2747"));
    }


    @Test
    void geToken() {
//        String method = "POST"; // 替换为你的请求方法，如 POST
//        String path = "/v3/jobs/video/6541ed8a4bfb6070fd95aac9"; // 替换为你的请求路径
//        String rawQuery = ""; // 替换为你的查询参数，如果为空则留空字符串
//        String host = "ai.qiniuapi.com"; // 替换为你的请求域名
//        String contentType = "application/json"; // 替换为你的请求内容类型，如果为空则留空字符串
//        String bodyStr="";
//        //String bodyStr = "{\"data\": {\"uri\":\"http://img3.redocn.com/20120528/Redocn_2012052817213559.jpg\"}}"; // 替换为请求体内容，如果为空则留空字符串
//        String secretKey = "Zm2fQUO3zMIH7R8psNby8oCqywT8QshxcXjNc54A"; // 替换为你的密钥
//        String accessKey = "MAh90OZvgbaAY6m5g8DhtNC5s7TtFomeGHu2pCrT"; // 替换为你的访问密钥
//
//        // Step 1: 构造待签名的 Data
//        StringBuilder data = new StringBuilder();
//        data.append(method).append(" ").append(path);
//        if (!rawQuery.isEmpty()) {
//            data.append("?").append(rawQuery);
//        }
//        data.append("\nHost: ").append(host);
//        if (!contentType.isEmpty()) {
//            data.append("\nContent-Type: ").append(contentType);
//        }
//        data.append("\n\n");
//        if (!bodyStr.isEmpty() && !contentType.isEmpty() && !contentType.equals("application/octet-stream")) {
//            data.append(bodyStr);
//        }
//
//        try {
//            // Step 2: 计算 HMAC-SHA1 签名
//            Mac hmacSha1 = Mac.getInstance("HmacSHA1");
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA1");
//            hmacSha1.init(secretKeySpec);
//            byte[] signatureBytes = hmacSha1.doFinal(data.toString().getBytes("UTF-8"));
//
//            // Step 2: 对签名结果进行 URL 安全的 Base64 编码
//            String encodedSign = Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
//
//            // Step 3: 拼接管理凭证
//            String qiniuToken = "Qiniu " + accessKey + ":" + encodedSign;
//
//            System.out.println("Qiniu Token: " + qiniuToken);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    @Test
    void Tes() throws QiniuException {
//        String s = qiniuApiService.TextCensor("和hi");
//        System.out.println(s);
        Boolean b = qiniuApiService.processFile("video/video:a7689c00e8e74674.mp4", "?avinfo", "/videotet/www.png");
        System.out.println(b);
    }

}
