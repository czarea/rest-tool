package com.czarea.rest;

import com.czarea.rest.controller.FooController.Page;
import com.czarea.rest.service.RestTemplateService;
import com.czarea.rest.vo.Foo;
import com.czarea.rest.vo.Grid;
import com.czarea.rest.vo.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestApplicationTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    private UriTemplateHandler uriTemplateHandler;

    private String rootUrl = "http://localhost:8080";

    @Test
    public void testList() {
        Foo foo = new Foo(1L, "name1");
        Page page = new Page(0, 10, foo);
        String url = uriTemplateHandler.expand(rootUrl + "/foos", page).toString();
        Response<Grid<Foo>> response = restTemplateService
            .request(url, HttpMethod.GET, null, new ParameterizedTypeReference<Response<Grid<Foo>>>() {
            });
        Assert.assertEquals(100, response.getData().getTotal());
    }

    @Test
    public void testGet() {
        Response<Foo> response = restTemplateService
            .request(rootUrl + "/foos/1", HttpMethod.GET, null, new ParameterizedTypeReference<Response<Foo>>() {});
        Assert.assertEquals(response.getData().getName(), "name1");
    }

    @Test
    public void testSGet() {
        Foo foo = restTemplate.getForEntity(rootUrl + "/foos/s/1", Foo.class).getBody();
        Assert.assertEquals(foo.getName(), "name1");
    }

    @Test
    public void testUpdate() {
        Foo foo = new Foo(1L, "zzx");
        restTemplate.put(rootUrl + "/foos", foo);
        Foo getFoo = restTemplate.getForEntity(rootUrl + "/foos/s/1", Foo.class).getBody();
        Assert.assertEquals(getFoo.getName(), "zzx");
    }

    @Test
    public void testSave() {
        Foo foo = new Foo(1000L, "foo");
        Response response = restTemplate.postForEntity(rootUrl + "/foos", foo, Response.class).getBody();
        Assert.assertEquals(response.getCode(), 200);
    }

    @Test
    public void testDelete() {
        restTemplate.delete(rootUrl + "/foos/1");
    }

    @Test
    public void testUri() {
        UriTemplateHandler uriTemplateHandler = restTemplate.getUriTemplateHandler();
        System.out.println(uriTemplateHandler);
    }

    @Test
    public void testUpload() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("E:\\lvji_git\\rest-tool\\src\\main\\resources\\test.txt"));
        MultiValueMap<String, MultipartFileResource> multiPartBody = new LinkedMultiValueMap<>();
        multiPartBody.add("file", new MultipartFileResource(inputStream, "file.txt"));
        RequestEntity<MultiValueMap<String, MultipartFileResource>> requestEntity = RequestEntity
            .post(URI.create(rootUrl + "/foos/upload"))
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(multiPartBody);
        Response response = restTemplate.exchange(rootUrl + "/foos/upload", HttpMethod.POST, requestEntity, Response.class).getBody();
        Assert.assertEquals(response.getCode(), 200);
    }

    @Test
    public void testUploadByResource() {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource("test.txt"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        ResponseEntity<Response> result = restTemplate.exchange(rootUrl + "/foos/upload", HttpMethod.POST, requestEntity, Response.class);
        Assert.assertEquals(result.getBody().getCode(), 200);
    }



    public class MultipartFileResource extends InputStreamResource {

        private String filename;

        public MultipartFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() throws IOException {
            return -1;
        }
    }

    @Test
    public void testDownload() throws UnsupportedEncodingException {
        // 小文件
        RequestEntity requestEntity = RequestEntity.get(URI.create(rootUrl + "/foos/download")).build();
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
        byte[] downloadContent = responseEntity.getBody();

        String str = new String(downloadContent, "UTF-8");

        // 大文件
        ResponseExtractor<ResponseEntity<File>> responseExtractor = response -> {
            File rcvFile = File.createTempFile("rcvFile", "zip");
            FileCopyUtils.copy(response.getBody(), new FileOutputStream(rcvFile));
            return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders()).body(rcvFile);
        };
        ResponseEntity<File> getFile = this.restTemplate
            .execute(rootUrl + "/foos/download", HttpMethod.GET, null, responseExtractor);
    }

}
