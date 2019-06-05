package com.czarea.rest.controller;

import com.czarea.rest.vo.Foo;
import com.czarea.rest.vo.Grid;
import com.czarea.rest.vo.PageRequest;
import com.czarea.rest.vo.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhouzx
 */
@RestController
@Slf4j
public class FooController {

    private List<Foo> fooList = Collections.synchronizedList(new ArrayList<>());

    public FooController() {
        for (int i = 0; i < 100; i++) {
            Foo foo = new Foo();
            foo.setId((long) i);
            foo.setName("name" + i);
            foo.setCreateTime(new Date());
            fooList.add(foo);
        }
    }

    @GetMapping("/foos")
    public Response<Grid<Foo>> list(Page page) {
        Integer offset = page.getOffset();
        Integer size = page.getLimit();
        int lastSize = offset * size + size;

        List<Foo> list = new ArrayList<>();
        for (int i = offset * size; i < fooList.size(); i++) {
            if (i < lastSize) {
                if (page.getFilter() == null) {
                    list.add(fooList.get(i));
                } else {
                    if (fooList.get(i).getName().equalsIgnoreCase(page.getFilter().getName())) {
                        list.add(fooList.get(i));
                        break;
                    }
                }
            }
        }
        return new Response<>(new Grid<>(fooList.size(), fooList.size() / size, list));
    }

    @GetMapping("/foos/{id}")
    public Response<Foo> get(@PathVariable("id") Long id) {
        Foo foo = fooList.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
        return new Response<>(foo);
    }

    @GetMapping("/foos/s/{id}")
    public Foo sget(@PathVariable("id") Long id) {
        return fooList.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping("/foos")
    public Response save(@RequestBody Foo foo) {
        fooList.add(foo);
        return Response.SUCCESS;
    }

    @DeleteMapping("/foos/{id}")
    public Response delete(@PathVariable("id") Long id) {
        fooList.removeIf(item -> item.getId().equals(id));
        return Response.SUCCESS;
    }

    @PutMapping("/foos")
    public Response update(@RequestBody Foo foo) {
        fooList.forEach(item -> {
            if (item.getId().equals(foo.getId())) {
                BeanUtils.copyProperties(foo, item);
                return;
            }
        });
        return Response.SUCCESS;
    }

    @PostMapping("/foos/upload")
    public Response upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST.value(), "文件不能为空！");
        }
        try {
            var fileName = file.getOriginalFilename();
            var is = file.getInputStream();
            Files.copy(is, Paths.get("D:/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return new Response(HttpStatus.BAD_REQUEST.value(), "写文件出错！");
        }
        return Response.SUCCESS;
    }

    public static class Page extends PageRequest<Foo> {

        public Page(Integer offset, Integer limit, com.czarea.rest.vo.Foo filter) {
            super(offset, limit, filter);
        }
    }
}
