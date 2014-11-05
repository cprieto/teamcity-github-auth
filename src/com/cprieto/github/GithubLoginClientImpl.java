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
    public boolean authenticate(String username, String password) throws IOException {
        HttpClient client = new HttpClient();
        client.getParams().setAuthenticationPreemptive(true);

        Credentials credentials = new UsernamePasswordCredentials(username, password);
        client.getState().setCredentials(AuthScope.ANY, credentials);

        GithubAuthRequest request = new GithubAuthRequest(clientId, clientSecret);

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writer().writeValueAsString(request);

        HttpMethod method = new GetMethod(gitHubUri);

        int result = client.executeMethod(method);

        client = null;

        return false;
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
