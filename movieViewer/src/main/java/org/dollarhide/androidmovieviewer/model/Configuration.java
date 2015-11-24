package org.dollarhide.androidmovieviewer.model;

import java.util.HashSet;
import java.util.Set;

public class Configuration {

    private String baseImageUrl;
    private Set<String> posterSizes = new HashSet<String>();

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public Set<String> getPosterSizes() {
        if (posterSizes == null) {
            posterSizes = new HashSet<String>();
        }

        return posterSizes;
    }

    public void setPosterSizes(Set<String> posterSizes) {
        this.posterSizes = posterSizes;
    }
}
