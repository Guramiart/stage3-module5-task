package com.mjc.school.service.dto;

import org.springframework.lang.Nullable;

public class PageDtoRequest {

    private int page = 1;
    private int size = 5;

    public PageDtoRequest(@Nullable int page, @Nullable int size) {
        if(page > 0) {
            this.page = page;
        }
        if(size > 0) {
            this.size = size;
        }
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
