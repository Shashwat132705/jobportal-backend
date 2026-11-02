
package com.jobportal.jobportal.dto;

import com.jobportal.jobportal.Job;
import java.util.List;

public class JobPageResponse {

    private List<JobResponseDTO> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public JobPageResponse(
            List<JobResponseDTO> content,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<JobResponseDTO> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public boolean isLast() { return last; }
}
