package com.taskjtatipera.githubrepoapi.service;

import com.taskjtatipera.githubrepoapi.constants.ErrorMessages;
import com.taskjtatipera.githubrepoapi.constants.GithubConstants;
import com.taskjtatipera.githubrepoapi.model.ErrorResponse;
import com.taskjtatipera.githubrepoapi.model.Branch;
import com.taskjtatipera.githubrepoapi.model.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GithubRepositoryService {

    public ResponseEntity<?> getRepositories(String username) {
        String url = GithubConstants.GITHUB_API_URL + "/users/" + username + "/repos";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Map[]> response = restTemplate.getForEntity(url, Map[].class);
            List<Repository> repositories = new ArrayList<>();

            for (Map<String, Object> repo : response.getBody()) {
                if (!(boolean) repo.get("fork")) {
                    String repoName = (String) repo.get("name");
                    String ownerLogin = (String) ((Map<String, Object>) repo.get("owner")).get("login");

                    String branchesUrl = (String) repo.get("branches_url");
                    branchesUrl = branchesUrl.replace("{/branch}", "");

                    ResponseEntity<Map[]> branchesResponse = restTemplate.getForEntity(branchesUrl, Map[].class);
                    List<Branch> branches = new ArrayList<>();

                    for (Map<String, Object> branch : branchesResponse.getBody()) {
                        branches.add(new Branch((String) branch.get("name"), (String) ((Map<String, Object>) branch.get("commit")).get("sha")));
                    }

                    repositories.add(new Repository(repoName, ownerLogin, branches));
                }
            }

            return ResponseEntity.ok(repositories);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ErrorMessages.USER_NOT_FOUND));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorMessages.INTERNAL_SERVER_ERROR));
        }
    }
}
