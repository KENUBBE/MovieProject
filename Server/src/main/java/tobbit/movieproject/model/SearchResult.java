package tobbit.movieproject.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {

    @JsonProperty("Search")
    private ArrayList<Movie> search = new ArrayList<>();
    @JsonProperty("totalResults")
    private String totalResults;
    @JsonProperty("Response")
    private String response;

    public SearchResult() {
    }

    public ArrayList<Movie> getSearch() {
        return search;
    }

    public void setSearch(ArrayList<Movie> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    /*@Override
    public String toString() {
        return "SearchResult{" +
                "search=" + search +
                ", totalResults='" + totalResults + '\'' +
                ", response='" + response + '\'' +
                '}';
    }*/
}
