package domainSimilarity.beans;

/**
 * Created by lenovo on 2017/7/31.
 */
public class Doc {
    private String url;
    private String body;

    public Doc(String url, String body) {
        this.url = url;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
