package com.podosoft.zenela.Dto.Responses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class SearchResponse {
    private Date time;
    private String query;
    private Collection<UserResponse> results = new ArrayList<>();

    public SearchResponse() {
    }

    public SearchResponse(Date time, String query) {
        this.time = time;
        this.query = query;
    }

    public SearchResponse(Date time, String query, Collection<UserResponse> results) {
        this.time = time;
        this.query = query;
        this.results = results;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Collection<UserResponse> getResults() {
        return results;
    }

    public void setResults(Collection<UserResponse> results) {
        this.results = results;
    }
}
