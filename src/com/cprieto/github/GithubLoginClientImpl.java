package com.cprieto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class GithubLoginClientImpl implements GithubLoginClient {
    private final String clientId = "put your client id";
    private final String clientSecret = "put your secret id";
    private final String gitHubUri = "https://api.github.com/authorizations";

    @Override
    public boolean authenticate(String username, String password) {
        HttpClient client = new HttpClient();
        client.getParams().setAuthenticationPreemptive(true);

        Credentials credentials = new UsernamePasswordCredentials(username, password);
        client.getState().setCredentials(AuthScope.ANY, credentials);

        GithubAuthRequest request = new GithubAuthRequest(clientId, clientSecret);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writer().writeValueAsString(request);

            HttpMethod method = new GetMethod(gitHubUri);

            int result = client.executeMethod(method);

            if (result != 200) {
                return false;
            }

            String response = method.getResponseBodyAsString();

            GithubAuthResponse success = mapper.reader().readValue(response);

            // TODO: we have to do something with the token, but it works
        } catch (IOException ex) {
            return false;
        } finally {
            client = null;
        }

        return true;
    }
}

class GithubAuthRequest {
    @JsonProperty("client_id")
    public String clientId;

    @JsonProperty("client_secret")
    public String clientSecret;

    public String[] scopes;

    public GithubAuthRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        scopes = new String[] { "user" };
    }
}


class GithubAuthResponse {
    public String id;
    public String url;
    public String token;
    public String note;
    @JsonProperty("note_url") public String noteUrl;
    @JsonProperty("created_at") public String createdAt;
    @JsonProperty("updated_at") public String updatedAt;
    public String[] scopes;
}